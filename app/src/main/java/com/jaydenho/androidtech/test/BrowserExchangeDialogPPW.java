package com.jaydenho.androidtech.test;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2016/9/28.
 */
public class BrowserExchangeDialogPPW extends PopupWindow {

    private static final String TAG = BrowserExchangeDialogPPW.class.getSimpleName();
    private Context mContext;

    private TextView mContentTV = null;
    private String mContentText = null;

    public BrowserExchangeDialogPPW(Context context) {
        this.mContext = context;
        init();
    }

    public void setContentText(String contentText) {
        this.mContentText = contentText;
        mContentTV.setText(mContentText);
    }

    private void init() {
        LinearLayout parentView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.ppw_browser_exchange_dialog, null);
        initView(parentView);
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x00000000));
//            setAnimationStyle(R.style.AnimBottom);
        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setContentView(parentView);
    }

    private void initView(View parentView) {
        mContentTV = (TextView) parentView.findViewById(R.id.tv_content);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
