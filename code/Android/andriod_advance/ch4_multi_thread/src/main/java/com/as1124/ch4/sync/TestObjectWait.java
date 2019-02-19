package com.as1124.ch4.sync;

import android.util.Log;

class TestObjectWait {

    public synchronized void causeToWait() {
        Log.i("Wait-Notify", Thread.currentThread().getName() + "]] Start wait-----");
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i("Wait-Notify", Thread.currentThread().getName() + "]] Finish===");
    }
}
