package com.as1124.background_tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.as1124.background_tasks.schedule.SchedulerActivity;
import com.as1124.background_tasks.threadpool.MultiThreadActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_multitask).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MultiThreadActivity.class)));

        findViewById(R.id.but_to_scheduler).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SchedulerActivity.class)));
    }
}
