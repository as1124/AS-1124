package com.as1124.media;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.as1124.media.audio.AudioActivity;
import com.as1124.media.video.VideoActivity;
import com.as1124.selflib.MediaUtils;
import com.as1124.selflib.WindowUtils;

/**
 * 多媒体处理，音频、视频的播放
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowUtils.fullScreen(this);

        // 音乐播放器
        findViewById(R.id.but_to_audio).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, AudioActivity.class))
        );

        // 录音机
        findViewById(R.id.but_to_record).setOnClickListener(v -> {
        });

        // 视频播放器
        findViewById(R.id.but_to_video).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, VideoActivity.class))
        );

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO}, 1111);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1111) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "权限申请成功", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this, "拒绝了权限申请", Toast.LENGTH_SHORT).show();
    }
}
