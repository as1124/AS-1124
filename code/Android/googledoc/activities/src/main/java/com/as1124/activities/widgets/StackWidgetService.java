package com.as1124.activities.widgets;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * @author as-1124(mailto:as1124huang@gmail.com)
 */
public class StackWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackWidgetFactory(getApplicationContext(), intent);
    }
}
