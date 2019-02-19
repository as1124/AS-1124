package com.as1124.ch4;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.as1124.ch4.blockqueue.BlockQueueActivity;
import com.as1124.ch4.sync.SyncMainActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 多线程模型
 * <ol>
 * <li>继承Thread</li>
 * <li>Runnable接口</li>
 * <li>Callable接口; 支持返回值、抛出异常, 获取结果Future#get()时会导致当前线程blocking, 直到call()执行完成</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
            }
        }

        findViewById(R.id.but_thread_callable).setOnClickListener(v -> {
            Callable<String> myCall = new CallableThread();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<String> future = executor.submit(myCall);
            try {
                // 等待线程结束, 并返回结果
                Log.i("Multi-Thread", "执行结果====" + future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.but_thread_interrupt).setOnClickListener(v -> {
            try {
                TestInterrupt.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        findViewById(R.id.but_to_sync).setOnClickListener(v ->
                startActivity(new Intent(this, SyncMainActivity.class)));
        findViewById(R.id.but_to_blocking).setOnClickListener(v ->
                startActivity(new Intent(this, BlockQueueActivity.class)));
    }

    /**
     * 支持返回值, 支持抛出异常
     */
    class CallableThread implements Callable<String> {

        @Override
        public String call() throws Exception {
            return "This is Callable Thread!!";
        }
    }
}
