package com.example.monroe.cse110recipes;


import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.monroe.cse110recipes.recipes.RecipeCreateActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by liangbao on 3/9/16.
 */


// This test class is to test add button works fine.

@RunWith(AndroidJUnit4.class)
public class ApplicationTest2 {

    @Rule
    public ActivityTestRule<MainActivity> rActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void Test1() {
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeCreateActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        RecipeCreateActivity nextActivity = (RecipeCreateActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);

        nextActivity.finish();

    }

}

