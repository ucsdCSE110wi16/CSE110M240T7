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


   /* public ApplicationTest() {
        super(MainActivity.class);
    }

    public void testOpenNextActivity() {
        // register next activity that need to be monitored.
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeListActivity.class.getName(), null, false);

        // open current activity.
        MainActivity myActivity = getActivity();
        final Button button = (Button) myActivity.findViewById(R.id.button2);
        myActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                button.performClick();
            }
        });

        //Watch for the timeout
        //example values 5000 if in ms, or 5 if it's in seconds.
        RecipeListActivity nextActivity = (RecipeListActivity)getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity .finish();
    }*/
}