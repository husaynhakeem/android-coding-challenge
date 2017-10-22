package com.stashinvest.stashchallenge.listing.popup;

import android.util.Log;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.api.model.Metadata;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PopUpDialogPresenter {

    private static final int NUMBER_OF_SIMILAR_ADS_TO_DISPLAY = 3;
    private static final int FIRST_METADATA = 0;

    private final GetImageMetadataUseCase getImageMetadataUseCase;
    private final GetSimilarImagesUseCase getSimilarImagesUseCase;

    private View view;
    private CompositeDisposable compositeDisposable;

    @Inject
    PopUpDialogPresenter(GetImageMetadataUseCase getImageMetadataUseCase, GetSimilarImagesUseCase getSimilarImagesUseCase) {
        this.getImageMetadataUseCase = getImageMetadataUseCase;
        this.getSimilarImagesUseCase = getSimilarImagesUseCase;
    }

    void setView(View view) {
        this.view = view;
    }

    void start(String imageId, String imageUrl) {
        compositeDisposable = new CompositeDisposable();
        displayImage(imageUrl);
        loadImageMetadata(imageId);
        loadSimilarImages(imageId);
    }

    private void displayImage(String imageUrl) {
        view.hideKeyboard();
        view.displayImage(imageUrl);
    }

    private void loadImageMetadata(String imageId) {
        Disposable disposable = getImageMetadataUseCase.getMetadata(imageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMetadataLoaded, this::onError);
        compositeDisposable.add(disposable);
    }

    private void onMetadataLoaded(MetadataResponse metadataResponse) {
        try {
            Metadata metadata = metadataResponse.getMetadata().get(FIRST_METADATA);
            view.displayImageTitle(metadata.getTitle());
            view.displayImageArtist(metadata.getArtist());
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            view.onError();
            e.printStackTrace();
        }
    }

    private void loadSimilarImages(String imageId) {
        Disposable disposable = getSimilarImagesUseCase.getImages(imageId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSimilarImagesLoaded, this::onError);
        compositeDisposable.add(disposable);
    }

    private void onSimilarImagesLoaded(ImageResponse imageResponse) {
        List<ImageResult> images = imageResponse.getImages();
        int numberOfSimilarImages = Math.min(imageResponse.getResultCount(), NUMBER_OF_SIMILAR_ADS_TO_DISPLAY);

        int index = 0;
        while (index < numberOfSimilarImages) {
            view.displaySimilarImage(index, images.get(index).getThumbUri());
            index++;
        }
    }

    private void onError(Throwable throwable) {
        view.onError();
        Log.e(PopUpDialogPresenter.class.getSimpleName(),
                "Error while loading popUpDialog data: " + throwable.getMessage());
    }

    void reset() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }

    interface View {
        void hideKeyboard();

        void displayImage(String imageUrl);

        void displayImageTitle(String imageTitle);

        void displayImageArtist(String imageArtist);

        void displaySimilarImage(int index, String imageUrl);

        void onError();
    }
}
