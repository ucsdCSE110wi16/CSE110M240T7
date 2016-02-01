package com.example.monroe.cse110recipes;

/**
 * Created by monroe on 1/31/2016.
 */
public class Recipe {
    String name;
    int minutes;
    int rating;
    int id;
    private static int _id = 0;
    public Recipe(String name, int minutes, int rating){
        this.name = name;
        this.minutes = minutes;
        this.rating = rating;
        this.id = _id++;
    }
}
