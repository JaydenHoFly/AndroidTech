package com.jaydenho.androidtech.hack.vpn;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ApkInstaller {
    private static Set<String> a = new HashSet();

    private static Intent install(Uri paramUri) {
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setDataAndType(paramUri, "application/vnd.android.package-archive");
        return localIntent;
    }

    public static Intent install(Uri paramUri, boolean paramBoolean, Map<String, String> paramMap) {
        Intent localIntent1 = install(paramUri);
        if ((!paramBoolean) && (Build.MANUFACTURER.equalsIgnoreCase("oppo")) && (install(MobileTools.getInstance()))) {
            Intent localIntent2 = new Intent("oppo.intent.action.INSTALL_PACKAGE");
            localIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            localIntent1.putExtra("oppo_extra_pkg_name", "com.oppo.market");
            localIntent2.putExtra("android.intent.extra.INTENT", localIntent1);
            paramMap.put("ins_strategy", "1");
            return localIntent2;
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("huawei")) {
            if ((!paramBoolean)) {
                paramMap.put("ins_strategy", "1");
                localIntent1.putExtra("caller_package", "com.huawei.appmarket");
                return localIntent1;
            }
            localIntent1.putExtra("caller_package", "com.google.launcher");
        }
        return localIntent1;
    }

    public static void install(Context paramContext, String paramString, boolean paramBoolean) {
        if (paramString == null)
            return;
        try {
            Intent localIntent = new Intent("android.intent.action.DELETE", Uri.parse("package:" + paramString));
            localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            paramContext.startActivity(localIntent);
            return;
        } catch (ActivityNotFoundException localActivityNotFoundException) {
            localActivityNotFoundException.printStackTrace();
        }
    }

    public static void install(String paramString) {
        if ((Build.VERSION.SDK_INT >= 21) && ((Build.BRAND.equalsIgnoreCase("HUAWEI")) || (Build.BRAND.equalsIgnoreCase("HONOR")) || (Build.BRAND.equalsIgnoreCase("OPPO")) || (Build.BRAND.equalsIgnoreCase("vivo")))) {
            install(paramString, true);
            return;
        }
        install(paramString, false);
    }

    public static void install(String paramString, boolean paramBoolean) {
        install(paramString, paramBoolean, false);
    }

    public static void install(String apkFilePath, boolean openVpn, boolean paramBoolean2) {
        int i = 1;
        if (openVpn) {
            SgToolVpnClient.cacheInstallTask(apkFilePath);
            return;
        }
        Uri localUri = Uri.fromFile(new File(apkFilePath));
        HashMap localHashMap = new HashMap();
        localHashMap.put("ins_strategy", "0");
        Intent localIntent1 = install(localUri, paramBoolean2, localHashMap);
//        SgToolAccessibilityService.a.a().a(apkFilePath);
        try {
            MobileTools.getInstance().startActivity(localIntent1);
            /*PackageInfo localPackageInfo = MobileTools.getInstance().getPackageManager().getPackageArchiveInfo(apkFilePath, i);
            if (localPackageInfo != null)
                e(localPackageInfo.applicationInfo.packageName);*/
           /* if ((SettingManager.getRootQuickSetup(MobileTools.getInstance())) && (SettingManager.getAutoInstall(MobileTools.getInstance()))) {
                if (i == 0)
                    break label187;
                str = "1";
                localHashMap.put("acc", str);
                a.a("sys_setup_start", localHashMap);
            }*/
        } catch (Exception localException1) {
          /*  while (true) {
                localHashMap.put("ins_strategy", "0");
                Intent localIntent2 = install(localUri);
                try {
                    MobileTools.getInstance().startActivity(localIntent2);
                } catch (Exception localException2) {
                }
                continue;
                i = 0;
                continue;
                label187:
                String str = "0";
            }*/
        }
    }

    public static boolean install() {
        File localFile1 = new File("/system/app/Superuser.apk");
        File localFile2 = new File("/system/app/superuser.apk");
        File localFile3 = new File("/system/app/su360.apk");
        return (localFile1.exists()) || (localFile2.exists()) || (localFile3.exists());
    }

    private static boolean install(Context paramContext) {
//        return paramContext.getPackageManager().queryIntentActivities(new Intent("oppo.intent.action.INSTALL_PACKAGE"), 65536).size() > 0;
        return true;
    }

    public static long b(String paramString) {
//        return 40000L + 1000L * () Math.sqrt(new File(paramString).length() / 1000000L);
        return 0;
    }

    public static boolean b() {
        if (Build.BRAND == null)
            return false;
        return Build.BRAND.equalsIgnoreCase("Xiaomi");
    }

    public static boolean c() {
        String str = System.getenv("PATH");
        new ArrayList();
        String[] arrayOfString = str.split(":");
        for (int i = 0; ; i++) {
            int j = arrayOfString.length;
            boolean bool = false;
            if (i < j) {
                if (new File(arrayOfString[i] + "/su").exists())
                    bool = true;
            } else
                return bool;
        }
    }

    public static boolean c(String paramString) {
        synchronized (a) {
            boolean bool = a.remove(paramString);
            return bool;
        }
    }

    public static boolean d(String paramString) {
/*        if (TextUtils.isEmpty(paramString)) ;
        while (true) {
            return false;
            try {
                PackageInfo localPackageInfo2 = MobileTools.getInstance().getPackageManager().getPackageInfo(paramString, 0);
                localPackageInfo1 = localPackageInfo2;
                if (localPackageInfo1 == null)
                    continue;
                return true;
            } catch (Exception localException) {
                while (true)
                    PackageInfo localPackageInfo1 = null;
            }
        }*/
        return true;
    }

    private static void e(String paramString) {
        synchronized (a) {
            a.add(paramString);
            return;
        }
    }
}