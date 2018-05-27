package com.example.ibrahim.udacity_and_baking_app.di.module;

import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.di.scope.PreActivity;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 *
 * Created by ibrahim on 22/05/18.
*BakeModules should provide finalized version of BakeApiService
* */
//@SuppressWarnings("ALL")
@Module
public class BakeModule {

    //add MainView
    private MainView mView;

    public BakeModule (MainView view){
        //pass view reference so the providing it
        this.mView=view;
    }



    /**
     * @param retrofit
     * because we have exposed retrofit we
     * can make dagger it knows how to pass
     *then using that inject inside presenter
     */
    @PreActivity
    @Provides
    BakeApiService provideBakeApiService(Retrofit retrofit) {
        return retrofit.create(BakeApiService.class);
    }
    /*
    providing that dependency of mainView
    */
    @PreActivity
    @Provides
    MainView provideView() {
        return mView;
    }
}
