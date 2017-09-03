package com.jaydenho.androidtech.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import java.util.List;

/**
 * 参考：git@github.com:mrmans0n/smart-location-lib.git
 */
public class LocationProvider implements LocationListener,
        PermissionManager.PermissionListener {

    public static final int ERROR_CODE_PERMISSION_DENY = 1000;
    public static final int ERROR_CODE_PROVIDER_UNABLE = 1001;
    public static final int ERROR_CODE_PERMISSION_REQUEST_REJECT = 1002;
    private static final String TAG = LocationProvider.class.getSimpleName();
    private LocationManager mLocationManager = null;
    private Activity mActivity = null;
    private SimpleLocationListener mSimpleLocationListener = null;

    /**
     * 只使用精准定位
     */
    private static final String[] LOCATION_PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};

    /**
     * @param simpleLocationListener 不一定有回调，oppo r9s拒绝权限后，没有任何回调
     */
    public void setSimpleLocationListener(SimpleLocationListener simpleLocationListener) {
        mSimpleLocationListener = simpleLocationListener;
    }

    public LocationProvider(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    @Override
    public void onPermissionsGranted(List<String> perms) {
        Log.d(TAG, "onPermissionsGranted");
        for (String permission : LOCATION_PERMISSIONS) {
            if (!perms.contains(permission)) {
                locationPermissionGranted(false);
                break;
            }
        }
        locationPermissionGranted(true);
    }

    @Override
    public void onPermissionsDenied(List<String> perms) {
        onLocationFailCallback(ERROR_CODE_PERMISSION_DENY, "onPermissionsDenied");
    }

    @Override
    public void onPermissionRequestRejected() {
        onLocationFailCallback(ERROR_CODE_PERMISSION_REQUEST_REJECT, "onPermissionRequestRejected");
    }

    public interface SimpleLocationListener {
        void onLocationChanged(Location location);

        void onLocationFail(int errorCode, String errorMsg);
    }

    private void requestLocationPermission() {
        Log.d(TAG, "start requestLocationPermission");
        PermissionManager.requestPermissions(mActivity, this, "申请定位权限", LOCATION_PERMISSIONS);
    }

    /**
     * 获取一次定位信息
     */
    public void start(boolean askForPermission) {
        /*
          进行定位
          provider:用于定位的locationProvider字符串
          minTime:时间更新间隔，单位：ms
          minDistance:位置刷新距离，单位：m
          listener:用于定位更新的监听者locationListener
         */
        if (!PermissionManager.hasPermissions(mActivity, LOCATION_PERMISSIONS)) {
            Log.d(TAG, "do not have location permission");
            if (askForPermission) {
                requestLocationPermission();
            } else {
                onLocationFailCallback(ERROR_CODE_PERMISSION_DENY, "no permission");
            }
            return;
        }
        startLocation();
    }

    private void locationPermissionGranted(boolean alreadyHasLocationPermission) {
        if (alreadyHasLocationPermission) {
            startLocation();
        } else {
            onLocationFailCallback(ERROR_CODE_PERMISSION_DENY, "locationPermissionGranted but no permission");
        }
    }

    private void startLocation() {
        Log.d(TAG, "startLocation");
        mLocationManager = (LocationManager) mActivity.getSystemService(Activity.LOCATION_SERVICE);
        final Criteria criteria = buildMediumAccuracyCriteria();
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLocationManager.requestSingleUpdate(criteria, this, null);
    }

    @NonNull
    private Criteria buildMediumAccuracyCriteria() {
        final Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setBearingAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setSpeedAccuracy(Criteria.ACCURACY_MEDIUM);
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        return criteria;
    }

    public void destroy() {
        removeLocationListener();
    }

    private void removeLocationListener() {
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.removeUpdates(this);
        }
    }

    /**
     * 需要在mActivity#onRequestPermissionsResult中回调该方法
     */
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionManager.onRequestPermissionsResult(mActivity, this, requestCode, permissions, grantResults);
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "hdz-location onLocationChanged lat: " + location.getLatitude() + " lon: " + location.getLongitude());
        onLocationChangedCallback(location);
    }

    private void onLocationChangedCallback(Location location) {
        if (mSimpleLocationListener != null) {
            mSimpleLocationListener.onLocationChanged(location);
        }
    }

    private void onLocationFailCallback(int errorCode, String errorMsg) {
        Log.d(TAG, "onLocationFailCallback. errorCode: " + errorCode + " errorMsg: " + errorMsg);
        if (mSimpleLocationListener != null) {
            mSimpleLocationListener.onLocationFail(errorCode, errorMsg);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "hdz-location onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "hdz-location onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d(TAG, "hdz-location onProviderDisabled");
    }

}
