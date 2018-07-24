package com.ankushgrover.imagesearch.ui.listing;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;
import android.widget.Toast;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.app.App;
import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.data.source.DataManager;
import com.ankushgrover.imagesearch.utils.NetworkUtils;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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

        if (!viewModel.getIsLoading().getValue()) {
            if (NetworkUtils.isConnectedToNetwork()) {
                isLoading(true);

                Disposable disposable = dataManager.getPhotosRepository().fetchPhotosFromNetwork(viewModel.getSearchTerm(), viewModel.getResult() == null ? 1 : viewModel.getResult().getPage() + 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(photos -> {

                            isLoading(false);
                            viewModel.setResult(photos);
                            viewModel.getPhotos().addAll(photos.getPhoto());
                            checkPhotosListBeforeDisplay();

                        }, throwable -> {
                            errorLog(throwable, R.string.general_error, -1);
                            isLoading(false);
                        });
                mDisposables.add(disposable);
                return true;
            } else {
                if (viewModel.getPhotos().size() > 0)
                    return true;
                isLoading(true);
                Disposable disposable = dataManager.getPhotosRepository().fetchPhotosFromDb(viewModel.getSearchTerm())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(networkResult -> {
                            isLoading(false);
                            viewModel.setResult(networkResult);
                            viewModel.getPhotos().addAll(networkResult.getPhoto());
                            checkPhotosListBeforeDisplay();
                        }, throwable -> {
                            errorLog(throwable, R.string.general_error, -1);
                            isLoading(false);
                        });
                mDisposables.add(disposable);
                return true;
            }
        }
        return false;
    }


    private void checkPhotosListBeforeDisplay() {
        if (viewModel.getPhotos().size() == 0)
            view.onError(R.string.no_images);
        else view.imagesAdded();

    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        isLoading(false);
        mDisposables.clear();
    }

    @Override
    public void errorLog(Throwable throwable, int generalResponse, @StringRes int errorResponse) {
        Log.e(getTag(), throwable.getMessage());
        view.generalResponse(generalResponse);
        //view.onError(errorResponse);
    }

    @Override
    public String getTag() {
        return ListingPresenter.class.getSimpleName();
    }

    private void isLoading(boolean isLoading) {
        viewModel.getIsLoading().setValue(isLoading);
    }
}
