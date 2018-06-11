package com.example.ibrahim.udacity_and_baking_app.di.components;

import com.example.ibrahim.udacity_and_baking_app.di.module.FragmentStepModule;
import com.example.ibrahim.udacity_and_baking_app.di.scope.AppScope;
import com.example.ibrahim.udacity_and_baking_app.modules.steps.fragments.StepsFragment;

import dagger.Component;

/**
 *
 * Created by ibrahim on 04/06/18.
 */
@SuppressWarnings("unused")
@AppScope
@Component(modules = FragmentStepModule.class,dependencies = ApplicationComponent.class)
public interface FragmentStepComponent {
    /**
     * @param stepsFragment the Components want inject into inside StepsFragment
     */
    void injectStepsFragment(StepsFragment stepsFragment);

}
