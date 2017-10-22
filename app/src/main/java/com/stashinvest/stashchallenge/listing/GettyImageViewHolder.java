package com.stashinvest.stashchallenge.listing;

import android.app.Activity;
import android.content.Context;
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

public class GettyImageViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.popup_image_view)
    ImageView imageView;

    private Context context;

    public GettyImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = itemView.getContext();
    }

    public void bind(ImageResult imageResult) {
        Picasso.with(itemView.getContext())
                .load(imageResult.getThumbUri())
                .into(imageView);

        ViewCompat.setTransitionName(imageView, imageResult.getId());
        itemView.setOnLongClickListener(view -> onLongClick(imageResult.getId(), imageResult.getThumbUri()));
    }

    private boolean onLongClick(String imageId, String imageUrl) {
        Intent intent = new Intent(context, PopUpDialogActivity.class);

        Bundle extras = new Bundle();
        extras.putString(IMAGE_ID, imageId);
        extras.putString(IMAGE_URL, imageUrl);
        intent.putExtras(extras);

        Bundle animationBundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                (Activity) context,
                imageView,
                ViewCompat.getTransitionName(imageView)).toBundle();

        context.startActivity(intent, animationBundle);

        return true;
    }
}
