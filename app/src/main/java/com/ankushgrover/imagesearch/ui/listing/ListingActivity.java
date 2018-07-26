package com.ankushgrover.imagesearch.ui.listing;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.architecture.BaseActivity;
import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.data.source.DataManager;
import com.ankushgrover.imagesearch.ui.detail.DetailsActivity;

import java.util.ArrayList;

public class ListingActivity extends BaseActivity implements ListingContract.View {

    private ListingPresenter presenter;
    private ListingViewModel model;
    private ListingAdapter adapter;
    private TextView errorTV;
    private RecyclerView recycler;

    private SwipeRefreshLayout swipe;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);

        model = ViewModelProviders.of(this).get(ListingViewModel.class);
        presenter = new ListingPresenter(DataManager.getInstance(), model, this);

        initView();
        initAdapter();

    }

    private void initAdapter() {
        adapter = new ListingAdapter(this, model.getPhotos());
        layoutManager = new GridLayoutManager(this, 2);
        adapter.setRecyclerView(recycler);
        recycler.setLayoutManager(layoutManager);
        adapter.setOnItemCLickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(DetailsActivity.PHOTOS, new ArrayList<>(model.getPhotos()));
            bundle.putInt(DetailsActivity.SELECTED_PHOTO_INDEX, position);
            switchActivity(DetailsActivity.class, bundle);
        });
    }

    private void initView() {
        errorTV = findViewById(R.id.tv_error);
        recycler = findViewById(R.id.recycler);
        swipe = findViewById(R.id.swipe);
        model.getIsLoading().observe(this, aBoolean -> swipe.setRefreshing(aBoolean));

        swipe.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

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
        return super.onCreateOptionsMenu(menu);
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
        layoutManager = new GridLayoutManager(this, columnCount);
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(int errorId) {

    }
}
