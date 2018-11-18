package com.as1124.camera;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.as1124.selflib.WindowUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 照相机相关功能
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowUtils.fullScreen(this);

        boolean result = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
        if (!result) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("Error").setMessage("设备没有摄像头，无法使用该APP！").show();
            return;
        }

        imageView = findViewById(R.id.image_camera);
        findViewById(R.id.but_camera_thumbnail).setOnClickListener(v -> {
            // 未指定保存的文件，所以结果只是在extra中保存了缩略图信息
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 0);
            }
        });

        findViewById(R.id.but_photo_file).setOnClickListener(v -> savePhoto());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1234);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
        } else if (requestCode == 1 && resultCode == RESULT_OK) {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (1234 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "申请照相机权限成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * For more recent apps targeting Android 7.0 (API level 24) and higher,
     * passing a file:// URI across a package boundary causes a {@link android.os.FileUriExposedException}。
     * 因此在应用间共享文件路径端话，必须使用{@link android.support.v4.content.FileProvider}来处理Uri参数的传递
     */
    private void savePhoto() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmm").format(new Date());
        String imageName = "JPEG_" + timestamp + "_";
        File storeDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            File image = File.createTempFile(imageName, ".jpg", storeDir);
            if (image != null) {
                Uri imageUri = FileProvider.getUriForFile(this, "com.as1124.camera.fileprovider", image);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//              直接使用Uri.fromFile(image))会报FileUriExposedException
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//Uri.fromFile(image));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, 1);
                }
            }
        } catch (IOException e) {
            Log.e("Camera-Image", e.getMessage(), e);
        }
    }
}