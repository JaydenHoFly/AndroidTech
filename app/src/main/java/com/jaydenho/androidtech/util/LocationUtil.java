package com.jaydenho.androidtech.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class LocationUtil implements LocationListener {

    public static final int ERROR_CODE_PERMISSION_DENY = 1000;
    public static final int ERROR_CODE_PROVIDER_UNABLE = 1001;
    private static final String TAG = LocationUtil.class.getSimpleName();
    private LocationManager mLocationManager = null;
    private Activity mActivity = null;
    private SimpleLocationListener mSimpleLocationListener = null;
    //TEST
    private StringBuilder mGpsTracker = new StringBuilder();

    public void setSimpleLocationListener(SimpleLocationListener simpleLocationListener) {
        mSimpleLocationListener = simpleLocationListener;
    }

    public LocationUtil(@NonNull Activity activity) {
        this.mActivity = activity;
    }

    public interface SimpleLocationListener {
        void onLocationChanged(Location location);

        void onLocationFail(int errorCode, String errorMsg);
    }

    /**
     * 获取一次定位信息,用户广告模块
     */
    public void start() {
        /**
         * 进行定位
         * provider:用于定位的locationProvider字符串
         * minTime:时间更新间隔，单位：ms
         * minDistance:位置刷新距离，单位：m
         * listener:用于定位更新的监听者locationListener
         */
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mGpsTracker.append("do not have location permission");
            Log.d(TAG, "dont have location permission");
            onLocationFailCallback(ERROR_CODE_PERMISSION_DENY, "");
            return;
        }
        mGpsTracker.append(" have location permission");
        mLocationManager = (LocationManager) mActivity.getSystemService(Activity.LOCATION_SERVICE);
        boolean isGPSEnabled;
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {//没有access_fine_location权限,不能使用gps和passive providers,三星手机会崩溃
            isGPSEnabled = false;
        } else {
            isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        mGpsTracker.append(" isNetworkEnabled: " + isNetworkEnabled + " isGPSEnabled: " + isGPSEnabled);
        if (isGPSEnabled) {
            Log.d(TAG, "hdz-location 正在定位 provider: " + LocationManager.GPS_PROVIDER);
            mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
        }
        if (isNetworkEnabled) {
            Log.d(TAG, "hdz-location 正在定位 provider: " + LocationManager.NETWORK_PROVIDER);
            mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
        }
        if (!isNetworkEnabled && !isGPSEnabled) {
            onLocationFailCallback(ERROR_CODE_PROVIDER_UNABLE, "");
        }
      /*  Location last = locationManager.getLastKnownLocation(provider);//UNDONE 待优化
        if (last != null) {
            saveLocation(last, provider);
        }*/
    }

    public void recordTracker() {
        //TEST
        if (!TextUtils.isEmpty(mGpsTracker.toString().trim())) {
            CommonUtil.saveOverLengthLog2SDCard("gps_log.txt", mGpsTracker.toString().trim());
        }
    }

    public void destroy() {
        removeLocationListener();
        recordTracker();
    }

    private void removeLocationListener() {
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        String provider = location.getProvider();
        mGpsTracker.append(" onLocationChanged 定位方式: " + (provider.equals(LocationManager.GPS_PROVIDER) ? "gps" : "network") + " lat:" + location.getLatitude() + " lon: " + location.getLongitude());
        Toast.makeText(mActivity, "onLocationChanged 定位方式: " + (provider.equals(LocationManager.GPS_PROVIDER) ? "gps" : "network") + " lat:" + location.getLatitude() + " lon: " + location.getLongitude(), Toast.LENGTH_LONG).show();
        Log.d(TAG, "hdz-location onLocationChanged");
        onLocationChangedCallback(location);
    }

    private void onLocationChangedCallback(Location location) {
        if (mSimpleLocationListener != null) {
            mSimpleLocationListener.onLocationChanged(location);
        }
    }

    private void onLocationFailCallback(int errorCode, String errorMsg) {
        if (mSimpleLocationListener != null) {
            mSimpleLocationListener.onLocationFail(errorCode, errorMsg);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        mGpsTracker.append(" onStatusChanged provider: " + provider);
        Log.d(TAG, "hdz-location onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String provider) {
        mGpsTracker.append(" onProviderEnabled provider: " + provider);
        Log.d(TAG, "hdz-location onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        mGpsTracker.append(" onProviderDisabled provider: " + provider);
        Log.d(TAG, "hdz-location onProviderDisabled");
        //UNDONE 是否需要回调失败
    }

}
