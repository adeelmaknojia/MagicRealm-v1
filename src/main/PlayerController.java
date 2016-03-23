package main;

import java.awt.Image;
import java.awt.ItemSelectable;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.CharConversionException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import main.GameGUI.DrawingPanel;
import main.GameGUI.DrawingPanel.MyMouseListener;
import messaging.MRMessage;
import Tiles.Clearing;
import client.ClientGUI;
import client.ClientInfo;
import client.MRClient;

public class PlayerController {
	public static Player model;
	public PlayerGUI view;
	public static ArrayList<Player> playerList = new ArrayList<Player>();
	private ActionListener actionListener;
	private ActionListener radioActionListener;
	private Image tempImg;
	private CharacterCards cards = new CharacterCards();
	private Character[] gameChars = cards.getClist();
	private String charStr = "Amazon";
	private String locStr = "INN";
	private int counter = 0;
	public GameGUI gameView;
	private JRadioButton[] radioBtn;
	public int cnt = 0; // variable to control the calling of action listener
	// on remove button
	public int buttonNumber = 0; // variable to check which radio button is
	// selected
	int m; // records number of moves done by player
	int setupcount = 0;
	SetupGUI setupView;
	JLabel[] displayI;
	JLabel[] displayC;
	JLabel[] displayG;
	JLabel[] displayH;
	
	// Per Clearings label to display items
	JLabel[] displayCl = new JLabel[25]; 
	
	public JLabel[] lbl_Weapons = new JLabel[25];
	public JLabel[] lbl_Armor = new JLabel[25];
	public JLabel[] lbl_ActionChits = new JLabel[25];

	// VICtory requirement point
	static int nG, nF, nN, gP, fP, nP;
	
	public ClientInfo myInfo;
	// Game sound class 
	SoundClip sound = new SoundClip();

	// CONSTRUCTOR
	public PlayerController() {
		this.charStr = "Amazon";
	}

	// CONSTRUCTOR
	public PlayerController(Player model, PlayerGUI view) {
		this.model = model;
		this.view = view;
		
		for(int i=0; i < 25; i++){
			lbl_Weapons[i] = new JLabel("");
			lbl_Armor[i] = new JLabel("");
			lbl_ActionChits[i] = new JLabel("");
			displayCl[i] = new JLabel("");
			
		}
	}

	// GETTERS
	public String getPName() {
		return model.getPlayerName();
	}

	public Character getPCharacter() {
		return model.getPlayerCharacter();
	}

	public Player getPlayer(int index) {
		return playerList.get(index);
	}

	public int getCounter() {
		return this.counter;
	}

	public Point getClocation() {
		return model.getPlayerCharacter().getPLocation();
	}

	public String getCTileLocation() {
		return model.getPlayerCharacter().getTileLocation();
	}

	public Clearing getCClearingLocation() {
		return model.getPlayerCharacter().getClearingLocation();
	}

	public int isCHidden() {
		return model.getPlayerCharacter().isHidden();
	}

	// SETTERS
	public void setPName(String pName) {
		model.setPlayerName(pName);
	}

	public void setPCharacter(Character pCharacter) {
		model.setPlayerCharacter(pCharacter);
	}

	public void setPStartingLocation(String location) {
		model.getPlayerCharacter().setStartingLocation(location);
	}

	public void setP_ID(int id) {
		model.setPlayerId(id);
	}

	public void setPList(Player p) {
		playerList.add(p);
	}

	public void setGold(int gold) {
		model.getPlayerCharacter().setGold(gold);
	}

	public void setFame(int fame) {
		model.getPlayerCharacter().setFame(fame);
	}

	public void setNotoriety(int notoriety) {
		model.getPlayerCharacter().setNotoriety(notoriety);
	}

	public void setClocation(int x, int y) {
		model.getPlayerCharacter().setPLocation(x, y);
	}

	public void setCBoardLocation(String i) {
		model.getPlayerCharacter().setBoardLocation(i);
	}

	public void setClearingLocation(Clearing c) {
		model.getPlayerCharacter().setClearingLocation(c);
	}

	public void setCHide(boolean h) {
		model.getPlayerCharacter().hide();
	}

	public void setCUnHide() {
		model.getPlayerCharacter().unhide();
	}

	// Function to set radio button label
	public void setRadionBtxText() {
		String str = null;
		radioBtn = gameView.getselectionBtns();
		int i = (playerList.size() - 1);
		str = playerList.get(i).getPlayerName();
		gameView.setRadiobtnText(i, str);
		radioBtn[i].setSelected(true);
		radioBtn[i].setVisible(true);
		radioBtn[i].addActionListener(radioActionListener);
	}

	public void updateRemoveRButton() {
		for (int i = 0; i < playerList.size(); i++) {
			String str = playerList.get(i).getPlayerName();
			gameView.setRadiobtnText(i, str);
			radioBtn[i].setVisible(true);
		}
		radioBtn[playerList.size()].setVisible(false);
		if (buttonNumber == counter + 1) {
			//updateOnRButton(buttonNumber - 1);
			radioBtn[buttonNumber - 1].setSelected(true);
		}
	}

