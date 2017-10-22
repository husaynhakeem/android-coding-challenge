package com.stashinvest.stashchallenge.listing;

import com.stashinvest.stashchallenge.api.model.ImageResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Single;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by husaynhakeem on 10/22/17.
 */
public class MainPresenterShould {

    private static final String ANY_KEYWORD = "any_keyword";

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    GetImagesUseCase getImagesUseCase;

    @Mock
    GettyImageFactory gettyImageFactory;

    @Mock
    ImageResponse imageResponse;

    @Mock
    MainPresenter.View view;

    private MainPresenter mainPresenter;

    @Before
    public void setUp() throws Exception {
        mainPresenter = new MainPresenter(getImagesUseCase, gettyImageFactory);
        mainPresenter.setView(view);
    }

    @Test
    public void hideKeyboardAndShowLoadingIndicator_whenSearchTriggered() throws Exception {
        when(getImagesUseCase.getImages(anyString())).thenReturn(Single.just(anyImageResponse()));

        mainPresenter.search(ANY_KEYWORD);

        verify(view).hideKeyboard();
        verify(view).showLoadingIndicator(true);
    }

    private ImageResponse anyImageResponse() {
        return new ImageResponse();
    }
}

