package com.example.monroe.cse110recipes.recipes;

import android.os.Environment;
import android.util.Log;

import com.example.monroe.cse110recipes.Ingredients.Ingredient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by monroe on 1/31/2016.
 * edited by Cliff on 2/1/2016
 */
public class Recipe  implements Serializable {//extends SQLiteOpenHelper{
    // Recipe database and name string variables
    private static final String DATABASE_NAME = "Recipe_Database";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static final String RECIPE_NAMES_FILE_NAME = "__RECIPES";
    public static final String RECIPE_FOLDER_NAME = "RECIPES";
    public static String strSeparator = "_,_";
    static final long serialVersionUID = -687991492884005033L;


    // Recipe data values
    String name; //recipe name
    ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
    int minutes; //time to cook
    int rating; //rating of the recipe
    int id; //id of the recipe??
    boolean favorite; // one of your favorite recipes or not
    private static int _id = 0;

    // Recipe misc. values
    String[] tags; //the series of tags that can be used to search for the recipe
    int difficulty; //difficulty level
    String lastUsed; //date the recipe was last accessed
    ArrayList<String> steps = new ArrayList<>(); //list of the steps in string format, ordered chronologically
    ArrayList<Integer> timePerStep = new ArrayList<>(); //time that each step takes, indexed the same as steps[]
/*
    public final String DATABASE_CREATE_OBJECT = "create table " +
            this.name + "(" + COLUMN_ID + "integer primary key autoincrement, "
            + this.minutes + "int, "
            + this.rating + "int, "
            + this.id + "int, "
            + this.favorite + "int, "
            + this.tags + "string, "
            + this.difficulty + "int, "
            + this.lastUsed + "string, "
            + this.steps + "string, "
            + this.timePerStep + "int);"; */

    /**
     * Default constructor. Initialize favorite to false, and set id to _id and increment _id.
     * Other values of the recipe are null
     */
    public Recipe() {
        this.favorite = false;
        this.id = _id++;
    }

    /**
     * Recipe constructor for initializing all data values of the recipe.
     * Sets the specified datatypes and initialize favorite to false and id to the the appropriate
     * id value.
     * @param name - Name of the recipe
     * @param minutes - Time to prepare the recipe
     * @param rating - Rating of the recipe
     */
    public Recipe (String name, int minutes, int rating) {
        this.name = name;
        this.minutes = minutes;
        this.rating = rating;
        this.favorite = false;
        this.id = _id++;
    }

    /*
    public Recipe (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    //constructor with some parameters
    public Recipe(String name, int minutes, int rating, Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.name = name;
        this.minutes = minutes;
        this.rating = rating;
        this.id = _id++;
    }*/

