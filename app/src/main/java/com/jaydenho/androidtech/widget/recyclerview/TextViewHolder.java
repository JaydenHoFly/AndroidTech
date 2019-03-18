package com.jaydenho.androidtech.widget.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.widget.view.AdInfo;
import com.jaydenho.androidtech.widget.view.ViewVisibleHelper;

/**
 * Created by hedazhao on 2017/5/9.
 */

public class TextViewHolder extends BaseViewHolder<String> {
    public TextViewHolder(Context context, @LayoutRes int layoutId, ViewGroup parent) {
        super(context, layoutId, parent);
    }

    @Override
    public void onBind(String s) {
        itemView.setBackgroundColor(Color.RED);
        TextView tv = (TextView) itemView.findViewById(R.id.tv);
        tv.setText("TextViewHolder: " + s);
        AdInfo.obtainAdInfo(s).trackPMPAdShow(itemView);
    }
}
