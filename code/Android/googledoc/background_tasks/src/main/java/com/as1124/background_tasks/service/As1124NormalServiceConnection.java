package com.as1124.background_tasks.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * 将组件和Service进行绑定并传递通信对象{@link IBinder}
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124NormalServiceConnection implements ServiceConnection {

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
}
