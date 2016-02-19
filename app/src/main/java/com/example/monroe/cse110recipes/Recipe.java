package com.example.monroe.cse110recipes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by monroe on 1/31/2016.
 * edited by Cliff on 2/1/2016
 */
public class Recipe {//extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "Recipe_Database";
    private static final int DATABASE_VERSION = 1;
    public static final String COLUMN_ID = "_id";
    public static String strSeparator = "_,_";


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
    }
    
    //retrieve a serialized file from an android device
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
    } /*
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
