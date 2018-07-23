package com.ankushgrover.imagesearch.ui.listing;

import android.util.Log;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.data.model.photo.Photos;
import com.ankushgrover.imagesearch.data.source.DataManager;
import com.ankushgrover.imagesearch.utils.NetworkUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
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
            isLoading(true);
            if (NetworkUtils.isConnectedToNetwork()) {
                Disposable disposable = dataManager.getPhotosRepository().fetchPhotosFromNetwork("", viewModel.getResult() == null ? 1 : viewModel.getResult().getPage() + 1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Photos>() {
                            @Override
                            public void accept(Photos photos) throws Exception {
                                isLoading(false);
                                viewModel.setResult(photos);
                                viewModel.getPhotos().addAll(photos.getPhoto());
                                checkPhotosListBeforeDisplay();
                            }
                        }, throwable -> {
                            errorLog(throwable, R.string.general_error);
                            isLoading(false);
                        });
                mDisposables.add(disposable);
                return true;
            } else {
                if (viewModel.getPhotos().size() > 0)
                    return true;
                isLoading(true);
                Disposable disposable = dataManager.getPhotosRepository().fetchPhotosFromDb("")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(networkResult -> {
                            isLoading(false);
                            viewModel.setResult(networkResult);
                            viewModel.getPhotos().addAll(networkResult.getPhoto());
                            checkPhotosListBeforeDisplay();
                        }, throwable -> {
                            errorLog(throwable, R.string.general_error);
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
        loadPhotos(false);
    }

    @Override
    public void unsubscribe() {
        isLoading(false);
        mDisposables.clear();
    }

    @Override
    public void errorLog(Throwable throwable, int generalResponse) {
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
