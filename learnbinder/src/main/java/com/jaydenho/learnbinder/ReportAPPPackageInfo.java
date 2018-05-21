package com.jaydenho.learnbinder;

import android.content.pm.ApplicationInfo;

public class ReportAPPPackageInfo {

    public String packageName;
    public String displayName;
    public String installer;
    public String tags;
    public boolean selected;
    public int versionCode;
    public String version;
    public long firstInstalled;
    public long lastUpdated;
    public int uid;
    public int rating;
    public String dataDir;
    public String comment;
    public int category;
    public int targetsdk;
    public ApplicationInfo appInfo;
    public boolean systemAPP;
    public boolean updateSystemAPP;
    public int flags;

    public ReportAPPPackageInfo() {
    }


    @Override
    public String toString() {
        return "SortablePackageInfo{" +
                "packageName='" + packageName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", installer='" + installer + '\'' +
                ", tags='" + tags + '\'' +
                ", selected=" + selected +
                ", versionCode=" + versionCode +
                ", version='" + version + '\'' +
                ", firstInstalled=" + firstInstalled +
                ", lastUpdated=" + lastUpdated +
                ", uid=" + uid +
                ", rating=" + rating +
                ", dataDir='" + dataDir + '\'' +
                ", comment='" + comment + '\'' +
                ", category=" + category +
                ", targetsdk=" + targetsdk +
                ", appInfo=" + appInfo +
                ", systemAPP=" + systemAPP +
                ", updateSystemAPP=" + updateSystemAPP +
                ", flags=" + flags +
                '}';
    }
}
