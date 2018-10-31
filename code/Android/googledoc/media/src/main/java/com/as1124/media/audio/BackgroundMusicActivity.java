package com.as1124.media.audio;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.media.R;

/**
 * 类似于系统、QQ音乐那样的播放器；可以后台锁屏播放，也可以接受系统多媒体按键的控
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class BackgroundMusicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_music);
    }
}
