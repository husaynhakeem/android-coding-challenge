package com.stashinvest.stashchallenge.listing.popup;

import com.stashinvest.stashchallenge.api.model.DisplaySize;
import com.stashinvest.stashchallenge.api.model.ImageResponse;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.api.model.Metadata;
import com.stashinvest.stashchallenge.api.model.MetadataResponse;
import com.stashinvest.stashchallenge.listing.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PopUpDialogPresenterShould {

    private static final String ANY_GETTY_IMAGE_ID = "any_getty_image_id";
    private static final String ANY_GETTY_IMAGE_URL = "any_getty_image_url";
    private static final String ANY_IMAGE_TITLE = "any_image_title";
    private static final String ANY_IMAGE_ARTIST = "any_image_artist";

    private static final String ANY_SIMILAR_IMAGE_ID = "any_similar_image_id";
    private static final String ANY_SIMILAR_IMAGE_TITLE = "any_similar_image_title";
    private static final int ANY_SIMILAR_IMAGE_INDEX = 0;
    private static final String ANY_SIMILAR_IMAGE_THUMB_URI = "any_similar_image_thumb_uri";

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
        presenter.displayImage(ANY_GETTY_IMAGE_URL);

        verify(view).hideKeyboard();
        verify(view).displayImage(ANY_GETTY_IMAGE_URL);
    }

    @Test
    public void loadMetadata_whenMetadataIsValid() throws Exception {
        when(getImageMetadataUseCase.getMetadata(ANY_GETTY_IMAGE_ID)).thenReturn(anyMetadataResponseSingle());

        presenter.setCompositeDisposable(new CompositeDisposable());
        presenter.loadImageMetadata(ANY_GETTY_IMAGE_ID);

        verify(view).displayImageTitle(ANY_IMAGE_TITLE);
        verify(view).displayImageArtist(ANY_IMAGE_ARTIST);
    }

    @Test
    public void callOnError_whenMetadataResponseHasEmptyMetadata() throws Exception {
        when(getImageMetadataUseCase.getMetadata(ANY_GETTY_IMAGE_ID)).thenReturn(anyEmptyMetadataResponseSingle());

        presenter.setCompositeDisposable(new CompositeDisposable());
        presenter.loadImageMetadata(ANY_GETTY_IMAGE_ID);

        verify(view).onError();
    }

    @Test
    public void displayOneSimilarImage_whenOneSimilarImageIsLoaded() throws Exception {
        when(getSimilarImagesUseCase.getImages(ANY_GETTY_IMAGE_ID)).thenReturn(anyImageResponseSingle());

        presenter.setCompositeDisposable(new CompositeDisposable());
        presenter.loadSimilarImages(ANY_GETTY_IMAGE_ID);

        verify(view).displaySimilarImage(ANY_SIMILAR_IMAGE_INDEX, ANY_SIMILAR_IMAGE_THUMB_URI);
    }


    private Single<MetadataResponse> anyEmptyMetadataResponseSingle() {
        MetadataResponse metadataResponse = new MetadataResponse();
        metadataResponse.setMetadata(new ArrayList<>());
        return Single.just(metadataResponse);
    }

    private Single<MetadataResponse> anyMetadataResponseSingle() {
        MetadataResponse metadataResponse = new MetadataResponse();
        metadataResponse.setMetadata(Collections.singletonList(anyMetadata()));
        return Single.just(metadataResponse);
    }

    private Metadata anyMetadata() {
        Metadata metadata = new Metadata();
        metadata.setId(ANY_GETTY_IMAGE_ID);
        metadata.setTitle(ANY_IMAGE_TITLE);
        metadata.setArtist(ANY_IMAGE_ARTIST);
        return metadata;
    }

    private Single<ImageResponse> anyImageResponseSingle() {
        ImageResponse imageResponse = new ImageResponse();
        imageResponse.setResultCount(1);
        imageResponse.setImages(anyImageResult());
        return Single.just(imageResponse);
    }

    private List<ImageResult> anyImageResult() {
        ImageResult imageResult = new ImageResult(ANY_SIMILAR_IMAGE_ID, ANY_SIMILAR_IMAGE_TITLE);
        imageResult.setDisplaySizes(Collections.singletonList(anyDisplaySize()));
        return Collections.singletonList(imageResult);
    }

    private DisplaySize anyDisplaySize() {
        DisplaySize displaySize = new DisplaySize();
        displaySize.setName("thumb");
        displaySize.setUri(ANY_SIMILAR_IMAGE_THUMB_URI);
        return displaySize;
    }
}