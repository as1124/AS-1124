package com.as1124.app_manifest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.as1124.selflib.WindowUtils;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        WindowUtils.fullScreen(this, Color.TRANSPARENT, true);

        findViewById(R.id.but_to_another).setOnClickListener(v ->
                startActivity(new Intent(this, AnotherActivity.class)));
        findViewById(R.id.but_to_third).setOnClickListener(v ->
                startActivity(new Intent(this, ThirdActivity.class)));
        findViewById(R.id.but_disable_copy).setOnClickListener(v -> disableClipboard());

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void disableClipboard() {
    }

}
