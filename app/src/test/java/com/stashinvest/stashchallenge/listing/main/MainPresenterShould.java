package com.stashinvest.stashchallenge.listing.main;

import com.stashinvest.stashchallenge.api.model.ImageResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.disposables.CompositeDisposable;

import static android.test.MoreAsserts.assertNotEqual;
import static com.stashinvest.stashchallenge.listing.TestsHelper.ANY_KEYWORD;
import static com.stashinvest.stashchallenge.listing.TestsHelper.anyImageResponseSingle;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterShould {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    LoadGettyImagesUseCase loadGettyImagesUseCase;

    @Mock
    GettyImageFactory gettyImageFactory;

    @Mock
    ImageResponse imageResponse;

    @Mock
    MainPresenter.View view;

    private MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new MainPresenter(loadGettyImagesUseCase, gettyImageFactory);
        presenter.setView(view);
    }

    @Test
    public void hideKeyboardAndShowLoadingIndicator_whenSearchTriggered() throws Exception {
        when(loadGettyImagesUseCase.loadImages(anyString())).thenReturn(anyImageResponseSingle());

        presenter.search(ANY_KEYWORD);

        verify(view).hideKeyboard();
        verify(view).displayLoadingIndicator(true);
    }

    @Test
    public void reInitializeTheCompositeDisposableIfItIsNotNull_whenStartingNewSearch() throws Exception {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        when(loadGettyImagesUseCase.loadImages(anyString())).thenReturn(anyImageResponseSingle());

        presenter.setCompositeDisposable(compositeDisposable);
        presenter.search(ANY_KEYWORD);

        assertNotEqual(presenter.getCompositeDisposable(), compositeDisposable);
    }
}
