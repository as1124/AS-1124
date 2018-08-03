package com.as1124.ch8test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class CameraActivity extends Activity implements View.OnClickListener {

    /**
     * RequestCode for activity_result
     */
    public static final int TAKE_PHOTO = 0x1;

    /**
     * RequestCode for activity_result
     */
    public static final int CHOOSE_PHOTO = 0x2;

    private ImageView photoView = null;

    private Uri imageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        photoView = findViewById(R.id.ch8_image_camera);
        findViewById(R.id.ch8_but_systemcamera).setOnClickListener(this);
        findViewById(R.id.ch8_but_selectphoto).setOnClickListener(this);
    }

    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (TAKE_PHOTO == requestCode && Activity.RESULT_OK == resultCode) {
            // 系统拍照成功
            try {
                Bitmap picture = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                if (picture != null) {
                    photoView.setImageBitmap(picture);
                }
            } catch (IOException e) {
                Log.e("=====ch8test=====", e.getMessage(), e);
                Toast.makeText(this, "拍照错误" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } else if (CHOOSE_PHOTO == requestCode && Activity.RESULT_OK == resultCode) {
            // 需要判断手机系统版本号
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4及以上系统使用这个方法处理图片
                handleImageUpon19(data);
            } else {
                // 4.4以下系统使用该方法处理图片
                handleImageBelow19(data);
            }
        }
    }

    /**
     * API 19及以上处理图片选择后的路径解析
     *
     * @param data 数据
     */
    @TargetApi(19)
    private void handleImageUpon19(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document-id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; //解析数字格式的id
                String selection = MediaStore.Images.Media._ID.concat("=").concat(id);
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 content 类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是 file 类型的Uri，直接获取图片路径
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    /**
     * API 19以下图片选择后的结果处理
     *
     * @param data 数据
     */
    private void handleImageBelow19(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;

        // 通过Uri和 selection 来获取真实的图片路径
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(uri, null, selection, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (TextUtils.isEmpty(imagePath)) {
            Toast.makeText(this, "Failed to get image!", Toast.LENGTH_SHORT).show();
        } else {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                photoView.setImageBitmap(bitmap);
            }
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (CHOOSE_PHOTO == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum();
            } else {
                Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.ch8_but_systemcamera:
                // 通过 getExternalCacheDir、getExternalFilesDir 不需要申声明SD卡使用权限
                File imageFile = new File(getExternalCacheDir(), "asIvy.jpg");
//                File imageFile = new File(getExternalFilesDir(null), "ch8test/asIvy.jpg");

                // 外部存储使用需要声明SD读写权限
//                File imageFile = new File(Environment.getExternalStorageDirectory(), "ch8test/asIvy.jpg");
                if (!imageFile.getParentFile().exists()) {
                    imageFile.getParentFile().mkdirs();
                }
                if (imageFile.exists()) {
                    imageFile.delete();
                    try {
                        imageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (Build.VERSION.SDK_INT >= 24) {
                    imageUri = FileProvider.getUriForFile(CameraActivity.this, "com.as1124.ivy", imageFile);
                } else {
                    imageUri = Uri.fromFile(imageFile);
                }

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_PHOTO);
                break;
            case R.id.ch8_but_selectphoto:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (CameraActivity.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        openAlbum();
                    } else {
                        // 没有权限则进行授权
                        CameraActivity.this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CHOOSE_PHOTO);
                    }
                } else {
                    openAlbum();
                }
                break;
        }
    }
}
