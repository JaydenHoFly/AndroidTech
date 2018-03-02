package com.jaydenho.androidtech.test;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hedazhao on 2018/2/27.
 */

public class OppoNotiService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification noti = MyNotification.getCommonNotification(this, "title", "desc", 0);
        startForeground(1, noti);
        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopForeground(false);
            }
        }, 1000);

        ConcurrentHashMap map ;
        return super.onStartCommand(intent, flags, startId);
    }
}
