package com.jaydenho.learnbinder.learnaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * Created by Administrator on 2017/5/1.
 */
public class SignService extends Service {

    private IBinder stub = new ISign.Stub() {

        @Override
        public String sign(String params) throws RemoteException {
            return "you are " + params;
        }

        @Override
        public String sign(int num, List<Param> params) throws RemoteException {
            if (params == null || params.isEmpty()) {
                throw new IllegalArgumentException("params is empty");
            }
            StringBuilder sb = new StringBuilder();
            for (Param param : params) {
                sb.append(param.name).append("|");
            }
            return sb.toString();
        }
    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
