package com.example.ibrahim.udacity_and_baking_app.di.module;

import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.di.scope.AppScope;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 *
 * Created by ibrahim on 30/05/18.
 */
@Module
public class DetailsModule {
    private final DetailsView mView;
    public DetailsModule(DetailsView view){
        this.mView=view;
    }

    @AppScope
    @Provides
    BakeApiService ingredientsApiService(Retrofit retrofit) {
        return retrofit.create(BakeApiService.class);
    }
    @AppScope
    @Provides
    DetailsView provideView() {
        return mView;
    }
}
