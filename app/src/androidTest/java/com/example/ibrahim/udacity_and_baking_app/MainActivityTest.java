package com.example.ibrahim.udacity_and_baking_app;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ibrahim.udacity_and_baking_app.IdlingResource.EspressoIdlingResource;
import com.example.ibrahim.udacity_and_baking_app.modules.home.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ibrahim on 12/06/18.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    /*test on click on item in RecyclerView  in MainActivity */
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    // Registers any resource that needs to be synchronized with Espresso before the test is run.

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void clickRecyclerViewItem_Bake() {

        int position = 3;
        /*get recyclerView by id and but the number of item position chosen to test */
        onView(withId(R.id.bake_list)).perform(RecyclerViewActions.scrollToPosition(position));
    }

    @After
    public void unregisterIdlingResource() {
        if (EspressoIdlingResource.getIdlingResource() != null) {
            IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
        }
    }


}
