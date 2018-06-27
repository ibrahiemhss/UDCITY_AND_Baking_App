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
    private static final int INGREDIENTS_CODE = 200;
    private static final int INGREDIENTS_WITH_ID = 250;


    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mDbHelper;

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_BAKE, BAKE_CODE);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_INGREDIENTS, INGREDIENTS_CODE);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.PATH_INGREDIENTS + "/*", INGREDIENTS_WITH_ID);

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

        Cursor retCursor;

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
            case INGREDIENTS_CODE:

                retCursor = db.query(Contract.BakeEntry.TABLE_INGREDIENTS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
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

        Uri returnUri;

        switch (match) {

            case BAKE_CODE:

                long id = db.insertWithOnConflict(Contract.BakeEntry.TABLE_BAKE, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.BakeEntry.PATH_BAKE_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

                break;
            case INGREDIENTS_CODE:

                long id2 = db.insertWithOnConflict(Contract.BakeEntry.TABLE_INGREDIENTS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

                if (id2 > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.BakeEntry.PATH_INGREDIENTS_URI, id2);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {


        // Get access to the database and write URI matching code to recognize a single item
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        int ingredientsDeleted; // starts as 0

        switch (match) {
            case INGREDIENTS_WITH_ID:

                // Use selections/selectionArgs to filter for this ID
                ingredientsDeleted = db.delete(Contract.BakeEntry.TABLE_INGREDIENTS, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (ingredientsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }


        return ingredientsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count;
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        switch (match) {
            case INGREDIENTS_CODE:
                count = db.update(Contract.BakeEntry.TABLE_INGREDIENTS, values, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }


}
