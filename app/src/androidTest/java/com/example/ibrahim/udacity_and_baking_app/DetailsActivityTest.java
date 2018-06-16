package com.example.ibrahim.udacity_and_baking_app;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ibrahim.udacity_and_baking_app.IdlingResource.EspressoIdlingResource;
import com.example.ibrahim.udacity_and_baking_app.modules.details.DetailsActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by ibrahim on 13/06/18.
 */
@RunWith(AndroidJUnit4.class)
public class DetailsActivityTest {
    int position = 1;
    /*test on click on item in RecyclerView  in DetailsActivity */
    @Rule
    public ActivityTestRule<DetailsActivity> mActivityTestRule = new ActivityTestRule<DetailsActivity>(DetailsActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent result = new Intent(targetContext, DetailsActivity.class);
            /*sending bundle to DetailsActivity by its position in arraylist*/
            result.putExtra(DetailsActivity.EXTRA_BAKE_NAME, getListValue(position));
            //send position
            result.putExtra(DetailsActivity.EXTRA_POSITION, position);

            return result;
        }
    };

    // Registers any resource that needs to be synchronized with Espresso before the test is run.

    @Before
    public void setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getIdlingResource());
    }

    @Test
    public void scrollToPosition() {
        /*get recyclerView by id and but the number of item position chosen to test */
        onView(withId(R.id.step_list)).perform(RecyclerViewActions.scrollToPosition(position));
        onView(withId(R.id.ingredients_list)).perform(RecyclerViewActions.scrollToPosition(position));

        /*test the specific text with specific position in arraylist*/
        onView(withId(R.id.tv_baking_name)).check(matches(withText(getListValue(position))));


    }

    //  unregister resources when not needed to avoid malfunction.
    @After
    public void unregisterIdlingResource() {
        if (EspressoIdlingResource.getIdlingResource() != null) {
            IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getIdlingResource());
        }
    }

    public String getListValue(int postion) {
        String getValue = null;
        if (postion == 0) {
            getValue = "Nutella Pie";
        } else if (postion == 1) {
            getValue = "Brownies";
        } else if (postion == 2) {
            getValue = "Yellow Cake";
        } else if (postion == 3) {
            getValue = "Cheesecake";
        }


        return getValue;
    }

}
