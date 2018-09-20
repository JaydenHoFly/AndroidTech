package com.jaydenho.androidtech.widget.anim;

import android.animation.Animator;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.AtyAnimAttrsBinding;

import java.util.concurrent.TimeUnit;

/**
 * Created by hedazhao on 2016/9/7.
 */
public class AttrAty extends Activity implements View.OnClickListener {

    private AtyAnimAttrsBinding mBinding = null;
    private Animation mScaleAnimation = null;
    private Animation mAlphaAnimation = null;
    private Animator mTextAlphaAnimation = null;
    private Animation mTranslateAnimation = null;
    private Animation mRotateAnimation = null;
    private TextView mAnimTV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_anim_attrs);
        initView();
    }

    private void initView() {
        mBinding.setOnClickListener(this);
        mAnimTV = (TextView) findViewById(R.id.tv_anim);
        mScaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleanim);
        mAlphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alphaanim);
        mTextAlphaAnimation = ObjectAnimator.ofInt(this, "textAlpha", 0, 255);
        mTextAlphaAnimation.setDuration(TimeUnit.SECONDS.toMillis(10));
        ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        mTranslateAnimation = AnimationUtils.loadAnimation(this, R.anim.translateanim);
        mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotateanim);
        mRotateAnimation = new RotateAnimation(0, 650, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
        Interpolator interpolator = new AnticipateOvershootInterpolator();
        //属性动画
        ValueAnimator floatAnimator = ValueAnimator.ofFloat(0, 40, 200, 400);
    }

    @Keep
    public void setTextAlpha(int textColorAlpha) {
        TextView tv = findViewById(R.id.btn_alpha);
//        tv.setTextColor(Color.argb(textColorAlpha,0,0,0));
        tv.setTextColor(textColorAlpha << 24 + getResources().getColor(R.color.text_color_black));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_alpha:
                mAnimTV.startAnimation(mAlphaAnimation);
                mTextAlphaAnimation.start();
                break;
            case R.id.btn_scale:
                mAnimTV.startAnimation(mScaleAnimation);
                break;
            case R.id.btn_translate:
                mAnimTV.startAnimation(mTranslateAnimation);
                break;
            case R.id.btn_rotate:
                mAnimTV.startAnimation(mRotateAnimation);
                break;
        }
    }
}
