package com.jaydenho.androidtech.plugin.droidplugin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by hedazhao on 2017/4/25.
 */
public class PluginActivity3 extends Activity {

    private static final String TAG = "plugin.PluginActivity3";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ams，没想到吧，我启动了。");

        TextView tv = new TextView(this);
        tv.setText("PluginActivity3");
        setContentView(tv);
    }
}
