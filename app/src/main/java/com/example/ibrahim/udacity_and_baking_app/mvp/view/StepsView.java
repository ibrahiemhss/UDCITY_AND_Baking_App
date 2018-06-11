package com.example.ibrahim.udacity_and_baking_app.mvp.view;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.ArrayList;

/**
 * Created by ibrahim on 02/06/18.
 */

public interface StepsView extends BaseView {
    void resume();

    void pause();

    void destroy();

    @SuppressWarnings({"EmptyMethod", "unused"})
    void onStepsLoaded(ArrayList<Steps> stepsList);
    //  void addPosition(int position);

}
