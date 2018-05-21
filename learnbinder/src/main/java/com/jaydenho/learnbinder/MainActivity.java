package com.jaydenho.learnbinder;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testSamsung();
    }

    public static ReportAPPPackageInfo[] getSelfInstalledAPPsPackageInfo(int i,Context context) {
        return getInstalledAPPsPackageInfo(i,context, new IFilter() {
            @Override
            public boolean filter(ApplicationInfo ai, PackageInfo info) {
                return !isSystemApp(ai) && !isAndroidSystemApp(info);
            }
        });
    }

    public static ReportAPPPackageInfo[] getPreInstalledAPPsPackageInfo(int i,Context context) {
        return getInstalledAPPsPackageInfo(i,context, new IFilter() {
            @Override
            public boolean filter(ApplicationInfo ai, PackageInfo info) {
                return isSystemApp(ai) && !isAndroidSystemApp(info);
            }
        });
    }

    public static boolean isAndroidSystemApp(PackageInfo info) {
        String regex = "^\\bcom.android.*$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(info.packageName);
        return matcher.matches();
    }

    public static boolean isSystemApp(@NonNull ApplicationInfo ai) {
        return (ai.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM
                || (ai.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
    }

    public static ReportAPPPackageInfo[] getInstalledAPPsPackageInfo(int i,Context context, IFilter filter) {
        PackageManager pm = context.getPackageManager();
        List<PackageInfo> originalList = pm.getInstalledPackages(0);
        Log.d(TAG, "getInstalledAPPsPackageInfo list: " + originalList.hashCode() + " i: " + i);
        //https://bugly.qq.com/v2/crash-reporting/crashes/900015673/18689023?pid=1
        //三星手机上，遍历已安装列表的时候，会报java.util.ConcurrentModificationException异常，应该是三星手机上的pm.getInstalledPackages()方法返回的List是同一个对象，然后系统还会往这个List中增删元素，所以将originalList拷贝一份，再遍历。
        List<PackageInfo> list;
        if (changeTypeByDays(true, new Boolean[]{true, false}))
            list = new ArrayList<>(originalList);
        else
            list = originalList;
        ReportAPPPackageInfo spiTmp[] = new ReportAPPPackageInfo[list.size()];
        Iterator<PackageInfo> it = list.iterator();
        int idx = 0;
        while (it.hasNext()) {
            PackageInfo info = it.next();
            try {
                ApplicationInfo ai = pm.getApplicationInfo(info.packageName, 0);
                if (filter.filter(ai, info)) {
                    spiTmp[idx] = new ReportAPPPackageInfo();
                    spiTmp[idx].packageName = info.packageName;
                    spiTmp[idx].displayName = pm
                            .getApplicationLabel(info.applicationInfo).toString();
                    spiTmp[idx].installer = pm.getInstallerPackageName(info.packageName);
                    spiTmp[idx].appInfo = ai;
                    spiTmp[idx].versionCode = info.versionCode;
                    spiTmp[idx].version = info.versionName;
                    spiTmp[idx].firstInstalled = info.firstInstallTime;
                    spiTmp[idx].lastUpdated = info.lastUpdateTime;
                    spiTmp[idx].uid = info.applicationInfo.uid;
                    spiTmp[idx].dataDir = info.applicationInfo.dataDir;
                    spiTmp[idx].targetsdk = ai.targetSdkVersion;
                    spiTmp[idx].systemAPP = (ai.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM;
                    spiTmp[idx].updateSystemAPP = (ai.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) == ApplicationInfo.FLAG_UPDATED_SYSTEM_APP;
                    spiTmp[idx].flags = ai.flags;
                    idx++;
                }
            } catch (PackageManager.NameNotFoundException exp) {
                Log.d(TAG, "exception: " + exp.getLocalizedMessage());
            }
        }
        // Reminder: the copying is necessary because we are filtering away the
        // system apps.
        ReportAPPPackageInfo spi[] = new ReportAPPPackageInfo[idx];
        System.arraycopy(spiTmp, 0, spi, 0, idx);
        return spi;
    }
    public interface IFilter {
        boolean filter(ApplicationInfo ai, PackageInfo info);
    }

    public void testSamsung(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportAPPPackageInfo[] installedAPPsPackageInfo = getPreInstalledAPPsPackageInfo(3,MainActivity.this);
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ReportAPPPackageInfo[] installedAPPsPackageInfo = getSelfInstalledAPPsPackageInfo(4,MainActivity.this);
                try {
                    Thread.sleep(1000);
                    getSelfInstalledAPPsPackageInfo(5,MainActivity.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        getPreInstalledAPPsPackageInfo(1,this);
        getSelfInstalledAPPsPackageInfo(2,this);
    }

    @NonNull
    public static <T> T changeTypeByDays(T defaultType, T[] values) {
        return changeTypeByTime("dd", defaultType, values);
    }

    @NonNull
    public static <T> T changeTypeByMonths(T defaultType, T[] values) {
        return changeTypeByTime("MM", defaultType, values);
    }

    @NonNull
    private static <T> T changeTypeByTime(String inFormat, T defaultType, T[] values) {
        T type;
        String time = DateFormat.format(inFormat, System.currentTimeMillis()).toString();
        int timeInt = Integer.valueOf(time);
        int x = timeInt % 10;
        if (x >= values.length) {
            x = 100;
        }
        switch (x) {
            case 0:
                type = values[0];
                break;
            case 1:
                type = values[1];
                break;
            case 2:
                type = values[2];
                break;
            case 3:
                type = values[3];
                break;
            case 4:
                type = values[4];
                break;
            case 5:
                type = values[5];
                break;
            case 6:
                type = values[6];
                break;
            case 7:
                type = values[7];
                break;
            case 8:
                type = values[8];
                break;
            case 9:
                type = values[9];
                break;
            default:
                type = defaultType;
        }
        return type;
    }
}
