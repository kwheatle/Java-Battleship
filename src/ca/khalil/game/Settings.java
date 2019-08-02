package ca.khalil.game;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Settings {

	private JFrame frmSettings;
	static JCheckBox chckbxFirstMove;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings window = new Settings();
					window.frmSettings.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Settings() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSettings = new JFrame();
		frmSettings.setTitle("Settings");
		frmSettings.setBounds(100, 100, 278, 165);
		frmSettings.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSettings.getContentPane().setLayout(null);
		
		chckbxFirstMove = new JCheckBox("Bot Moves First");
		chckbxFirstMove.setBounds(0, 7, 112, 23);
		chckbxFirstMove.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Game.botFirst = chckbxFirstMove.isSelected(); //Sets whether or no tthe bot will go first
			}
		});
		frmSettings.getContentPane().add(chckbxFirstMove);
		
		JLabel lblSize = new JLabel("16");
		lblSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblSize.setBounds(210, 49, 46, 14);
		frmSettings.getContentPane().add(lblSize);
		
		JSlider sldrSize = new JSlider();
		sldrSize.setMaximum(26);
		sldrSize.setMinimum(10);
		sldrSize.setValue(16);
		sldrSize.setBounds(0, 60, 200, 26);
		sldrSize.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblSize.setText(""+sldrSize.getValue()); //Allows you to see the slider value
				Main.SIZE = sldrSize.getValue(); //Sets the Grid Size
			}
			
		});
		frmSettings.getContentPane().add(sldrSize);
		
		JLabel lblGridSize = new JLabel("Grid Size");
		lblGridSize.setHorizontalAlignment(SwingConstants.CENTER);
		lblGridSize.setBounds(0, 37, 200, 14);
		frmSettings.getContentPane().add(lblGridSize);
		
		
	}
}
