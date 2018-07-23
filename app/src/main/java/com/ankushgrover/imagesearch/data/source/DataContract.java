package com.ankushgrover.imagesearch.data.source;

public interface DataContract {

    interface PhotosContract {

        /**
         * Method to fetch photos from db.
         *
         * @param lastId:     Last known id of the photo.
         * @param searchTerm: The search term for which photos are required.
         */
        void fetchPhotos(int lastId, String searchTerm);

    }

}
