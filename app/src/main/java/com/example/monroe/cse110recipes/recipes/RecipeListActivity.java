package com.example.monroe.cse110recipes.recipes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.monroe.cse110recipes.MainActivity;
import com.example.monroe.cse110recipes.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {
    // Recipe list activity values
    public static HashMap<Integer, Recipe> recipes = new HashMap<Integer, Recipe>();
    public boolean filterShown = false;
    public Animation slideDownAnimation;
    public Animation slideUpAnimation;
    public String filterRecipeName = "";
    public boolean filterFavorites = false;
    public String filterIngredients = "";
    public StableArrayAdapter mArrayAdapter = null;

    public String filterType = null;

    /**
     * When the activity is started, set up data and views
     * @param savedInstanceState - mapping of parcelable variables
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list_view);

        if (filterType == null)filterType = getIntent().getStringExtra("filterType");
        if (filterType == null) {
            if (savedInstanceState != null) filterType = savedInstanceState.getString("filterType");
        }

        if (filterType != null) {
            if (filterType.equals(MainActivity.FilterTypeFavorites)) filterFavorites = true;
            else if (filterType.equals(MainActivity.FilterTypeRecipe)) {
                showFilters();
                if (filterType.equals((MainActivity.FilterTypeIngredients))) {
                    ((EditText)findViewById(R.id.filter_ingredients)).requestFocus();
                }
            }
        }

        ((ListView) findViewById(R.id.listview)).setEmptyView(findViewById(R.id.emptyRecipes));

        findViewById(R.id.filter_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("tag", "in here");
                filterClick();
            }
        });
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("filterType", filterType);
    }

    /**
     * Set the recipe list filter to favorites depending on parameter
     * @param val - true = filter to favorites only, false = show all
     */
    private void setFilterFavorites(Boolean val){
        ((Switch)findViewById(R.id.filter_favorites)).setChecked(val);
        filterFavorites = val;
    }

    /**
     * Add recipes to the list
     */
    private void populateList(){
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
        // Create a new liew view to display recipes
        final ListView listview = (ListView) findViewById(R.id.listview);
//        if(recipes.size() == 0) {
            Recipe r = new Recipe();
            String[] values = new String[] {};
            ArrayList<Recipe> recipes1 = new ArrayList<Recipe>();
            recipes1 = r.readRecipes();
            for (int i = 0; i < recipes1.size(); i++) {
                r = recipes1.get(i);
                if (r == null) continue;
                recipes.put(recipes1.get(i).id, recipes1.get(i));
            }
//            for (int i = 0; i < values.length; ++i) {
//                Recipe r = new Recipe(values[i], 30, 2);
//                recipes.put(r.id, r);
//            }
//        }
//        else {
//        }

        // Create a new ArrayList to contain recipes
        final ArrayList<Recipe> list2 = new ArrayList<Recipe>();
        Iterator it = recipes.entrySet().iterator();
        while(it.hasNext()){
            list2.add((Recipe) ((HashMap.Entry) it.next()).getValue());

        }

        // Initialize a stable array adapter
        final StableArrayAdapter adapter = mArrayAdapter = new StableArrayAdapter(this,
                R.layout.recipe_listview_item, list2);
        listview.setAdapter(adapter);

        // Upon selection of a recipe on the list, transition to the recipe view
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Recipe item = (Recipe) parent.getItemAtPosition(position);
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setTitle("Alert");
//                alertDialog.setMessage("id: " + item.id);
//                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                alertDialog.show();
                Intent myIntent = new Intent(RecipeListActivity.this, RecipeActivity.class);
                myIntent.putExtra("RecipeID", item.id); //Optional parameters
                startActivity(myIntent);
//                view.animate().setDuration(2000).alpha(0)
//                        .withEndAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                list.remove(item);
//                                adapter.notifyDataSetChanged();
//                                view.setAlpha(1);
//                            }
//                        });
            }

        });

    }

    /**
     * When we return to the list view, resume regular activity
     */
    @Override
    protected void onResume(){
        super.onResume();
        Log.d("tag", "on resume");
//        finish();
//        startActivity(getIntent());

    }

    /**
     * When we restart the list view, start like new
     */
    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("tag", "on restart");
