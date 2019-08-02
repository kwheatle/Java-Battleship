package ca.khalil.game;

/**
 * Main class<br>
 * 
 * @Description Creates the levels for the Player and Bot and starts the
 *              {@link Game}
 * @Date Dec 2018 - Jan 2019
 * 
 * @author Khalil Wheatle
 *
 */
public class Main {
	public static int SIZE = 16;
	public static Level enemyField, playerField;
	static Game g;

	public static void main(String[] args) {
		
		//Initializing Levels
		enemyField = new Level(Main.SIZE + 1, "Enemy");
		playerField = new Level(Main.SIZE + 1, "Player");

		//Creating levels
		enemyField.createLevel();
		playerField.createLevel();

		
		//Starting game
		g = new Game();
		g.init();
		g.start();
	}
	
	

	/**
	 * Recursive Binary Search
	 * 
	 * @param array
	 *            - {@link String} array to search in
	 * @param search
	 *            - {@link String} to search for
	 * @param low
	 *            - Lowest index (Put 0)
	 * @param high
	 *            - Highest index (Put array.length - 1)
	 * @return Index of the string (0-indexed), or -1 if the string wasn't found
	 */
	public static int BinarySearch(String[] array, String search, int low, int high) {

		if (high >= low) {
			int mid = low + ((high - low) / 2);

			if (array[mid].compareTo(search) == 0) { //If search == mid
				return mid; //Return mid
			}

			if (array[mid].compareTo(search) > 0) { //If the search is lower than the middle
				return BinarySearch(array, search, low, mid - 1); //High = the mid - 1
			}

			if (array[mid].compareTo(search) < 0) { //If the search is higher than the middle
				return BinarySearch(array, search, mid + 1, high); //Low = the mid + 1
			}

		} // End if high>=low

		return -1; //If not found
	}// End BinarySearch

}
