package com.as1124.googledoc.connectivity.wifi;

import android.annotation.TargetApi;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.os.Build;
import android.util.Log;

@TargetApi(Build.VERSION_CODES.O)
public class WiFiAwareDiscoveryCallback extends DiscoverySessionCallback {

    protected PublishDiscoverySession publishSession;
    protected SubscribeDiscoverySession subscribeSession;

    @Override
    public void onPublishStarted(PublishDiscoverySession session) {
        Log.i("[Wi-Fi Aware]", "Wi-Fi Aware Service Publish Success!!");
        publishSession = session;
    }

    @Override
    public void onMessageReceived(PeerHandle peerHandle, byte[] message) {
    }

    @Override
    public void onSubscribeStarted(SubscribeDiscoverySession session) {
        Log.i("[Wi-Fi Aware]", "Wi-Fi Aware Service Subscribe Success!!");
        subscribeSession = session;
    }
}
