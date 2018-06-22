package com.example.ibrahim.udacity_and_baking_app.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/*Created by Indian Dollar on 1/10/2017.
 */

public class MyContentProvider extends ContentProvider {

    private static final int BAKE_CODE = 100;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mDbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_BAKE, BAKE_CODE);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context);

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatcher.match(uri);

        Cursor retCursor = null;

        switch (match) {
            case BAKE_CODE:

                retCursor = db.query(Contract.BakeEntry.TABLE_BAKE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            default:
                break;
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        Uri returnUri = null;

        switch (match) {
            case BAKE_CODE:

                long id = db.insertWithOnConflict(Contract.BakeEntry.TABLE_BAKE, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.BakeEntry.PATH_BAKE_URI, id);
                }

                break;
            default:
                break;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
