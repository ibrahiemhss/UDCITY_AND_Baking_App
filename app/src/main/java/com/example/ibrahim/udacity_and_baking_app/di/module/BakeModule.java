package com.example.ibrahim.udacity_and_baking_app.di.module;

import com.example.ibrahim.udacity_and_baking_app.api.BakeApiService;
import com.example.ibrahim.udacity_and_baking_app.di.scope.PreActivity;
import com.example.ibrahim.udacity_and_baking_app.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
//TODO (26) create class BakeModule
/**
 *
 * Created by ibrahim on 22/05/18.
*BakeModules should provide finalized version of BakeApiService
* */
//@SuppressWarnings("ALL")
@Module
public class BakeModule {

    /*TODO (50) add MainView*/
      private final MainView mView;
    /*TODO (51) create constructor to pass mView*/
      public BakeModule (MainView view){
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
    BakeApiService provideBakeApiService(Retrofit retrofit) {
        return retrofit.create(BakeApiService.class);
    }
    /*
    TODO (52) providing that dependency of mainView
    */
    @PreActivity
    @Provides
    MainView provideView() {
        return mView;
    }
}
