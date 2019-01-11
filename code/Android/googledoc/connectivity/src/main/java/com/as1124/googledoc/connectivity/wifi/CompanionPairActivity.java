package com.as1124.googledoc.connectivity.wifi;

import android.app.Activity;
import android.app.AlertDialog;
import android.companion.AssociationRequest;
import android.companion.BluetoothDeviceFilter;
import android.companion.CompanionDeviceManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

import java.util.regex.Pattern;

/**
 * 设置过滤请求，附近发现匹配设备时会自动进行配对（不是连接）
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class CompanionPairActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_companion_pair);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Companion Device Manager").setMessage("附件设备配对连接功能只能在 Android 8.0及以上设备试用");
            builder.create().show();
        } else {
            CompanionDeviceManager deviceManager = (CompanionDeviceManager) getSystemService(Context.COMPANION_DEVICE_SERVICE);

            // To skip filtering based on name and supported feature flags(UUIDs).
            // don't include calls to setNamePattern() and addServiceUuid().
            BluetoothDeviceFilter bluetoothFilter = new BluetoothDeviceFilter.Builder().setNamePattern(Pattern.compile("JBL")).build();

            // The argument provided in setSingleDevice() determines whether single device name or a list of device
            // name is presented to the user as pairing options.
            AssociationRequest pairingRequest = new AssociationRequest.Builder().addDeviceFilter(bluetoothFilter)
                    .setSingleDevice(true).build();

            deviceManager.associate(pairingRequest, new CompanionDeviceManager.Callback() {
                @Override
                public void onDeviceFound(IntentSender chooserLauncher) {
                    Log.i("Companion-Device", "成功");
                    try {
                        startIntentSenderForResult(chooserLauncher, 124, null, 0, 0, 0);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(CharSequence error) {
                    Log.i("Companion-Device", "配对设备查找请求失败");
                }
            }, null);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 124 && resultCode == RESULT_OK) {
            // User has chosen to pair with the Bluetooth device.
            Toast.makeText(this, "匹配请求成功处理", Toast.LENGTH_SHORT).show();
        }
    }
}
