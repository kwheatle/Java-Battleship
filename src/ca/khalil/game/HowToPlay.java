package ca.khalil.game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import java.awt.Color;

public class HowToPlay {

	private JFrame frmHowToPlay;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HowToPlay window = new HowToPlay();
					window.frmHowToPlay.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public HowToPlay() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHowToPlay = new JFrame();
		frmHowToPlay.setTitle("How To Play");
		frmHowToPlay.setBounds(100, 100, 450, 300);
		frmHowToPlay.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmHowToPlay.getContentPane().setLayout(null);
		
		JTextArea txtrInstructions = new JTextArea();
		txtrInstructions.setForeground(Color.ORANGE);
		txtrInstructions.setBackground(Color.BLACK);
		txtrInstructions.setWrapStyleWord(true);
		txtrInstructions.setLineWrap(true);
		txtrInstructions.setText("Click on a square or click the spacebar while hovering over it to shoot that square\r\n\r\nHit all parts of the ship to sink it\r\n\r\nSink all ships to win\r\n\r\nClick the button at the bottom to see your ship\r\n\r\nPress 'S' to open the save window\r\n\r\nIf the bot sinks all your ships you lose\r\n\r\n");
		txtrInstructions.setBounds(0, 0, 434, 261);
		frmHowToPlay.getContentPane().add(txtrInstructions);
	}
}
