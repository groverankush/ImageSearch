package com.ankushgrover.imagesearch.ui.listing;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.app.GlideApp;
import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.ui.detail.DetailsFragment;
import com.ankushgrover.imagesearch.utils.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 24/7/18.
 */
public class ListingAdapter extends RecyclerBaseAdapter {

    static private ListingViewModel listingModel;
    private final RequestManager requestManager;
    private final ViewHolderListenerImpl viewHolderListener;
    private String TAG = ListingAdapter.class.getSimpleName();
    private Fragment fragment;
    private List<Photo> photos;
    private ListingContract.View listingView;

    public ListingAdapter(Fragment fragment, ListingViewModel model, ListingContract.View view) {
        super(fragment.getActivity(), -1);

        this.fragment = fragment;
        this.photos = model.getPhotos();
        this.listingView = view;
        listingModel = model;

        this.requestManager = GlideApp.with(    fragment);
        this.viewHolderListener = new ViewHolderListenerImpl(fragment);
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;

            /*GlideApp
                    .with(fragment.getActivity())
                    .load(Utils.makeImageUrl(photos.get(position)))
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_portrait)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumb);
            holder.thumb.setTransitionName(photos.get(position).getId());*/


            requestManager
                    .load(Utils.makeImageUrl(photos.get(position)))
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            viewHolderListener.onLoadCompleted(holder.thumb, position);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable>
                                target, DataSource dataSource, boolean isFirstResource) {
                            viewHolderListener.onLoadCompleted(holder.thumb, position);
                            return false;
                        }
                    })
                    .into(holder.thumb);
            // Set the string value of the image resource as the unique transition name for the view.
            holder.thumb.setTransitionName(photos.get(position).getId());

        }
    }

    @Override
    public int getTotalItemCount() {
        return this.photos.size();
    }

    @Override
    public int getNormalItemViewType(int position) {
        return 0;
    }

    @Override
    public boolean requireNext() {
        return listingView.fetchMoreImages();
    }

    /**
     * A listener that is attached to all ViewHolders to handle image loading events and clicks.
     */
    private interface ViewHolderListener {

        void onLoadCompleted(ImageView view, int adapterPosition);

        void onItemClicked(View view, int adapterPosition);
    }

    /**
     * Default {@link ViewHolderListener} implementation.
     */
    private static class ViewHolderListenerImpl implements ViewHolderListener {

        private Fragment fragment;
        private AtomicBoolean enterTransitionStarted;

        ViewHolderListenerImpl(Fragment fragment) {
            this.fragment = fragment;
            this.enterTransitionStarted = new AtomicBoolean();
        }

        @Override
        public void onLoadCompleted(ImageView view, int position) {
            // Call startPostponedEnterTransition only when the 'selected' image loading is completed.
            if (listingModel.getSelectedItemPosition() != position) {
                return;
            }
            if (enterTransitionStarted.getAndSet(true)) {
                return;
            }
            fragment.startPostponedEnterTransition();
        }

        @Override
        public void onItemClicked(View view, int position) {
            // Update the position.
            listingModel.setSelectedItemPosition(position);

            // Exclude the clicked card from the exit transition (e.g. the card will disappear immediately
            // instead of fading out with the rest to prevent an overlapping animation of fade and move).
            ((TransitionSet) fragment.getExitTransition()).excludeTarget(view, true);

            ImageView transitioningView = view.findViewById(R.id.iv_thumb);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setViewModel(listingModel);
            fragment.getFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true) // Optimize for shared element transition
                    .addSharedElement(transitioningView, transitioningView.getTransitionName())
                    .replace(R.id.container, detailsFragment, DetailsFragment.class
                            .getSimpleName())
                    .addToBackStack(null)
                    .commit();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumb;

        public ViewHolder(View itemView) {
            super(itemView);

            thumb = itemView.findViewById(R.id.iv_thumb);

            itemView.setOnClickListener(v -> {
                //ItemCLicked(getAdapterPosition());
                viewHolderListener.onItemClicked(v, getAdapterPosition());
            });

        }
    }
}
