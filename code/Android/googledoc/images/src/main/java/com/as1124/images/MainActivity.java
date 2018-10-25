package com.as1124.images;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.images.bitmap.BitmapActivity;
import com.as1124.images.drawable.DrawableActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_drawable).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, DrawableActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.but_to_bitmap).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, BitmapActivity.class);
            startActivity(intent);
        });

    }

}
