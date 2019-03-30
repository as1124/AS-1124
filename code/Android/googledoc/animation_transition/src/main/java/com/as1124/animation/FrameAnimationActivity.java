package com.as1124.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class FrameAnimationActivity extends Activity {

    private AnimationDrawable wifiFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);

        ImageView imageFrameAnimation = findViewById(R.id.image_frame_animation);
        Drawable drawable = getResources().getDrawable(R.drawable.frame_animation);
        if (drawable != null && drawable instanceof AnimationDrawable) {
            wifiFrame = (AnimationDrawable) drawable;
            imageFrameAnimation.setImageDrawable(wifiFrame);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Google 官方建议: Not to call on onCreate() method cause the AnimationDrawable is not yet fully
        // attached to the window, better call on onStart().
        if (wifiFrame != null) {
            wifiFrame.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (wifiFrame != null && wifiFrame.isRunning()) {
            wifiFrame.stop();
        }
    }
}
