package com.ankushgrover.imagesearch.app;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.ankushgrover.imagesearch.data.source.local.AppDatabase;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/7/18.
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

        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "image-search").build();
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
