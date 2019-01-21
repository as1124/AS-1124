package com.as1124.activities.widgets;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.as1124.activities.R;

/**
 * This activity is for configuring APPWIDGET, such as the App Widget color, size and so on. And will be
 * automatically launched by the App Widget host.
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class WidgetSettingActivity extends Activity {

    private AppWidgetManager appWidgetManager;

    private int mWidgetID = AppWidgetManager.INVALID_APPWIDGET_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_setting);

        setResult(RESULT_CANCELED);

        appWidgetManager = (AppWidgetManager) getSystemService(Context.APPWIDGET_SERVICE);
        Intent receivedIntent = getIntent();
        String action = receivedIntent.getAction();
        Bundle extras = receivedIntent.getExtras();

        if (extras == null) {
            this.finish();
            return;
        } else {
            mWidgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (AppWidgetManager.ACTION_APPWIDGET_CONFIGURE.equals(action)) {
            // do configuring with the widget
            AppWidgetProviderInfo widgetProviderInfo = appWidgetManager.getAppWidgetInfo(mWidgetID);

            // when configure finished, update the widget
            appWidgetManager.updateAppWidget(mWidgetID, createRemoteViewInfo(mWidgetID));

            // finally, return a result
            Intent resultIntent = new Intent();
            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetID);
            setResult(RESULT_OK, resultIntent);
        } else if (extras != null && extras.containsKey("but_name")) {
            String butName = extras.getString("but_name");
            RemoteViews views = createRemoteViewInfo(mWidgetID);
            if ("widget_next".equals(butName)) {
                views.setImageViewBitmap(R.id.image_widget, BitmapFactory.decodeResource(getResources(), R.drawable.widget3));
            } else {
                views.setImageViewBitmap(R.id.image_widget, BitmapFactory.decodeResource(getResources(), R.drawable.widget1));
            }
            appWidgetManager.updateAppWidget(mWidgetID, views);
        }
        this.finish();
    }

    private RemoteViews createRemoteViewInfo(int widgetID) {
        RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_as1124_layout);
        Intent intent = new Intent(getApplicationContext(), WidgetSettingActivity.class);
        intent.putExtra("but_name", "widget_next");
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        PendingIntent nextButtonIntent = PendingIntent.getActivity(getApplicationContext(), 248, intent, 0);
        views.setOnClickPendingIntent(R.id.but_widget_next, nextButtonIntent);

        Intent intent2 = new Intent(getApplicationContext(), WidgetSettingActivity.class);
        intent2.putExtra("but_name", "widget_previous");
        intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetID);
        PendingIntent previousButtonIntent = PendingIntent.getActivity(getApplicationContext(), 249, intent2, 0);
        views.setOnClickPendingIntent(R.id.but_widget_previous, previousButtonIntent);
        return views;
    }
}
