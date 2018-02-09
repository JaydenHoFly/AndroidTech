package com.jaydenho.androidtech.widget.view.window;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hedazhao on 2018/2/9.
 */

public class ToastIntentService extends IntentService {

    public ToastIntentService() {
        super("toastIntentService");

    }
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public ToastIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("ToastIntentService","onHandleIntent");
        Toast.makeText(this, "intent service", Toast.LENGTH_SHORT).show();
    }
}
