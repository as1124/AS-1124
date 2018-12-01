package com.as1124.touch_input;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.as1124.selflib.WindowUtils;
import com.as1124.touch_input.gesture.GestureActivity;
import com.as1124.touch_input.gesture.MyGestureListener;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        WindowUtils.fullScreen(this);

        findViewById(R.id.but_to_gesture).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, GestureActivity.class)));
    }
}
