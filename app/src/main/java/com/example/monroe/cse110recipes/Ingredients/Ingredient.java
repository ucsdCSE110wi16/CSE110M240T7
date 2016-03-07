package com.example.monroe.cse110recipes.Ingredients;

import java.io.Serializable;

/**
 * Created by tqhoang on 3/7/16.
 */
public class Ingredient   implements Serializable {
    public String name;
    public Float amount;
    public String metric;
    static final long serialVersionUID = -687991492884005034L;


    public Ingredient(String s, Float i, String m) {
        name = s;
        amount = i;
        metric = m;
    }
}
