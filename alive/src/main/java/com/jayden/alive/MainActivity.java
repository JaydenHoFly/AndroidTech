package com.jayden.alive;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View callOtherBtn = findViewById(R.id.btn_call_other);
        callOtherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendService();
            }
        });

        findViewById(R.id.btn_jump_to_permission).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobileInfoUtils.jumpStartInterface(MainActivity.this);
            }
        });
    }

    /**
     * 黑色唤醒，启动另一个app中的Service, 达到唤醒另一个app的目的。
     */
    private void sendService() {
        boolean find = false;

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        Intent serviceIntent = new Intent();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : mActivityManager.getRunningAppProcesses()) {
//            if(runningAppProcessInfo.processName)
        }
        //判断服务是否起来，如果服务没起来，就唤醒
        if (!find) {
//            serviceIntent.setPackage("com.jaydenho.androidtech");
//            serviceIntent.setAction("blackalive");

//            serviceIntent.setPackage("com.xunlei.downloadprovider");
//            serviceIntent.setAction("com.xunlei.downloadprovider.assist");

//            serviceIntent.setComponent(new ComponentName("com.xunlei.downloadprovider","com.xunlei.downloadprovider.pushmessage.umeng.UmengPushHandleService"));

//            serviceIntent.setComponent(new ComponentName("com.xunlei.downloadprovider","com.xunlei.downloadprovider.pushmessage.getui.GetuiPushService"));

//            serviceIntent.setComponent(new ComponentName("com.xunlei.downloadprovider","com.xunlei.downloadprovider.app.alive.assist.AssistService"));

//            serviceIntent.setComponent(new ComponentName("com.xunlei.downloadprovider","com.xunlei.downloadprovider.pushmessage.getui.GetuiPush1Service"));

//            serviceIntent.setPackage("com.xunlei.downloadprovider");
//            serviceIntent.setAction("com.igexin.sdk.action.service.message");

            serviceIntent.setPackage("com.xunlei.downloadprovider");
//            serviceIntent.setAction("com.taobao.accs.intent.action.SERVICE");
//            serviceIntent.setAction("org.agoo.android.intent.action.RECEIVE");
//            serviceIntent.setAction("org.android.agoo.client.MessageReceiverService");
            serviceIntent.setAction("com.xunlei.downloadprovider.service.DownloadServiceAction");

            serviceIntent.putExtra("source","android_tech");
            startService(serviceIntent);
            Toast.makeText(this, "开始唤醒 B", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "B 不用唤醒", Toast.LENGTH_SHORT).show();
        }
    }
}
