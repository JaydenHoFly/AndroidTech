package com.jaydenho.androidtech.plugin.droidplugin;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaydenho.androidtech.plugin.droidplugin.hookactivity.HookHelper;
import com.jaydenho.androidtech.plugin.droidplugin.hookams.AMSHookHelper;
import com.jaydenho.androidtech.plugin.droidplugin.hookbinder.BinderHookHelper;
import com.jaydenho.androidtech.plugin.droidplugin.hookclassloader.muticlassloader.LoadedApkClassLoaderHookHelper;

import java.io.File;

/**
 * Created by hedazhao on 2017/4/23.
 */
public class PluginActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            BinderHookHelper.hookServiceManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        LinearLayout layout = new LinearLayout(this);

        EditText editText = new EditText(this);
        layout.addView(editText);

        TextView jump2Activity2 = new TextView(this);
        jump2Activity2.setText("jump2Activity3");
        jump2Activity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PluginActivity.this, PluginActivity3.class));
            }
        });
        layout.addView(jump2Activity2);

        TextView jump2PluginActivity = new TextView(this);
        jump2PluginActivity.setText("jump2PluginActivity");
        jump2PluginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent t = new Intent();
                    t.setComponent(new ComponentName("com.weishu.upf.ams_pms_hook.app",
                            "com.weishu.upf.ams_pms_hook.app.MainActivity"));
                    //目前能调起assets下的apk中的MainActivity,但是资源问题还没解决,会崩溃
                    startActivity(t);
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        layout.addView(jump2PluginActivity);

        setContentView(layout);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
//            Utils.extractAssets(newBase, "dynamic-proxy-hook.apk");
            Utils.extractAssets(newBase, "ams-pms-hook.apk");
//            Utils.extractAssets(newBase, "test.apk");

            LoadedApkClassLoaderHookHelper.hookLoadedApkInActivityThread(getFileStreamPath("ams-pms-hook.apk"));

            AMSHookHelper.hookActivityManagerNative();
            AMSHookHelper.hookActivityThreadMH();
            HookHelper.changeActivityInstrumentation(this);
            HookHelper.attachContext();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
