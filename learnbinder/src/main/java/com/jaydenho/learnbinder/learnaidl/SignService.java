package com.jaydenho.learnbinder.learnaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/5/1.
 */
public class SignService extends Service {

    private IBinder stub = new ISignSelf.Stub() {

        @Override
        public String sign(String params) throws RemoteException {
            return "you are " + params;
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
