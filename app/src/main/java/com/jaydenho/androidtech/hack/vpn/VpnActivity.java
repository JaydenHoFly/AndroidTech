package com.jaydenho.androidtech.hack.vpn;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.VpnService;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jaydenho.androidtech.BaseActivity;

/**
 * Created by hedazhao on 2018/5/21.
 */
public class VpnActivity extends BaseActivity {
    private static final String TAG = "vpn.VpnActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startVpnPermissionIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            Log.d(TAG, "vpn permission result. resultCode: " + resultCode);
            if (resultCode == RESULT_OK) {
                startVpnService();
            }
        }
    }

    private void startVpnService() {
        Intent intent = new Intent("jayden.example.VpnService");
        intent.setPackage("com.jaydenho");
        startService(intent);
    }

    private void startVpnPermissionIntent() {
        Intent vpnIntent = VpnService.prepare(this);
        if (vpnIntent == null) {
            Log.d(TAG, "vpnIntent is null");
            startVpnService();
            return;
        }
        startActivityForResult(vpnIntent, 1000);
    }
}
