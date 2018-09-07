package com.as1124.ch10test;

import android.app.IntentService;
import android.content.Intent;


/**
 * 不同于普通的Service，IntentService只需要处理onHandleIntent即可，同时
 * 因为调用在子线程而非main线程中，所以IntentService不会出现ANR问题
 * <strong>值得注意的是：IntentService在运行完成后是会自动销毁的，不会一直保留</strong>
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

        }
        android.util.Log.i(getClass().getSimpleName(), "MyIntentService onHandleIntent called!!");
        android.util.Log.i(getClass().getSimpleName(), "Thread id is " + Thread.currentThread().getId());
    }

    @Override
    public void onDestroy() {
        android.util.Log.i(getClass().getSimpleName(), "MyIntentService destoryed!");
        super.onDestroy();
    }
}
