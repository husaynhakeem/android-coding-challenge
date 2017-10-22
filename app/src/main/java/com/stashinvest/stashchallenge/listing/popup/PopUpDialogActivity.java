package com.stashinvest.stashchallenge.listing.popup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.util.ViewUtility;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PopUpDialogActivity extends AppCompatActivity implements PopUpDialogPresenter.View {

    public static final String IMAGE_ID = "image_id";
    public static final String IMAGE_URL = "image_url";

    private static final int FIRST_SIMILAR_IMAGE_INDEX = 0;
    private static final int SECOND_SIMILAR_IMAGE_INDEX = 1;
    private static final int THIRD_SIMILAR_IMAGE_INDEX = 2;

    private Snackbar snackbar;
    private Unbinder unbinder;

    @Inject
    PopUpDialogPresenter presenter;

    @BindView(R.id.popup_image_view)
    ImageView popupImageView;

    @BindView(R.id.tv_title)
    TextView titleTextView;

    @BindView(R.id.tv_artist)
    TextView artistTextView;

    @BindView(R.id.similar_image_view1)
    ImageView firstSimilarImageView;

    @BindView(R.id.similar_image_view2)
    ImageView secondSimilarImageView;

    @BindView(R.id.similar_image_view3)
    ImageView thirdSimilarImageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Dialog);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_dialog);
        unbinder = ButterKnife.bind(this);
        setupDagger();
        setUpPresenter();
        setUpViews();
    }

    private void setupDagger() {
        App.getInstance().getAppComponent().inject(this);
    }

    @SuppressWarnings("ConstantConditions")
    private void setUpPresenter() {
        presenter.setView(this);
        Bundle extras = getIntent().getExtras();
        if (extras == null || !extras.containsKey(IMAGE_ID) || !extras.containsKey(IMAGE_URL))
            onError();
        presenter.start(extras.getString(IMAGE_ID), extras.getString(IMAGE_URL));
    }

    private void setUpViews() {
        ViewCompat.setTransitionName(popupImageView, getIntent().getStringExtra(IMAGE_ID));
    }

    @Override
    public void hideKeyboard() {
        ViewUtility.hideKeyboard(findViewById(android.R.id.content));
    }

    @Override
    public void displayImage(String imageUrl) {
        Picasso.with(this)
                .load(imageUrl)
                .into(popupImageView);
    }

    @Override
    public void displayImageTitle(String imageTitle) {
        titleTextView.setText(imageTitle);
    }

    @Override
    public void displayImageArtist(String imageArtist) {
        artistTextView.setText(getString(R.string.artist_format, imageArtist));
    }

    @Override
    public void displaySimilarImage(int index, String imageUrl) {
        ImageView similarImageView = null;
        switch (index) {
            case FIRST_SIMILAR_IMAGE_INDEX:
                similarImageView = firstSimilarImageView;
                break;
            case SECOND_SIMILAR_IMAGE_INDEX:
                similarImageView = secondSimilarImageView;
                break;
            case THIRD_SIMILAR_IMAGE_INDEX:
                similarImageView = thirdSimilarImageView;
                break;
        }

        if (similarImageView == null) {
            onError();
            return;
        }

        Picasso.with(this)
                .load(imageUrl)
                .into(similarImageView);
    }

    @Override
    public void onError() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();

        snackbar = Snackbar.make(findViewById(android.R.id.content), getString(R.string.images_response_error), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.retry, view -> setUpPresenter());
        snackbar.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
        presenter.reset();
        presenter = null;
    }
}
