package com.as1124.googledoc.connectivity.wifi;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

public class WifiP2PReceiver extends BroadcastReceiver {

    private static final String TAG = "P2P-Receiver";

    private WifiP2pManager p2pManager;
    private WifiP2pManager.Channel p2pChannel;
    private Activity p2pActivity;

    public WifiP2PReceiver(WifiP2pManager p2pManager, WifiP2pManager.Channel channel, Activity activity) {
        this.p2pManager = p2pManager;
        this.p2pChannel = channel;
        this.p2pActivity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            Log.i(TAG, "WiFi P2P state = " + (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED));
        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            // callback by WifiP2pManager.discoverPeers in the main-thread
            intent.getParcelableArrayListExtra(WifiP2pManager.EXTRA_P2P_DEVICE_LIST);

            // request available peers from the wifi p2p manager. This is an asynchronous call and the calling activity is notified
            // with a callback on PeerListListener.onPeersAvailable()
        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
            NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
            WifiP2pInfo p2pInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_INFO);
            Log.i(TAG, networkInfo.toString() + p2pInfo.toString());
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
            WifiP2pDevice device = intent.getParcelableExtra(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE);
            Log.i(TAG, "P2P 设备信息 = " + device.deviceName + ", " + device.deviceAddress);
        }
    }
}
