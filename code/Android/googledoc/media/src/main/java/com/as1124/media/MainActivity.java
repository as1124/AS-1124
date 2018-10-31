package com.as1124.media;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.media.audio.AudioActivity;
import com.as1124.media.video.VideoActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 音乐播放器
        findViewById(R.id.but_to_audio).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AudioActivity.class);
            startActivity(intent);
        });

        // 录音机
        findViewById(R.id.but_to_record).setOnClickListener(v->{

        });

        // 视频播放器
        findViewById(R.id.but_to_video).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
            startActivity(intent);
        });

    }
}
