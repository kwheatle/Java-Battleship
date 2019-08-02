package ca.khalil.game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/*
 * 
 * My own class
 * Deals with the Main Menu
 * 
 * */


public class Menu {

	private JFrame menu;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Menu window = new Menu();
					window.menu.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public Menu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		menu = new JFrame();
		menu.getContentPane().setBackground(Color.BLACK);
		menu.setTitle("Khalil's Summative | Menu");
		menu.setBounds(500, 100, 565, 541);
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.getContentPane().setLayout(null);

		JTextArea txtrMenu = new JTextArea();
		txtrMenu.setFont(new Font("Monospaced", Font.PLAIN, 26));
		txtrMenu.setText("> Play <\r\n\r\nHow To Play\r\n\r\nSettings\r\n\r\nExit");
		txtrMenu.setForeground(Color.ORANGE);
		txtrMenu.setEditable(false);
		txtrMenu.setFocusable(true);
		txtrMenu.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();

				if (key == KeyEvent.VK_UP || key == KeyEvent.VK_W) { //Cycling through menu selections
					if (txtrMenu.getText().contains("> Play")) {
						txtrMenu.setText("Play\r\n\r\nHow To Play\r\n\r\nSettings\r\n\r\n> Exit <");						
					} else if (txtrMenu.getText().contains("> How")) {
						txtrMenu.setText("> Play <\r\n\r\nHow To Play\r\n\r\nSettings\r\n\r\nExit");
					} else if (txtrMenu.getText().contains("> Settings")) {
						txtrMenu.setText("Play\r\n\r\n> How To Play <\r\n\r\nSettings\r\n\r\nExit");
					} else if (txtrMenu.getText().contains("> Exit")) {
						txtrMenu.setText("Play\r\n\r\nHow To Play\r\n\r\n> Settings <\r\n\r\nExit");
					}
				}//end of if
				
				if (key == KeyEvent.VK_DOWN || key == KeyEvent.VK_S) { //Cycling through menu selections
					if (txtrMenu.getText().contains("> Play")) {
						txtrMenu.setText("Play\r\n\r\n> How To Play <\r\n\r\nSettings\r\n\r\nExit");
					} else if (txtrMenu.getText().contains("> How")) {
						txtrMenu.setText("Play\r\n\r\nHow To Play\r\n\r\n> Settings <\r\n\r\nExit");
					} else if (txtrMenu.getText().contains("> Settings")) {
						txtrMenu.setText("Play\r\n\r\nHow To Play\r\n\r\nSettings\r\n\r\n> Exit <");
					} else if (txtrMenu.getText().contains("> Exit")) {
						txtrMenu.setText("> Play <\r\n\r\nHow To Play\r\n\r\nSettings\r\n\r\nExit");
					}
				}//end of if

				if (key == KeyEvent.VK_ENTER || key == KeyEvent.VK_SPACE) { //To select certain actions
					if (txtrMenu.getText().contains("> Play")) {
						Main.main(null); //Start main game
						menu.setVisible(false); //hide form
						menu.dispose(); //Close form to free memory
					} else if (txtrMenu.getText().contains("> How")) {
						HowToPlay.main(null); //Runs the "How To Play" menu
					} else if (txtrMenu.getText().contains("> Settings")) {
						Settings.main(null); //Run the settings window
					} else if (txtrMenu.getText().contains("> Exit")) {
						menu.dispatchEvent(new WindowEvent(menu, WindowEvent.WINDOW_CLOSING));//Close Frame and exit program | Simulates clicking the "close" button
					}
				}//end of if
			}//end of keyPressed
		});
		txtrMenu.setBackground(menu.getContentPane().getBackground());
		txtrMenu.setBounds(191, 130, 348, 264);
		menu.getContentPane().add(txtrMenu);
		
		JLabel lblLogo = new JLabel("BATTLESHIP");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setFont(new Font("Unispace", Font.BOLD, 40));
		lblLogo.setForeground(Color.ORANGE);
		lblLogo.setBounds(125, 23, 304, 92);
		menu.getContentPane().add(lblLogo);

	}//end of Initialize
}//end of class