    //Serialize and save a file on an android device
    /*
    public void saveRecipe(Context context) {
    	try {
            FileOutputStream fos = context.openFileOutput(name, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this);
            os.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Before reading in recipes and loading them, make sure that the file exists
     */
    public static void ensureRecipesFileExists(){
        File f = new File(Environment.getExternalStorageDirectory(), RECIPE_NAMES_FILE_NAME);
        try{
            f.createNewFile();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Before reading in recipes and loading them, make sure that the directory exists
     */
    public static void ensureRecipesFolderExists(){
        File f = new File(Environment.getExternalStorageDirectory(), RECIPE_FOLDER_NAME);
        try{
            f.mkdirs();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Read the list of recipes to initialize the database
     * @return - an arraylist of recipes representing the database
     */
    public static ArrayList<Recipe> readRecipes(){
        // Create a temporary recipes arraylist
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        try {
            // Read from a local file containing known recipes
            File f = new File(Environment.getExternalStorageDirectory(), RECIPE_FOLDER_NAME);
            f.mkdirs();
            for(File recipeFile : f.listFiles()){
                Log.d("recipe file name:",recipeFile.getName());
                recipes.add(loadRecipe(recipeFile.getName()));
            }
            // Return the arraylist of recipes
            return recipes;
//            BufferedReader br = new BufferedReader(new FileReader(f));
//            String line;
//            while((line = br.readLine()) != null){
////                int count = Integer.parseInt(line);
//                String recipeName = line;
//                recipes.add(loadRecipe(recipeName));
//
//            }
//            br.close();
//            return recipes;
        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
        // Exception for when there is an issue reading the file
        catch (Exception e){
            e.printStackTrace();
            return recipes;
        }


    }
    public static ArrayList<Recipe> readRecipesOLD(){
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        try {
            File f = new File(Environment.getExternalStorageDirectory(), RECIPE_NAMES_FILE_NAME);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while((line = br.readLine()) != null){
//                int count = Integer.parseInt(line);
                String recipeName = line;
                recipes.add(loadRecipe(recipeName));

            }
            br.close();
            return recipes;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    /**
     * Save all recipes onto a locally stored file to read in upon next execution
     * @param recipes - Collection of recipes to save
     */
    public static void saveRecipes(Collection<Recipe>recipes){



//        FileOutputStream outStream = null;
//        try {
//            File f = new File(Environment.getExternalStorageDirectory(), RECIPE_NAMES_FILE_NAME);
//            BufferedWriter bw = new BufferedWriter(new FileWriter(f));

//            int count = recipes.size();
//            bw.write(Integer.toString(count));
//            bw.newLine();
            Iterator<Recipe> iter = recipes.iterator();
            while(iter.hasNext()){
                Recipe r = iter.next();
//                bw.write(r.name);
//                bw.newLine();
                r.saveRecipe();
            }
//            bw.close();
//        }
//        catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        catch (IOException e) {
//            e.printStackTrace();
//        }
    }
    /**
     * Non-static saveRecipe method. Used for the same purpose as static version; however,
     * does not close file upon completion. Useful for writing newly created recipes
     */
    public void saveRecipe() {
        FileOutputStream outStream = null;
        try {
            File f = new File(new File(Environment.getExternalStorageDirectory(),RECIPE_FOLDER_NAME), name);
            f.createNewFile();
            outStream = new FileOutputStream(f);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(this);
            objectOutStream.close();
        }
        // Exception for when the file does not exist
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Exception for when there is an issue writing to the file
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRecipeOLD() {
        FileOutputStream outStream = null;
        try {
            File f = new File(Environment.getExternalStorageDirectory(), name);
            outStream = new FileOutputStream(f);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(this);
            objectOutStream.close();

            //update recipe names
            f = new File(Environment.getExternalStorageDirectory(), RECIPE_NAMES_FILE_NAME);
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(this.name);
            bw.newLine();
            bw.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
//            File f = new File(Environment.getExternalStorageDirectory(), name);
            File f = new File(new File(Environment.getExternalStorageDirectory(), RECIPE_FOLDER_NAME), this.getName());
            f.delete();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Load all recipes from an input file
     * @param name - The name of the file to be read
     * @return - recipe object for reach recipe
     */
    public static Recipe loadRecipe(String name) {
        // Create return variable
        Recipe r = null;
        // File to be read
        FileInputStream inStream = null;
        try {
            // Open file and read
            File f = new File(new File(Environment.getExternalStorageDirectory(),RECIPE_FOLDER_NAME), name);
            inStream = new FileInputStream(f);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);

            r = (Recipe) objectInStream.readObject();
            objectInStream.close();
        }
        // File not found exception
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Class not found exception
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (OptionalDataException e) {
            e.printStackTrace();
        }
        // Exception for stream corruption while reading
        catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        // Exception for any issue while reading
        catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    /**
     * Read in a recipe and load the recipe int othe database
     * @return - The recipe read
     */
    public Recipe loadRecipe() {
        // Return recipe value
        Recipe r = null;
        // Input stream to be read from
        FileInputStream inStream = null;
        try {
            // Rerad from input stream and save recipe into return value
            File f = new File(Environment.getExternalStorageDirectory(), name);
            inStream = new FileInputStream(f);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);

            r = (Recipe) objectInStream.readObject();
            // Upon completion, close the input stream
            objectInStream.close();
        }
        // File not found
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Class not found
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (OptionalDataException e) {
            e.printStackTrace();
        }
        // Issue reading the stream
        catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        // Errors with input stream
        catch (IOException e) {
            e.printStackTrace();
        }
        // Only return if r is not null
        if (r != null) {
            return r;
        }
        // If r is null, return a new recipe object
        return this;
    }

    /**
     * Convert a given array of strings to a singular string
     * @param array - Array of strings to be converted to a singular string
     * @return - resultant string
     */
    public static String convertArrayToString(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str+array[i];
            if (i<array.length - 1) {
                str = str +strSeparator;
            }
        }
        return str;
    }

    /**
     * Convert a singular string to an array of strings
     * @param str - Singular string to be converted to an array
     * @return - Array of strings derived from str
     */
    public static String[] convertStringToArray (String str) {
        String[] arr = str.split(strSeparator);
        return arr;
    }

    /** Setter methods to set recipe values **/
    public void setName(String name) { this.name = name; }
    public void setMinutes(int minutes) { this.minutes = minutes; }
    public void setRating(int rating) { this.rating = rating; }
    public void setId(int id) { this.id = id; }

    /** Getter methods to return recipe values **/
    public String getName(){
        return name;
    }
    public int getMinutes(){
        return minutes;
    }
    public int getId(){
        return id;
    }
    public int getRating(){
        return rating;
    }
}
