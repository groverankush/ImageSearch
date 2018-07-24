package com.ankushgrover.imagesearch.data.source;

import com.ankushgrover.imagesearch.app.App;
import com.ankushgrover.imagesearch.app.RetroInterceptor;
import com.ankushgrover.imagesearch.data.source.local.AppDatabase;
import com.ankushgrover.imagesearch.data.source.remote.PhotosDataSource;
import com.ankushgrover.imagesearch.data.source.repositories.PhotosRepository;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/7/18.
 */
public class DataManager {

    private static DataManager instance;
    private PhotosRepository repository;
    private PhotosDataSource remoteDataSource;
    private AppDatabase database;


    private DataManager() {


        remoteDataSource = initRemoteDataSource();
        database = App.getApplication().getDatabase();


        repository = new PhotosRepository(remoteDataSource, database);
    }

    public static DataManager getInstance() {
        if (instance == null)
            instance = new DataManager();
        return instance;
    }

    private PhotosDataSource initRemoteDataSource() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new RetroInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/services/rest/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();



        return retrofit.create(PhotosDataSource.class);
    }

    public PhotosRepository getPhotosRepository() {
        return repository;
    }

}
