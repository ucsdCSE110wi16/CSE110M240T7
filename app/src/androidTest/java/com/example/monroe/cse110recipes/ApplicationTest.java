package com.example.monroe.cse110recipes;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.app.Fragment;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.View;
import android.app.Instrumentation.ActivityMonitor;
import android.widget.Button;
import android.test.InstrumentationTestCase;
import android.test.ActivityTestCase;

public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {
    public ApplicationTest() {
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
    }
}