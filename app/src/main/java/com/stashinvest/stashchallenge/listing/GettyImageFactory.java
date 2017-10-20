package com.stashinvest.stashchallenge.listing;

import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.listing.viewmodel.GettyImageViewModel;

import javax.inject.Inject;

public class GettyImageFactory {
    @Inject
    public GettyImageFactory() {
    }

    public GettyImageViewModel createGettyImageViewModel(int id, ImageResult imageResult, GettyImageViewModel.Listener listener) {
        return new GettyImageViewModel(id, imageResult, listener);
    }
}
