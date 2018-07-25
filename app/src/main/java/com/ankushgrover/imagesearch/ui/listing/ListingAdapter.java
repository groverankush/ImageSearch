package com.ankushgrover.imagesearch.ui.listing;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.app.GlideApp;
import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.ui.detail.DetailPagerFragment;
import com.ankushgrover.imagesearch.ui.detail.DetailsFragment;
import com.ankushgrover.imagesearch.utils.Utils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 24/7/18.
 */
public class ListingAdapter extends RecyclerBaseAdapter {

    private String TAG = ListingAdapter.class.getSimpleName();
    private Fragment fragment;
    private List<Photo> photos;
    private ListingContract.View listingView;

    public ListingAdapter(Fragment fragment, List<Photo> photos, ListingContract.View view) {
        super(fragment.getActivity(), -1);

        this.fragment = fragment;
        this.photos = photos;
        this.listingView = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;

            GlideApp
                    .with(fragment.getActivity())
                    .load(Utils.makeImageUrl(photos.get(position)))
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_portrait)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumb);
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

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView thumb;

        public ViewHolder(View itemView) {
            super(itemView);

            thumb = itemView.findViewById(R.id.iv_thumb);

            itemView.setOnClickListener(v -> {
                onItemCLicked(getAdapterPosition());
                fragment.getFragmentManager()
                        .beginTransaction()
                        .setReorderingAllowed(true) // Optimize for shared element transition
                        .addSharedElement(thumb, thumb.getTransitionName())
                        .replace(R.id.container, new DetailPagerFragment(), DetailsFragment.class.getSimpleName())
                        .addToBackStack(null)
                        .commit();
            });

        }
    }
}
