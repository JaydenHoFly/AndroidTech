package com.jaydenho.androidtech.plugin.droidplugin.hookclassloader.muticlassloader;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.ArrayMap;

import com.jaydenho.androidtech.plugin.droidplugin.Utils;
import com.jaydenho.androidtech.plugin.droidplugin.hookams.AMSHookHelper;
import com.jaydenho.androidtech.plugin.droidplugin.hookclassloader.muticlassloader.packageparser.PackageParserApi23;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hedazhao on 2017/4/30.
 * 用于生成插件的LoadedApk
 */
public class LoadedApkClassLoaderHookHelper {

    private static Map<String, Object> sLoadedApks = new HashMap<>();

    /**
     * @param apkFile apk file
     * @return LoadedApk
     */
    public static Object generateLoadedApk(@NonNull ApplicationInfo applicationInfo, File apkFile, Object activityThread) {
        try {
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");

            Class<?> compatibilityInfoClass = Class.forName("android.content.res.CompatibilityInfo");
            Field defaultCompatibilityInfoField = compatibilityInfoClass.getDeclaredField("DEFAULT_COMPATIBILITY_INFO");
            defaultCompatibilityInfoField.setAccessible(true);
            Object defaultCompatibilityInfo = defaultCompatibilityInfoField.get(null);

            Method getPackageInfoNoCheckMethod = activityThreadClass.getDeclaredMethod("getPackageInfoNoCheck", applicationInfo.getClass(), compatibilityInfoClass);
            getPackageInfoNoCheckMethod.setAccessible(true);

            return getPackageInfoNoCheckMethod.invoke(activityThread, applicationInfo, defaultCompatibilityInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将可以解析插件apk的ClassLoader插入ActivityThread#mPackage中
     *
     * @param apkFile apk file
     */
    public static void hookLoadedApkInActivityThread(File apkFile) {
        try {
            ApplicationInfo applicationInfo = null;
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
                applicationInfo = PackageParserApi23.generateApplicationInfo(apkFile);
            }
            if (applicationInfo != null) {
                ClassLoader pluginClassLoader = new PluginClassLoader(apkFile.getPath(), Utils.getPluginOptDexDir(applicationInfo.packageName).getPath(), Utils.getPluginLibDir(applicationInfo.packageName).getPath(), ClassLoader.getSystemClassLoader());

                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Object activityThread = AMSHookHelper.getActivityThread(activityThreadClass);

                Object loadedApk = generateLoadedApk(applicationInfo, apkFile, activityThread);
                if (loadedApk != null) {
                    Class loadedApkClass = Class.forName("android.app.LoadedApk");
                    Field classLoaderField = loadedApkClass.getDeclaredField("mBaseClassLoader");
                    classLoaderField.setAccessible(true);
                    classLoaderField.set(loadedApk, pluginClassLoader);

                    Field mPackagesField = activityThreadClass.getDeclaredField("mPackages");
                    mPackagesField.setAccessible(true);
                    ArrayMap<String, WeakReference<Object>> mPackages = (ArrayMap<String, WeakReference<Object>>) mPackagesField.get(activityThread);

                    // 由于是弱引用, 因此我们必须在某个地方存一份, 不然容易被GC; 那么就前功尽弃了.
                    sLoadedApks.put(applicationInfo.packageName, loadedApk);

                    WeakReference loadedApkWR = new WeakReference(loadedApk);
                    mPackages.put(applicationInfo.packageName, loadedApkWR);

                    mPackagesField.set(activityThread, mPackages);
                } else {
                    throw new RuntimeException("PluginLoadedApkGenerator#generateLoadedApk, LoadedApk is null!");
                }
            } else {
                throw new RuntimeException("PluginLoadedApkGenerator#generateLoadedApk, applicationInfo is null!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
