package com.example.ibrahim.udacity_and_baking_app.mvp.view;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.ArrayList;

/**
 *
 * Created by ibrahim on 30/05/18.
 */

public interface DetailsView extends BaseView {

    void onIngredientsLoaded(ArrayList<Ingredients> ingredientsList);
    void onStepsLoaded(ArrayList<Steps> stepsList);

}
