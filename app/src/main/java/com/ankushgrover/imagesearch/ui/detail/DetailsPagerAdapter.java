package com.ankushgrover.imagesearch.ui.detail;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ankushgrover.imagesearch.data.model.photo.Photo;

import java.util.List;

/**
 * Created by Ankush Grover(ankushgrover02@gmail.com) on 25/7/18.
 */
public class DetailsPagerAdapter extends FragmentStatePagerAdapter {
    private List<Photo> photos;

    public DetailsPagerAdapter(FragmentManager fm, List<Photo> photos) {
        super(fm);
        this.photos = photos;
    }

    @Override
    public Fragment getItem(int i) {
        ImageFragment fragment = new ImageFragment();
        fragment.setPhoto(photos.get(i));

        return fragment;
    }

    @Override
    public int getCount() {
        return photos.size();
    }
}
