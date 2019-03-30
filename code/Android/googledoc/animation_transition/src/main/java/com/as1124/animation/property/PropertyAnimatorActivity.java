package com.as1124.animation.property;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;

import com.as1124.animation.R;

import java.text.MessageFormat;

/**
 * 属性动画
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class PropertyAnimatorActivity extends Activity {

    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animator);

        view = findViewById(R.id.view_property_animation);
        findViewById(R.id.but_linear_animator).setOnClickListener(v -> linearMove());
        findViewById(R.id.but_accelerate_animator).setOnClickListener(v -> accelerateMove());
        findViewById(R.id.but_anticipate_animator).setOnClickListener(v -> anticipateMove());
        findViewById(R.id.but_bounce_animator).setOnClickListener(v -> bounceMove());
        findViewById(R.id.but_cycle_animator).setOnClickListener(v -> cycleMove());
    }

    private void linearMove() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 10, 900);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> formatLog(animation));
        animator.start();
    }

    private void accelerateMove() {
        // 默认差值器是 AccelerateDecelerateInterpolator
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 10, 900);
        animator.setDuration(4000).addUpdateListener(animation -> formatLog(animation));
        animator.start();
    }

    private void anticipateMove() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 10, 900);
        animator.setDuration(4000);
        animator.setInterpolator(new AnticipateInterpolator());
        animator.addUpdateListener(animation -> formatLog(animation));
        animator.start();
    }

    private void bounceMove() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 10, 900);
        animator.setDuration(4000);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(animation -> formatLog(animation));

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 10, 600);
        animator2.setDuration(4000);
        animator2.setInterpolator(new BounceInterpolator());

        AnimatorSet set = new AnimatorSet();
        set.play(animator).with(animator2);
        set.start();
    }

    private void cycleMove() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 10, 900);
        animator.setDuration(4000);
        animator.setInterpolator(new CycleInterpolator(6.5f));
        animator.addUpdateListener(animation -> formatLog(animation));
        animator.start();
    }

    private void formatLog(ValueAnimator animation) {
        long duration = animation.getDuration();
        String currentPropertyValue = animation.getAnimatedValue().toString();
        long passedTime = animation.getCurrentPlayTime();
        float persent = animation.getAnimatedFraction();
        String name = "";
        TimeInterpolator interpolator = animation.getInterpolator();
        if (interpolator != null) {
            name = interpolator.getClass().getSimpleName();
        }
        Log.i("Animator", MessageFormat.format("动画当前属性值 {0}, 时间比 {1}/{2} = {3}, 插值器类型 {4}",
                currentPropertyValue, passedTime, duration, persent, name));
    }
}
