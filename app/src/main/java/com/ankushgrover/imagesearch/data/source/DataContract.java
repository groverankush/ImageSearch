package com.ankushgrover.imagesearch.data.source;

import android.support.annotation.NonNull;

import com.ankushgrover.imagesearch.data.model.photo.Photos;

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

    }

}
