package com.ankushgrover.imagesearch.data.source.repositories;

import android.support.annotation.NonNull;

import com.ankushgrover.imagesearch.BuildConfig;
import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.data.model.photo.Photos;
import com.ankushgrover.imagesearch.data.source.DataContract;
import com.ankushgrover.imagesearch.data.source.local.AppDatabase;
import com.ankushgrover.imagesearch.data.source.remote.PhotosDataSource;
import com.ankushgrover.imagesearch.utils.Utils;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/7/18.
 */
public class PhotosRepository implements DataContract.PhotosContract {

    private PhotosDataSource remoteDataSource;
    private AppDatabase database;

    public PhotosRepository(PhotosDataSource remoteDataSource, AppDatabase database) {
        this.remoteDataSource = remoteDataSource;
        this.database = database;
    }


    @Override
    public Single<Photos> fetchPhotosFromDb(@NonNull String searchTerm) {

        return database.photosDao().fetchPhotos(Utils.formatSearchTermForDb(searchTerm))
                .map(photos -> {
                    Photos object = new Photos();
                    object.setPhoto(photos);
                    return object;
                });
    }

    @Override
    public Single<Photos> fetchPhotosFromNetwork(@NonNull String searchTerm, int page) {

        return remoteDataSource.fetchPhotos(BuildConfig.API_KEY,
                Utils.formatSearchTermForNetwork(searchTerm), page)
                .map(photoResult -> {
                    if (photoResult.getStat().equals("ok")) {
                        return photoResult.getPhotos();
                    } else throw new Exception("Unable to fetch data from servers.");
                });
    }

    @Override
    public Completable savePhotosListToDb(List<Photo> photos) {
        return Completable.fromAction(() -> database.photosDao().insertPhotos(photos));
    }


}
