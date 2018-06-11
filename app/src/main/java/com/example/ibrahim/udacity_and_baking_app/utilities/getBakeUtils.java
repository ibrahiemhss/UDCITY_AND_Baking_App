package com.example.ibrahim.udacity_and_baking_app.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;

import java.util.ArrayList;

/**
 * Created by ibrahim on 11/06/18.
 */

public class getBakeUtils {
    private static final String TAG = "OpenFavoriteUtils";

    public static ArrayList<Bake> getBake(Context context)

    {
       Bake bake;
   ArrayList<Bake> bakeArrayList = new ArrayList<>();
                 /* get the ContentProvider URI */
        Uri uri = Contract.PATH_BAKE_URI;
                /* Perform the ContentProvider query */
        Cursor c = context.getContentResolver().query(uri,
                /* Columns; leaving this null returns every column in the table */
                null,
               /* Optional specification for columns in the "where" clause above */
                null,
                /* Values for "where" clause */
                null,
                /* Sort order to return in Cursor */
                null);

                 /*make sure if curser not null to bypass the mistake */
        if (c != null) {
                /*start cursor reading and move from column to other to find all data inside table*/
            while (c.moveToNext()) {
                bake = new Bake();
                /*get all value by cursor while moving by get its column name and get value inside it*/

                String names = c.getString(c.getColumnIndexOrThrow(Contract.COL_NAMES));
                /*while cursor movement will get value of every column this value will save inside all movie object from Movies Class*/
                bake.setName(names);

                /*add all new value of movie object to moviesArrayList*/
                bakeArrayList.add(bake);

                Log.i(TAG, "FetchMovies\ntitle\n" + names);
            }
            c.close();
        }


        return bakeArrayList;
    }
}
