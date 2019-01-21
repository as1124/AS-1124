package com.as1124.activities.widgets;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.as1124.activities.R;

public class StackWidgetEventReceiver extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // update each of the app widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; i++) {

            // set up the intent that starts StackViewService, which will provider the views for this collection.
            Intent intent = new Intent(context, StackWidgetService.class);
            // Add the app widget ID to the Intent extras
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // Instantiate the RemoteViews object for the app widget layout.
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_stack_item);
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects to a RemoteViewService through the specified intent.
            // This is how you populate the data
            rv.setRemoteAdapter(appWidgetIds[i], R.id.stack_widget_view, intent);

            // The empty view is displayed when the collection has no items.
            // It should be the same layout used to instantiate the RemoteViews object above
            rv.setEmptyView(R.id.stack_widget_view, R.id.text_widget_view);


            // Do additional processing specific to this app widget...

            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }
}
