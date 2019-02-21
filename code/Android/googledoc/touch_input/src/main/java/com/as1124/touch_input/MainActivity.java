package com.as1124.touch_input;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import com.as1124.selflib.WindowUtils;
import com.as1124.touch_input.gesture.GestureActivity;
import com.as1124.touch_input.inputmethod.InputMethodActivity;
import com.as1124.touch_input.keyinput.KeyInputActivity;

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
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.CYAN);
        }

        findViewById(R.id.but_to_gesture).setOnClickListener(v ->
                startActivity(new Intent(this, GestureActivity.class)));
        findViewById(R.id.but_to_keyinput).setOnClickListener(v ->
                startActivity(new Intent(this, KeyInputActivity.class)));
        findViewById(R.id.but_to_inputmethod).setOnClickListener(v ->
                startActivity(new Intent(this, InputMethodActivity.class)));

    }
}
