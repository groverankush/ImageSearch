package com.ankushgrover.imagesearch.data.model.photosearchmapping;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 24/7/18.
 */
@Entity(indices = {@Index(value = "searchTerm", unique = true)})
public class PhotoSearchMap {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String searchTerm;

    public PhotoSearchMap(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

}
