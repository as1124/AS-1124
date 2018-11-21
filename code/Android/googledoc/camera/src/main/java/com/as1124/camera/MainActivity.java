package com.as1124.camera;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
 * <ol>
 * <li>调用系统相机拍照</li>
 * <li>FileProvider: 用于应用间文件URI共享(Android 7.0及以上)</li>
 * <li>自动扫描多媒体信息并保存到database, {@link Intent#ACTION_MEDIA_SCANNER_SCAN_FILE}</li>
 * <li>应用内打开相机捕获图像或视频</li>
 * </ol>
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
        } else {
            Toast.makeText(this, "手机有摄像头", Toast.LENGTH_SHORT).show();
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
        findViewById(R.id.but_media_scanner).setOnClickListener(v -> testAutoShare2Gallery());
        findViewById(R.id.but_scale_photo).setOnClickListener(v -> decodeImageSize());
        findViewById(R.id.but_to_selfcamera).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this, SelfCameraActivity.class)));
        findViewById(R.id.but_to_selfvideo).setOnClickListener(
                v -> startActivity(new Intent(MainActivity.this, SelfVideoActivity.class)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 请求相机和存储权限
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO
                }, 1234);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("data")) {
                Object bitmap = data.getExtras().get("data");
                if (bitmap != null) {
                    imageView.setImageBitmap((Bitmap) bitmap);
                }
            }
        } else if (requestCode == 1 && resultCode == RESULT_OK) {
            Toast.makeText(this, "拍照保存成功", Toast.LENGTH_SHORT).show();
        } else if (requestCode == 11 && resultCode == RESULT_OK) {
            Toast.makeText(this, "拍照保存成功, 请检查Media_Scanner", Toast.LENGTH_SHORT).show();
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
            if (!storeDir.exists()) {
                storeDir.mkdirs();
            }
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

    /**
     * 默认保存到SD卡上非公共多媒体文件夹中的图片是不会被系统多媒体资源管理器扫描到的，
     * 即：拍照保存之后在相册中看不到该照片，其他APP也不知道该照片的存在（在SD卡中是能找到该文件的）
     */
    private void testAutoShare2Gallery() {
        // 创建的资源对其他APP是private的
        File storeDir = Environment.getExternalStorageDirectory();
        try {
            File image = new File(storeDir, "huangjw/JPEG_camera_test.jpg");
            Uri imageUri = FileProvider.getUriForFile(this, "com.as1124.camera.fileprovider", image);
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, 11);

                // ATTENTION 如果这里使用FileProvider反而扫描不到
                // ATTENTION 是否应该在onActivityResult中处理扫描
                Intent shareIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                shareIntent.setData(Uri.fromFile(image));
                sendBroadcast(shareIntent);
            }
        } catch (Exception e) {
            Log.e("Camera-Image", e.getMessage(), e);
        }
    }

    /**
     * 缩放图像，按照ImageView的实际大小缩放显示原始图片，这样可以避免OOM，节省内存
     */
    private void decodeImageSize() {
        int targetHeight = imageView.getHeight();
        int targetWidth = imageView.getWidth();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img1419);
        int realHeight = bitmap.getHeight();
        int realWidth = bitmap.getWidth();

        // Determine how much to scale down the image
        int scaleFactor = Math.min(realWidth / targetWidth, realHeight / targetHeight);

        // Decode the image file into a Bitmap sized to fill the view
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        bitmap.recycle();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.img1419, options);
        imageView.setImageBitmap(bitmap);
    }
}