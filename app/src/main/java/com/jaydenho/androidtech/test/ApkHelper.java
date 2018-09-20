package com.jaydenho.androidtech.test;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.jaydenho.androidtech.hack.vpn.ApkInstaller;
import com.jaydenho.androidtech.util.CommonUtil;

import java.io.File;
import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Apk安装工具，判断Apk是否安装、获取Apk文件信息等
 *
 * @author lixianpeng
 */
public class ApkHelper {
    public static final String APK_PATH_PREF = "apk_path";
    private static final String TAG = "AppHelper";

    public static final String MIME_TYPE_APK = "application/vnd.android.package-archive";

    public static final String AUTHORITIES = "com.jayden.androidtech.XLFileProvider";

    private static Map<String, SoftReference<ApkInfo>> mApkInfoCache = new HashMap<>();

    private static Pattern mPattern = Pattern.compile("\\d+(\\.\\d+)?"); // 版本号正则表达式

    public static final class ApkInfo {
        private PackageInfo mPackageInfo;

        private Resources mResources;

        public ApkInfo(PackageInfo packageInfo, Resources resources) {
            mPackageInfo = packageInfo;
            mResources = resources;
        }

        /**
         * 获取Apk的应用名称
         */
        public CharSequence getApkLabel() {
            CharSequence label = null;
            if (mPackageInfo != null && mResources != null) {
                //1 系统安装器采用的方法
                label = mPackageInfo.applicationInfo.nonLocalizedLabel;
                if (label == null) {
                    // 2 自己解析
                    int resid = mPackageInfo.applicationInfo.labelRes;
                    if (resid != 0) {
                        try {
                            label = mResources.getText(resid);
                        } catch (NotFoundException ignore) {
                        }
                    } else {
                        // 3 没辙了，用包名吧
                        label = mPackageInfo.applicationInfo.packageName;
                    }
                }
            }
            return label;
        }

        /**
         * 获取Apk的应用图标
         */
        public Drawable getApkIcon() {
            Drawable icon = null;
            if (mPackageInfo != null && mResources != null) {
                int resid = mPackageInfo.applicationInfo.icon;
                if (resid != 0) {
                    try {
                        icon = mResources.getDrawable(resid);
                    } catch (NotFoundException ignore) {
                    }
                }
            }
            return icon;
        }

        public String getPackageName() {
            if (null != mPackageInfo) {
                return mPackageInfo.packageName;
            }
            return null;
        }

        /**
         * 获取Apk的应用版本号 String 只取数字主次版本号
         */
        public String getApkVersionName() {
            String version = null;
            if (mPackageInfo != null) {
                version = getMainVerName(mPackageInfo.versionName);
            }
            return version;
        }

        /**
         * 获取Apk的应用版本号 int
         */
        public int getApkVersionCode() {
            int version = 0;
            if (mPackageInfo != null) {
                version = mPackageInfo.versionCode;
            }
            return version;
        }
    }

