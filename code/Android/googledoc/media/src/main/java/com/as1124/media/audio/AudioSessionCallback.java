package com.as1124.media.audio;

import android.content.Intent;
import android.media.session.MediaSession;
import android.util.Log;
import android.view.KeyEvent;

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

    @Override
    public boolean onMediaButtonEvent(Intent mediaButtonIntent) {
        Log.i(TAG, "Audio onMediaButtonEvent");
        String action = mediaButtonIntent.getAction();
        KeyEvent keyEvent = mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
        int a = keyEvent.getKeyCode();
        if (KeyEvent.KEYCODE_MEDIA_PLAY == a) {
            Log.i(TAG, "硬件控制器：播放Audio");
        }
        return super.onMediaButtonEvent(mediaButtonIntent);

    }
}
