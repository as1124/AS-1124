package com.as1124.activities.widgets;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LauncherApps;
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

        Intent receivedIntent = getIntent();
        String action = receivedIntent.getAction();
        if (AppWidgetManager.ACTION_APPWIDGET_CONFIGURE.equals(action)) {
            // 设置 Widget 相关信息的动作
            appWidgetManager = (AppWidgetManager) getSystemService(Context.APPWIDGET_SERVICE);
            Bundle extras = receivedIntent.getExtras();
            if (extras == null) {
                return;
            }
            mWidgetID = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

            // do configuring with the widget
            AppWidgetProviderInfo widgetProviderInfo = appWidgetManager.getAppWidgetInfo(mWidgetID);

            // when finished, update the widget
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_as1124_layout);
            appWidgetManager.updateAppWidget(mWidgetID, views);

            // finally, return a result
            Intent resultIntent = new Intent();
            resultIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mWidgetID);
            setResult(RESULT_OK, resultIntent);
            this.finish();
        }
    }
}
