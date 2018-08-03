package com.as1124.ch7test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final int PHONE_CALL = 0x1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ch7_but_phonecall).setOnClickListener(v -> {
            /************* 权限动态申请的同时仍需要在 Manifest.xml 中声明相应权限**********************************/
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    MainActivity.this.requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, PHONE_CALL);
                    return;
                } else {
                    makePhoneCall();
                }
            } else {
                makePhoneCall();
            }
        });

        findViewById(R.id.ch7_but_tocontact).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContactsActivity.class);
            startActivity(intent);
        });
    }

    private void makePhoneCall() {
        // 因为权限可能导致安全问题爆发
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:10086"));
            startActivity(intent);
        } catch (SecurityException e) {
            android.util.Log.e("ch7test", e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PHONE_CALL == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                Toast.makeText(this, "拒绝了拨号请求", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
