package com.as1124.selflib.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 监听应用前后台切换动作
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class AppSwitchReceiver extends BroadcastReceiver {

    // ATTENTION 毫无作用

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_USER_BACKGROUND.equals(action)) {
            Log.i("AppSwitchReceiver", "App in background");
        } else if (Intent.ACTION_USER_FOREGROUND.equals(action)) {
            Log.i("AppSwitchReceiver", "App in foreground");
        }
    }
}
