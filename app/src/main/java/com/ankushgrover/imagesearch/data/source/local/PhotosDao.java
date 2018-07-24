package com.ankushgrover.imagesearch.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.ankushgrover.imagesearch.data.model.photo.Photo;

import java.util.List;

import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 23/7/18.
 */
@Dao
public interface PhotosDao {


    @Insert(onConflict = IGNORE)
    void insertPhotos(List<Photo> photos);


    @Query("Select * from Photo where search_term_id = :searchTermId")
    Single<List<Photo>> fetchPhotos(int searchTermId);

}
