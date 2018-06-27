package com.example.ibrahim.udacity_and_baking_app.utilities;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;

import java.util.ArrayList;

/*
 * Created by ibrahim on 11/06/18.
 */

public class getBakeUtils {
    private static final String TAG = "getBakeUtils";

    public static ArrayList<Ingredients> geIngredients(Context context)

    {
        Ingredients ingredients;
        ArrayList<Ingredients> ingredientsArrayList = new ArrayList<>();
                 /* get the ContentProvider URI */
        Uri uri = Contract.BakeEntry.PATH_INGREDIENTS_URI;
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
                ingredients = new Ingredients();
                /*get all value by cursor while moving by get its column name and get value inside it*/

                String ingredient = c.getString(c.getColumnIndexOrThrow(Contract.BakeEntry.COL_INGREDIENT));
                String measure = c.getString(c.getColumnIndexOrThrow(Contract.BakeEntry.COL_MEASURE));
                long quantity = c.getLong(c.getColumnIndexOrThrow(Contract.BakeEntry.COL_QUANTITY));

                /*while cursor movement will get value of every column this value will save inside all movie object from Movies Class*/

                ingredients.setIngredient(ingredient);
                ingredients.setMeasure(measure);
                ingredients.setQuantity(quantity);
                /*add all new value of movie object to moviesArrayList*/
                ingredientsArrayList.add(ingredients);

                Log.i(TAG, "FetchIngredients \n ingredient =" + ingredient + "\n measure =" + measure + "\n quantity =" + String.valueOf(quantity));
            }
            c.close();
        }


        return ingredientsArrayList;
    }
}
