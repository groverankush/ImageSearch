package com.ankushgrover.imagesearch.ui.listing;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
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

import java.util.List;
import java.util.Map;

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

        View view = inflater.inflate(R.layout.layout_recycler, container, false);

        initView(view);
        initAdapter();
        setHasOptionsMenu(true);
        presenter = new ListingPresenter(DataManager.getInstance(), model, this);

        prepareTransitions();
        postponeEnterTransition();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        scrollToPosition();
    }

    private void initAdapter() {
        adapter = new ListingAdapter(this, model, this);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        adapter.setRecyclerView(recycler);
        recycler.setLayoutManager(layoutManager);
        //adapter.setOnItemCLickListener(position -> model.setSelectedItemPosition(position));
    }

    private void initView(View view) {
        errorTV = view.findViewById(R.id.tv_error);
        errorTV.setVisibility(model.getPhotos().size() > 0 ? View.GONE : View.VISIBLE);
        recycler = view.findViewById(R.id.recycler);
        swipe = view.findViewById(R.id.swipe);
        model.getIsLoading().observe(this, aBoolean -> swipe.setRefreshing(aBoolean));

        swipe.setEnabled(false);
    }

    public void setViewModel(ListingViewModel model) {

        this.model = model;
    }

    /**
     * Prepares the shared element transition to the pager fragment, as well as the other transitions
     * that affect the flow.
     */
    private void prepareTransitions() {
        setExitTransition(TransitionInflater.from(getContext())
                .inflateTransition(R.transition.grid_exit_transition));

        // A similar mapping is set at the ImagePagerFragment with a setEnterSharedElementCallback.


        setExitSharedElementCallback(
                new SharedElementCallback() {
                    @Override
                    public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
                        // Locate the ViewHolder for the clicked position.
                        RecyclerView.ViewHolder selectedViewHolder = recycler.findViewHolderForAdapterPosition(model.getSelectedItemPosition());
                        if (selectedViewHolder == null || selectedViewHolder.itemView == null)
                            return;


                        // Map the first shared element name to the child ImageView.
                        sharedElements.put(names.get(0), selectedViewHolder.itemView.findViewById(R.id.iv_thumb));
                    }
                });
    }

    /**
     * Scrolls the recycler view to show the last viewed item in the grid. This is important when
     * navigating back from the grid.
     */
    private void scrollToPosition() {
        recycler.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v,
                                       int left,
                                       int top,
                                       int right,
                                       int bottom,
                                       int oldLeft,
                                       int oldTop,
                                       int oldRight,
                                       int oldBottom) {
                recycler.removeOnLayoutChangeListener(this);
                final RecyclerView.LayoutManager layoutManager = recycler.getLayoutManager();
                View viewAtPosition = layoutManager.findViewByPosition(model.getSelectedItemPosition());
                // Scroll to position if the view for the current position is null (not currently part of
                // layout manager children), or it's not completely visible.
                if (viewAtPosition == null || layoutManager
                        .isViewPartiallyVisible(viewAtPosition, false, true)) {
                    recycler.post(() -> layoutManager.scrollToPosition(model.getSelectedItemPosition()));
                }
            }
        });
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
