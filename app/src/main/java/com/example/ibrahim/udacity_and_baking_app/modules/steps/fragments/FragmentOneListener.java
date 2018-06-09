package com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Steps;

import java.util.ArrayList;

/**
 * Created by ibrahim on 07/06/18.
 */

public interface FragmentOneListener {
    void setStep(int index , ArrayList<Steps> steps);


    void setCurrent(int index);

}
