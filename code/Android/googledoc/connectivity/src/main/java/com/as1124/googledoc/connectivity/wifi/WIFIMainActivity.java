package com.as1124.googledoc.connectivity.wifi;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.aware.DiscoverySessionCallback;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.WifiAwareManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

import java.util.List;

/**
 * <ul>
 * <li>Wi-Fi</li>
 * <li>Wi-Fi P2P</li>
 * <li>Wi-Fi Aware</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WIFIMainActivity extends Activity {

    private WifiManager wifiManager = null;
    private BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                boolean scanFinished = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                Log.i("WI-FI Scan", "扫描完成 = " + scanFinished);
            }
        }
    };

    // Peer to Peer
    private WifiP2pManager p2pManager = null;
    private WifiP2pManager.Channel p2pChannel = null;
    private WifiP2pDeviceList p2pDeviceList = null;
    private BroadcastReceiver p2pReceiver = null;
    private WifiP2pManager.ChannelListener channelListener = () -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(WIFIMainActivity.this);
        builder.setMessage("WI-FI P2P Channel 连接断开了").create().show();
    };

    // Wi-Fi Aware
    private WifiAwareManager awareManager = null;
    private WiFiAwareAttachCallback awareAttachCallback = new WiFiAwareAttachCallback();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        findViewById(R.id.but_scan_wifi).setOnClickListener(v -> scanWifiSignals());
        findViewById(R.id.but_wifi_info).setOnClickListener(v -> showConnectedWIFIInfo());
        if (Build.VERSION.SDK_INT >= 21) {
            if (!wifiManager.isP2pSupported()) {
                Toast.makeText(this, "不支持P2P协议", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Obtain an instance of WifiP2pManager, and initialize Wi-Fi P2P framework with my application
        p2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        p2pChannel = p2pManager.initialize(this, Looper.getMainLooper(), channelListener);
        if (p2pChannel == null) {
            Toast.makeText(this, "无法试用P2P功能, Channel == null", Toast.LENGTH_SHORT).show();
        }
        p2pReceiver = new WifiP2PReceiver(p2pManager, p2pChannel, this);
        findViewById(R.id.but_scan_p2p).setOnClickListener(v -> scanP2PDevices());
        findViewById(R.id.but_connect_p2p).setOnClickListener(v -> connectP2PDevice());
        findViewById(R.id.but_transfer_p2p).setOnClickListener(v -> sendData2P2P());

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_WIFI_AWARE)) {
            Toast.makeText(this, "不支持 Wi-Fi Aware 功能", Toast.LENGTH_SHORT).show();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            awareManager = (WifiAwareManager) getSystemService(Context.WIFI_AWARE_SERVICE);
            findViewById(R.id.but_aware_session).setOnClickListener(v -> wifiAwareSession());
            findViewById(R.id.but_aware_publish);
            findViewById(R.id.but_aware_subscribe);
            findViewById(R.id.but_aware_transform);
        }

        findViewById(R.id.but_to_companion).setOnClickListener(v -> startActivity(new Intent(this, CompanionPairActivity.class)));
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiScanReceiver, intentFilter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        filter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        registerReceiver(p2pReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(wifiScanReceiver);
        unregisterReceiver(p2pReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (awareAttachCallback.awareSession != null) {
            awareAttachCallback.awareSession.close();
        }
    }

    private void scanWifiSignals() {
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WI-FI功能未打开", Toast.LENGTH_SHORT).show();
            wifiManager.setWifiEnabled(true);
            return;
        }

        if (!wifiManager.startScan()) {
            Toast.makeText(this, "扫描WI-FI热点失败", Toast.LENGTH_SHORT).show();
        }

        // 这里立即获取结果可能返回的是上一次扫描的旧结果( 因为扫描未结束 )
        List<ScanResult> wifiList = wifiManager.getScanResults();
        for (ScanResult wifiInfo : wifiList) {
            Log.i("WI-FI", wifiInfo.SSID + ", " + wifiInfo.capabilities);
        }
    }

    private void showConnectedWIFIInfo() {
        // 获取配置过的WI-FI信息
        List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration configuration : list) {
            if ("Ivy".equals(configuration.SSID)) {
                // 忘记网络
                wifiManager.disableNetwork(configuration.networkId);
                break;
            }
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        Log.i("WI-FI", "当前连接的WI-FI： " + wifiInfo.getSSID() + ", mac = " + wifiInfo.getMacAddress() + ", ip = " + wifiInfo.getIpAddress());
    }

    private void scanP2PDevices() {
        // 该方法是异步执行, 调用之后获取的设备结果信息会在 BroadcastReceiver中回调
        p2pManager.discoverPeers(p2pChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i("Wi-Fi P2P", "Discover peers success called!");
            }

            @Override
            public void onFailure(int reason) {
                Log.i("Wi-Fi P2P", "Discover peers call failed with reason-code = " + reason);
            }
        });

        p2pManager.requestPeers(p2pChannel, peers -> {
            Log.i("P2P Peers", "发现可连接的P2P设备数量 = " + peers.getDeviceList().size());
            p2pDeviceList = peers;
        });
    }

    private void connectP2PDevice() {
        if (p2pDeviceList == null || p2pDeviceList.getDeviceList().isEmpty()) {
            return;
        }
        WifiP2pDevice device = p2pDeviceList.get("");
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = device.deviceAddress;
        p2pManager.connect(p2pChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.i("P2P Device", "connect p2p-device 成功");
            }

            @Override
            public void onFailure(int reason) {
                Log.i("P2P Device", "connect p2p-device 失败");
            }
        });
    }

    private void sendData2P2P() {
        // See  FileServerAsyncTask, need ServerSocket and Socket-Client
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void wifiAwareSession() {
        if (!awareManager.isAvailable()) {
            Toast.makeText(this, "设备上Wi-Fi Aware功能关闭 ? ", Toast.LENGTH_SHORT).show();
        }
        awareManager.attach(awareAttachCallback, null);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void publishService() {
        if (awareAttachCallback.awareSession != null) {
            PublishConfig config = new PublishConfig.Builder().setServiceName("As1124_Aware_Service").build();
            DiscoverySessionCallback discoverySessionCallback = new WiFiAwareDiscoveryCallback();
            awareAttachCallback.awareSession.publish(config, discoverySessionCallback, null);
        }
    }


}
