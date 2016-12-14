package com.jaydenho.androidtech.widget.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.cunoraz.gifview.library.GifView;
import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/11/14.
 */
public class ViewPagerItemView extends FrameLayout {

    private Context mContext = null;
    private GifView mGifView = null;

    public ViewPagerItemView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.view_pager_item_1, this);
        mGifView = (GifView) v.findViewById(R.id.gif_view);
    }

    public GifView getGifView() {
        return this.mGifView;
    }

}
