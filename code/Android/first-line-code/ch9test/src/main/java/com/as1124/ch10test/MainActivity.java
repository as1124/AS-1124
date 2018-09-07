package com.as1124.ch10test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 本章节内容主要介绍android网络通信，没有过多介绍webview控件使用
 * <ol>
 * <li>网络请求第三方库：OkHttp</li>
 * <li>xml报文解析</li>
 * <li>json报文解析</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.but_to_webview).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WebviewActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.but_okhttp).setOnClickListener(v -> {
            new Thread() {
                @Override
                public void run() {
                    OkHttpClient httpClient = new OkHttpClient();
                    Request request = new Request.Builder().url("http://www.baidu.com").build();
                    try (Response response = httpClient.newCall(request).execute()) {
                        String data = response.body().string();
                        android.util.Log.i(getClass().getSimpleName(), data);
                    } catch (IOException e) {
                        android.util.Log.e(MainActivity.class.getSimpleName(), e.getMessage(), e);
                    }
                }
            }.start();
        });
    }
}
