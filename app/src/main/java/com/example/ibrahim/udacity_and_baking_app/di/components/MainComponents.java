package com.example.ibrahim.udacity_and_baking_app.di.components;

import com.example.ibrahim.udacity_and_baking_app.di.module.MainModule;
import com.example.ibrahim.udacity_and_baking_app.di.scope.AppScope;
import com.example.ibrahim.udacity_and_baking_app.modules.home.MainActivity;

import dagger.Component;
//TODO (25) create interface MainComponents

/**
 * Created by ibrahim on 22/05/18.
 */

//

/*TODO (31)
  activities might run and they might be
   destroyed so in this case is in AppScope
(per runtime which it can be for any object
 so as far it is running then we can use it)
*/
@AppScope
/*TODO (27) referring to MainModule class
 and dependency in order to access retrofit
ApplicationComponent get whatever is necessary and then use it here*/
@Component(modules = MainModule.class, dependencies = ApplicationComponent.class)
public interface MainComponents {

    /**
     * TODO (53) create inject method
     *
     * @param activity the Components want inject into inside MainActivity
     */


    void inject(MainActivity activity);


}
