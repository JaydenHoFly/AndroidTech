package com.jaydenho.androidtech.view.anim;

import android.animation.IntEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.AtyValueAnimatorBinding;

/**
 * Created by hedazhao on 2016/9/22.
 */
public class ValueAnimatorAty extends Activity implements View.OnClickListener {

    private AtyValueAnimatorBinding mBinding = null;
    private TextView mCircleBallTV = null;
    private LoveView mLoveView = null;
    private ValueAnimator mBallAnimator = null;
    private ValueAnimator mLetterAnimator = null;
    private boolean isInitAnimation = false;
    private ValueAnimator mLoveAnimator = null;
    private ObjectAnimator mLoveObjectAnimator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_value_animator);
        initView();
    }

    private void initView() {
        mCircleBallTV = (TextView) findViewById(R.id.tv_anim);
        mLoveView = (LoveView) findViewById(R.id.love_view);
        mBinding.setOnClickListener(this);

        mCircleBallTV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isInitAnimation) {
                    initBallAnimator();
                    initLetterAnimator();
                    initLoveAnimator();
                    initLoveObjectAnimator();
                    isInitAnimation = true;
                }
            }
        });
    }

    private void initLetterAnimator() {
        mLetterAnimator = ValueAnimator.ofObject(new LetterEvaluator(), 'A', 'Z');
        mLetterAnimator.setInterpolator(new AccelerateInterpolator());
        mLetterAnimator.setDuration(5000);
        mLetterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Character letter = (Character) animation.getAnimatedValue();
                mCircleBallTV.setText(String.valueOf(letter));
            }
        });

    }

    private void initBallAnimator() {
        int top = mCircleBallTV.getTop();
        mBallAnimator = ValueAnimator.ofInt(top, top + 200, top + 50, top + 200, top + 100, top + 200);
        mBallAnimator.setDuration(5000);
        mBallAnimator.setInterpolator(new LinearInterpolator());
        mBallAnimator.setEvaluator(new IntEvaluator());
        mBallAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int x = (int) animation.getAnimatedValue();
                mCircleBallTV.layout(mCircleBallTV.getLeft(), x, mCircleBallTV.getRight(), x + mCircleBallTV.getHeight());
            }
        });
    }

    public void initLoveAnimator() {
        int radius = 200;
        mLoveAnimator = ValueAnimator.ofInt(radius / 2, radius, radius * 2);
        mLoveAnimator.setDuration(3000);
        mLoveAnimator.setInterpolator(new BounceInterpolator());
        mLoveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mLoveView.setRadius((int) animation.getAnimatedValue());
            }
        });
    }

    public void initLoveObjectAnimator() {
        int radius = 200;
        mLoveObjectAnimator = ObjectAnimator.ofInt(mLoveView, "radius", radius / 2, radius, radius * 2);
        mLoveObjectAnimator.setDuration(3000);
        mLoveObjectAnimator.setInterpolator(new BounceInterpolator());
    }

    public void startLoveAnimation() {
        if (mLoveAnimator != null) {
            mLoveAnimator.start();
        }
    }

    public void startLoveObjectAnimation() {
        if (mLoveObjectAnimator != null) {
            mLoveObjectAnimator.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_drop_ball:
                mBallAnimator.start();
                break;
            case R.id.btn_letter:
                mLetterAnimator.start();
                break;
            case R.id.btn_love:
                startLoveAnimation();
                break;
            case R.id.btn_love_object:
                startLoveObjectAnimation();
                break;
        }
    }

    public class LetterEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            int endAscii = (int) endValue;
            int startAscii = (int) startValue;
            return (char) ((int) (startAscii + (endAscii - startAscii) * fraction));
        }
    }
}
