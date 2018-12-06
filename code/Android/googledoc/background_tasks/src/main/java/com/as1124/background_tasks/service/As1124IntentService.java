package com.as1124.background_tasks.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Message;
import android.util.Log;

/**
 * {@linkplain IntentService}相比于{@linkplain android.app.Service}在于其自动处理了start和bind操作,
 * 同时其是逐一处理启动命令所携带的Intent信息, 处理过程不允许中断
 * 底层实现为：创建{@linkplain android.os.HandlerThread}来实现Handler脱离UI线程执行交互，Handler默认
 * 在主线程中执行{@linkplain android.os.Handler#handleMessage(Message)}; 同时在处理完{@link #onHandleIntent(Intent)}
 * 之后自动调用了stopSelf来杀死Service.
 *
 * @author as-1124(mailto:as1124huang)
 */
public class As1124IntentService extends IntentService {

    public As1124IntentService() {
        super("As1124IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Log.i(getClass().getSimpleName(), "IntentService#onHandleIntent: " + action);
        }
    }

}
