package com.stashinvest.stashchallenge.api;

import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;

import javax.inject.Inject;

import io.reactivex.Single;

public class GettyImageService {

    private static final String FIELDS = "id,title,thumb";
    private static final String SORT_ORDER = "best";

    @Inject
    GettyImagesApi api;

    @Inject
    GettyImageService() {
    }

    public Single<ImageResponse> searchImages(String phrase) {
        return api.searchImages(phrase, FIELDS, SORT_ORDER);
    }

    public Single<MetadataResponse> getImageMetadata(String id) {
        return api.getImageMetadata(id);
    }

    public Single<ImageResponse> getSimilarImages(String id) {
        return api.getSimilarImages(id);
    }
}
