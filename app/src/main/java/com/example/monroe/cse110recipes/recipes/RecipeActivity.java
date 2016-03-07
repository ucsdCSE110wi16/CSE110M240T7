package com.example.monroe.cse110recipes.recipes;

import android.content.DialogInterface;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;

import com.example.monroe.cse110recipes.DisplayTimer;
import com.example.monroe.cse110recipes.R;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {
    // Variables for the Recipe Activity
    private Recipe currentRecipe;   // Selected recipe
    private Menu mOptionsMenu;      // Options

    /**
     * When the recipe activity is created, set up data and views
     * @param savedInstanceState - mapping of parcelable variables
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call AppCompatActivity's constructor with savedInstanceState for activities
        super.onCreate(savedInstanceState);
        // Set the content view to the recipe layout
        setContentView(R.layout.recipe_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set the currentRecipe as the selected recipe
        Recipe r = RecipeListActivity.recipes.get(getIntent().getExtras().get("RecipeID"));
        currentRecipe = r;
        Log.d("tag", currentRecipe.favorite ? "favorited" : "not");

        //Populate with Recipie's name
        ((TextView)findViewById(R.id.recipe)).setText(r.name);
        //Populate the total cook time
        ((TextView)findViewById(R.id.cook_Time)).setText(String.valueOf(r.getMinutes()));

        String[] itemList = {"Add all the wet ingredients to a large bowl", "whisk until smoooth", "Pour Milk in bowl", "Look at bowl", "add dry ingredients to another bowl and set aside"};

        Log.d("steps: ", r.steps.size() + "");
        Log.d("steps: ", r.timePerStep.size() + "");
        Log.d("steps: ", r.ingredients.size() + "");
        // list the ingredients name
        ArrayList<String> abc = new ArrayList<String>();
        for (int i = 0; i < r.ingredients.size(); i++) {
            Log.d("fuck, ingred name: ", r.ingredients.get(i).name);
            abc.add(r.ingredients.get(i).name);
        }
        ArrayAdapter<String> ingredientsAdapter = new ArrayAdapter<String>(this, R.layout.simple_row, R.id.steps, abc);
        ListView ingredientsListView = (ListView) findViewById(R.id.ingredientsList);
        ingredientsListView.setAdapter(ingredientsAdapter);


        //String[] abc = {"yolo", "swag"};

        //Instantiate ArrayAdapter for instructions
        ArrayAdapter<String> instructionsAdapter = new ArrayAdapter<String>(this, R.layout.simple_row,R.id.steps, r.steps);
        //Link array adapter to the Listview
        ListView stepsListView = (ListView) findViewById(R.id.StartHere);
        stepsListView.setAdapter(instructionsAdapter);


        int tempInstructTime[] = {1,2,30, 50,};
        ArrayList<String> george= new ArrayList<String>();
        for(int i = 0; i < r.timePerStep.size(); i++) {
            Log.d("fuck, time per step: ",Integer.toString(r.timePerStep.get(i)) + " minutes");
            george.add(Integer.toString(r.timePerStep.get(i)) + " minutes");

        }


        ArrayAdapter<String> timerAdapter = new ArrayAdapter<String>(this, R.layout.clickable_row,R.id.stepTime,george);

        // Link array adapter to the timer list view
        ListView timerListView = (ListView) findViewById(R.id.timesHere);
        timerListView.setAdapter(timerAdapter);
    }

    /**
     * Private helper method to delete the current recipe
     */
    private void deleteRecipe(){
        RecipeListActivity.recipes.remove(currentRecipe.id);
        currentRecipe.delete();
        finish();
    }

    /**
     * Private helper method to confirm deletion of recipe. "Are you sure bro?" "Bro. I'm sure."
     */
    private void confirmDelete(){
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Deleting Recipe")
                .setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecipe();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

    /**
     * When the activity is created, create the menu. In the menu, we create the options
     * @param menu - The menu in which options are to be created
     * @return - always returns true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mOptionsMenu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_view, menu);
        if(currentRecipe.favorite){

            mOptionsMenu.findItem(R.id.action_favorited).setVisible(true);
            mOptionsMenu.findItem(R.id.action_notfavorited).setVisible(false);
        }
        else{

            mOptionsMenu.findItem(R.id.action_favorited).setVisible(false);
            mOptionsMenu.findItem(R.id.action_notfavorited).setVisible(true);

        }
        return true;
    }

    /**
     * When a menu item is selected. What to do when a recipe is selected
     * @param item - The menu item selected
     * @return - true when a menu item is selected, false when it isn't
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        if (id == R.id.action_favorited) {

            mOptionsMenu.findItem(R.id.action_favorited).setVisible(false);
            mOptionsMenu.findItem(R.id.action_notfavorited).setVisible(true);
            currentRecipe.favorite = false;
            currentRecipe.saveRecipe();
        }
        else if (id == R.id.action_notfavorited) {

            mOptionsMenu.findItem(R.id.action_favorited).setVisible(true);
            mOptionsMenu.findItem(R.id.action_notfavorited).setVisible(false);
            currentRecipe.favorite = true;
            currentRecipe.saveRecipe();
        }
        else if (id == R.id.action_delete) {

            confirmDelete();
        }
        else if (id == R.id.action_edit) {
            Intent myIntent = new Intent(RecipeActivity.this, RecipeCreateActivity.class);
            myIntent.putExtra("recipeID", currentRecipe.id); //Optional parameters
            Log.d("tag","settinga recipeID: "+currentRecipe.id);
            startActivity(myIntent);
        }
        Log.d("tag", currentRecipe.favorite?"favorited":"not");
        Log.d("tag", RecipeListActivity.recipes.get(getIntent().getExtras().get("RecipeID")).favorite ? "favorited" : "not");

        return super.onOptionsItemSelected(item);



    }

    public void timerClick(View v){
        Intent intent = new Intent(this, DisplayTimer.class);
        String buttonText = ((Button)v).getText().toString();
        intent.putExtra("string", buttonText);
        this.startActivity(intent);

    }//end of timerClick method called by clickable_row

}
