package com.as1124.media.audio;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;

import com.as1124.media.MediaUtils;
import com.as1124.media.R;

/**
 * 演示只能前台播放音频的功能，且不接受耳机/MediaButton的控制
 *
 * @author as-1124(as1124huang@gmail.com)
 */
public class ForegroundMusicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_music);

        MediaUtils.queryLocalAudioItem(getApplicationContext());
    }


    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

}
