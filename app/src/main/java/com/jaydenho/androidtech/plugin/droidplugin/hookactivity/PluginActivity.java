package com.jaydenho.androidtech.plugin.droidplugin.hookactivity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

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

        EditText editText = new EditText(this);
        setContentView(editText);
    }
}
