package com.stashinvest.stashchallenge.listing;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.listing.popup.PopUpDialogFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GettyImageViewHolder extends RecyclerView.ViewHolder {
    private static final String TAG = "dialog popup";
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

        itemView.setOnLongClickListener(view -> onLongClick(view, imageResult.getId(), imageResult.getThumbUri()));
    }

    private boolean onLongClick(View view, String imageId, String imageUrl) {
        PopUpDialogFragment fragment = PopUpDialogFragment.newInstance(imageId, imageUrl);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.setTransitionName(imageId);
            ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction()
                    .addSharedElement(view, ViewCompat.getTransitionName(view))
                    .addToBackStack(TAG)
                    .add(fragment, TAG)
                    .commit();

        } else {
            fragment.show(((AppCompatActivity) itemView.getContext()).getSupportFragmentManager(), TAG);
        }

        return true;
    }
}
