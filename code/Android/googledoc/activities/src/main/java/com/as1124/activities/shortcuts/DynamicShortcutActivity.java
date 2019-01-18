package com.as1124.activities.shortcuts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.as1124.activities.R;

public class DynamicShortcutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_shortcut);

        TextView textView = findViewById(R.id.textView_dynamic_shortcut);
        Intent intent = getIntent();
        String action = intent.getAction();
        textView.append("\r\n action = " + action);
    }
}
