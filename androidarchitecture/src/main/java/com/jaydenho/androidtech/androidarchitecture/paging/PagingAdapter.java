package com.jaydenho.androidtech.androidarchitecture.paging;

import android.app.Application;
import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jaydenho.androidtech.androidarchitecture.R;

/**
 * Created by hedazhao on 2018/8/1.
 */
public class PagingAdapter extends PagedListAdapter<Concert, ConcertViewHolder> {
    protected PagingAdapter() {
        super(DIFF_CALLBACK);
    }

    protected PagingAdapter(@NonNull AsyncDifferConfig config) {
        super(config);
    }

    @NonNull
    @Override
    public ConcertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ConcertViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_concert_view_holder, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ConcertViewHolder holder, int position) {
        Concert concert = getItem(position);
        holder.bindTo(concert);
    }

    private static DiffUtil.ItemCallback<Concert> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Concert>() {
                // The ID property identifies when items are the same.
                @Override
                public boolean areItemsTheSame(Concert oldItem, Concert newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                // Use Object.equals() to know when an item's content changes.
                // Implement equals(), or write custom data comparison logic here.
                @Override
                public boolean areContentsTheSame(Concert oldItem, Concert newItem) {
                    return oldItem.equals(newItem);
                }
            };
}