	public void updateOnRButton() {
		m = model.getPlayerCharacter().getMoves();
		// PLAYERS
		// MOVES
		// gameView.setLblAdeelText(model.getPlayerName());
		// gameView.setCharacterText(model.getPlayerCharacter()
		// .getName());
		// gameView.setCounterText(model.getPlayerCharacter()
		// .getCounterName());
		// gameView.setGoldText(""
		// + model.getPlayerCharacter().getStartingGold());
		// gameView.setVulText(model.getPlayerCharacter()
		// .getWeight());
		// gameView.setFameText(""
		// + model.getPlayerCharacter().getFame());
		// gameView.setNotereyText(""
		// + model.getPlayerCharacter().getNotoriety());
		// gameView.setCCounterImage(model.getPlayerCharacter()
		// .getName());
		// gameView.getTable().setValueAt(
		// model.getPlayerCharacter().getGoldPoint(),
		// 0, 1);
		// gameView.getTable().setValueAt(
		// model.getPlayerCharacter().getFamePoint(),
		// 2, 1);
		// gameView.getTable().setValueAt(
		// model.getPlayerCharacter()
		// .getNotorityPoint(), 1, 1);
		// gameView.getTable().setValueAt(
		// model.getPlayerCharacter()
		// .getGoldRecorded(), 0, 4);
		// gameView.getTable().setValueAt(
		// model.getPlayerCharacter()
		// .getFameRecorded(), 2, 4);
		// gameView.getTable().setValueAt(
		// model.getPlayerCharacter()
		// .getNotorityRecordedt(), 1, 4);
		// if (playerList.get(buttonNumber).getPlayerCharacter().getMoves() >=
		// 0) {
		// gameView.getMoveBtn().setEnabled(true);
		// }
		// gameView.getActionField().setText("");
		// if (m <= 0) {
		// gameView.getMoveBtn().setEnabled(false);
		// gameView.getActionField().setText("Not Enough Moves");
		// }

	}

//	public void updateOnRButton(int index) {
//		if (playerList != null && playerList.size() > 0) {
//			m = playerList.get(buttonNumber).getPlayerCharacter().getMoves();
//			// PLAYERS
//			// MOVES
//			gameView.setLblAdeelText(getPlayer(index).getPlayerName());
//			gameView.setCharacterText(getPlayer(index).getPlayerCharacter()
//					.getName());
//			gameView.setCounterText(getPlayer(index).getPlayerCharacter()
//					.getCounterName());
//			gameView.setGoldText(""
//					+ getPlayer(index).getPlayerCharacter().getStartingGold());
//			gameView.setVulText(getPlayer(index).getPlayerCharacter()
//					.getWeight());
//			gameView.setFameText(""
//					+ getPlayer(index).getPlayerCharacter().getFame());
//			gameView.setNotereyText(""
//					+ getPlayer(index).getPlayerCharacter().getNotoriety());
//			
////			gameView.setCCounterImage(getPlayer(index).getPlayerCharacter()
////					.getName());
//			
////			// Special advantage
//			gameView.setCAdvantage1(getPlayer(index).getPlayerCharacter()
//					.getName(), getPlayer(index).getPlayerCharacter().getSpecialAdvantage(0));
//						
//			gameView.setCAdvantage2(getPlayer(index).getPlayerCharacter()
//					.getName(), getPlayer(index).getPlayerCharacter().getSpecialAdvantage(1));
//			
//			
//			gameView.getTable().setValueAt(
//					playerList.get(index).getPlayerCharacter().getGoldPoint(),
//					0, 1);
//			gameView.getTable().setValueAt(
//					playerList.get(index).getPlayerCharacter().getFamePoint(),
//					2, 1);
//			gameView.getTable().setValueAt(
//					playerList.get(index).getPlayerCharacter()
//							.getNotorityPoint(), 1, 1);
//			gameView.getTable().setValueAt(
//					playerList.get(index).getPlayerCharacter()
//							.getGoldRecorded(), 0, 4);
//			gameView.getTable().setValueAt(
//					playerList.get(index).getPlayerCharacter()
//							.getFameRecorded(), 2, 4);
//			gameView.getTable().setValueAt(
//					playerList.get(index).getPlayerCharacter()
//							.getNotorityRecordedt(), 1, 4);
//			if (playerList.get(buttonNumber).getPlayerCharacter().getMoves() >= 0) {
//				if (MRClient.gameStarted
//						&& MRClient.currentPlayer == buttonNumber)
//					gameView.getMoveBtn().setEnabled(true);
//			}
//			gameView.getActionField().setText("");
//			if (m <= 0) {
//				gameView.getMoveBtn().setEnabled(false);
//				gameView.getActionField().setText("Not Enough Moves");
//			}
//		}
//	}

	// Remove player from the list and update the counter
	public void removePlayer(int id) {
		playerList.remove(id);
		counter--;
	}


