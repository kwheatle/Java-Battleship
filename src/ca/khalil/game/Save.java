package ca.khalil.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class Save implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 321238368638507044L;
	private transient Game g;
	/**
	 * Stream to open file
	 */
	private FileOutputStream FOS;
	
	/**
	 * For reading streams of raw data
	 */
	private FileInputStream FIS;

	/**
	 * Stream for writing object data to file
	 */
	private ObjectOutput OO;
	/**
	 * Stream for deserializing and reading object data from file
	 */
	private ObjectInputStream OIS;

	/**
	 * 
	 * Save constructor
	 * 
	 * @param filename
	 *            - Name of the file to save to/from. '.txt' ending is optional
	 *            (note: file will be saved as a txt regardless)
	 * @param g
	 *            - The game we are saving
	 * 
	 *
	 */
	public Save() {
		this.g = Main.g;
	}

	/**
	 * 
	 * Write game data to save file
	 * 
	 */
	public void writeSave(String filename) {
		try {

			// Opening stream to file and handling extension
			if (filename.contains(".save")) {
				FOS = new FileOutputStream(filename);
			} else {
				FOS = new FileOutputStream(filename + ".save");
			}
			OO = new ObjectOutputStream(FOS);

			// Saving variables
			OO.writeInt(Main.SIZE);
			OO.writeInt(g.WIDTH);
			OO.writeInt(g.HEIGHT);
			OO.writeDouble(g.factor);
			OO.writeInt(g.field);
			OO.writeInt(g.WINNER);
			OO.writeObject(g.bot);
			OO.writeObject(Main.enemyField);
			OO.writeObject(Main.playerField);

			OO.flush();
			OO.close();
			//System.out.println("SAVE WRITTEN");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read and set game data from save file
	 * 
	 */
	public boolean readSave(String filename) {
		try {

			// Opening stream to file and handling extension
			if (filename.contains(".save")) {
				FIS = new FileInputStream(filename);
			} else {
				FIS = new FileInputStream(filename + ".save");
			}
			OIS = new ObjectInputStream(FIS);

			// Reading and setting Variables
			Main.SIZE = OIS.readInt();
			g.WIDTH = OIS.readInt();
			g.HEIGHT = OIS.readInt();
			g.factor = OIS.readDouble();
			g.field = OIS.readInt();
			g.WINNER = OIS.readInt();
			g.bot = (Bot) OIS.readObject();
			Main.enemyField = (Level) OIS.readObject();
			Main.playerField = (Level) OIS.readObject();

			OIS.close();

			//Set the frame to the proper size
			g.frame.setSize(g.WIDTH, g.HEIGHT);

			g.repaint();

			//System.out.println("SAVE LOADED");

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void readSaveList() {
		// Opening stream to file and handling extension
		try {

			//Find the save file
			FIS = new FileInputStream("Saves.slst");
			OIS = new ObjectInputStream(FIS);

			//Read and save the info
			SaveWindow.saveList = (List<String>) OIS.readObject();

			OIS.close();

		} catch (Exception e) {

		}

	}

	public void writeSaveList() {
		try {
			 //Find the save file
			FOS = new FileOutputStream("Saves.slst");
			OO = new ObjectOutputStream(FOS);

			//Write to the file
			OO.writeObject(SaveWindow.saveList); 

			OO.flush();
			OO.close();

		} catch (Exception e) {

		}
	}

}
