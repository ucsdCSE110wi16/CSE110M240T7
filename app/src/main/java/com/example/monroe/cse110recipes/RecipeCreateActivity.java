package com.example.monroe.cse110recipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RatingBar;

public class RecipeCreateActivity extends AppCompatActivity {
    private Recipe currentRecipe = null;
    private EditText veditTitle;
    private EditText veditTime;
    private RatingBar vratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create2);
        setupViewFields();
        Log.d("tag: ", "veditTitle: " + veditTitle);
        Log.d("tag: ", "veditTime: " + veditTime);
        Log.d("tag: ", "vratingBar: " + vratingBar);
        if(getIntent().getExtras() != null && getIntent().getExtras().get("recipeID") != null){
            Integer recipeID = (Integer)getIntent().getExtras().get("recipeID");
            Recipe r = RecipeListActivity.recipes.get(recipeID);
            currentRecipe = r;
            setCurrentRecipe(r);
        }
    }
    private void setupViewFields(){
        veditTitle = ((EditText)findViewById(R.id.edit_title));
        veditTime = ((EditText) findViewById(R.id.edit_time));
        vratingBar = ((RatingBar)findViewById(R.id.edit_rating));
    }
    private void setCurrentRecipe(Recipe r){
        veditTitle.setText(r.name);
        veditTime.setText(r.minutes + "");
        vratingBar.setRating((float)r.rating);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_create, menu);
        return true;
    }

    private void saveRecipe(){
        Recipe r;
        if(currentRecipe != null){
            r = currentRecipe;
        }
        else{
            r = new Recipe();

        }
        r.name = veditTitle.getText().toString();
        r.minutes = Integer.parseInt(veditTime.getText().toString());
        r.rating = (int)vratingBar.getRating();
        Log.d("tag","num stars: "+((RatingBar)findViewById(R.id.edit_rating)).getRating());
        RecipeListActivity.recipes.put(r.id,r);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            saveRecipe();
        }

        return super.onOptionsItemSelected(item);
    }
}
