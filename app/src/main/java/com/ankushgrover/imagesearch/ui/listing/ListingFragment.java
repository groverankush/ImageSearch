package com.ankushgrover.imagesearch.ui.listing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.data.source.DataManager;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 25/7/18.
 */
public class ListingFragment extends Fragment implements ListingContract.View {

    private ListingPresenter presenter;
    private ListingViewModel model;
    private ListingAdapter adapter;
    private GridLayoutManager layoutManager;
    private TextView errorTV;
    private SwipeRefreshLayout swipe;
    private RecyclerView recycler;

    public ListingFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_recycler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new ListingPresenter(DataManager.getInstance(), model, this);

        initView(view);
        initAdapter();
        setHasOptionsMenu(true);
    }

    private void initAdapter() {
        adapter = new ListingAdapter(this, model.getPhotos(), this);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter.setRecyclerView(recycler);
        recycler.setLayoutManager(layoutManager);
        adapter.setOnItemCLickListener(position -> model.setSelectedItemPosition(position));
    }

    private void initView(View view) {
        errorTV = view.findViewById(R.id.tv_error);
        recycler = view.findViewById(R.id.recycler);
        swipe = view.findViewById(R.id.swipe);
        model.getIsLoading().observe(this, aBoolean -> swipe.setRefreshing(aBoolean));

        swipe.setEnabled(false);
    }

    public void setViewModel(ListingViewModel model) {

        this.model = model;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnCloseListener(() -> {
            model.setSearchTerm("");
            return false;
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                model.setSearchTerm(query);
                if (!query.isEmpty()) {
                    presenter.loadPhotos(true);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_two:
                setLayoutManager(2);
                return true;

            case R.id.action_three:
                setLayoutManager(3);
                return true;

            case R.id.action_four:
                setLayoutManager(4);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Method to set appropriate layout manager
     *
     * @param columnCount
     */
    private void setLayoutManager(int columnCount) {
        int visiblePosition = layoutManager.findFirstVisibleItemPosition();
        layoutManager = new GridLayoutManager(getActivity(), columnCount);
        recycler.setLayoutManager(layoutManager);
        recycler.scrollToPosition(visiblePosition);
    }

    @Override
    public void imagesAdded() {
        swipe.setRefreshing(false);
        errorTV.setVisibility(View.GONE);
        recycler.setVisibility(View.VISIBLE);

        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean fetchMoreImages() {
        return presenter.loadPhotos(false);

    }

    @Override
    public void generalResponse(int message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int errorId) {

    }
}
