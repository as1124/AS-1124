package com.as1124.googledoc.connectivity.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.as1124.googledoc.connectivity.R;

import java.util.ArrayList;
import java.util.List;

public class BluetoothMainActivity extends Activity implements View.OnClickListener {

    private BluetoothManager manager;

    private BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    private List<BluetoothDevice> devices = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);

        manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        findViewById(R.id.but_open_bluetooth).setOnClickListener(this);
        findViewById(R.id.but_close_bluetooth).setOnClickListener(this);
        findViewById(R.id.but_scan_bluetooth).setOnClickListener(this);
        findViewById(R.id.but_pair_bluetooth).setOnClickListener(this);
        findViewById(R.id.but_send_bluetooth).setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter();
        filter.addAction("");
        filter.addAction("");
        registerReceiver(scanReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_open_bluetooth:
                openBluetooth();
                break;
            case R.id.but_close_bluetooth:
                closeBluetooth();
                break;
            case R.id.but_scan_bluetooth:
                scanDevices();
                break;
            case R.id.but_pair_bluetooth:
                pairDevice();
                break;
            case R.id.but_send_bluetooth:
                break;
            default:
                break;
        }
    }

    private void openBluetooth() {
        BluetoothAdapter adapter = manager.getAdapter();
        BluetoothAdapter adapter2 = BluetoothAdapter.getDefaultAdapter();
        if (adapter.getAddress().equals(adapter2.getAddress())) {
            Log.i("Bluetooth", "获取的结果一致");
        }
        if (!adapter.isEnabled()) {
            adapter.enable();
        }
    }

    private void closeBluetooth() {
        BluetoothAdapter adapter = manager.getAdapter();
        if (adapter.isEnabled()) {
            adapter.disable();
        }
    }

    private void scanDevices() {
        BluetoothAdapter adapter = manager.getAdapter();
        if (Build.VERSION.SDK_INT >= 21) {
            adapter.getBluetoothLeScanner().startScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    if (Build.VERSION.SDK_INT >= 21) {
                        BluetoothDevice one = result.getDevice();
                        Log.i("Bluetooth", "Scan one==" + one.getAddress());
                    }
                }

                @Override
                public void onScanFailed(int errorCode) {
                    super.onScanFailed(errorCode);
                    Log.e("Bluetooth", "蓝牙设备扫描失败");
                }
            });
        } else {
            adapter.startDiscovery();
        }
    }

    private void pairDevice() {

    }

}
