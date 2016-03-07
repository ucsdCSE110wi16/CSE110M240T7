package com.example.monroe.cse110recipes.datastructure;

import com.example.monroe.cse110recipes.recipes.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by tqhoang on 2/7/16.
 */
public class NST {
    private NSTNode root;
    private HashMap<Recipe, NSTNode> recipes;

    /**
     * Public constructor. Used to create an instance of the N-ary Search Tree. Creates a root node
     * and initializes an empty HashMap to contain recipes
     */
    public NST() {
        this.root = new NSTNode("ROOT");
        this.recipes = new HashMap<>();
    }

    /**
     * Insert method. Used to insert ingredient nodes into the N-ary Search Tree. Loops through
     * each child of the current node, if it already exists, do nothing and repeat for the next
     * node.
     * @param ingredients - The name of the ingredient for the new node in the NST
     * @param recipe - The recipe of the list of ingredients
     */
    public void insertRecipe(ArrayList<String> ingredients, Recipe recipe) {
        // Local nodes to keep track of the current node and it's parent.
        NSTNode currentNode = root;
        NSTNode parentNode = root;

        // For each index of ingredients,
        for (String index : ingredients) {
            // Create a new NSTNode to be added
            NSTNode newNode = new NSTNode(index);
            // Create an index variable initialized to 0
            int child = 0;
            // Switch for the while loop
            boolean search = true;
            while (search) {
                // If the current node is not a child, create a new child node and set it's parent
                if (child >= currentNode.getChildren().size()) {
                    currentNode.getChildren().add(newNode);
                    newNode.setParent(parentNode);
                    search = false;
                }
                // If we have not found the child and still have more childre, go to the next child
                else if (!currentNode.getChildren().get(child).getIngredient().equals(index)) {
                    child++;
                }
                // If the child already exists, set currentNode and parentNode, then keep looping
                else if (currentNode.getChildren().get(child).getChildren().equals(index)) {
                    currentNode = currentNode.getChildren().get(child);
                    parentNode = currentNode.getParent();
                    search = false;
                }
            }

            // If we are a the final ingredient, set the recipe and add the node into the HashMap
            if (ingredients.get(ingredients.size() - 1).equals(index)) {
                currentNode.setRecipe(recipe);
                recipes.put(currentNode.getRecipe(), currentNode);
            }
        }
    }

    /**
     * Delete the desired recipe from the NST, along with it's ingredient nodes as long as they
     * are not ingredients to another recipe.
     * @param recipe - The recipe to be deleted
     */
    public void deleteRecipe(Recipe recipe) {
        // If the recipe does not exist, do nothing.
        if (!recipes.containsKey(recipe)) {
            return;
        }

        // The node containing the recipe, and a temporary node for traversing
        NSTNode currentNode = recipes.get(recipe);
        NSTNode tempNode;

        // If the current node has no children, clear it, and traverse upwards to delete all
        // ingredients until we reach a node with another child or recipe
        if (currentNode.getChildren().isEmpty()) {
            // Remove the recipe from the hashmap
            recipes.remove(recipe);
            // Set all the values of the current node to null and traverse up the NST
            while (currentNode.getChildren().isEmpty() && currentNode.getRecipe() == null) {
                currentNode.setRecipe(null);
                currentNode.setIngredient(null);
                tempNode = currentNode;
                currentNode = currentNode.getParent();
                tempNode.setParent(null);
            }
        }
    }

    /** Returns the root node of the NST **/
    public NSTNode getRoot() { return this.root; }

    /**
     * Acquire the list of ingredients for the specified recipe
     * @param recipe - The recipe who's ingredients are to be returned
     * @return - The ingredients for the specified recipe, in alphabetical order. If the recipe
     *           does not exist, return an empty ingrdients list.
     */
    public ArrayList<String> getRecipe(Recipe recipe) {
        // If the recipe does not exist, return an empty array list
        if (! recipes.containsKey(recipe)) {
            return new ArrayList<>();
        }
        // Create a local node at the node containing the recipe
        NSTNode currentNode = recipes.get(recipe);
        // Create a new ingredients list to be returned
        ArrayList<String> ingredients = new ArrayList<>();

        // Traverse up the NST, recording the value of each ingredient until the root.
        while (currentNode.getParent() != root) {
            ingredients.add(currentNode.getIngredient().toString());
            currentNode = currentNode.getParent();
        }

        // Sort the ingredients list in alphabetical order and return
        Collections.sort(ingredients);
        return ingredients;
    }
}
