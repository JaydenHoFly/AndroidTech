package com.jaydenho.androidtech.widget.recyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * Created by hedazhao on 2017/5/9.
 */

public abstract class BaseViewHolder<DATA> extends RecyclerView.ViewHolder {
    public BaseViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        super(LayoutInflater.from(context).inflate(layoutId, null));
    }

    public abstract void onBind(DATA data);
}
