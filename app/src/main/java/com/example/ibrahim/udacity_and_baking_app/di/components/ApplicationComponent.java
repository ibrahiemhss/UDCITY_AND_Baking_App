package com.example.ibrahim.udacity_and_baking_app.di.components;

import android.content.Context;

import com.example.ibrahim.udacity_and_baking_app.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
//TODO (14) create interface ApplicationComponent

/**
 * Created by ibrahim on 22/05/18.
 */
//used throughout the entire app

/*noted as a @Singleton that might be used
 throughout the entire app so that the type of scope*/
@Singleton
/*TODO (17)noted as a @Component  each component at least issued refer to modules or dependencies
  >>>attention<<<
   this ApplicationComponent might not have a pattern
   class that means it might not depending to another
    modules or other components but it can be used a
     parent which then it will be used for the child components
*/
/*
so would be the module the ApplicationModule and
no dependencies so we will leave it empty as it is
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    //TODO (28) exposeRetrofit
    Retrofit exposeRetrofit();

    //TODO (30) Provides Context
    Context exposeContext();
}
