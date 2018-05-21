package com.jaydenho.androidtech.hack.vpn;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.morgoo.helper.Log;

/**
 * Created by hedazhao on 2018/5/21.
 */
public class ExampleVpnService extends Service {

    private static final String TAG = "vpn.ExampleVpnService";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "vpn service started");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
