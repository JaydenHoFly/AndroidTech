package com.jaydenho.androidtech.test;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

/**
 * Created by hedazhao on 2016/7/26.
 */
public class TestAty3 extends AppCompatActivity {

    private static final String TAG = TestAty3.class.getSimpleName();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "isFinishing: " + TestAty3.this.isFinishing());
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                Intent intent = new Intent(this,TestAty.class);
                startActivity(intent);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
