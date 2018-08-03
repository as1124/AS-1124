package com.as1124.ch8test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoActivity extends Activity implements View.OnClickListener {

    public static final int VIDEO_PLAY = 0x1;

    private VideoView videoView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        videoView = findViewById(R.id.ch8_videoview);
        /**************** 初始化完成时调用 *****************************/
        videoView.setOnPreparedListener(mediaPlayer -> {
            int timeCount = videoView.getDuration();
            android.util.Log.i(VideoActivity.class.getSimpleName(), "Video OnPrepared: 时长=" + timeCount);
        });

        /**************** 多媒体播放时时间处理 MediaPlayer#MEDIA_INFO_ *******************/
        videoView.setOnInfoListener((mediaPlayer, what, extra) -> {
            int timeCount = videoView.getDuration();
            android.util.Log.i(VideoActivity.class.getSimpleName(), "Video OnInfoListener: 时长=" + timeCount);
            return true;
        });

        /******************** 播放结束后调用 ******************************************/
        videoView.setOnCompletionListener(mediaPlayer -> {
            int timeCount = videoView.getDuration();
            android.util.Log.i(VideoActivity.class.getSimpleName(), "Video OnComplete: 时长=" + timeCount);
        });
        findViewById(R.id.ch8_but_videostart).setOnClickListener(this);
        findViewById(R.id.ch8_but_videopause).setOnClickListener(this);
        findViewById(R.id.ch8_but_videoreplay).setOnClickListener(this);
        findViewById(R.id.ch8_but_videorestop).setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, VIDEO_PLAY);
            } else {
                initVideoPlay();
            }
        } else {
            initVideoPlay();
        }
    }

    private void initVideoPlay() {
        File file = new File(Environment.getExternalStorageDirectory(), "heiheihei.mp4");
        if (file.exists()) {
            videoView.setVideoPath(file.getPath());
        } else {
            videoView.suspend();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (VIDEO_PLAY == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initVideoPlay();
            } else {
                Toast.makeText(this, "拒绝权限无法播放音频", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.suspend();
        }
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.ch8_but_videostart:
                if (!videoView.isPlaying()) {
                    videoView.start();
                }
                break;
            case R.id.ch8_but_videopause:
                if (videoView.isPlaying()) {
                    videoView.pause();
                }
                break;
            case R.id.ch8_but_videoreplay:
                if (videoView.isPlaying()) {
                    videoView.resume();
                }
                break;
            case R.id.ch8_but_videorestop:
                if (videoView.isPlaying()) {
                    videoView.suspend();
                    initVideoPlay();
                }
                break;
            default:
                break;
        }
    }
}
