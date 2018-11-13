package com.as1124.camera;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.as1124.selflib.WindowUtils;

/**
 * 照相机相关功能
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowUtils.fullScreen(this);

        boolean result = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        if (!result) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Error").setMessage("设备没有摄像头，无法使用该APP！").show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 1234);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1234 == requestCode) {
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "申请照相机权限成功", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
