
package com.example.ibrahim.udacity_and_baking_app.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import javax.inject.Inject;


/**
 *
 * Created by ibrahim on 22/05/18.
 */
public class DbHelper extends SQLiteOpenHelper {

    private static final String TAG = DbHelper.class.getSimpleName();

    @Inject
    public DbHelper(Context context) {
        super(context, Contract.BakeEntry.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(Contract.BakeEntry.CREATE_TABLE_BAKE);

        } catch (SQLException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(Contract.BakeEntry.DROP_TABLE_BAKE);

        onCreate(db);
    }


}
