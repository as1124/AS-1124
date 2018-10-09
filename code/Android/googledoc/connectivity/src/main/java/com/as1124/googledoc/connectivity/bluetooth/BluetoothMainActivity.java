package com.as1124.googledoc.connectivity.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class BluetoothMainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "Bluetooth";

    private static final int REQUEST_CODE = 1111;

    private BluetoothManager manager;

    private BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.i(TAG, "搜索蓝牙设备");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i(TAG, "蓝牙设备搜索结束");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mDevices.put(device.getAddress(), device);
                Log.i(TAG, "找到设备==" + name + ", " + device.getAddress());
            } else if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
                Log.i(TAG, "收到配对请求");
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                Log.i(TAG, "绑定状态改变");
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.i(TAG, "ACL 已连接");
            }
        }
    };

    private Map<String, BluetoothDevice> mDevices = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);

        manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            }

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        }

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
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_PAIRING_REQUEST);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        registerReceiver(scanReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(scanReceiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //
            } else {
                Toast.makeText(this, "拒绝了位置权限，可能因此导致部分蓝牙功能不能用", Toast.LENGTH_SHORT).show();
            }
        }
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
                new Thread(() -> {
                    pairDevice();
                }).start();
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
            Log.i(TAG, "获取的结果一致");
        }
        if (!adapter.isEnabled()) {
//            adapter.enable();
            Intent openIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(openIntent, 1122);
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
//        if (Build.VERSION.SDK_INT >= 21) {
//            // 得到的结果太多，没有价值
//            adapter.getBluetoothLeScanner().startScan(new ScanCallback() {
//                @Override
//                public void onScanResult(int callbackType, ScanResult result) {
//                    super.onScanResult(callbackType, result);
//                    if (Build.VERSION.SDK_INT >= 21) {
//                        BluetoothDevice one = result.getDevice();
//                        Log.i(TAG, "Scan one==" + one.getAddress() + ", name===" + one.getName());
//                    }
//                }
//
//                @Override
//                public void onScanFailed(int errorCode) {
//                    super.onScanFailed(errorCode);
//                    Log.e(TAG, "蓝牙设备扫描失败");
//                }
//            });
//        } else {
        adapter.startDiscovery();
//        }
    }

    private void pairDevice() {
        BluetoothAdapter adapter = manager.getAdapter();
        // 记录的只是已经配对的设备，并不一定是连接的
        Set<BluetoothDevice> boundDevices = adapter.getBondedDevices();
        if (!boundDevices.isEmpty()) {
            Log.i(TAG, "本地蓝牙已进行过设备配对和绑定");
        }
        if (adapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothProfile.STATE_CONNECTED) {
            Log.i(TAG, "本地蓝牙设备已连接");
        } else {
            Log.i(TAG, "本地蓝牙未连接");
        }

        String remoteAddr = "68:3E:34:27:52:5E";
        BluetoothDevice device = adapter.getRemoteDevice(remoteAddr);
        if (device.getBondState() == BluetoothDevice.BOND_BONDED || device.getBondState() == BluetoothDevice.BOND_BONDING) {
            Log.i(TAG, "远程蓝牙设备正在绑定或已绑定");
        }
        boolean flag = device.createBond();
        BluetoothSocket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            socket = device.createRfcommSocketToServiceRecord(UUID.randomUUID());
            socket.connect();
            if (socket.isConnected()) {
                File file = new File(Environment.getExternalStorageDirectory(), "huangjw.mp3");
                if (file.exists()) {
                    outputStream = socket.getOutputStream();
                    inputStream = new FileInputStream(file);
                    byte[] buff = new byte[4096];
                    int length = -1;
                    while ((length = inputStream.read(buff)) != -1) {
                        outputStream.write(buff, 0, length);
                    }
                    outputStream.flush();
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "蓝牙设备连接失败", e);
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
            if (outputStream != null) {
                IOUtils.closeQuietly(outputStream);
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
