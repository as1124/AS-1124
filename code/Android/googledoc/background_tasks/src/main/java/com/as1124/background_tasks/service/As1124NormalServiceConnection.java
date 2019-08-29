package com.as1124.background_tasks.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 将组件和Service进行绑定并传递通信对象{@link IBinder}<br/>
 * <p>
 * <strong>一般建议在Activity#onStart()进行绑定, 在onStop进行unBindService</strong>,
 * 调用{@link Context#bindService(Intent, ServiceConnection, int)}会立即返回但并不意味着 serviceConnected 成功回调
 * </p>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124NormalServiceConnection implements ServiceConnection {

    public static final int SERVICE_CONNECTED = 1;
    public static final int SERVICE_DISCONNECTED = -1;

    private WeakReference<As1124NormalService> serviceRef;
    private Handler mHandler;

    public As1124NormalServiceConnection(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder binder) {
        As1124NormalService service = ((As1124NormalServiceBinder) binder).getService();
        serviceRef = new WeakReference<>(service);
        Message msg = mHandler.obtainMessage(SERVICE_CONNECTED, this);
        msg.sendToTarget();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        this.serviceRef.clear();
        Message msg = mHandler.obtainMessage(SERVICE_DISCONNECTED);
        msg.sendToTarget();
    }

    public As1124NormalService getLocalService() {
        return serviceRef.get();
    }

    public static boolean bindWithService(Context context, Intent bindIntent, Handler callback) {
        As1124NormalServiceConnection connection = new As1124NormalServiceConnection(callback);

        // 调用会立即返回, 但并不代表 serviceConnected
        return context.bindService(bindIntent, connection, Service.BIND_AUTO_CREATE);
    }

}
