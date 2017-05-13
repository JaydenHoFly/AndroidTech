package com.jaydenho.androidtech.widget.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/5/9.
 */

public class ImageViewHolder extends BaseViewHolder<String> {
    public ImageViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        super(context, layoutId, parent);
    }

    @Override
    public void onBind(String s) {
        itemView.setBackgroundColor(Color.RED);
        TextView tv = (TextView) itemView.findViewById(R.id.tv);
        tv.setText("ImageViewHolder: " + s);
    }
}
