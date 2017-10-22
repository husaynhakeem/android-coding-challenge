package com.stashinvest.stashchallenge.listing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.stashinvest.stashchallenge.App;
import com.stashinvest.stashchallenge.R;
import com.stashinvest.stashchallenge.listing.viewmodel.BaseViewModel;
import com.stashinvest.stashchallenge.util.SpaceItemDecoration;
import com.stashinvest.stashchallenge.util.ViewUtility;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindDimen;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainFragment extends Fragment implements MainPresenter.View {
    public static final String EMPTY = "";
    public static final int SPAN_COUNT = 3;

    @BindDimen(R.dimen.image_space)
    int space;

    @Inject
    ViewModelAdapter adapter;
    @Inject
    MainPresenter presenter;

    @BindView(R.id.main_fragment_root)
    View rootView;
    @BindView(R.id.search_phrase)
    EditText searchView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    Snackbar snackbar;
    Unbinder unbinder;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.getInstance().getAppComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        setUpPresenter();
        setUpViews();
        return view;
    }

    private void setUpPresenter() {
        presenter.setView(this);
    }

    private void setUpViews() {
        searchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.search(getSearchKeyword());
                return true;
            }
            return false;
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
        recyclerView.addItemDecoration(new SpaceItemDecoration(space, space, space, space));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoadingIndicator(boolean visible) {
        progressBar.setVisibility(visible ? VISIBLE : GONE);
    }

    @Override
    public void onResponse(List<BaseViewModel> images) {
        adapter.setViewModels(images);
    }

    @Override
    public void onFailure() {
        if (snackbar != null && snackbar.isShown())
            snackbar.dismiss();
        snackbar = Snackbar.make(rootView, getContext().getString(R.string.images_response_error), Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(getContext().getString(R.string.retry), view -> presenter.search(getSearchKeyword()));
        snackbar.show();
    }

//    @Override
//    public void onImageLongPress(String imageId, String imageUrl) {
//        PopUpDialogFragment fragment = PopUpDialogFragment.newInstance(imageId, imageUrl);
//        fragment.show(getFragmentManager(), "Image " + imageId);
//    }

    @NonNull
    private String getSearchKeyword() {
        return searchView == null ? EMPTY : searchView.getText().toString();
    }

    @Override
    public void hideKeyboard() {
        ViewUtility.hideKeyboard(rootView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
