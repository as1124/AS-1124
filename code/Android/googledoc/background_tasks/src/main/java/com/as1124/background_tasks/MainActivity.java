package com.as1124.background_tasks;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.as1124.background_tasks.broadcast.BroadcastManiActivity;
import com.as1124.background_tasks.schedule.SchedulerActivity;
import com.as1124.background_tasks.service.ServiceMainActivity;
import com.as1124.background_tasks.threadpool.MultiThreadActivity;

/**
 * 本主题介绍如何在Android后台处理任务
 * <ul>
 * <li>利用JAVA线程池后台并行处理多任务</li>
 * <li>利用Android定时器处理定时、定时重复的操作</li>
 * <li>{@link android.app.Service}</li>
 * <li>任务触发条件器：{@link android.content.BroadcastReceiver}</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_multitask).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MultiThreadActivity.class)));

        findViewById(R.id.but_to_scheduler).setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SchedulerActivity.class)));

        findViewById(R.id.but_to_service).setOnClickListener(v ->
                startActivity(new Intent(getBaseContext(), ServiceMainActivity.class)));

        findViewById(R.id.but_to_receiver).setOnClickListener(v ->
                startActivity(new Intent(getBaseContext(), BroadcastManiActivity.class)));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
