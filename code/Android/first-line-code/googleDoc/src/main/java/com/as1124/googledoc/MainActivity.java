package com.as1124.googledoc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.googledoc.permission.PermissionActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.to_permission_activity).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, PermissionActivity.class);
            startActivity(intent);
        });
    }
}
