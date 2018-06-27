package com.example.ibrahim.udacity_and_baking_app.modules.AppWidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.utilities.getBakeUtils;

import java.util.ArrayList;
import java.util.List;

/*
 *Created by ibrahim on 26/06/18.
 */

class WidgetRemoteViewsFactorys implements RemoteViewsService.RemoteViewsFactory {
    private static final String TAG = "WidgetRemoteViewsFactor";
    private Context context = null;
    private List<Ingredients> widgetList = new ArrayList<>();

    public WidgetRemoteViewsFactorys(Context context, Intent intent) {
        this.context = context;
        int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        Log.d("AppWidgetId", String.valueOf(appWidgetId));

    }

    private void updateWidgetListView() {
        widgetList.clear();
        this.widgetList = getBakeUtils.geIngredients(context);
    }

    @Override
    public int getCount() {

        return widgetList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d("WidgetCreatingView", "WidgetCreatingView");
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.list_item_widget);

        remoteView.setTextViewText(R.id.widget_ingredient, widgetList.get(position).getIngredient());
        remoteView.setTextViewText(R.id.widget_measure, widgetList.get(position).getMeasure());
        remoteView.setTextViewText(R.id.widget_quantity, String.valueOf(widgetList.get(position).getQuantity()));
        Intent fillInIntent = new Intent();
        Log.d(TAG, "ItemWidget_id_send = " +
                SharedPrefManager.getInstance(context).getPrefBakePosition());
        fillInIntent.putExtra(Contract.EXTRA_POSITION,
                SharedPrefManager.getInstance(context).getPrefBakePosition());
        fillInIntent.putExtra(Contract.EXTRA_BAKE_NAME, SharedPrefManager.getInstance(context).getPrefBakeName());
        remoteView.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);
        return remoteView;
    }

    @Override
    public int getViewTypeCount() {
        return widgetList.size();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onCreate() {
        widgetList.clear();
        Log.d(TAG, "ItemWidget = onCreate");
        updateWidgetListView();
    }

    @Override
    public void onDataSetChanged() {
        widgetList.clear();
        Log.d(TAG, "ItemWidget = onDataSetChanged");

        updateWidgetListView();
    }

    @Override
    public void onDestroy() {
        widgetList.clear();
        Log.d(TAG, "ItemWidget = onDestroy");


    }


}