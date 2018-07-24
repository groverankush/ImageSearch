package com.ankushgrover.imagesearch.data.source;

import android.support.annotation.NonNull;

import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.data.model.photo.Photos;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface DataContract {

    interface PhotosContract {

        /**
         * Method to fetch photos from db.
         *
         * @param searchTerm: The search term for which photos are required.
         */
        Single<Photos> fetchPhotosFromDb(@NonNull String searchTerm);

        /**
         * Method to search photos from API.
         *
         * @param searchTerm: The search term for which photos are required.
         * @param page:       Page number to be searched.
         * @return
         */
        Single<Photos> fetchPhotosFromNetwork(@NonNull String searchTerm, int page);

        /**
         * Method to save photos to db.
         *
         * @param photos
         * @return
         */
        Completable savePhotosListToDb(List<Photo> photos);

    }

}
