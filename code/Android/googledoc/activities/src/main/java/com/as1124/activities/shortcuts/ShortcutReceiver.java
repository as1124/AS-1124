package com.as1124.activities.shortcuts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ShortcutReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        android.util.Log.i("Shortcut", "Pin 快捷方式通知 = " + action);
    }
}
