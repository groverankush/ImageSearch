package com.ankushgrover.imagesearch.data.source.repositories;

import com.ankushgrover.imagesearch.data.source.DataContract;
import com.ankushgrover.imagesearch.data.source.local.AppDatabase;
import com.ankushgrover.imagesearch.data.source.remote.PhotosDataSource;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 23/7/18.
 */
public class PhotosRepository implements DataContract.PhotosContract {

    private PhotosDataSource remoteDataSource;
    private AppDatabase database;

    public PhotosRepository(PhotosDataSource remoteDataSource, AppDatabase database) {
        this.remoteDataSource = remoteDataSource;
        this.database = database;
    }
}
