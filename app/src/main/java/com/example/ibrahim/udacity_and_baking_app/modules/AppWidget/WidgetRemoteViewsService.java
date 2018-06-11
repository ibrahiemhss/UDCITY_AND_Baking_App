package com.example.ibrahim.udacity_and_baking_app.modules.AppWidget;

import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

/**
 * Created by ibrahim on 24/05/18.
 */

public class WidgetRemoteViewsService extends RemoteViewsService {
    private static final String TAG = "WidgetService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "onGetViewFactory: " + "Service called");
        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}
