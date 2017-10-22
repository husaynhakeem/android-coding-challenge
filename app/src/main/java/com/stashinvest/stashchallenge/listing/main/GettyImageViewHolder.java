package com.stashinvest.stashchallenge.listing.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.listing.popup.PopUpDialogActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.stashinvest.stashchallenge.listing.popup.PopUpDialogActivity.IMAGE_ID;
import static com.stashinvest.stashchallenge.listing.popup.PopUpDialogActivity.IMAGE_URL;

class GettyImageViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.popup_image_view)
    ImageView popupImageView;

    GettyImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(ImageResult imageResult) {
        Picasso.with(itemView.getContext())
                .load(imageResult.getThumbUri())
                .into(popupImageView);

        ViewCompat.setTransitionName(popupImageView, imageResult.getId());
        itemView.setOnLongClickListener(view -> onLongClick(imageResult.getId(), imageResult.getThumbUri()));
    }

    private boolean onLongClick(String imageId, String imageUrl) {
        Intent intent = new Intent(itemView.getContext(), PopUpDialogActivity.class);
        intent.putExtras(popupDialogArgumentsBundle(imageId, imageUrl));
        itemView.getContext().startActivity(intent, popupDialogAnimationBundle());
        return true;
    }

    private Bundle popupDialogArgumentsBundle(String imageId, String imageUrl) {
        Bundle arguments = new Bundle();
        arguments.putString(IMAGE_ID, imageId);
        arguments.putString(IMAGE_URL, imageUrl);
        return arguments;
    }

    private Bundle popupDialogAnimationBundle() {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) itemView.getContext(),
                popupImageView,
                ViewCompat.getTransitionName(popupImageView)).toBundle();
    }
}
