package com.jaydenho.androidtech.widget.view.window;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by Administrator on 2018/2/7.
 */

public class WindowActivity extends Activity {
    private WindowManager mWindowManager = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mWindowManager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                0, 0, PixelFormat.TRANSLUCENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION;
        final Button btn = new Button(getContext());
        btn.setText("btn");
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int rawX = (int) motionEvent.getRawX();
                int rawY = (int) motionEvent.getRawY();
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        layoutParams.x = rawX;
                        layoutParams.y = rawY;
                        mWindowManager.updateViewLayout(btn, layoutParams);
                        break;
                }
                return false;
            }
        });
        if (mWindowManager != null) {
            mWindowManager.addView(btn, layoutParams);
        }
    }

    private Context getContext() {
        return this;
    }
}
