package com.as1124.ch3.views;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

/**
 * 用来练习通过{@link android.os.Messenger}进行跨进程通信
 * <ol>
 * <li>不用编写AIDL代码</li>
 * <li>使用Message通信, 方便</li>
 * <li>双向</li>
 * <li>通过{@link android.os.Bundle}传递复杂类型数据</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AcrossProcessService extends Service {
    private static final String LOG_TAG = "AcrossProcess";

    private Messenger messenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // make a copy to get reply information
            Message msgToReply = Message.obtain(msg);
            msgToReply.what = msg.what;
            msgToReply.arg1 = 200;
//            msgToReply.obj = new String("CH3-VIEW-Family, Service call finished!");
            msgToReply.replyTo = null;
            try {
                msg.replyTo.send(msgToReply);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    });

    public AcrossProcessService() {
        Log.i(LOG_TAG, "Service init!");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(LOG_TAG, "Service onCreate!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG, "Service onStartCommand!");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(LOG_TAG, "Service onBind!");

        // Messenger通信关键, 根本仍然是通过IBinder实现跨进程
        return messenger.getBinder();
    }

    @Override
    public void onDestroy() {
        Log.i(LOG_TAG, "Service onDestroy!");
        super.onDestroy();
    }
}
