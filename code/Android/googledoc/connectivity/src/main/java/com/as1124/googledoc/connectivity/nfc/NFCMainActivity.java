package com.as1124.googledoc.connectivity.nfc;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.as1124.googledoc.connectivity.R;

/**
 * Android设备NFC功能使用学习
 *
 * @author as-1124(maito:as1124huang@gmai.com)
 */
public class NFCMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc_main);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            Toast.makeText(this, "Android设备不支持NFC功能", Toast.LENGTH_SHORT).show();
            return;
        }


    }
}
