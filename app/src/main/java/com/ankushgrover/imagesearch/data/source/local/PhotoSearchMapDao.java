package com.ankushgrover.imagesearch.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.support.annotation.StringRes;

import com.ankushgrover.imagesearch.data.model.photosearchmapping.PhotoSearchMap;

import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 24/7/18.
 */
@Dao
public interface PhotoSearchMapDao {

    @Insert(onConflict = IGNORE)
    long insertSearchTerm(PhotoSearchMap map);

    @Query("Select * from PhotoSearchMap where searchTerm = :searchTerm")
    Single<PhotoSearchMap> getSearchTerm(String searchTerm);

    @Query("Select id from PhotoSearchMap where searchTerm = :searchTerm")
    int getSearchTermId(String searchTerm);

}
