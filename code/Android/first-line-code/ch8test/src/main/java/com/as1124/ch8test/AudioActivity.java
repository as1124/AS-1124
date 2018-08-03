package com.as1124.ch8test;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class AudioActivity extends Activity implements View.OnClickListener {

    public static final int AUDIO_PLAY = 0x1;

    private MediaPlayer mediaPlayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        findViewById(R.id.ch8_but_audiostart).setOnClickListener(this);
        findViewById(R.id.ch8_but_audiopause).setOnClickListener(this);
        findViewById(R.id.ch8_but_audiostop).setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUDIO_PLAY);
            } else {
                initMediaPlayer();
            }
        } else {
            initMediaPlayer();
        }
    }

    private void initMediaPlayer() {
        try {
            File audioFile = new File(Environment.getExternalStorageDirectory(), "huangjw.mp3");
            if (audioFile.exists()) {
                mediaPlayer.setDataSource(audioFile.getPath());
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.prepare();
            } else {
                mediaPlayer.release();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (AUDIO_PLAY == requestCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initMediaPlayer();
            } else {
                Toast.makeText(this, "拒绝权限无法播放音频", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.ch8_but_audiostart:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }
                break;
            case R.id.ch8_but_audiopause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                }
                break;
            case R.id.ch8_but_audiostop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
