package com.jaydenho.androidtech.widget.view.viewpager;

import android.content.Context;

/**
 * Created by hedazhao on 2017/2/15.
 */
public class ViewPagerItemStyle2 extends BaseViewPagerItem {
    public ViewPagerItemStyle2(Context context) {
        super(context);
        addTextView(context);
    }

    @Override
    public void refreshUI(int realPosition) {
        setBackgroundResource(BACKGROUND_CONFIG[realPosition]);
        setText("样式二 真实位置： " + realPosition);
    }

}
