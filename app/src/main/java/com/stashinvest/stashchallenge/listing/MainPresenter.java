package com.stashinvest.stashchallenge.listing;

import android.util.Log;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.listing.viewmodel.BaseViewModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

class MainPresenter {

    private static final String TAG = MainPresenter.class.getName();

    private final GetImagesUseCase getImagesUseCase;
    private final GettyImageFactory gettyImageFactory;
    private View view;

    @Inject
    MainPresenter(GetImagesUseCase getImagesUseCase, GettyImageFactory gettyImageFactory) {
        this.getImagesUseCase = getImagesUseCase;
        this.gettyImageFactory = gettyImageFactory;
    }

    void setView(View view) {
        this.view = view;
    }

    void search(String keyword) {
        view.hideKeyboard();
        view.showLoadingIndicator(true);
        getImagesUseCase.getImages(keyword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onResponse, this::onFailure);
    }

    private void onResponse(ImageResponse imageResponse) {
        Observable.just(imageResponse.getImages())
                .flatMapIterable(images -> images)
                .map(image -> (BaseViewModel) gettyImageFactory.createGettyImageViewModel(image))
                .toList()
                .subscribe(this::onImagesReady, this::onFailure);
    }

    private void onImagesReady(List<BaseViewModel> gettyImageViewModels) {
        view.showLoadingIndicator(false);
        view.onResponse(gettyImageViewModels);
    }

    private void onFailure(Throwable throwable) {
        view.showLoadingIndicator(false);
        view.onFailure();
        Log.e(TAG, "Error while fetching the images: " + throwable.getMessage());
    }

    interface View {
        void hideKeyboard();

        void showLoadingIndicator(boolean visible);

        void onResponse(List<BaseViewModel> images);

        void onFailure();
    }
}
