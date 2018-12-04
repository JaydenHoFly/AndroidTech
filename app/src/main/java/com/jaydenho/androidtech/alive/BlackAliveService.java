package com.jaydenho.androidtech.alive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * 黑色唤醒，另一个app启动当前Service, 达到被唤醒的目的。
 * Created by hedazhao on 2018/12/4.
 */
public class BlackAliveService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "进程已被唤醒", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }
}
