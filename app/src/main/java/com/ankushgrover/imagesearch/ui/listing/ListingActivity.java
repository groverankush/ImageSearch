package com.ankushgrover.imagesearch.ui.listing;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.architecture.BaseActivity;
import com.ankushgrover.imagesearch.data.source.DataManager;

public class ListingActivity extends BaseActivity implements ListingContract.View {

    private ListingPresenter presenter;
    private ListingViewModel model;
    private ListingAdapter adapter;
    private TextView errorTV;
    private RecyclerView recycler;

    private SwipeRefreshLayout swipe;

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
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        adapter.setRecyclerView(recycler);
        recycler.setLayoutManager(manager);
        /*adapter.setOnItemCLickListener(position -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(DetailsActivity.MOVIE_DETAIL, model.getMovies().get(position));
            switchActivity(DetailsActivity.class, bundle);
        });*/
    }

    private void initView() {
        errorTV = findViewById(R.id.tv_error);
        recycler = findViewById(R.id.recycler);
        swipe = findViewById(R.id.swipe);
        model.getIsLoading().observe(this, aBoolean -> swipe.setRefreshing(aBoolean));

        swipe.setOnRefreshListener(() -> {
            //TODO Search term check
            //presenter.loadPhotos(true)
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                presenter.loadPhotos(true, query);
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
                return true;

            case R.id.action_three:
                return true;

            case R.id.action_four:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void imagesAdded() {
        Log.d("as", model.getPhotos().size() + "");
    }

    @Override
    public boolean fetchMoreMovies() {
        return false;
    }

    @Override
    public void generalResponse(int message) {

    }

    @Override
    public void onError(int errorId) {

    }
}
