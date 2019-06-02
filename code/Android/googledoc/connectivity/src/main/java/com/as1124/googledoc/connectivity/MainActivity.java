package com.as1124.googledoc.connectivity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.background.HttpsConnectionActivity;
import com.as1124.googledoc.connectivity.bluetooth.BluetoothMainActivity;
import com.as1124.googledoc.connectivity.nfc.NFCMainActivity;
import com.as1124.googledoc.connectivity.usb.USBMainActivity;
import com.as1124.googledoc.connectivity.vpn.VPNMainActivity;
import com.as1124.googledoc.connectivity.wifi.WIFIMainActivity;

/**
 * 手机网络相关学习
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private ConnectivityManager networkManager = null;
    private TelephonyManager telephonyManager = null;

    private BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                showNetworkInfo();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_https).setOnClickListener(this);
        findViewById(R.id.but_get_network).setOnClickListener(this);
        findViewById(R.id.but_network_settings).setOnClickListener(this);
        findViewById(R.id.but_data_whitelist).setOnClickListener(this);
        findViewById(R.id.but_sync_adapter).setOnClickListener(this);
        findViewById(R.id.but_to_bluetooth).setOnClickListener(this);
        findViewById(R.id.but_to_nfc).setOnClickListener(this);
        findViewById(R.id.but_to_wifi).setOnClickListener(this);
        findViewById(R.id.but_to_usb).setOnClickListener(this);
        findViewById(R.id.but_to_vpn).setOnClickListener(this);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(networkReceiver);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_to_https:
                startActivity(new Intent(this, HttpsConnectionActivity.class));
                break;
            case R.id.but_get_network:
                showNetworkInfo();
                break;
            case R.id.but_network_settings:
                startActivity(new Intent(this, NetworkSettingActivity.class));
                break;
            case R.id.but_data_whitelist:
                Intent intent = new Intent(Settings.ACTION_IGNORE_BACKGROUND_DATA_RESTRICTIONS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                break;
            case R.id.but_sync_adapter:
                // ATTENTION 账号数据同步管理
                break;
            case R.id.but_to_bluetooth:
                Intent bluetoothIntent = new Intent(this, BluetoothMainActivity.class);
                startActivity(bluetoothIntent);
                break;
            case R.id.but_to_nfc:
                startActivity(new Intent(this, NFCMainActivity.class));
                break;
            case R.id.but_to_wifi:
                startActivity(new Intent(this, WIFIMainActivity.class));
                break;
            case R.id.but_to_usb:
                startActivity(new Intent(MainActivity.this, USBMainActivity.class));
                break;
            case R.id.but_to_vpn:
                startActivity(new Intent(MainActivity.this, VPNMainActivity.class));
                break;
            case R.id.but_to_volley:
                break;
            case R.id.but_to_okhttp:
                break;
            case R.id.but_to_retrofit:
                break;
            default:
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        android.util.Log.i(getClass().getSimpleName(), "Configuration Changed!!");
        Toast.makeText(this, "Activity Configuration change !", Toast.LENGTH_SHORT).show();
    }

    private void showNetworkInfo() {
        if (networkManager == null) {
            networkManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        }
        NetworkInfo activeNetwork = networkManager.getActiveNetworkInfo();
        if (activeNetwork == null) {
            Toast.makeText(this, "无可用网络链接", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            Network[] allType = networkManager.getAllNetworks();
            for (Network network : allType) {
                NetworkInfo info = networkManager.getNetworkInfo(network);
                if (info != null) {
                    switch (info.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            Toast.makeText(this, "使用的是WI-FI网络, 链接状态 = " + info.isConnected(), Toast.LENGTH_SHORT).show();
                            break;
                        case ConnectivityManager.TYPE_MOBILE:
                            String typeName = info.getSubtypeName();
                            switch (telephonyManager.getNetworkType()) {
                                case TelephonyManager.NETWORK_TYPE_GPRS:
                                    typeName = "GPRS";
                                default:
                            }
                            Toast.makeText(this, "网络类型： " + typeName, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Log.i("Connectivity", info.getTypeName() + "==" + info.isAvailable()
                                    + ", isConnected=" + info.isConnected());
                    }
                }
            }
        }
    }


}
