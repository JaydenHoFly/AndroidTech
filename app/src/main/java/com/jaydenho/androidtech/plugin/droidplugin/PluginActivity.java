package com.jaydenho.androidtech.plugin.droidplugin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaydenho.androidtech.plugin.droidplugin.hookAMS.AMSHookHelper;
import com.jaydenho.androidtech.plugin.droidplugin.hookbinder.BinderHookHelper;

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

        try {
            AMSHookHelper.hookActivityManagerNative();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            AMSHookHelper.hookActivityThreadMH();
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

        setContentView(layout);
    }
}
