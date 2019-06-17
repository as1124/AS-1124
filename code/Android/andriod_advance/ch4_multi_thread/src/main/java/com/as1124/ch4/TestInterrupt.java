package com.as1124.ch4;

import android.util.Log;

import java.util.concurrent.TimeUnit;

public class TestInterrupt {

    public static void interrupt() throws InterruptedException {
        Thread thread = new Thread(new MoonRunner());
        thread.start();
        TimeUnit.MILLISECONDS.sleep(20);
        thread.interrupt();
    }

    protected static class MoonRunner implements Runnable {

        @Override
        public void run() {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                Log.i("MoonRunner", "i = " + (++i));
            }
            Log.i("MoonRunner", "====STOP=====");
        }
    }
}
