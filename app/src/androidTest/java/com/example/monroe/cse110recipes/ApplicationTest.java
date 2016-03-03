package com.example.monroe.cse110recipes;

import android.app.Instrumentation.ActivityMonitor;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void testSearchByRecipeClick(){

        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeListActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        RecipeListActivity nextActivity = (RecipeListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void testCreateRecipe() {

        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeCreateActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        RecipeCreateActivity nextActivity = (RecipeCreateActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }
/*
    @Test
    public void testFilterSearchByRecipe(){

        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.edit_title)).perform(typeText("shit")), closeSoftKeyboard());
        onView(withId(R.id.edit_time)).perform(typeText("10"),closeSoftKeyboard());
        onView(withId(R.id.edit_rating).perform(click))

    }
*/
/*
    @Test
    public void testSearchByIngredientsClick(){

        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeListActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        RecipeListActivity nextActivity = (RecipeListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }*/


}