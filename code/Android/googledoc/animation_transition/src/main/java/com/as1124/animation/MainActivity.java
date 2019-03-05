package com.as1124.animation;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.as1124.animation.property.PropertyAnimatorActivity;
import com.as1124.animation.view.ViewAnimationActivity;

/**
 * Android动画、动效
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_animation).setOnClickListener(v ->
                startActivity(new Intent(this, ViewAnimationActivity.class)));
        findViewById(R.id.but_to_animator).setOnClickListener(v ->
                startActivity(new Intent(this, PropertyAnimatorActivity.class)));


        if (Build.VERSION.SDK_INT >= 21) {
            StateListAnimator animator = AnimatorInflater.loadStateListAnimator(getBaseContext(), R.animator.button_state_animator);
            findViewById(R.id.but_animator_state_list).setStateListAnimator(animator);
        }
    }
}
