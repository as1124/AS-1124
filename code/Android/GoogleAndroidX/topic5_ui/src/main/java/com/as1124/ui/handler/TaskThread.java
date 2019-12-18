package com.as1124.ui.handler;


import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class TaskThread implements Runnable {

    private static AHandler taskHandler;

    private WeakReference<Handler> logHandlerRef;

    protected TaskThread(Handler uiHandler) {
        logHandlerRef = new WeakReference<>(uiHandler);
    }

    @Override
    public void run() {
        if (taskHandler == null) {
            Handler logHandler = logHandlerRef.get();
            logHandler.sendMessage(logHandler.obtainMessage(99, "任务线程A启动"));

            Looper.prepare();
            taskHandler = new AHandler();
            Looper.loop();
        }
    }

    public Handler getHandler() {
        return taskHandler;
    }

    class AHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 1:
                    // 开始命令
                    String fileURL = msg.obj.toString();
                    Message newMsg = logHandlerRef.get().obtainMessage(99, "开始下载任务：" + fileURL);
                    logHandlerRef.get().sendMessage(newMsg);
                    break;
                case 0:
                    // 退出命令
                    Message msgx = logHandlerRef.get().obtainMessage(99, "----退出线程AA---");
                    logHandlerRef.get().sendMessage(msgx);
                    break;
                default:
                    break;
            }
        }
    }
}
