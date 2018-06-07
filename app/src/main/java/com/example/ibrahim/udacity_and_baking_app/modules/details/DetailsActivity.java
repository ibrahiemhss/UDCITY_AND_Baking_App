package com.example.ibrahim.udacity_and_baking_app.modules.details;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerDetailsComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.DetailsModule;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.IngredientsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.StepsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.StepsActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments.MyStringListener;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments.StepsFragment;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.DetailsPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.StepsView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.ibrahim.udacity_and_baking_app.data.Contract.EXTRA_DESCRIPTION;
import static com.example.ibrahim.udacity_and_baking_app.data.Contract.EXTRA_VIDEO_URL;


/**
 * Created by ibrahim on 22/05/18.
 */

@SuppressWarnings("WeakerAccess")
public class DetailsActivity extends BaseActivity implements DetailsView  {
    private static final String TAG = "DetailsActivity";

    private MyStringListener listener;

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private static final String STATE_INGREDIENTS = "state_ingredients";
    public static final String STATE_STEPS = "state_steps";

    StepsFragment stepsFragment ;
    List<Steps> stepsList = new ArrayList<>();

    public List<Steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    int position;

    @BindView(R.id.linear_details)
    protected LinearLayout linear_details;

    @BindView(R.id.move_left)
    protected ImageView mImgMoveLeft;

    @BindView(R.id.move_right)
    protected ImageView mImgMoveright;
    int i = 0;
    int mySelectedId;

    private boolean mTwoPane;

    @BindView(R.id.ingredients_list)
    protected RecyclerView mIngredients_list;
    private IngredientsAdapter mIngredientsAdapter;

    @BindView(R.id.step_list)
    protected RecyclerView mStep_list;
    private StepsAdapter mStepsAdapter;

    private ArrayList<Steps> mStepsArrayList;
    private ArrayList<Ingredients> mIngredientsArrayList;


    @Override
    protected int getContentView() {

        return R.layout.activity_detials;
    }

