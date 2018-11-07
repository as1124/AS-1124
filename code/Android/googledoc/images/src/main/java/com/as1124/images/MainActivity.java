package com.as1124.images;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.as1124.images.bitmap.BitmapActivity;
import com.as1124.images.bitmap.PaletteActivity;
import com.as1124.images.drawable.DrawableActivity;
import com.as1124.images.selector.ImageSelectorActivity;
import com.as1124.images.utils.WindowUtils;

/**
 * 资源处理时一定要注意的就是OOM问题，不管是Drawable还是Bitmap都需要注意，
 * 推荐使用Glide处理图像
 * <ol>
 * <li>drawable资源处理及展现，图片、自定义drawable绘制</li>
 * <li>Bitmap资源处理</li>
 * <li>Hardware acceleration 绘图，从app/activity/window/view四个层面可以控制</li>
 * <li>实现了类似于微信选择图片的多选功能</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowUtils.fullScreen(this);

        findViewById(R.id.but_to_drawable).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrawableActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.but_to_bitmap).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BitmapActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.but_palette).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PaletteActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.but_to_select_image).setOnClickListener(
                v -> ImageSelectorActivity.startForResult(this, 1122, true, false, 3));

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 111);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (111 == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (1122 == requestCode) {
            if (RESULT_OK == resultCode) {
                String[] paths = data.getStringArrayExtra(ImageSelectorActivity.KEY_RESULT);
                Log.i("IMAGE_SELECTOR", paths.toString());
            } else if (RESULT_CANCELED == resultCode) {
                Toast.makeText(this, "用户取消了", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
