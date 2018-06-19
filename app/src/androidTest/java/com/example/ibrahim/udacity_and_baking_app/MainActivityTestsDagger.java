package com.example.ibrahim.udacity_and_baking_app;

import android.support.test.rule.ActivityTestRule;

import com.example.ibrahim.udacity_and_baking_app.modules.home.MainActivity;
import com.example.ibrahim.udacity_and_baking_app.mvp.model.Bake;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created by ibrahim on 15/06/18.
 */

public class MainActivityTestsDagger {


    @Rule
    public ActivityTestRule<MainActivity> activityRule =
            new ActivityTestRule<>(MainActivity.class);
    private ArrayList<Bake> mBakeArrayList;

  /*  MainActivity activity = activityRule.getActivity();


    @Before
    public void init() {
        //getting the application class
        BakeApplication myApp = (BakeApplication) InstrumentationRegistry
                .getInstrumentation()
                .getTargetContext()
                .getApplicationContext();

    *//*    //building the app component with the mocked module
     ApplicationComponent applicationComponent=   DaggerMainComponents.builder()
                .applicationComponent(getApplicationComponent())
                //MainModule requiring a view being implements by that
                .build().inject(activity);


        myApp.setmApplicationComponent(myApp);*//*
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((BakeApplication) activity.getApplication()).getApplicationComponent();
    }*/

    @Test
    public void getComponents() throws Exception {


        ArrayList<Bake> bakes =
                activityRule.
                        getActivity().
                        getmBakeArrayList();

        mBakeArrayList = bakes;

    }





}
