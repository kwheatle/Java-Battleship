package ca.khalil.game;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * Game Class<br>
 * 
 * @Description Handles all the Game functions and Drawing. Creates the Player
 *              and the Bot <br>
 * @Date Dec 2018 - Jan 2019 <br>
 * @Version 3.0 <br>
 *
 *
 * @author Khalil Wheatle
 */
public class Game extends Canvas implements Runnable, MouseListener, KeyListener, Serializable {

	private static final long serialVersionUID = -4767217677840229122L;
	public final int PLAYER = 0, BOT = 1;
	public int field = BOT, WINNER = -1;
	
	JFrame frame = new JFrame("BattleShip | Enemy's Field");
	private JButton switchField = new JButton("Switch Viewed Field");
	private int shotX, shotY;
	
	public static boolean botFirst = false;//Bot move first variable
	public Bot bot = new Bot(Main.playerField, botFirst);

	//Setting window border size
	double factor = 0.9 * Main.SIZE;
	int WIDTH = (int) ((Main.SIZE * 30 + factor) + 60);
	int HEIGHT = (int) ((Main.SIZE * 30 + factor * 3) + 60);

	public Game() {
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setLocation(500, 100);
		frame.setResizable(false); // I set it to true because it doesn't make a difference to the gameplay
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.addMouseListener(this);
		frame.addKeyListener(this);
		addMouseListener(this);
		addKeyListener(this);
		frame.add(this, BorderLayout.CENTER);

		frame.add(switchField, BorderLayout.SOUTH);
		switchField.setFocusable(false);

		switchField.addActionListener(new ActionListener() {//When the button is clicked

			@Override
			public void actionPerformed(ActionEvent e) {
				field ^= 1; // Flips the field between 1 (BOT) and 0 (Player)
				frame.setTitle("BattleShip by Khalil | " + currField().name + "'s field");
				repaint(); // redraw the field (to see the change)
			}
		});

		frame.setVisible(true); // ^ frame stuff
		frame.requestFocus();
	}

	public Level currField() {//Returns the level you're looking at
		if (field == BOT)
			return Main.enemyField;
		if (field == PLAYER)
			return Main.playerField;
		else // Should never happen
			return null;
	}

	public void run() {

		while (WINNER == -1) {//WHile the game hasn't ended
			int enemySunk = 0, playerSunk = 0;

			for (Ship s : Main.enemyField.ships) {
				s.tick();
				if (s.isSunk) {
					enemySunk++;
				}

				if (enemySunk >= Main.enemyField.ships.size()) { // If all the enemy ships are sunk
					WINNER = PLAYER;
				}
			}

			for (Ship s : Main.playerField.ships) {
				s.tick();
				if (s.isSunk) {
					playerSunk++;
				}

				if (playerSunk >= Main.playerField.ships.size()) { // If all the player ships are sunk
					WINNER = BOT;
				}
			}

			if (bot.isTurn) {
				try {
					bot.tick();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!bot.isTurn)
					repaint();//Repaint the field at the end of the turn
			}
		}
	}

	public void start() {
		Thread game = new Thread(this, "game");
		game.start();
	}

	public void printLevel(Level l) {
		System.out.println(Arrays.deepToString(l.field).replace("[[", " ").replace("]]", "").replace("],", "\n")
				.replace("[", "").replace("  ,", "   "));
	}

	public void init() {
		for (int i = 0; i < 2; i++) { // Places the ships on their levels
			field = i;
			new Ship(6, 1, currField()).placeShip();
			new Ship(1, 2, currField()).placeShip();
			new Ship(5, 1, currField()).placeShip();
			new Ship(1, 3, currField()).placeShip();
		}

		frame.requestFocus();
	}

	/**
	 * 
	 * Calls the extended {@link #shoot(int, int, Level) shoot} function
	 * 
	 * @param a
	 *            - the coords you want to shoot at as an array {x, y}
	 * @param Level
	 *            - The level you want to shoot in
	 * @return Returns {@link #shoot(int, int, Level)}
	 */
	public static boolean shoot(int[] a, Level Level) {
		return shoot(a[0], a[1], Level);
	}

