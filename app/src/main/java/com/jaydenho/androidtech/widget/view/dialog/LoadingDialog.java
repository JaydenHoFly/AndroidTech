package com.jaydenho.androidtech.widget.view.dialog;

import android.app.AlertDialog;
import android.content.Context;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/12/27.
 */

public class LoadingDialog extends AlertDialog {
    public LoadingDialog(Context context) {
        super(context);
        setContentView(R.layout.loading_dialog);
    }
}
