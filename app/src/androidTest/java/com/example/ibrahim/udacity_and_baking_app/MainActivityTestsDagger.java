package com.example.ibrahim.udacity_and_baking_app;

import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;

import com.example.ibrahim.udacity_and_baking_app.application.BakeApplication;
import com.example.ibrahim.udacity_and_baking_app.di.components.ApplicationComponent;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerMainComponents;
import com.example.ibrahim.udacity_and_baking_app.di.module.MainModule;
import com.example.ibrahim.udacity_and_baking_app.modules.home.MainActivity;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;
import com.example.ibrahim.udacity_and_baking_app.mvp.presenter.MainPresenter;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * Created by ibrahim on 15/06/18.
 */

public class MainActivityTestsDagger implements MainView {


    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);
    @Inject
    protected MainPresenter mPresenter;
    MainActivity activity = activityRule.getActivity();
    private ArrayList<Bake> mBakeArrayList;


    @Before
    public void init() {
        //getting the application class
        BakeApplication myApp = (BakeApplication) InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getApplicationContext();

        //building the app component with the mocked module
        DaggerMainComponents.builder()
                .applicationComponent(getApplicationComponent())
                //MainModule requiring a view being implements by that
                .mainModule(new MainModule(this))
                .build().inject(activity);


        //setting the component with the mocked module as the main app component
        mPresenter.geBaking();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((BakeApplication) activity.getApplication()).getApplicationComponent();
    }

    @Test
    public void getComponents() throws Exception {


        getmBakeArrayList();
        /// those are the mocked users....

    }

    @Override
    public void onBakeLoaded(ArrayList<Bake> bakeList) {
        setmBakeArrayList(bakeList);
    }

    @Override
    public void onShowDialog(String message) {

    }

    public ArrayList<Bake> getmBakeArrayList() {
        return mBakeArrayList;
    }

    public void setmBakeArrayList(ArrayList<Bake> mBakeArrayList) {
        this.mBakeArrayList = mBakeArrayList;
    }

    @Override
    public void onShowToast() {

    }

    @Override
    public void onHideDialog(String message) {

    }
}
