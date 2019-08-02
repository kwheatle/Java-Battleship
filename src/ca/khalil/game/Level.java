package ca.khalil.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Level Class<br>
 * 
 * @Description Holds the ships for the Player and the Bot<br>
 * @Date Dec 2018 - Jan 2019
 * 
 * 
 * @author Khalil Wheatle
 * 
 * @see {@link #Level(int, String) Level Constructor}
 *
 */
public class Level implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 107116071981070377L;
	public String[][] field; //Field for the computer to use
	public String name = "";
	public int[][] hitMap; //Field that the player sees

	// I'm too lazy to type out the entire array so .split is easier
	public String[] Top = " <A<B<C<D<E<F<G<H<I<J<K<L<M<N<O<P<Q<R<S<T<U<V<W<X<Y<Z".split("<");
	public String[] Left = "   <1  <2  <3  <4  <5  <6  <7  <8  <9  <10 <11 <12 <13 <14 <15 <16 <17 <18 <19 <20 <21 <22 <23 <24 <25 <26 ".split("<");

	public List<Ship> ships = new ArrayList<Ship>();

	/**
	 * 
	 * Level Constructor
	 * 
	 * @param size - The size of the level. Max of 26 (16 is recommended)
	 * @param name - The display name of the level
	 */
	public Level(int size, String name) {
		this.name = name;
		//Setting array sizes
		field = new String[size][size];
		hitMap = new int[size][size];
	}

	/**
	 * Creates the level
	 * 
	 */
	public void createLevel() {
		for (int r = 0; r < field.length; r++) {
			for (int c = 0; c < field[r].length; c++) {
				field[0][c] = Top[c]; //Sets the top of the array to the matching letter in the alphabet
				field[r][c] = "0";
			}
		}
		for (int r = 0; r < field.length; r++) {
			field[r][0] = Left[r]; //Sets the Left Side of the array to the matching number of its row (using array to get the proper spacing)
		}

		for (int r = 0; r < hitMap.length; r++) {
			for (int c = 0; c < hitMap[r].length; c++) {
				hitMap[r][c] = Game.NONE; //Sets the hitmap to the proper state (0)
			}
		}
	}


}
