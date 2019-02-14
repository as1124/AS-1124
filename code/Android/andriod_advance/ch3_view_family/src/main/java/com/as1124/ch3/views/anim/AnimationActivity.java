package com.as1124.ch3.views.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.as1124.ch3.views.R;

/**
 * 属性动画，相对于View动画提出. View动画不具有交互性，因为执行过后交互的触发区域仍然停留在原来的位置(即使fillAfter=true)
 * <ul>
 * <li>{@link ObjectAnimator}</li>
 * <li>{@link AnimatorSet}</li>
 * <li>{@link android.animation.AnimatorInflater}; 区别于在xml中定义View动画, 这里的文件夹名称<strong>【animator】</strong></li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AnimationActivity extends Activity {

    private AnimatorSet animatorSet;
    private Button startBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        startBut = findViewById(R.id.but_start_anim);
        startBut.setOnClickListener(v -> startObjectAnimation());

        findViewById(R.id.but_animator_stop).setOnClickListener(v -> stopAnimator());
        findViewById(R.id.but_animator_holder).setOnClickListener(v -> propertyValuesHolder4());
        findViewById(R.id.but_animator_xml).setOnClickListener(v -> loadFromXML());
    }

    private void startObjectAnimation() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(startBut, "translationX", 0f, 200f, 400f);
        animator1.setRepeatMode(ObjectAnimator.REVERSE);
        animator1.setRepeatCount(ObjectAnimator.INFINITE);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(startBut, "scaleX", 1, 2, 0.5f);
        animator2.setRepeatMode(ObjectAnimator.REVERSE);
        animator2.setRepeatCount(ObjectAnimator.INFINITE);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(startBut, "scaleY", 1, 2, 0.5f);
        animator3.setRepeatMode(ObjectAnimator.REVERSE);
        animator3.setRepeatCount(ObjectAnimator.INFINITE);

        ObjectAnimator animator4 = ObjectAnimator.ofFloat(startBut, "rotationX", 0, 90, 180);
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

        animatorSet = new AnimatorSet();
        animatorSet.setDuration(3000);
        animatorSet.play(animator1).with(animator2).with(animator3).after(animator4);
        animatorSet.start();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 200, 400);
        valueAnimator.setTarget(this);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(anim -> {
            Float va = (Float) anim.getAnimatedValue();
            Log.i("ValueAnimator", "Random value = " + va);
        });
        valueAnimator.start();
    }

    private void stopAnimator() {
        if (animatorSet != null) {
            animatorSet.cancel();
            startBut.clearAnimation();
        }
    }

    private void propertyValuesHolder4() {
        PropertyValuesHolder valuesHolder1 = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 2f, 0.8f);
        PropertyValuesHolder valuesHolder2 = PropertyValuesHolder.ofFloat("rotationX", 0, 90f, 0);
        PropertyValuesHolder valuesHolder3 = PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.2f, 1.0f);

        ObjectAnimator.ofPropertyValuesHolder(startBut, valuesHolder1, valuesHolder2, valuesHolder3)
                .setDuration(6000).start();
    }

    private void loadFromXML() {
        Animator animator = AnimatorInflater.loadAnimator(getBaseContext(), R.animator.as1124);
        animator.setTarget(startBut);
        animator.start();
    }
}
