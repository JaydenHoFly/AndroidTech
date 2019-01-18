package com.jaydenho.androidtech.process;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jaydenho.androidtech.BaseActivity;
import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.test.MyNotification;

/**
 * 依附于子进程的Activity.
 * 从主进程的Activity跳转子进程的Activity,
 * Activity栈的关系不会因进程不同而改变。
 * Created by hedazhao on 2019/1/18.
 */
public class ProcessActivity extends BaseActivity {
    private static final String TAG = "ProcessActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_process);
        MyNotification.notifyTaskNotification(this, "Hello", "Process", "", 3231);
    }
}
