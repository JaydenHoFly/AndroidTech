package com.jaydenho.androidtech.widget.view.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hedazhao on 2018/4/17.
 */
public class CustomDurationLongClickView extends android.support.v7.widget.AppCompatImageView {

    private static final String TAG = "CustomDurationLongClickView";
    private CheckForCustomLongPress mPendingCheckForCustomLongPress;
    private boolean mHasPerformedLongPress;
    private long mLongClickDurationMillis = 2000;
    private OnLongClickListener mOnLongClickListener = null;

    public CustomDurationLongClickView(Context context) {
        super(context);
        init();
    }

    public CustomDurationLongClickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomDurationLongClickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setLongClickable(false);
        setOnClickListener(new OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick");
            }
        });
    }

    @Override
    public void setLongClickable(boolean longClickable) {
        super.setLongClickable(false);
    }

    @Override
    public void setOnClickListener(@Nullable final OnClickListener l) {
        super.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (l != null && !mHasPerformedLongPress) {
                    l.onClick(v);
                }
            }
        });
    }

    @Override
    public void setOnLongClickListener(@Nullable OnLongClickListener l) {
        mOnLongClickListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                checkCustomLongClick();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                removeCustomLongClickCallback();
                break;
        }
        return super.onTouchEvent(event);
    }

    private void checkCustomLongClick() {
        mHasPerformedLongPress = false;
        if (mPendingCheckForCustomLongPress == null) {
            mPendingCheckForCustomLongPress = new CheckForCustomLongPress();
        }
        postDelayed(mPendingCheckForCustomLongPress, mLongClickDurationMillis);
    }

    private void removeCustomLongClickCallback() {
        if (mPendingCheckForCustomLongPress != null) {
            removeCallbacks(mPendingCheckForCustomLongPress);
        }
    }

    private final class CheckForCustomLongPress implements Runnable {

        @Override
        public void run() {
            if (performCustomLongClick()) {
                mHasPerformedLongPress = true;
            }
        }
    }

    @SuppressLint("LongLogTag")
    private boolean performCustomLongClick() {
        if (!mHasPerformedLongPress) {
            if (mOnLongClickListener != null) {
                mOnLongClickListener.onLongClick(this);
            }
            return true;
        }
        return false;
    }
}