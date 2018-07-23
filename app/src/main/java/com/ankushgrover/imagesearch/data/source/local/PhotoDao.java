package com.ankushgrover.imagesearch.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.data.model.photo.Photos;

import java.util.List;

import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 23/7/18.
 */
@Dao
public interface PhotoDao {


    @Insert(onConflict = REPLACE)
    void insertPhotos(List<Photo> photos);


    @Query("Select * from Photo where _id > :lastId and title like :searchTerm limit 100")
    Single<Photos> fetchPhotos(int lastId, String searchTerm);

}
