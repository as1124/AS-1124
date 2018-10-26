package com.as1124.media.audio;

import android.app.Activity;
import android.media.session.MediaSession;
import android.os.Bundle;

import com.as1124.media.R;

public class AudioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        MediaSession session = new MediaSession(this, "");
        session.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS);
    }
}
