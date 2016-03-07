package com.example.monroe.cse110recipes;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.monroe.cse110recipes.recipes.Recipe;
import com.example.monroe.cse110recipes.recipes.RecipeListActivity;

public class MainActivity extends AppCompatActivity {
    //////////////////////////////////////////////////////////////////////////////////////
    /*************************************************************************************
     * THEY WANT TO SEE A LIST OF ALL INGREDIENTS, ALL FAVORITES, AND 1 PAGE FOR SEARCHING.
     ************************************************************************************/
    //////////////////////////////////////////////////////////////////////////////////////
    public static final String FilterTypeIngredients = "ingredients";
    public static final String FilterTypeRecipe = "recipe";
    public static final String FilterTypeFavorites = "favorites";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;

    /**
     * When the application is started, set up data and views
     * @param savedInstanceState - mapping of parcelable variables
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call AppCompatActivity's constructor with savedInstanceState for activities
        super.onCreate(savedInstanceState);
        // Set the content view to the main activity
        setContentView(R.layout.activity_main);


        ImageView i = (ImageView)findViewById(R.id.img1);
        getWritePermission();
        //i.setBackgroundResource(R.drawable.gif);

//        AnimationDrawable pro = (AnimationDrawable)i.getBackground();
//        pro.start();
    }
    public void getWritePermission(){

        String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        int res = getApplicationContext().checkCallingOrSelfPermission(permission);
        Log.d("WTF", (res == PackageManager.PERMISSION_GRANTED) ? "haz" : "not haz");
        if(res==PackageManager.PERMISSION_GRANTED){

        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        }

    }

    @Override
     public void onRequestPermissionsResult(int requestCode,
                                            String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    String permission = "android.permission.WRITE_EXTERNAL_STORAGE";
                    int res = getApplicationContext().checkCallingOrSelfPermission(permission);
                    Log.d("WTF", (res == PackageManager.PERMISSION_GRANTED) ? "hazzzz" : "not hazzz");
                    Recipe.ensureRecipesFolderExists();

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setMessage("Yeah, we're gonna need that permission. Thaaanks");
                    dlgAlert.setTitle("How bout you grant that permission?");
                    dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            getWritePermission();
                        }});
                    dlgAlert.setCancelable(false);
                    dlgAlert.create().show();
                    dlgAlert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            getWritePermission();

                        }
                    });

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    /**
     * Start am activity that allows for searching of recipes
     * @param v - View
     */
    public void searchByIngredientsClick(View v){

        Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
        myIntent.putExtra("filterType", FilterTypeIngredients); //Optional parameters
        startActivity(myIntent);
    }

    /**
     * Start an activity that displays all known recipes
     * @param v - View
     */
    public void searchByRecipeClick(View v){

        Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
        myIntent.putExtra("filterType", FilterTypeRecipe); //Optional parameters
        startActivity(myIntent);
    }

    /**
     * Start an activity that displays only favorite recipes
     * @param v - View
     */
    public void searchByFavoritesClick(View v){

        Intent myIntent = new Intent(MainActivity.this, RecipeListActivity.class);
        myIntent.putExtra("filterType", FilterTypeFavorites); //Optional parameters
        startActivity(myIntent);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    ///////////////////////  Untouched methods generated by android-studio  ///////////////////////
    ///////////////////////////////////////////////////////////////////////////////////////////////

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