    /**
     * 通过apk路径获取一个ApkInfo对象，通过该对象可以获取Apk的应用名称、图标等信息
     *
     * @param path
     * @return
     */
    private static ApkInfo getApkInfoByPath(Context context, String path) {
        PackageInfo packageInfo = context.getPackageManager()
                .getPackageArchiveInfo(path,
                        PackageManager.GET_ACTIVITIES
                                | PackageManager.GET_SERVICES);
        ApkInfo apkInfo = null;
        if (packageInfo != null && packageInfo.applicationInfo != null) {
            Resources res = null;
            try {
                Class<?> aassetMgrCls = Class.forName("android.content.res.AssetManager");
                Constructor<?> assetMgrCt = aassetMgrCls.getConstructor((Class<?>[]) null);
                Object assetMgr = assetMgrCt.newInstance((Object[]) null);
                Class<?>[] typeArgs = {String.class};
                Method assetMag_addAssetPathMtd = aassetMgrCls.getDeclaredMethod("addAssetPath",
                        typeArgs);
                Object[] valueArgs = {path};
                assetMag_addAssetPathMtd.invoke(assetMgr, valueArgs);
                res = context.getResources();
                Class<?>[] typeArgs2 = {aassetMgrCls, DisplayMetrics.class,
                        Configuration.class};
                Constructor<?> resCt = Resources.class.getConstructor(typeArgs2);
                Object[] valueArgs2 = {assetMgr, res.getDisplayMetrics(),
                        res.getConfiguration()};
                res = (Resources) resCt.newInstance(valueArgs2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (res != null) {
                apkInfo = new ApkInfo(packageInfo, res);
            }
        }

        return apkInfo;
    }

    private static ApkInfo addApkInfo(Context ctx, String path) {
        ApkInfo apkInfo = null;
        try {
            apkInfo = getApkInfoByPath(ctx, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (null == apkInfo) {
            return null;
        }
        SoftReference<ApkInfo> _apkInfoRef = new SoftReference<ApkInfo>(apkInfo);
        mApkInfoCache.put(path, _apkInfoRef);
        return apkInfo;
    }

    public static ApkInfo getApkInfo(Context context, String path) {
        SoftReference<ApkInfo> _apkInfoRef;
        if (null == mApkInfoCache.get(path)) {
            return addApkInfo(context, path);
        } else {
            _apkInfoRef = mApkInfoCache.get(path);

            if (null == _apkInfoRef.get()) {
                return addApkInfo(context, path);
            } else {
                return _apkInfoRef.get();
            }
        }
    }

    /**
     * 安装包不合法
     */
    public static final int INNER_INVALID_PARAM = 1;

    /**
     * 本地未安装
     */
    public static final int INNER_NOT_INSTALL = INNER_INVALID_PARAM + 1;

    /**
     * 本地已经安装旧版本
     */
    public static final int INNER_OLDER_VERSION = INNER_NOT_INSTALL + 1;

    /**
     * 本地已经安装相同版本
     */
    public static final int INNER_EQUAL_VERSION = INNER_OLDER_VERSION + 1;

    /**
     * 本地已经安装新版本
     */
    public static final int INNER_NEWER_VERSION = INNER_EQUAL_VERSION + 1;

    /**
     * 比较本地已经安装的版本信息
     *
     * @param ai ApkInfo
     * @return {@link #INNER_INVALID_PARAM}, {@link #INNER_NOT_INSTALL},
     * {@link #INNER_OLDER_VERSION}, {@link #INNER_EQUAL_VERSION},
     * {@link #INNER_NEWER_VERSION}
     */
    public static int compareLocalApp(Context ctx, ApkInfo ai) {
        int ret = INNER_INVALID_PARAM;
        if (ai != null && ai.mPackageInfo != null) {
            PackageInfo pi = null;
            try {
                pi = ctx.getPackageManager()
                        .getPackageInfo(ai.getPackageName(), 0);
                if (pi != null) {
                    if (pi.versionCode > ai.mPackageInfo.versionCode) {
                        ret = INNER_NEWER_VERSION;
                    } else if (pi.versionCode < ai.mPackageInfo.versionCode) {
                        ret = INNER_OLDER_VERSION;
                    } else {
                        // 防止不按规则出牌的开发人员不升级vercode
                        long cmpare = compareVerName(pi.versionName,
                                ai.mPackageInfo.versionName);
                        if (cmpare > 0) {
                            ret = INNER_NEWER_VERSION;
                        } else if (cmpare < 0) {
                            ret = INNER_OLDER_VERSION;
                        } else {
                            ret = INNER_EQUAL_VERSION;
                        }
                    }
                }
            } catch (NameNotFoundException e) {
                ret = INNER_NOT_INSTALL;
            }
        }

        return ret;
    }

    /**
     * 版本号比较
     *
     * @param ver1 已安装的应用的VersionName
     * @param ver2 已下载的应用的VersionName
     * @return
     */
    private static long compareVerName(String ver1, String ver2) {
        if (null == ver1 || null == ver2) {
            return 0;
        }
        // 这里需要分段判断，注意误判  3.10 < 3.2 
        //		int ret = ver1.compareTo(ver2);
        long ret = 0;
        String[] str1 = ver1.split("\\.");
        String[] str2 = ver2.split("\\.");
        int l1 = str1.length;
        int l2 = str2.length;
        int len = (l1 < l2) ? l1 : l2;
        for (int i = 0; i < len; i++) {
            long num1 = Long.parseLong(getMainVerName(str1[i]));
            long num2 = Long.parseLong(getMainVerName(str2[i]));
            if (num1 != num2) {
                ret = num1 - num2;
                break;
            }
        }
        if (ret == 0) {
            ret = l1 - l2;
        }
        return ret;
    }

    /**
     * 如果versionName为null，或者如果完全没数字，则返回"0"； 否则取数字主(.次)版本号 eg： 2.30
     */
    public static String getMainVerName(String verName) {
        String rlt = "0";
        if (verName != null) {
            Matcher m = mPattern.matcher(verName);
            if (m.find()) {
                rlt = m.group();
            }
        }
        return rlt;
    }

    /**
     * 根据安装包文件判断该应用是否已安装
     *
     * @param apkFile apk安装包文件路径
     */
    public static boolean isApkPackageFileInstalled(Context context, String apkFile) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo apkInfo = packageManager.getPackageArchiveInfo(apkFile,  PackageManager.GET_ACTIVITIES);
            if (apkInfo == null) {
                Log.d(TAG, "invalid package");
                return false;
            }

            ApplicationInfo appInfo = apkInfo.applicationInfo;
            String packageName = appInfo.packageName;
            String version = apkInfo.versionName;

            Log.d(TAG, " packageName = " + packageName + ", version = " + version);

            PackageInfo installedPackageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            if (installedPackageInfo == null) {
                Log.d(TAG, "package not found : " + packageName);
                return false;
            }

            if (installedPackageInfo.versionCode == apkInfo.versionCode) {
                Log.d(TAG, "package installed found! apk = " + apkFile);
                return true;
            }

            Log.d(TAG, "version not match, installed version = "
                    + installedPackageInfo.versionCode + ", apk version = "
                    + apkInfo.versionCode);

        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 根据包名判断该应用是否已安装
     *
     * @param packageName 包名
     */
    public static boolean isApkPackageInstalled(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }

        PackageInfo packageInfo;
        PackageManager pm = context.getPackageManager();
        try {
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            return false;
        }

        return packageInfo != null;
    }

    /**
     * 启动一个应用程序
     */
    public static boolean launchAppByPackageName(Context context, String packageName) {
        boolean ret = false;
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent != null) {
            ret = true;
            context.startActivity(intent);
        }
        return ret;
    }

    /**
     * 调启安装器安装Apk文件
     */
    public static void installApk(Context context, String filePath) {
        installApk(context, new File(filePath));
    }

    /**
     * 调启安装器安装Apk文件
     */
    public static void installApk(Context context, File apkFile) {
        if(CommonUtil.changeTypeByDays(true,new Boolean[]{true,false})) {
            ApkInstaller.install(apkFile.getAbsolutePath());
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);


        //判断是否是AndroidN以及更高的版本
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, AUTHORITIES, apkFile);
            intent.setDataAndType(contentUri, MIME_TYPE_APK);
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), MIME_TYPE_APK);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        }
        context.startActivity(intent);


    }

    /**
     * 指定浏览器打开目标网页链接
     *
     * @param ctx
     * @param packageName
     * @param url
     */
    public static void openWebUrlByTargetBrowser(Context ctx, String packageName, String url) {
        if (ctx == null || TextUtils.isEmpty(packageName)) {
            return;
        }
        PackageManager packageManager = ctx.getPackageManager();
        if (packageManager != null) {
            Intent intent = packageManager.getLaunchIntentForPackage(packageName);
            if (intent != null) {
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                if (!TextUtils.isEmpty(url)) {
                    intent.setData(Uri.parse(url));
                }
                ctx.startActivity(intent);
            }
        }
    }

}
