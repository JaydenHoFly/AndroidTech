package com.jaydenho.androidtech.widget.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ViewStubProxy;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.AtyBasicAttrsViewBinding;

/**
 * Created by hedazhao on 2016/9/25.
 */
public class BasicAttrsAty extends Activity {

    private AtyBasicAttrsViewBinding mBinding = null;
    private ViewGroup mRootView = null;
    private BasicAttrsView mBasicView = null;
    private LotteryView mLotteryView = null;
    private QQMsgClearView mQQMsgClearView = null;

    private TextView mWindowTV = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_basic_attrs_view);
        initView();

        mWindowTV = new TextView(this);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, 0, 0, PixelFormat.TRANSPARENT);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED;
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.x = 100;
        layoutParams.y = 300;
        getWindowManager().addView(mWindowTV, layoutParams);

    }

    private void initView() {
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mBasicView = new BasicAttrsView(this);
        mLotteryView = new LotteryView(this);
        mQQMsgClearView = new QQMsgClearView(this);
        mRootView.addView(mBasicView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
