package com.stashinvest.stashchallenge.listing;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.stashinvest.stashchallenge.listing.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

public class ViewModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<BaseViewModel> viewModels;
    private final SparseArrayCompat<BaseViewModel> viewTypeMap;


    @Inject
    public ViewModelAdapter() {
        viewModels = new ArrayList<>();
        viewTypeMap = new SparseArrayCompat<>();
    }


    public void setViewModels(Collection<? extends BaseViewModel> viewModels) {
        this.viewModels.clear();
        viewTypeMap.clear();
        addAll(viewModels);
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewTypeMap.get(viewType).createViewHolder(parent);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewModels.get(position).bindViewHolder(holder);
    }


    @Override
    public int getItemCount() {
        return viewModels.size();
    }


    @Override
    public int getItemViewType(int position) {
        return viewModels.get(position).getViewType().getId();
    }


    private void addAll(Collection<? extends BaseViewModel> viewModels) {
        if (viewModels == null) {
            return;
        }

        for (BaseViewModel baseViewModel : viewModels) {
            this.viewModels.add(baseViewModel);

            //If there are multiple items of the same type the index will just update
            viewTypeMap.put(baseViewModel.getViewType().getId(), baseViewModel);
        }
    }
}
