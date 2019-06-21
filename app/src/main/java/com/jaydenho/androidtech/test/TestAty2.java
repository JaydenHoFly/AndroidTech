package com.jaydenho.androidtech.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jaydenho.androidtech.R;

import java.lang.annotation.Annotation;

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            setDisplayCutoutMode(getWindow(), WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER);
        }
        setContentView(R.layout.aty_test_2_1);
    }

    /**
     * android P 设置刘海屏显示模式
     * @param window
     * @param mode LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT 默认非全屏使用刘海，全屏不使用刘海
     *             LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES 此flag下不进行应用窗口的限制，窗口可以显示到刘海区域
     *             LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER 此flag下应用窗口一直被限制在刘海区域以下
     */
    public static void setDisplayCutoutMode(Window window, int mode){
        if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.layoutInDisplayCutoutMode = mode;
            window.setAttributes(lp);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_VOLUME_UP:
                Intent intent = new Intent(this,TestAty3.class);
                startActivity(intent);
                setResult(1);
                finish();
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                finishAffinity();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }
}
