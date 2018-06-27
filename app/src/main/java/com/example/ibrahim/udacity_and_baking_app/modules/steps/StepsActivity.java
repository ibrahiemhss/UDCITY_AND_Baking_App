package com.example.ibrahim.udacity_and_baking_app.modules.steps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.fragments.StepsFragment;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.ArrayList;

import butterknife.BindView;

/**
 *Created by ibrahim on 03/06/18.
 */

@SuppressWarnings("WeakerAccess")
public class StepsActivity extends BaseActivity {

    private static final String TAG = "StepsActivity";
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @BindView(R.id.v_back)
    protected ImageView mBack;
    Fragment mStepsFragment;
    private int mIndex;
    private ArrayList<Steps> mArrayList;

    //getContent view from BaseActivity
    @Override
    protected int getContentView() {
        return R.layout.activity_steps;
    }

    //onViewReady view from BaseActivity
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        //get Bundle with intent come from DetailsActivity
        final Bundle extras = getIntent().getExtras();
        mStepsFragment = new StepsFragment();

        //first entry after oncreate
        if (savedInstanceState == null) {
            //get all values inside Bundle with intent
            if ((extras != null &&
                    extras.containsKey(Contract.EXTRA_STATE_INDEX) &&
                    extras.containsKey(Contract.EXTRA_STATE_STEPS))) {
                mIndex = extras.getInt(Contract.EXTRA_STATE_INDEX);
                mArrayList = extras.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
                Log.d(TAG, "mIndex StepsActivity =" + String.valueOf(mIndex));
                Log.d(TAG, "bundleList StepsActivity =" + String.valueOf(mArrayList.size()));

                initializeView();
            }
        }


        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle(null);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepsActivity.this, DetailsActivity.class);
                startActivity(intent);
            }
        });

        //get last value of all data showed that saved in savedInstanceState
        if (savedInstanceState != null
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INDEX)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_STEPS)) {

            mArrayList = extras.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
            mIndex = savedInstanceState.getInt(Contract.EXTRA_STATE_INDEX);
            mStepsFragment = getSupportFragmentManager().getFragment(savedInstanceState, Contract.EXTRA_STEP_FRAGMENT);
            Log.d(TAG, "mIndex OnsaveInStepsActivity = " + mIndex);
            initializeView();
        }

        //hide toolbar if isRotated() true
        if (isRotated()) {
            mToolbar.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }


    //hide toolbar if isRotated() true
    @Override
    protected void onRestart() {
        super.onRestart();
        if (isRotated()) {
            mToolbar.setVisibility(View.GONE);
        } else {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    //pass wanted data to show after rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //get index that saved in sharedPreferences and save it in bundle
        outState.putInt(Contract.EXTRA_STATE_INDEX, SharedPrefManager.getInstance(StepsActivity.this).getPrefIndex());
        //save arraylist
        outState.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mArrayList);
        //save mStepsFragment
        getSupportFragmentManager().putFragment(outState, Contract.EXTRA_STEP_FRAGMENT, mStepsFragment);
        Log.d(TAG, "mIndex outStateInStepsActivity = " + SharedPrefManager.getInstance(StepsActivity.this).getPrefIndex());
        super.onSaveInstanceState(outState);
    }

    //method will show every things
    private void initializeView() {

        Bundle bundle = new Bundle();
        //pass step ArrayList to StepsFragment
        bundle.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mArrayList);
        //pass mIndex to StepsFragment
        bundle.putInt(Contract.EXTRA_STEP_INDEX, mIndex);
        //pass the rotation case true or false
        bundle.putBoolean(Contract.EXTRA_ROTATION, isRotated());
        Log.d(TAG, "mRotation  On rotation StepActivity = " + String.valueOf(isRotated()));
        //pass the no rotation case true or false will used to display description text
        bundle.putBoolean(Contract.EXTRA_NO_ROTATION, notRotated());
        Log.d(TAG, "bundleList send from StepsActivity = " + String.valueOf(mArrayList.size()));

        //create StepsFragment and pass all value inside bundle
        mStepsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, mStepsFragment)
                .commit();
    }


    @SuppressLint("SwitchIntDef")
    private boolean isRotated() {

        boolean mRotation;
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

    @SuppressLint("SwitchIntDef")
    private boolean notRotated() {

        boolean mNoRotation;
        assert (this.getSystemService(Context.WINDOW_SERVICE)) != null;
        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                mNoRotation = true;
                break;

            default:
                mNoRotation = false;

        }
        Log.d(TAG, "mNoRotation  On StepActivity getScreenSize = " + String.valueOf(mNoRotation));
        return mNoRotation;
    }
}
