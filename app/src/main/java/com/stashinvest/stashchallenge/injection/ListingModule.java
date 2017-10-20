package com.stashinvest.stashchallenge.injection;

import com.stashinvest.stashchallenge.api.GettyImageService;
import com.stashinvest.stashchallenge.listing.GetImagesUseCase;
import com.stashinvest.stashchallenge.listing.popup.GetImageMetadataUseCase;
import com.stashinvest.stashchallenge.listing.popup.GetSimilarImagesUseCase;
import com.stashinvest.stashchallenge.listing.popup.PopUpDialogPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ListingModule {

    @Provides
    @Singleton
    GetImagesUseCase provideGetImagesUseCase(GettyImageService gettyImageService) {
        return new GetImagesUseCase(gettyImageService);
    }

    @Provides
    @Singleton
    PopUpDialogPresenter providesPopUpDialogPresenter(GetImageMetadataUseCase getImageMetadataUseCase, GetSimilarImagesUseCase getSimilarImagesUseCase) {
        return new PopUpDialogPresenter(getImageMetadataUseCase, getSimilarImagesUseCase);
    }
}
