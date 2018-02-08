package com.jaydenho.androidtech.widget.view.window;

import android.content.Context;
import android.graphics.PixelFormat;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Administrator on 2018/2/7.
 */

public class WindowView extends View {
    private WindowManager mWindowManager = null;

    public WindowView(Context context) {
        super(context);
        init();
    }

    public WindowView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WindowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.TRANSLUCENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG;
        Button btn = new Button(getContext());
        btn.setText("btn");
        btn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        if (mWindowManager != null) {
            mWindowManager.addView(btn, layoutParams);
        }
    }

}
