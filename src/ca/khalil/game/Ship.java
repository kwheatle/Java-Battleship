package ca.khalil.game;

import java.io.Serializable;
import java.util.Random;

/**
 * Ship Class <br>
 * 
 * @Description Handles the creating, placing, etc. of the ships<br>
 * @Date Dec 2018 - Jan 2019
 * 
 * @author Khalil Wheatle
 * @see {@link #Ship(int, int, Level) Ship Constructor}
 */
public class Ship implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7734425753264389255L;

	private Random rand = new Random();

	
	int width, height, x, y; //Ship bounds data
	boolean isSunk;
	Level level; //Level the ship exists on
	
	/**
	 * Array for holding all the ship's coordinates
	 */
	public String[] shipCoords;

	/**
	 * 
	 * @param width
	 *            - The width of the ship
	 * @param height
	 *            - The height of the ship
	 * @param level
	 *            - The {@link Level} in which the ship will be placed
	 */
	public Ship(int width, int height, Level level) {
		this.width = width;
		this.height = height;

		shipCoords = new String[width * height];
		this.level = level;
		level.ships.add(this);

	}

	/**
	 * 
	 * Gets the ship that exists and the x and y coords
	 * 
	 * @param x
	 *            - x position
	 * @param y
	 *            - y position
	 * @return Returns this ship if part of it exists at the x and y coords. Else it
	 *         returns null
	 */
	public Ship getShip(int x, int y) {
		String pos = x + "-" + y;
		if (find(shipCoords, pos) >= 0) { //If the pos is found in shipCoords
			return this;
		}
		//else
		return null;
	}

	/**
	 * Places the ship in the {@link Level} specified in the
	 * {@link #Ship(int, int, Level) Constructor}
	 * 
	 * @see {@link #Ship(int, int, Level)}
	 */
	public void placeShip() {
		boolean isValid = true;
		do {
			int i = 0;

			//Setting random starting pos
			x = rand.nextInt(Main.SIZE - width) + 1;
			y = rand.nextInt(Main.SIZE - height) + 1;

			for (int x1 = x; x1 < x + width; x1++) { // Setting Ship Coords
				for (int y1 = y; y1 < y + height; y1++) {
					this.shipCoords[i] = x1 + "-" + y1;
					i++;
				}
			}

			if (level.ships.isEmpty()) //If no other ships exist
				break;

			for (Ship s : level.ships) { //For every ship in this ships level
				if (s == this) // ignore the ship thats running this loop
					continue;
				for (int w = 0; w < this.shipCoords.length; w++) { //For every coord that the ship covers
					isValid = find(s.shipCoords, this.shipCoords[w]) < 0; // isVaild = false if value returned is > -1;
																			// Checking for coord overlap
					if (!isValid)
						break;
				}//end for w
				if (!isValid)
					break;
			}//end for Ship s

		} while (!isValid); //Loop until there are no overlaps

		for (int x1 = x; x1 < x + width; x1++) { // Setting Ship Coords
			for (int y1 = y; y1 < y + height; y1++) {
				level.field[y1][x1] = "1";
			}
		}

	}

	/**
	 * @param coord
	 *            - coordinates ("x-y") to check
	 * @return boolean value if that part of the ship has been hit
	 */
	protected boolean CoordsToLevel(String[] coord) {
		return level.hitMap[Integer.parseInt(coord[1])][Integer.parseInt(coord[0])] == 1;
	}

	/**
	 * checks that part of the ship has been hit based off the coord index in
	 * {@link Ship#shipCoords}
	 * 
	 * @param ArrayIndex
	 *            - The index to check
	 * @return Boolean values if that part of the ship has been hit
	 */
	protected boolean checkCoords(int ArrayIndex) {
		String[] coord = shipCoords[ArrayIndex].split("-");
		return CoordsToLevel(coord);
	}

	/**
	 * Checks if this ship is sunk. Also changes the {@code isSunk} value in-place
	 * 
	 * @return Boolean isSunk
	 */
	public boolean checkSunk() {
		boolean currStatus = true;
		for (int i = 0; i < width * height; i++) {
			if (!currStatus) //If at any point the current status is false, exit
				break;
			currStatus = currStatus && checkCoords(i); //using boolean logic to see if any part of the ship is sunk
		}
		this.isSunk = currStatus;
		return this.isSunk;
	}

	/**
	 * 
	 * Updates the level tile to reflect whether or not the ship is sunk
	 * 
	 */
	public void updateShip() {
		for (int x1 = x; x1 < x + width; x1++) { // Setting Ship Coords
			for (int y1 = y; y1 < y + height; y1++) {
				level.field[y1][x1] = "0";
				level.hitMap[y1][x1] = Game.SUNK; //Sets hitmap to correct state (2)
			}
		}
	}
	
	/**
	 * 
	 * Checks if ship is sunk and handles the changing of values after every turn
	 * 
	 */
	public void tick() {
		if (!isSunk)
			checkSunk();
		
		if (this.isSunk) {
			updateShip();
		}
	}

	static boolean swapped = false;

	/**
	 * takes two array indexes and swaps the values of them in-place
	 * 
	 * @param a
	 *            - first index
	 * @param b
	 *            - second index
	 * 
	 * @see {@link Ship#shipCoords}
	 */
	public void swap(int a, int b) {
		String temp = shipCoords[a];
		shipCoords[a] = shipCoords[b];
		shipCoords[b] = temp;
		swapped = true;
	}

	/**
	 * in-place bubble sort into ascending order for {@link #shipCoords}
	 */
	public void Sort_Ascending_Bubble() {
		swapped = true;
		while (swapped) { // While swaps are still happening
			swapped = false;
			for (int i = 0; i < shipCoords.length - 1; i++) {
				int n = i + 1;
				if (shipCoords[i].compareTo(shipCoords[n]) > 0) { //if this index is bigger then the next one
					swap(i, n); // swap them
				}
			}
		}

	}

	/**
	 * 
	 * In-place {@link String} Binary Search
	 * 
	 * @param array
	 *            - String[] that you will be searching in
	 * @param target
	 *            - The String you're looking for
	 * @return The index of the String
	 * 
	 * @see {@link Main#BinarySearch(String[], String, int, int)}
	 */
	private int find(String[] array, String target) {
		Sort_Ascending_Bubble();
		return Main.BinarySearch(array, target, 0, array.length - 1);
	}

}
