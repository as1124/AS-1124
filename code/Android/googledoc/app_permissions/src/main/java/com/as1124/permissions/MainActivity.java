package com.as1124.permissions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.google.android.gms.iid.InstanceID;

import java.io.File;
import java.util.UUID;

/**
 * Android Runtime Permission
 * <ul>
 * <li>只在protectLevel=dangerous的权限需要动态授权</li>
 * <li>调用第三方APP需要使用的动态权限不需要申请</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    private static final String AUTHORITIES = "com.as1124.permissions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_system_camera).setOnClickListener(v -> {
            // 这里只申请了存储权限不用申请相机权限
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 211);
                }
            } else {
                startCamera();
            }

        });

        findViewById(R.id.but_self_camera).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 212);
                } else {
                    Toast.makeText(this, "系统建议不弹出授权对话框", Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.but_google_play_uuid).setOnClickListener(v -> {
            String deviceID = InstanceID.getInstance(getBaseContext()).getId();
            Toast.makeText(this, "Google-Play设备ID=【" + deviceID + "】", Toast.LENGTH_SHORT).show();

        });

        findViewById(R.id.but_random_uuid).setOnClickListener(v -> {
            UUID uuid = UUID.randomUUID();
            android.util.Log.i("【DEVICE-ID】", uuid.toString());
            // 但是这里每次生成的不一样
        });

        findViewById(R.id.but_imei).setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    showIMEI();
                } else {
                    requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 213);
                }
            } else {
                showIMEI();
            }
        });

        findViewById(R.id.but_self_permission).setOnClickListener(v ->
                startActivity(new Intent(getBaseContext(), SelfPermissionActivity.class)));
    }

    private void startCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File dcimDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File imgFile = new File(dcimDirectory, "Camera/mygirl.jpg");
        Uri outputUri = FileProvider.getUriForFile(getBaseContext(), AUTHORITIES, imgFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 111);
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void showIMEI() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = telephonyManager.getImei();
        Toast.makeText(this, "手机IME=【" + imei + "】", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == Activity.RESULT_OK) {
            Toast.makeText(this, "拍照保存成功", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 211) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(getBaseContext());
                builder.setIcon(getApplicationInfo().icon).setTitle("权限管理").setMessage("拒绝了【文件管理】权限").show();
            }
        } else if (requestCode == 212) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "申请【相机】权限失败", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 213) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showIMEI();
            }
        }
    }
}
