package com.as1124.media.audio;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class MyAudioPlayManager {

    private Context context;

    public MyAudioPlayManager(Context context) {
        this.context = context;
    }

    public void init() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(context.getApplicationContext(), Uri.parse(""));
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), e.getMessage());
        }
    }

}
