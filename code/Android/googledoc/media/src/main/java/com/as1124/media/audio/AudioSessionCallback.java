package com.as1124.media.audio;

import android.media.session.MediaSession;
import android.util.Log;

/**
 * 音频播放控制信息回调
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AudioSessionCallback extends MediaSession.Callback {

    private static final String TAG = AudioSessionCallback.class.getSimpleName();

    @Override
    public void onPrepare() {
        super.onPrepare();
        Log.i(TAG, "Audio onPrepare");
    }

    @Override
    public void onPlay() {
        super.onPlay();
        Log.i(TAG, "Audio onPlay");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "Audio onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "Audio onStop");
    }
}
