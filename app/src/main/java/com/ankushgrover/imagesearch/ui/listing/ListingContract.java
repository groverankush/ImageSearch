package com.ankushgrover.imagesearch.ui.listing;

import com.ankushgrover.imagesearch.architecture.BasePresenter;
import com.ankushgrover.imagesearch.architecture.BaseView;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 22/7/18.
 */
public interface ListingContract {

    interface View extends BaseView {

        void imagesAdded();

        boolean fetchMoreImages();


    }

    interface Presenter extends BasePresenter {

        /**
         * Responsible for fetching images based on user preference and current requests.
         *
         * @param force: Forcibly fetch images. Ignore the previous requests.
         * @return
         */
        boolean loadPhotos(boolean force);
    }

}