	public Character getCharObj(String cName) {
		if (cName.equalsIgnoreCase("Amazon"))
			return gameChars[0];
		else if (cName.equalsIgnoreCase("Captain"))
			return gameChars[1];
		else if (cName.equalsIgnoreCase("Swordsman"))
			return gameChars[2];
		else if (cName.equalsIgnoreCase("Dwarf"))
			return gameChars[3];
		else if (cName.equalsIgnoreCase("Elf"))
			return gameChars[4];
		else if (cName.equalsIgnoreCase("Black Knight"))
			return gameChars[5];
		else if (cName.equalsIgnoreCase("White Knight"))
			return gameChars[6];
		else if (cName.equalsIgnoreCase("Berserker"))
			return gameChars[7];
		else
			return gameChars[0];
	}

	/**
	 * Updates the ClientUI with the number of clients connected and playing the
	 * game
	 * 
	 */
	public void updateClientPanel(String[] clients) {
		if (gameView.selectionPanel != null) {
			// remove previous info
			gameView.selectionPanel.removeAll();
			// add freshinfo
			int i = 0;
			// ButtonGroup operations = new ButtonGroup();
			gameView.lblPlayers = new JLabel[clients.length];
			for (String ci : clients) {
				gameView.lblPlayers[i] = new JLabel();
				gameView.lblPlayers[i].setText((i + 1) + ". " + ci);
				gameView.lblPlayers[i].setLocation(20, 20 + i * 20);
				gameView.lblPlayers[i].setSize(300, 20);
				gameView.lblPlayers[i].setVisible(true);
				gameView.selectionPanel.add(gameView.lblPlayers[i]);
				// operations.add(gameView.selectionBtns[i]);
				i++;
			}

			gameView.selectionPanel.revalidate();
			gameView.selectionPanel.repaint();
		}
	}

	public final void updateUI(String[] response) {
		this.buttonNumber = Integer.parseInt(response[4]);
		// Action listener for Adding new player
		ActionListener AddBtnActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				view.getFrame().setVisible(true);
			}
		};

		// Action listener for Removing new player
		ActionListener RemoveBtnActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				removePlayer(buttonNumber);
				updateRemoveRButton();
			}
		};

		// Action listener for opening "Adding Victory Requirements Window"
		ActionListener AddVRActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
