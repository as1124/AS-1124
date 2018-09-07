package com.as1124.ch10test;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.as1124.ch10test.async.message.AsynMessageActivity;

/**
 * 本章节内容主要讲述了服务以及服务间通信（异步通信）的能力
 * <ol>
 * <li>Android异步通信能力：runOnUiThread、Handler-Message、AsyncTask</li>
 * <li>Service onCreate只调用一次，onStartCommand可能会被调用多次</li>
 * <li>Service 通过IBinder/ServiceConnection 来实现和Activity交互</li>
 * <li>ServiceConnection 实现服务bind的时候并不代表服务是启动的，bindService只会调用Service#onCreae</li>
 * <li>被绑定的Service必须在解绑之后才能被stop</li>
 * <li>Service中的方法调用都是在主线程中执行的，一般为避免ANR，耗时操作应当另起线程执行</li>
 * <li>IntentService执行完成后会自动销毁，不像普通的Service会驻留后台</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    private MyServiceConnection serviceConnection = new MyServiceConnection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_async).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AsynMessageActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.but_start_service).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            startService(intent);
        });

        findViewById(R.id.but_stop_service).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyService.class);
            stopService(intent);
        });

        findViewById(R.id.but_bind_service).setOnClickListener(v -> {
            Intent bindIntent = new Intent(MainActivity.this, MyService.class);
            boolean result = bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
            android.util.Log.i(MainActivity.class.getSimpleName(), "bindService called result =" + result);
            if (result) {
                if (serviceConnection.isConnected()) {
                    android.util.Log.i(MainActivity.class.getSimpleName(), "Service connection connected!!");
                } else {
                    android.util.Log.i(MainActivity.class.getSimpleName(), "Service connection in connecting");
                }
            }
        });

        findViewById(R.id.but_unbind_service).setOnClickListener(v -> {
            unbindService(serviceConnection);
        });

        findViewById(R.id.but_test_state).setOnClickListener(v -> {
            MyServiceBinder binder = (MyServiceBinder) serviceConnection.getBinder();
            if (binder != null) {
                android.util.Log.i(MainActivity.class.getSimpleName(), "Binder 状态==" + binder.isBinderAlive());
                Service service = binder.getServiceRef();
                if (service != null) {
                    android.util.Log.i(MainActivity.class.getSimpleName(), "Got Service Reference");
                    ((MyService) service).testaa();
                }
            }
        });

        findViewById(R.id.but_start_foreground).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ForegroundService.class);
            if (Build.VERSION.SDK_INT >= 26) {
                startForegroundService(intent);
            } else {
                intent.putExtra("startType", "foreground");
                startService(intent);
            }
        });

        findViewById(R.id.but_start_intentservice).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MyIntentService.class);
            startService(intent);
        });
    }

}
