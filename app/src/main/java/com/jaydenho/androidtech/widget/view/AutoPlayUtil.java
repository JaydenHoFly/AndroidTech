package com.jaydenho.androidtech.widget.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;


/**
 * Author:LiuJianSheng
 * Date:2016/7/14
 */
public class AutoPlayUtil {
    public static int getVisibilityPercents(Rect rect, View currentView) {
        if (!currentView.getLocalVisibleRect(rect)) {
            return 0;
        }
        int percents = 100;
        int height = currentView.getHeight();
        if (viewIsPartiallyHiddenTop(rect)) {
            percents = (height - rect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(rect, height)) {
            percents = (int) rect.bottom * 100 / height;
//            percents = rect.bottom * 100 / height;
        }
        return percents;
    }

    private static boolean viewIsPartiallyHiddenBottom(Rect rect, int height) {
        return rect.bottom > 0 && rect.bottom < height;
    }

    private static boolean viewIsPartiallyHiddenTop(Rect rect) {
        return rect.top > 0;
    }
}
