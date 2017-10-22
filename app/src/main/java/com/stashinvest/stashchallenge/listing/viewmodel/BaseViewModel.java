package com.stashinvest.stashchallenge.listing.viewmodel;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseViewModel<T extends RecyclerView.ViewHolder> {
    private final int itemResourceId;


    BaseViewModel(int itemResourceId) {
        this.itemResourceId = itemResourceId;
    }


    public T createViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemResourceId, parent, false);
        return createItemViewHolder(view);
    }


    public abstract T createItemViewHolder(View view);


    public abstract void bindViewHolder(T holder);


    public abstract ViewModelType getViewType();
}
