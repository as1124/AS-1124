package com.as1124.background_tasks.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class StatusReportReceiver extends BroadcastReceiver {

    public static final String ACTION_REPORT_STATUS = "com.as1124.serviceWorkStatus";

    public static void regist2Local(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_REPORT_STATUS);
        BroadcastReceiver receiver = new StatusReportReceiver();
        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, filter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (ACTION_REPORT_STATUS.equals(intent.getAction())) {
            int workid = intent.getIntExtra("serviceID", 0);
            Toast.makeText(context, "任务执行完成：" + workid, Toast.LENGTH_SHORT).show();
        }
    }
}
