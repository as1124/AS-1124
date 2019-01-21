package com.as1124.activities.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.as1124.activities.R;

/**
 * 只有当 Widget 需要向List、Grid那样去展现多组数据 (需要借用Adapter处理数据) 的时候才需要用到
 * {@link RemoteViewsService}, {@link android.widget.RemoteViewsService.RemoteViewsFactory}
 *
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class StackWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private static String TAG = "RemoteViewAdapter";

    private static int mCount = 10;
    private Context mContext;
    private int mAppWidgetID;

    public StackWidgetFactory(Context context, Intent intent) {
        this.mContext = context;
        mAppWidgetID = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
    }

    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate");

        // In onCreate() you setup any connections / cursors to your data source. Heavy lifting, for example
        // downloading or creating content etc, should be deferred to onDataSetChanged() or getViewAt().
        // Taking more than 20 seconds in this call will result in an ANR.
        for (int i = 0; i < mCount; i++) {

        }
    }

    @Override
    public void onDataSetChanged() {
        Log.i(TAG, "onDataSetChanged");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onCreate");
    }

    @Override
    public int getCount() {
        Log.i(TAG, "onDestroy");
        return mCount;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.i(TAG, "getViewAt");

        // Construct a remote views item based on the app widget item XML file, and set the text
        // based on the position.
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_stack_item);
        remoteViews.setTextViewText(R.id.text_widget_view, "StackView at: " + position);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        Log.i(TAG, "getLoadingView");
        return null;
    }

    @Override
    public int getViewTypeCount() {
        Log.i(TAG, "getViewTypeCount");
        return 0;
    }

    @Override
    public long getItemId(int position) {
        Log.i(TAG, "getItemId");
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        Log.i(TAG, "hasStableIds");
        return false;
    }
}
