package com.example.ibrahim.udacity_and_baking_app.mvp.view;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.BakeIngredients;

import java.util.List;

/**
 * Created by ibrahim on 30/05/18.
 */

public interface DetailsView extends BaseView {
    void onBakeLoaded(List<BakeIngredients> bakeList);

}
