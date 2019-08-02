package ca.khalil.game;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SaveWindow {

	/**
	 * Save Object
	 */
	private Save s = new Save();
	private JFrame frmSaveWindow;
	
	/**
	 * List of save names
	 */
	public static List<String> saveList = new ArrayList<String>();
	private JComboBox<String> cmboSave = new JComboBox<String>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaveWindow window = new SaveWindow();
					window.frmSaveWindow.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SaveWindow() {
		s.readSaveList(); //Read the .slst file for the saves
		saveList.remove((Object) "SeanGay");
		cmboSave = new JComboBox<String>(saveList.toArray(new String[0])); //Give the combo box the list of saves
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSaveWindow = new JFrame();
		frmSaveWindow.setTitle("Save Window");
		frmSaveWindow.setBounds(100, 100, 226, 160);
		frmSaveWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmSaveWindow.getContentPane().setLayout(null);

		JLabel lblSaveFileName = new JLabel("Save File Name");
		lblSaveFileName.setHorizontalAlignment(SwingConstants.CENTER);
		lblSaveFileName.setBounds(0, 11, 210, 14);
		frmSaveWindow.getContentPane().add(lblSaveFileName);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String item = (String) cmboSave.getEditor().getItem(); // Get the current item

				if (!saveList.contains(item)) { // IF the item doesn't exist in the list
					saveList.add(item); // Add it
					s.writeSaveList(); // Write it to file for permanent memory
				}

				s.writeSave(item); // Write the save to a file
				cmboSave.addItem(item); // Write it to combo box for short term memory
			}
		});
		btnSave.setBounds(10, 86, 89, 23);
		frmSaveWindow.getContentPane().add(btnSave);

		JButton btnLoad = new JButton("Load");
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String item = (String) cmboSave.getEditor().getItem(); // Get the current item

				boolean read = s.readSave(item); // Read the save
				
				if (!saveList.contains(item) && read) { // IF the item doesn't exist in the list and There is actually a save under that name
					saveList.add(item); // Add it
					s.writeSaveList(); // Write it to file for permanent memory
					cmboSave.addItem(item); // Write it to combo box for short term memory
				}
				
				if(saveList.contains(item) && !read) { // If the item exists in the list but there is no save under that name
					saveList.remove(item); //Remove it
					cmboSave.removeItem(item); //Delete it from combo box
					s.writeSaveList(); //Delete it from file by not writing it
				}
				
				
			}
		});
		btnLoad.setBounds(116, 86, 89, 23);
		frmSaveWindow.getContentPane().add(btnLoad);

		cmboSave.setBounds(65, 36, 86, 20);
		cmboSave.setEditable(true);
		frmSaveWindow.getContentPane().add(cmboSave);

	}
}
