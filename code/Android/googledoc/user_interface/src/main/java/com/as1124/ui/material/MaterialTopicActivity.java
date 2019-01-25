package com.as1124.ui.material;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.as1124.ui.R;

public class MaterialTopicActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_topic);

        findViewById(R.id.but_to_fab).setOnClickListener(
                v -> startActivity(new Intent(this, FloatingBarActivity.class)));
    }
}
