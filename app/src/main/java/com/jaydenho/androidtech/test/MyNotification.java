package com.jaydenho.androidtech.test;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.jaydenho.androidtech.R;

/**
 * Created by hedazhao on 2017/11/6.
 */

public class MyNotification {

    @SuppressLint("NewApi")
    public static void notifyTaskNotification(Context context, String title, String desc, String type, long taskId) {
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = getCommonNotification(context, title, desc, Notification.DEFAULT_SOUND);
        mNotificationManager.notify(11, notification);
    }

    public static Notification getCommonNotification(Context context, String title, String desc, int nfDefaults) {
        Notification notification = new NotificationCompat.Builder(context)
                .setAutoCancel(true)
                .setTicker(title)
                .setSmallIcon(R.drawable.avatar_layer_list)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setDefaults(nfDefaults)
                .setNumber(0)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setContentTitle(title)
                .setContentText(desc)
                .build();
        return notification;
    }
}
