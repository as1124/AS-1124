package com.as1124.ui.material;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.Toast;

import com.as1124.ui.R;

public class FloatingBarActivity extends Activity {

    private static int tintMode = 0;

    private FloatingActionButton floatingButton;
    private FloatingActionButton fabHome, fabToutiao, fabWeixin;

    private boolean isOpen = false;

    private View scaleTestView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab);

        floatingButton = findViewById(R.id.fab_left_top);
//        floatingButton.setBackgroundColor(Color.rgb(0, 155, 0));
//        floatingButton.setRippleColor(Color.rgb(255, 0, 0));
//        floatingButton.setExpanded(true);
//        floatingButton.show();
        fabHome = findViewById(R.id.fab_home);
        fabHome.setOnClickListener(v -> fabHomeClick());
        fabToutiao = findViewById(R.id.fab_touxiao);
        fabToutiao.setOnClickListener(v ->
                Toast.makeText(this, "点击今日头条", Toast.LENGTH_SHORT).show());
        fabWeixin = findViewById(R.id.fab_weixin);
        fabWeixin.setOnClickListener(v ->
                Toast.makeText(this, "点击微信", Toast.LENGTH_SHORT).show());

        findViewById(R.id.but_background_tint).setOnClickListener(v -> changeBackground());
        findViewById(R.id.but_change_alpha).setOnClickListener(v -> changeAlpha());


        scaleTestView = findViewById(R.id.view_scale_test);
        SeekBar seekBar = findViewById(R.id.seekbar_scale_test);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                scaleTestView.setPivotX(0.5f);
                scaleTestView.setPivotY(0.5f);
                float scaleRate = progress / 100.0f;
                scaleTestView.setScaleX(scaleRate);
                scaleTestView.setScaleY(scaleRate);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

    }

    private void changeBackground() {
        if (Build.VERSION.SDK_INT >= 23) {
            ColorStateList colorState = getResources().getColorStateList(R.color.fab_state_selector, null);
            floatingButton.setBackgroundTintList(colorState);
        }
        tintMode = tintMode % 17;
        switch (tintMode) {
            case 0:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.SRC);
                break;
            case 1:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.SRC_IN);
                break;
            case 2:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.SRC_ATOP);
                break;
            case 3:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.SRC_OUT);
                break;
            case 4:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.SRC_OVER);
                break;
            case 5:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.ADD);
                break;
            case 6:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.CLEAR);
                break;
            case 7:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.DARKEN);
                break;
            case 8:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.DST);
                break;
            case 9:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.DST_ATOP);
                break;
            case 10:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.DST_IN);
                break;
            case 11:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.DST_OUT);
                break;
            case 12:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.DST_OVER);
                break;
            case 13:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.LIGHTEN);
                break;
            case 14:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.OVERLAY);
                break;
            case 15:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.SCREEN);
                break;
            case 16:
                floatingButton.setBackgroundTintMode(PorterDuff.Mode.XOR);
                break;
        }
        tintMode++;
    }

    private void changeAlpha() {
        floatingButton.setAlpha(0.9f);
    }

    private void fabHomeClick() {
        if (isOpen) {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.fab_main_close);
            Animation itemClose = AnimationUtils.loadAnimation(this, R.anim.fab_item_close);
            fabHome.startAnimation(animation);
            fabToutiao.setVisibility(View.INVISIBLE);
            fabToutiao.startAnimation(itemClose);
            fabToutiao.setClickable(false);
            fabWeixin.setVisibility(View.INVISIBLE);
            fabWeixin.startAnimation(itemClose);
            fabWeixin.setClickable(false);
            fabHome.startAnimation(animation);

            isOpen = false;
        } else {
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.fab_main_open);
            Animation itemOpen = AnimationUtils.loadAnimation(this, R.anim.fab_item_open);
            fabHome.startAnimation(animation);
            fabToutiao.setVisibility(View.VISIBLE);
            fabToutiao.startAnimation(itemOpen);
            fabToutiao.setClickable(true);
            fabWeixin.setVisibility(View.VISIBLE);
            fabWeixin.startAnimation(itemOpen);
            fabWeixin.setClickable(true);

            isOpen = true;
        }
    }

}
