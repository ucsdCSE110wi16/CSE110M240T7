import com.example.monroe.cse110recipes.recipes.Recipe;

import java.util.ArrayList;

/**
 * Created by tqhoang on 2/7/16.
 */
public class NSTNode {
    private ArrayList<NSTNode> children;
    private NSTNode parent;
    private String ingredient;
    private Recipe recipe;

    /**
     * "Default" constructor. Only takes in an ingredient parameter. Used to initialize root
     * @param ingredient - The name of the ingredient
     */
    public NSTNode(String ingredient) {
        this.ingredient = ingredient;
        this.children = new ArrayList<>();
        this.recipe = new Recipe();
    }

    /**
     * Public constructor. Used to create a new N-ary Search Tree Node. Calls "default" constructor
     * @param ingredient - The name of the ingredient for this node
     * @param parent - The parent of this node
     */
    public NSTNode(String ingredient, NSTNode parent) {
        this(ingredient);
        this.parent = parent;
    }

    /** Getters and setters for private members of NSTNode **/
    public ArrayList<NSTNode> getChildren() { return children; }
    public NSTNode getParent() { return parent; }
    public String getIngredient() { return ingredient; }
    public Recipe getRecipe() { return recipe; }

    public void setParent(NSTNode node) { this.parent = node; }
    public void setIngredient(String ingredient) { this.ingredient = ingredient; }
    public void setRecipe(Recipe recipe) { this.recipe = recipe; }
}