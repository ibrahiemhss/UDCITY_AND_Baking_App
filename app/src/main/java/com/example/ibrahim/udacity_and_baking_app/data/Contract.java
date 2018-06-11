package com.example.ibrahim.udacity_and_baking_app.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ibrahim on 26/05/18.
 */
@SuppressWarnings("unused")
public class Contract implements BaseColumns {


    public static final String SCHEMA = "content://";
    public static final String AUTHORITY = "com.example.ibrahim.udacity_and_baking_app";
    public static final Uri BASE_CONTENT_URI = Uri.parse(SCHEMA + AUTHORITY);
    public static final String PATH_BAKE = "bake";
    public static final Uri PATH_BAKE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BAKE).build();


    //using these static variable in all classes as we want
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";
    public static final String EXTRA_VIDEO_URL = "extra_video_url ";
    public static final String EXTRA_DESCRIPTION = "extra_description ";
    //database name
    public static final String DB_NAME = "bake_app_db";
    //id for all table
    public static final String ID = "id";
    //for table of Bake
    public static final String TABLE_BAKE = "table_bake";
    public static final String COL_NAMES = "name";
    public static final String DROP_TABLE_BAKE = "DROP TABLE IF EXISTS " + TABLE_BAKE;
    public static final String SELECT_QUERY_BAKE = "SELECT * FROM " + TABLE_BAKE;
    public static final String CREATE_TABLE_BAKE = "create table " + TABLE_BAKE + "(" +
            ID + " integer primary key autoincrement not null," +
            COL_NAMES + " text not null)";
    public static final String QUANTITY = "quantity";
    public static final String MEASURE = "measure";
    public static final String INGREDIENT = "ingredient";
    //for table steps
    public static final String TABLE_STEPS = "tabele_steps";
    public static final String DESCRIPTION = "description";
    public static final String VIDEO_URL = "videoURL";
    public static final String THUMBNAIL_URL = "thumbnailURL";
    public static final String DROP_TABLE_STEPS = "DROP TABLE IF EXISTS " + TABLE_STEPS;
    public static final String SELECT_QUERY_STEPS = "SELECT * FROM " + TABLE_STEPS;
    public static final String CREATE_TABLE_STEPS = "create table " + TABLE_STEPS + "(" +
            ID + " integer primary key autoincrement not null," +
            DESCRIPTION + " text not null ," +
            VIDEO_URL + " text not null ," +
            THUMBNAIL_URL + " text not null )";
    private static final String PREVIEW_DESCRIPTION = "previewDescription";
    private static final String DETAIL_DESCRIPTION = "detailDescription";
    private static final String IMAGE_URL = "imageUrl";
    //for table ingredient
    private static final String TABLE_INGREDIENT = "tabele_ingredient";
    public static final String DROP_TABLE_INGREDIENT = "DROP TABLE IF EXISTS " + TABLE_INGREDIENT;
    public static final String SELECT_QUERY_INGREDIENT = "SELECT * FROM " + TABLE_INGREDIENT;
    public static final String CREATE_TABLE_INGREDIENT = "create table " + TABLE_INGREDIENT + "(" +
            ID + " integer primary key autoincrement not null," +
            QUANTITY + " text not null ," +
            MEASURE + " text not null ," +
            INGREDIENT + " text not null )";


}
