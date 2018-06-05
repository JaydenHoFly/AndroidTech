package com.jaydenho.androidtech.test.systemreveiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by hedazhao on 2018/6/4.
 */
public class SystemReceiver extends BroadcastReceiver {
    private static final String TAG = "SystemReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"action: " + intent.getAction());
        Log.d(TAG,"uid: " + intent.getIntExtra(Intent.EXTRA_UID, -1));
        Toast.makeText(context,intent.getAction(),Toast.LENGTH_SHORT).show();
    }
}
