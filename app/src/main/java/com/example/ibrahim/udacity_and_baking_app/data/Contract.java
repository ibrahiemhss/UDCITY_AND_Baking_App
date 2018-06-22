package com.example.ibrahim.udacity_and_baking_app.data;

import android.net.Uri;
import android.provider.BaseColumns;

/*Created by ibrahim on 26/05/18.
 */
@SuppressWarnings("unused")
public class Contract implements BaseColumns {

    //using these static variable in all classes as we want

    //internet url
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    //extra strings saved in bundle
    public static final String EXTRA_VIDEO_URL = "extra_video_url";
    public static final String EXTRA_DESCRIPTION = "extra_description";
    public static final String EXTRA_STATE_FIRST_OPEN = "state_first_open";
    public static final String EXTRA_STATE_INGREDIENTS = "state_ingredients";
    public static final String EXTRA_POSITION = "extra_position";
    public static final String EXTRA_BAKE_NAME = "extra_bake";
    public static final String EXTRA_STATE_STEPS = "state_steps";
    public static final String EXTRA_STATE_FRAGMENT = "state_fragment";
    public static final String EXTRA_STATE_INDEX = "state_index";
    public static final String EXTRA_ROTATION = "state_rotation";
    public static final String EXTRA_NO_ROTATION = "state_no_rotation";
    public static final String EXTRA_STEP_INDEX = "extra_index";
    public static final String EXTRA_IS_TABLET = "extra_tablet";

    //content provider
    protected static final String PATH_BAKE = "bake";
    protected static final String AUTHORITY = "com.example.ibrahim.udacity_and_baking_app";
    private static final String SCHEMA = "content://";
    private static final Uri BASE_CONTENT_URI = Uri.parse(SCHEMA + AUTHORITY);

    public static final class BakeEntry implements BaseColumns {
        public static final Uri PATH_BAKE_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BAKE).build();
        //for table of Bake
        public static final String TABLE_BAKE = "table_bake";
        public static final String COL_NAMES = "name";
        public static final String DROP_TABLE_BAKE = "DROP TABLE IF EXISTS " + TABLE_BAKE;
        public static final String SELECT_QUERY_BAKE = "SELECT * FROM " + TABLE_BAKE;
        public static final String CREATE_TABLE_BAKE = "create table " + TABLE_BAKE + "(" +
                _ID + " integer primary key autoincrement not null," +
                COL_NAMES + " text not null)";
        //database name
        protected static final String DB_NAME = "bake_app.db";

    }


}
