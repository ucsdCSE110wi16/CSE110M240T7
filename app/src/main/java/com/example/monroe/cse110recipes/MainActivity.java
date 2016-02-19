package com.example.monroe.cse110recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final String FilterTypeIngredients = "ingredients";
    public static final String FilterTypeRecipe = "recipe";
    public static final String FilterTypeFavorites = "favorites";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        final ActionBar ab = getSupportActionBar();


        ImageView i = (ImageView)findViewById(R.id.img1);
        i.setBackgroundResource(R.drawable.ness);
        //i.setBackgroundResource(R.drawable.gif);

//        AnimationDrawable pro = (AnimationDrawable)i.getBackground();
//        pro.start();
    }

    public void searchByIngredientsClick(View v){

        Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
        myIntent.putExtra("filterType", FilterTypeIngredients); //Optional parameters
        startActivity(myIntent);
    }

    public void searchByRecipeClick(View v){

        Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
        myIntent.putExtra("filterType", FilterTypeRecipe); //Optional parameters
        startActivity(myIntent);
    }

    public void searchByFavoritesClick(View v){

        Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
        myIntent.putExtra("filterType", FilterTypeFavorites); //Optional parameters
        startActivity(myIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
