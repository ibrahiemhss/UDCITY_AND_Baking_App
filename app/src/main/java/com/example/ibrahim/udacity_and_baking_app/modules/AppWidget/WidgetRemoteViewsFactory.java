package com.example.ibrahim.udacity_and_baking_app.modules.AppWidget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;


/**
 * Created by ibrahim on 24/05/18.
 */
class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetRemoteViewsFactor";

    private Context mContext;
    private Cursor mCursor;
    private int position;

    public WidgetRemoteViewsFactory() {
    }

    @SuppressWarnings("unused")
    public WidgetRemoteViewsFactory(Context applicationContext, Intent intent) {
        mContext = applicationContext;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

        //get value of String from content provider
        if (mCursor != null) {
            mCursor.close();
        }

        final long identityToken = Binder.clearCallingIdentity();
        Uri uri = Contract.BakeEntry.PATH_BAKE_URI;
        mCursor = mContext.getContentResolver().query(uri,
                null,
                null,
                null,
                Contract._ID + " DESC");

        Binder.restoreCallingIdentity(identityToken);

    }

    @Override
    public void onDestroy() {
        if (mCursor != null) {
            mCursor.close();
        }
    }

    @Override
    public int getCount() {
        return mCursor == null ? 0 : mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (position == AdapterView.INVALID_POSITION ||
                mCursor == null || !mCursor.moveToPosition(position)) {
            return null;
        }

        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.list_item_widget);
        rv.setTextViewText(R.id.widgetItemTaskNameLabel, mCursor.getString(1));
        Log.d(TAG, "ItemWidget_String = " + mCursor.getString(1));

        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(MainWidgetProvider.EXTRA_ID, mCursor.getInt(1));
        Log.d(TAG, "ItemWidget_id_send = " + position);
        fillInIntent.putExtra(Contract.EXTRA_POSITION, position);
        fillInIntent.putExtra(Contract.EXTRA_BAKE_NAME, mCursor.getString(1));

        rv.setOnClickFillInIntent(R.id.widgetItemContainer, fillInIntent);

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return mCursor.moveToPosition(position) ? mCursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

}