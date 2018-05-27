package com.example.ibrahim.udacity_and_baking_app.mvp.view;

import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;

import java.util.List;

/**
 *
 * Created by ibrahim on 22/05/18.
 */

public interface MainView extends BaseView {

    void onBakeLoaded(List<Bake> bakeList);

    void onShowDialog(String message);

    void onShowToast();

    void onHideDialog(String message);
}
