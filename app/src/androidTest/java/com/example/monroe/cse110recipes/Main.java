import com.example.monroe.cse110recipes.NST;
import com.example.monroe.cse110recipes.recipes.Recipe;
import com.example.monroe.cse110recipes.datastructure.NSTNode;

import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		NST tree = new NST();
		ArrayList<String> ingredients = new ArrayList<>();
		ingredients.add("Apple");
		ingredients.add("Banana");
		ingredients.add("Cherry");
		ingredients.add("Date");
		ingredients.add("Ebola");
		Recipe recipe = new Recipe();
		recipe.setName("Fruit Salad");
		
		tree.insertRecipe(ingredients, recipe);
		ArrayList<NSTNode> temp = tree.getRoot().getChildren();
		System.out.println(tree.getRoot().getIngredient());
		for (NSTNode child : temp) {
			System.out.println("Ingredient " + child.getIngredient());
			System.out.println("Parent " + child.getParent().getIngredient());
			child = child.getChildren().get(0);
			System.out.println("Ingredient " + child.getIngredient());
			System.out.println("Parent " + child.getParent().getIngredient());
		}
		System.out.println(tree.getRecipe(recipe));
	}
	
}
