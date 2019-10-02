package NewRecipe;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


/**
 *Responsible for updating the recipes taken from a text file.
 * Provides methods in order to access, add, or remove recipes.
 */
public class RecipeBook {
	private Recipe[] mRecipes;
	private int mCount;

	public RecipeBook() {
		mRecipes = new Recipe[4];
		mCount = 0;
	}
	/**
	 * The constructor opens the text file and constructs the several recipes contained.
	 * @param recipeName The name of the text file provided by the user, if the
	 *file is not found then a FileNotFoundException is thrown.
	 */
	public RecipeBook(String recipeName) {
		File inputFile = new File(recipeName);

		try {
			Scanner file = new Scanner(inputFile);

			int firstLine = file.nextInt();
			double x = Math.log(firstLine);
			double y = Math.ceil(x);
			double realSize = Math.pow(2 , (y + 1));
			int realSize1 = (int) realSize;


			//mRecipes = new Recipe[realSize1];


			// this is needed to ignore the first line of the file
			file.nextLine();
    		mCount = 0;

			for (int i = 0; i < realSize1; i++) {
				if (!file.hasNextLine()) {
					break;
				}
				String instance = file.nextLine();

				// split the file line into an array of the parameters required
				String[] details = instance.split("\\|");
				String title = details[0];
				String author = details[1];
				String description = details[2];
				int preptime = Integer.parseInt(details[3]);
				int cookingTime = Integer.parseInt(details[4]);

				// splitting the last elements into separate arrays
				String [] ingredients = details[5].split("@");
				String[] steps = details[6].split("@");

				// inserting all the parameters to construct the object
				Recipe object = new Recipe(title, author, description, preptime, cookingTime, ingredients, steps);



				// adding the object to the array
				mRecipes[i] = object;
				mCount ++;


			}
		}
		catch (FileNotFoundException ex) {
			mRecipes = new Recipe[2];
			System.out.println("File was not found");

		}
	}




	/**
	 * Provides the number of recipes contained in the recipe book.
	 * @return The amount of recipes in the recipe book .
	 */

	public int getRecipeCount() {
		return mCount;
	}

	/**
	 * Allows for access of a certain recipe, given the index.
	 * @param index The specific position where to acquire the element in the array.
	 * @return A specific recipe from the array, given the index.
	 */

	public Recipe getRecipe(int index) {
		return  mRecipes[index];
	}

	/**
	 * @param ingredient The ingredient that the user is searching for.
	 * @param startingIndex position of the first recipe that contains the ingredient
	 * @return The next position where the ingredient appears
	 * Returns -1 if there is no recipe that contains that ingredient
	 */

	public int findIngredient(String ingredient, int startingIndex) {
		for (int i = startingIndex; i < getRecipeCount(); i++) {
			if (mRecipes[i].usesIngredient(ingredient)) {
				return i;
			}
		}
		return -1;

	}

	/**
	 * @param ingredient The ingredient that the user is searching for within the different recipes.
	 * @return The first index where the ingredient occurs within the different recipes.
	 */

	public int findIngredient(String ingredient) {
		return findIngredient(ingredient,0);
	}


	/**
	 * Gives array new size by doubling the previous size
	 */
	private void resize() {
		Recipe [] temp = new Recipe[mCount* 2];
		for (int i = 0;  i < mCount; i++) {
			temp[i] = mRecipes[i];
		}
		mRecipes = temp;
	}


	/**
	 * Adds a recipe last in the array.
	 * @param v The recipe that needs to be added last.
	 */
	public void addLast(Recipe v) {
		//figure if array needs to be resized?

		if (mCount == mRecipes.length) {
			resize();
		}
		mRecipes[mCount] = v;
		mCount += 1;

	}


	/**
	 * Allows user to insert a recipe in a particular place in the recipe book.
	 * @param index Where the recipe needs to be added.
	 * @param v The recipe that needs to be added.
	 */
	public void insert(int index, Recipe v) {
		if (index >= mCount || index < 0) {
			throw new ArrayIndexOutOfBoundsException("Index is out of bounds");
		}
		if (mCount == mRecipes.length) {
			resize();
			//System.out.println(mRecipes.length);
		}

		shiftRight(index);
		mRecipes[index] = v;
		mCount += 1;


	}

	/**
	 * Allows for removal of a recipe at the given position.
	 * @param index Given a number, the recipe at that number is
	 * supposed to be removed.
	 */
	public void removeAt(int index) {
		if (index >= mCount || index < 0) {
			throw new ArrayIndexOutOfBoundsException("Index is out of bounds");
		}
		else if (index == mCount - 1) {
			mRecipes[index] = null;
			mCount -= 1;

		}

		else {
			mRecipes[index] = null;
			shiftLeft(index);
			mCount -= 1;
		}

	}


	/**
	 * Responsible for finding if the string provided is the title of any recipe.
	 * @param title The recipe title that is supposed to be found.
	 * @return Index is returned if the title is found.
	 * Returns -1 if the title does not match with any.
	 */
	public int findRecipeTitle(String title) {

		for (int i = 0; i < mCount; i++) {
			Recipe chosenRecipe = mRecipes[i];
			if (chosenRecipe.getTitle().equals(title)) {
				return i;
			}
		}
		return -1;
	}


