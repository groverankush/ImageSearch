package com.ankushgrover.imagesearch.ui.listing;

import android.support.annotation.NonNull;

import com.ankushgrover.imagesearch.architecture.BasePresenter;
import com.ankushgrover.imagesearch.architecture.BaseView;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 22/7/18.
 */
public interface ListingContract {

    interface View extends BaseView {

        void imagesAdded();

        boolean fetchMoreMovies();


    }

    interface Presenter extends BasePresenter {

        /**
         * Responsible for fetching images based on user preference and current requests.
         *
         * @param force:      Forcibly fetch images. Ignore the previous requests.
         * @param searchTerm: The text to be quried
         * @return
         */
        boolean loadPhotos(boolean force, @NonNull String searchTerm);
    }

}
