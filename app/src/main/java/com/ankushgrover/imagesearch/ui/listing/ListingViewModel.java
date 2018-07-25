package com.ankushgrover.imagesearch.ui.listing;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.data.model.photo.Photos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 22/7/18.
 */
public class ListingViewModel extends ViewModel {
    private Photos result;
    private List<Photo> photos;
    private MutableLiveData<Boolean> isLoading;
    private String searchTerm;
    private int selectedItemPosition;

    public List<Photo> getPhotos() {
        if (photos == null)
            photos = new ArrayList<Photo>();
        return photos;
    }


    public Photos getResult() {
        return result;
    }

    public void setResult(Photos result) {
        this.result = result;
    }

    public MutableLiveData<Boolean> getIsLoading() {
        if (isLoading == null) {
            isLoading = new MutableLiveData<>();
            isLoading.setValue(false);
        }
        return isLoading;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }

    public int getSelectedItemPosition() {
        return selectedItemPosition;
    }

    public void setSelectedItemPosition(int selectedItemPosition) {
        this.selectedItemPosition = selectedItemPosition;
    }
}
