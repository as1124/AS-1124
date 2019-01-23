package com.as1124.activities.widgets;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.as1124.activities.R;

/**
 * 桌面小部件, live on the home screen, so they have to co-exist(共存)<br/>
 * 类型：
 * <ol>
 * <li>Information Type: 比如天气</li>
 * <li>Collection Type: 比如桌面新闻浏览</li>
 * <li>Control Type: 类似于android下拉出现的开关集合</li>
 * <li>Hybrid Type: 音乐播放部件,包含上面多类型的; </li>
 * </ol>
 * 支持的手势
 * <ol>
 * <li>Touch</li>
 * <li>Vertical swipe</li>
 * </ol>
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WidgetMainActivity extends Activity {

    private AppWidgetManager appWidgetManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_main);

        appWidgetManager = (AppWidgetManager) getSystemService(Context.APPWIDGET_SERVICE);
        findViewById(R.id.but_pin_widget).setOnClickListener(v -> pinWidgetOnDesktop());
    }

    private void pinWidgetOnDesktop() {
        if (Build.VERSION.SDK_INT >= 26) {
            if (appWidgetManager.isRequestPinAppWidgetSupported()) {
                // Create te PendingIntent object only if your app needs to be notified that the user
                // allowed the widget to be pinned. Note that, if the pinning operation fails, your app isn't notified.
                Intent pinWidgetIntent = new Intent();
                ComponentName provider = new ComponentName(getPackageName(), As1124WidgetEventReceiver.class.getName());
                pinWidgetIntent.setComponent(provider);

                // Configure the intent so that your app's broadcast receiver gets the callback successfully.
                // This callback receives the ID of the newly-pinned widget(EXTRA_APPWIDGET_ID)
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1234, pinWidgetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                appWidgetManager.requestPinAppWidget(provider, null, pendingIntent);
                return;
            }
        }

        Toast.makeText(this, "无法完成Pin AppWidget 操作", Toast.LENGTH_SHORT).show();
    }
}
