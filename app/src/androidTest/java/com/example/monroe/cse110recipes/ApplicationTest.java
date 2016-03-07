package com.example.monroe.cse110recipes;

import android.app.Activity;
import android.app.Instrumentation.ActivityMonitor;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getContext;
import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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



    @Test
    public void testSaveRecipe() {
        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeCreateActivity.class.getName(), null, false);
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.edit_title)).perform(click(), typeText("Hello"));
        onView(withId(R.id.edit_time)).perform(click(), typeText("10"));
        onView(withId(R.id.edit_rating)).perform(click());
        onView(withId(R.id.action_save)).perform(click());
       try {
           Recipe r = Recipe.loadRecipe("Hello");
           assertEquals(r.getMinutes(), 10);
       }
       catch(Exception e){
            fail();
        }
    }

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
   /* @Test
    public void testDataItemNotInAdapter(){
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.action_add)).perform(click());
        onView(withId(R.id.edit_title)).perform(click(), typeText("Hello"));
        onView(withId(R.id.edit_time)).perform(click(), typeText("10"));
        onView(withId(R.id.edit_rating)).perform(click());
        onView(withId(R.id.action_save)).perform(click());

        ActivityMonitor activityMonitor = getInstrumentation().addMonitor(RecipeActivity.class.getName(), null, false);

        onView(withId(R.id.filter_recipe_name)).perform(click(), typeText("he"));
        onView(withId(R.id.filter_button)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.listview)).atPosition(0).perform(click());
        Activity currentActivity = getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5);
        View v = currentActivity.findViewById(R.id.recipe);
        TextView rView = (TextView) v;
        String recipeName = rView.getText().toString();
        assertEquals(recipeName,"hello world");
        /*onView(withId(R.id.listview))
                .check(matches(not(withAdaptedData(withItemContent("hello")))));*/
  //  }

  /*  public void testFilter() {
        onView(withId(R.id.button3)).perform(click());
        onView(withId(R.id.filter_recipe_name)).perform(click(), typeText("he"));
        onView(withId(R.id.filter_button)).perform(click());


    }*/




    }