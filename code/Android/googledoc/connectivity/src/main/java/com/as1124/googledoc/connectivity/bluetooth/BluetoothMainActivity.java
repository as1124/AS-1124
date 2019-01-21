package com.as1124.googledoc.connectivity.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 蓝牙管理
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class BluetoothMainActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "BluetoothDevice";

    private static final int REQUEST_CODE = 1111;

    private BluetoothManager manager = null;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothDevice pairedDevice = null;

    // 代指手机蓝牙HeadSet描述配置而不是蓝牙耳机设备
    private BluetoothHeadset bluetoothHeadset = null;
    private BluetoothProfile.ServiceListener profileListener = new BluetoothProfile.ServiceListener() {
        @Override
        public void onServiceConnected(int profile, BluetoothProfile proxy) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = (BluetoothHeadset) proxy;
            }
        }

        @Override
        public void onServiceDisconnected(int profile) {
            if (profile == BluetoothProfile.HEADSET) {
                bluetoothHeadset = null;
                Log.i(TAG, "蓝牙耳机失去连接");
            }
        }
    };

    private BroadcastReceiver deviceReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
                Log.i(TAG, "搜索蓝牙设备开始");
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                Log.i(TAG, "蓝牙设备搜索结束");
            } else if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                String name = intent.getStringExtra(BluetoothDevice.EXTRA_NAME);
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                Log.i(TAG, "找到设备==" + name + ", " + device.getAddress());
            } else if (BluetoothDevice.ACTION_PAIRING_REQUEST.equals(action)) {
                Log.i(TAG, "收到配对请求");
            } else if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                int nowState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.BOND_NONE);
                int previousState = intent.getIntExtra(BluetoothDevice.EXTRA_PREVIOUS_BOND_STATE, BluetoothDevice.BOND_NONE);
                if (nowState == BluetoothDevice.BOND_BONDED) {
                    pairedDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                } else {
                    pairedDevice = null;
                }
                Log.i(TAG, "蓝牙设备绑定状态改变, from " + previousState + " to " + nowState);
            } else if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
                Log.i(TAG, "ACL 已连接");
            } else if (BluetoothDevice.ACTION_UUID.equals(action)) {
                pairedDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                ParcelUuid[] parcelUuid = (ParcelUuid[]) intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                Log.i(TAG, "UUID fetched!!! = " + parcelUuid[0].getUuid());
            }
        }
    };

    private BroadcastReceiver adapterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
                int nowState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.STATE_OFF);
                int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, BluetoothAdapter.STATE_OFF);
                Log.i("BluetoothAdapter", "蓝牙状态变化: from " + previousState + " to " + nowState);
            } else if (BluetoothAdapter.ACTION_REQUEST_ENABLE.equals(action)) {
                Log.i("BluetoothAdapter", "收到打开蓝牙请求");
            } else if (BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED.equals(action)) {
                String newName = intent.getStringExtra(BluetoothAdapter.EXTRA_LOCAL_NAME);
                Log.i("BluetoothAdapter", "修改本地蓝牙名称, New_local_name = " + newName);
            } else if (BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED.equals(action)) {
                int nowState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.STATE_DISCONNECTED);
                int previousState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, BluetoothAdapter.STATE_DISCONNECTED);
                Log.i("BluetoothAdapter", "蓝牙设备连接状态变化: from " + previousState + " to " + nowState);
            } else if (BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE.equals(action)) {
                Log.i("BluetoothAdapter", "收到扫描发现蓝牙请求");
            } else if (BluetoothAdapter.ACTION_SCAN_MODE_CHANGED.equals(action)) {
                int nowMode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
                int previousMode = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_SCAN_MODE, BluetoothAdapter.SCAN_MODE_NONE);
                Log.i("BluetoothAdapter", "本地蓝牙扫描模式发生变化, from " + previousMode + " to " + nowMode);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_main);

        manager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (manager == null || bluetoothAdapter == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bluetooth").setMessage("设备不支持蓝牙功能").create().show();
            return;
        }

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
        findViewById(R.id.but_listen_bluetooth).setOnClickListener(this);
        findViewById(R.id.but_send_bluetooth).setOnClickListener(this);
        findViewById(R.id.but_connect_headset).setOnClickListener(this);
        findViewById(R.id.but_call_headset).setOnClickListener(this);
        findViewById(R.id.but_scan_ble).setOnClickListener(this);
        findViewById(R.id.but_connect_gatt).setOnClickListener(this);

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
        filter.addAction(BluetoothDevice.ACTION_UUID);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(deviceReceiver, filter);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_LOCAL_NAME_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        intentFilter.addAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(adapterReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(deviceReceiver);
        unregisterReceiver(adapterReceiver);
    }

    @Override
    protected void onDestroy() {
        if (bluetoothHeadset != null) {
            bluetoothAdapter.closeProfileProxy(BluetoothProfile.HEADSET, bluetoothHeadset);
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1122 && resultCode == RESULT_OK) {
            Toast.makeText(this, "蓝牙打开成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "申请定位、存储权限成功", Toast.LENGTH_SHORT).show();
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
                new Thread(() -> pairDevice()).start();
                break;
            case R.id.but_listen_bluetooth:
                createServerListener();
                break;
            case R.id.but_send_bluetooth:
                sendFile();
                break;
            case R.id.but_connect_headset:
                connectHeadset();
                break;
            case R.id.but_call_headset:
                doHeadsetFunction();
                break;
            case R.id.but_scan_ble:
                scanBLE();
                break;
            case R.id.but_connect_gatt:
                connectGATT();
            default:
                break;
        }
    }

    private void openBluetooth() {
        BluetoothAdapter adapter = manager.getAdapter();
        if (adapter.getAddress().equals(bluetoothAdapter.getAddress())) {
            Toast.makeText(this, "getDefaultAdapter和Manager#getAdapter()结果一致", Toast.LENGTH_SHORT).show();
        }
        if (!adapter.isEnabled()) {
//            adapter.enable();
            Intent openIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            if (openIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(openIntent, 1122);
            }
        }
    }

    private void closeBluetooth() {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.disable();
        }
    }

    private void scanDevices() {
        if (bluetoothAdapter.isEnabled()) {
            bluetoothAdapter.startDiscovery();
        }
    }

    private void pairDevice() {
        // 记录的只是已经配对的设备，并不一定是连接的
        Set<BluetoothDevice> boundDevices = bluetoothAdapter.getBondedDevices();
        if (!boundDevices.isEmpty()) {
            pairedDevice = boundDevices.toArray(new BluetoothDevice[]{})[0];
            Log.i(TAG, "本地蓝牙已进行过设备配对和绑定");
        }
        if (bluetoothAdapter.getProfileConnectionState(BluetoothProfile.A2DP) == BluetoothProfile.STATE_CONNECTED) {
            Log.i(TAG, "本地蓝牙设备已连接");
        } else if (bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothProfile.STATE_CONNECTED) {
            Log.i(TAG, "蓝牙头戴设备已经连接");
        }

        String remoteAddr = "64:CC:2E:CD:64:C1";// JBL TUNE205BT
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(remoteAddr);
        if (device.getBondState() == BluetoothDevice.BOND_BONDED || device.getBondState() == BluetoothDevice.BOND_BONDING) {
            Log.i(TAG, "远程蓝牙设备正在绑定或已绑定");
        }

        // true: 一般标识未曾配对绑定的结果
        boolean flag = device.createBond();
        Log.i(TAG, "蓝牙设备绑定结果 == " + flag);
        pairedDevice.fetchUuidsWithSdp();
    }

    static int index = 2;

    private void sendFile() {
        BluetoothSocket socket = null;
        OutputStream outputStream = null;
        InputStream inputStream = null;
        if (pairedDevice == null) {
            Toast.makeText(this, "无绑定设备可接收文件", Toast.LENGTH_SHORT).show();
            return;
        }
        bluetoothAdapter.cancelDiscovery();
        pairedDevice.createBond();

        // 方式一, not worked
//        UUID uuid = UUID.randomUUID();
//        socket = pairedDevice.createRfcommSocketToServiceRecord(uuid);
        try {
            socket = pairedDevice.createRfcommSocketToServiceRecord(serverUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 方式二, not worked
//        ParcelUuid[] uuids = pairedDevice.getUuids();
//        if (index < uuids.length) {
//            try {
//                socket = pairedDevice.createInsecureRfcommSocketToServiceRecord(uuids[index].getUuid());
//                socket = pairedDevice.createRfcommSocketToServiceRecord(uuids[index].getUuid());
//                index++;
//                socket.connect();
//            } catch (IOException e) {
//                try {
//                    if (socket != null) {
//                        socket.close();
//                    }
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//            }
//        }


        // 方式三, not worked
//        try {
//            Method method = BluetoothDevice.class.getDeclaredMethod("createRfcommSocket", Integer.TYPE);
//            Object object = method.invoke(pairedDevice, Integer.valueOf(1));
//            if (object != null) {
//                socket = (BluetoothSocket) object;
//                socket.connect();
//            } else {
//                return;
//            }
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 方式四, not work
        pairedDevice.fetchUuidsWithSdp();

        if (socket == null || !socket.isConnected()) {
            return;
        }

        try {
            inputStream = getAssets().open("IMG0553.JPG");
            outputStream = socket.getOutputStream();
            byte[] buff = new byte[4096];
            int length;
            while ((length = inputStream.read(buff)) != -1) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
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
        } // end of finally
    }

    /**
     * 连接蓝牙耳机 {@link BluetoothProfile}
     */
    private void connectHeadset() {
        if (bluetoothAdapter.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothProfile.STATE_CONNECTED) {
            Toast.makeText(this, "手机已经连接蓝牙耳机", Toast.LENGTH_SHORT).show();
        }

        // Establish connection to the proxy.
        bluetoothAdapter.getProfileProxy(this, this.profileListener, BluetoothProfile.HEADSET);
    }

    /**
     * 连接上蓝牙耳机后, 调用蓝牙耳机功能
     */
    private void doHeadsetFunction() {
        if (bluetoothHeadset != null) {
            List<BluetoothDevice> connectedDevices = bluetoothHeadset.getConnectedDevices();
            Log.i(TAG, "蓝牙耳机已经连接设备 = " + connectedDevices.size());
            if (connectedDevices.size() > 0) {
                BluetoothDevice device = connectedDevices.get(0);
                String name = device.getName();
                String address = device.getAddress();
                boolean isConnected = bluetoothHeadset.getConnectionState(device) == BluetoothHeadset.STATE_CONNECTED;
                boolean isAudioConnected = bluetoothHeadset.isAudioConnected(device);
                Log.i(TAG, "连接的蓝牙耳机设备 ：name" + name + ", " + address + ", state = " + isConnected + ", " + isAudioConnected);
            }
        }
    }

    private void scanBLE() {
        if (Build.VERSION.SDK_INT >= 21) {
            // 得到的结果太多，没有价值
            bluetoothAdapter.getBluetoothLeScanner().startScan(new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    BluetoothDevice one = result.getDevice();
                    Log.i(TAG, "Scan one==" + one.getAddress() + ", name===" + one.getName());
                }

                @Override
                public void onScanFailed(int errorCode) {
                    Log.e(TAG, "BLE 蓝牙设备扫描失败");
                }
            });
        }
    }

    private void connectGATT() {
        String remoteAddr = "64:CC:2E:CD:64:C1";// JBL TUNE205BT
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(remoteAddr);
        device.createBond();
        BluetoothGatt bluetoothGatt = device.connectGatt(this, true, new As1124GattCallback());
        bluetoothGatt.connect();
    }


    static final UUID serverUID = UUID.randomUUID();
    BluetoothServerSocket blueServer = null;

    private void createServerListener() {
        try {
            blueServer = bluetoothAdapter.listenUsingRfcommWithServiceRecord("As1124BlueServer", serverUID);
            new Thread(() -> {
                try {
                    blueServer.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (blueServer != null) {
                        try {
                            blueServer.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
