package com.stashinvest.stashchallenge.listing.popup;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetImageMetadataUseCase {

    private final GettyImageService gettyImageService;

    @Inject
    GetImageMetadataUseCase(GettyImageService gettyImageService) {
        this.gettyImageService = gettyImageService;
    }

    Single<MetadataResponse> getMetadata(String imageId) {
        return gettyImageService.getImageMetadata(imageId);
    }
}
