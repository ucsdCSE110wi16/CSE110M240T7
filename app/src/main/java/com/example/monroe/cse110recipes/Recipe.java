package com.example.monroe.cse110recipes;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import android.content.Context;
/**
 * Created by monroe on 1/31/2016.
 * edited by Cliff on 2/1/2016
 */
public class Recipe {
    String name; //recipe name
    int minutes; //time to cook
    int rating; //rating of the recipe
    int id; //id of the recipe??
    boolean favorite = false;
    private static int _id = 0;

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

    }
    //constructor with some parameters
    public Recipe(String name, int minutes, int rating){
        this.name = name;
        this.minutes = minutes;
        this.rating = rating;
        this.id = _id++;
    }
    
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
    }
}
