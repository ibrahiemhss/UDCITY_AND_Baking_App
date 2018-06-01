package com.example.ibrahim.udacity_and_baking_app.di.components;

import com.example.ibrahim.udacity_and_baking_app.di.module.BakeModule;
import com.example.ibrahim.udacity_and_baking_app.di.scope.PreActivity;
import com.example.ibrahim.udacity_and_baking_app.modules.home.MainActivity;

import dagger.Component;
 //TODO (25) create interface BakeComponents
/**
 *
 * Created by ibrahim on 22/05/18.
 */

//

/*TODO (31) activities might run and
 they might be destroyed so in this case is in PreActivity
(per runtime which it can be for any object so as far it is running then we can use it)
*/
@PreActivity
/*TODO (27) referring to BakeModule class
 and dependency in order to access retrofit
ApplicationComponent get whatever is necessary and then use it here*/
@Component (modules = BakeModule.class,dependencies = ApplicationComponent.class)
public interface BakeComponents {

    /**TODO (53) create inject method
     * @param activity the Components want inject into inside MainActivity
     */


    void inject(MainActivity activity);

}
