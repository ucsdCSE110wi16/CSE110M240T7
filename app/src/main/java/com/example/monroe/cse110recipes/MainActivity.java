package com.example.monroe.cse110recipes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.listview);
        String[] values = new String[] { "Pasta fagu", "Cereal", "Oatmeal Paradise",
                "Chicken and Taters", "Thanksgiving Dinner", "Milk", "Ramen", "Ramen (top)",
                "Curry (Steph)", "Netflix and Chili", "Spaghetti"};

        final ArrayList<String> list = new ArrayList<String>();
        final ArrayList<Recipe> list2 = new ArrayList<Recipe>();
        for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
            list2.add(new Recipe(values[i], 30, 2));
        }
        final StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        final StableArrayAdapter2 adapter2 = new StableArrayAdapter2(this,
                R.layout.recipe_listview, list2);
        listview.setAdapter(adapter2);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Recipe item = (Recipe) parent.getItemAtPosition(position);
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("id: " + item.id);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                Intent myIntent = new Intent(MainActivity.this, RecipeActivity.class);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class StableArrayAdapter2 extends ArrayAdapter<Recipe> {

        HashMap<Recipe, Integer> mIdMap = new HashMap<Recipe, Integer>();
        List<Recipe> recipes;
        Context context;

        public StableArrayAdapter2(Context context, int textViewResourceId,
                                  List<Recipe> objects) {
            super(context, textViewResourceId, objects);
            recipes = objects;
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.recipe_listview, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.recipeName);
            TextView timeView = (TextView) rowView.findViewById(R.id.recipeTime);
            textView.setText(recipes.get(position).name);
            // change the icon for Windows and iPhone
            String time = recipes.get(position).minutes + "minutes";
            ((RatingBar) rowView.findViewById(R.id.recipeRating)).setRating((float)recipes.get(position).rating);

            return rowView;
        }

        @Override
        public long getItemId(int position) {
            Recipe item = getItem(position);
            return item.id;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }
}
