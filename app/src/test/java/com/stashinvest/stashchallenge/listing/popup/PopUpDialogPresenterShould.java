package com.stashinvest.stashchallenge.listing.popup;

import com.stashinvest.stashchallenge.listing.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.disposables.CompositeDisposable;

import static com.stashinvest.stashchallenge.listing.TestsHelper.ANY_IMAGE_ID;
import static com.stashinvest.stashchallenge.listing.TestsHelper.ANY_IMAGE_URL;
import static com.stashinvest.stashchallenge.listing.TestsHelper.ANY_IMAGE_ARTIST;
import static com.stashinvest.stashchallenge.listing.TestsHelper.ANY_IMAGE_TITLE;
import static com.stashinvest.stashchallenge.listing.TestsHelper.ANY_SIMILAR_IMAGE_INDEX;
import static com.stashinvest.stashchallenge.listing.TestsHelper.ANY_SIMILAR_IMAGE_THUMB_URI;
import static com.stashinvest.stashchallenge.listing.TestsHelper.anyEmptyMetadataResponseSingle;
import static com.stashinvest.stashchallenge.listing.TestsHelper.anyImageResponseSingle;
import static com.stashinvest.stashchallenge.listing.TestsHelper.anyMetadataResponseSingle;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PopUpDialogPresenterShould {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Rule
    public RxSchedulersOverrideRule rxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Mock
    PopUpDialogPresenter.View view;

    @Mock
    GetImageMetadataUseCase getImageMetadataUseCase;

    @Mock
    GetSimilarImagesUseCase getSimilarImagesUseCase;

    private PopUpDialogPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new PopUpDialogPresenter(getImageMetadataUseCase, getSimilarImagesUseCase);
        presenter.setView(view);
    }

    @Test
    public void hideKeyboardAndDisplayGettyImage_onStart() throws Exception {
        presenter.displayImage(ANY_IMAGE_URL);

        verify(view).hideKeyboard();
        verify(view).displayImage(ANY_IMAGE_URL);
    }

    @Test
    public void loadMetadata_whenMetadataIsValid() throws Exception {
        when(getImageMetadataUseCase.getMetadata(ANY_IMAGE_ID)).thenReturn(anyMetadataResponseSingle());

        presenter.setCompositeDisposable(new CompositeDisposable());
        presenter.loadImageMetadata(ANY_IMAGE_ID);

        verify(view).displayImageTitle(ANY_IMAGE_TITLE);
        verify(view).displayImageArtist(ANY_IMAGE_ARTIST);
    }

    @Test
    public void callOnError_whenMetadataResponseHasEmptyMetadata() throws Exception {
        when(getImageMetadataUseCase.getMetadata(ANY_IMAGE_ID)).thenReturn(anyEmptyMetadataResponseSingle());

        presenter.setCompositeDisposable(new CompositeDisposable());
        presenter.loadImageMetadata(ANY_IMAGE_ID);

        verify(view).onError();
    }

    @Test
    public void displayOneSimilarImage_whenOneSimilarImageIsLoaded() throws Exception {
        when(getSimilarImagesUseCase.getImages(ANY_IMAGE_ID)).thenReturn(anyImageResponseSingle());

        presenter.setCompositeDisposable(new CompositeDisposable());
        presenter.loadSimilarImages(ANY_IMAGE_ID);

        verify(view).displaySimilarImage(ANY_SIMILAR_IMAGE_INDEX, ANY_SIMILAR_IMAGE_THUMB_URI);
    }
}