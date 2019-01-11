package com.as1124.googledoc.connectivity.usb;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

import java.util.Map;

public class USBMainActivity extends Activity {

    private UsbManager usbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usb_main);

        usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_ACCESSORY)) {
            //
        } else {
            Toast.makeText(this, "设备不支持USB附件处理", Toast.LENGTH_SHORT).show();
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_USB_HOST)) {
            Toast.makeText(this, "设备不支持USB Host 主机模式", Toast.LENGTH_SHORT).show();
        }

        UsbAccessory[] accessories = usbManager.getAccessoryList();
        Map<String, UsbDevice> usbDevices = usbManager.getDeviceList();
        Log.i("USBManager", "Host 和 Accessory");
    }
}
