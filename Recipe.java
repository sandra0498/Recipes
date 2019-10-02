package NewRecipe;

/**
 * Represents various recipes that are taken from a text file.
 * Responsible for taking the different information of the recipe(s)
 * and formatting them for later use.
 */
public class Recipe {

	private String mTitle;
	private String mAuthor;
	private int mPrepTime;
	private int mCookingTime;
	private String[] mIngredients;
	private String [] mSteps;
	private String mDescription;
	/**
	 * Constructs a recipe with a title, author, prep time/cook time,etc.
	 * @param title The title of the recipe.
	 * @param author  The author of the recipe.
	 * @param prepTime  The prep time of the recipe.
	 * @param cookingTime The cooking time of the recipe.
	 * @param ingredients The array of the ingredients of the recipe.
	 * @param steps  The array of the steps of the recipe.
	 */

	public Recipe(String title, String author, String description, int prepTime, int cookingTime,
				  String[]ingredients, String[] steps) {
		mTitle = title;
		mAuthor = author;
		mDescription = description;
		mPrepTime = prepTime;
		mCookingTime = cookingTime;
		mIngredients = ingredients;
		mSteps = steps;

	}

	/**
	 * Gets the title of the recipe.
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * Gets the author of the recipe.
	 */
	public String getAuthor() {
		return mAuthor;
	}

	/**
	 * Gets the prep time of the recipe.
	 */
	public int getPrepTime() {
		return mPrepTime;
	}


	/**
	 *Gets the cooking time of the recipe.
	 */
	public int getCookingTime() {
		return mCookingTime;
	}


	public String getDescription() {
		return mDescription;
	}

	/**
	 * Gets the array of the ingredients.
	 */
	public String[] getIngredients() {
		return mIngredients;
	}

	/**
	 * Gets the array of steps.
	 */
	public String[] getSteps() {
		return mSteps;
	}

	/**
	 * @return A string detailing each recipe in a specific format.
	 */
	public String getDetails() {
		String temp =  mTitle + "\n" + "by "+ mAuthor + "\n" + mPrepTime;
		String time = " minute";
		if (mPrepTime > 1) {
			time += "s";
		}
		String timeCooking = " minute";
		if (mCookingTime > 1) {
			timeCooking += "s";
		}

		temp += time + " prep time," + " "+ mCookingTime + timeCooking + " cook time" +
				"\n" + "Ingredients:" + "\n";
		// use a for loop to iterate all the elements
		for (int i = 0; i < mIngredients.length; i++) {
			temp += (i + 1) ;
			temp += ". ";
			temp += mIngredients[i];
			temp += "\n";
		}
		temp += "Steps: \n";
		// use another for loop for the steps array
		for (int i = 0; i < mSteps.length; i++){
			temp += (i + 1) ;
			temp += ". ";
			temp += mSteps[i];
			temp += "\n";
		}
		return temp;
	}

	/**
	 * @return The sum of the cooking time and the prep time.
	 */
	public int getTotalTime() {
		int totalTime = mCookingTime + mPrepTime;
		return totalTime;
	}

	/**
	 * Allows the user to mutate the author inputted.
	 */
	public void setAuthor(String author){
		mAuthor = author;
	}

	/**
	 * Allows the user to mutate the title of the recipe.
	 */
	public void setTitle(String title) {
		mTitle = title;
	}

	/**
	 * @return The length of the steps array.
	 */
	public int getNumberOfSteps() {
		int NumOfSteps = mSteps.length ;
		return NumOfSteps;
	}

	/**
	 * @return A shopping list showcasing all the ingredients of the recipe.
	 */
	public String getShoppingList() {
		String shopList = "";
		for (int i = 0; i < mIngredients.length; i++) {
			shopList += (i + 1);
			shopList += " ";
			shopList += mIngredients[i];
			shopList += "\n";
		}
		return shopList;
	}

	/**
	 * @return The string of the title, description, and the total time of the recipe.
	 * The formatting is separated by a "-".
	 */

	public String toString() {
		String mins = "minute";
		if (getTotalTime() > 1) {
			mins += "s";
		}
		String newStr = mTitle + " - "+ mDescription +" - "+ getTotalTime() + " "+ mins;
		return  newStr;
	}

	/**
	 * @param ingredient String that the user inputs asking for a specific ingredient.
	 * @return True if the string elements in the Recipe array contain the substring.
	 */
	public boolean usesIngredient(String ingredient) {
		boolean checkIngredient = false;

		for(String i : mIngredients) {
			if (i.contains(ingredient) || i.contains(ingredient.toLowerCase())) {
				checkIngredient = true;
			}
		}
		return checkIngredient;
	}


	}
