package com.as1124.ch8test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 第8章通知、多媒体内容学习
 *
 * @author as-1124 (mailto: as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.ch8_but_tonotify).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.ch8_but_tocamera).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CameraActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.ch8_but_toaudio).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AudioActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.ch8_but_tovideo).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, VideoActivity.class);
            startActivity(intent);
        });
    }
}
