package com.ankushgrover.imagesearch.ui.listing;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class RecyclerBaseAdapter extends Adapter<ViewHolder> {

    private final int FOOTER_ITEM = 1;
    private final int HEADER_ITEM = 2;

    private OnItemCLickListener onItemCLickListener;
    private HeaderAddListener headerAddListener;

    private LayoutInflater mInflater;
    private boolean addFooter = false;
    private int footerResourceId = -1;

    private boolean isCallingScrollToEnd = false;

    public abstract ViewHolder onCreateItemViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType);

    public abstract void onBindItemViewHolder(ViewHolder holder, int position);

    public abstract int getTotalItemCount();

    public abstract int getNormalItemViewType(int position);

    public abstract boolean requireNext();

    public RecyclerBaseAdapter(Context context, int footerResourceId) {
        this.footerResourceId = footerResourceId;
        mInflater = LayoutInflater.from(context);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerViewScroll());
        recyclerView.setAdapter(this);
    }

    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
        this.onItemCLickListener = onItemCLickListener;
    }

    public void setHeader(HeaderAddListener headerAddListener) {
        this.headerAddListener = headerAddListener;
    }

    public void onItemCLicked(int pos) {
        if (onItemCLickListener != null && pos != -1)
            onItemCLickListener.onItemClicked(pos);
    }

    /**
     * Method called when recycler view scrolled to end
     */
    public void onScrolledToEnd() {
        if (isCallingScrollToEnd)
            return;
        isCallingScrollToEnd = true;
        requireNext();
        //Checking whether footer already added
        /*if (addFooter)
            return;
        addFooter = requireNext();
        if (addFooter)
            notifyItemInserted(getItemCount() - 1);*/
        isCallingScrollToEnd = false;
    }

    /**
     * Method to remove footer
     */
    public void removeFooter() {

        if (addFooter) {
            addFooter = false;
            notifyItemRemoved(getItemCount());
        }
    }

    public boolean isFetchingNextPage() {
        return addFooter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_ITEM)
            return new FooterHolder(mInflater.inflate(footerResourceId, parent, false));
        if (viewType == HEADER_ITEM && headerAddListener != null)
            return headerAddListener.onCreateHeaderViewHolder(parent, mInflater);

        return onCreateItemViewHolder(mInflater, parent, viewType);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (addFooter && getItemCount() - 1 == position)
            return;
        if (headerAddListener != null && position == 0)
            headerAddListener.onBindHeaderViewHolder(holder);
        onBindItemViewHolder(holder, headerAddListener != null ? position-1 : position);
    }

    @Override
    public int getItemViewType(int position) {
        if (addFooter && position == getItemCount() - 1)
            return FOOTER_ITEM;

        if (headerAddListener != null && position == 0)
            return HEADER_ITEM;

        int viewType = getNormalItemViewType(position);

        if (viewType == 1 || viewType == 2)
            throw new IllegalArgumentException("viewType must not be 1 or 2");
        return viewType;
    }

    @Override
    public int getItemCount() {
        return getTotalItemCount() + (addFooter ? 1 : 0) + (headerAddListener != null ? 1 : 0);
    }

    private class RecyclerViewScroll extends RecyclerView.OnScrollListener {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

            LinearLayoutManager lm = (LinearLayoutManager) recyclerView.getLayoutManager();
            if (lm != null
                    && !addFooter
                    && lm.findLastVisibleItemPosition() >= getItemCount() - 2) {
                onScrolledToEnd();
            }
        }
    }

    private class FooterHolder extends ViewHolder {

        public FooterHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnItemCLickListener {
        void onItemClicked(int position);
    }

    public interface HeaderAddListener {
        ViewHolder onCreateHeaderViewHolder(ViewGroup parent, LayoutInflater inflater);

        void onBindHeaderViewHolder(ViewHolder holder);
    }
}
