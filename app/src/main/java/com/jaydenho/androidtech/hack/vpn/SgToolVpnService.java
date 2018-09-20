package com.jaydenho.androidtech.hack.vpn;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.VpnService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;

import java.io.IOException;

/**
 * Created by hedazhao on 2018/5/21.
 */
public class SgToolVpnService extends VpnService {

    private static final String TAG = "vpn.SgToolVpnService";

    private PendingIntent mPendingIntent;
    private ParcelFileDescriptor mFileDescriptor;
    private long c;

    public SgToolVpnService() {
        this.mPendingIntent = null;
        this.mFileDescriptor = null;
        this.c = 0L;
    }

    @TargetApi(21)
    private void createVpn() {
        if (this.mFileDescriptor == null) {
            final VpnService.Builder vpnService$Builder = new VpnService.Builder();
            vpnService$Builder.setMtu(1500);
            vpnService$Builder.addAddress("10.0.2.0", 32);
            vpnService$Builder.addRoute("0.0.0.0", 0);
            try {
                vpnService$Builder.addAllowedApplication("com.android.packageinstaller");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            try {
                vpnService$Builder.addAllowedApplication("com.google.android.packageinstaller");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (Build.BRAND.equals("vivo")) {
                try {
                    vpnService$Builder.addAllowedApplication("com.bbk.appstore");
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
            try {
                vpnService$Builder.addAllowedApplication("com.xunlei.downloadprovider");
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            this.mFileDescriptor = vpnService$Builder.setSession("VPNServiceDemo").setConfigureIntent(this.mPendingIntent).establish();
        }
    }

    public void onCreate() {
        super.onCreate();
        this.c = System.currentTimeMillis();
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.mFileDescriptor == null) {
            return;
        }
        try {
            this.mFileDescriptor.close();
            this.mFileDescriptor = null;
        } catch (IOException ex) {
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        try {
            this.createVpn();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return START_NOT_STICKY;
       /* while (true) {
            if (intent.hasExtra("stop_service")) {
                try {
                    if (mFileDescriptor != null) {
                        this.mFileDescriptor.close();
                    }
                    this.mFileDescriptor = null;
                    this.stopSelf();
//                    final HashMap<String, String> hashMap = new HashMap<String, String>();
//                    hashMap.put("timeout", String.valueOf((long) (0.5 + (System.currentTimeMillis() - this.c) / 1000.0)));
//                    com.sogou.pingbacktool.a.a("vpn_service", hashMap);
                    new Handler(Looper.getMainLooper()).postDelayed((Runnable) new Runnable() {
                        @Override
                        public void run() {
                            System.exit(0);
                        }
                    }, 100L);
                    return START_NOT_STICKY;
                } catch (IOException ex2) {
                    ex2.printStackTrace();
                    continue;
                }
            } else {
                if (mFileDescriptor == null) {
                    try {
                        this.createVpn();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    return START_NOT_STICKY;
                }
            }
        }*/
    }
}
