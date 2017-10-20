package com.stashinvest.stashchallenge.listing.viewmodel;

import android.view.View;

import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.api.model.ImageResult;
import com.stashinvest.stashchallenge.listing.GettyImageViewHolder;
import com.stashinvest.stashchallenge.util.LongPressGestureDetector;

import static com.stashinvest.stashchallenge.listing.viewmodel.ViewModelType.GETTY_IMAGE;

public class GettyImageViewModel extends BaseViewModel<GettyImageViewHolder> implements LongPressGestureDetector.Listener {
    private final ImageResult imageResult;
    private final Listener listener;
    private final int id;

    public GettyImageViewModel(int id, ImageResult imageResult, Listener listener) {
        super(R.layout.getty_image_layout);
        this.id = id;
        this.imageResult = imageResult;
        this.listener = listener;
    }

    @Override
    public GettyImageViewHolder createItemViewHolder(View view) {
        return new GettyImageViewHolder(view);
    }

    @Override
    public void bindViewHolder(GettyImageViewHolder holder) {
        holder.bind(imageResult, this);
    }

    @Override
    public ViewModelType getViewType() {
        return GETTY_IMAGE;
    }

    @Override
    public void onLongPress() {
        if (listener != null) {
            listener.onImageLongPress(imageResult.getId(), imageResult.getThumbUri());
        }
    }

    public interface Listener {
        void onImageLongPress(String id, String uri);
    }
}
