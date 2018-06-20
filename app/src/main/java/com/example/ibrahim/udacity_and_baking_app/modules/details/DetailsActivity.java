package com.example.ibrahim.udacity_and_baking_app.modules.details;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ibrahim.udacity_and_baking_app.IdlingResource.EspressoIdlingResource;
import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.data.Contract;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerDetailsComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.DetailsModule;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.IngredientsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.StepsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.fragments.StepsFragment;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.StepsActivity;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.DetailsPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by ibrahim on 22/05/18.
 */

@SuppressWarnings({"WeakerAccess", "unused"})
public class DetailsActivity extends BaseActivity implements DetailsView {



    private static final String TAG = "DetailsActivity";
    private static final int DEFAULT_POSITION = -1;
    @BindView(R.id.linear_details)
    protected LinearLayout linear_details;
    @BindView(R.id.ingredients_list)
    protected RecyclerView mIngredients_list;
    @BindView(R.id.step_list)
    protected RecyclerView mStep_list;
    @BindView(R.id.tv_baking_name)
    protected TextView mTxtBake;
    @Inject
    protected DetailsPresenter mPresenter;
    FragmentListener mCallback;
    Fragment stepsFragment;
    List<Integer> stepsListIndex;
    int urlPosition;
    // int position;
    int i = 0;
    int mIndex;
    int mySelectedId;
    private boolean mTwoPane;
    private boolean mRotation;
    private boolean mFirstOpen;
    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private ArrayList<Steps> mStepsArrayList;
    private ArrayList<Ingredients> mIngredientsArrayList;

    public int getmIndex() {
        return mIndex;
    }

    public void setmIndex(int mIndex) {
        this.mIndex = mIndex;
    }

