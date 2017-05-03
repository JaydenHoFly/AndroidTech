package com.jaydenho.learnbinder.learnaidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by Administrator on 2017/5/3.
 */

public class BookService extends Service {
    private Binder stub = new IBook.Stub() {
        @Override
        public int getBookPrice(String name) throws RemoteException {
            return 100;
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }
}
