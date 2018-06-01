package com.example.ibrahim.udacity_and_baking_app.di.components;

import com.example.ibrahim.udacity_and_baking_app.di.module.IngredientsModule;
import com.example.ibrahim.udacity_and_baking_app.di.scope.PreActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;

import dagger.Component;

/**
 *
 * Created by ibrahim on 30/05/18.
 */
@PreActivity
@Component (modules = IngredientsModule.class,dependencies = ApplicationComponent.class)
public interface IngredientsComponents {
   void inject(DetailsActivity activity);

}
