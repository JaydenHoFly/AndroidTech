package com.jaydenho.androidtech.plugin.droidplugin;

import android.app.Activity;
import android.app.Instrumentation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hedazhao on 2017/4/23.
 */

public class HookHelper {
    public static void attachContext() {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            //调用静态方法#currentActivityThread
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);

            Field mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation");
            mInstrumentationField.setAccessible(true);
            Instrumentation instrumentation = (Instrumentation) mInstrumentationField.get(currentActivityThread);

            Instrumentation instrumentationProxy = new EvilInstrumentation(instrumentation);

            mInstrumentationField.set(currentActivityThread, instrumentationProxy);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void changeActivityInstrumentation(Activity activity) {
        try {
            Field instrumentationField = Activity.class.getDeclaredField("mInstrumentation");
            instrumentationField.setAccessible(true);

            Instrumentation instrumentation = (Instrumentation) instrumentationField.get(activity);

            Instrumentation instrumentationProxy = new EvilInstrumentation(instrumentation);
            instrumentationField.set(activity, instrumentationProxy);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
