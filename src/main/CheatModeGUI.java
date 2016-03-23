package main;

import java.awt.EventQueue;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.JButton;

//import com.sun.corba.se.impl.encoding.CodeSetConversion.BTCConverter;


import client.ClientGUI;
import messaging.MRMessage;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class CheatModeGUI {

	public JFrame frmCheatMode;
	private JButton mountainBtns[] = new JButton[5];
	private JButton caveBtns[] = new JButton[5];
	private JButton valleyBtns[] = new JButton[5];
	private JButton woodBtns[] = new JButton[5];
	private JButton siteChitBtns[] = new JButton[10];
	private JButton soundChitBtns[] = new JButton[10];
	private JButton btnDone;
	Image M_Img;
	Image newM_img;
	Chits mapChits = new Chits();
	int counter = 0; // counter for counting sound chits placed on the board
	int LY_counter = 0;
	int LT_counter = 0;


	JButton[] getMountainBtns() {
		return mountainBtns;
	}

	JButton[] getCaveBtns() {
		return caveBtns;
	}

	JButton[] getValleyBtns() {
		return valleyBtns;
	}

	JButton[] getWoodsBtns() {
		return woodBtns;
	}

	JButton[] getSiteChitsBtns() {
		return siteChitBtns;
	}

	JButton[] getSoundChitsBtns() {
		return soundChitBtns;
	}

	String[] cavesT = { "BL", "CN", "CS", "HP", "RN" };
	String[] mountainsT = { "CL", "CG", "DW", "LG", "MN" };
	String[] valleyT = { "AV", "BV", "CV", "DV", "EV" };
	String[] woodsT = { "LW", "MW", "NW", "OW", "PW" };
	String[] schitsT = { "LT (Lost Castle)", "LY (Lost City)", "CL (Mountain)",
			"CG (Mountain)", "DW (Mountain)", "LG (Mountain)", "MN (Mountain)",
			"BL (Cave)", "CN (Cave)", "CS (Cave)", "HP (Cave)", "RN (Cave)" };

	public static ArrayList<String> cavesName = new ArrayList<String>();
	public static ArrayList<String> mountainName = new ArrayList<String>();
	public static ArrayList<String> valleyName = new ArrayList<String>();
	public static ArrayList<String> woodsName = new ArrayList<String>();
	// used to store sound and site chits location for board
	public static ArrayList<String> soundName = new ArrayList<String>();
	public static ArrayList<String> siteName = new ArrayList<String>();
	int flag = 0;
	int flagS = 0;
	// Used to store sound and site chits for setup card
	static ArrayList<String> soundlCastle = new ArrayList<String>();
	static ArrayList<String> soundlCity = new ArrayList<String>();

	static ArrayList<String> sitelCastle = new ArrayList<String>();
	static ArrayList<String> sitelCity = new ArrayList<String>();


	public static ArrayList<String> FinaLCastle = new ArrayList<String>();
	public static ArrayList<String> FinaLCity = new ArrayList<String>();

	Chits tempChits = new Chits();

	public static boolean CheatMode = false;

	public boolean isCheatMode() {
		return this.CheatMode;
	}

	public void setCheatMode(boolean v) {
		this.CheatMode = v;
	}


//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					CheatModeGUI window = new CheatModeGUI();
//					window.frmCheatMode.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public CheatModeGUI() {
		initialize();
		for (int i = 0; i < 5; i++) {
			cavesName.add("");
			mountainName.add("");
			valleyName.add("");
			woodsName.add("");
		}

		for (int i = 0; i < 10; i++) {
			soundName.add("");
			siteName.add("");
			// store all chits related to treasure setup card
			soundlCastle.add("");
			soundlCity.add("");
			sitelCastle.add("");
			sitelCity.add("");	
		}
	}

	public void run() {
		Player model = new Player();
		PlayerGUI view = new PlayerGUI();
		PlayerController controller = new PlayerController(model, view);
		controller.control();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmCheatMode = new JFrame();
		frmCheatMode.setTitle("Cheat Mode");
		frmCheatMode.setBounds(200, 10, 300, 663);
		frmCheatMode.setResizable(false);
		frmCheatMode.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmCheatMode.getContentPane().setLayout(null);

		/******************************* CHEAT MODE ACTION LISTENERS *********************************/

		// Caves
		ActionListener CBtnlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() instanceof JButton) {
					String chitName = ((JButton) e.getSource()).getText();
					int chitNumber = Integer.parseInt(chitName.substring(1, 2));
					String[] Temp = new String[cavesT.length - 1];
					JFrame frame = new JFrame("Caves Tiles");
					String choice = (String) JOptionPane.showInputDialog(frame,
							"Select the Cave Tile?", "Caves Tiles",
							JOptionPane.QUESTION_MESSAGE, null, cavesT,
							cavesT[0]);
					if ((((JButton) e.getSource()).isEnabled() == true)
							&& choice != null) {
						((JButton) e.getSource()).setEnabled(false);
						cavesName.add(chitNumber, choice);
						counter++;
						System.out.println("NAme: " + choice + " number:  "
								+ chitNumber);
						int c = 0;
						for (int i = 0; i < cavesT.length; i++) {
							if (choice != cavesT[i]) {
								Temp[c] = cavesT[i];
								c++;
							}
						}
						cavesT = Temp;
					}
				}

				if(counter >= 40)
					btnDone.setEnabled(true);
				else
					btnDone.setEnabled(false);
			}
		};

		// Mountains
		ActionListener MBtnlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent m) {
				if (m.getSource() instanceof JButton) {
					String chitName = ((JButton) m.getSource()).getText();
					int chitNumber = Integer.parseInt(chitName.substring(1, 2));
					String[] Temp = new String[mountainsT.length - 1];
					JFrame frame = new JFrame("Mountain Tiles");
					String choice = (String) JOptionPane.showInputDialog(frame,
							"Select the Mountain Tile?", "Mountain Tiles",
							JOptionPane.QUESTION_MESSAGE, null, mountainsT,
							mountainsT[0]);
					if ((((JButton) m.getSource()).isEnabled() == true)
							&& choice != null) {
						((JButton) m.getSource()).setEnabled(false);
						mountainName.add(chitNumber, choice);
						counter++;
						System.out.println("Name: " + choice + " number:  "
								+ chitNumber);
						int c = 0;
						for (int i = 0; i < mountainsT.length; i++) {
							if (choice != mountainsT[i]) {
								Temp[c] = mountainsT[i];
								c++;
							}
						}
						mountainsT = Temp;
					}
				}

				if(counter >= 40)
					btnDone.setEnabled(true);
				else
					btnDone.setEnabled(false);
			}
		};

		// Valley
		ActionListener VBtnlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent v) {
				if (v.getSource() instanceof JButton) {
					String chitName = ((JButton) v.getSource()).getText();
					int chitNumber = Integer.parseInt(chitName.substring(1, 2));
					String[] Temp = new String[valleyT.length - 1];
					JFrame frame = new JFrame("Valley Tiles");
					String choice = (String) JOptionPane.showInputDialog(frame,
							"Select the Valley Tile?", "Valley Tiles",
							JOptionPane.QUESTION_MESSAGE, null, valleyT,
							valleyT[0]);
					if ((((JButton) v.getSource()).isEnabled() == true)
							&& choice != null) {
						((JButton) v.getSource()).setEnabled(false);
						valleyName.add(chitNumber, choice);
						counter++;
						System.out.println("Name: " + choice + " number:  "
								+ chitNumber);
						int c = 0;
						for (int i = 0; i < valleyT.length; i++) {
							if (choice != valleyT[i]) {
								Temp[c] = valleyT[i];
								c++;
							}
						}
						valleyT = Temp;
					}
				}
				if(counter >= 40)
					btnDone.setEnabled(true);
				else
					btnDone.setEnabled(false);
			}
		};

		// Woods
		ActionListener WBtnlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent w) {

				if (w.getSource() instanceof JButton) {
					String chitName = ((JButton) w.getSource()).getText();
					int chitNumber = Integer.parseInt(chitName.substring(1, 2));
					String[] Temp = new String[woodsT.length - 1];
					JFrame frame = new JFrame("Wood Tiles");
					String choice = (String) JOptionPane.showInputDialog(frame,
							"Select the Wood Tile?", "Wood Tiles",
							JOptionPane.QUESTION_MESSAGE, null, woodsT,
							woodsT[0]);
					if ((((JButton) w.getSource()).isEnabled() == true)
							&& choice != null) {
						((JButton) w.getSource()).setEnabled(false);
						woodsName.add(chitNumber, choice);
						counter++;
						System.out.println("Name: " + choice + " number:  "
								+ chitNumber);
						int c = 0;
						for (int i = 0; i < woodsT.length; i++) {
							if (choice != woodsT[i]) {
								Temp[c] = woodsT[i];
								c++;
							}
						}
						woodsT = Temp;
					}
				}

				if(counter >= 40)
					btnDone.setEnabled(true);
				else
					btnDone.setEnabled(false);
			}
		};

		/*************************************************************************************************************/

		// Sound chits Listener
		ActionListener SoundBtnlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent i) {
				if (i.getSource() instanceof JButton) {

					String chitName = ((JButton) i.getSource()).getText();
					int chitNumber = Integer.parseInt(chitName.substring(1, 2));
					String[] Temp;
					JFrame frame = new JFrame("Sound Chits");

					if ((siteName.get(0) == "" || siteName.get(1) == "")) {
						JOptionPane.showMessageDialog(frame,
								"Place Lost City and Lost Castle chits first");

					}

					else if ((!soundName.get(0).equals("") || soundName.get(1)
							.equals(""))) {
						String choice = (String) JOptionPane
								.showInputDialog(
										frame,
										"Select the Mountain or Cave Tile for Sound Chits?",
										"Sound Chits",
										JOptionPane.QUESTION_MESSAGE, null,
										schitsT, schitsT[0]);

						if ((((JButton) i.getSource()).isEnabled() == true)
								&& choice != null && flagS < 10) {
							((JButton) i.getSource()).setEnabled(false);
							soundName.add(chitNumber, choice.substring(0, 2)
									+ chitNumber);

							// check if chit selected is for lost castle then add chit to the soundlCastle array and add counter +1 
							if(choice.substring(0, 2).equals("LT")){
								soundlCastle.add(chitNumber,choice.substring(0, 2));
								FinaLCastle.add(tempChits.getChit(20+chitNumber).getChitName()+tempChits.getChit(20+chitNumber).getChitNum());
								LT_counter++;
							}

							// check if chit selected is for lost city then add chit to the soundlCity array and add counter +1 
							if(choice.substring(0, 2).equals("LY")){
								soundlCity.add(chitNumber,choice.substring(0, 2));
								
								FinaLCity.add(tempChits.getChit(20+chitNumber).getChitName()+tempChits.getChit(20+chitNumber).getChitNum());
								LY_counter++;
								
								//System.out.println("chit selected: " + choice.substring(0, 2) );
							}

							counter++;
							int c = 0;

							// SET NEW ARRAY SIZE 
							if ((choice.substring(0, 2).equals("LT") || choice.substring(0, 2).equals("LY")) && (LY_counter < 4 && LT_counter < 4)){
								Temp = new String[schitsT.length];		
							}
							else{
								Temp = new String[schitsT.length - 1];
							}


							// UPDATING NEW ARRAY 
							for (int j = 0; j < schitsT.length; j++) {
								if (schitsT[j] != choice && ((!(choice.substring(0, 2).equals("LT"))) && (!choice.substring(0, 2).equals("LY")))){
									Temp[c] = schitsT[j];
									c++;
								}
								else if (choice.substring(0, 2).equals("LT")){
									if(LT_counter == 5){
										for(int k=0; k < schitsT.length; k++){
											if((schitsT[k] != choice)){
												Temp[c] = schitsT[k];
												c++;
											}
										}
										LT_counter++;
										break;
									}

									else{
										Temp = schitsT;
										break;
									}
								}

								else if(choice.substring(0, 2).equals("LY")){
									if(LY_counter == 5){
										for(int k=0; k < schitsT.length; k++){
											if((schitsT[k] != choice)){
												Temp[c] = schitsT[k];
												c++;
											}
										}
										LY_counter++;
										break;
									}
									else{
										Temp = schitsT;
										break;
									}
								}
							}
							schitsT = Temp;
							flagS++;
						}
					}

					// Printing data
					System.out.println("*****************************************");
					for(int p = 0; p < 10; p++){
						System.out.println("SOUND LOST CASTLE: " + soundlCastle.get(p));
					}
					System.out.println("*****************************************");

					System.out.println("*****************************************");
					for(int p = 0; p < 10; p++){
						System.out.println("SOUND LOST CITY: " + soundlCity.get(p));
					}
					System.out.println("*****************************************");

				}

				if(counter >= 40)
					btnDone.setEnabled(true);
				else
					btnDone.setEnabled(false);

			}
		};

		// Site Chits Listener
		ActionListener SiteBtnlistener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent k) {
				if (k.getSource() instanceof JButton) {

					String chitName = ((JButton) k.getSource()).getText();
					int chitNumber = Integer.parseInt(chitName.substring(1, 2));
					String[] Temp;
					JFrame frame = new JFrame("Site Chits");

					if ((siteName.get(0) == "" || siteName.get(1) == "")
							&& !(chitName.equals("i0") || chitName.equals("i1"))) {
						JOptionPane.showMessageDialog(frame,
								"Place Lost City and Lost Castle chits first");

					}

					else if ((chitName.equals("i0") || chitName.equals("i1"))
							|| (!siteName.get(0).equals("") || siteName.get(1)
									.equals(""))) {
						String choice = (String) JOptionPane
								.showInputDialog(
										frame,
										"Select the Mountain or Cave Tile for Site Chits?",
										"Site Chits",
										JOptionPane.QUESTION_MESSAGE, null,
										schitsT, schitsT[0]);

						if ((((JButton) k.getSource()).isEnabled() == true)
								&& choice != null && flag < 10) {
							((JButton) k.getSource()).setEnabled(false);
							siteName.add(chitNumber, choice.substring(0, 2)
									+ chitNumber);

							// check if chit selected is for lost castle then add chit to the sitelCastle array and add counter +1 
							if(choice.substring(0, 2).equals("LT")){
								sitelCastle.add(chitNumber,choice.substring(0, 2));
								FinaLCastle.add(tempChits.getChit(30+chitNumber).getChitName()+tempChits.getChit(30+chitNumber).getChitNum());
								LT_counter++;
							}

							// check if chit selected is for lost city then add chit to the sitelCity array and add counter +1 
							if(choice.substring(0, 2).equals("LY")){
								sitelCity.add(chitNumber,choice.substring(0, 2));
								FinaLCity.add(tempChits.getChit(30+chitNumber).getChitName()+tempChits.getChit(30+chitNumber).getChitNum());
								LY_counter++;
							}

							counter++;
							int c = 0;

							// SET NEW ARRAY SIZE 
							if ((choice.substring(0, 2).equals("LT") || choice.substring(0, 2).equals("LY")) && (LY_counter < 4 && LT_counter < 4)){
								Temp = new String[schitsT.length];

							}
							else{
								Temp = new String[schitsT.length - 1];

							}


							// UPDATING NEW ARRAY 
							for (int j = 0; j < schitsT.length; j++) {
								if (schitsT[j] != choice && ((!(choice.substring(0, 2).equals("LT"))) && (!choice.substring(0, 2).equals("LY")))){
									Temp[c] = schitsT[j];
									c++;
								}
								else if (choice.substring(0, 2).equals("LT")){
									if(LT_counter == 5){
										for(int b=0; b < schitsT.length; b++){
											if((schitsT[b] != choice)){
												Temp[c] = schitsT[b];
												c++;
											}
										}
										LT_counter++;
										break;
									}

									else{
										Temp = schitsT;
										break;
									}
								}

								else if(choice.substring(0, 2).equals("LY")){									
									if(LY_counter == 5){
										for(int f=0; f < schitsT.length; f++){
											if((schitsT[f] != choice)){
												Temp[c] = schitsT[f];
												c++;
											}
										}
										LY_counter++;
										break;
									}
									else{
										Temp = schitsT;
										break;
									}
								}
							}
							schitsT = Temp;
							flag++;
						}
					}

					//Printing data
					System.out.println("*****************************************");
					for(int p = 0; p < 10; p++){
						System.out.println("SITE LOST CASTLE: " + sitelCastle.get(p));
					}
					System.out.println("*****************************************");

					System.out.println("*****************************************");
					for(int p = 0; p < 10; p++){
						System.out.println("SITE LOST CITY: " + sitelCity.get(p));
					}
					System.out.println("*****************************************");

				}

				if(counter >= 40){

					btnDone.setEnabled(true);
				}
				else
					btnDone.setEnabled(false);
			}
		};

		/*************************************************************************************************************************/

		JPanel WarningPanel = new JPanel();
		WarningPanel.setBorder(new TitledBorder(new LineBorder(new Color(51,
				153, 255)), "Warning Chits", TitledBorder.LEADING,
				TitledBorder.TOP, null, SystemColor.windowText));
		WarningPanel.setBounds(0, 0, 290, 277);
		frmCheatMode.getContentPane().add(WarningPanel);
		WarningPanel.setLayout(null);

		/*** CAVE CHITS *******/
		for (int c = 0; c < caveBtns.length; c++) {
			M_Img = mapChits.getChit(c).getImage(0);
			newM_img = M_Img.getScaledInstance(50, 50,
					java.awt.Image.SCALE_SMOOTH);
			caveBtns[c] = new JButton("c" + c, new ImageIcon(newM_img));
			caveBtns[c].setHorizontalTextPosition(SwingConstants.CENTER);
			caveBtns[c].setForeground(new Color(0, 0, 0, 0));
			caveBtns[c].setBounds((55 * c) + 10, 20, 50, 50);
			caveBtns[c].addActionListener(CBtnlistener);
			WarningPanel.add(caveBtns[c]);
		}

		/*** MOUNTAINS CHITS *******/
		for (int m = 0; m < mountainBtns.length; m++) {
			M_Img = mapChits.getChit(m + 5).getImage(0);
			newM_img = M_Img.getScaledInstance(50, 50,
					java.awt.Image.SCALE_SMOOTH);
			mountainBtns[m] = new JButton("m" + m, new ImageIcon(newM_img));
			mountainBtns[m].setHorizontalTextPosition(SwingConstants.CENTER);
			mountainBtns[m].setForeground(new Color(0, 0, 0, 0));
			mountainBtns[m].setBounds((55 * m) + 10, 80, 50, 50);
			mountainBtns[m].addActionListener(MBtnlistener);
			WarningPanel.add(mountainBtns[m]);
		}

		/*** VALLEY CHITS *******/
		for (int v = 0; v < valleyBtns.length; v++) {
			M_Img = mapChits.getChit(v + 10).getImage(0);
			newM_img = M_Img.getScaledInstance(50, 50,
					java.awt.Image.SCALE_SMOOTH);
			valleyBtns[v] = new JButton("v" + v, new ImageIcon(newM_img));
			valleyBtns[v].setHorizontalTextPosition(SwingConstants.CENTER);
			valleyBtns[v].setForeground(new Color(0, 0, 0, 0));
			valleyBtns[v].setBounds((55 * v) + 10, 140, 50, 50);
			valleyBtns[v].addActionListener(VBtnlistener);
			WarningPanel.add(valleyBtns[v]);
		}

		/*** WOODS CHITS *******/
		for (int m = 0; m < woodBtns.length; m++) {
			M_Img = mapChits.getChit(m + 15).getImage(0);
			newM_img = M_Img.getScaledInstance(50, 50,
					java.awt.Image.SCALE_SMOOTH);
			woodBtns[m] = new JButton("w" + m, new ImageIcon(newM_img));
			woodBtns[m].setHorizontalTextPosition(SwingConstants.CENTER);
			woodBtns[m].setForeground(new Color(0, 0, 0, 0));
			woodBtns[m].setBounds((55 * m) + 10, 200, 50, 50);
			woodBtns[m].addActionListener(WBtnlistener);
			WarningPanel.add(woodBtns[m]);
		}

		/*********************************************************************************************************/

		JPanel SoundPanel = new JPanel();
		SoundPanel.setBorder(new TitledBorder(new LineBorder(new Color(51, 153,
				255)), "Sound Chits", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		SoundPanel.setBounds(0, 280, 290, 150);
		frmCheatMode.getContentPane().add(SoundPanel);
		SoundPanel.setLayout(null);

		/************** SOUND CHITS *************/

		for (int s = 0; s < soundChitBtns.length; s++) {
			M_Img = mapChits.getChit(s + 20).getImage(0);
			newM_img = M_Img.getScaledInstance(50, 50,
					java.awt.Image.SCALE_SMOOTH);
			soundChitBtns[s] = new JButton("s" + s, new ImageIcon(newM_img));
			soundChitBtns[s].setHorizontalTextPosition(SwingConstants.CENTER);
			soundChitBtns[s].setForeground(new Color(0, 0, 0, 0));
			if (s < 5) {
				soundChitBtns[s].setBounds((55 * s) + 10, 20, 50, 50);
			} else {
				soundChitBtns[s].setBounds((55 * (s - 5)) + 10, 80, 50, 50);
			}
			soundChitBtns[s].addActionListener(SoundBtnlistener);
			SoundPanel.add(soundChitBtns[s]);
		}

		JPanel SitePanel = new JPanel();
		SitePanel.setBorder(new TitledBorder(new LineBorder(new Color(51, 153,
				255)), "Site Chits", TitledBorder.LEADING, TitledBorder.TOP,
				null, new Color(0, 0, 0)));
		SitePanel.setBounds(0, 433, 290, 150);
		frmCheatMode.getContentPane().add(SitePanel);
		SitePanel.setLayout(null);

		/************** SITE CHITS *************/

		for (int s = 0; s < siteChitBtns.length; s++) {
			M_Img = mapChits.getChit(s + 30).getImage(0);
			newM_img = M_Img.getScaledInstance(50, 50,
					java.awt.Image.SCALE_SMOOTH);
			siteChitBtns[s] = new JButton("i" + s, new ImageIcon(newM_img));
			siteChitBtns[s].setHorizontalTextPosition(SwingConstants.CENTER);
			siteChitBtns[s].setForeground(new Color(0, 0, 0, 0));
			if (s < 5) {
				siteChitBtns[s].setBounds((55 * s) + 10, 20, 50, 50);
			} else {
				siteChitBtns[s].setBounds((55 * (s - 5)) + 10, 80, 50, 50);
			}
			siteChitBtns[s].addActionListener(SiteBtnlistener);
			SitePanel.add(siteChitBtns[s]);
		}

		btnDone = new JButton("Done");
		btnDone.setBounds(107, 604, 89, 23);
		frmCheatMode.getContentPane().add(btnDone);
		btnDone.setEnabled(false);

		// Action listener for done button
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				// Printing final lost castle and lost city chits array for treasure setup card
				
//				for(int sc=0; sc < FinaLCastle.size(); sc++){
//					System.out.println("CHITS : " + FinaLCastle.get(sc).getChitName());
//				}
//				
//				System.out.println("************************************************");
//				for(int sc=0; sc < FinaLCity.size(); sc++){
//					System.out.println("CHITS : " + FinaLCity.get(sc).getChitName());
//				}

				// send the chits to the server
				MRMessage mr;

				// TODO Ask Tahniat
				// 1. How about appearance chits that are set in Random mode?
				// Don't we need to set them in cheat mode?
				// 2. What are sound and site chits; we did not use them in the
				// random mode??
				// 3. What about sitelCastle, sitelCity variables? They seem to
				// never used.

				// mr = new MRMessage(MRMessage.GET_CHITS, "app");
				// mr.setObject(appChart);
				// writeMsg(mr);
				mr = new MRMessage(MRMessage.SET_CHITS, "cave");
				mr.setObject(cavesName);
				ClientGUI.mr.sendMessage(mr);
				mr = new MRMessage(MRMessage.SET_CHITS, "mountain");
				mr.setObject(mountainName);
				ClientGUI.mr.sendMessage(mr);
				mr = new MRMessage(MRMessage.SET_CHITS, "wood");
				mr.setObject(woodsName);
				ClientGUI.mr.sendMessage(mr);
				mr = new MRMessage(MRMessage.SET_CHITS, "valley");
				mr.setObject(valleyName);
				ClientGUI.mr.sendMessage(mr);
				mr = new MRMessage(MRMessage.SET_CHITS, "sound");
				mr.setObject(soundName);
				ClientGUI.mr.sendMessage(mr);
				mr = new MRMessage(MRMessage.SET_CHITS, "site");
				mr.setObject(siteName);
				ClientGUI.mr.sendMessage(mr);
				
				mr = new MRMessage(MRMessage.SET_CHITS, "LostCity");
				mr.setObject(FinaLCity);
				ClientGUI.mr.sendMessage(mr);
				
				mr = new MRMessage(MRMessage.SET_CHITS, "LostCastle");
				mr.setObject(FinaLCastle);
				ClientGUI.mr.sendMessage(mr);

				mr = new MRMessage(MRMessage.BOARD_INITIALIZED, "site");
				mr.setObject(siteName);
				ClientGUI.mr.sendMessage(mr);

				// We have finished the work with the cheat mode gui?
				// dispose it now
				frmCheatMode.setVisible(false);
				frmCheatMode.dispose();

			}
		});


	}

}
