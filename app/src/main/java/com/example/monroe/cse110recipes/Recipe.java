package com.example.monroe.cse110recipes;

import android.os.Environment;
import android.util.Log;

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
    private static final String DATABASE_NAME = "Recipe_Database";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static final String RECIPE_NAMES_FILE_NAME = "__RECIPES";
    public static final String RECIPE_FOLDER_NAME = "RECIPES";
    public static String strSeparator = "_,_";
    static final long serialVersionUID = -687991492884005033L;


    String name; //recipe name
    int minutes; //time to cook
    int rating; //rating of the recipe
    int id; //id of the recipe??
    boolean favorite = false;
    private static int _id = 0;
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

    //setter methods
    public void setName(String name){
        this.name = name;
    }

    public void setMinutes(int minutes){
        this.minutes = minutes;
    }

    public void setRating(int rating){
        this.rating = rating;
    }

    public void setId(int id){
        this.id = id;
    }

    //Getter methods
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

    //basic constructor

    public static int getNewId(){
        return _id++;
    }

    public Recipe () {

        this.id = _id++;
    }
    public Recipe (String name, int minutes, int rating) {
        this.name = name;
        this.minutes = minutes;
        this.rating = rating;
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
    
    String[] tags; //the series of tags that can be used to search for the recipe
    int difficulty; //difficulty level
    String lastUsed; //date the recipe was last accessed
    String[] steps; //list of the steps in string format, ordered chronologically
    int[] timePerStep; //time that each step takes, indexed the same as steps[]
    
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

    public static void ensureRecipesFileExists(){
        File f = new File(Environment.getExternalStorageDirectory(), RECIPE_NAMES_FILE_NAME);
        try{
            f.createNewFile();

        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void ensureRecipesFolderExists(){
        File f = new File(Environment.getExternalStorageDirectory(), RECIPE_FOLDER_NAME);
        try{
            f.mkdir();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public static ArrayList<Recipe> readRecipes(){
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        try {
            File f = new File(Environment.getExternalStorageDirectory(), RECIPE_FOLDER_NAME);
            for(File recipeFile : f.listFiles()){
                Log.d("recipe file name:",recipeFile.getName());
                recipes.add(loadRecipe(recipeFile.getName()));
            }

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
        catch (Exception e){
            e.printStackTrace();
            return null;
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
    public void saveRecipe() {
        FileOutputStream outStream = null;
        try {
            File f = new File(new File(Environment.getExternalStorageDirectory(),RECIPE_FOLDER_NAME), name);
            outStream = new FileOutputStream(f);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);

            objectOutStream.writeObject(this);
            objectOutStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

    public static Recipe loadRecipe(String name) {
        Recipe r = null;
        FileInputStream inStream = null;
        try {
//            File f = new File(Environment.getExternalStorageDirectory(), name);
            File f = new File(new File(Environment.getExternalStorageDirectory(),RECIPE_FOLDER_NAME), name);
            inStream = new FileInputStream(f);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);

            r = (Recipe) objectInStream.readObject();
            r.id = Recipe.getNewId();
            objectInStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (OptionalDataException e) {
            e.printStackTrace();
        }
        catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return r;
    }

    public Recipe loadRecipe() {
        Recipe r = null;
        FileInputStream inStream = null;
        try {
            File f = new File(Environment.getExternalStorageDirectory(), name);
            inStream = new FileInputStream(f);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);

            r = (Recipe) objectInStream.readObject();
            objectInStream.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch (OptionalDataException e) {
            e.printStackTrace();
        }
        catch (StreamCorruptedException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (r != null) {
            return r;
        }
        return this;
    }
    
    //retrieve a serialized file from an android device
    /*
    public Recipe loadRecipe(Context context)  {
        Recipe recipe = null;
        try {
            FileInputStream fis = context.openFileInput(this.name);
            ObjectInputStream is = new ObjectInputStream(fis);
            recipe = (Recipe) is.readObject();
            is.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return recipe;
    }*/ /*
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DATABASE_CREATE_OBJECT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
            db.execSQL("Drop Table If Exists" + this.name);
            onCreate(db);
    } */
    public static String convertArrayToSTring(String[] array) {
        String str = "";
        for (int i = 0; i < array.length; i++) {
            str = str+array[i];
            if (i<array.length - 1) {
                str = str +strSeparator;
            }
        }
        return str;
    }
    public static String[] convertStringToArray (String str) {
        String[] arr = str.split(strSeparator);
        return arr;
    }
}
