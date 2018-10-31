package com.as1124.media.audio;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.as1124.media.R;

/**
 * 音乐播放器设计模型为C/S形式
 * Server端：{@link android.service.media.MediaBrowserService}, {@link MediaSession}
 * Client端：{@link MediaBrowser}, {@link MediaController}
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AudioActivity extends Activity {

//    private MediaController mediaController;
//
//    private MediaController.Callback controlledCallback = new AudioControlledCallback();
//
//    private MediaBrowser mediaBrowser;
//    // 与MediaBrowserService通信状态回调
//    private MediaBrowser.ConnectionCallback browserConnectionCallback = new MediaBrowser.ConnectionCallback() {
//
//        @Override
//        public void onConnected() {
//            super.onConnected();
//            //Get the token form MediaSession
//            MediaSession.Token token = mediaBrowser.getSessionToken();
//
//            // Create a MediaController
//            mediaController = new MediaController(AudioActivity.this, token);
//            mediaController.registerCallback(controlledCallback);
//
//            // save the controller
//
//            // Finish building the UI
//            buildTransportControls();
//        }
//
//        @Override
//        public void onConnectionSuspended() {
//            // The Service has crashed. Disable transport controls until it
//            // automatically reconnects
//        }
//
//        @Override
//        public void onConnectionFailed() {
//            // The Service has refused our connection
//        }
//    };
//

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        findViewById(R.id.but_to_media_notification).setOnClickListener(v -> {
            showMediaNotification();
        });
        findViewById(R.id.but_to_foreground_music).setOnClickListener(v -> {
            Intent intent = new Intent(AudioActivity.this, ForegroundMusicActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.but_to_background_music).setOnClickListener(v -> {
            Intent intent = new Intent(AudioActivity.this, BackgroundMusicActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 显示多媒体样式的通知消息
     */
    public void showMediaNotification() {
        // ATTENTION 没处理完
        Notification.Builder builder = new Notification.Builder(AudioActivity.this);
        Intent intent = new Intent(AudioActivity.this, ForegroundMusicActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 111, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentTitle("AS-1124 Foreground Music Player")
                .setContentText("正在播放你妹妹")
                .setSubText("呵呵，SubText is a what ghost")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                // Add a pause button
                .addAction(new Notification.Action(R.drawable.audio_play, "播放", pIntent))
                // Take advantage of MediaStyle features
                .setStyle(new Notification.MediaStyle()
//                        .setMediaSession(AudioBrowserService.getMediaSession().getSessionToken())
                        .setShowActionsInCompactView(0));
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_audio);
//
//        findViewById(R.id.but_media_browser).setOnClickListener(v -> {
//            mediaBrowser = new MediaBrowser(this,
//                    new ComponentName(this, AudioBrowserService.class), browserConnectionCallback, null);
//            mediaBrowser.connect();
//        });
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (mediaController != null) {
//            mediaController.unregisterCallback(controlledCallback);
//            mediaController = null;
//        }
//        mediaBrowser.disconnect();
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    private void buildTransportControls() {
//        findViewById(R.id.but_audio_play).setOnClickListener(v -> {
//            if (mediaController != null) {
//                int state = mediaController.getPlaybackState().getState();
//                if (PlaybackState.STATE_PLAYING == state) {
//                    // 不做处理
//                } else if (PlaybackState.STATE_PAUSED == state) {
//                    mediaController.getTransportControls().play();
//                }
//            }
//        });
//        findViewById(R.id.but_audio_pause).setOnClickListener(v -> {
//            if (mediaController != null) {
//                int state = mediaController.getPlaybackState().getState();
//                if (state == PlaybackState.STATE_PLAYING) {
//                    mediaController.getTransportControls().pause();
//                }
//            }
//        });
//        findViewById(R.id.but_audio_stop).setOnClickListener(v -> {
//            if (mediaController != null) {
//                int state = mediaController.getPlaybackState().getState();
//                if (state != PlaybackState.STATE_STOPPED) {
//                    mediaController.getTransportControls().stop();
//                }
//            }
//        });
//
//        // Metadata和State描述了当前的播放状态和信息
//        MediaMetadata metadata = mediaController.getMetadata();
//        PlaybackState playbackState = mediaController.getPlaybackState();
//    }
//
//
//    /**
//     * 代码片段介绍8.0之前如何获取去Android系统忠的AudioFocus
//     */
//    protected void requestAudioFocusBefore8() {
//        AudioManager manager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        AudioManager.OnAudioFocusChangeListener changeListener = new AudioManager.OnAudioFocusChangeListener() {
//            @Override
//            public void onAudioFocusChange(int focusChange) {
//
//            }
//        };
//
//        // request audio focus for playback
//        int result = manager.requestAudioFocus(changeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
//        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//            Toast.makeText(this, "获取音频焦点成功", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @TargetApi(Build.VERSION_CODES.O)
//    protected void requestAudioFocusAfter8() {
//        AudioManager manager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        AudioAttributes playbackAttrs = new AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_MEDIA)
//                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
//
//        AudioManager.OnAudioFocusChangeListener changeListener = new AudioManager.OnAudioFocusChangeListener() {
//            @Override
//            public void onAudioFocusChange(int focusChange) {
//
//            }
//        };
//        AudioFocusRequest request = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
//                .setAudioAttributes(playbackAttrs)
//                .setAcceptsDelayedFocusGain(true)
//                .setOnAudioFocusChangeListener(changeListener).build();
//    }

}
