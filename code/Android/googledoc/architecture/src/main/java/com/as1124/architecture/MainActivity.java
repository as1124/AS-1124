package com.as1124.architecture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.architecture.binding.DataBindingActivity;
import com.as1124.architecture.lifecycle.LifeAwareActivity;

/**
 * Android Architecture Component, Jet-Pack相关的内容
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_binding).setOnClickListener(
                v -> startActivity(new Intent(getBaseContext(), DataBindingActivity.class)));

        findViewById(R.id.but_to_lifecycle).setOnClickListener(
                v -> startActivity(new Intent(getBaseContext(), LifeAwareActivity.class)));
    }
}
