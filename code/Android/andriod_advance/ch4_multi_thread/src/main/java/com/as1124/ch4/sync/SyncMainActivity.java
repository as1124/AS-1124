package com.as1124.ch4.sync;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.as1124.ch4.R;

/**
 * 线程同步问题
 * <ol>
 * <li>wait/notify/notifyAll方法必须在同步区块内调用, 否则{@link IllegalMonitorStateException}</li>
 * <li>{@link #notifyAll()}这些方法的同步对象必须是加锁对象</li>
 * <li>{@link Thread#join()}调用会导致调用线程阻塞直到目标线程执行完成并死亡</li>
 * <li>{@link Thread#yield()} 执行后表示当前线程将会让出资源让其他线程执行，并不用于同步并发场景</li>
 * <li>volatile: 作用于实例域, 是的修改能同步被其他线程所获取; 即：实例域被一个线程修改后立即同步内存数据到CPU缓存中从而达到并发线程能立即获取修改后的新值</li>
 * <li>显式同步锁：synchronized </li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class SyncMainActivity extends Activity {

    final static TestObjectWait testObj = new TestObjectWait();
    static int type = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync_main);

        findViewById(R.id.but_object_wait).setOnClickListener(v -> objectWait());

        findViewById(R.id.but_object_notify).setOnClickListener(v -> objectNotify());

        findViewById(R.id.but_thread_join).setOnClickListener(v -> threadJoin());

        findViewById(R.id.but_thread_yield).setOnClickListener(v -> threadYield());

    }

    private void objectWait() {
        // 启动5个线程在 testObj 对象上进行等待
        for (int i = 0; i < 5; i++) {
            new Thread(() -> testObj.causeToWait(), "Thread-" + i).start();
        }
    }

    /**
     * 必须在同步块中使用, 并且同步对象必须是加锁对象
     */
    private void objectNotify() {
        synchronized (testObj) {
            switch (type) {
                case 0:
                    testObj.notify();
                    break;
                case 1:
                    testObj.notifyAll();
                default:
                    break;
            }
        }
        type++;
    }

    private void threadJoin() {
        // 会导致当前线程阻塞
        Thread tt = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Log.i("Thread-Join", "被等待的线程===" + i);
                    try {
                        sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        tt.start();
        try {
            tt.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("Thread-Join", "主调度线程执行完成");
    }

    private void threadYield() {
        // 并不能实现交替执行的目标
        new YieldThread("YieldThread -- AA").start();
        new YieldThread("YieldThread -- BB").start();
    }


    static class YieldThread extends Thread {

        YieldThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            for (int j = 0; j < 10; j++) {
                Log.i("Yield-Thread", "current == " + getName() + ", j == " + j);
                yield();
            }
        }
    }

}
