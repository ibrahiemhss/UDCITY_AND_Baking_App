package com.example.ibrahim.udacity_and_baking_app.modules.details;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.data.SharedPrefManager;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerDetailsComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.DetailsModule;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.IngredientsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.StepsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.StepsActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments.StepsFragment;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.DetailsPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

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

@SuppressWarnings({"WeakerAccess", "unused"})
public class DetailsActivity extends BaseActivity implements DetailsView {

    public static final String EXTRA_POSITION = "extra_position";
    public static final String STATE_STEPS = "state_steps";
    public static final String STATE_FRAGMENT = "state_fragment";
    private static final String TAG = "DetailsActivity";
    private static final int DEFAULT_POSITION = -1;
    private static final String STATE_INGREDIENTS = "state_ingredients";
    @BindView(R.id.linear_details)
    protected LinearLayout linear_details;
    @BindView(R.id.ingredients_list)
    protected RecyclerView mIngredients_list;
    @BindView(R.id.step_list)
    protected RecyclerView mStep_list;
    @Inject
    protected DetailsPresenter mPresenter;
    FragmentListener mCallback;
    boolean addNewFragment;
    Fragment stepsFragment;
    List<Steps> stepsList = new ArrayList<>();
    List<Integer> stepsListIndex;
    int urlPosition;
    int position;
    int i = 0;
    int mySelectedId;

    private boolean mTwoPane;
    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;
    private ArrayList<Steps> mStepsArrayList;
    private ArrayList<Ingredients> mIngredientsArrayList;

    public List<Steps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    @Override
    protected int getContentView() {

        return R.layout.activity_detials;
    }

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        SharedPrefManager.getInstance(DetailsActivity.this).seSetVideoUrl();

        GetFragmentByScreenSize();

        getPositionFromIntent();
        initialiseListIngredients();
        initialiseListSteps();
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
            mPresenter.getDetails(position);
            SharedPrefManager.getInstance(this).setPrefPosition(position);

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

                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList(StepsFragment.EXTRA_STEP_LIST_ACTIVITY, mStepsArrayList);
                    bundle.putInt(StepsFragment.EXTRA_STEP_INDEX, position);
                    bundle.putBoolean(StepsFragment.EXTRA_LARGE_SCREEN, mTwoPane);

                    stepsFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getSupportFragmentManager();
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
        mIngredientsArrayList = ingredientsList;
        mIngredientsAdapter.addIngredients(mIngredientsArrayList);

    }

    @Override
    public void onStepsLoaded(ArrayList<Steps> stepsList) {
        mStepsArrayList = stepsList;
        mStepsAdapter.addSteps(mStepsArrayList);
        setStepsList(mStepsArrayList);
    }

    public boolean isTablet() {
        return (DetailsActivity.this.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public void GetFragmentByScreenSize() {

        assert (this.getSystemService(Context.WINDOW_SERVICE)) != null;
        assert this.getSystemService(Context.WINDOW_SERVICE) != null;
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

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(StepsFragment.EXTRA_STEP_LIST_ACTIVITY, mStepsArrayList);
        bundle.putInt(StepsFragment.EXTRA_STEP_INDEX, 0);
        bundle.putBoolean(StepsFragment.EXTRA_LARGE_SCREEN, mTwoPane);

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