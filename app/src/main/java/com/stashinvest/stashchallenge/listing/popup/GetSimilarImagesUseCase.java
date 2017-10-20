package com.stashinvest.stashchallenge.listing.popup;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetSimilarImagesUseCase {

    private final GettyImageService gettyImageService;

    @Inject
    GetSimilarImagesUseCase(GettyImageService gettyImageService) {
        this.gettyImageService = gettyImageService;
    }

    Single<ImageResponse> getImages(String imageId) {
        return gettyImageService.getSimilarImages(imageId);
    }
}
