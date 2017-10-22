package com.stashinvest.stashchallenge.listing.main;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageResponse;

import javax.inject.Inject;

import io.reactivex.Single;

class LoadGettyImagesUseCase {

    private final GettyImageService gettyImageService;

    @Inject
    LoadGettyImagesUseCase(GettyImageService gettyImageService) {
        this.gettyImageService = gettyImageService;
    }

    Single<ImageResponse> loadImages(String keyword) {
        return gettyImageService.searchImages(keyword);
    }
}
