package com.jaydenho.androidtech.profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jaydenho.androidtech.BaseActivity;
import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2018/4/24.
 */
public class ProfileAty extends BaseActivity {
    private static final String TAG = "ProfileAty";

    private HandlerThread mHandlerThread = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_profile);
//        hardOperation();
        mHandlerThread = new HandlerThread("hard");
        mHandlerThread.start();
        Handler h = new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                hardOperation();
                return false;
            }
        });
        h.sendEmptyMessageDelayed(1, 2000);
    }

    private void hardOperation() {
        long begin = System.currentTimeMillis();
        Log.d(TAG,"begin: " + begin);
        for (int i = 0; i < 500; i++) {
            for (int j = 0; j < 500; j++) {
                int z = i + j;
            }
        }
        Log.d(TAG,"end. duration: " + (System.currentTimeMillis() - begin));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit();
    }
}
