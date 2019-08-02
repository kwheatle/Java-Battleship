package ca.khalil.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * The Bot Class <br>
 * 
 * @Description Handles the A.I shooting and logic <br>
 * @Date Dec 2018 - Jan 2019
 * @author Khalil Wheatle
 * @see {@link #Bot(Level, boolean, Game) Bot Constructor}
 *
 */
public class Bot implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2668962691038499791L;
	private Random rand = new Random();
	private final int FIND = 0, HIT = 1, SINK = 2; //Naming States
	public int state = FIND;//Default state
	public boolean isTurn;
	private int x, y, hitX, hitY;//X and y variables
	private boolean isHit = false, wasHit = false; //Hit booleans
	Dir currDir;
	Ship currShip = null;
	private Level l;

	/**
	 * Direction Enum Up, Down, Left, or Right (1, 2, 3, and 4 respectively)
	 *
	 */
	public enum Dir {
		Up(1), Down(2), Left(3), Right(4);

		private final int value;

		private Dir(int v) {
			value = v;
		}

		/**
		 * 
		 * Gives the opposite direction of the one given
		 * 
		 * @param direction
		 *            - The original direction
		 * @return the opposite of the direction given as {@link Dir}
		 */
		static Dir getOpposite(Dir direction) {
			if (direction.value % 2 == 0) { //For the opposite of an even number go back else go forward
				return getDir(direction.value - 1);
			} else {
				return getDir(direction.value + 1);
			}
		}

		/**
		 * Get a direction from a value
		 * 
		 * @param value
		 *            - Value of the {@link Dir} you want
		 * @return the {@link Dir} with the assigned value given
		 */
		static Dir getDir(int value) {
			for (Dir e : Dir.values()) {
				if (e.value == value) {
					return e;
				}
			}
			return null;// not found
		}
	}

	private List<Dir> alreadyShot = new ArrayList<Dir>();

	/**
	 * 
	 * Bot Constructor
	 * 
	 * @param L
	 *            - The {@link Level} the bot will be shooting at
	 * @param isFirst
	 *            - Boolean value. Determines if the bot will move first or not
	 * @param g
	 *            - The {@link Game} the bot is operating in. Only used for
	 *            repainting the board
	 */
	public Bot(Level L, boolean isFirst) {
		this.isTurn = isFirst;
		this.l = L;
	}

	/**
	 * 
	 * Shoots in a random direction based off the x and y coords given
	 * 
	 * @param x
	 *            - x_pos of shot
	 * @param y
	 *            - y_pos of shot
	 * @return the next shoot position as array {x_pos, y_pos}
	 */
	public int[] randDir(int x, int y) {

		do {
			currDir = Dir.getDir(rand.nextInt(4) + 1); // gets direction from random num between 1 and 4 both inclusive
		} while (alreadyShot.contains(currDir) == true); //Makes sure it wont shoot in the same direction twice

		//Handling direction 
		if (currDir == Dir.Up) {
			y--;
		} else if (currDir == Dir.Down) {
			y++;
		} else if (currDir == Dir.Left) {
			x--;
		} else if (currDir == Dir.Right) {
			x++;
		}
		alreadyShot.add(currDir);
		return new int[] { x, y };//Returning new shot position
	}

	/**
	 * 
	 * Shoots in a specified direction based off the direction, x and y coords given
	 * 
	 * @param direction
	 *            - The direction you want to shoot in ({@link Dir})
	 * @param coords
	 *            - The array of {x_pos, y_pos} for initial coords
	 * @return the next shoot position as array {x_pos, y_pos}
	 */
	private int[] shootDir(Dir direction, int[] coords) {
		//getting coords
		x = coords[0];
		y = coords[1];
		//handling directions
		if (direction == Dir.Up) {
			y--;
		} else if (direction == Dir.Down) {
			y++;
		} else if (direction == Dir.Left) {
			x--;
		} else if (direction == Dir.Right) {
			x++;
		}

		return new int[] { x, y }; //Returning new shot position
	}

	int shot[] = new int[2];
	boolean isSunk = false, once = false;

	public void tick() throws InterruptedException {
		if (isTurn) {
			
			
			Thread.sleep(500);//Wait 0.5s
			Main.g.field = Main.g.PLAYER; //switch to the Player's field
			Main.g.repaint();
			Thread.sleep(500); //Wait 0.5s
	
			

			switch (state) {
			case FIND: // Find a ship

				x = rand.nextInt(Main.SIZE) + 1;
				y = rand.nextInt(Main.SIZE) + 1;

				isHit = Game.shoot(x, y, l);
				if (isHit) {
					for (Ship s : l.ships) {
						Ship temp = s.getShip(x, y);
						if (temp != null) {
							currShip = temp; // Save the ship that you hit
							break;
						}
					}
					state = HIT;
				}
				break;
			case HIT: // If we hit a ship
				//Saving first hit position
				hitX = x;
				hitY = y;

				shot = randDir(hitX, hitY);

				isHit = Game.shoot(shot, l);
				if (isHit) {
					for (Ship s : l.ships) {
						Ship temp = s.getShip(x, y);
						if (temp != null) { //Valid ship check
							if (temp.equals(currShip)) { // If we hit the same ship from before
								alreadyShot.clear();
								state = SINK; //Next state
							} else {
								System.out.println("Hit Other");
							}
							
							break;
						}
					}

				}
				break;
			case SINK: // Sink the ship
				if (!wasHit) {
					if (!once) { // Run this once | sets our position to the first place we hit
						shot[0] = hitX;
						shot[1] = hitY;
						once = true;
					}
					currDir = Dir.getOpposite(currDir); // Shoot the opposite direction
				}

				shot = shootDir(currDir, shot); // Setting up shot

				isHit = Game.shoot(shot, l); // shoot
								
				// Checking if we sunk the ship
				if (currShip.checkSunk()) {
					currShip.updateShip();
					isSunk = false;
					state = FIND; //Reset state
					once = false;
					currShip = null;
				}

				break;
			}

			wasHit = isHit;
			isHit = false;
			isTurn = false;
			
			
			Main.g.paint(Main.g.getGraphics()); //Forces a repaint 
			Thread.sleep(500); //wait 0.5s
			Main.g.field = Main.g.BOT; //Switch back to the bots field
		}
	}

}
