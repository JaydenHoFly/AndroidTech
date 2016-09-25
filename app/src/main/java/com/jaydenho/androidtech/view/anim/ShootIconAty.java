package com.jaydenho.androidtech.view.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.AtyShootIconBinding;

/**
 * Created by hedazhao on 2016/9/24.
 */
public class ShootIconAty extends Activity implements View.OnClickListener {

    private AtyShootIconBinding mBinding = null;
    private AnimatorSet mShootIconAnimatorSet = null;
    private ValueAnimator mTranslateAnimator = null;
    private int mRadius;
    private int mScreenWith;
    private int mScreenHeight;
    private ImageView mOneIV = null;
    private ImageView mTwoIV = null;
    private ImageView mThreeIV = null;
    private ImageView mFourIV = null;
    private TextView mShootBtn = null;
    private boolean isAnimatorInited = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_shoot_icon);
        initScreenSize();
        initView();
        initAnimator();
    }

    private void initView() {
        mShootBtn = (TextView) findViewById(R.id.btn_shoot);
        mOneIV = (ImageView) findViewById(R.id.icon_one_iv);
        mTwoIV = (ImageView) findViewById(R.id.icon_two_iv);
        mThreeIV = (ImageView) findViewById(R.id.icon_three_iv);
        mFourIV = (ImageView) findViewById(R.id.icon_four_iv);

        mBinding.setOnClickListener(this);
    }

    private void initAnimator() {
        mShootBtn.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isAnimatorInited) {
                    mRadius = 300 * 3;
                    int averageAngle = 90 / 5;
                    ObjectAnimator oneTranAnimator = getTranslateAnimator(mOneIV, averageAngle);
                    ObjectAnimator twoTranAnimator = getTranslateAnimator(mTwoIV, averageAngle * 2);
                    ObjectAnimator threeTranAnimator = getTranslateAnimator(mThreeIV, averageAngle * 3);
                    ObjectAnimator fourTranAnimator = getTranslateAnimator(mFourIV, averageAngle * 4);
                    mShootIconAnimatorSet = new AnimatorSet();
                    mShootIconAnimatorSet.setDuration(3000);
                    mShootIconAnimatorSet.playTogether(oneTranAnimator, twoTranAnimator, threeTranAnimator, fourTranAnimator);
                    isAnimatorInited = true;
                }
            }
        });
    }

    private ObjectAnimator getTranslateAnimator(ImageView target, float angle) {
        double radian = Math.PI / 180 * angle;
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofFloat("translationX", 0,  - (float) (mRadius * Math.cos(radian)));
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofFloat("translationY", 0,  - (float) (mRadius * Math.sin(radian)));
        PropertyValuesHolder xScaleHolder = PropertyValuesHolder.ofFloat("scaleX", 0, 1);
        PropertyValuesHolder yScaleHolder = PropertyValuesHolder.ofFloat("scaleY", 0, 1);
        PropertyValuesHolder alphaHolder = PropertyValuesHolder.ofFloat("alpha", 0, 1);
        PropertyValuesHolder rotateHolder = PropertyValuesHolder.ofFloat("rotation", 0, 360, 720);
        return ObjectAnimator.ofPropertyValuesHolder(target, xHolder, yHolder, xScaleHolder, yScaleHolder, alphaHolder, rotateHolder);
    }

    private ObjectAnimator getScaleAnimator(ImageView target) {
        PropertyValuesHolder xHolder = PropertyValuesHolder.ofInt("scaleX", 0, 1);
        PropertyValuesHolder yHolder = PropertyValuesHolder.ofInt("scaleY", 0, 1);
        return ObjectAnimator.ofPropertyValuesHolder(target, xHolder, yHolder);
    }

    private void initScreenSize() {
        WindowManager wm = this.getWindowManager();
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        mScreenWith = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_shoot:
                mShootIconAnimatorSet.start();
                break;
        }
    }
}