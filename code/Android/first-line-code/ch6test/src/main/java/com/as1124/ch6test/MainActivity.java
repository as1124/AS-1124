package com.as1124.ch6test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.as1124.ch6test.db.DatabaseMainActivity;

/**
 * 本节内容介绍持久化存储
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_persistent_file).setOnClickListener(this);
        findViewById(R.id.but_persistent_preference).setOnClickListener(this);
        findViewById(R.id.but_persistent_database).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.but_persistent_file:
                break;
            case R.id.but_persistent_preference:
                break;
            case R.id.but_persistent_database:
                Intent intent = new Intent(MainActivity.this, DatabaseMainActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
