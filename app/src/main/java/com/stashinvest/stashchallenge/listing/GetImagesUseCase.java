package com.stashinvest.stashchallenge.listing;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.ImageResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetImagesUseCase {

    private final GettyImageService gettyImageService;

    @Inject
    public GetImagesUseCase(GettyImageService gettyImageService) {
        this.gettyImageService = gettyImageService;
    }

    Single<ImageResponse> getImages(String keyword) {
        return gettyImageService.searchImages(keyword);
    }
}
