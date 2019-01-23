package com.as1124.selflib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 监听手机Screen状态：锁屏、亮屏
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class ScreenStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            Log.i("ScreenStateReceiver", "Screen is ON");
        } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
            Log.i("ScreenStateReceiver", "Screen in OFF");
        }
    }
}
