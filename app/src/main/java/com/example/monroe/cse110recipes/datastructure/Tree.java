package com.example.monroe.cse110recipes.datastructure;

import com.example.monroe.cse110recipes.Recipe;

import java.util.ArrayList;

public class Tree<Ingredient> {
    private Node<Ingredient> root;
    private ArrayList<Recipe> recipes;

    /**
     * Constructor. When creating the tree, we initialize a new node with ROOT as its ingredient.
     */
    public Tree() {
        root = new Node<Ingredient>();
    }

    /**
     * Returns the root of the tree. Currently broken
     * @return
     */
    public Node<Ingredient> getRoot() {
        return this.root;
    }

    /**
     * Method called to build the tree, starting with just the root, to adding all ingredients
     * into the tree.
     * @param nodes - A list of ingredients to be added into the tree.
     */
    public void buildTree(ArrayList<Node<Ingredient>> nodes) {
        for (int index = 0; index < nodes.size(); index++) {
            root.addNode(nodes.get(index));
        }
    }
}
