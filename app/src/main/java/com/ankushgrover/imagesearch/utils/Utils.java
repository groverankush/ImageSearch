package com.ankushgrover.imagesearch.utils;

import android.net.Uri;
import android.support.annotation.NonNull;

import java.util.Locale;

public class Utils {


    /**
     * Method to format search term so that it can be queried from database.
     *
     * @param searchTerm
     * @return
     */
    public static String formatSearchTermForDb(@NonNull String searchTerm) {
        return String.format(Locale.ENGLISH, "%%%s%%", searchTerm.toLowerCase());
    }

    /**
     * Method to format search term so that it can be queried from network.
     *
     * @param searchTerm
     * @return
     */
    public static String formatSearchTermForNetwork(@NonNull String searchTerm) {
        return searchTerm.toLowerCase();
    }

    /**
     * Method to create Poster path.
     *
     * @param part
     * @return
     */
    public static String makePosterPath(String part) {

        //http://image.tmdb.org/t/p/w185/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w185")
                .appendEncodedPath(part);
        return builder.build().toString();


    }

    /**
     * Method to create Banner path.
     *
     * @param part
     * @return
     */
    public static String makeBannerPath(String part) {

        //http://image.tmdb.org/t/p/w185/gBmrsugfWpiXRh13Vo3j0WW55qD.jpg


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http")
                .authority("image.tmdb.org")
                .appendPath("t")
                .appendPath("p")
                .appendPath("w500")
                .appendEncodedPath(part);
        return builder.build().toString();

    }

    public static String makeTrailerThumbPath(String key) {
        //https://www.youtube.com/watch?v=vn9mMeWcgoM
        //https://img.youtube.com/vi/vn9mMeWcgoM/0.jpg
        return String.format(Locale.ENGLISH, "https://img.youtube.com/vi/%s/0.jpg", key);
    }

    public static String makeTrailerPath(String key) {
        return String.format(Locale.ENGLISH, "https://www.youtube.com/watch?v=%s", key);
    }


}
