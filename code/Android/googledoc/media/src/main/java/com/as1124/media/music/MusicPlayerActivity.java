package com.as1124.media.music;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;

import com.as1124.media.R;
import com.as1124.selflib.MediaUtils;
import com.as1124.selflib.WindowUtils;

/**
 * @author as-1124(as1124huang@gmail.com)
 */
public class MusicPlayerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        WindowUtils.fullScreen(this);
        MediaUtils.queryAudioItem(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

}
