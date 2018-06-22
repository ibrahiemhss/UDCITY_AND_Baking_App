package com.example.ibrahim.udacity_and_baking_app.modules.details;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
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
 *Created by ibrahim on 22/05/18.
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
    @BindView(R.id.v_ingredient_next)
    protected ImageView mImageNext;
    @BindView(R.id.v_Ingredient_back)
    protected ImageView mImageBack;
    @BindView(R.id.v_step_up)
    protected ImageView mImageUp;
    @BindView(R.id.v_step_down)
    protected ImageView mImageDown;
    @Inject
    protected DetailsPresenter mPresenter;

    private StepsFragment mStepsFragment;
    private List<Integer> stepsListIndex;
    private int mIndex;
    private LinearLayoutManager mIngredientLayoutManager;
    private LinearLayoutManager mStepLayoutManager;
    private boolean mTwoPane;
    private boolean mRotation;
    private boolean mFirstOpen;
    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private ArrayList<Steps> mStepsArrayList;
    private ArrayList<Ingredients> mIngredientsArrayList;

    //getContent view from BaseActivity
    @Override
    protected int getContentView() {

        return R.layout.activity_detials;
    }

    //onViewReady view from BaseActivity
    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        ButterKnife.bind(this);
        mStepsFragment = new StepsFragment();

        EspressoIdlingResource.increment(); // stops Espresso tests from going forward
        getAllData();
        EspressoIdlingResource.decrement(); // Tells Espresso test to resume

        //first show after onCreate
        if (savedInstanceState == null) {
            //this int will save  position that clicked to send it to StepsFragment
            mIndex = 0;
            //this boolean will load data or cancel if first time opening or not
            mFirstOpen = false;

            //get data from two lists steps & ingredients
            initialiseListIngredients();
            initialiseListSteps();
        }
        //get last value of all data showed that saved in savedInstanceState
        if (savedInstanceState != null
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INGREDIENTS)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_INDEX)
                && savedInstanceState.containsKey(Contract.EXTRA_STATE_FIRST_OPEN)) {

            mFirstOpen = savedInstanceState.getBoolean(Contract.EXTRA_STATE_FIRST_OPEN);
            mIngredientsArrayList = savedInstanceState.getParcelableArrayList(Contract.EXTRA_STATE_INGREDIENTS);
            mStepsArrayList = savedInstanceState.getParcelableArrayList(Contract.EXTRA_STATE_STEPS);
            mIndex = savedInstanceState.getInt(Contract.EXTRA_STATE_INDEX);
            Log.d(TAG, "mIndex OnsaveInActivity = " + mIndex);

            initialiseListIngredients();
            initialiseListSteps();
            GetFragmentByScreenSize(mIndex);

        }

    }

    @Override
    protected void resolveDaggerDependency() {

        DaggerDetailsComponents.builder()
                .applicationComponent(getApplicationComponent())
                .detailsModule(new DetailsModule(this))
                .build().inject(this);
    }

    //save wanted data to show after rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //send last array list values
        outState.putParcelableArrayList(Contract.EXTRA_STATE_INGREDIENTS, mIngredientsArrayList);
        outState.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mStepsArrayList);
        //send true to change the state of mFirstOpen
        outState.putBoolean(Contract.EXTRA_STATE_FIRST_OPEN, true);
        //send last index saved
        outState.putInt(Contract.EXTRA_STATE_INDEX, mIndex);
        Log.d(TAG, "mIndex outStateInActivity = " + mIndex);

        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestart() {

        /*this will be important to get all data again after back from coming activity
        *in not use methods to get  data again her show will be empty */
        super.onRestart();
        getAllData();
        initialiseListIngredients();
        initialiseListSteps();
        GetFragmentByScreenSize(mIndex);
        Log.d(TAG, "onRestartDetails IngredientsArrayList = " + String.valueOf(mIngredientsArrayList.size()));
        Log.d(TAG, "onRestartDetails StepsArrayList = " + String.valueOf(mStepsArrayList.size()));

    }

    private void getAllData() {

        //intent come from MainActivity
        Bundle extras = getIntent().getExtras();
        assert extras != null;


        int position;
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
            /*sure i saved some of position to one case that if i will
             come back from StepActivity there no intent in this case*/
            mTxtBake.setText(SharedPrefManager.getInstance(DetailsActivity.this).getPrefBakeName());
            mPresenter.getDetails(SharedPrefManager.getInstance(DetailsActivity.this).getPrefDetailsPosition());

        }
    }

    //method get show of Ingredients list
    private void initialiseListIngredients() {

        mIngredients_list.setHasFixedSize(true);
        mIngredientLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mIngredients_list.setLayoutManager(mIngredientLayoutManager);
        mIngredientsAdapter = new IngredientsAdapter(getLayoutInflater());
        mIngredients_list.setAdapter(mIngredientsAdapter);

        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = mIngredients_list.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = mIngredientLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemIndex >= totalItemCount) return;
                mIngredientLayoutManager.smoothScrollToPosition(mIngredients_list, null, lastVisibleItemIndex + 1);
            }
        });

        mImageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstVisibleItemIndex = mIngredientLayoutManager.findFirstCompletelyVisibleItemPosition();
                if (firstVisibleItemIndex > 0) {
                    mIngredientLayoutManager.smoothScrollToPosition(mIngredients_list, null, firstVisibleItemIndex - 1);
                }
            }
        });
    }

    //method get show of Steps list
    private void initialiseListSteps() {

        mStep_list.setHasFixedSize(true);
        mStepLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mStep_list.setLayoutManager(mStepLayoutManager);
        mStepsAdapter = new StepsAdapter(getLayoutInflater());
        mImageDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalItemCount = mStep_list.getAdapter().getItemCount();
                if (totalItemCount <= 0) return;
                int lastVisibleItemIndex = mStepLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemIndex >= totalItemCount) return;
                mStepLayoutManager.smoothScrollToPosition(mStep_list, null, lastVisibleItemIndex + 1);
            }
        });

        mImageUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int firstVisibleItemIndex = mStepLayoutManager.findLastVisibleItemPosition();
                if (firstVisibleItemIndex == 0) return;
                mStepLayoutManager.smoothScrollToPosition(mStep_list, null, firstVisibleItemIndex - 1);

            }
        });

        mStep_list.setAdapter(mStepsAdapter);
        /*in onclick steps will change int mIndex to clicked position
        * and make two case
        * 1== if mTwoPane mean that the screen is rotated or the device is tablet her will
        *call fragment to show data wanted
        * 2== in small screen will goo next activity just */
        mStepsAdapter.setStepsClickListener(new StepsAdapter.OnStepsClickListener() {
            @Override
            public void onClick(int position) {

                if (mTwoPane) {

                    initializeFragment(position);
                    mIndex = position;
                    Log.d(TAG, "mIndex afterOnClickPosition = " + String.valueOf(mIndex));


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


    //this get Ingredients values from dagger
    @Override
    public void onIngredientsLoaded(ArrayList<Ingredients> ingredientsList) {
        //save ingredientsList list in global mIngredientsArrayList to be using anywhere
        mIngredientsArrayList = ingredientsList;
        //call initialiseListIngredients to get show
        initialiseListIngredients();
        //add mIngredientsArrayList to IngredientsAdapter
        mIngredientsAdapter.addIngredients(mIngredientsArrayList);


    }

    //this get Steps values from dagger
    @Override
    public void onStepsLoaded(ArrayList<Steps> stepsList) {
        //save stepsList list in global mStepsArrayList to be using anywhere
        mStepsArrayList = stepsList;
        //call initialiseListSteps to get show
        initialiseListSteps();
        //add mStepsArrayList to StepsAdapter
        mStepsAdapter.addSteps(mStepsArrayList);

        /*This is a way to force non-sending  not null
         mStepsArrayList and  0 value of mIndex to fragment
         and open fragment if that the first time opened and
         sure called after implemented onStepsLoaded not in onCreate */
        if (!mFirstOpen) {
            GetFragmentByScreenSize(mIndex);
            //after correct call make mFirstOpen true to call fragment after rotation
            mFirstOpen = true;
            Log.d(TAG, "bundleList onStepsLoaded = " + String.valueOf(mStepsArrayList.size()));

        }


    }

    //this return true if device is tablet
    public boolean isTablet() {
        return (DetailsActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //This method is known for rotating the screen and small screen
    public void GetFragmentByScreenSize(int index) {

        assert (this.getSystemService(Context.WINDOW_SERVICE)) != null;
        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            //this if not rotated
            case Surface.ROTATION_0:
                //but sure if it is tablet will make mTwoPane true and initializeFragment
                if (isTablet()) {
                    initializeFragment(index);
                    mTwoPane = true;
                }
                break;

            //this if rotated 90 make mTwoPane true and initializeFragment
            case Surface.ROTATION_90:
                initializeFragment(index);
                mTwoPane = true;
                break;

            //this if rotated 180 make mTwoPane true and initializeFragment
            case Surface.ROTATION_180:
                initializeFragment(index);
                mTwoPane = true;
                break;
        }
    }

    //this will create fragment
    private void initializeFragment(int index) {
        mStepsFragment = new StepsFragment();
        //bundle will send to StepsFragment
        Bundle bundle = new Bundle();
        //save values wanted in bundle
        bundle.putParcelableArrayList(Contract.EXTRA_STATE_STEPS, mStepsArrayList);
        bundle.putInt(Contract.EXTRA_STEP_INDEX, index);
        bundle.putBoolean(Contract.EXTRA_ROTATION, false);
        bundle.putBoolean(Contract.EXTRA_IS_TABLET, isTablet());
        bundle.putBoolean(Contract.EXTRA_NO_ROTATION, false);
        //send bundle
        mStepsFragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        //create new show of fragment
        fragmentManager.beginTransaction()
                .replace(R.id.step_container, mStepsFragment)
                .commit();

        mIndex = index;
        Log.d(TAG, "bundleList send from DetailsActivity = " + String.valueOf(mStepsArrayList.size()));

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        }
    }
}