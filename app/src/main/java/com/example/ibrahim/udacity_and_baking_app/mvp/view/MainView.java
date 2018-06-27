package com.example.ibrahim.udacity_and_baking_app.mvp.view;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;

import java.util.ArrayList;


/*
 * Created by ibrahim on 22/05/18.
 */
// extends BaseView

public interface MainView extends BaseView {

    //create onBakeLoaded and add list of Bake
    void onBakeLoaded(ArrayList<Bake> bakeList);

    //onShowDialog & onShowToast& onHideDialog to show message
    void onShowDialog(String message);

    void onShowToast();

    void onHideDialog(String message);
}
