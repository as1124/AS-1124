package com.as1124.background_tasks.threadpool;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.as1124.background_tasks.R;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Java线程池模型的使用 {@link java.util.concurrent.ThreadPoolExecutor},
 * 并行方式来处理多线程任务
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MultiThreadActivity extends Activity {

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_thread);

        mWebview = findViewById(R.id.webview_threadpool);
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.getSettings().setDomStorageEnabled(true);
        WebViewClient client = new WebViewClient();
        mWebview.setWebViewClient(client);
        mWebview.loadUrl("https://www.cnblogs.com/trust-freedom/p/6594270.html");

        findViewById(R.id.but_thread_executor).setOnClickListener(v -> testMultiThread());
    }

    private void testMultiThread() {
        int cpuCore = Runtime.getRuntime().availableProcessors();
        // 有限的缓冲队列, 缓冲任务数量20
        BlockingQueue<Runnable> waitingQueue = new ArrayBlockingQueue<>(20);
        ThreadFactory threadFactory = new MyThreadFactory();
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(cpuCore, 15, 5,
                TimeUnit.SECONDS, waitingQueue, threadFactory);
        for (int i = 0; i < 33; i++) {
            poolExecutor.execute(new SelfTask("[Task] " + (i + 1)));
            Log.i("[ThreadPool]", "剩余容量===" + waitingQueue.remainingCapacity());
        }

    }

    class SelfTask implements Runnable {
        String taskName;

        public SelfTask(String taskName) {
            this.taskName = taskName;
        }

        @Override
        public void run() {
            Log.i("[MultiThreadActivity]", "当前线程名称 = " + Thread.currentThread().getName()
                    + ", 当前任务名 = " + taskName);
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Log.e("[MultiThreadActivity]", e.getMessage(), e);
            }
        }
    }
}
