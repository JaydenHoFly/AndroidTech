package com.jaydenho.androidtech.others;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.AlarmManagerCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static android.app.AlarmManager.RTC_WAKEUP;

/**
 * Created by hedazhao on 2018/9/21.
 */
public class LearnAlarmManager {
    private static final String TAG = "LearnAlarmManager";
    private Context mContext;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Context context) {
        mContext = context;
        Log.d(TAG, "startAlarm2.");
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            Log.d(TAG, "startAlarm.");
            Intent intent = new Intent("com.jaydenho.androidtech.learmalarmreceiver");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(2018, 9, 21, 18, 20);
            alarmManager.setRepeating(RTC_WAKEUP, calendar.getTimeInMillis(), TimeUnit.SECONDS.toMillis(1), pendingIntent);
        }
    }
}
