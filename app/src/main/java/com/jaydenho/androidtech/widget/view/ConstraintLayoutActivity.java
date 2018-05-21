package com.jaydenho.androidtech.widget.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.ImageView;

import com.jaydenho.androidtech.BaseActivity;
import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2018/4/10.
 */
public class ConstraintLayoutActivity extends BaseActivity {
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout_01);
        final ConstraintLayout rootView = findViewById(R.id.root_view);
        final ImageView iv4 = findViewById(R.id.imageView4);
        final ConstraintSet constraintSet1 = new ConstraintSet();
        constraintSet1.clone(this, R.layout.constraint_layout_01);
        iv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    TransitionManager.beginDelayedTransition(rootView);
                }
                if (flag = !flag) {
                    constraintSet1.constrainWidth(R.id.imageView4, ConstraintSet.MATCH_CONSTRAINT);
                    constraintSet1.constrainHeight(R.id.imageView4, 400);
                    constraintSet1.connect(R.id.imageView4, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
                    constraintSet1.applyTo(rootView);
                } else {
                    constraintSet1.constrainWidth(R.id.imageView4, 500);
                    constraintSet1.constrainHeight(R.id.imageView4, 200);
                    constraintSet1.connect(R.id.imageView4, ConstraintSet.TOP, R.id.iv_b1, ConstraintSet.BOTTOM);
                    constraintSet1.applyTo(rootView);
                }
                rootView.invalidate();
                rootView.requestLayout();
            }
        });
    }
}
