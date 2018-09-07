package com.as1124.ch10test;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ForegroundService extends Service {

    public ForegroundService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        android.util.Log.i(getClass().getSimpleName(), "Foreground Service onCreate!!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String type = intent.getStringExtra("startType");
        if ("foreground".equals(type)) {
            Intent startIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 12345, startIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notification = new Notification.Builder(this)
                    .setContentTitle("测试前台服务")
                    .setContentText("服务正在运行")
                    .setContentIntent(pendingIntent)
                    .setWhen(System.currentTimeMillis())
                    .build();
            startForeground(12345, notification);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
