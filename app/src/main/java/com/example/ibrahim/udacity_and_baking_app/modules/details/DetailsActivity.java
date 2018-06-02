package com.example.ibrahim.udacity_and_baking_app.modules.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ibrahim.udacity_and_baking_app.R;
import com.example.ibrahim.udacity_and_baking_app.base.BaseActivity;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerDetailsComponents;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerDetailsComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.DetailsModule;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.IngredientsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.details.adapter.StepsAdapter;
import com.example.ibrahim.udacity_and_baking_app.modules.details.fragments.StepsFragment;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.DetailsPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 *
 * Created by ibrahim on 22/05/18.
 */

@SuppressWarnings("WeakerAccess")
public class DetailsActivity extends BaseActivity implements DetailsView {


    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private IngredientsAdapter mIngredientsAdapter;
    private StepsAdapter mStepsAdapter;


    @BindView(R.id.ingredients_list)
    protected RecyclerView mIngredients_list;

    @Override
    protected int getContentView() {
        return R.layout.detials_activity;
    }

    @Inject
    protected DetailsPresenter mPresenter;

    @Override
    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        getPositionFromIntent();
        initialiseListIngredients();
       // initialiseListSteps();

    }
    @Override
    protected void resolveDaggerDependency() {

        DaggerDetailsComponents.builder()
                .applicationComponent(getApplicationComponent())
                .detailsModule(new DetailsModule(this))
                .build().inject(this);
    }
    private void getPositionFromIntent() {

        //intent come MainActivity
        Intent intent = getIntent();
        int position=0;
        if (intent != null) {
             position = intent.getIntExtra(EXTRA_POSITION
                    , DEFAULT_POSITION);

             /*pass position to DetailsPresenter class
             to get the value as list objects from Ingredients class
             and Steps class
               ----- NOTE----------------------
                because i have 2 array which is <<ingredients>> and <<steps>>
              inside json i get every Arraylist alone by its position inside general JsonAArray
              that all of its value come from ListsDetailsBakeApiService by retrofit with
              Observable inside BakingResponse class */
            mPresenter.getBakeIngredients(position);
            StepsFragment stepsFragment =new StepsFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            stepsFragment.setPos(position);
            fragmentManager.beginTransaction()
                    .add(R.id.step_container, stepsFragment)
                    .commit();

        }
    }



    private void initialiseListIngredients() {

        ButterKnife.bind(this);
        mIngredients_list.setHasFixedSize(true);
        mIngredients_list.setLayoutManager(new LinearLayoutManager(this,
        LinearLayoutManager.VERTICAL, false));
        mIngredientsAdapter = new IngredientsAdapter( getLayoutInflater());
        mIngredients_list.setAdapter(mIngredientsAdapter);
    }
    /*private void initialiseListSteps() {

        ButterKnife.bind(this);
        mStep_list.setHasFixedSize(true);
        mStep_list.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        mStepsAdapter = new StepsAdapter( getLayoutInflater());
        mStep_list.setAdapter(mStepsAdapter);
    }*/

    @Override
    public void onIngredientsLoaded(List<Ingredients> ingredientsList) {
        mIngredientsAdapter.addIngredients(ingredientsList);

    }

    @Override
    public void onStepsLoaded(List<Steps> stepsList) {
    }
}
