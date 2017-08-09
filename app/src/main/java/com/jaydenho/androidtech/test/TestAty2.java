package com.jaydenho.androidtech.test;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/7/26.
 */
public class TestAty2 extends AppCompatActivity {

    private static final String TAG = TestAty2.class.getSimpleName();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.d(TAG, "isFinishing: " + TestAty2.this.isFinishing());
            return false;
        }
    });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_test_2);
        mHandler.sendEmptyMessageDelayed(1, 5000);
    }
}