//        finish();
//        startActivity(getIntent());

    }

    /**
     * When we start the list view, call the super constructor and fill the list view with recipes
     * Then check on the filter mode and filter to the desired list of recipes
     */
    @Override
    protected void onStart(){
        super.onStart();
        Log.d("tag", "on start");
        populateList();
        if(filterType == null){
            filterType = getIntent().getStringExtra("filterType");
        }
        if(filterType != null) {
            if (filterType.equals(MainActivity.FilterTypeFavorites)) {
                setFilterFavorites(true);
                mArrayAdapter.getFilter().filter("");
            } else if (filterType.equals(MainActivity.FilterTypeRecipe)) {
                showFilters();
            }
        }
//        finish();
//        startActivity(getIntent());

    }

    /**
     * Upon creation, create the menu options according to the menu_recipe_list_view
     * @param menu - The menu in which we create options
     * @return - always true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipe_list_view, menu);
        return true;
    }

    /**
     * When an option is selected, get it's id and open the recipe view
     * @param item - The menu option that is selected
     * @return - true if the item is selected, false if it isn't
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.action_add){

            Intent myIntent = new Intent(RecipeListActivity.this, RecipeCreateActivity.class);
            startActivity(myIntent);

        }
        else if(id==R.id.action_filter){
            if(filterShown){
                hideFilters();
            }
            else{
                showFilters();
            }

        }
        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Show filtered recipes
     */
    public void showFilters(){
        View recipeView = findViewById(R.id.recipe_filter);
        //recipeView.startAnimation(slideUpAnimation);
        recipeView.setVisibility(View.VISIBLE);
        filterShown = true;

    }

    /**
     * Hide filtered recipes
     */
    public void hideFilters(){
        View recipeView = findViewById(R.id.recipe_filter);
        //recipeView.startAnimation(slideDownAnimation);
        recipeView.setVisibility(View.GONE);
        filterShown = false;

    }

    /**
     * ArrayAdapter that is stable and used for displaying recipes
     */
    private class StableArrayAdapter extends ArrayAdapter<Recipe> implements Filterable {

        private List<Recipe> originalData = null;
        private List<Recipe>filteredData = null;
        HashMap<Recipe, Integer> mIdMap = new HashMap<Recipe, Integer>();
        List<Recipe> recipes;
        Context context;
        private ItemFilter mFilter = new ItemFilter();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<Recipe> objects) {
            super(context, textViewResourceId, objects);
            recipes = objects;
            originalData = filteredData = objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            List<Recipe> recipes = filteredData;
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.recipe_listview_item, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.recipeName);
            TextView timeView = (TextView) rowView.findViewById(R.id.recipeTime);
            textView.setText(recipes.get(position).name);
            // change the icon for Windows and iPhone
            String time = recipes.get(position).minutes + " minutes";
            timeView.setText(time);

            ((RatingBar) rowView.findViewById(R.id.recipeRating)).setRating((float) recipes.get(position).rating);
            if(recipes.get(position).favorite){

                rowView.findViewById(R.id.favorited).setVisibility(ImageView.VISIBLE);
                rowView.findViewById(R.id.notfavorited).setVisibility(ImageView.GONE);
            }
            else{

                rowView.findViewById(R.id.favorited).setVisibility(ImageView.GONE);
                rowView.findViewById(R.id.notfavorited).setVisibility(ImageView.VISIBLE);

            }

            return rowView;
        }

        @Override
        public int getCount() {
            return filteredData.size();
        }

        @Override
        public long getItemId(int position) {
            Recipe item = getItem(position);
            return item.id;
        }

        @Override
        public Filter getFilter(){
            return mFilter;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        private class ItemFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<Recipe> list = originalData;

                int count = list.size();
                final ArrayList<Recipe> nlist = new ArrayList<Recipe>(count);

                Recipe filterableRecipe ;

                for (int i = 0; i < count; i++) {
                    filterableRecipe = list.get(i);
                    Log.d("tag", "recipe: "+i);
                    if(filterFavorites && !filterableRecipe.favorite){
                        continue;
                    }
                    if(!filterRecipeName.isEmpty() && !filterableRecipe.name.toLowerCase().trim().contains(filterRecipeName.toLowerCase().trim())){
                        continue;
                    }
                    //TODO
                    if(!filterIngredients.isEmpty()){
                        String[] ingredients = filterIngredients.split(",");
                        for(String ing : ingredients){
                        }
                    }
                    nlist.add(filterableRecipe);
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<Recipe>) results.values;
                notifyDataSetChanged();
            }

            public Filter getFilter() {
                return mFilter;
            }

        }

    }

    /**
     * Check filtering
     */
    public void filterClick(){
        Log.d("tag", "in here 1");
        filterFavorites = ((Switch)findViewById(R.id.filter_favorites)).isChecked();
        filterRecipeName = ((EditText)findViewById(R.id.filter_recipe_name)).getText().toString();
        mArrayAdapter.getFilter().filter("");
    }
}