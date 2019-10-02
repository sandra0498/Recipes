package NewRecipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class RecipeProgram {
	public static void main(String[] args) {
		Scanner reader = new Scanner(System.in);
		System.out.println("Please enter the name of a recipe file:");
		String recipeName = reader.nextLine();

		// calls the recipeset to construct the tree
		RecipeSet newTree = new RecipeSet(recipeName);

		boolean keepRunning = true;
		while (keepRunning) {
			System.out.println("Main menu");
			System.out.println("1. Show all recipes ");
			System.out.println("2. Show recipe details");
			System.out.println("3: Add Recipe");
			System.out.println("4: Remove Recipe");
			System.out.println("5: Search for ingredient");
			System.out.println("6: Save Recipe book");
			System.out.println("7: Print tree efficiency");
			System.out.println("8. Exit");
			System.out.println("Please enter a selection:");
			int recipeSelect = reader.nextInt();

			if (recipeSelect == 1) {
				newTree.getAllRecipes();
			}

			else if (recipeSelect == 2) {
				System.out.println("Please input the recipe's title");
				reader.nextLine();
				String recipeTitle = reader.nextLine();
				Recipe chosenRecipe = newTree.findRecipe(recipeTitle);
				if (chosenRecipe != null) {
					System.out.println(chosenRecipe.toString());

				}
				else {
					System.out.println("Recipe title was not found! ");

				}

			}

			else if (recipeSelect == 3) {
				reader.nextLine();
				System.out.println("Please enter the name of file to add:");
				String fileName = reader.nextLine();

				File newfile = new File(fileName);
				Recipe object = null;

				try {
					Scanner file = new Scanner(newfile);
					//file.nextLine();

					while (file.hasNextLine()) {
						String instance = file.nextLine();
						// split the file line into an array of the parameters required
						String[] details = instance.split("\\|");
						String title = details[0];
						String author = details[1];
						String description = details[2];
						int preptime = Integer.parseInt(details[3]);
						int cookingTime = Integer.parseInt(details[4]);

						// splitting the last elements into separate arrays
						String[] ingredients = details[5].split("@");
						String[] steps = details[6].split("@");

						// inserting all the parameters to construct the object
						object = new Recipe(title, author, description, preptime, cookingTime,
								ingredients, steps);
						newTree.add(object);

					}
				}
				catch (FileNotFoundException e) {
					System.out.println(e);
				}


			}


			else if (recipeSelect == 4) {
				reader.nextLine();
				System.out.println("Input the recipe name:");
				String name = reader.nextLine();
				newTree.remove(name);
			}


			else if (recipeSelect == 5) {
				reader.nextLine();
				System.out.println("What ingredient are you looking for?");
				String ingredient = reader.nextLine();

				newTree.findIngredient(ingredient);

				//RecipeBook specialRecipes = r.findIngredient(ingredient);

			}

			else if (recipeSelect == 6) {

				reader.nextLine();
				System.out.println("Please enter the name of the file");
				String newName = reader.nextLine();
				PrintWriter writer = null;

				try {
					writer = new PrintWriter(newName);
					writer.println(newTree.getCount());
				}
				catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				RecipeBook recipes = newTree.getAllRecipes();
				for (int i = 0; i < newTree.getCount(); i++) {
					String adding = "";
					Recipe singleRecipe = recipes.getRecipe(i);
					if (singleRecipe != null) {
						adding += singleRecipe.getTitle();
						adding += "|";
						adding += singleRecipe.getAuthor();
						adding += "|";
						adding += singleRecipe.getDescription();
						adding += "|";
						adding += Integer.toString(singleRecipe.getPrepTime());
						adding += "|";
						adding += Integer.toString(singleRecipe.getCookingTime());
						adding += "|";

						adding += singleRecipe.getIngredients()[0];
						for (int s = 1; s < singleRecipe.getIngredients().length; s++) {
							adding += "@";
							adding += singleRecipe.getIngredients()[s];

						}
						adding += "|";

						adding += singleRecipe.getSteps()[0];

						for (int a = 1; a < singleRecipe.getNumberOfSteps(); a++) {
							adding += "@";
							adding += singleRecipe.getSteps()[a];
						}
						writer.println(adding);
					}
				}
				writer.close();
			}

			else if (recipeSelect == 7) {
				double maxAmount = Math.pow(2, newTree.getHeight()) - 1;
				double treeEfficiency = newTree.getCount() / maxAmount;
				System.out.println("Efficiency " + treeEfficiency);

			}
			else if (recipeSelect == 8) {
				System.out.println("Goodbye!");
				keepRunning = false;

			}


		}

	}
}



