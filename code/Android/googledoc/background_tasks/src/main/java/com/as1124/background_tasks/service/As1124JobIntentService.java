package com.as1124.background_tasks.service;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124JobIntentService extends JobIntentService {

    static AtomicInteger workID = new AtomicInteger(1);

    public As1124JobIntentService() {
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
        String arg = intent.getStringExtra("caller");
        Log.i(getClass().getSimpleName(), "接收到的数据信息==" + arg + "::" + action);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Log.i(getClass().getSimpleName(), e.getMessage(), e);
        }
        LocalBroadcastManager localManager = LocalBroadcastManager.getInstance(getBaseContext());
        Intent statusIntent = new Intent(StatusReportReceiver.ACTION_REPORT_STATUS);
        statusIntent.putExtra("serviceID", workID.getAndIncrement());
        localManager.sendBroadcast(statusIntent);
    }

}
