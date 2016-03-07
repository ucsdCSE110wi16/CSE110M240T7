package com.example.monroe.cse110recipes;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.example.monroe.cse110recipes.recipes.Recipe;
import com.example.monroe.cse110recipes.recipes.RecipeCreateActivity;
import com.example.monroe.cse110recipes.recipes.RecipeListActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.PreferenceMatchers.withTitle;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.monroe.cse110recipes.R.id.action_favorited;
import static com.example.monroe.cse110recipes.R.id.recipe;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.fail;
import static org.hamcrest.EasyMock2Matchers.equalTo;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class ApplicationTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule(MainActivity.class);

    @Test
    public void test1(){

        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeListActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        RecipeListActivity nextActivity = (RecipeListActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        // next activity is opened and captured.
        assertNotNull(nextActivity);
        nextActivity.finish();
    }

    @Test
    public void test2() {

        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeCreateActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        RecipeCreateActivity nextActivity = (RecipeCreateActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(nextActivity);
        nextActivity.finish();
    }



    @Test
    public void test3() {
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeCreateActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.edit_title)).perform(click(), typeText("TestSaveButton"));
        onView(withId(R.id.edit_time)).perform(click(), typeText("10"));
        onView(withId(R.id.edit_rating)).perform(click());
        onView(withId(R.id.action_save)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());
        onView(withId(R.id.recipe)).check(matches(withText("TestSaveButton")));
        onView(withId(R.id.cook_Time)).check(matches(withText("10")));

    }


    private String getString(int resId){
        return getInstrumentation().getTargetContext().getString(resId);
    }
/*
    @Test
    public void test4(){
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.edit_title)).perform(click(), typeText("TestLikeButton"));
        onView(withId(R.id.edit_time)).perform(click(), typeText("10"));
        onView(withId(R.id.edit_rating)).perform(click());
        onView(withId(R.id.action_save)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());
        //onView(withContentDescription(getString(R.id.action_favorited))).perform(click());
       openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());
        onView(withText("Favorite")).perform(click());
       // onData(anything()).inAdapterView(withId(R.id.action_notfavorited)).perform(click());
        //onView(withId(R.id.home)).perform(click());
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.button5)).perform(click());

    }*/
   /* public static Matcher<Object> withItemContent(String expectedText) {
        checkNotNull(expectedText);
        return withItemContent(equalTo(expectedText));
    }


    public static Matcher<View> withAdaptedData(final Matcher<Object> dataMatcher) {
        return new TypeSafeMatcher<View>() {

    @Override
    public void describeTo(Description description) {
        description.appendText("with class name: ");
        dataMatcher.describeTo(description);
    }

    @Override
    public boolean matchesSafely(View view) {
        if (!(view instanceof ListView)) {
            return false;
        }
        @SuppressWarnings("rawtypes")
        Adapter adapter = ((ListView) view).getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (dataMatcher.matches(adapter.getItem(i))) {
                return true;
            }
        }
        return false;
    }
};
}*/

    //@SuppressWarnings("unchecked")
 /*   @Test
    public void test5(){
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.edit_title)).perform(click(), typeText("TestFilter_Apple"));
        onView(withId(R.id.edit_time)).perform(click(), typeText("10"));
        onView(withId(R.id.edit_rating)).perform(click());
        onView(withId(R.id.action_save)).perform(click());

       /* onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.edit_title)).perform(click(), typeText("TestFilter_Banana"));
        onView(withId(R.id.edit_time)).perform(click(), typeText("5"));
        onView(withId(R.id.edit_rating)).perform(click());
        onView(withId(R.id.action_save)).perform(click());

        onView(withId(R.id.filter_recipe_name)).perform(click(), typeText("Apple"));
        onView(withId(R.id.filter_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());
        onView(withId(R.id.recipe)).check(matches(withText("TestFilter_Apple")));
        //onView(withId(R.id.recipe)).check(matches(withText("TestFilter_Apple")));
        //onView(withId(R.id.cook_Time)).check(matches(withText("5")));

        /*onView(withId(R.id.listview))
                .check(matches(not(withAdaptedData(withItemContent("hello")))));*/
 // }

  /*  public void testFilter() {
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.filter_recipe_name)).perform(click(), typeText("he"));
        onView(withId(R.id.filter_button)).perform(click());


    }*/




    }