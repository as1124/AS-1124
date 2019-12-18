package com.as1124.ui.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

public class CommandThread implements Runnable {

    private static BHandler commandHandler;

    private WeakReference<Handler> logHandlerRef;

    private WeakReference<Handler> taskHandlerRef;

    protected CommandThread(Handler uiHandler, Handler taskHandler) {
        logHandlerRef = new WeakReference<>(uiHandler);
        taskHandlerRef = new WeakReference<>(taskHandler);
    }

    @Override
    public void run() {
        if (commandHandler == null) {
            Looper.prepare();
            Handler logHandler = logHandlerRef.get();
            logHandler.sendMessage(logHandler.obtainMessage(99, "命令线程B启动"));
            commandHandler = new BHandler();
            Looper.loop();
        }
    }

    public static Handler getHandler() {
        return commandHandler;
    }

    class BHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 11:
                    // 发送执行命令
                    String fileURL = msg.obj.toString();
                    Message newMsg = taskHandlerRef.get().obtainMessage(1, fileURL);
                    taskHandlerRef.get().sendMessage(newMsg);
                    break;
                case 10:
                    // 退出命令
                    this.getLooper().quit();
                    Message newMsgx = logHandlerRef.get().obtainMessage(99, "----退出线程BB---");
                    logHandlerRef.get().sendMessage(newMsgx);
                    break;
                default:
                    break;
            }
        }
    }

}
