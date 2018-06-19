package com.example.ibrahim.udacity_and_baking_app.modules.steps;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments.StepsFragment;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 03/06/18.
 */

public class StepsActivity extends BaseActivity {

    private static final String TAG = "StepsActivity";
    private int mIndex;
    private ArrayList<Steps> mArrayList;
    private StepsFragment mStepsFragment;

    @Override
    protected int getContentView() {
        return R.layout.activity_steps;

    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        final Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            if ((extras != null &&
                    extras.containsKey(Contract.EXTRA_STATE_INDEX) &&
                    extras.containsKey(Contract.EXTRA_STATE_STEPS))) {
                mIndex = extras.getInt(Contract.EXTRA_STATE_INDEX);
                mArrayList = extras.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
                Log.d(TAG, "mInex StepsActivity =" + String.valueOf(mIndex));
                Log.d(TAG, "bundleList StepsActivity =" + String.valueOf(mArrayList.size()));

                initializeView();

            }

        }

        if (savedInstanceState != null
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INDEX)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_STEPS)) {

            mArrayList = extras.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
            mIndex = savedInstanceState.getInt(Contract.EXTRA_STATE_INDEX);
            Log.d(TAG, "mIndex OnsaveInStepsActivity = " + mIndex);
            initializeView();

        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(Contract.EXTRA_STATE_INDEX, SharedPrefManager.getInstance(StepsActivity.this).getPrefIndex());
        outState.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mArrayList);
        Log.d(TAG, "mIndex outStateInStepsActiviy = " + SharedPrefManager.getInstance(StepsActivity.this).getPrefIndex());
        super.onSaveInstanceState(outState);
    }


    public void initializeView() {

        mStepsFragment = new StepsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mArrayList);
        bundle.putInt(Contract.EXTRA_STEP_INDEX,
                mIndex);
        if (getScreenSize()) {
            bundle.putBoolean(Contract.EXTRA_ROTATION, true);
            Log.d(TAG, "mRotation  On rotation StepActivity = " + String.valueOf(getScreenSize()));

        } else {
            bundle.putBoolean(Contract.EXTRA_ROTATION, false);
            Log.d(TAG, "mRotation  On not rotation StepActivity = " + String.valueOf(getScreenSize()));

        }

        Log.d(TAG, "bundleList send from StepsActivity = " + String.valueOf(mArrayList.size()));

        mStepsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, mStepsFragment)
                .commit();


    }

    public boolean getScreenSize() {

        boolean mRotation = false;
        assert (this.getSystemService(Context.WINDOW_SERVICE)) != null;
        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {

            case Surface.ROTATION_90:
                mRotation = true;
                break;

            case Surface.ROTATION_180:
                mRotation = true;
                break;

            default:
                mRotation = false;

        }

        Log.d(TAG, "mRotation  On StepActivity getScreenSize = " + String.valueOf(mRotation));

        return mRotation;
    }
}
