package com.as1124.ch3.views.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.as1124.ch3.views.R;

/**
 * 属性动画
 * <ul>
 * <li>{@link ObjectAnimator}</li>
 * <li>{@link AnimatorSet}</li>
 * <li>{@link android.animation.AnimatorInflater}</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AnimationActivity extends Activity {

    private Button startBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        startBut = findViewById(R.id.but_start_anim);
        startBut.setOnClickListener(v -> startObjectAnimation());
    }

    private void startObjectAnimation() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(startBut, "translationX", 0f, 400f, 0f);
        animator1.setRepeatMode(ObjectAnimator.RESTART);
        animator1.setRepeatCount(ObjectAnimator.INFINITE);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(startBut, "scaleX", 1, 2, 1);
        animator2.setRepeatMode(ObjectAnimator.RESTART);
        animator2.setRepeatCount(ObjectAnimator.INFINITE);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(startBut, "scaleY", 1, 2, 1);
        animator3.setRepeatMode(ObjectAnimator.RESTART);
        animator3.setRepeatCount(ObjectAnimator.INFINITE);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(startBut, "rotationX", 0, 90, 0);
        animator4.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                Toast.makeText(AnimationActivity.this, "动画结束", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                Toast.makeText(AnimationActivity.this, "动画播放", Toast.LENGTH_SHORT).show();
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.setDuration(3000);
        set.play(animator1).with(animator2).with(animator3).after(animator4);
        set.start();
    }
}
