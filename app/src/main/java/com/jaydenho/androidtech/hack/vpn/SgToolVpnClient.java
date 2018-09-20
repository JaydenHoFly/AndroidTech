package com.jaydenho.androidtech.hack.vpn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.VpnService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hedazhao on 2018/9/14.
 */
public class SgToolVpnClient extends Activity {
    private static final String FILE_NAME = "file_name";
    private static final long INTERNAL_TIME = 259200000L;
    private static final int MSG_INSTALL_TASK_PROCESSED = 11;
    private static final int MSG_NEW_INSTALL_TASK = 10;
    private static final int MSG_SETUP_APK = 21;
    private static final int MSG_STOP_VPN = 20;
    private static final String VPN_INTENT = "vpn_intent";
    private static List<String> sFileNames;
    public static Handler sHandler;
    private static long sLastInstallTime;
    private static int sVpnDialogTimes;
    private String mFileName;
    private Intent mIntent4Vpn;

    static {
        SgToolVpnClient.sVpnDialogTimes = 0;
        SgToolVpnClient.sLastInstallTime = 0L;
        SgToolVpnClient.sFileNames = new LinkedList<String>();
        SgToolVpnClient.sHandler = new Handler(Looper.getMainLooper()) {
            public void handleMessage(final Message message) {
                super.handleMessage(message);
                switch (message.what) {
                    case 10: {
                        if (!SgToolVpnClient.sFileNames.isEmpty()) {
                            SgToolVpnClient.sFileNames.add((String) message.obj);
                            return;
                        }
                        installAnApk((String) message.obj);
                    }
                    case 11: {
                        SgToolVpnClient.sFileNames.remove(message.obj);
                        if (!SgToolVpnClient.sFileNames.isEmpty()) {
                            installAnApk(SgToolVpnClient.sFileNames.get(0));
                            return;
                        }
                        break;
                    }
                    case 20: {
                        final Intent intent = new Intent(MobileTools.getInstance(), (Class) SgToolVpnService.class);
                        intent.putExtra("stop_service", 123);
                        MobileTools.getInstance().startService(intent);
                    }
                    case 21: {
                        ApkInstaller.install((String) message.obj, false, true);
                        SgToolVpnClient.sHandler.sendMessageDelayed(SgToolVpnClient.sHandler.obtainMessage(11, message.obj), 500L);
                        SgToolVpnClient.sLastInstallTime = System.currentTimeMillis();
                    }
                }
            }
        };
    }

    public SgToolVpnClient() {
        this.mIntent4Vpn = null;
        this.mFileName = null;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().addFlags(16);
        this.handleIntent(this.getIntent());
        this.startActivityForResult(this.mIntent4Vpn, 0);
    /*    final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("action", "show");
        a.a("vpn_service", hashMap);*/
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        if (intent != null && intent.hasExtra("file_name")) {
            this.mFileName = intent.getStringExtra("file_name");
            if (!SgToolVpnClient.sFileNames.contains(this.mFileName)) {
                SgToolVpnClient.sFileNames.add(this.mFileName);
            }
        }
    }

    public static void cacheInstallTask(final String s) {
        SgToolVpnClient.sHandler.sendMessage(SgToolVpnClient.sHandler.obtainMessage(10, (Object) s));
    }

    public static boolean checkShowTime(final Context context) {
//        return PreferenceUtil.getLastVpnShowTime(context) < System.currentTimeMillis() - 259200000L;
        return true;
    }

    private void handleIntent(final Intent intent) {
        if (intent != null) {
            if (intent.hasExtra("file_name")) {
                this.mFileName = intent.getStringExtra("file_name");
                if (!SgToolVpnClient.sFileNames.contains(this.mFileName)) {
                    SgToolVpnClient.sFileNames.add(this.mFileName);
                }
            }
            if (intent.hasExtra("vpn_intent")) {
                this.mIntent4Vpn = (Intent) intent.getParcelableExtra("vpn_intent");
            }
        }
    }

    @TargetApi(14)
    private static void installAnApk(final String s) {
        final Intent prepare = VpnService.prepare(MobileTools.getInstance());
        if (prepare == null) {
            openVpnAndInstallApk(-1, s);
            return;
        }
        if(true){
//        if (SgToolVpnClient.sVpnDialogTimes < 2 && System.currentTimeMillis() - SgToolVpnClient.sLastInstallTime > 1000L && (PreferenceUtil.getRequestOpenVpnTimes(MobileTools.getInstance(), 0) < 5 || checkShowTime(MobileTools.getInstance()))) {
            final Intent intent = new Intent(MobileTools.getInstance(), (Class) SgToolVpnClient.class);
            intent.putExtra("file_name", s);
            intent.putExtra("vpn_intent", (Parcelable) prepare);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MobileTools.getInstance().startActivity(intent);
            return;
        }
        openVpnAndInstallApk(0, s);
    }

    private static void openVpnAndInstallApk(final int n, final String s) {
        if (n == -1) {
            final Intent intent = new Intent(MobileTools.getInstance(), (Class) SgToolVpnService.class);
            SgToolVpnClient.sHandler.removeMessages(20);
            SgToolVpnClient.sHandler.sendMessageDelayed(SgToolVpnClient.sHandler.obtainMessage(20), 20000L);
            MobileTools.getInstance().startService(intent);
            SgToolVpnClient.sHandler.sendMessageDelayed(SgToolVpnClient.sHandler.obtainMessage(21, (Object) s), 100L);
            return;
        }
        ApkInstaller.install(s, false);
        SgToolVpnClient.sHandler.sendMessageDelayed(SgToolVpnClient.sHandler.obtainMessage(11, (Object) s), 500L);
        SgToolVpnClient.sLastInstallTime = System.currentTimeMillis();
    }

    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        ++SgToolVpnClient.sVpnDialogTimes;
        if (n2 == -1) {
//            PreferenceUtil.setRequestOpenVpnTimes((Context)this, 0);
        } else {
    /*        PreferenceUtil.setLastVpnShowTime((Context)this, System.currentTimeMillis());
            final int requestOpenVpnTimes = PreferenceUtil.getRequestOpenVpnTimes((Context)this, 0);
            if (requestOpenVpnTimes >= 5) {
                PreferenceUtil.setRequestOpenVpnTimes((Context)this, 1);
            }
            else {
                PreferenceUtil.setRequestOpenVpnTimes((Context)this, requestOpenVpnTimes + 1);
            }
        }*/
            openVpnAndInstallApk(n2, this.mFileName);
            this.finish();
            final HashMap<String, String> hashMap = new HashMap<String, String>();
            String s;
            if (n2 == -1) {
                s = "confirm";
            } else {
                s = "cancel";
            }
            hashMap.put("action", s);
//        a.a("vpn_service", hashMap);
        }


    }

}