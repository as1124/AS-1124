package com.as1124.background_tasks.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * <ul>
 * <li>如果服务是前台服务，当调用stopForeground时并不会杀死服务, 只标识服务可以在将来被系统杀死(可选移除前台通知)</li>
 * </ul>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124NormalService extends Service {

    public As1124NormalService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();


        return Service.START_STICKY;
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
