package NewRecipe;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Responsible for the creation of a binary search tree
 * that consists of the recipes taken from the file.
 */
public class RecipeSet {
	private class Node {
		private Recipe mData;
		private Node mLeft, mRight;
		private Node mParent;

		/**
		 * Allows for the creation of a node.
		 * @param newData The recipe from the file.
		 */
		public Node(Recipe newData) {
			mData = newData;
		}

		/**
		 * Sets the left child of the previous node.
		 * @param n The node that is going to be set left
		 *          of the previous node.
		 */
		public void setLeft(Node n) {
			mLeft = n;
		}

		/**
		 * Sets the right child of the previous node.
		 * @param r The node that is going to be set right
		 *          of the previous node.
		 */
		public void setRight(Node r) {
			mRight = r;
		}

		/**
		 * Sets the parent to the previous node.
		 * @param p The node that will be considered
		 *          the new parent.
		 */
		public void setParent(Node p) {
			mParent = p;
		}
	}

	private int mCount;
	private Node mRoot;


	/**
	 *  The constructor opens the text file and
	 *   constructs the recipes contained within.
	 * @param recipeName The title of the recipe. If file is not found,
	 * then a FileNotFoundException is thrown.
	 */
	public RecipeSet(String recipeName) {
		File inputFile = new File(recipeName);

		try {
			Scanner file = new Scanner(inputFile);
			int firstLine = file.nextInt();
			double x = Math.log(firstLine);
			double y = Math.ceil(x);
			double realSize = Math.pow(2, (y + 1));
			int realSize1 = (int) realSize;

			file.nextLine();

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
				String[] ingredients = details[5].split("@");
				String[] steps = details[6].split("@");

				// inserting all the parameters to construct the object
				Recipe object = new Recipe(title, author, description, preptime,
						cookingTime, ingredients, steps);
				add(object);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}


	/**
	 * Gets the number of nodes in the tree.
	 */
	public int getCount() {
		return mCount;
	}

	/**
	 * Allows for the retrieval of a recipe, given the title.
	 * @param recipeName The title of the recipe.
	 * @return The recipe itself, given the title.
	 */
	public Recipe findRecipe(String recipeName) {
		Node possibleNode = find(recipeName, mRoot);
		if (possibleNode == null) {
			return null;
		}
		else {
			return possibleNode.mData;
		}
	}

	private Node find(String recipeName, Node n) {
		if (n == null) {
			return null;
		}
		//if string.compareTo(other string) == 0 , then
		// return string
		// if string.compareTO < 1 , then ...
		//base case
		if (n.mData.getTitle().compareTo(recipeName) == 0) {
			return n;
		}
		if (n.mData.getTitle().compareTo(recipeName) < 0) {
			return find(recipeName, n.mRight);
		}
		return find(recipeName, n.mLeft);

	}


	/**
	 * Responsible for adding the recipe to the tree.
	 * @param recipe The recipe that was just
	 *               passed from the constructor.
	 */
	public void add(Recipe recipe) {
		if (mRoot == null) {
			Node firstNode = new Node(recipe);
			mRoot = firstNode;
			mCount++;
		}
		else {
			add(recipe, mRoot);
		}

	}

	private void add(Recipe recipe, Node n) {
		// adds if the recipe title doesn't already exist

		if (!recipe.getTitle().equals(n.mData.getTitle())) {
			if (n.mData.getTitle().compareTo(recipe.getTitle()) < 0
					&& n.mRight == null) {
				Node newNode = new Node(recipe);
				n.setRight(newNode);
				newNode.setParent(n);
				mCount++;
			} else if (n.mData.getTitle().compareTo(recipe.getTitle()) > 0
					&& n.mLeft == null) {
				Node newNode = new Node(recipe);
				n.setLeft(newNode);
				newNode.setParent(n);
				mCount++;
			}
			// Enter the else if a node is in the way
			else {
				if (n.mData.getTitle().compareTo(recipe.getTitle()) < 0) {
					add(recipe, n.mRight);
				}
				else {
					add(recipe, n.mLeft);
				}
			}
		}
	}


	/**
	 * Gets the height of the tree.
	 */
	public int getHeight() {
		return getHeight(mRoot);
	}

	private int getHeight(Node n) {
		//helper method
		if (n == null) {
			return 0;
		}
		//counts the height of the subtrees
		int leftHeight = getHeight(n.mLeft);
		int rightHeight = getHeight(n.mRight);

		return Math.max(leftHeight, rightHeight) + 1;
	}

	/**
	 * Allows for the removal of a node within the tree.
	 * @param recipeName The title of the recipe.
	 */
	public void remove(String recipeName) {
		Node foundNode = find(recipeName, mRoot);
		Node leftChild = foundNode.mLeft;
		Node rightChild = foundNode.mRight;
		Node parent = foundNode.mParent;


		// case if node is a leaf
		if (leftChild == null && rightChild == null) {
			if (mRoot == foundNode) {
				mRoot = null;
			}
			else if (parent.mRight == foundNode) {
				parent.setRight(null);
			}
			else if (parent.mLeft == foundNode) {
				parent.setLeft(null);
			}
		}
		//case if n has exactly one child
		else if (leftChild == null && rightChild != null) {
			// am I the root?
			// am I the left or right of my parent?
			if (foundNode.equals(mRoot)) {
				mRoot = rightChild;

			}
			else if (parent.mRight == foundNode) {
				parent.setRight(rightChild);
				rightChild.setParent(parent);
			}
			else if (parent.mLeft == foundNode) {
				parent.setLeft(rightChild);
				rightChild.setParent(parent);
			}

		}
		else if (leftChild != null && rightChild == null) {
			if (foundNode.equals(mRoot)) {
				mRoot = leftChild;
			}
			else {
				parent.setLeft(leftChild);
			}
		}
		else {
			//case if node has two children

			Node greatest = findLargestNode(foundNode.mLeft);
			remove(greatest.mData.getTitle());
			foundNode.mData = greatest.mData;

		}
		mCount--;

	}


	private Node findLargestNode(Node n) {
		if (n.mRight == null) {
			return n;
		}
		return findLargestNode(n.mRight);
	}

	/**
	 * Responsible for finding the recipes that contain that ingredient.
	 * @param ingredient The ingredient the user is searching for.
	 * @return A recipe book that contains only the recipes
	 * that use that ingredient.
	 */
	public RecipeBook findIngredient(String ingredient) {
		RecipeBook book = new RecipeBook();

		return findIngredient(ingredient, mRoot, book);
	}

	private RecipeBook findIngredient(String ingredient, Node n, RecipeBook r) {
		//traverse through tree and add to recipe book
		// Check n for ingredient - if found, add to r
		//in-order traversal
		if (n.mLeft != null) {
			findIngredient(ingredient, n.mLeft, r);
		}

		if (n.mData.usesIngredient(ingredient)) {
			r.addLast(n.mData);
			System.out.println(n.mData.toString());
		}

		if (n.mRight != null) {
			// if r exists
			findIngredient(ingredient, n.mRight, r);
		}

		return r;
	}


	/**
	 * Gets all the recipes in the tree.
	 */
	public RecipeBook getAllRecipes() {
		//pre-order traversal
		RecipeBook book = new RecipeBook();
		return getAllRecipes(mRoot, book);
	}

	private RecipeBook getAllRecipes(Node n, RecipeBook r) {
		//print all recipes
		if (n != null) {
			System.out.println(n.mData.toString());
			r.addLast(n.mData);
			getAllRecipes(n.mLeft, r);
			getAllRecipes(n.mRight, r);
		}
		return r;
	}
}