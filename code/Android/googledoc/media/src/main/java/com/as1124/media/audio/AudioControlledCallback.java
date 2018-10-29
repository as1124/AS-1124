package com.as1124.media.audio;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class AudioControlledCallback extends MediaController.Callback {

    private static final String TAG = AudioControlledCallback.class.getSimpleName();

    @Override
    public void onSessionDestroyed() {
        super.onSessionDestroyed();

        Log.i(TAG, "onSessionDestroyed!!");
    }

    @Override
    public void onSessionEvent(String event, Bundle extras) {
        super.onSessionEvent(event, extras);

        Log.i(TAG, "onSessionEvent===" + event);
    }

    @Override
    public void onPlaybackStateChanged(PlaybackState state) {
        super.onPlaybackStateChanged(state);
        Log.i(TAG, "onPlaybackStateChanged===" + state.getState());
    }

    @Override
    public void onMetadataChanged(MediaMetadata metadata) {
        super.onMetadataChanged(metadata);
        Log.i(TAG, "onMetadataChanged===" + metadata.keySet().toString());
    }

    @Override
    public void onQueueChanged(List<MediaSession.QueueItem> queue) {
        super.onQueueChanged(queue);
    }

    @Override
    public void onQueueTitleChanged(CharSequence title) {
        super.onQueueTitleChanged(title);
    }

    @Override
    public void onExtrasChanged(Bundle extras) {
        super.onExtrasChanged(extras);
    }

    @Override
    public void onAudioInfoChanged(MediaController.PlaybackInfo info) {
        super.onAudioInfoChanged(info);
    }
}
