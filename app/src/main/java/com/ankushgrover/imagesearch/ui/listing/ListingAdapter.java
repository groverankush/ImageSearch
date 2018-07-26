package com.ankushgrover.imagesearch.ui.listing;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ankushgrover.imagesearch.R;
import com.ankushgrover.imagesearch.app.GlideApp;
import com.ankushgrover.imagesearch.data.model.photo.Photo;
import com.ankushgrover.imagesearch.utils.Utils;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Ankush Grover(ankush.grover@finoit.co.in) on 24/7/18.
 */
public class ListingAdapter extends RecyclerBaseAdapter {

    private String TAG = ListingAdapter.class.getSimpleName();
    private Context context;
    private List<Photo> photos;
    private ListingContract.View listingView;

    public ListingAdapter(Context context, List<Photo> photos) {
        super(context, -1);

        this.context = context;
        this.photos = photos;
        this.listingView = (ListingContract.View) context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;

            GlideApp
                    .with(context)
                    .load(Utils.makeImageUrl(photos.get(position)))
                    .centerCrop()
                    .placeholder(R.drawable.placeholder_portrait)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.thumb);
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

            itemView.setOnClickListener(v -> onItemCLicked(getAdapterPosition()));

        }
    }
}
