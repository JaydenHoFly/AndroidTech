package com.jaydenho.androidtech.databinding;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;

/**
 * Created by hedazhao on 2017/12/14.
 */

public class DataBindingInfo {

    private String text = null;

    @DrawableRes
    private int imageResId = 0;
    private int viewType = 0;

    public DataBindingInfo(String text, int imageResId, int viewType) {
        this.text = text;
        this.imageResId = imageResId;
        this.viewType = viewType;
    }

    public String getText() {
        return text;
    }

    public int getImageResId() {
        return imageResId;
    }

    public int getViewType() {
        return viewType;
    }
}
