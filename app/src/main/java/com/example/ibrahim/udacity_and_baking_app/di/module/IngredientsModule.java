package com.example.ibrahim.udacity_and_baking_app.di.module;

import com.example.ibrahim.udacity_and_baking_app.api.ListsDetailsBakeApiService;
import com.example.ibrahim.udacity_and_baking_app.di.scope.PreActivity;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.DetailsView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 *
 * Created by ibrahim on 30/05/18.
 */
@Module

public class IngredientsModule {
    /*TODO (50) add MainView*/
    private final DetailsView mView;
    /*TODO (51) create constructor to pass mView*/
    public IngredientsModule (DetailsView view){
        //pass view reference so the providing it
        this.mView=view;
    }

    /**TODO (32) exposed retrofit
     * @param retrofit
     * because we have exposed retrofit we
     * can make dagger it knows how to pass
     *then using that inject inside presenter
     */
    @PreActivity
    @Provides
    ListsDetailsBakeApiService ingredientsApiService(Retrofit retrofit) {
        return retrofit.create(ListsDetailsBakeApiService.class);
    }
    /*
    TODO (52) providing that dependency of mainView
    */
    @PreActivity
    @Provides
    DetailsView provideView() {
        return mView;
    }
}