    @Inject
    protected DetailsPresenter mPresenter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);

        SharedPrefManager.getInstance(DetailsActivity.this).seSetVideoUrl(null);

        getPositionFromIntent();
        initialiseListIngredients();
        initialiseListSteps();
        GetFragmentByScreenSize();
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_INGREDIENTS)
                && savedInstanceState.containsKey(STATE_STEPS)) {
            mIngredientsArrayList = savedInstanceState.getParcelableArrayList(STATE_INGREDIENTS);
            mStepsArrayList = savedInstanceState.getParcelableArrayList(STATE_STEPS);

            mIngredientsAdapter.addIngredients(mIngredientsArrayList);
            mStepsAdapter.addSteps(mStepsArrayList);


        }
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
        outState.putParcelableArrayList(STATE_INGREDIENTS, mIngredientsArrayList);
        outState.putParcelableArrayList(STATE_STEPS, mStepsArrayList);
        super.onSaveInstanceState(outState);
    }

    private void getPositionFromIntent() {

        //intent come MainActivity
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra(EXTRA_POSITION
                    , DEFAULT_POSITION);

             /*pass position to DetailsPresenter class
             to get the value as list objects from Ingredients class
             and Steps class
               ----- NOTE----------------------
                because i have 2 array which is <<ingredients>> and <<steps>>
              inside json i get every Arraylist alone by its position inside general JsonAArray
              that all of its value come from BakeApiService by retrofit with
              Observable inside BakingResponse class */
            mPresenter.getDetials(position);
            SharedPrefManager.getInstance(this).setPrefPosition(position);
            Log.d("mypossssssssss1",String.valueOf(position));

        }



}

    private void initialiseListIngredients() {

        ButterKnife.bind(this);
        mIngredients_list.setHasFixedSize(true);
        mIngredients_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        mIngredientsAdapter = new IngredientsAdapter(getLayoutInflater());
        mIngredients_list.setAdapter(mIngredientsAdapter);
    }

    private void initialiseListSteps() {

        ButterKnife.bind(this);
        mStep_list.setHasFixedSize(true);
        mStep_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mStepsAdapter = new StepsAdapter(getLayoutInflater());
        mStepsAdapter.setStepsClickListener(new StepsAdapter.OnStepsClickListener() {
            @Override
            public void onClick(int position) {

                if (mTwoPane) {

                    stepsFragment = new StepsFragment();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    stepsFragment.setmDescription(getStepsList().get(position).getDescription());
                    stepsFragment.setmVideoUrl(getStepsList().get(position).getVideoURL());
                    stepsFragment.setVideoView(getStepsList().get(position).getVideoURL());
                    stepsFragment.initializePlayer();


                    Log.d(TAG, "VideoURL _ DetailsActivity :" + getStepsList().get(position).getVideoURL());
                    listener.computeSomething(getStepsList().get(position).getVideoURL());
                    fragmentManager.beginTransaction()
                            .replace(R.id.step_container, stepsFragment)
                            .commit();



                } else {
                    Intent intent = new Intent(DetailsActivity.this, StepsActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString(EXTRA_VIDEO_URL, mStepsAdapter.getVideoURL());
                    extras.putString(EXTRA_DESCRIPTION, mStepsAdapter.getDescription());
                    intent.putExtras(extras);
                    startActivity(intent);
                }

            }
        });
        mStep_list.setAdapter(mStepsAdapter);
    }

    @Override
    public void onIngredientsLoaded(ArrayList<Ingredients> ingredientsList) {
        mIngredientsArrayList=ingredientsList;
        mIngredientsAdapter.addIngredients(mIngredientsArrayList);

    }

    @Override
    public void onStepsLoaded(ArrayList<Steps> stepsList) {
        mStepsArrayList=stepsList;
        mStepsAdapter.addSteps(mStepsArrayList);
        setStepsList(mStepsArrayList);
    }


    public boolean isTablet() {
        return (DetailsActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void GetFragmentByScreenSize() {

        assert ( this.getSystemService(Context.WINDOW_SERVICE)) != null;
        final int rotation = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                if (isTablet()) {

                    changeVideo();

                    mTwoPane = true;
                }

                break;
            case Surface.ROTATION_90:

                changeVideo();

                mTwoPane = true;

                break;
            case Surface.ROTATION_180:

                changeVideo();

                mTwoPane = true;

                break;

        }
    }

    private void changeVideo() {
        stepsFragment = new StepsFragment();

        mImgMoveLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                i++;
                if (i >= mStepsArrayList.size()) {
                    i = mStepsArrayList.size() - 1;
                    mImgMoveLeft.setVisibility(View.INVISIBLE);
                    mImgMoveright.setVisibility(View.VISIBLE);

                } else {
                    mImgMoveright.setVisibility(View.VISIBLE);

                }

                SharedPrefManager.getInstance(DetailsActivity.this).seSetVideoUrl(mStepsArrayList.get(i).getVideoURL());
//                stepsFragment.initializePlayer(SharedPrefManager.getInstance(DetailsActivity.this).getVideoUrl());
                Log.d(TAG, "VideoURL:" + SharedPrefManager.getInstance(DetailsActivity.this).getVideoUrl());
                FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.step_container, stepsFragment);
                fragmentManager.addToBackStack(null);
                fragmentManager.commit();

// Commit the transaction
                //  stepsFragment.resume();
            }
        });

        mImgMoveright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                i--;
                if (i <= 0) {
                    i = 0;
                    mImgMoveright.setVisibility(View.INVISIBLE);
                    mImgMoveLeft.setVisibility(View.VISIBLE);

                } else {
                    mImgMoveLeft.setVisibility(View.VISIBLE);

                }
                SharedPrefManager.getInstance(DetailsActivity.this).seSetVideoUrl(mStepsArrayList.get(i).getVideoURL());
           //     stepsFragment.initializePlayer(SharedPrefManager.getInstance(DetailsActivity.this).getVideoUrl());
                FragmentTransaction fragmentManager = getSupportFragmentManager().beginTransaction();
                fragmentManager.replace(R.id.step_container, stepsFragment);
                fragmentManager.addToBackStack(null);
                fragmentManager.commit();

                Log.d(TAG, "VideoURL:" + SharedPrefManager.getInstance(DetailsActivity.this).getVideoUrl());


            }
        });
    }

}
