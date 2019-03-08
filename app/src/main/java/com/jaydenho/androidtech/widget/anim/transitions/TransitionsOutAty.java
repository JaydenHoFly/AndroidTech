package com.jaydenho.androidtech.widget.anim.transitions;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2019/3/8.
 */
public class TransitionsOutAty extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_transitions_out);
        findViewById(R.id.iv_shared).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitionsOutAty.this, TransitionsInAty.class);
                Bundle bundle = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bundle = ActivityOptions.makeSceneTransitionAnimation(TransitionsOutAty.this,
                            Pair.create(findViewById(R.id.iv_shared), "scene"),
                            Pair.create(findViewById(R.id.btn_jump), "qq")).toBundle();
                }
                startActivity(intent, bundle);
            }
        });
    }
}
