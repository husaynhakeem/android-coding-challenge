package com.stashinvest.stashchallenge.listing.main;

import android.view.View;

import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.listing.viewmodel.BaseViewModel;
import com.stashinvest.stashchallenge.listing.viewmodel.ViewModelType;

class GettyImageViewModel extends BaseViewModel<GettyImageViewHolder> {
    private final ImageResult imageResult;

    GettyImageViewModel(ImageResult imageResult) {
        super(R.layout.layout_getty_image);
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
        return ViewModelType.GETTY_IMAGE;
    }
}
