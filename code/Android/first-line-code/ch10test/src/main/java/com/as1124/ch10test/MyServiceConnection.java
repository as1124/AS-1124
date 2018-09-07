package com.as1124.ch10test;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class MyServiceConnection implements ServiceConnection {

    private IBinder binder;

    private boolean isConnected = false;

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        android.util.Log.i(getClass().getSimpleName(), "onServiceConnected==" + name.toString());
        this.binder = service;
        this.isConnected = true;
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        android.util.Log.i(getClass().getSimpleName(), "onServiceDisconnected==" + name.toString());
        this.isConnected = false;
        this.binder = null;
    }

    public IBinder getBinder() {
        return binder;
    }

    public boolean isConnected() {
        return isConnected;
    }
}