	/**
	 * @param x
	 *            - the X-coord for shooting
	 * @param y
	 *            - the Y-coord for shooting
	 * @param Level
	 *            - the Level in which you'll be shooting
	 * @return boolean value of whether the shot hit
	 */
	public static boolean shoot(int x, int y, Level Level) {
		boolean isHit = false;
		String shotCoords = x + "-" + y; //Converting to co-ord format used in the ship class
		for (Ship s : Level.ships) {
			for (String c : s.shipCoords) {
				if (shotCoords.equals(c)) { //If we shot at a coordinate that a ship lies on
					//s.tick();
					isHit = true;
					break;
				}
			}
			if (isHit)
				break;
		}
		if (isHit) {
			if (Level.hitMap[y][x] != SUNK) {
				Level.hitMap[y][x] = HIT;
			}
		} else {
			Level.hitMap[y][x] = MISS;
		}

		return isHit;
	}

	public static final int NONE = 0, HIT = 1, SUNK = 2, MISS = 3;

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		for (int i = 1; i < Main.SIZE + 1; i++) {
			for (int j = 1; j < Main.SIZE + 1; j++) {

				if (currField().hitMap[j][i] == NONE) {
					g.setColor(Color.black);
					g.drawRect(i * 30, j * 30, 30, 30);
				} else if (currField().hitMap[j][i] == MISS) {
					g.setColor(Color.BLUE);
					g.fillRect(i * 30, j * 30, 30, 30);
					g.setColor(Color.BLACK);
					g.drawString(" Miss", i * 30, j * 30 + 20);
				}

				if (field == PLAYER) {
					g.setColor(Color.red);
					g.drawString("Switch fields in order to shoot", WIDTH / 2 - 80, HEIGHT - 60);
					if (Main.playerField.field[j][i].equals("1")) {//If part of your ship is at this position
						g.setColor(Color.GREEN);
						g.fillRect(i * 30, j * 30, 30, 30);
						g.setColor(Color.BLACK);
						g.drawString("Ship", i * 30, j * 30 + 20);
					}
				}

				if (currField().hitMap[j][i] == HIT) {
					g.setColor(Color.YELLOW);
					g.fillRect(i * 30, j * 30, 30, 30);
					g.setColor(Color.BLACK);
					g.drawString(" Hit!", i * 30, j * 30 + 20);
				} else if (currField().hitMap[j][i] == SUNK) {
					g.setColor(Color.RED);
					g.fillRect(i * 30, j * 30, 30, 30);
					g.setColor(Color.BLACK);
					g.drawString("Sunk", i * 30, j * 30 + 20);
				}

			}
			g.setColor(Color.BLACK);
			g.drawString(currField().Top[i], i * 30 + 15, 20); //Drawing letter at the top
			g.drawString("" + i, 15, i * 30 + 15); //Drawing number along the side

		}
		g2.setStroke(new BasicStroke(10));//Makes brush wider
		
		g2.setFont(new Font("TimesRoman", Font.BOLD, 40));
		g2.setColor(Color.GREEN);
		if (WINNER == PLAYER) {
			g2.drawString("YOU WIN!", WIDTH / 2 - 80, HEIGHT / 2);
		} else if (WINNER == BOT) {
			g2.drawString("YOU LOSE :(", WIDTH / 2 - 80, HEIGHT / 2);
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		shotX = ((arg0.getX() / 30) % (Main.SIZE + 1));//Getting the x pos of the mouse % 30 (box size) to get the box you're hovering over
		shotY = ((arg0.getY() / 30) % (Main.SIZE + 1));// same thing for the y-pos

		if (shotX > 0 && shotY > 0 && field == BOT && WINNER == -1 && !bot.isTurn) {
			shoot(shotX, shotY, currField());
			bot.isTurn = true;
			repaint();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int keycode = e.getKeyCode();

		if (keycode == KeyEvent.VK_SPACE && field == BOT && WINNER == -1 && !bot.isTurn) {
			shotX = (int) ((getMousePosition().getX() / 30) % (Main.SIZE + 1));
			shotY = (int) ((getMousePosition().getY() / 30) % (Main.SIZE + 1));

			if (shotX > 0 && shotY > 0) {
				shoot(shotX, shotY, currField());
				bot.isTurn = true;
				repaint();
			}
		}
				
		if(keycode == KeyEvent.VK_S) {
			SaveWindow.main(null); //open the save window
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
