package com.stashinvest.stashchallenge.listing.popup;

import android.util.Log;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.api.model.Metadata;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class PopUpDialogPresenter {

    private static final String TAG = PopUpDialogPresenter.class.getSimpleName();
    private static final String EMPTY = "";
    private static final int NUMBER_OF_SIMILAR_ADS_TO_DISPLAY = 3;
    private static final int FIRST_METADATA = 0;

    private final GetImageMetadataUseCase getImageMetadataUseCase;
    private final GetSimilarImagesUseCase getSimilarImagesUseCase;

    private View view;

    @Inject
    public PopUpDialogPresenter(GetImageMetadataUseCase getImageMetadataUseCase, GetSimilarImagesUseCase getSimilarImagesUseCase) {
        this.getImageMetadataUseCase = getImageMetadataUseCase;
        this.getSimilarImagesUseCase = getSimilarImagesUseCase;
    }

    void setView(View view) {
        this.view = view;
    }

    void start(String imageId, String imageUrl) {
        displayImage(imageUrl);
        loadImageMetadata(imageId);
        loadSimilarImages(imageId);
    }

    private void displayImage(String imageUrl) {
        view.hideKeyboard();
        view.displayImage(imageUrl);
    }

    private void loadImageMetadata(String imageId) {
        getImageMetadataUseCase.getMetadata(imageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMetadataLoaded, this::onError);
    }

    private void onMetadataLoaded(MetadataResponse metadataResponse) {
        String title = EMPTY;
        String artist = EMPTY;
        try {
            Metadata metadata = metadataResponse.getMetadata().get(FIRST_METADATA);
            title = metadata.getTitle();
            artist = metadata.getArtist();
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        view.displayImageMetadata(title, artist);
    }

    private void loadSimilarImages(String imageId) {
        getSimilarImagesUseCase.getImages(imageId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSimilarImagesLoaded, this::onError);
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
        Log.e(TAG, "Error while loading popUpDialog data: " + throwable.getMessage());
    }

    interface View {
        void hideKeyboard();

        void displayImage(String imageUrl);

        void displayImageMetadata(String imageTitle, String imageArtist);

        void displaySimilarImage(int index, String imageUrl);

        void onError();
    }
}
