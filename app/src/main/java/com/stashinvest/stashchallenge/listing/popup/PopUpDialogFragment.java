package com.stashinvest.stashchallenge.listing.popup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class PopUpDialogFragment extends DialogFragment implements PopUpDialogPresenter.View {

    private static final String IMAGE_ID = "image_id";
    private static final String IMAGE_URL = "image_url";

    private static final int FIRST_SIMILAR_IMAGE_INDEX = 0;
    private static final int SECOND_SIMILAR_IMAGE_INDEX = 1;
    private static final int THIRD_SIMILAR_IMAGE_INDEX = 2;

    private Snackbar snackbar;
    private Unbinder unbinder;

    @Inject
    PopUpDialogPresenter presenter;

    @BindView(R.id.popup_dialog_root)
    View rootView;

    @BindView(R.id.popup_image_view)
    ImageView popupImageView;

    @BindView(R.id.title_view)
    TextView titleTextView;

    @BindView(R.id.similar_image_view1)
    ImageView firstSimilarImageView;

    @BindView(R.id.similar_image_view2)
    ImageView secondSimilarImageView;

    @BindView(R.id.similar_image_view3)
    ImageView thirdSimilarImageView;

    public static PopUpDialogFragment newInstance(String imageId, String imageUrl) {
        PopUpDialogFragment popUpDialogFragment = new PopUpDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(IMAGE_ID, imageId);
        arguments.putString(IMAGE_URL, imageUrl);
        popUpDialogFragment.setArguments(arguments);
        return popUpDialogFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_popup, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpPresenter();
        return view;
    }

    @SuppressWarnings("ConstantConditions")
    private void setUpPresenter() {
        presenter.setView(this);
        Bundle arguments = getArguments();
        if (arguments == null || !arguments.containsKey(IMAGE_ID) || !arguments.containsKey(IMAGE_URL))
            onError();
        presenter.start(arguments.getString(IMAGE_ID), arguments.getString(IMAGE_URL));
    }

    @Override
    public void hideKeyboard() {
        ViewUtility.hideKeyboard(rootView);
    }

    @Override
    public void displayImage(String imageUrl) {
        Picasso.with(getContext())
                .load(imageUrl)
                .into(popupImageView);
    }

    @Override
    public void displayImageMetadata(String imageTitle, String imageArtist) {
        titleTextView.setText(getString(R.string.metadata_title_format, imageTitle, imageArtist));
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

        Picasso.with(getContext())
                .load(imageUrl)
                .into(similarImageView);
    }

    @Override
    public void onError() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();

        snackbar = Snackbar.make(rootView, getString(R.string.images_response_error), Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.retry, view -> setUpPresenter());
        snackbar.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }
}
