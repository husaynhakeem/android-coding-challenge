package com.stashinvest.stashchallenge.listing.main;

import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.listing.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class MainPresenter {

    private static final String TAG = MainPresenter.class.getName();

    private final LoadGettyImagesUseCase loadGettyImagesUseCase;
    private final GettyImageFactory gettyImageFactory;

    private View view;
    private CompositeDisposable compositeDisposable;

    @Inject
    MainPresenter(LoadGettyImagesUseCase getImagesUseCase, GettyImageFactory gettyImageFactory) {
        this.loadGettyImagesUseCase = getImagesUseCase;
        this.gettyImageFactory = gettyImageFactory;
    }

    void setView(View view) {
        this.view = view;
    }

    void search(String keyword) {
        view.hideKeyboard();
        view.displayLoadingIndicator(true);

        reInitializeCompositeDisposable();

        Disposable disposable = loadGettyImagesUseCase.loadImages(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onImagesInitiallyLoaded, this::onFailure);
        compositeDisposable.add(disposable);
    }

    private void reInitializeCompositeDisposable() {
        reset();
        compositeDisposable = new CompositeDisposable();
    }

    void onImagesInitiallyLoaded(ImageResponse imageResponse) {
        Disposable disposable = Observable.just(imageResponse.getImages())
                .flatMapIterable(images -> images)
                .map(image -> (BaseViewModel) gettyImageFactory.createGettyImageViewModel(image))
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onImagesReady, this::onFailure);
        compositeDisposable.add(disposable);
    }

    private void onImagesReady(List<BaseViewModel> gettyImageViewModels) {
        view.displayLoadingIndicator(false);
        view.displayImages(gettyImageViewModels);
    }

    private void onFailure(Throwable throwable) {
        view.displayLoadingIndicator(false);
        view.onFailure();
        Log.e(TAG, "Error while loading the images: " + throwable.getMessage());
    }

    void reset() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }

    @VisibleForTesting
    void setCompositeDisposable(CompositeDisposable compositeDisposable) {
        this.compositeDisposable = compositeDisposable;
    }

    @VisibleForTesting
    CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    interface View {
        void hideKeyboard();

        void displayLoadingIndicator(boolean visible);

        void displayImages(List<BaseViewModel> images);

        void onFailure();
    }
}
