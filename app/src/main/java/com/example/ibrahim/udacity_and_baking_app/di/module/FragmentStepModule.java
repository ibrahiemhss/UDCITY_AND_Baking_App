package com.example.ibrahim.udacity_and_baking_app.di.module;

import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.di.scope.AppScope;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.StepsView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ibrahim on 04/06/18.
 */
@Module
public class FragmentStepModule {

    //add StepsView
    private final StepsView mView;
    public FragmentStepModule(StepsView view){
        this.mView=view;
    }

    //inject BakeApiService inside presenter
    @AppScope
    @Provides
    BakeApiService ingredientsApiService(Retrofit retrofit) {
        return retrofit.create(BakeApiService.class);
    }
    //providing  dependency of StepsView
    @AppScope
    @Provides
    StepsView provideView() {
        return mView;
    }
}
