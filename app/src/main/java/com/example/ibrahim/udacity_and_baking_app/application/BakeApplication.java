package com.example.ibrahim.udacity_and_baking_app.application;

import android.app.Application;

import com.example.ibrahim.udacity_and_baking_app.di.components.ApplicationComponent;
import com.example.ibrahim.udacity_and_baking_app.di.components.DaggerApplicationComponent;
import com.example.ibrahim.udacity_and_baking_app.di.module.ApplicationModule;

import static com.example.ibrahim.udacity_and_baking_app.data.Contract.BASE_URL;

/**
 * Created by ibrahim on 22/05/18.
 *
 * application is a singleton object which will
 * be available throughout the entire lifecycle of
 * app and initialize most of the things that might be
 * required throughout our entire app
 * used to specify inside application that in inside the manifests
 */

public class BakeApplication extends Application {

    /**
     *  initialise ApplicationComponent method
     * from {@linkplain com.example.ibrahim.udacity_and_baking_app.base.BaseActivity}
     */
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeApplicationComponents();

    }


    private void initializeApplicationComponents() {
        /* get value from dagger*/
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this, BASE_URL.trim()))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }


}
