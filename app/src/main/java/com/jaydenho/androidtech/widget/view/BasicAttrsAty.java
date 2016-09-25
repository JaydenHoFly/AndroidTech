package com.jaydenho.androidtech.widget.view;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.ViewGroup;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.AtyBasicAttrsViewBinding;

/**
 * Created by hedazhao on 2016/9/25.
 */
public class BasicAttrsAty extends Activity {

    private AtyBasicAttrsViewBinding mBinding = null;
    private ViewGroup mRootView = null;
    private BasicAttrsView mBasicView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_basic_attrs_view);
        initView();
    }

    private void initView() {
        mRootView = (ViewGroup) findViewById(R.id.root_view);
        mBasicView = new BasicAttrsView(this);
        mRootView.addView(mBasicView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }
}
