package com.jaydenho.androidtech.widget.view;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jaydenho.androidtech.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by hedazhao on 2016/11/14.
 */
public class ViewPagerAty extends Activity {

    private static final String TAG = ViewPagerAty.class.getSimpleName();
    private ViewPager mVP = null;
    private CircleIndicator mViewPagerIndicator = null;
    private MyViewPagerAdapter mAdapter = null;
    private List<ViewPagerItemView> mViews = null;
    private int mLastPosition = 0;
    private int mCurrentPosition = 0;

    /**
     * 已经加载过gif图的position集合
     */
    private Set<Integer> mGifPositionSet = null;

    private static final int[] GIF_ARRAY = new int[]{R.mipmap.gif_guide_2_planet, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5, R.mipmap.gif5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN); // hide status bar
        }
        setContentView(R.layout.aty_viewpager);
        initViews();
        mAdapter = new MyViewPagerAdapter();

        mVP.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "onPageScrolled: position: " + position);
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: position: " + position);
                mLastPosition = mCurrentPosition;
                mCurrentPosition = position;
                setGifResource(mViews.get(mCurrentPosition), mCurrentPosition);
                mViews.get(mLastPosition).getGifView().pause();
                mViews.get(mCurrentPosition).getGifView().play();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.d(TAG, "onPageScrollStateChanged: state: " + state);
            }
        });

        mVP.setAdapter(mAdapter);
        mViews.get(0).getGifView().play();
        mLastPosition = -1;
        mCurrentPosition = 0;
        mViewPagerIndicator.setViewPager(mVP);

        Log.d(TAG, "mVP.getCurrentItem(): " + mVP.getCurrentItem());
        mVP.setCurrentItem(1);
    }

    private void initViews() {
        mVP = (ViewPager) findViewById(R.id.vp);
        mViewPagerIndicator = (CircleIndicator) findViewById(R.id.circle_indicator);

        mGifPositionSet = new HashSet<>();
        mViews = new ArrayList<>();
        ViewPagerItemView v;
        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        setGifResource(v, 0);
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        /* v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);

        v = new ViewPagerItemView(this);
        v.getGifView().pause();
        mViews.add(v);*/
    }

    private void setGifResource(@NonNull ViewPagerItemView v, int position) {
        if (!mGifPositionSet.contains(position)) {
            if (position >= GIF_ARRAY.length) {
                throw new ArrayIndexOutOfBoundsException("this position is out of gif_array bounds. position: " + position + " gif_array.length: " + GIF_ARRAY.length);
            }
            v.getGifView().setGifResource(GIF_ARRAY[position]);
            mGifPositionSet.add(position);
        } else {
            Log.d(TAG, "gif has already added. position: " + position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearViews();
    }

    private void clearViews() {
        mViews.clear();
    }

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViews.get(position));
            return mViews.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViews.get(position));
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
