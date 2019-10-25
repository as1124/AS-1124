package com.as1124.ui.layout;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.as1124.ui.R;
import com.as1124.ui.layout.recycler.AboutCardviewActivity;
import com.as1124.ui.layout.recycler.AboutRecyclerView;

public class AboutLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_layout);

        findViewById(R.id.btn_recycler_view).setOnClickListener(v -> startActivity(new Intent(this, AboutRecyclerView.class)));
        findViewById(R.id.btn_card_view).setOnClickListener(v -> startActivity(new Intent(this, AboutCardviewActivity.class)));
    }
}
