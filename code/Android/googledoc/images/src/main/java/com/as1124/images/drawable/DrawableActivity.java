package com.as1124.images.drawable;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ImageView;

import com.as1124.images.R;
import com.as1124.images.views.CustomerDrawable;

/**
 * Android Drawable资源介绍
 * @author as-1124 (mailto:as1124huang@gmail.com)
 */
public class DrawableActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawable);

        findViewById(R.id.but_change_alpha).setOnClickListener(v -> {
            findViewById(R.id.img_drawable).setAlpha(0.2f);
            findViewById(R.id.img_drawable).invalidate();
        });

        TransitionDrawable transition = (TransitionDrawable) getResources().getDrawable(R.drawable.img_xml);
        ImageView imageView = findViewById(R.id.img_transition_drawable);
        imageView.setImageDrawable(transition);
        transition.startTransition(7000);

        findViewById(R.id.but_to_nine).setOnClickListener(v -> {
            Intent intent = new Intent(DrawableActivity.this, NinePatchActivity.class);
            startActivity(intent);
        });

        ImageView customerImage = findViewById(R.id.img_drawable_customer);
        CustomerDrawable myDrawable = new CustomerDrawable();
        customerImage.setImageDrawable(myDrawable);

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        android.util.Log.i(getClass().getSimpleName(), "屏幕宽像素值==" + metrics.widthPixels);
    }

}
