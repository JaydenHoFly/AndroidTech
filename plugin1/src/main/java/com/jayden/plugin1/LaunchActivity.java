package com.jayden.plugin1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class LaunchActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = new TextView(this);
        tv.setText("Hello, I'm LaunchActivity from plugin1");
        setContentView(tv);
    }
}
