package com.example.ibrahim.udacity_and_baking_app.modules.AppWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;


/**
 * Created by ibrahim on 24/05/18.
 */
public class SimpleAppWidgetProvider extends AppWidgetProvider {

    public static final String ACTION_DETAILS_ACTIVITY = "ACTION_DETAILS_ACTIVITY";
    public static final String EXTRA_SYMBOL = "SYMBOL";
    private static final String TAG = "SimpleAppWidgetProvider";
    private static final String REFRESH_ACTION = "com.example.ibrahim.udacity_and_baking_app.action.REFRESH";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(

                    context.getPackageName(),
                    R.layout.simple_widget

            );

            Cursor cursor = context.getContentResolver().query(
                    Contract.PATH_BAKE_URI,
                    new String[]{"count(*)"},
                    null,
                    null,
                    null
            );

            cursor.moveToFirst();

            views.setTextViewText(R.id.tv_widgetBake, String.valueOf(cursor.getInt(0)));

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

    }

}
