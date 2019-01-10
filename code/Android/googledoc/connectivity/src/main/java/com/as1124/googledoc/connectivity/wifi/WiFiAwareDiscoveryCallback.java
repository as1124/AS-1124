package com.as1124.googledoc.connectivity.wifi;

import android.annotation.TargetApi;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PeerHandle;
import android.net.wifi.aware.PublishDiscoverySession;
import android.net.wifi.aware.SubscribeDiscoverySession;
import android.os.Build;

@TargetApi(Build.VERSION_CODES.O)
public class WiFiAwareDiscoveryCallback extends DiscoverySessionCallback {

    @Override
    public void onPublishStarted(PublishDiscoverySession session) {
    }

    @Override
    public void onMessageReceived(PeerHandle peerHandle, byte[] message) {
    }

    @Override
    public void onSubscribeStarted(SubscribeDiscoverySession session) {
    }
}
