package com.example.monroe.cse110recipes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

public class RecipeCreateActivity extends AppCompatActivity {
    private Recipe currentRecipe = null;
    private EditText veditTitle;
    private EditText veditTime;
    private RatingBar vratingBar;
    private ImageView vingredientAddButton;
    private LinearLayout vingredientContainer;


    public void ingredientRemoveClick(View v){
        vingredientContainer.removeView((LinearLayout)v.getParent());
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_create2);
        setupViewFields();
        vingredientAddButton.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                View ingredient = getLayoutInflater().inflate((R.layout.ingredients_fields2), null);
                ingredient.findViewById(R.id.ingredient_remove).setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        ingredientRemoveClick(v);
                    }

                });
                ((LinearLayout)findViewById(R.id.ingredient_container)).addView(ingredient);
            }
        });
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
        vingredientAddButton = ((ImageView)findViewById(R.id.ingredient_add));
        vingredientContainer = ((LinearLayout)findViewById(R.id.ingredient_container));
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
        String minutesText = veditTime.getText().toString();
        if(!minutesText.isEmpty())r.minutes = Integer.parseInt(minutesText);
        r.rating = (int)vratingBar.getRating();
        Log.d("tag","num stars: "+((RatingBar)findViewById(R.id.edit_rating)).getRating());
        RecipeListActivity.recipes.put(r.id,r);
        //r.saveRecipe();
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
