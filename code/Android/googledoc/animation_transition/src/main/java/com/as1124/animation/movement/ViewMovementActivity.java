package com.as1124.animation.movement;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.PathInterpolator;

import com.as1124.animation.R;


/**
 * 动画完成View的移动
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ViewMovementActivity extends Activity {

    private View mView;
    private View scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_movement);
        mView = findViewById(R.id.view_to_move);

        findViewById(R.id.but_move_animator).setOnClickListener(v -> moveWithObjectAnimator());
        findViewById(R.id.but_move_path).setOnClickListener(v -> moveInPath());
//        scrollView = findViewById(R.id.layout_to_fling);
        scrollView = findViewById(R.id.h_scroll_view);
        findViewById(R.id.but_move_fling).setOnClickListener(v -> flingAnimation());

    }

    private void moveWithObjectAnimator() {
        // 如果起始位置不在200, 则会先跳过去然后做200->500的移动动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(mView, View.TRANSLATION_X, 200, 500);
        animator.setDuration(500).start();
    }

    private void moveWithPath() {
        if (Build.VERSION.SDK_INT >= 21) {
            Path path = new Path();
//            path.arcTo(0, 0, 0, 0, 270, -180, true);
            path.lineTo(0.25f, 0.25f);
            path.moveTo(0.25f, 0.5f);
            path.lineTo(1f, 1f);
            PathInterpolator interpolator = new PathInterpolator(path);

            ObjectAnimator animator = ObjectAnimator.ofFloat(mView, View.TRANSLATION_X, 200);
            animator.setInterpolator(interpolator);
            animator.setDuration(2000);
            animator.start();
        }
    }

    private void moveInPath() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Path path = new Path();
            path.arcTo(0f, 0f, 1000f, 1000f, 270f, -180f, true);
            ObjectAnimator animator = ObjectAnimator.ofFloat(mView, View.X, View.Y, path);
            animator.setDuration(2000);
            animator.start();
        }
    }

    private void flingAnimation() {
        // View的X轴的滑动上添加Fling效果
//        FlingAnimation animation = new FlingAnimation(scrollView, DynamicAnimation.SCROLL_X);
        // 初始加速度10dp每秒
//        float velocity = 10 * getResources().getDisplayMetrics().density;
//        animation.setStartVelocity(-velocity)
//                .setMinValue(0)
//                .setMaxValue(1400)
//                .setFriction(1.1f).start();
    }

}
