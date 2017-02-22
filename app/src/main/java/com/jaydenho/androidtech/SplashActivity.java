package com.jaydenho.androidtech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;

/**
 * Created by hedazhao on 2016/12/29.
 */
public class SplashActivity extends Activity {

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }
}
