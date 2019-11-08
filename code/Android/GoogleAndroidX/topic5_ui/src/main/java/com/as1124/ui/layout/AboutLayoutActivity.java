package com.as1124.ui.layout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.as1124.ui.R;
import com.as1124.ui.layout.card.AboutCardviewActivity;
import com.as1124.ui.layout.recycler.AboutRecyclerView;

public class AboutLayoutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_layout);

        findViewById(R.id.btn_recycler_view).setOnClickListener(v -> startActivity(new Intent(this, AboutRecyclerView.class)));
        findViewById(R.id.btn_card_view).setOnClickListener(v -> startActivity(new Intent(this, AboutCardviewActivity.class)));

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkCallingOrSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE}, 1122);
            }
        }

    }


}
