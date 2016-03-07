package com.example.monroe.cse110recipes.recipes;

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

import com.example.monroe.cse110recipes.Ingredients.Ingredient;
import com.example.monroe.cse110recipes.R;

import java.util.ArrayList;

public class RecipeCreateActivity extends AppCompatActivity {
    // Recipe values to be edited upon creation
    private Recipe currentRecipe = null;
    private EditText veditTitle;
    private EditText veditTime;
    private EditText veditInstruction;
    private RatingBar vratingBar;
    private ImageView vingredientAddButton;
    private LinearLayout vingredientContainer;
    private ImageView vinstructionAddButton;
    private LinearLayout vinstructionContainer;

    /**
     * When the create activity is started, set up data and views
     * @param savedInstanceState - mapping of parcelable variables
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call AppCompatActivity's constructor with savedInstanceState for activities
        super.onCreate(savedInstanceState);
        // Set the content view to the activity_recipe_create2
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


        //instructions portion
        vinstructionAddButton.setOnClickListener(new ImageView.OnClickListener() {
            public void onClick(View v) {
                View instruction = getLayoutInflater().inflate((R.layout.instructions_fields), null);
                instruction.findViewById(R.id.instructions_remove).setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        instructionRemoveClick(v);
                    }

                });
                ((LinearLayout)findViewById(R.id.instructions_container)).addView(instruction);
            }
        });





        Log.d("tag: ", "veditTitle: " + veditTitle);
        Log.d("tag: ", "veditTime: " + veditTime);
        Log.d("tag: ", "vratingBar: " + vratingBar);
        Log.d("tag: ", "veditInstruction" + veditInstruction);
        if(getIntent().getExtras() != null && getIntent().getExtras().get("recipeID") != null){
            Integer recipeID = (Integer)getIntent().getExtras().get("recipeID");
            Recipe r = RecipeListActivity.recipes.get(recipeID);
            currentRecipe = r;
            setCurrentRecipe(r);
        }
    }

    /**
     * Remove the current view
     * @param v - view to be removed
     */
    public void ingredientRemoveClick(View v){
        vingredientContainer.removeView((LinearLayout) v.getParent());

    }

    /**
     * Remove the current view for Instructions
     */
    public void instructionRemoveClick(View v){
        vinstructionContainer.removeView((LinearLayout)v.getParent());
    }

    /**
     * Private helper method to set up the fields for user input
     */
    private void setupViewFields(){
        veditTitle = ((EditText)findViewById(R.id.edit_title));
        veditTime = ((EditText) findViewById(R.id.edit_time));
        vratingBar = ((RatingBar)findViewById(R.id.edit_rating));
        vingredientAddButton = ((ImageView)findViewById(R.id.ingredient_add));
        vingredientContainer = ((LinearLayout)findViewById(R.id.ingredient_container));
        vinstructionAddButton = ((ImageView)findViewById(R.id.instructions_add));
        vinstructionContainer = ((LinearLayout)findViewById(R.id.instructions_container));

    }

    /**
     * Private helper method to edit a recipe's data
     * @param r - the recipe whose data is to be manipulated
     */
    private void setCurrentRecipe(Recipe r){
        veditTitle.setText(r.name);
        veditTime.setText(r.minutes + "");
        vratingBar.setRating((float) r.rating);
        for(int i=0;i<r.ingredients.size();i++){
            View ingredient = getLayoutInflater().inflate((R.layout.ingredients_fields2), null);
            ((EditText)ingredient.findViewById(R.id.edit_ingredient_title)).setText(r.ingredients.get(i).name);
            ((EditText)ingredient.findViewById(R.id.edit_ingredient_metric)).setText(r.ingredients.get(i).metric);
            ((EditText)ingredient.findViewById(R.id.edit_ingredient_amt)).setText(r.ingredients.get(i).amount == null ? "" : r.ingredients.get(i).amount.toString());
            ingredient.findViewById(R.id.ingredient_remove).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ingredientRemoveClick(v);
                }

            });
            ((LinearLayout)findViewById(R.id.ingredient_container)).addView(ingredient);


        }
        for(int i=0;i<r.steps.size();i++){
            View step = getLayoutInflater().inflate((R.layout.instructions_fields), null);
            ((EditText)step.findViewById(R.id.edit_instruction)).setText(r.steps.get(i));
            ((EditText)step.findViewById(R.id.edit_instructions_time)).setText(r.timePerStep.get(i).toString());
            step.findViewById(R.id.instructions_remove).setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    instructionRemoveClick(v);
                }

            });
            ((LinearLayout)findViewById(R.id.instructions_container)).addView(step);


        }
    }

    /**
     * Private helper method to save the newly edited values to the recipe. If no
     * recipe exists, create a new one and save the values to it.
     */
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


        /** FOR EACH INPUTTED INGREDIENT, ADD INGREDIENTS VIA r.ingredients.add(INGREDIENT) **/

        ArrayList<View> ingredients = new ArrayList<View>();
        r.ingredients = new ArrayList<>();
        LinearLayout ingreCont = (LinearLayout)findViewById(R.id.ingredient_container);
        final int childCount = ingreCont.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = ingreCont.getChildAt(i);
            String name = ((EditText)child.findViewById(R.id.edit_ingredient_title)).getText().toString();
            String metric = ((EditText)child.findViewById(R.id.edit_ingredient_metric)).getText().toString();
            Float amt = null;
            String amtString = ((EditText)child.findViewById(R.id.edit_ingredient_amt)).getText().toString();
            if (!amtString.isEmpty()) amt = Float.parseFloat(amtString);
            Ingredient ing = new Ingredient(name, amt, metric);
            r.ingredients.add((ing));

        }

        r.steps = new ArrayList<>();
        r.timePerStep = new ArrayList<>();
        ArrayList<View> steps = new ArrayList<View>();
        LinearLayout stepsCont = (LinearLayout)findViewById(R.id.instructions_container);
        final int stepsCount = stepsCont.getChildCount();
        for (int i = 0; i < stepsCount; i++) {
            final View child = stepsCont.getChildAt(i);
            String step = ((EditText)child.findViewById(R.id.edit_instruction)).getText().toString();
            Integer time = 0;
            String timeString = ((EditText)child.findViewById(R.id.edit_instructions_time)).getText().toString();
            if (!timeString.isEmpty()) time = Integer.parseInt(timeString);
            r.timePerStep.add(time);
            r.steps.add((step));

        }
        // SAMPLE CODE
        /* for (int i = 0; i < INGREDIENTS FROM LINEARLAYOUT; i==) {
             r.ingredients.add(new Ingredient(STRING FROM LAYOUT, METRIC FROM LAYOUT);
           }
         */
        // temporary test ingredient.
//        r.ingredients.add(new Ingredient("yoloswag", 420));


        RecipeListActivity.recipes.put(r.id,r);
        r.saveRecipe();
        finish();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////  Untouched methods generated by android-studio  ///////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_create, menu);
        return true;
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
