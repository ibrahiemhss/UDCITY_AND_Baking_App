/*
 * Copyright (c) 2016 Filippo Engidashet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ibrahim.udacity_and_baking_app.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.example.ibrahim.udacity_and_baking_app.data.Contract.CREATE_TABLE_BAKE;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.CREATE_TABLE_INGREDIENT;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.CREATE_TABLE_STEPS;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.DB_NAME;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.DROP_TABLE_BAKE;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.DROP_TABLE_INGREDIENT;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.DROP_TABLE_STEPS;


public class Storage extends SQLiteOpenHelper {

    private static final String TAG = Storage.class.getSimpleName();

    @Inject
    public Storage(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE_BAKE);
            db.execSQL(CREATE_TABLE_INGREDIENT);
            db.execSQL(CREATE_TABLE_STEPS);

        } catch(SQLException e) {
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_BAKE);
        db.execSQL(DROP_TABLE_INGREDIENT);
        db.execSQL(DROP_TABLE_STEPS);

        onCreate(db);
    }


}
