package com.jaydenho.androidtech.widget.view.scroll;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.jaydenho.androidtech.R;
import com.jaydenho.androidtech.databinding.AtyScrollBinding;

/**
 * Created by hedazhao on 2017/2/9.
 */
public class ScrollAty extends Activity {

    private AtyScrollBinding mBinding = null;
    private View mRootView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.aty_scroll);

        mRootView = findViewById(R.id.layout);

        mBinding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.scroll_by_btn:
                        mRootView.scrollBy(10, 20);
                        break;
                    case R.id.scroll_to_btn:
                        mRootView.scrollTo(-50, -200);
                        break;
                }
            }
        });
    }
}
