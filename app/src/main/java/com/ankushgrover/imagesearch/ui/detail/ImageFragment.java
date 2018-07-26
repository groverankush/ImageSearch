package com.ankushgrover.imagesearch.ui.detail;


import android.graphics.drawable.Drawable;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImageFragment extends Fragment {

    public static final String KEY_PHOTO_URL = "keyPhotoUrl";
    public static final String KEY_PHOTO_ID = "keyPhotoId";
    private Photo photo;

    public ImageFragment() {
        // Required empty public constructor
    }

    public void setPhoto(Photo photo) {

        this.photo = photo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        view.findViewById(R.id.image).setTransitionName(photo.getId());
        // Load the image with Glide to prevent OOM error when the image drawables are very large.
        Glide.with(this)
                .load(Utils.makeImageUrl(photo))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable>
                            target, boolean isFirstResource) {
                        // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                        // startPostponedEnterTransition() should also be called on it to get the transition
                        // going in case of a failure.
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                            target, DataSource dataSource, boolean isFirstResource) {
                        // The postponeEnterTransition is called on the parent ImagePagerFragment, so the
                        // startPostponedEnterTransition() should also be called on it to get the transition
                        // going when the image is ready.
                        getParentFragment().startPostponedEnterTransition();
                        return false;
                    }
                })
                .into((ImageView) view.findViewById(R.id.image));
        return view;
    }

}
