package com.as1124.ch4;

import android.util.Log;

import java.util.concurrent.TimeUnit;

/**
 * 线程中断。同步关键字<code>volatile</code>的使用
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class TestInterrupt2 {

    public static void interrupt() throws InterruptedException {
        TestInterrupt2.MoonRunner2 runner2 = new TestInterrupt2.MoonRunner2();
        Thread thread = new Thread(runner2);
        thread.start();
        TimeUnit.MILLISECONDS.sleep(20);
        runner2.cancel();
    }

    protected static class MoonRunner2 implements Runnable {

        private long i = 0;

        // 被volatile修饰的变量在多线程中：一旦发生变更，其他线程可立即获取新的结果值
        private volatile boolean on = true;

        @Override
        public void run() {
            while (on) {
                Log.i("MoonRunner22", "i = " + (++i));
            }
            Log.i("MoonRunner22", "====STOP=====");
        }

        public void cancel() {
            on = false;
        }
    }
}
