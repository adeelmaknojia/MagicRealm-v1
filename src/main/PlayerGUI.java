package main;
import java.awt.EventQueue;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JTextField;

import java.awt.Insets;

import javax.swing.JComboBox;

import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;

import client.MRClient;

public class PlayerGUI {

	private JFrame frame;
	private JTextField txtUnknown;
	private Image img = null;
	private JButton btnStartGame;
	private JComboBox comboBox_char;
	private JComboBox comboBox_location;
	private JLabel label;
	private Image tempImg;

	/**
	 * Create the application.
	 */
	
	public JFrame getFrame(){
		return this.frame;
	}
	public PlayerGUI() {

		frame = new JFrame();
		frame.setBounds(350, 10, 800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 36, 0, 0, 0, 0, 0, 0, 44, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		frame.setVisible(true);
		frame.setSize(600, 650);

		JLabel lblWelcomeToMagic = new JLabel("Welcome To Magic Realm");
		lblWelcomeToMagic.setFont(new Font("Times New Roman", Font.BOLD, 26));
		GridBagConstraints gbc_lblWelcomeToMagic = new GridBagConstraints();
		gbc_lblWelcomeToMagic.gridwidth = 2;
		gbc_lblWelcomeToMagic.insets = new Insets(0, 0, 5, 5);
		gbc_lblWelcomeToMagic.gridx = 1;
		gbc_lblWelcomeToMagic.gridy = 1;
		frame.getContentPane().add(lblWelcomeToMagic, gbc_lblWelcomeToMagic);

		JLabel lblPlayerName = new JLabel("Player Name");
		GridBagConstraints gbc_lblPlayerName = new GridBagConstraints();
		gbc_lblPlayerName.anchor = GridBagConstraints.EAST;
		gbc_lblPlayerName.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayerName.gridx = 1;
		gbc_lblPlayerName.gridy = 3;
		frame.getContentPane().add(lblPlayerName, gbc_lblPlayerName);

		txtUnknown = new JTextField();
		txtUnknown.setText("Unknown");
		GridBagConstraints gbc_txtUnknown = new GridBagConstraints();
		gbc_txtUnknown.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtUnknown.insets = new Insets(0, 0, 5, 5);
		gbc_txtUnknown.gridx = 2;
		gbc_txtUnknown.gridy = 3;
		frame.getContentPane().add(txtUnknown, gbc_txtUnknown);
		txtUnknown.setColumns(10);

		JLabel lblSelectCharacter = new JLabel("Select Character");
		GridBagConstraints gbc_lblSelectCharacter = new GridBagConstraints();
		gbc_lblSelectCharacter.anchor = GridBagConstraints.EAST;
		gbc_lblSelectCharacter.insets = new Insets(0, 0, 5, 5);
		gbc_lblSelectCharacter.gridx = 1;
		gbc_lblSelectCharacter.gridy = 4;
		frame.getContentPane().add(lblSelectCharacter, gbc_lblSelectCharacter);

		comboBox_char = new JComboBox(MRClient.availableChars);
		GridBagConstraints gbc_comboBox_char = new GridBagConstraints();
		gbc_comboBox_char.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_char.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_char.gridx = 2;
		gbc_comboBox_char.gridy = 4;
		frame.getContentPane().add(comboBox_char, gbc_comboBox_char);

		JLabel lblCharacterLocation = new JLabel("Character Location");
		GridBagConstraints gbc_lblCharacterLocation = new GridBagConstraints();
		gbc_lblCharacterLocation.anchor = GridBagConstraints.EAST;
		gbc_lblCharacterLocation.insets = new Insets(0, 0, 5, 5);
		gbc_lblCharacterLocation.gridx = 1;
		gbc_lblCharacterLocation.gridy = 5;
		frame.getContentPane().add(lblCharacterLocation, gbc_lblCharacterLocation);

		
		comboBox_location = new JComboBox(getCharLocation());
		GridBagConstraints gbc_comboBox_location = new GridBagConstraints();
		gbc_comboBox_location.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_location.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_location.gridx = 2;
		gbc_comboBox_location.gridy = 5;
		frame.getContentPane().add(comboBox_location, gbc_comboBox_location);


		label = new JLabel("");
		tempImg = getCharImage("Amazon");
		Image newimg = tempImg.getScaledInstance( 500, 400,  java.awt.Image.SCALE_SMOOTH ) ;
		label.setIcon(new ImageIcon(newimg));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.gridheight = 2;
		gbc_label.gridwidth = 2;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 7;
		frame.getContentPane().add(label, gbc_label);

		btnStartGame = new JButton("Join Game");
		GridBagConstraints gbc_btnStartGame = new GridBagConstraints();
		gbc_btnStartGame.insets = new Insets(0, 0, 5, 5);
		gbc_btnStartGame.gridx = 2;
		gbc_btnStartGame.gridy = 9;
		frame.getContentPane().add(btnStartGame, gbc_btnStartGame);
		

	}
	//*************************************************************************************//

	// Function to destroy this window
	public void disposeScreen(){
		frame.dispose();
	}

	// Function to get button
	public JButton getButton(){
		return btnStartGame;
	}

	// Function to get combobox of characters
	public JComboBox getCharCombobox(){
		return comboBox_char;
	}

	// Function to get combobox of character locations
	public JComboBox getLocationCombobox(){
		return comboBox_location;
	}

	// Function to get textField
	public JTextField getTextField(){
		return txtUnknown;
	}

	// Function to get label for images
	public JLabel getLabel(){
		return label;
	}

	// Function to get character List
	public String[] getCharList(){
		String[] characterStrings = {"Amazon", "Captain", "Swordsman", "Dwarf", "Elf", "Black Knight", "White Knight", "Berserker"};
		return characterStrings;
	}
	
	// Function to get character Location list
	public String[] getCharLocation(){
		String[] strList = new String[]{"INN", "HOUSE", "GUARD", "CHAPEL"};
		return strList;
	}

	// Function to get Character image
	public Image getCharImage(String s){
		if(s.equals("Amazon")){
			img = new ImageIcon("./images/characterdetail/Amazon.jpg").getImage();
			return img;
		}
		else if(s.equals("Captain")){
			img = new ImageIcon("./images/characterdetail/Captain.jpg").getImage();
			return img;
		}

		else if(s.equals("Swordsman")){
			img = new ImageIcon("./images/characterdetail/Swordsman.jpg").getImage();
			return img;
		}

		else if(s.equals("Elf")){
			img = new ImageIcon("./images/characterdetail/Elf.jpg").getImage();
			return img;
		}

		else if(s.equals("Dwarf")){
			img = new ImageIcon("./images/characterdetail/Dwarf.jpg").getImage();
			return img;
		}

		else if(s.equals("Black Knight")){
			img = new ImageIcon("./images/characterdetail/Black Knight.jpg").getImage();
			return img;
		}

		else if(s.equals("White Knight")){
			img = new ImageIcon("./images/characterdetail/White Knight.jpg").getImage();
			return img;
		}
		else if(s.equals("Berserker")){
			img = new ImageIcon("./images/characterdetail/Berserker.jpg").getImage();
			return img;
		}

		else{
			return img;
		}
	}
}
