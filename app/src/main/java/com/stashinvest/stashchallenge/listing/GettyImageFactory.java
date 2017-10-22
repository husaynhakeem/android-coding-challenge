package com.stashinvest.stashchallenge.listing;

import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.listing.viewmodel.GettyImageViewModel;

import javax.inject.Inject;

class GettyImageFactory {

    @Inject
    GettyImageFactory() {
    }

    public GettyImageViewModel createGettyImageViewModel(ImageResult imageResult) {
        return new GettyImageViewModel(imageResult);
    }
}
