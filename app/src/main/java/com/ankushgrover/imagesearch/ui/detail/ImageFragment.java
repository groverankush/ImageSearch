package com.ankushgrover.imagesearch.ui.detail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.app.GlideApp;
import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.utils.Utils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {


    private Photo photo;

    public ImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        GlideApp
                .with(this)
                .load(Utils.makeImageUrl(photo))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into((ImageView) view.findViewById(R.id.image));

    }

    public void setPhoto(Photo photo) {

        this.photo = photo;
    }
}
