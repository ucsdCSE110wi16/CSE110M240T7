package com.example.monroe.cse110recipes.datastructure;

import java.util.ArrayList;

public class Node<Ingredient> {
    private Ingredient ingredient;
    private ArrayList<Node<Ingredient>> children;
    private Node<Ingredient> parent;

    /**
     * Public default constructor. Creates an empty arraylist to contain children nodes. Used only
     * to create the root, and the start of child nodes.
     */
    public Node() {
        this.parent = null;
        this.children = new ArrayList<>();
    }

    /**
     * Public constructor. Creates an empty arraylist to contain children nodes. Initializes the
     * ingredient and parent values.
     * @param ingredient - The name of the ingredient of this node.
     */
    public Node(Ingredient ingredient) {
        this();
        this.ingredient = ingredient;
    }

    /**
     * Look through all children. If a child containing the same ingredient exists, do nothing.
     * Otherwise, create a child node and set it's parents.
     * @param child - The child to be added into the tree.
     */
    public void addNode(Node<Ingredient> child) {
        if (!findDuplicate(child)) {
            child.setParent(this);
            this.getChildren().add(child);
        }

        nextNode(this, child.getIngredient());
    }

    /** Getters and setters for private members **/
    public Ingredient getIngredient() { return this.ingredient; }
    public ArrayList<Node<Ingredient>> getChildren() { return this.children; }
    public Node<Ingredient> getParent() { return this.parent; }

    public void setNode(Node<Ingredient> node) {
        this.ingredient = node.ingredient;
        this.children = node.children;
        this.parent = node.parent;
    }

    public void setParent(Node<Ingredient> parent) {
        this.parent = parent;
    }

    /**
     * Moves on to the next node in the ingredients list
     * @param node - The node to be changed. This will be changed to the next node
     * @param ingredient - The ingredient name to look for for the next node
     */
    private void nextNode(Node<Ingredient> node, Ingredient ingredient) {
        for (int index = 0; index < children.size(); index++) {
            if (node.getChildren().get(index).getIngredient().equals(ingredient)) {
                node.setNode(this.getChildren().get(index));
            }
        }
    }

    /**
     * Iterate through all children of the current node. If a node with the same ingredient exists,
     * return true. Else, return false
     * @param child - The child who's ingredient is being checked for duplicates
     * @return - FALSE if no duplicate, TRUE if a duplicate is found
     */
    private boolean findDuplicate(Node<Ingredient> child) {
        boolean found = false;
        for (int index = 0; index < this.getChildren().size(); index++) {
            if (!found) {
                if (equals(child)) {
                    found = true;
                }
            }
        }

        return found;
    }

    /**
     * Checks to see if the ingredient of this node is the same as the ingredient of the parameter
     * @param node - The node who's ingredient is to be compared to this node
     * @return - FALSE if not the same, TRUE if it is.
     */
    private boolean equals(Node<Ingredient> node) {
        return node.getIngredient().equals(this.getIngredient());
    }
}