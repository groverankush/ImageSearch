package com.ankushgrover.imagesearch.architecture;

import android.support.annotation.StringRes;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 5/6/18.
 */
public interface BasePresenter {

    void subscribe();

    void unsubscribe();

    void errorLog(Throwable throwable, @StringRes int generalResponse, @StringRes int errorResponse);

    String getTag();

}
