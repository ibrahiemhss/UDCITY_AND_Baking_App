package com.example.ibrahim.udacity_and_baking_app.mvp.view;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Ingredients;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.List;

/**
 *
 * Created by ibrahim on 30/05/18.
 */

public interface DetailsView extends BaseView {
    void onIngredientsLoaded(List<Ingredients> ingredientsList);
    void onStepsLoaded(List<Steps> stepsList);

}