    @Override
    protected int getContentView() {

        return R.layout.activity_detials;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        SharedPrefManager.getInstance(DetailsActivity.this).seSetVideoUrl();

        ButterKnife.bind(this);
        stepsFragment = new StepsFragment();

        EspressoIdlingResource.increment(); // stops Espresso tests from going forward

        getAllData();
        EspressoIdlingResource.decrement(); // Tells Espresso test to resume


        if (savedInstanceState == null) {
            mIndex = 0;
            mFirstOpen = false;
            initialiseListIngredients();
            initialiseListSteps();


        }
        if (savedInstanceState != null
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INGREDIENTS)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INDEX)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_FIRST_OPEN)) {

            mFirstOpen = savedInstanceState.getBoolean(Contract.EXTRA_STATE_FIRST_OPEN);
            mIngredientsArrayList = savedInstanceState.getParcelableArrayList(Contract.EXTRA_STATE_INGREDIENTS);
            mStepsArrayList = savedInstanceState.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
            // stepsFragment = getSupportFragmentManager().getFragment(savedInstanceState, Contract.STATE_FRAGMENT);
            mIndex = savedInstanceState.getInt(Contract.EXTRA_STATE_INDEX);
            Log.d(TAG, "mIndex OnsaveInActivity = " + mIndex);

            initialiseListIngredients();
            initialiseListSteps();
            GetFragmentByScreenSize(mIndex);

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Bundle extras = getIntent().getExtras();
        assert extras != null;


        mTxtBake.setText(extras.getString(Contract.EXTRA_BAKE_NAME));
    }

    @Override
    protected void resolveDaggerDependency() {

        DaggerDetailsComponents.builder()
                .applicationComponent(getApplicationComponent())
                .detailsModule(new DetailsModule(this))
                .build().inject(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Contract.EXTRA_STATE_INGREDIENTS, mIngredientsArrayList);
        outState.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mStepsArrayList);

        outState.putBoolean(Contract.EXTRA_STATE_FIRST_OPEN, true);
        outState.putInt(Contract.EXTRA_STATE_INDEX, mIndex);

        // getSupportFragmentManager().putFragment(outState, Contract.STATE_FRAGMENT, stepsFragment);
        Log.d(TAG, "mIndex outStateInActiviy = " + mIndex);

        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestart() {

        super.onRestart();
        getAllData();
        initialiseListIngredients();
        initialiseListSteps();
        GetFragmentByScreenSize(mIndex);
        Log.d(TAG, "onRestartDetails IngredientsArrayList = " + String.valueOf(mIngredientsArrayList.size()));

        Log.d(TAG, "onRestartDetails StepsArrayList = " + String.valueOf(mStepsArrayList.size()));

    }

    private void getAllData() {

        //intent come MainActivity
        Bundle extras = getIntent().getExtras();
        assert extras != null;


        int position = 0;
        if (extras != null &&
                extras.containsKey(Contract.EXTRA_POSITION)
                && extras.containsKey(Contract.EXTRA_BAKE_NAME)) {
            position = extras.getInt(Contract.EXTRA_POSITION);
            mTxtBake.setText(extras.getString(Contract.EXTRA_BAKE_NAME));

                         /*pass position to DetailsPresenter class
             to get the value as list objects from Ingredients class
             and Steps class
               ----- NOTE----------------------
                jason have 2 array which is <<ingredients>> and <<steps>>
               get every Arraylist alone by its position inside general JsonAArray
              that all of its value come from BakeApiService by retrofit with
              Observable inside BakingResponse class */


            mPresenter.getDetails(position);

        } else {
            mTxtBake.setText(SharedPrefManager.getInstance(DetailsActivity.this).getPrefBakeName());
            mPresenter.getDetails(SharedPrefManager.getInstance(DetailsActivity.this).getPrefDetailsPosition());


        }




    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INGREDIENTS)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_FRAGMENT)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INDEX)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_FIRST_OPEN)) {

            mFirstOpen = savedInstanceState.getBoolean(Contract.EXTRA_STATE_FIRST_OPEN);
            mIngredientsArrayList = savedInstanceState.getParcelableArrayList(Contract.EXTRA_STATE_INGREDIENTS);
            mStepsArrayList = savedInstanceState.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
            // stepsFragment = getSupportFragmentManager().getFragment(savedInstanceState, Contract.STATE_FRAGMENT);
            mIndex = savedInstanceState.getInt(Contract.EXTRA_STATE_INDEX);
            Log.d(TAG, "mIndex onRestoreInstanceState = " + mIndex);

            initialiseListIngredients();
            initialiseListSteps();
            GetFragmentByScreenSize(mIndex);

        }

    }

    private void initialiseListIngredients() {

        mIngredients_list.setHasFixedSize(true);
        mIngredients_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mIngredientsAdapter = new IngredientsAdapter(getLayoutInflater());
        mIngredients_list.setAdapter(mIngredientsAdapter);
    }

    private void initialiseListSteps() {

        mStep_list.setHasFixedSize(true);
        mStep_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mStepsAdapter = new StepsAdapter(getLayoutInflater(), DetailsActivity.this);

        mStep_list.setAdapter(mStepsAdapter);

        mStepsAdapter.setStepsClickListener(new StepsAdapter.OnStepsClickListener() {
            @Override
            public void onClick(int position) {

                if (mTwoPane) {

                    initializeFragment(position);
                    mIndex = position;
                    Log.d(TAG, "mIndex afterOnClickpPosition = " + String.valueOf(mIndex));


                } else {
                    Intent intent = new Intent(DetailsActivity.this, StepsActivity.class);
                    Bundle extras = new Bundle();
                    mIndex = position;
                    extras.putInt(Contract.EXTRA_STATE_INDEX, mIndex);
                    SharedPrefManager.getInstance(DetailsActivity.this).setPrefIndex(mIndex);
                    extras.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mStepsArrayList);
                    intent.putExtras(extras);
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    public void onIngredientsLoaded(ArrayList<Ingredients> ingredientsList) {
        mIngredientsArrayList = ingredientsList;
        initialiseListIngredients();
        mIngredientsAdapter.addIngredients(mIngredientsArrayList);


    }

    @Override
    public void onStepsLoaded(ArrayList<Steps> stepsList) {
        mStepsArrayList = stepsList;
        initialiseListSteps();
        mStepsAdapter.addSteps(mStepsArrayList);

        if (!mFirstOpen) {
            GetFragmentByScreenSize(mIndex);
            mFirstOpen = true;
            Log.d(TAG, "bundleList onStepsLoaded = " + String.valueOf(mStepsArrayList.size()));

        }



    }

    public boolean isTablet() {
        return (DetailsActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void GetFragmentByScreenSize(int index) {

        assert (this.getSystemService(Context.WINDOW_SERVICE)) != null;
        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                if (isTablet()) {
                    initializeFragment(index);


                    mTwoPane = true;
                }

                break;
            case Surface.ROTATION_90:


                initializeFragment(index);




                mTwoPane = true;

                break;
            case Surface.ROTATION_180:
                initializeFragment(index);



                mTwoPane = true;

                break;

        }
    }

    private void initializeFragment(int index) {
        stepsFragment = new StepsFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mStepsArrayList);
        bundle.putInt(Contract.EXTRA_STEP_INDEX,
                index);
        //bundle.putBoolean(StepsFragment.EXTRA_LARGE_SCREEN, mTwoPane);
        bundle.putBoolean(Contract.EXTRA_ROTATION, false);

        Log.d(TAG, "bundleList send from DetailsActivity = " + String.valueOf(mStepsArrayList.size()));

        mIndex = index;
        stepsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, stepsFragment)
                .commit();

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
    }


    @SuppressWarnings("unused")
    public interface FragmentListener {
        void initializePlayer(String url);
    }
}