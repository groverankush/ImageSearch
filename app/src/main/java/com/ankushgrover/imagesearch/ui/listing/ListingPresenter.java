package com.ankushgrover.imagesearch.ui.listing;

import com.ankushgrover.imagesearch.data.source.DataManager;
import com.ankushgrover.imagesearch.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 22/7/18.
 */
public class ListingPresenter implements ListingContract.Presenter {

    private final DataManager dataManager;
    private final ListingViewModel viewModel;
    private final ListingContract.View view;
    private final CompositeDisposable mDisposables;

    ListingPresenter(DataManager dataManager, ListingViewModel viewModel, ListingContract.View view) {
        this.dataManager = dataManager;
        this.viewModel = viewModel;
        this.view = view;
        this.mDisposables = new CompositeDisposable();
    }

    @Override
    public boolean loadPhotos(boolean force) {
        if (force) {
            viewModel.getPhotos().clear();
            viewModel.setResult(null);
            unsubscribe();
            isLoading(false);
        }

        /*if (!viewModel.getIsLoading().getValue()) {
            if (Preferences.isFavouritesSelected()) {
                fetchFavourites();
                return true;
            } else if (NetworkUtils.isConnectedToNetwork()) {
                fetchPhotos();
                return true;
            } else {
                if (!Preferences.isFavouritesSelected()) {
                    view.generalResponse(R.string.network_error);
                    view.onError(R.string.network_error);
                }

            }
        }*/
        return false;
    }

    private void fetchPhotos() {

        /*if (viewModel.getPhotos().size() > 0)
            return;
        isLoading(true);
        Disposable disposable = dataManager.getMoviesRepository().fetchFavouriteMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkResult -> {
                    isLoading(false);
                    viewModel.setResult(networkResult);
                    viewModel.getMovies().addAll(networkResult.getResults());
                    checkMoviesListBeforeDisplay();
                }, throwable -> {
                    errorLog(throwable, R.string.msg_something_went_wrong, R.string.genereal_error);
                    isLoading(false);
                });
        mDisposables.add(disposable);*/

    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void errorLog(Throwable throwable, int generalResponse) {

    }

    @Override
    public String getTag() {
        return null;
    }

    private void isLoading(boolean isLoading) {
        viewModel.getIsLoading().setValue(isLoading);
    }
}
