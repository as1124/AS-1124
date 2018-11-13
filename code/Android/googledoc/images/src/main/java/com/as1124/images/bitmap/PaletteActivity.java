package com.as1124.images.bitmap;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import android.widget.Toolbar;

import com.as1124.images.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

/**
 * 通过{@link Palette}调色板来实现导航栏、状态栏、actionBar背景色和Theme主色调保持一致的能力
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class PaletteActivity extends Activity {

    private Toolbar toolbar;
    private Palette.Swatch vibrantSwatch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);

        toolbar = findViewById(R.id.toolbar_palette);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageView imageView = findViewById(R.id.img_palette);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.myaaa);
        RequestManager requestManager = Glide.with(imageView);
        if (bitmap == null) {
            requestManager.load("http://pic1.win4000.com/mobile/3/59a50c32d3f53.jpg").into(imageView);
        } else {
            requestManager.load(bitmap).into(imageView);
        }

        //Generate the palette and get the vibrant swatch
        Palette palette = Palette.from(bitmap).generate();
        vibrantSwatch = palette.getVibrantSwatch();

        findViewById(R.id.but_change_toolbar).setOnClickListener(v -> changeColor());
    }

    private void changeColor() {
        // check that the Vibrant swatch is available
        if (vibrantSwatch != null) {
            int backgroundColor = vibrantSwatch.getRgb();
            int textColor = vibrantSwatch.getTitleTextColor();
            toolbar.setBackgroundColor(backgroundColor);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                toolbar.setTitleTextColor(textColor);
            }
        }
    }
}
