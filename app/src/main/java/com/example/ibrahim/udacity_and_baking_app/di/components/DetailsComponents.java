package com.example.ibrahim.udacity_and_baking_app.di.components;

import com.example.ibrahim.udacity_and_baking_app.di.module.DetailsModule;
import com.example.ibrahim.udacity_and_baking_app.di.scope.AppScope;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;

import dagger.Component;

/**
 * Created by ibrahim on 30/05/18.
 */
@AppScope
@Component(modules = DetailsModule.class, dependencies = ApplicationComponent.class)
public interface DetailsComponents {
    /**
     * @param activity the Components want inject into inside DetailsActivity
     */
    void inject(DetailsActivity activity);

}
