package com.as1124.media.music;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.as1124.media.R;
import com.as1124.selflib.MediaUtils;
import com.as1124.selflib.WindowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author as-1124(as1124huang@gmail.com)
 */
public class MusicPlayerActivity extends Activity implements View.OnClickListener {

    private List<MediaBrowser.MediaItem> mSongs = new ArrayList<>();

    private RecyclerView mSongListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);
        WindowUtils.fullScreen(this, Color.TRANSPARENT, true);
        mSongs = MediaUtils.queryAudioItem(getApplicationContext());

        findViewById(R.id.image_back).setOnClickListener(this);
        findViewById(R.id.image_more).setOnClickListener(this);
        mSongListView = findViewById(R.id.list_songs);
        mSongListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mSongListView.setAdapter(new MusicListAdapter(this));
        findViewById(R.id.image_play).setOnClickListener(this);
        findViewById(R.id.image_playlist).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onClick(View v) {
        int viewID = v.getId();
        switch (viewID) {
            case R.id.image_back:
                this.finish();
                break;
            case R.id.image_more:
                break;
            case R.id.image_play:
                break;
            case R.id.image_playlist:
                break;
            default:
                break;
        }
    }

    public List<MediaBrowser.MediaItem> getSongs() {
        return this.mSongs;
    }
}