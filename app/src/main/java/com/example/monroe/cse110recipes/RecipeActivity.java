package com.example.monroe.cse110recipes;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

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
        ((TextView)findViewById(R.id.recipe)).setText(r.name);

        ((TextView)findViewById(R.id.cook_Time)).setText(String.valueOf(r.getMinutes()));
        String[] itemList = new String[] {"Pour cereal in bowl first", "Pour Milk in bowl"};
        ArrayAdapter<String> instructionsAdapter = new ArrayAdapter<String>(this, R.layout.simple_row,R.id.steps, itemList);
        ListView stepsListView = (ListView) findViewById(R.id.StartHere);
        stepsListView.setAdapter(instructionsAdapter);

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
        }
        else if (id == R.id.action_notfavorited) {

            mOptionsMenu.findItem(R.id.action_favorited).setVisible(true);
            mOptionsMenu.findItem(R.id.action_notfavorited).setVisible(false);
            currentRecipe.favorite = true;
        }

        return super.onOptionsItemSelected(item);
    }

}
