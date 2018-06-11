package com.example.ibrahim.udacity_and_baking_app.modules.steps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments.StepsFragment;

import static com.example.ibrahim.udacity_and_baking_app.data.Contract.EXTRA_DESCRIPTION;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.EXTRA_VIDEO_URL;

/**
 *
 * Created by ibrahim on 03/06/18.
 */

public class StepsActivity extends BaseActivity {
    private String mVideoURL;
    private String mDescription;
    @Override
    protected int getContentView() {
        return R.layout.activity_steps;

    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        final Bundle extras = getIntent().getExtras();
        if ((extras != null ? extras.getString(EXTRA_VIDEO_URL) : null) != null) {
            mVideoURL = extras.getString(EXTRA_VIDEO_URL);
            Log.d("videoURL",mVideoURL);

        }
         if (extras.getString(EXTRA_DESCRIPTION)!=null){
            mDescription=extras.getString(EXTRA_DESCRIPTION);
            Log.d("description",mDescription);

        }
        StepsFragment stepsFragment =new StepsFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        stepsFragment.setmVideoUrl(mVideoURL);
        stepsFragment.setmDescription(mDescription);
        fragmentManager.beginTransaction()
                .add(R.id.step_container, stepsFragment)
                .commit();

    }


}
