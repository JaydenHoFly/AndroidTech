package com.jaydenho.androidtech.widget.recyclerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;

/**
 * Created by hedazhao on 2017/5/9.
 */

public class CustomLayoutManager extends GridLayoutManager {

    public CustomLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CustomLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public CustomLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

}