//				static int nG nF, nN, gP, fP, nP;
				if (gameView.getTable().getValueAt(0, 3) != null){
					nG = (int) gameView.getTable().getValueAt(0, 3);
				}
					
				else {
					JOptionPane.showMessageDialog(gameView.frame,
							"Please set the needed Gold points ");
					return;
				}
				if (gameView.getTable().getValueAt(2, 3) != null){
					nF = (int) gameView.getTable().getValueAt(2, 3);
				}
				else {
					JOptionPane.showMessageDialog(gameView.frame,
							"Please set the needed Fame points ");
					return;
				}
				if (gameView.getTable().getValueAt(1, 3) != null){
					nN = (int) gameView.getTable().getValueAt(1, 3);
				}
				else {
					JOptionPane.showMessageDialog(gameView.frame,
							"Please set the needed Notoreity points ");
					return;
				}

				if (gameView.getTable().getValueAt(0, 1) != null){
					gP = (int) gameView.getTable().getValueAt(0, 1);
				}
				else {
					JOptionPane.showMessageDialog(gameView.frame,
							"Please set the Gold points ");
					return;
				}
				if (gameView.getTable().getValueAt(2, 1) != null){
					fP = (int) gameView.getTable().getValueAt(2, 1);
				}
				else {
					JOptionPane.showMessageDialog(gameView.frame,
							"Please set the Fame points ");
					return;
				}
				if (gameView.getTable().getValueAt(1, 1) != null){
					nP = (int) gameView.getTable().getValueAt(1, 1);
				}
				else {
					JOptionPane.showMessageDialog(gameView.frame,
							"Please set the Notoreity points ");
					return;
				}

				model.getPlayerCharacter().setNeedGold(nG);
				model.getPlayerCharacter().setNeedFame(nF);
				model.getPlayerCharacter().setNeedNotoriety(nN);

				model.getPlayerCharacter().setGoldPoint(gP);
				model.getPlayerCharacter().setFamePoint(fP);
				model.getPlayerCharacter().setNotorietyPoint(nP);
				
				gameView.setGoldText(nG+"");
				gameView.setFameText(nF+"");
				gameView.setNotereyText(nN+"");

				MRMessage mrm = new MRMessage(
						MRMessage.PLAYER_VICTORY_REQUIREMENTS,
						myInfo.getClientName() + "-"
								+ myInfo.getCharacterName() + "-"
								+ myInfo.getCharacterLocation() + "#" + nG
								+ "#" + nF + "#" + nN + "#" + gP + "#" + fP
								+ "#" + nP);
				ClientGUI.mr.sendMessage(mrm);
				gameView.getVRBtn().setEnabled(false);

			}
		};

		// Action listener for opening "SETUP CARD"
		ActionListener SetupCardActionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (setupcount == 0) {
					setupView = new SetupGUI();
					gameView.getSetupBtn().setText("Hide Setup Card");
					setupcount++;
				} else {
					if (gameView.getSetupBtn().getText()
							.equals("Show Setup Card")) {
						setupView.getFrame().setVisible(true);
						gameView.getSetupBtn().setText("Hide Setup Card");
					} else if (gameView.getSetupBtn().getText()
							.equals("Hide Setup Card")) {
						setupView.getFrame().setVisible(false);
						gameView.getSetupBtn().setText("Show Setup Card");
					}
				}

			}
		};

		// ************** Display Dwellings *******************//
		// Dwelling button action listener
		ActionListener InnBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				gameView.getDwellingDPanel().removeAll();
				System.out
						.println("--------------INN is clicked----------------");
				if (gameView.getDwellingDPanel().isVisible() == false) {
					gameView.getDwellingDPanel().setVisible(true);
					gameView.setMessageText("");
					gameView.getDwellingDPanel().setBorder(BorderFactory
							.createTitledBorder("Inn Display Panel"));
					displayI = gameView.getInnLabels();

					for (int i = 0; i < displayI.length; i++) {
						if (((60 * i) + 10) < ((60 * 12) + 10)) {
							displayI[i].setBounds(((60 * i) + 10), 30, 50, 50);
						} else {
							displayI[i].setBounds(((60 * (i - 12)) + 10), 90,
									50, 50);
						}
						gameView.getDwellingDPanel().add(displayI[i]);
					}
				} else if (gameView.getDwellingDPanel().isVisible() == true)
					gameView.getDwellingDPanel().setVisible(false);

			}
		};

		ActionListener HouseBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				gameView.getDwellingDPanel().removeAll();
				System.out.println("-----------House is clicked--------------");
				if (gameView.getDwellingDPanel().isVisible() == false) {
					gameView.getDwellingDPanel().setVisible(true);
					gameView.setMessageText("");
					gameView.getDwellingDPanel().setBorder(BorderFactory
							.createTitledBorder("House Display Panel"));
					displayH = gameView.getHouseLabels();

					for (int i = 0; i < displayH.length; i++) {
						if (((60 * i) + 10) < ((60 * 12) + 10)) {
							displayH[i].setBounds(((60 * i) + 10), 30, 50, 50);
						} else {
							displayH[i].setBounds(((60 * (i - 12)) + 10), 90,
									50, 50);
						}
						gameView.getDwellingDPanel().add(displayH[i]);
					}
				} else if (gameView.getDwellingDPanel().isVisible() == true)
					gameView.getDwellingDPanel().setVisible(false);
			}
		};

		ActionListener ChappelBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				gameView.getDwellingDPanel().removeAll();
				System.out.println("--------Chapel is clicked-------------");
				if (gameView.getDwellingDPanel().isVisible() == false) {
					gameView.getDwellingDPanel().setVisible(true);
					gameView.setMessageText("");
					gameView.getDwellingDPanel().setBorder(BorderFactory
							.createTitledBorder("Chapel Display Panel"));
					displayC = gameView.getChapelLabels();

					for (int i = 0; i < displayC.length; i++) {
						if (((60 * i) + 10) < ((60 * 12) + 10)) {
							displayC[i].setBounds(((60 * i) + 10), 30, 50, 50);
						} else {
							displayC[i].setBounds(((60 * (i - 12)) + 10), 90,
									50, 50);
						}
						gameView.getDwellingDPanel().add(displayC[i]);
					}
				} else if (gameView.getDwellingDPanel().isVisible() == true)
					gameView.getDwellingDPanel().setVisible(false);

			}
		};

		ActionListener GuardBtnListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				System.out
						.println("-----------Guard is clicked-------------------");
				gameView.getDwellingDPanel().removeAll();
				if (gameView.getDwellingDPanel().isVisible() == false) {
					gameView.getDwellingDPanel().setVisible(true);
					gameView.setMessageText("");
					gameView.getDwellingDPanel().setBorder(BorderFactory
							.createTitledBorder("Guard Display Panel"));
					displayG = gameView.getGuardLabels();

					for (int i = 0; i < displayG.length; i++) {
						if (((60 * i) + 10) < ((60 * 12) + 10)) {
							displayG[i].setBounds(((60 * i) + 10), 30, 50, 50);
						} else {
							displayG[i].setBounds(((60 * (i - 12)) + 10), 90,
									50, 50);
						}
						gameView.getDwellingDPanel().add(displayG[i]);
					}
				} else if (gameView.getDwellingDPanel().isVisible() == true)
					gameView.getDwellingDPanel().setVisible(false);

			}
		};
		
		
		ActionListener ClearingListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				
				System.out.println("-----------Clearing is clicked-------------------");
				gameView.getDwellingDPanel().removeAll();
				if(gameView.getDwellingDPanel().isVisible() == false && e.getSource() instanceof JButton){	
					gameView.getDwellingDPanel().setVisible(true);
					gameView.setMessageText("");
					gameView.getDwellingDPanel().setBorder(BorderFactory
							.createTitledBorder("Clearing Display Panel"));
				
					String cNum = ((JButton) e.getSource()).getText();
					int targetClearing = Integer.parseInt(cNum);
					System.out.println("CLEARING SOURCE: " + targetClearing);
								

					int counter = 1;
					
					for (int i = 0; i < gameView.BSIZE; i++) {
						for (int j = 0; j < gameView.BSIZE; j++) {
							if (gameView.board[i][j].getName() != null) {
								for (int k = 1; k < gameView.board[i][j].getAllClearings().length + 1; k++) {
								if(gameView.board[i][j].getClearing(k) != null){
									if(counter == targetClearing+1){
										int playerSize = gameView.board[i][j].getClearing(k).getAllCharactersOnClearing().size();
										for (int s = 0 ; s < playerSize; s++){
											System.out.println("Players in clearings: " + gameView.board[i][j].getClearing(k).getAllCharactersOnClearing().get(s).getName());
											Image PImg = gameView.board[i][j].getClearing(k).getAllCharactersOnClearing().get(s).getImage();
											Image newimg = PImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH );
											displayCl[s].setIcon(new ImageIcon(newimg));
										}
										int monsterSize = gameView.board[i][j].getClearing(k).getAllMonsters().size();
										for (int s = 0 ; s < monsterSize; s++){
											System.out.println("Monsters in clearings: " + gameView.board[i][j].getClearing(k).getMonster(s).getName());
											Image PImg = gameView.board[i][j].getClearing(k).getMonster(s).getImage();
											Image newimg = PImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH );
											displayCl[s+playerSize].setIcon(new ImageIcon(newimg));
										}
									}
									
										counter++;
									}
								}
							}
							
							else{
								gameView.setMessageText("CLEARING IS EMPTY");
								System.out.println("CLEARING IS EMPTY");
							}
						}
					}
					
					if(!(displayCl == (null))){
					System.out.println("DISPLAY LENGTH : ****** " + displayCl.length);
					for(int i=0; i < displayCl.length; i++){
						
						if(((60*i)+10) < ((60*12)+10)){
							displayCl[i].setBounds(((60*i)+10), 30, 50, 50);
						}
						else{
							displayCl[i].setBounds(((60*(i-12))+10), 90, 50, 50);
						}	
						gameView.getDwellingDPanel().add(displayCl[i]);
					}
					}
				}
				else if(gameView.getDwellingDPanel().isVisible() == true){
					for(int i=0; i < 25; i++){
						displayCl[i] = new JLabel("");	
					}
					gameView.getDwellingDPanel().setVisible(false);
					gameView.getDwellingDPanel().removeAll();
				}
			}
			};
			
			
			/****************** CHARACTER DETAIL BUTTONS ***********************/
			
			ActionListener CWeaponsBtnListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {

					System.out.println("-----------Character Weapons clicked-------------------");
					gameView.getDwellingDPanel().removeAll();
					if (gameView.getDwellingDPanel().isVisible() == false) {
						gameView.getDwellingDPanel().setVisible(true);
						gameView.getDwellingDPanel().setBorder(BorderFactory
								.createTitledBorder("Player Weapons"));
						
						
						for(int j=0; j < playerList.get(buttonNumber).getPlayerCharacter().getweaponList().size(); j++){
							Image tempImg1 = playerList.get(buttonNumber).getPlayerCharacter().getweaponList().get(j).getImage();
							Image newimg1 = tempImg1.getScaledInstance(50, 50,java.awt.Image.SCALE_SMOOTH);								
							lbl_Weapons[j].setIcon(new ImageIcon(newimg1));
						}
						
						for (int i = 0; i < lbl_Weapons.length; i++) {
							if (((60 * i) + 10) < ((60 * 12) + 10)) {
								lbl_Weapons[i].setBounds(((60 * i) + 10), 30, 50, 50);
							} else {
								lbl_Weapons[i].setBounds(((60 * (i - 12)) + 10), 90,
										50, 50);
							}
							gameView.getDwellingDPanel().add(lbl_Weapons[i]);
						}
					} else if (gameView.getDwellingDPanel().isVisible() == true)
						gameView.getDwellingDPanel().setVisible(false);

				}
			};
			
			
			ActionListener CArmorBtnListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {

					System.out.println("-----------Armor clicked-------------------");
					gameView.getDwellingDPanel().removeAll();
					if (gameView.getDwellingDPanel().isVisible() == false) {
						gameView.getDwellingDPanel().setVisible(true);
						gameView.getDwellingDPanel().setBorder(BorderFactory
								.createTitledBorder("Player Armors"));
						
						for(int j=0; j < playerList.get(buttonNumber).getPlayerCharacter().getArmor().size(); j++){
							Image tempImg1 = playerList.get(buttonNumber).getPlayerCharacter().getArmor().get(j).getImage();
							Image newimg1 = tempImg1.getScaledInstance(50, 50,java.awt.Image.SCALE_SMOOTH);								
							lbl_Armor[j].setIcon(new ImageIcon(newimg1));
						}
						
						for (int i = 0; i < lbl_Armor.length; i++) {
							if (((60 * i) + 10) < ((60 * 12) + 10)) {
								lbl_Armor[i].setBounds(((60 * i) + 10), 30, 50, 50);
							} else {
								lbl_Armor[i].setBounds(((60 * (i - 12)) + 10), 90,
										50, 50);
							}
							gameView.getDwellingDPanel().add(lbl_Armor[i]);
						}
					} else if (gameView.getDwellingDPanel().isVisible() == true)
						gameView.getDwellingDPanel().setVisible(false);

				}
			};
			
			
			ActionListener CActionChitsBtnListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {

					System.out.println("---------- Action Chits clicked-------------------");
					gameView.getDwellingDPanel().removeAll();
					if (gameView.getDwellingDPanel().isVisible() == false) {
						gameView.getDwellingDPanel().setVisible(true);
						gameView.getDwellingDPanel().setBorder(BorderFactory
								.createTitledBorder("Player Action Chits"));
						
						for(int j=0; j < playerList.get(buttonNumber).getPlayerCharacter().getActionChits().size(); j++){
							Image tempImg1 = playerList.get(buttonNumber).getPlayerCharacter().getActionChits().get(j).getImage();
							Image newimg1 = tempImg1.getScaledInstance(50, 50,java.awt.Image.SCALE_SMOOTH);								
							lbl_ActionChits[j].setIcon(new ImageIcon(newimg1));
						}
						
						
						for (int i = 0; i < lbl_ActionChits.length; i++) {
							if (((60 * i) + 10) < ((60 * 12) + 10)) {
								lbl_ActionChits[i].setBounds(((60 * i) + 10), 30, 50, 50);
							} else {
								lbl_ActionChits[i].setBounds(((60 * (i - 12)) + 10), 90,
										50, 50);
							}
							gameView.getDwellingDPanel().add(lbl_ActionChits[i]);
						}
					} else if (gameView.getDwellingDPanel().isVisible() == true)
						gameView.getDwellingDPanel().setVisible(false);

				}
			};

		// ************** CHARACTER ACTIONS ******************//
		// MOVE button action listener
		ActionListener moveListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("ACTION MOVE: "
						+ gameView.getActionField().getText());
				System.out.println("Player Name: "
						+ playerList.get(buttonNumber).getPlayerName());
				System.out.println("Player moves: " + m);
				if (!(gameView.getActionField().getText().equals(""))) {
					// Activity.move(playerList.get(buttonNumber)
					// .getPlayerCharacter(), gameView.getActionField()
					// .getText(), gameView.board);
					// send the move details to the server
					MRMessage mrm = new MRMessage(MRMessage.MOVE,
							myInfo.getClientName() + "-"
									+ myInfo.getCharacterName() + "-"
									+ myInfo.getCharacterLocation() + "-"
									+ gameView.getActionField().getText());

					ClientGUI.mr.sendMessage(mrm);

					gameView.setMessageText("");
					gameView.getActionField().setText("");
					
					// m = m-1;
					gameView.hexPanel.repaint(); // updating game board

				}
				/*
				 * else if (m <= 0){
				 * gameView.setMessageText("Not enough moves");
				 * gameView.getMoveBtn().setEnabled(false); }
				 */
				else if (gameView.getActionField().getText().equals("")) {
					gameView.setMessageText("Invalid Move");
				}

				//playerList.get(buttonNumber).getPlayerCharacter().setMoves(m);
				// Activity.move(playerList.get(buttonNumber).getPlayerCharacter(),
				// gameView.getActionField().getText(), gameView.board);
				// gameView.dp.updateBoard();

			}
		};

		// HIDE button action listener
		ActionListener hideListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("ACTION HIDE: "
						+ gameView.getActionField().getText());

				// System.out.println("Player Name: " +
				// playerList.get(buttonNumber).getPlayerName());
				if (gameView.dicePanel.ans > 0 && gameView.dicePanel.ans < 7) {

					MRMessage mrm = new MRMessage(MRMessage.ACTION_HIDE_REQ,
							myInfo.getClientName() + "-"
									+ myInfo.getCharacterName() + "-"
									+ myInfo.getCharacterLocation() + "-"
									+ gameView.dicePanel.ans);
					ClientGUI.mr.sendMessage(mrm);

					if(gameView.getMessage().getText().equals("You are now Unhidden")){
						gameView.setMessageText("You are now hidden");
						gameView.getHideBtn().setText("Unhide");
					}
					else{
						gameView.setMessageText("You are now Unhidden");
						gameView.getHideBtn().setText("Hide");
					}
					gameView.getActionField().setText("");
					
					// ///////////////////////////////////////////////
					// We need to do this on the server rather!!
					// ////////////////////////////////////////////
					// Activity.hide(playerList.get(buttonNumber)
					// .getPlayerCharacter(), gameView.dicePanel.ans);
					// We need to do this on the server rather!!
					// ///////////////////////////////////////////////
					System.out.println("Dice Value: " + gameView.dicePanel.ans);
					gameView.hexPanel.repaint();

				} else {
					gameView.setMessageText("Roll dice and select higest value");
				}
			}
		};

		// alert button action listener
		ActionListener alertListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("ACTION alert: "
						+ gameView.getActionField().getText());
				System.out.println("Player Name: " + model.getPlayerName());
				gameView.setMessageText("");
				String selectedWeapon = Activity.alertNew(model.getPlayerCharacter());

				// send alert details to the server
				// only if we have a selectedWeapon
				if (selectedWeapon.length() > 0) {
					MRMessage mrm = new MRMessage(MRMessage.ACTION_ALERT,
							myInfo.getClientName() + "-"
									+ myInfo.getCharacterName() + "-"
									+ myInfo.getCharacterLocation() + "-"
									+ selectedWeapon);
					ClientGUI.mr.sendMessage(mrm);
				}
			}
		};

		// SEARCH button action
		ActionListener searchListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("ACTION Search: "
						+ gameView.getActionField().getText());
				System.out.println("Player Name: "
						+ playerList.get(buttonNumber).getPlayerName());

				if (!(gameView.getActionField().getText().equals(""))
						&& (!(gameView.getActionField().getText()
								.equals("Invalid Action")))) {
					String rets = Activity.search(playerList.get(buttonNumber)
							.getPlayerCharacter(), gameView.getActionField()
							.getText(), gameView.dicePanel.ans, gameView.board);
					String []rs = rets.split("#");
				
					MRMessage mrm = new MRMessage(MRMessage.SEARCH,
							myInfo.getClientName() + "#"
									+ myInfo.getCharacterName() + "#"
									+ myInfo.getCharacterLocation() + "#"
									+ rs[0] + "#" + gameView.getActionField().getText()+ "#" + gameView.dicePanel.ans + "#" + rs[1]);
					ClientGUI.mr.sendMessage(mrm);
		
					gameView.setMessageText("");
				} else if (gameView.getActionField().getText().equals("")) {
					gameView.setMessageText("Invalid Action");
				}
			}
		};

		// BLOCK button action listener
		ActionListener blockListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				
				MRMessage mrm = new MRMessage(MRMessage.BLOCK,
						myInfo.getClientName() + "-"
								+ myInfo.getCharacterName() + "-"
								+ myInfo.getCharacterLocation());
				ClientGUI.mr.sendMessage(mrm);

				if(gameView.getMessage().getText().equals("You blocked everyone!")){
					gameView.setMessageText("You blocked everyone!");
				}
				else{
					gameView.setMessageText("You already blocked!");
				}
				gameView.getActionField().setText("");
			}
		};
		// Trade button action listener
		ActionListener tradeListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("I am Trading");
				String s = Trade.trade(playerList.get(buttonNumber).getPlayerCharacter(), gameView.board);
				
				if (s.length() > 0) {
					MRMessage mrm = new MRMessage(MRMessage.TRADE,
							myInfo.getClientName() + "-"
									+ myInfo.getCharacterName() + "-"
								 + s);
					ClientGUI.mr.sendMessage(mrm);
				}
			
			}
		};

		// Combat button action listener
		ActionListener combatListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("I am in Combat Mode");
				// CombatGUI combatView = new
				// CombatGUI(playerList.get(buttonNumber).getPlayerCharacter());
				Combat.combat(
						playerList.get(buttonNumber).getPlayerCharacter(),
						playerList.get(buttonNumber).getPlayerCharacter());
				gameView.setMessageText("");
				//updateOnRButton(buttonNumber);
			}
		};

		// Activate button action Listener
		ActionListener activateListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("ACTION activate: "
						+ gameView.getActionField().getText());
				System.out.println("Player Name: "
						+ playerList.get(buttonNumber).getPlayerName());
				gameView.setMessageText("");
				String s = Activity.activateSomething(model
						.getPlayerCharacter());
				// send Message to the server.

				if (s.length() > 0) {
					MRMessage mrm = new MRMessage(MRMessage.ACTIVATE,
							myInfo.getClientName() + "-"
									+ myInfo.getCharacterName() + "-"
									+ myInfo.getCharacterLocation() + "-" + s);
					ClientGUI.mr.sendMessage(mrm);
				}
			}
		};

		// REST button action Listener
		ActionListener restListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				System.out.println("ACTION Rest: "
						+ gameView.getActionField().getText());
				System.out.println("Player Name: "
						+ playerList.get(buttonNumber).getPlayerName());
				gameView.setMessageText("");
				
				String s = Activity.rest(playerList.get(buttonNumber).getPlayerCharacter());
				if(s.length() > 0) {
					MRMessage mrest = new MRMessage(MRMessage.REST, myInfo.getClientName()+"-"
							+myInfo.getCharacterName()+"-"
							+myInfo.getCharacterLocation()+"-"+s);
					ClientGUI.mr.sendMessage(mrest);
				}
			}
		};

		// Updating Player model with the information

		setPName(response[1]);
		setPCharacter(getCharObj(response[2]));
		setPStartingLocation(response[3]);
		setP_ID(Integer.parseInt(response[4]));

		// Creating new Instance of the Player and adding to the List
		Player p = new Player(counter, model.getPlayerName(),
				model.getPlayerCharacter());
		// playerList.add(counter, p);

		// Remove character from the selection list
		int i = view.getCharCombobox().getSelectedIndex();
		view.getCharCombobox().removeItemAt(i);

		// Update Player name text field
		view.getTextField().setText("");


		// gameView = new GameGUI(response[2] + " - " + response[1]);
		gameView = new GameGUI(myInfo.getClientName(), response[2]);
		sound.morningSong(); // Morning song (sound)

	
		view.getFrame().setVisible(false);

		// control the flow of remove button action listener
		if (cnt == 0) {
			gameView.getRemoveBtn().addActionListener(RemoveBtnActionListener);
			gameView.getAddBtn().addActionListener(AddBtnActionListener);
			gameView.getVRBtn().addActionListener(AddVRActionListener);
			gameView.getSetupBtn().addActionListener(SetupCardActionListener);
			gameView.getMoveBtn().addActionListener(moveListener);
			gameView.getHideBtn().addActionListener(hideListener);
			gameView.getAlertBtn().addActionListener(alertListener);
			gameView.getSearchBtn().addActionListener(searchListener);
			gameView.getTradeBtn().addActionListener(tradeListener);
			gameView.getCombatBtn().addActionListener(combatListener);
			gameView.getActivateBtn().addActionListener(activateListener);
			gameView.getRestBtn().addActionListener(restListener);
			
			gameView.getCWeaponsBtns().addActionListener(CWeaponsBtnListener);
			gameView.getCArmorBtns().addActionListener(CArmorBtnListener);
			gameView.getCActionChitsBtns().addActionListener(CActionChitsBtnListener);
			// Dwellings buttons listeners
			gameView.getDwellingBtn(0).addActionListener(ChappelBtnListener);
			gameView.getDwellingBtn(1).addActionListener(GuardBtnListener);
			gameView.getDwellingBtn(2).addActionListener(HouseBtnListener);
			gameView.getDwellingBtn(3).addActionListener(InnBtnListener);
			for(int k=0; k < 95; k++){
				gameView.getClearingBtns(k).addActionListener(ClearingListener);
			}
		}
		cnt++;
		counter++;

		// TODO Get the response from the server on PLAYER_DETAILS
		// message sent
		// Update the UI according to the reply
	}

	// Contains all the action listeners of the Game
	public void control() {

		// Action listener for Start Game button
		actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {

				// TODO Need to send Player Name, Character and Character
				// Location to the server here
				// ItemSelectable is = itemEvent.getItemSelectable();
				System.out.println("Hello, I am "
						+ view.getTextField().getText().toString()
						+ " and my character is "
						+ view.getCharCombobox().getSelectedItem().toString());
				;
				myInfo = new ClientInfo(view.getTextField().getText()
						.toString(), "", "", 0);
				myInfo.setCharacterLocation(locStr);
				myInfo.setCharacterName(view.getCharCombobox()
						.getSelectedItem().toString());
				MRMessage mrm = new MRMessage(MRMessage.PLAYER_DETAILS, view
						.getTextField().getText().toString()
						+ "#"
						+ view.getCharCombobox().getSelectedItem().toString()
						+ "#" + locStr);
				ClientGUI.mr.sendMessage(mrm);
				// Hide
				view.getFrame().setVisible(false);
				view.disposeScreen();

			}
		};

