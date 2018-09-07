package com.as1124.googledoc.permission;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.as1124.googledoc.R;

public class PermissionActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);

        findViewById(R.id.but_normal_permission).setOnClickListener(this);
        findViewById(R.id.but_runtime_permission).setOnClickListener(this);
        findViewById(R.id.but_custom_permission).setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int viewid = v.getId();
        switch (viewid) {
            case R.id.but_normal_permission:
                ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo current = connectManager.getActiveNetworkInfo();
                if (current != null) {
                    Toast.makeText(this, "当前网络状态==" + current.getTypeName(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.but_runtime_permission:
                if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this, "需要使用短信权限", Toast.LENGTH_SHORT).show();
                }
                if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivity(intent);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 111);
                }
                break;
            case R.id.but_custom_permission:
                // 自定义权限：定义权限的module自身拥有权限，不需要申请，如果其他module要调用对应功能则需要申请权限
                Intent intent2 = new Intent(PermissionActivity.this, CustomPermissionActivity.class);
                startActivity(intent2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        }
    }
}
