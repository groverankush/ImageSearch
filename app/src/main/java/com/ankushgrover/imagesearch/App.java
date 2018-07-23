package com.ankushgrover.imagesearch;

import android.app.Application;

import com.ankushgrover.imagesearch.data.source.local.AppDatabase;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 23/7/18.
 */
public class App extends Application {

    private static App instance;
    //TODO: Initialize database.
    private AppDatabase database;

    public static App getApplication() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
