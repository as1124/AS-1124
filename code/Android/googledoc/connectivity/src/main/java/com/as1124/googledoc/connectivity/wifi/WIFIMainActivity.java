package com.as1124.googledoc.connectivity.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

public class WIFIMainActivity extends Activity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AlertDialog.Builder builder = new AlertDialog.Builder(WIFIMainActivity.this);
                    builder.setMessage("正在扫描WI-FI热点...").create().show();
                    break;
                case 1:
                    break;
                default:

            }
        }
    };

    private WifiManager wifiManager = null;

    private BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_main);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        findViewById(R.id.but_scan_wifi).setOnClickListener(v -> scanWifiSignals());
    }

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(wifiScanReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(wifiScanReceiver);
    }

    private void scanWifiSignals() {
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(this, "WI-FI功能未打开", Toast.LENGTH_SHORT).show();
            // ATTENTION 能通过代码开启WI-FI吗 ?

            return;
        }

        if (!wifiManager.startScan()) {
            mHandler.obtainMessage(0).sendToTarget();
        } else {
            Toast.makeText(this, "扫描WI-FI热点失败", Toast.LENGTH_SHORT).show();
        }

    }
}
