package com.as1124.activities.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.as1124.activities.R;

/**
 * 小部件事件广播接收器
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class As1124WidgetEventReceiver extends AppWidgetProvider {

    private static String TAG = "As1124WidgetEventReceiver";

    public As1124WidgetEventReceiver() {
        // default constructor
        super();
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // This is called to update the App Widget at intervals defined by the updatePeriodMills attribute,
        // this method is also called when the user adds the App Widget; this is most important callback because
        // it is called when each App Widget is added to a host.
        Log.i(TAG, "AppWidget onUpdate !!!");

//        int N = appWidgetIds.length;
//        // Perform this loop procedure for each App Widget that belongs to this provider
//        for (int i = 0; i < N; i++) {
//            int appWidgetID = appWidgetIds[i];
//            // Create an Intent to launch the activity
//            Intent intent = new Intent(context, WidgetMainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 248, intent, 0);
//
//            // Get the layout for the App Widget and attach an on-click listener to the button
//            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_as1124_layout);
//            views.setOnClickPendingIntent(R.id.but_widget_next, pendingIntent);
//
//            // Tell the AppWidgetManager to perform an update on the current app widget
//            appWidgetManager.updateAppWidget(appWidgetID, views);
//        }
    }

    @Override
    public void onEnabled(Context context) {
        Log.i(TAG, "AppWidget is enabled");
    }

    @Override
    public void onDisabled(Context context) {
        Log.i(TAG, "AppWidget is disabled!");
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        Log.i(TAG, "AppWidget Options Changed!");

        // 变化的数据信息
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.i(TAG, "AppWidget is deleted !");
    }
}
