package com.as1124.app_manifest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_another).setOnClickListener(v -> startActivity(new Intent(this, AnotherActivity.class)));
        findViewById(R.id.but_to_third).setOnClickListener(v -> startActivity(new Intent(this, ThirdActivity.class)));
    }

}
