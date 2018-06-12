package com.example.ibrahim.udacity_and_baking_app.modules.AppWidget;

import android.appwidget.AppWidgetProvider;


/**
 * Created by ibrahim on 24/05/18.
 */
public class SimpleAppWidgetProvider extends AppWidgetProvider {

    /*public static final String ACTION_DETAILS_ACTIVITY = "ACTION_DETAILS_ACTIVITY";
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
                    Contract.BakeEntry.PATH_BAKE_URI,
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
*/
}
