package com.as1124.googledoc.connectivity.wifi;

import android.annotation.TargetApi;
import android.net.wifi.aware.AttachCallback;
import android.net.wifi.aware.WifiAwareSession;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.O)
public class WiFiAwareAttachCallback extends AttachCallback {

    private static final String TAG = "WiFiAwareAttachCallback";

    protected WifiAwareSession awareSession;

    @Override
    public void onAttached(WifiAwareSession session) {
        Log.i(TAG, "Wi-Fi Aware Session Get");
        this.awareSession = session;
    }

    @Override
    public void onAttachFailed() {
        Log.i(TAG, "无法获取 Wi-Fi Aware Session");
    }
}
