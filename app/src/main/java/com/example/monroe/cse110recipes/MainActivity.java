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

public class MainActivity extends AppCompatActivity {

    public static final String FilterTypeIngredients = "ingredients";
    public static final String FilterTypeRecipe = "recipe";
    public static final String FilterTypeFavorites = "favorites";
    public static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);
//        final ActionBar ab = getSupportActionBar();


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
