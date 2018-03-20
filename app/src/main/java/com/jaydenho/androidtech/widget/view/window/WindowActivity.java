package com.jaydenho.androidtech.widget.view.window;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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
        layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
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

        testToastWindowInOtherThread();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 1、线程中弹toast的条件：线程中要有运行的Looper，因为toast的window展示和隐藏的时机受NotificationManagerService调配，
     *      NotificationManagerService和Toast通过Binder进行交互，NotificationManagerService调用Toast.TN#show方法展示，
     *      此时show方法是执行在Binder线程池中的，show方法中由通过Handler将真正的展示方法放在了调用Toast#show方法所在的线程中，因为有Handler的存在，所以需要Looper.
     * 2、在IntentService中弹toast会出现异常：在IntentService中弹toast本质上和在Thread中弹toast一致，但是由于IntentService#onHandleIntent方法执行之后，IntentService就自我销毁了，导致当NotificationManagerService
     *      调用Toast.TN#show方法时，IntentService的线程已经dead，无法继续执行toast的window展示，会报`Handler (android.os.Handler) {3ea85228} sending message to a Handler on a dead thread`警告。
     */
    private void testToastWindowInOtherThread() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(WindowActivity.this, "thread toast", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();

        final HandlerThread thread = new HandlerThread("toast");
        thread.start();

        Handler handler = new Handler(thread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Toast.makeText(WindowActivity.this, "handlerThread toast", Toast.LENGTH_SHORT).show();
                thread.quit();
            }
        };
        handler.sendEmptyMessage(0);

        Intent intent = new Intent(this, ToastIntentService.class);
        startService(intent);
    }

    private Context getContext() {
        return this;
    }
}
