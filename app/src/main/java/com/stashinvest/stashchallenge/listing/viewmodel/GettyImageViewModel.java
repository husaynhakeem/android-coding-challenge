package com.stashinvest.stashchallenge.listing.viewmodel;

import android.view.View;

import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.listing.GettyImageViewHolder;

import static com.stashinvest.stashchallenge.listing.viewmodel.ViewModelType.GETTY_IMAGE;

public class GettyImageViewModel extends BaseViewModel<GettyImageViewHolder> {
    private final ImageResult imageResult;

    public GettyImageViewModel(ImageResult imageResult) {
        super(R.layout.getty_image_layout);
        this.imageResult = imageResult;
    }

    @Override
    public GettyImageViewHolder createItemViewHolder(View view) {
        return new GettyImageViewHolder(view);
    }

    @Override
    public void bindViewHolder(GettyImageViewHolder holder) {
        holder.bind(imageResult);
    }

    @Override
    public ViewModelType getViewType() {
        return GETTY_IMAGE;
    }
}
