package com.jaydenho.androidtech.widget.anim.transitions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
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
        ViewCompat.setTransitionName(findViewById(R.id.iv_avatar),"detail:avatar");
        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransitionsOutAty.this, TransitionsInAty.class);
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(TransitionsOutAty.this).toBundle();
/*
                Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(TransitionsOutAty.this,
                        Pair.create(findViewById(R.id.iv_shared), "scene"),
                        Pair.create(findViewById(R.id.btn_jump), "qq")).toBundle();
*/
                startActivity(intent, bundle);
            }
        });
    }
}
