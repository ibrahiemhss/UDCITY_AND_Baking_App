package com.example.ibrahim.udacity_and_baking_app.di.components;

import com.example.ibrahim.udacity_and_baking_app.di.module.DetailsModule;
import com.example.ibrahim.udacity_and_baking_app.di.scope.AppScope;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.details.fragments.StepsFragment;

import dagger.Component;

/**
 *
 * Created by ibrahim on 30/05/18.
 */
@AppScope
@Component (modules = DetailsModule.class,dependencies = ApplicationComponent.class)
public interface DetailsComponents {
   void inject(DetailsActivity activity);
   void injectStepsFragment(StepsFragment stepsFragment);

}
