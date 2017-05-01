package com.jaydenho.androidtech.plugin.droidplugin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by hedazhao on 2017/4/25.
 */

public class PluginActivityStub extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("PluginActivityStub");
        setContentView(tv);
    }
}
