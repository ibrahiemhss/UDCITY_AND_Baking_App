package com.example.ibrahim.udacity_and_baking_app.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ibrahim on 30/12/17.
 * SharedPrefManager will save the value of selected sort of show that will base in query url
 *
 * @see <a href="https://github.com/ibrahiemhss/Mashaweer-master/blob/master/app/src/main/java/com/mashaweer/ibrahim/mashaweer/data/SharedPrefManager.java"">https://github.com</a>
 */
public class SharedPrefManager {
    private static final String PREF_POSITION = "pref_position";
    private static final String PREF_VIDEO_URL = "pref_video_url";

    private static final String SHARED_PREF_NAME = "save_contents";
    private static SharedPrefManager mInstance;
    private final SharedPreferences pref;

    private SharedPrefManager(Context context) {
        pref = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);

        }
        return mInstance;
    }

    public int getPrefPosition() {
        return pref.getInt(PREF_POSITION, 0);

    }

    public void setPrefPosition(int position) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(PREF_POSITION, position);
        editor.apply();
        editor.commit();

    }

    public String getVideoUrl() {
        return pref.getString(PREF_VIDEO_URL, null);

    }

    public void seSetVideoUrl(String videoUrl) {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(PREF_VIDEO_URL, videoUrl);
        editor.apply();
        editor.commit();

    }
}