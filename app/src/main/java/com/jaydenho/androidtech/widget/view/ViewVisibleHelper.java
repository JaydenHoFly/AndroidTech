package com.jaydenho.androidtech.widget.view;

import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by hedazhao on 2019/3/17.
 */
public class ViewVisibleHelper {
    private static final String TAG = "ViewVisibleHelper";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static void testViewListener(final View v, final String tag) {
        final Rect rect = new Rect();
        v.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.d(TAG, "v.onLayoutChange--tag=" + tag + "|v=" + v);
            }
        });
        v.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                Log.d(TAG, "v.onViewAttachedToWindow--tag=" + tag + "|v=" + v);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                Log.d(TAG, "v.onViewDetachedFromWindow--tag=" + tag + "|v=" + v);
            }
        });

        //getViewTreeObserver
        v.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int percent = AutoPlayUtil.getVisibilityPercents(rect, v);
                Log.d(TAG, "v.getViewTreeObserver.onScrollChanged--tag=" + tag + "|visibility percent=" + percent + "|v=" + v);
            }
        });
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.d(TAG, "v.getViewTreeObserver.onGlobalLayout--tag=" + tag + "|v=" + v);
            }
        });
        v.getViewTreeObserver().addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
            @Override
            public void onWindowAttached() {
                Log.d(TAG, "v.getViewTreeObserver.onWindowAttached--tag=" + tag + "|v=" + v);
            }

            @Override
            public void onWindowDetached() {
                Log.d(TAG, "v.getViewTreeObserver.onWindowDetached--tag=" + tag + "|v=" + v);
            }
        });
        v.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                Log.d(TAG, "v.getViewTreeObserver.onGlobalFocusChanged--tag=" + tag + "|v=" + v);
            }
        });
        v.getViewTreeObserver().addOnWindowFocusChangeListener(new ViewTreeObserver.OnWindowFocusChangeListener() {
            @Override
            public void onWindowFocusChanged(boolean hasFocus) {
                Log.d(TAG, "v.getViewTreeObserver.onWindowFocusChanged--tag=" + tag + "|v=" + v);
            }
        });
        v.getViewTreeObserver().addOnDrawListener(new ViewTreeObserver.OnDrawListener() {
            @Override
            public void onDraw() {
                Log.d(TAG, "v.getViewTreeObserver.onDraw--tag=" + tag + "|v=" + v);
            }
        });
        v.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Log.d(TAG, "v.getViewTreeObserver.onPreDraw--tag=" + tag + "|v=" + v);
                return true;
            }
        });
    }

}
