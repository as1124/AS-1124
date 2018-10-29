package com.as1124.media.audio;

import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.service.media.MediaBrowserService;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AudioBrowserService extends MediaBrowserService {

    private static final String MY_MEDIA_ROOT_ID = "media_root_id";
    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";

    private static MediaSession mediaSession;

    public AudioBrowserService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (mediaSession == null) {
            // create a MediaSession
            mediaSession = new MediaSession(this, "huangjw-audio-session");
        }

        // Enable callbacks from MediaButtons and TransportControls
        mediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        PlaybackState.Builder stateBuilder = new PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY | PlaybackState.ACTION_PAUSE
                        | PlaybackState.ACTION_PLAY_PAUSE | PlaybackState.ACTION_STOP);
        PlaybackState playbackState = stateBuilder.build();
        mediaSession.setPlaybackState(playbackState);

        // handel callbacks from the media controller
        mediaSession.setCallback(new AudioSessionCallback());
        mediaSession.setActive(true);

        // set the session's token so that client activities can communicate with it
        setSessionToken(mediaSession.getSessionToken());
    }

    @Override
    public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
        Log.i(getClass().getSimpleName(), "接受连接：" + clientPackageName + "--" + clientUid);

        if ("com.as1124.media".equals(clientPackageName)) {
            return new BrowserRoot(MY_MEDIA_ROOT_ID, null);
        } else {
            // 拒绝其他应用连接
            return new BrowserRoot(MY_EMPTY_MEDIA_ROOT_ID, null);
        }
    }

    @Override
    public void onLoadChildren(String parentId, Result<List<MediaBrowser.MediaItem>> result) {
        // Browsing not allowed
        if (TextUtils.equals(MY_EMPTY_MEDIA_ROOT_ID, parentId)) {
            result.sendResult(Collections.EMPTY_LIST);
            return;
        }

        // Assume  for example that the music catalog is already loaded/cached
        List<MediaBrowser.MediaItem> mediaItems = new ArrayList<>();

        // Check if this is the root menu
        if (MY_MEDIA_ROOT_ID.equals(parentId)) {
            // Build the MediaItem objects for the top level, and put them in the mediaItems list...

        } else {
            // Examine the passed parentId to see which submenu we're at,
            // and put the children of that menu in the mediaItems list...
        }
        result.sendResult(mediaItems);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaSession.release();
    }

    public static MediaSession getMediaSession() {
        return mediaSession;
    }
}
