package com.example.monroe.cse110recipes;

import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.example.monroe.cse110recipes.recipes.RecipeActivity;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

/**
 * Created by liangbao on 3/11/16.
 */

// This class is to test like button is working fine, and the recipe which is liked will be stored in favorite recipes page.

public class ApplicationTest5 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void Test1(){
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeActivity.class.getName(), null, false);
        onView(withId(R.id.button4)).perform(click());
        onView(withId(R.id.action_add)).perform(click());


        onView(withId(R.id.edit_title)).perform(click(), typeText("TestLikeButton"));
        onView(withId(R.id.edit_time)).perform(click(), typeText("10"));
        onView(withId(R.id.edit_rating)).perform(click());
        onView(withId(R.id.action_save)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());


        // onView(withId(R.id.action_edit)).perform(click());
        //openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withId(R.id.action_notfavorited)).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.button5)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());

        //onData(anything()).equals(withId(R.id.action_edit)).perform(click());
        //RecipeActivity nextActivity = (RecipeActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
       // assertNotNull(nextActivity);
       // nextActivity.finish();
    }

}
