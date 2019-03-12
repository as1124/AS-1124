package com.as1124.animation.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.as1124.animation.R;

/**
 * 帧动画、试图动画示例
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ViewAnimationActivity extends Activity {

    private AnimationDrawable wifiFrame;
    private View anotherContent;
    private ProgressBar loadingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_animation);

        ImageView imageFrameAnimation = findViewById(R.id.image_frame_animation);
        Drawable drawable = getResources().getDrawable(R.drawable.frame_animation);
        if (drawable != null && drawable instanceof AnimationDrawable) {
            wifiFrame = (AnimationDrawable) drawable;
            imageFrameAnimation.setImageDrawable(wifiFrame);
        }

        anotherContent = findViewById(R.id.framelayout_content);
        anotherContent.setVisibility(View.GONE);
        anotherContent.setAlpha(0);
        loadingView = findViewById(R.id.progress_loading);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Google 官方建议: Not to call on onCreate() method cause the AnimationDrawable is not yet fully
        // attached to the window, better call on onStart().
        if (wifiFrame != null) {
            wifiFrame.start();
        }

        // Animate the content view to 100% opacity, and clear any animation listener set on the view.
        anotherContent.setVisibility(View.VISIBLE);
        ViewPropertyAnimator viewAnimator = anotherContent.animate();
        viewAnimator.alpha(1.0f).setDuration(2000);

        // Animate the loading view to 0 opacity, after the animation ends, set its visibility to GONE
        // as an optimization step
        loadingView.animate().alpha(0).setDuration(2000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loadingView.setVisibility(View.GONE);
            }
        });

    }

}
