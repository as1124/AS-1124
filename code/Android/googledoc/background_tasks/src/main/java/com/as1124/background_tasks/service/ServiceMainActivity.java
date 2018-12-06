package com.as1124.background_tasks.service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.support.v4.app.JobIntentService;

import com.as1124.background_tasks.R;

/**
 * <ul>不管哪种类型的service#onStartCommand()，切换到后台一段时间后都会被系统杀死
 * <li>{@link android.app.Service}</li>
 * <li>{@link android.app.IntentService}: 逐一处理单个启动请求</li>
 * <li>{@link android.app.job.JobService}</li>
 * </ul>
 *
 * @author as-1124(maito:as1124huang@gmail.com)
 */
public class ServiceMainActivity extends Activity {

    As1124NormalServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_main);

        StatusReportReceiver.regist2Local(getBaseContext());

        findViewById(R.id.but_to_normal_service).setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), As1124NormalService.class);
            startService(intent);
        });

        findViewById(R.id.but_to_stop_service).setOnClickListener(v ->
                stopService(new Intent(getBaseContext(), As1124NormalService.class)));

        findViewById(R.id.but_to_intent_service).setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), As1124IntentService.class);
            intent.setAction("com.xiaoxiannv.test.IntentService");
            startService(intent);
        });

        findViewById(R.id.but_job_intent_service).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra("caller", "XiaoXianNv");

            // 针对同一类JobService, jobID必须相同且唯一
            JobIntentService.enqueueWork(getBaseContext(), As1124JobIntentService.class, 123, intent);
        });

        findViewById(R.id.but_bind_service).setOnClickListener(v -> {
            Intent bindIntent = new Intent(getBaseContext(), As1124NormalService.class);
            if (serviceConnection == null) {
                serviceConnection = new As1124NormalServiceConnection();
            }
            bindService(bindIntent, serviceConnection, Service.BIND_AUTO_CREATE);
        });
    }
}
