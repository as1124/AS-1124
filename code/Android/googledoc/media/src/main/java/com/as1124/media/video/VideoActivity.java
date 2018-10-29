package com.as1124.media.video;

import android.app.Activity;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;

import com.as1124.media.R;

public class VideoActivity extends Activity {

    private MediaSession mediaSession;

    private PlaybackState.Builder stateBuilder;

    private MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        // create a MediaSession
        mediaSession = new MediaSession(getApplicationContext(), "Huangjw-VideoPlayer");

        // Enable callbacks from MediaButtons and TransportControls
        mediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible
        mediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        stateBuilder = new PlaybackState.Builder();
        stateBuilder.setActions(PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PLAY_PAUSE
                | PlaybackState.ACTION_STOP);
        mediaSession.setPlaybackState(stateBuilder.build());

        mediaSession.setCallback(new VideoSessionCallback());

        mediaController = new MediaController(this, mediaSession.getSessionToken());
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}
