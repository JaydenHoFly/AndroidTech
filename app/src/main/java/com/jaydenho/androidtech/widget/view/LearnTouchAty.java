package com.jaydenho.androidtech.widget.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jaydenho.androidtech.BaseActivity;
import com.jaydenho.androidtech.R;

/**
 * 学习View的触摸事件分发。
 *
 * @author hedazhao
 * @since 2019/11/3
 */
public class LearnTouchAty extends BaseActivity {
    private static final String TAG = "LearnTouchAty";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_learn_touch);
        learnTouch();
    }

    /**
     * 层层嵌套的View。
     * 1、外层和里层都选择消费触摸事件，触摸里层时，里层收到onTouch的完整响应（down，move，up）。
     * 2、onTouch返回true、setOnClickListener都会消费触摸事件。
     * 3、外层和里层都不消费触摸事件，触摸里层时，外层和里层都会收到onTouch的down响应，但不会收到后续响应，因为没有消费触摸事件。
     * 4、外层选择消费触摸事件，里层不消费触摸事件，触摸里层时，外层收到onTouch的完整响应，里面收到onTouch的down响应。
     * 综上：触摸事件会先分发给里层处理（外层可以选择其他方式拦截），如果里层不消费，再交给外层消费，一个触摸事件和一整次点击（down，move，up）都只能被一个View消费。
     */
    private void learnTouch() {
        View.OnTouchListener l = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch--v.height=" + v.getHeight() + "|event.action=" + event.getAction());
                if (v.getId() == R.id.btn_2) {
                    return false;
                }
                return false;
            }
        };

        View.OnClickListener cl = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick--v.height=" + v.getHeight());
            }
        };

        findViewById(R.id.btn_1).setOnTouchListener(l);
        findViewById(R.id.btn_2).setOnTouchListener(l);
        findViewById(R.id.btn_3).setOnTouchListener(l);

//        findViewById(R.id.btn_3).setOnClickListener(cl);
    }
}
