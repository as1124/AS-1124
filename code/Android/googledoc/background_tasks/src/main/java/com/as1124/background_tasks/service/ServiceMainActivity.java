package com.as1124.background_tasks.service;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.JobIntentService;
import android.widget.Toast;

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

    private As1124NormalServiceConnection mConnection;

    Handler mBindHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case As1124NormalServiceConnection.SERVICE_CONNECTED:
                    mConnection = (As1124NormalServiceConnection) msg.obj;
                    AlertDialog.Builder builder = new AlertDialog.Builder(ServiceMainActivity.this);
                    builder.setMessage(mConnection.getLocalService().tellMeWhy()).show();
                    break;
                case As1124NormalServiceConnection.SERVICE_DISCONNECTED:
                    Toast.makeText(ServiceMainActivity.this, "Service disconnected!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

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
            boolean result = As1124NormalServiceConnection.bindWithService(getBaseContext(), bindIntent, mBindHandler);
            if (!result) {
                Toast.makeText(getBaseContext(), "调用 bindService 方法失败", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.but_unbind_service).setOnClickListener(v -> {
            if (mConnection != null) {
                // 取消绑定并不会触发 ServiceConnection#disConnected方法
                unbindService(mConnection);
            }
        });
    }
}
