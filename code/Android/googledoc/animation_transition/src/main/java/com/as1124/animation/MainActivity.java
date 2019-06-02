package com.as1124.animation;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.as1124.animation.movement.ViewMovementActivity;
import com.as1124.animation.property.PropertyAnimatorActivity;
import com.as1124.animation.view.ViewAnimationActivity;
import com.as1124.selflib.WindowUtils;

/**
 * Android动画、场景过度动效
 * <ol>
 * <li>帧动画：{@link android.graphics.drawable.AnimationDrawable}</li>
 * <li>View动画：</li>
 * <li>Property动画：</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_frame).setOnClickListener(v ->
                startActivity(new Intent(this, FrameAnimationActivity.class)));
        findViewById(R.id.but_to_animation).setOnClickListener(v ->
                startActivity(new Intent(this, ViewAnimationActivity.class)));
        findViewById(R.id.but_to_animator).setOnClickListener(v ->
                startActivity(new Intent(this, PropertyAnimatorActivity.class)));

        if (Build.VERSION.SDK_INT >= 21) {
            StateListAnimator animator = AnimatorInflater.loadStateListAnimator(getBaseContext(), R.animator.button_state_animator);
            findViewById(R.id.but_animator_state_list).setStateListAnimator(animator);
        }

        findViewById(R.id.but_to_view_move).setOnClickListener(v ->
                startActivity(new Intent(this, ViewMovementActivity.class)));
        findViewById(R.id.but_to_zoom).setOnClickListener(v ->
                startActivity(new Intent(this, ZoomActivity.class)));

        WindowUtils.fullScreen(this);
    }
}