//		// Action listener for radio button
//		radioActionListener = new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//
//				// for (buttonNumber = 0; buttonNumber < (playerList.size() -
//				// 1); buttonNumber++) {
//				// if (radioBtn[buttonNumber] == e.getSource())
//				// break;
//				// }
//
//				// Update History panel on radio button click
//				switch (buttonNumber) {
//				case 0:
//					updateOnRButton(buttonNumber);
//					break;
//				case 1:
//					updateOnRButton(buttonNumber);
//					break;
//				case 2:
//					updateOnRButton(buttonNumber);
//					break;
//				case 3:
//					updateOnRButton(buttonNumber);
//					break;
//				case 4:
//					updateOnRButton(buttonNumber);
//					break;
//				case 5:
//					updateOnRButton(buttonNumber);
//					break;
//				case 6:
//					updateOnRButton(buttonNumber);
//					break;
//				case 7:
//					updateOnRButton(buttonNumber);
//					break;
//				}
//			}
//		};

		// Item Listener for Character Combobox selection
		ItemListener itemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				ItemSelectable is = itemEvent.getItemSelectable();
				charStr = selectedString(is);
				tempImg = view.getCharImage(charStr);
				Image newimg = tempImg.getScaledInstance(500, 400,
						java.awt.Image.SCALE_SMOOTH);
				view.getLabel().setIcon(new ImageIcon(newimg));
			}
		};

		// Item Listener for Location Combobox
		ItemListener locationItemListener = new ItemListener() {
			public void itemStateChanged(ItemEvent itemEvent) {
				ItemSelectable is = itemEvent.getItemSelectable();
				locStr = selectedString(is);
			}
		};

		// Binding Listeners with their components
		view.getButton().addActionListener(actionListener);
		view.getCharCombobox().addItemListener(itemListener);
		view.getLocationCombobox().addItemListener(locationItemListener);
	}

	// function to get selected string from Object
	static private String selectedString(ItemSelectable is) {
		Object selected[] = is.getSelectedObjects();
		return ((selected.length == 0) ? "null" : (String) selected[0]);
	}
}
