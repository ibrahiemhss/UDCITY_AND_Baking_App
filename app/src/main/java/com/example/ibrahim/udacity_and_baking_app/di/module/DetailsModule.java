package com.example.ibrahim.udacity_and_baking_app.di.module;

import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.di.scope.AppScope;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by ibrahim on 30/05/18.
 * <p>
 * DetailsModule should provide  BakeApiService
 */
@SuppressWarnings("unused")
@Module
public class DetailsModule {

    //add DetailsView
    private final DetailsView mView;

    public DetailsModule(DetailsView view) {
        this.mView = view;
    }

    //inject BakeApiService inside presenter
    @AppScope
    @Provides
    BakeApiService ingredientsApiService(Retrofit retrofit) {
        return retrofit.create(BakeApiService.class);
    }

    //providing  dependency of DetailsView
    @AppScope
    @Provides
    DetailsView provideView() {
        return mView;
    }
}