	/**
	 * The array is shifted from the given array.
	 * @param index The given position of the recipe.
	 */
	public void shiftLeft(int index) {
		for (int i = index; i < mCount - 1; i++) {
			mRecipes[i] = mRecipes[i + 1];
		}

	}


	/**
	 * The array is shifted right from the given index.
	 * @param index The given position of the recipe.
	 */
	public void shiftRight(int index) {
		for (int i = mCount; i > index; i--) {
			mRecipes[i] = mRecipes[i - 1];
		}
	}


	/**
	 * Displays the menu of options, on what to do with the recipes.
	 * @param reader The name of the scanner.
	 */
	public  void menuOptions(Scanner reader) {
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
				firstOption();
			}
			else if (recipeSelect == 2) {
				secondOption(reader);
			}
			else if (recipeSelect == 3) {
				thirdOption(reader);
			}
			else if (recipeSelect == 4) {
				fourthOption(reader);
			}
			else if (recipeSelect == 5) {
				fifthOption(reader);
			}
			else if (recipeSelect == 6) {
				sixthOption(reader);
			}
			else if (recipeSelect == 7) {

			}
			else if (recipeSelect == 8) {
				System.out.println("Goodbye!");
				keepRunning = false;
			}
		}
	}


	/**
	 * Prints out all the recipes from the recipe book.
	 */
	public  void firstOption() {
		System.out.println("All " + "" + getRecipeCount() + " " + "recipes");

		for (int index = 0; index < getRecipeCount(); index++) {

			System.out.println(((index + 1) + ". " + getRecipe(index)));
		}
	}


	/**
	 * Enter a number to look up a certain recipe.
	 * @param reader The name of the scanner object.
	 */
	public  void secondOption(Scanner reader) {
		System.out.println("Please enter a recipe number");
		int recipeNum = reader.nextInt();
		if (recipeNum > 0 && recipeNum <= getRecipeCount()) {
			Recipe choice = getRecipe(recipeNum - 1);
			System.out.println(choice.getDetails());
		}
		System.out.println("There is no recipe with that number");



	}


	/**
	 * Allows the user to enter a file name to add to the main file.
	 * @param reader The name of the scanner object.
	 */
	public void thirdOption(Scanner reader) {
		reader.nextLine();
		System.out.println("Please enter the name of file to add:");
		String fileName = reader.nextLine();


		File newfile = new File(fileName);
		Recipe object = null;
		try {
			Scanner file = new Scanner(newfile);
			String instance = file.nextLine();
			// split the file line into an array of the parameters required
			String[] details = instance.split("\\|");
			String title = details[0];
			String author = details[1];
			String description = details[2];
			int preptime = Integer.parseInt(details[3]);
			int cookingTime = Integer.parseInt(details[4]);

			// splitting the last elements into separate arrays
			String [] ingredients = details[5].split("@");
			String[] steps = details[6].split("@");

			// inserting all the parameters to construct the object
			object = new Recipe(title, author, description, preptime, cookingTime, ingredients, steps);


		}
		catch (FileNotFoundException e) {
			System.out.println(e);
		}

//		System.out.println("At what position in the book? -1 for last");
//		int positionInBook = reader.nextInt();
//		if (positionInBook == -1) {
//			addLast(object);
//		}
//		else if (positionInBook >= 0) {
//			insert(positionInBook, object);
//		}


	}


	/**
	 * Allows for a recipe name to be entered and removed if found.
	 * @param reader The name of the scanner object.
	 */
	public void fourthOption(Scanner reader) {
		reader.nextLine();
		System.out.println("Input the recipe name:");
		String name = reader.nextLine();
		int recipeIndex = findRecipeTitle(name);


		if (recipeIndex >= 0) {
			removeAt(recipeIndex);
			System.out.println(name + " " + "was removed from the recipe book");
		}
		else {
			System.out.println("That title was not found");
		}
	}

	/**
	 * Shows the recipes that contain a certain ingredient.
	 * @param reader The name of the scanner object.
	 */
	public void fifthOption(Scanner reader) {
		reader.nextLine();
		System.out.println("What ingredient are you looking for?");
		String ingredient = reader.nextLine();
		System.out.println("These recipes use" + " "+ ingredient + ": ");
		int currentIndex = findIngredient(ingredient);
		boolean noError = false;
		while (currentIndex >= 0) {
			System.out.println((currentIndex + 1) + ". " + getRecipe(currentIndex).toString());
			currentIndex = findIngredient(ingredient, currentIndex + 1);
			noError = true;
		}
		if (!noError) {
			System.out.println("No recipes use that ingredient ");
		}

	}


	/**
	 * Allows the user to save the updated recipes to a new file.
	 * @param reader The name of the scanner object.
	 */
	public void sixthOption(Scanner reader) {
		reader.nextLine();
		System.out.println("Please enter the name of the file");
		String newName = reader.nextLine();
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(newName);
			writer.println(mCount);

		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < getRecipeCount(); i++) {
			String adding = "";
			Recipe singleRecipe = getRecipe(i);
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

//	public double seventhOption() {
//
//	}





}
