package com.jaydenho.androidtech.plugin.droidplugin.hookclassloader.muticlassloader.packageparser;

import android.app.Application;
import android.content.pm.ApplicationInfo;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by hedazhao on 2017/4/30.
 */
public class PackageParserApi23 {

    /**
     * @param apkFile apk file
     * @return
     */
    public static ApplicationInfo generateApplicationInfo(File apkFile) {
        try {
            Class<?> packageParserClass = Class.forName("android.content.pm.PackageParser");
            Object packageParser = packageParserClass.newInstance();

            //gen android.content.pm.PackageParser$Package
            Method parsePackageMethod = packageParserClass.getDeclaredMethod("parsePackage", File.class, int.class);
            parsePackageMethod.setAccessible(true);
            Object parsedPackage = parsePackageMethod.invoke(packageParser, apkFile, 0);

            //gen applicationInfo

            // 第三个参数 mDefaultPackageUserState 我们直接使用默认构造函数构造一个出来即可
            Class<?> packageUserStateClass = Class.forName("android.content.pm.PackageUserState");
            Object defaultPackageUserState = packageUserStateClass.newInstance();

            Method generateApplicationInfoMethod = packageParserClass.getDeclaredMethod("generateApplicationInfo", parsedPackage.getClass(), int.class, packageUserStateClass);
            generateApplicationInfoMethod.setAccessible(true);
            ApplicationInfo applicationInfo = (ApplicationInfo)  generateApplicationInfoMethod.invoke(packageParser, parsedPackage, 0, defaultPackageUserState);

            String apkPath = apkFile.getPath();

            applicationInfo.sourceDir = apkPath;
            applicationInfo.publicSourceDir = apkPath;

            return applicationInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
