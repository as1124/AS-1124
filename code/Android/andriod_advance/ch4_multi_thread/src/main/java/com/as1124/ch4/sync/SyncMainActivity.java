package com.as1124.ch4.sync;

import android.app.Activity;
import android.os.Bundle;

import com.as1124.ch4.R;

/**
 * 线程同步问题
 * <ol>
 * <li>wait/notify/notifyAll方法必须在同步区域内调用, 否则{@link IllegalMonitorStateException}</li>
 * <li>volatile: 作用于实例域, 是的修改能同步被其他线程所获取; 即：实例域被一个线程修改后立即同步内存数据到CPU缓存中从而达到并发线程能立即获取修改后的新值</li>
 * <li>显式同步锁：synchronized </li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SyncMainActivity extends Activity {

    final TestObjectWait testObj = new TestObjectWait();
    static int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_main);

        findViewById(R.id.but_object_wait).setOnClickListener(v -> {
            for (int i = 0; i < 5; i++) {
                Thread thread = new Thread(() -> testObj.causeToWait(), "Thread-" + i);
                thread.start();
            }
        });
        findViewById(R.id.but_object_notify).setOnClickListener(v -> {
            // 必须在同步区域中使用
            switch (type) {
                case 0:
                    synchronized (testObj) {
                        testObj.notify();
                    }
                    break;
                case 1:
                    synchronized (testObj) {
                        testObj.notifyAll();
                    }
                default:
                    break;
            }
            type++;
        });
        findViewById(R.id.but_thread_join).setOnClickListener(v -> {

        });
        findViewById(R.id.but_lock).setOnClickListener(v -> {
            final PayManagement payManagement = new PayManagement(5, 200.0d);
            Thread thread1 = new Thread(() -> {
                try {
                    payManagement.transfer(0, 2, 300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "0--2 Transfer");
            thread1.start();

            Thread thread2 = new Thread(() -> {
                try {
                    payManagement.transfer(1, 0, 150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "1--0 Transfer");
            thread2.start();

        });
    }

}
