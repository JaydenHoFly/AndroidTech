package com.jaydenho.androidtech.widget.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by hedazhao on 2017/6/27.
 */

public class PullScrollView extends ScrollView {

    private View mRootView = null;
    private float mPreY = 0;
    private Rect mOriginRect = new Rect();
    private ObjectAnimator mTranslateAnimator = null;

    public PullScrollView(Context context) {
        this(context, null);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float curY = ev.getY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (mRootView != null) {
                    mOriginRect.set(mRootView.getLeft(), mRootView.getTop(), mRootView.getRight(), mRootView.getBottom());
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float deltaY = (curY - mPreY) * 0.25f;
                if (mRootView != null) {
                    mRootView.layout(mRootView.getLeft(), mRootView.getTop() + (int) deltaY, mRootView.getRight(), mRootView.getBottom() + (int) deltaY);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mRootView != null) {
                    mRootView.layout(mOriginRect.left, mOriginRect.top, mOriginRect.right, mOriginRect.bottom);
                    mTranslateAnimator = ObjectAnimator.ofInt(mRootView, "TranslationY", (int) curY - mOriginRect.top, 0);
                    mTranslateAnimator.setDuration(200);
                    mTranslateAnimator.setInterpolator(new LinearInterpolator());
                    mTranslateAnimator.start();
                }
                break;
        }
        mPreY = curY;
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        mRootView = getChildAt(0);
        super.onFinishInflate();
    }
}
