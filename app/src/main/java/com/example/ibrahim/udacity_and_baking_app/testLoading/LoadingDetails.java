package com.example.ibrahim.udacity_and_baking_app.testLoading;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.DetailsPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by ibrahim on 14/06/18.
 */

public class LoadingDetails implements DetailsView {
    private static final int DELAY_MILLIS = 3000;
    // Create an ArrayList of mSteps & mIngredients
    static ArrayList<Steps> mSteps = new ArrayList<>();
    static ArrayList<Ingredients> mIngredients = new ArrayList<>();
    @Inject
    protected DetailsPresenter mPresenter;
    int postion;

    public LoadingDetails(int postion) {
        this.postion = postion;
    }

    @Override
    public void onIngredientsLoaded(ArrayList<Ingredients> ingredientsList) {
        mIngredients = ingredientsList;

    }

    @Override
    public void onStepsLoaded(ArrayList<Steps> stepsList) {
        mSteps = stepsList;

    }


}