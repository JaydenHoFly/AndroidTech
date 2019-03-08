package com.jaydenho.androidtech.widget.anim.transitions;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2019/3/8.
 */
public class TransitionsInAty extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_transitions_in);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setExitTransition(new Explode());
        }

    }

}
