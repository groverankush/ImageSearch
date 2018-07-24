package com.ankushgrover.imagesearch.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.data.model.photosearchmapping.PhotoSearchMap;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/7/18.
 */
@Database(entities = {Photo.class, PhotoSearchMap.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PhotosDao photosDao();

    public abstract PhotoSearchMapDao photoSearchMapDao();
}
