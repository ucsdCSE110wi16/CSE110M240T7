package com.example.monroe.cse110recipes;

import android.content.DialogInterface;
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

public class RecipeActivity extends AppCompatActivity {

    private Recipe currentRecipe;
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Recipe r = RecipeListActivity.recipes.get(getIntent().getExtras().get("RecipeID"));
        currentRecipe = r;
        Log.d("tag", currentRecipe.favorite?"favorited":"not");
        //Populate with Recipie's name
        ((TextView)findViewById(R.id.recipe)).setText(r.name);
        //Populate the total cook time
        ((TextView)findViewById(R.id.cook_Time)).setText(String.valueOf(r.getMinutes()));

        String[] itemList = {"Pour cereal in bowl first", "Pour Milk in bowl"};
        //Instantiate ArrayAdapter
        ArrayAdapter<String> instructionsAdapter = new ArrayAdapter<String>(this, R.layout.simple_row,R.id.steps, itemList);
        //Link array adapter to the Listview
        ListView stepsListView = (ListView) findViewById(R.id.StartHere);
        stepsListView.setAdapter(instructionsAdapter);

        int tempInstructTime[] = {1,2,3};
        String instructionTime[] = null;
        for(int i = 0; i < tempInstructTime.length; i++){

            instructionTime[i] = Integer.toString(tempInstructTime[i]) + " minutes";

        }

        ArrayAdapter<String> timerAdapter = new ArrayAdapter<String>(this, R.layout.clickable_row,R.id.stepTime,instructionTime);
        ListView timerListView = (ListView) findViewById(R.id.timesHere);
        timerListView.setAdapter(timerAdapter);






    }



    private void deleteRecipe(){
        RecipeListActivity.recipes.remove(currentRecipe.id);
        currentRecipe.delete();
        finish();
    }

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
        Log.d("tag", RecipeListActivity.recipes.get(getIntent().getExtras().get("RecipeID")).favorite?"favorited":"not");

        return super.onOptionsItemSelected(item);
    }

}
