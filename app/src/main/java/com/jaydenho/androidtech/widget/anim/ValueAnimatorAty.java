package com.jaydenho.androidtech.widget.anim;

import android.animation.AnimatorSet;
import android.animation.IntEvaluator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.AtyValueAnimatorBinding;

/**
 * Created by hedazhao on 2016/9/22.
 */
public class ValueAnimatorAty extends Activity implements View.OnClickListener {

    private static final String TAG = ValueAnimatorAty.class.getSimpleName();
    private AtyValueAnimatorBinding mBinding = null;
    private TextView mCircleBallTV = null;
    private LoveView mLoveView = null;
    private ValueAnimator mBallAnimator = null;
    private ValueAnimator mLetterAnimator = null;
    private boolean isInitAnimation = false;
    private ValueAnimator mLoveAnimator = null;
    private ObjectAnimator mLoveObjectAnimator = null;
    private ObjectAnimator mCallObjectAnimator = null;
    private AnimatorSet mBallAnimatorSet = null;
    private ImageView mTelephoneIV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_value_animator);
        initView();
    }

    private void initView() {
        mCircleBallTV = (TextView) findViewById(R.id.tv_anim);
        mLoveView = (LoveView) findViewById(R.id.love_view);
        mTelephoneIV = (ImageView) findViewById(R.id.telephone_iv);
        mBinding.setOnClickListener(this);

        mCircleBallTV.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isInitAnimation) {
                    initBallAnimator();
                    initLetterAnimator();
                    initLoveAnimator();
                    initLoveObjectAnimator();
                    initCallAnimation();
                    initAnimationSet();
                    isInitAnimation = true;
                }
            }
        });
    }


    private void initLetterAnimator() {
        mLetterAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofKeyframe("", Keyframe.ofObject(0.2f, 'A'), Keyframe.ofObject(0.5f, 'Z'), Keyframe.ofObject(1, '3')));
        mLetterAnimator = ValueAnimator.ofObject(new LetterEvaluator(), 'A', '3', 'Z');
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
        PropertyValuesHolder radiusHolder = PropertyValuesHolder.ofInt("radius", radius / 2, radius, radius * 2);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofObject("color", new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                if (fraction < 0.5) {
                    return startValue;
                }
                return endValue;
            }
        }, getResources().getColorStateList(R.color.colorAccent), getResources().getColorStateList(R.color.colorPrimaryDark));
//        mLoveAnimator = ValueAnimator.ofInt(radius / 2, radius, radius * 2);
        mLoveAnimator = ValueAnimator.ofPropertyValuesHolder(colorHolder, radiusHolder);
        mLoveAnimator.setDuration(3000);
        mLoveAnimator.setInterpolator(new BounceInterpolator());
        mLoveAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "animation.value: " + animation.getAnimatedValue());
                if (animation.getAnimatedValue() instanceof Integer) {
                    mLoveView.setRadius((int) animation.getAnimatedValue());
                }
            }
        });
    }

    public void initLoveObjectAnimator() {
        int radius = 200;
        PropertyValuesHolder radiusHolder = PropertyValuesHolder.ofInt("radius", radius / 2, radius, radius * 2);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofObject("color", new TypeEvaluator() {
            @Override
            public Object evaluate(float fraction, Object startValue, Object endValue) {
                if (fraction < 0.5) {
                    return startValue;
                }
                return endValue;
            }
        }, getResources().getColorStateList(R.color.colorAccent), getResources().getColorStateList(R.color.colorPrimaryDark));
//        mLoveObjectAnimator = ObjectAnimator.ofInt(mLoveView, "radius", radius / 2, radius, radius * 2);
        mLoveObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(mLoveView, colorHolder, radiusHolder);
        mLoveObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.d(TAG, "animation.value: " + animation.getAnimatedValue());
            }
        });
        mLoveObjectAnimator.setDuration(3000);
        mLoveObjectAnimator.setInterpolator(new BounceInterpolator());
    }

    public void initCallAnimation() {
        Keyframe firstKeyFrame = Keyframe.ofFloat(0, -30);
        Keyframe secondKeyFrame = Keyframe.ofFloat(0.1f, 30);
        Keyframe thirdKeyFrame = Keyframe.ofFloat(0.2f, -30);
        Keyframe fourthKeyFrame = Keyframe.ofFloat(0.3f, 30);
        Keyframe fifthKeyFrame = Keyframe.ofFloat(0.4f, 0);
        Keyframe fifth1KeyFrame = Keyframe.ofFloat(0.5f, 0);
        Keyframe sixthKeyFrame = Keyframe.ofFloat(0.6f, 0);
        Keyframe seventhKeyFrame = Keyframe.ofFloat(0.7f, -30);
        Keyframe eighthKeyFrame = Keyframe.ofFloat(0.8f, 30);
        Keyframe ninthKeyFrame = Keyframe.ofFloat(0.9f, -30);
        Keyframe tenthKeyFrame = Keyframe.ofFloat(1f, 30);
        PropertyValuesHolder callHolder = PropertyValuesHolder.ofKeyframe("rotation", firstKeyFrame, secondKeyFrame, thirdKeyFrame, fourthKeyFrame, fifthKeyFrame, fifth1KeyFrame, sixthKeyFrame, seventhKeyFrame, eighthKeyFrame, ninthKeyFrame, tenthKeyFrame);
        mCallObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(mTelephoneIV, callHolder);
        mCallObjectAnimator.setDuration(1000);
        mCallObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        mCallObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
    }

    private void initAnimationSet() {
        mBallAnimatorSet = new AnimatorSet();
        mBallAnimatorSet.playTogether(mBallAnimator, mLetterAnimator);
//        mBallAnimatorSet.play(mLetterAnimator).before(mBallAnimator);
        mBallAnimatorSet.setDuration(2000);
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
                if (mBallAnimator != null) {
                    //需要判空再操作，因为手机设置中可以关闭动画，导致Animator对象为null
                    mBallAnimator.start();
                }
                break;
            case R.id.btn_letter:
                if (mLetterAnimator != null) {
                    mLetterAnimator.start();
                }
                break;
            case R.id.btn_love:
//                startLoveAnimation();
                if (mCallObjectAnimator != null) {
                    mCallObjectAnimator.cancel();
                }
                break;
            case R.id.btn_love_object:
                startLoveObjectAnimation();
                break;
            case R.id.btn_call:
                if (mCallObjectAnimator != null) {
                    mCallObjectAnimator.start();
                }
                break;
            case R.id.btn_set:
                if (mBallAnimatorSet != null) {
                    mBallAnimatorSet.start();
                }
                break;
        }
    }

    public class LetterEvaluator implements TypeEvaluator<Character> {

        @Override
        public Character evaluate(float fraction, Character startValue, Character endValue) {
            Log.d(TAG, "startValue: " + startValue + " endValue: " + endValue);
            int endAscii = (int) endValue;
            int startAscii = (int) startValue;
            return (char) ((int) (startAscii + (endAscii - startAscii) * fraction));
        }
    }
}
