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

import com.jaydenho.learnbinder.learnaidl.IBook;
import com.jaydenho.learnbinder.learnaidl.ISign;

/**
 * Created by Administrator on 2017/5/1.
 */
public class AIDLActivity extends Activity {

    private static final String TAG = "AIDLActivity";

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            sign(service);
            book(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private void sign(IBinder service) {
        ISign sign = ISign.Stub.asInterface(service);
        try {
            String signString = sign.sign("hello world");
            Log.d(TAG,"SignString: " + signString);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void book(IBinder service) {
        IBook book = IBook.Stub.asInterface(service);
        try{
            int price = book.getBookPrice("sanguo");
            Log.d(TAG,"price: " + price);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Intent intent = new Intent("com.jaydenho.learnbinder.signaidl");
        Intent intent = new Intent("com.jaydenho.learnbinder.bookaidl");
        intent.setPackage("com.jaydenho.learnbinder");
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
