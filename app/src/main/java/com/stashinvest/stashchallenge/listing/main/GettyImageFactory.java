package com.stashinvest.stashchallenge.listing.main;

import com.stashinvest.stashchallenge.api.model.ImageResult;

import javax.inject.Inject;

class GettyImageFactory {

    @Inject
    GettyImageFactory() {
    }

    GettyImageViewModel createGettyImageViewModel(ImageResult imageResult) {
        return new GettyImageViewModel(imageResult);
    }
}
