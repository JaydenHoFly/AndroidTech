package com.jaydenho.androidtech.ipc.binder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jaydenho.learnbinder.learnaidl.ISign;
import com.jaydenho.learnbinder.learnaidl.Param;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/5/1.
 */
public class AIDLActivity extends Activity {

    private static final String TAG = "AIDLActivity";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            sign(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void sign(IBinder service) {
        ISign sign = ISign.Stub.asInterface(service);
        try {
            String signString = sign.sign("hello world");
            Log.d(TAG, "SignString: " + signString);
            List<Param> params = new ArrayList<>();
            params.add(new Param("zs"));
            params.add(new Param("lisi"));
            String signString2 = sign.sign(1, params);
            Log.d(TAG, "SignString2: " + signString2);
            String signString3 = sign.sign(2, Collections.<Param>emptyList());
            Log.d(TAG, "signString3: " + signString3);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent("com.jaydenho.learnbinder.signaidl");
        intent.setPackage("com.jaydenho.learnbinder");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
