package com.ankushgrover.imagesearch.data.source.remote;

import com.ankushgrover.imagesearch.data.model.photo.PhotoResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface PhotosDataSource {



    @GET("?method=flickr.photos.search&format=json&jsoncallback=1")
    Single<PhotoResult> fetchPhotos(@Query("api_key") String apiKey,
                                    @Query("text") String searchTerm,
                                    @Query("page") int page);


    //https://api.flickr.com/services
    

    //https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key={API_KEY}&text=Hello&format=json&nojsoncallback=1&page=10

    //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}.jpg

    //https://farm1.staticflickr.com/925/28677372997_dc673cd664.jpg

}
