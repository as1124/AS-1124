package com.as1124.ch10test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MyService extends Service {

    private MyServiceBinder binder = null;

    public MyService() {
        // default constructor
    }

    @Override
    public void onCreate() {
        // 服务创建时调用，并且服务只会创建一次
        android.util.Log.i(getClass().getSimpleName(), "Service created!!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 每次服务启动时候调用
        android.util.Log.i(getClass().getSimpleName(), "Service started!!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        android.util.Log.i(getClass().getSimpleName(), "Service bind!!");
        synchronized (this) {
            if (binder == null) {
                binder = new MyServiceBinder(this);
            }
        }
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        android.util.Log.i(getClass().getSimpleName(), "Service unbind!!");
        this.binder = null;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.util.Log.i(getClass().getSimpleName(), "Service destroyed!!");
    }

    public void testaa() {
        android.util.Log.i(getClass().getSimpleName(), "Myservice testaa called!!");
    }
}
