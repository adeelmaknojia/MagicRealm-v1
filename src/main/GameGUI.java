package main;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import messaging.MRMessage;
import client.ClientGUI;
import Tiles.*;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**********************************
 * This is the main class of a Java program to play a game based on hexagonal
 * tiles. The mechanism of handling hexes is in the file hexmech.java.
 ***********************************/

public class GameGUI {
	JButton btnSetupCard;
	JFrame frame;
	private int Numplayers = 8;
	private JLabel lblAdeel = new JLabel();
	private JLabel label = new JLabel();
	private JLabel lblCharacter = new JLabel();
	private JLabel label_1 = new JLabel();
	private JLabel label_2 = new JLabel();
	private JLabel lblLight = new JLabel();
	private JLabel lblCountername = new JLabel();
	JRadioButton[] selectionBtns = new JRadioButton[Numplayers];
	JLabel[] lblPlayers = new JLabel[Numplayers];
	private JButton btnAdd;
	private JButton btnRemove;
	private JButton btnVR;
	private JButton btnMove;
	private JButton btnHide;
	private JButton btnAlert;
	private JButton btnSearch;
	private JButton btnTrade;
	private JButton btnBlock;
	private JButton btnActivate;
	private JButton btnRest;
	private JButton btnCheat;
	private JLabel lblCimage = new JLabel();
	private JLabel lblCSA1 = new JLabel();
	private JLabel lblCSA2 = new JLabel();
	private JTextField txtAction;
	public JLabel label_Message;
	private JTable table;
	Point d;
	int highlight = 0;
	PlayerController p = new PlayerController();
	public RollDicePanel dicePanel;
	public DrawingPanel hexPanel = new DrawingPanel();
	public JPanel dwellingDisplayPanel = new JPanel();
	public JPanel DisplayPanel = new JPanel();
	private JButton[] btnDwelling = new JButton[4];
	private JButton[] btnClearings = new JButton[95];
	
	// Character info buttons
	private JButton btnCWeapons;
	private JButton btnCArmors;
	private JButton btnCActionChits;
	
	public ArrayList<JLabel[]> nClearing = new ArrayList<JLabel[]>();

	public JLabel[] lbl_InnImages = new JLabel[25];
	public JLabel[] lbl_GuardImages = new JLabel[25];
	public JLabel[] lbl_ChapelImages = new JLabel[25];
	public JLabel[] lbl_HouseImages = new JLabel[25];
	public JLabel[] lbl_Clearings = new JLabel[25];
	Image tempImg;
	Image newimg;

	public CheatModeGUI Cmview = new CheatModeGUI();
	public static boolean board1;

	JPanel selectionPanel;

	/**
	 * * Stores the title of the client frame
	 */
	private String title;

	public JFrame getFrame() {
		return frame;
	}

	public GameGUI(String title, String character) {
		this(title);
		setCCounterImage(character);

	}

	public GameGUI(String title) {
		this();
		this.title = title;
		// initGame();
		// createAndShowGUI();
	}
	

	public void setLabels() {
		if (PlayerController.model.getPlayerCharacter() != null) {
			lblCharacter.setText(PlayerController.model.getPlayerCharacter().getName());
			lblAdeel.setText(PlayerController.model.getPlayerName());
			label.setText(PlayerController.model.getPlayerCharacter().getGoldPoint()+"");
			label_1.setText(PlayerController.model.getPlayerCharacter().getFamePoint()+"");
			label_2.setText(PlayerController.model.getPlayerCharacter().getNotorityPoint()+"");		
			lblLight.setText(PlayerController.model.getPlayerCharacter().getWeight()+"");
			lblCountername.setText(PlayerController.model.getPlayerCharacter().getCounterName()+"");
			setCAdvantage1(PlayerController.model.getPlayerCharacter().getName(),
					PlayerController.model.getPlayerCharacter().getSpecialAdvantage(0));
			setCAdvantage2(PlayerController.model.getPlayerCharacter().getName(),
					PlayerController.model.getPlayerCharacter().getSpecialAdvantage(1));
		}
	}
	

	// GETTER
	public JButton getAddBtn() {return this.btnAdd;}
	public JButton getRemoveBtn() {return this.btnRemove;}
	public JButton getVRBtn() {return this.btnVR;}
	public JButton getSetupBtn() {return this.btnSetupCard;}
	public JButton getMoveBtn() {return this.btnMove;}
	public JButton getHideBtn() {return this.btnHide;}
	public JButton getAlertBtn() {return this.btnAlert;}
	public JButton getSearchBtn() {return this.btnSearch;}
	public JButton getTradeBtn() {return this.btnTrade;}
	public JButton getActivateBtn() {return this.btnActivate;}
	public JButton getRestBtn() {return this.btnRest;}
	public JButton getCheatBtn() {return this.btnCheat;}
	public JButton getCombatBtn() {return this.btnBlock;}
	public JButton getDwellingBtn(int i) {return this.btnDwelling[i];}
	public JLabel getCCounter() {return this.lblCimage;}
	public JTextField getActionField() {return this.txtAction;}
	public JLabel getMessage() {return this.label_Message;}
	public JRadioButton[] getselectionBtns() {return this.selectionBtns;}
	public JTable getTable() {return this.table;}
	public JPanel getDwellingDPanel() {return this.dwellingDisplayPanel;}
	public JPanel getDisplayPanel() {return this.DisplayPanel;}
	public JLabel[] getInnLabels() {return this.lbl_InnImages;}
	public JLabel[] getGuardLabels() {return this.lbl_GuardImages;}
	public JLabel[] getChapelLabels() {return this.lbl_ChapelImages;}
	public JLabel[] getHouseLabels() {return this.lbl_HouseImages;}
	public JLabel[] getClearingsLabels() {return this.lbl_Clearings;}
	public JButton getClearingBtns(int i){return this.btnClearings[i];}
	public ArrayList<JLabel[]> getClearingsLabelsArray(){return nClearing;}

	public JButton getCWeaponsBtns(){return this.btnCWeapons;}
	public JButton getCArmorBtns(){return this.btnCArmors;}
	public JButton getCActionChitsBtns(){return this.btnCActionChits;}

	
	public void setCCounterImage(String s) {
		this.lblCimage.setIcon(new ImageIcon(getCharCounter(s)
				.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
	}
	
	
	public void setCAdvantage1(String s, String a) {
		this.lblCSA1.setIcon(new ImageIcon(getCharSpecialAdvantage(s,a)
				.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
	}
	
	public void setCAdvantage2(String s, String a) {
		this.lblCSA2.setIcon(new ImageIcon(getCharSpecialAdvantage(s,a)
				.getScaledInstance(32, 32, java.awt.Image.SCALE_SMOOTH)));
	}

	// FUNCTIONS TO SET LABEL TEXT
	public void setLblAdeelText(String str) {
		this.lblAdeel.setText(str);
		;
	}

	public void setGoldText(String str) {
		this.label.setText(str);
	}

	public void setFameText(String str) {
		this.label_1.setText(str);
	}

	public void setNotereyText(String str) {
		this.label_2.setText(str);
	}

	public void setVulText(char str) {
		this.lblLight.setText(String.valueOf(str));
	}

	public void setCharacterText(String str) {
		this.lblCharacter.setText(str);
	}

	public void setCounterText(String str) {
		this.lblCountername.setText(str);
	}

	public void setRadiobtnText(int index, String str) {
		this.selectionBtns[index].setText(str);
	}

	public void setMessageText(String str) {
		this.label_Message.setText(str);
	}

	// DEFAULT CONSTRUCTOR
	public GameGUI() {
		// Initializing dwelling buttons
		for (int i = 0; i < 4; i++) {
			btnDwelling[i] = new JButton();
			btnDwelling[i].setOpaque(false);
			btnDwelling[i].setContentAreaFilled(false);
			btnDwelling[i].setBorderPainted(true);
			hexPanel.add(btnDwelling[i]);
		}

		// Initializing clearings buttons
		for (int i = 0; i < 95; i++) {
			btnClearings[i] = new JButton(""+i);
			btnClearings[i].setOpaque(false);
			btnClearings[i].setContentAreaFilled(false);
			btnClearings[i].setForeground(new Color(0, 0, 0, 0));
			btnClearings[i].setBorderPainted(false);
			hexPanel.add(btnClearings[i]);
		}

		for (int i = 0; i < 25; i++) {
			lbl_InnImages[i] = new JLabel("");
			lbl_GuardImages[i] = new JLabel("");
			lbl_ChapelImages[i] = new JLabel("");
			lbl_HouseImages[i] = new JLabel("");
			lbl_Clearings[i] = new JLabel("");
		}
		
		// Adeel
		if (board1 == true)
			initGame1();
		else
			initGame2();

		createAndShowGUI();

	}

	// constants and global variables
	final static Color COLOURBACK = Color.WHITE;
	final static Color COLOURGRID = Color.BLACK;
	final static Color COLOURONE = new Color(255, 255, 255, 200);
	final static Color COLOURONETXT = Color.BLUE;
	final static int EMPTY = 0;
	public final static int BSIZE = 6; // board size.
	final static int HEXSIZE = 220; // hex size in pixels
	final static int BORDERS = 15;
	final static int SCRSIZE = HEXSIZE * (BSIZE + 1) + BORDERS * 3; // screen
	// size
	// (vertical
	// dimension).
	final static int FrameWidth = 1300;
	final static int FrameHeight = 750;
	int tileP = 0;
	int initialSetup = 0;
	int setup = 0;
	ArrayList<Chit> pieces = new ArrayList<Chit>();
	ArrayList<Point> dw = new ArrayList<Point>();
	ArrayList<Point> monsters = new ArrayList<Point>(2);

	public static Tile[][] board = new Tile[BSIZE][BSIZE]; // 2d array for board
	Dwellings dwellings = new Dwellings();

	public static ArrayList<Chit> sChits = (new Chits()).getAllSChit();

	// obtain these random values from the server rather.
	// public static ArrayList<Chit> appChart = Chits
	// .randomizeAppearanceChart(sChits);
	// public static ArrayList<Chit> caveChit =
	// Chits.randomizeCaveChits(sChits);
	// public static ArrayList<Chit> mountainChit = Chits
	// .randomizeMountainChits(sChits);
	
	// public static ArrayList<Chit> woodsChit =
	// Chits.randomizeWoodsChits(sChits);
	public static ArrayList<Chit> appChart  = new ArrayList<Chit>();
	public static ArrayList<Chit> caveChit;
	public static ArrayList<Chit> mountainChit;
	public static ArrayList<Chit> valleyChit = Chits.randomizeValleyChits(sChits);
	// public static ArrayList<Chit> valleyChit;
	public static ArrayList<Chit> woodsChit;
	public static ArrayList<Integer> random;
	public static ArrayList<Chit> lostCastle = new ArrayList<Chit>();
	public static ArrayList<Chit> lostCity = new ArrayList<Chit>();
	public static TreasureSetUpCard tsc;
	ArrayList<Chit> cmChits = (new Chits()).getAllSChit();

	int counter = 0;
	int test = 0;
	private boolean tileLocationCalculated = false;

	// FIRST BOARD
	void initGame1() {

		hexmech.setXYasVertex(false); // RECOMMENDED: leave this as FALSE.

		hexmech.setHeight(HEXSIZE); // Either setHeight or setSize must be run
		// to initialize the hex
		hexmech.setBorders(BORDERS);

		board[0][0] = new EmptyTile();
		board[0][1] = new HighPass();//
		board[0][2] = new Cavern();//
		board[0][3] = new Mountain();//
		board[0][4] = new PineWoods();//
		board[0][5] = new EmptyTile();

		board[1][0] = new EvilValley();//
		board[1][1] = new Borderland();//
		board[1][2] = new BadValley();//
		board[1][3] = new Caves();//
		board[1][4] = new EmptyTile();
		board[1][5] = new EmptyTile();

		board[2][0] = new Cliff();//
		board[2][1] = new Ledges();//
		board[2][2] = new OakWoods();//
		board[2][3] = new MapleWoods();//
		board[2][4] = new Ruins();//
		board[2][5] = new LindenWoods();//

		board[3][0] = new EmptyTile();
		board[3][1] = new Crag();//
		board[3][2] = new DeepWoods();//
		board[3][3] = new NutWoods();//
		board[3][4] = new AwfulValley();//
		board[3][5] = new EmptyTile();

		board[4][0] = new EmptyTile();
		board[4][1] = new EmptyTile();
		board[4][2] = new DarkValley();
		board[4][3] = new CurstValley();
		board[4][4] = new EmptyTile();
		board[4][5] = new EmptyTile();

		board[5][0] = new EmptyTile();
		board[5][1] = new EmptyTile();
		board[5][2] = new EmptyTile();
		board[5][3] = new EmptyTile();
		board[5][4] = new EmptyTile();
		board[5][5] = new EmptyTile();

		// Initializing the board

		// Initializing the clearing paths

	}

	void initGame2() {

		hexmech.setXYasVertex(false); // RECOMMENDED: leave this as FALSE.

		hexmech.setHeight(HEXSIZE); // Either setHeight or setSize must be run
		// to initialize the hex
		hexmech.setBorders(BORDERS);

		board[0][0] = new EmptyTile();
		board[0][1] = new EmptyTile();
		board[0][2] = new Mountain();
		board[0][3] = new AwfulValley();
		board[0][4] = new EmptyTile();
		board[0][5] = new EmptyTile();

		board[1][0] = new HighPass();//
		board[1][1] = new EmptyTile();//
		board[1][2] = new Caves();//
		board[1][3] = new Cavern();//
		board[1][4] = new Crag();
		board[1][5] = new EmptyTile();//

		board[2][0] = new BadValley();//
		board[2][1] = new CurstValley();//
		board[2][2] = new EvilValley();//
		board[2][3] = new NutWoods();//
		board[2][4] = new Borderland();//
		board[2][5] = new Cliff();//

		board[3][0] = new Ledges();
		board[3][1] = new LindenWoods();//
		board[3][2] = new DeepWoods();//
		board[3][3] = new MapleWoods();//
		board[3][4] = new Ruins();//
		board[3][5] = new EmptyTile();//

		board[4][0] = new EmptyTile();
		board[4][1] = new EmptyTile();
		board[4][2] = new OakWoods();
		board[4][3] = new EmptyTile();
		board[4][4] = new PineWoods();
		board[4][5] = new DarkValley();

		board[5][0] = new EmptyTile();//
		board[5][1] = new EmptyTile();//
		board[5][2] = new EmptyTile();//
		board[5][3] = new EmptyTile();//
		board[5][4] = new EmptyTile();//
		board[5][5] = new EmptyTile();//
		// Initializing the board

		// Initializing the clearing paths

	}

	/**
	 * Function to freeze all controls of the client
	 * 
	 * */
	public void freeze() {
		btnAdd.setEnabled(false);
		btnRemove.setEnabled(false);
		// btnVR.setEnabled(false);
		btnMove.setEnabled(false);
		btnHide.setEnabled(false);
		btnAlert.setEnabled(false);
		btnSearch.setEnabled(false);
		btnTrade.setEnabled(false);
		//btnCombat.setEnabled(false);
		btnActivate.setEnabled(false);
		btnRest.setEnabled(false);
		dicePanel.disableRollButton();
		// btnCheat.setEnabled(false);
	}

	/**
	 * Function to freeze all controls of the client
	 * 
	 * */
	public void unfreeze() {
		btnAdd.setEnabled(true);
		btnRemove.setEnabled(true);
		// btnVR.setEnabled(true);
		btnMove.setEnabled(true);
		btnHide.setEnabled(true);
		btnAlert.setEnabled(true);
		btnSearch.setEnabled(true);
		btnTrade.setEnabled(true);
		//btnCombat.setEnabled(true);
		btnActivate.setEnabled(true);
		btnRest.setEnabled(true);
		dicePanel.enableRollButton();
		// btnCheat.setEnabled(true);
	}

	public void createAndShowGUI() {
		// JFrame.setDefaultLookAndFeelDecorated(true);
				frame = new JFrame("Magic Realm");
				
				// hexPanel = new DrawingPanel("Game Board");
				JPanel BtnPanel = new JPanel();
				// *******************************//
				JPanel BoardPanel = new JPanel();
				JPanel HistoryPanel = new JPanel();
				JPanel PlayerDetails = new JPanel();
				JPanel actionPanel = new JPanel();
				JPanel victoryPanel = new JPanel();
				JPanel gameActPanel = new JPanel();
				selectionPanel = new JPanel();
				dicePanel = new RollDicePanel();
				// ********************************//

				// Choose to lay out components manually on JFrame
				frame.getContentPane().setLayout(null);

				// ************************BUTTON PANEL
				// **********************************//
				// Adding button Panel to the Frame		
//				BtnPanel.setLocation(0, 680);
//				BtnPanel.setSize(543, 30);
				
				BtnPanel.setLayout(null);
				BtnPanel.setBounds(0, 680, 543, 30);
				ImageIcon SCimg = new ImageIcon("./images/actions/setup.gif");
				btnSetupCard = new JButton("Show Setup Card",SCimg);
				btnSetupCard.setHorizontalTextPosition(SwingConstants.RIGHT );
				btnSetupCard.setBounds(10, 0, 180,25);
				btnSetupCard.setContentAreaFilled(false);
				BtnPanel.add(btnSetupCard);
				frame.getContentPane().add(BtnPanel);

		// ************************HISTORY PANEL
		// **********************************//
		// Adding player History Panel to the Frame
		HistoryPanel.setLayout(null);
		HistoryPanel.setBackground(COLOURBACK);
		HistoryPanel.setBorder(BorderFactory
				.createTitledBorder("Player History Panel"));
		HistoryPanel.setLocation(543, 0);
		HistoryPanel.setSize((FrameWidth - 553), FrameHeight - 320);
		frame.getContentPane().add(HistoryPanel);

		// ********************** PLAYER DETAILS
		// PANEL*************************//
		PlayerDetails.setBorder(new LineBorder(new Color(51, 153, 255)));
		PlayerDetails.setBackground(Color.WHITE);
		PlayerDetails.setBounds(20, 20, 380, 179);
		HistoryPanel.add(PlayerDetails);
		PlayerDetails.setLayout(null);

		lblCharacter.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCharacter.setBounds(10, 5, 140, 25);
		PlayerDetails.add(lblCharacter);

		JLabel lblPlayer = new JLabel("Player: ");
		lblPlayer.setBounds(10, 39, 46, 14);
		PlayerDetails.add(lblPlayer);

		JLabel lblGold = new JLabel("Gold: ");
		lblGold.setBounds(10, 64, 46, 14);
		PlayerDetails.add(lblGold);

		JLabel lblFame = new JLabel("Fame: ");
		lblFame.setBounds(10, 89, 46, 14);
		PlayerDetails.add(lblFame);

		JLabel lblNotoriety = new JLabel("Notoriety: ");
		lblNotoriety.setBounds(10, 114, 57, 14);
		PlayerDetails.add(lblNotoriety);

		JLabel lblVulnerability = new JLabel("Vulnerability: ");
		lblVulnerability.setBounds(10, 139, 80, 14);
		PlayerDetails.add(lblVulnerability);

		Image tempImg = getCharCounter("Amazon");
		Image newimg = tempImg.getScaledInstance(50, 50,
				java.awt.Image.SCALE_SMOOTH);
		lblCimage = new JLabel("");
		lblCimage.setHorizontalAlignment(SwingConstants.CENTER);
		lblCimage.setBounds(165, 11, 50, 50);
		lblCimage.setIcon(new ImageIcon(newimg));
		PlayerDetails.add(lblCimage);

		lblAdeel.setBounds(53, 39, 56, 14);
		PlayerDetails.add(lblAdeel);

		label.setBounds(47, 64, 46, 14);
		PlayerDetails.add(label);

		label_1.setBounds(47, 89, 46, 14);
		PlayerDetails.add(label_1);

		label_2.setBounds(67, 114, 46, 14);
		PlayerDetails.add(label_2);

		lblLight.setBounds(92, 139, 56, 14);
		PlayerDetails.add(lblLight);

		lblCountername.setHorizontalAlignment(SwingConstants.CENTER);
		lblCountername.setBounds(110, 70, 150, 14);
		PlayerDetails.add(lblCountername);
		
		
		ImageIcon CWimg = new ImageIcon("./images/actions/weapon.gif");
		btnCWeapons = new JButton("Weapons", CWimg);
		btnCWeapons.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCWeapons.setVerticalAlignment(SwingConstants.TOP ) ;
		btnCWeapons.setHorizontalTextPosition(SwingConstants.CENTER );
		btnCWeapons.setBounds(280, 5, 100, 55);
		btnCWeapons.setContentAreaFilled(false);
		btnCWeapons.setFont(new Font("Arial", Font.PLAIN, 11));
		PlayerDetails.add(btnCWeapons);
		
		
		ImageIcon AMimg = new ImageIcon("./images/actions/armor.gif");
		btnCArmors = new JButton("Armors",AMimg);
		btnCArmors.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCArmors.setVerticalAlignment(SwingConstants.TOP ) ;
		btnCArmors.setHorizontalTextPosition(SwingConstants.CENTER ) ;
		btnCArmors.setBounds(280,60, 100, 55);
		btnCArmors.setContentAreaFilled(false);
		btnCArmors.setFont(new Font("Arial", Font.PLAIN, 11));
		PlayerDetails.add(btnCArmors);
		
		ImageIcon ACimg = new ImageIcon("./images/actions/chits.gif");
		btnCActionChits = new JButton("Action Chits",ACimg);
		btnCActionChits.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnCActionChits.setVerticalAlignment(SwingConstants.TOP ) ;
		btnCActionChits.setHorizontalTextPosition(SwingConstants.CENTER );
		btnCActionChits.setBounds(280,115, 100, 55);
		btnCActionChits.setContentAreaFilled(false);
		btnCActionChits.setFont(new Font("Arial", Font.PLAIN, 11));
		PlayerDetails.add(btnCActionChits);
		
		
		// Character Badges 
		
		Image SA1Img = getCharSpecialAdvantage("Amazon","AIM");
		lblCSA1 = new JLabel("");
		lblCSA1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCSA1.setBounds(240, 75, 32, 32);
		lblCSA1.setIcon(new ImageIcon(SA1Img));
		PlayerDetails.add(lblCSA1);
		
		
		Image SA2Img = getCharSpecialAdvantage("Amazon","STAMINA");
		lblCSA2 = new JLabel("");
		lblCSA2.setHorizontalAlignment(SwingConstants.CENTER);
		lblCSA2.setBounds(240, 110, 32, 32);
		lblCSA2.setIcon(new ImageIcon(SA1Img));
		PlayerDetails.add(lblCSA2);
		
		

		// ************************** Action Panel ************************//
		actionPanel.setBorder(new LineBorder(new Color(51, 153, 255)));
		actionPanel.setBackground(Color.WHITE);
		actionPanel.setBounds(20, 200, 720, 60);
		HistoryPanel.add(actionPanel);
		actionPanel.setLayout(null);


		ImageIcon Himg = new ImageIcon("./images/actions/hide.gif");
		btnHide = new JButton("Unhide",Himg);
		btnHide.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnHide.setVerticalAlignment      ( SwingConstants.TOP ) ;
		btnHide.setHorizontalTextPosition ( SwingConstants.CENTER ) ;
		btnHide.setBounds(0, 0, 73, 60);
		btnHide.setContentAreaFilled(false);
		actionPanel.add(btnHide);

		ImageIcon Aimg = new ImageIcon("./images/actions/alert.gif");
		btnAlert = new JButton("Alert",Aimg);
		btnAlert.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnAlert.setVerticalAlignment      ( SwingConstants.TOP ) ;
		btnAlert.setHorizontalTextPosition ( SwingConstants.CENTER ) ;
		btnAlert.setBounds(73, 0, 70, 60);
		btnAlert.setContentAreaFilled(false);
		actionPanel.add(btnAlert);

		ImageIcon Simg = new ImageIcon("./images/actions/search.gif");
		btnSearch = new JButton("Search",Simg);
		btnSearch.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnSearch.setVerticalAlignment ( SwingConstants.TOP ) ;
		btnSearch.setHorizontalTextPosition ( SwingConstants.CENTER ) ;
		btnSearch.setBounds(143, 0, 80, 60);
		btnSearch.setContentAreaFilled(false);
		actionPanel.add(btnSearch);
		
		ImageIcon Mimg = new ImageIcon("./images/actions/move.gif");
		btnMove = new JButton("Move", Mimg);
		btnMove.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnMove.setVerticalAlignment ( SwingConstants.TOP ) ;
		btnMove.setHorizontalTextPosition ( SwingConstants.CENTER ) ;
		btnMove.setBounds(223, 0, 70, 60);
		btnMove.setContentAreaFilled(false);
		actionPanel.add(btnMove);

		ImageIcon Timg = new ImageIcon("./images/actions/trade.gif");
		btnTrade = new JButton("Trade", Timg);
		btnTrade.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnTrade.setVerticalAlignment ( SwingConstants.TOP );
		btnTrade.setHorizontalTextPosition ( SwingConstants.CENTER );
		btnTrade.setBounds(293, 0, 70, 60);
		btnTrade.setContentAreaFilled(false);
		actionPanel.add(btnTrade);

		ImageIcon Rimg = new ImageIcon("./images/actions/rest.gif");
		btnRest = new JButton("Rest",Rimg);
		btnRest.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnRest.setVerticalAlignment(SwingConstants.TOP ) ;
		btnRest.setHorizontalTextPosition(SwingConstants.CENTER ) ;
		btnRest.setBounds(363, 0, 70, 60);
		btnRest.setContentAreaFilled(false);
		actionPanel.add(btnRest);
		
		ImageIcon Acimg = new ImageIcon("./images/actions/activate.gif");
		btnActivate = new JButton("Activate",Acimg);
		btnActivate.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnActivate.setVerticalAlignment(SwingConstants.TOP ) ;
		btnActivate.setHorizontalTextPosition(SwingConstants.CENTER ) ;
		btnActivate.setBounds(433, 0, 80, 60);
		btnActivate.setContentAreaFilled(false);
		actionPanel.add(btnActivate);
		
		btnBlock = new JButton("Block");
		btnBlock.setBounds(513, 0, 80, 60);
		actionPanel.add(btnBlock);
	

		// **************** VICTORY REQUIREMENT PANEL ************************//
		victoryPanel.setBorder(new LineBorder(new Color(51, 153, 255)));
		victoryPanel.setBackground(Color.WHITE);
		victoryPanel.setBounds(20, 274, 400, 138);
		HistoryPanel.add(victoryPanel);
		victoryPanel.setLayout(null);

		btnVR = new JButton("Setup Victory Requirements");
		btnVR.setBounds(0, 88, 398, 50);
		victoryPanel.add(btnVR);
		final DefaultTableModel model = new DefaultTableModel() {
			

			 int g = PlayerController.model.getPlayerCharacter().getStartingGold();
			 int n = 0;
			 int f = 0;

			 public Class<?> getColumnClass(int columnIndex) {
					switch (columnIndex) {
					case 0:
						return String.class;
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
						return Integer.class;
					}
					return super.getColumnClass(columnIndex);
				}


				@Override
				public Object getValueAt(int row, int column) {
					super.setValueAt("Gold", 0, 0);
					super.setValueAt(30, 0, 2);
					super.setValueAt(g, 0, 5);
					if(row == 1){
						super.setValueAt("Notoriety", 1, 0);
						super.setValueAt(20, 1, 2);
						super.setValueAt(n, 1, 5);

					}
					if(row == 2){
						super.setValueAt("Fame", 2, 0);
						super.setValueAt(10, 2, 2);
						super.setValueAt(f, 2, 5);
						//super.setValueAt("Total", 3, 0);
					}
					if(row == 3){
						super.setValueAt("Total", 3, 0);

					}
					if (column == 6) {
						Integer r = (Integer) getValueAt(row, 4);
						Integer o = (Integer) getValueAt(row, 5);
						// Integer r = (Integer) getValueAt(row, 5);
						//  Integer o = (Integer) getValueAt(row, 5);

						if (r != null && o != null) {
							return r + o;
						}
						else {
							return 0;
						}
					}

					if (column == 3) {
						Integer p = (Integer) getValueAt(row, 1);
						Integer x = (Integer) getValueAt(row, 2);
						if (p != null && x != null ) {
							return p * x;
						}
						else {
							return 0;
						}
					}

					return super.getValueAt(row, column);
				}

				@Override
				public boolean isCellEditable(int row, int column) {
					return column == 1 || column == 2 || column == 3 || column == 4 || column == 5;
				}

				@Override
				public void setValueAt(Object aValue, int row, int column) {
					if(column > 0 || row > 0){
						super.setValueAt(aValue, row, column);
						fireTableCellUpdated(row, 6);
						fireTableCellUpdated(row, 3);
					}
				}

				@Override
				public String getColumnName(int column) {
					switch (column) {
					case 0:
						return "Category";
					case 1:
						return "Points";
					case 2:
						return "X";
					case 3:
						return "Need";
					case 4:
						return "Record";
					case 5:
						return "Own";
					case 6:
						return "Total";      
					}
					return super.getColumnName(column);
				}


				@Override
				public int getColumnCount() {
					return 7;
				}
			};

		table = new JTable(model);
		table.setFillsViewportHeight(true);
		model.addRow(new Vector());
		model.addRow(new Vector());
		model.addRow(new Vector());
		// model.addRow(new Vector());
		table.setBackground(SystemColor.menu);
		table.getColumnModel().getColumn(0).setResizable(false);
		table.setBounds(10, 58, 370, 89);
		// Add the table to a scrolling pane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(400, 250);
		victoryPanel.add(scrollPane, BorderLayout.CENTER);

		// ******************** Game Action Panel*************************//

		gameActPanel.setBorder(BorderFactory
				.createTitledBorder("Record Action"));
		gameActPanel.setBackground(Color.WHITE);
		gameActPanel.setBounds(400, 15, 342, 185);

		HistoryPanel.add(gameActPanel);
		gameActPanel.setLayout(null);

		JLabel label_Note = new JLabel(
				"Note: DV4 for moving character to dark valley clearing 4");
		label_Note.setBounds(10, 30, 500, 30);
		gameActPanel.add(label_Note);

		JLabel label_A = new JLabel("ACTION: ");
		label_A.setBounds(10, 80, 80, 15);
		gameActPanel.add(label_A);

		txtAction = new JTextField();
		txtAction.setText("");
		txtAction.setBounds(65, 80, 180, 20);
		gameActPanel.add(txtAction);

		label_Message = new JLabel("");
		label_Message.setBounds(10, 30, 500, 200);
		gameActPanel.add(label_Message);

		dicePanel.setBorder(BorderFactory.createTitledBorder("Dice Panel"));
		dicePanel.setBackground(Color.WHITE);
		dicePanel.setBounds(420, 265, 310, 150);
		HistoryPanel.add(dicePanel);
		dicePanel.setLayout(null);

		// **************** PLAYER SELECTION PANEL ************************//
		selectionPanel.setBorder(BorderFactory
				.createTitledBorder("Player Selection Panel"));
		selectionPanel.setBackground(Color.WHITE);
		selectionPanel.setLayout(null);
		selectionPanel.setPreferredSize(new Dimension(400,(FrameWidth - 553)));
		
		JScrollPane scrollpane = new JScrollPane(selectionPanel);
		scrollpane.setBounds(543, 430, (FrameWidth - 553), 100);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setVisible(true);
		frame.getContentPane().add(scrollpane);

		//ButtonGroup operations = new ButtonGroup();
		// selectionBtns = new JRadioButton[Numplayers];
		/*
		 * for (int i = 0; i < Numplayers; i++) { selectionBtns[i] = new
		 * JRadioButton(); selectionBtns[i].setLocation(20, 20 + i * 20);
		 * selectionBtns[i].setSize(150, 20);
		 * selectionBtns[i].setVisible(false);
		 * selectionPanel.add(selectionBtns[i]);
		 * operations.add(selectionBtns[i]); //
		 * selectionBtns[i].addActionListener(this); }
		 */

		btnAdd = new JButton("Add Player");
		btnAdd.setBounds(200, 20, 150, 40);
		// selectionPanel.add(btnAdd);

		btnRemove = new JButton("Remove Player");
		btnRemove.setBounds(400, 20, 150, 40);
		// selectionPanel.add(btnRemove);

		// ********************* DWELLINGS DISPLAY PANEL********************//

		dwellingDisplayPanel.setVisible(false);
		dwellingDisplayPanel.setBorder(BorderFactory
				.createTitledBorder("Dwellings Display Panel"));
		dwellingDisplayPanel.setBackground(new Color(176,224,230));
		dwellingDisplayPanel.setBounds(543, 530, (FrameWidth - 553), 150);
		frame.getContentPane().add(dwellingDisplayPanel);
		dwellingDisplayPanel.setLayout(null);

		// **********************************BOARD
		// PANEL*************************************//
		BoardPanel.setLayout(null);
		BoardPanel.setBackground(COLOURBACK);
		BoardPanel.setBorder(BorderFactory.createTitledBorder("Adeel"));
		BoardPanel.setLocation(0, 0);
		BoardPanel.setSize(543, 679);
		hexPanel.setPreferredSize(new Dimension((int) (SCRSIZE / 1.5),
				(int) (SCRSIZE / 1.2)));
		BoardPanel.add(hexPanel);
		JScrollPane scrollPane1 = new JScrollPane(hexPanel);
		scrollPane1
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane1
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane1.setBounds(0, 0, 543, 679);
		BoardPanel.add(scrollPane1);
		frame.getContentPane().add(BoardPanel);

		// frame.getContentPane().add(hexPanel);

		// content.add(board);
		// for hexes in the FLAT orientation, the height of a 10x10 grid is
		// 1.1764 * the width. (from h / (s+t))
		frame.setSize(1300, 750);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		setLabels();
		frame.setVisible(true);
		freeze();
	}

	public class DrawingPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public DrawingPanel() {
			setBackground(COLOURBACK);

			// Make a border around the outside with the given title
			// setBorder(BorderFactory.createTitledBorder(title));

			MyMouseListener ml = new MyMouseListener();
			addMouseListener(ml);

		}

		// Initializing board 1 clearings
		public void boardInit() {

			board[0][1].getClearing(6).setAdjacentClearing(
					board[1][0].getClearing(5));
			board[0][1].getClearing(3).setAdjacentClearing(
					board[0][2].getClearing(5));
			board[0][1].getClearing(2).setAdjacentClearing(
					board[1][1].getClearing(1));

			board[0][2].getClearing(5).setAdjacentClearing(
					board[0][1].getClearing(3));
			board[0][2].getClearing(2).setAdjacentClearing(
					board[1][1].getClearing(5));
			board[0][2].getClearing(1).setAdjacentClearing(
					board[1][2].getClearing(4));

			board[0][3].getClearing(5).setAdjacentClearing(
					board[1][2].getClearing(4));
			board[0][3].getClearing(2).setAdjacentClearing(
					board[0][4].getClearing(4));

			board[0][4].getClearing(4).setAdjacentClearing(
					board[0][3].getClearing(2));
			board[0][4].getClearing(5).setAdjacentClearing(
					board[1][3].getClearing(1));

			board[1][0].getClearing(2).setAdjacentClearing(
					board[2][0].getClearing(1));
			board[1][0].getClearing(5).setAdjacentClearing(
					board[0][1].getClearing(6));
			board[1][0].getClearing(4).setAdjacentClearing(
					board[2][1].getClearing(2));
			board[1][0].getClearing(4).setAdjacentClearing(
					board[1][1].getClearing(2));

			board[1][1].getClearing(1).setAdjacentClearing(
					board[0][1].getClearing(2));
			board[1][1].getClearing(1).setAdjacentClearing(
					board[1][2].getClearing(5));
			board[1][1].getClearing(2).setAdjacentClearing(
					board[1][0].getClearing(4));
			board[1][1].getClearing(2).setAdjacentClearing(
					board[2][2].getClearing(2));
			board[1][1].getClearing(4).setAdjacentClearing(
					board[2][1].getClearing(4));
			board[1][1].getClearing(5).setAdjacentClearing(
					board[0][2].getClearing(2));

			board[1][2].getClearing(1).setAdjacentClearing(
					board[2][2].getClearing(5));
			board[1][2].getClearing(2).setAdjacentClearing(
					board[1][3].getClearing(2));
			board[1][2].getClearing(4).setAdjacentClearing(
					board[0][2].getClearing(1));
			board[1][2].getClearing(4).setAdjacentClearing(
					board[0][3].getClearing(5));
			board[1][2].getClearing(5).setAdjacentClearing(
					board[1][1].getClearing(1));

			board[1][3].getClearing(1).setAdjacentClearing(
					board[0][4].getClearing(5));
			board[1][3].getClearing(2).setAdjacentClearing(
					board[1][2].getClearing(2));
			board[1][3].getClearing(5).setAdjacentClearing(
					board[2][3].getClearing(4));

			board[2][0].getClearing(1).setAdjacentClearing(
					board[1][0].getClearing(2));
			board[2][0].getClearing(2).setAdjacentClearing(
					board[2][1].getClearing(3));

			board[2][1].getClearing(2).setAdjacentClearing(
					board[1][0].getClearing(4));
			board[2][1].getClearing(3).setAdjacentClearing(
					board[2][0].getClearing(2));
			board[2][1].getClearing(4).setAdjacentClearing(
					board[1][1].getClearing(4));
			board[2][1].getClearing(5).setAdjacentClearing(
					board[2][2].getClearing(2));

			board[2][2].getClearing(2).setAdjacentClearing(
					board[1][1].getClearing(2));
			board[2][2].getClearing(2).setAdjacentClearing(
					board[2][1].getClearing(5));
			board[2][2].getClearing(4).setAdjacentClearing(
					board[3][2].getClearing(1));
			board[2][2].getClearing(5).setAdjacentClearing(
					board[1][2].getClearing(1));
			board[2][2].getClearing(5).setAdjacentClearing(
					board[2][3].getClearing(5));

			board[2][3].getClearing(2).setAdjacentClearing(
					board[2][4].getClearing(5));
			board[2][3].getClearing(2).setAdjacentClearing(
					board[3][3].getClearing(5));
			board[2][3].getClearing(4).setAdjacentClearing(
					board[1][3].getClearing(5));
			board[2][3].getClearing(5).setAdjacentClearing(
					board[2][2].getClearing(5));
			board[2][3].getClearing(5).setAdjacentClearing(
					board[3][2].getClearing(5));

			board[2][4].getClearing(1).setAdjacentClearing(
					board[3][3].getClearing(4));
			board[2][4].getClearing(2).setAdjacentClearing(
					board[3][4].getClearing(1));
			board[2][4].getClearing(2).setAdjacentClearing(
					board[2][5].getClearing(4));
			board[2][4].getClearing(5).setAdjacentClearing(
					board[2][3].getClearing(2));

			board[2][5].getClearing(4).setAdjacentClearing(
					board[2][4].getClearing(2));
			board[2][5].getClearing(5).setAdjacentClearing(
					board[3][4].getClearing(2));

			board[3][1].getClearing(2).setAdjacentClearing(
					board[3][2].getClearing(1));

			board[3][2].getClearing(1).setAdjacentClearing(
					board[2][2].getClearing(4));
			board[3][2].getClearing(1).setAdjacentClearing(
					board[3][1].getClearing(2));
			board[3][2].getClearing(2).setAdjacentClearing(
					board[4][2].getClearing(5));
			board[3][2].getClearing(2).setAdjacentClearing(
					board[4][3].getClearing(2));
			board[3][2].getClearing(5).setAdjacentClearing(
					board[2][3].getClearing(5));

			board[3][3].getClearing(2).setAdjacentClearing(
					board[3][4].getClearing(5));
			board[3][3].getClearing(4).setAdjacentClearing(
					board[2][4].getClearing(1));
			board[3][3].getClearing(5).setAdjacentClearing(
					board[2][3].getClearing(2));
			board[3][3].getClearing(5).setAdjacentClearing(
					board[4][3].getClearing(4));

			board[3][4].getClearing(1).setAdjacentClearing(
					board[2][4].getClearing(2));
			board[3][4].getClearing(2).setAdjacentClearing(
					board[2][5].getClearing(5));
			board[3][4].getClearing(5).setAdjacentClearing(
					board[3][3].getClearing(2));

			board[4][2].getClearing(1).setAdjacentClearing(
					board[4][3].getClearing(1));
			board[4][2].getClearing(5).setAdjacentClearing(
					board[3][2].getClearing(2));

			board[4][3].getClearing(1).setAdjacentClearing(
					board[4][2].getClearing(1));
			board[4][3].getClearing(2).setAdjacentClearing(
					board[3][2].getClearing(2));
			board[4][3].getClearing(4).setAdjacentClearing(
					board[3][3].getClearing(5));

		}

		// For Board 2
		public void boardInit2() {

			board[0][2].getClearing(4).setAdjacentClearing(
					board[0][3].getClearing(4));

			board[0][3].getClearing(4).setAdjacentClearing(
					board[0][2].getClearing(4));
			board[0][3].getClearing(4).setAdjacentClearing(
					board[1][2].getClearing(5));

			board[1][0].getClearing(2).setAdjacentClearing(
					board[2][0].getClearing(1));
			board[1][0].getClearing(3).setAdjacentClearing(
					board[2][1].getClearing(4));

			board[1][2].getClearing(1).setAdjacentClearing(
					board[2][2].getClearing(5));
			board[1][2].getClearing(2).setAdjacentClearing(
					board[1][3].getClearing(2));
			board[1][2].getClearing(5).setAdjacentClearing(
					board[0][3].getClearing(4));

			board[1][3].getClearing(1).setAdjacentClearing(
					board[2][4].getClearing(1));
			board[1][3].getClearing(2).setAdjacentClearing(
					board[2][3].getClearing(5));
			board[1][3].getClearing(5).setAdjacentClearing(
					board[1][2].getClearing(2));

			board[1][4].getClearing(2).setAdjacentClearing(
					board[2][4].getClearing(5));

			board[2][0].getClearing(1).setAdjacentClearing(
					board[1][0].getClearing(2));
			board[2][0].getClearing(4).setAdjacentClearing(
					board[3][0].getClearing(2));

			board[2][1].getClearing(1).setAdjacentClearing(
					board[3][0].getClearing(4));
			board[2][1].getClearing(2).setAdjacentClearing(
					board[2][0].getClearing(5));
			board[2][1].getClearing(4).setAdjacentClearing(
					board[1][0].getClearing(3));
			board[2][1].getClearing(4).setAdjacentClearing(
					board[2][2].getClearing(1));
			board[2][1].getClearing(5).setAdjacentClearing(
					board[3][1].getClearing(5));

			board[2][2].getClearing(1).setAdjacentClearing(
					board[2][1].getClearing(4));
			board[2][2].getClearing(2).setAdjacentClearing(
					board[3][1].getClearing(5));
			board[2][2].getClearing(4).setAdjacentClearing(
					board[3][2].getClearing(1));
			board[2][2].getClearing(4).setAdjacentClearing(
					board[2][3].getClearing(5));
			board[2][2].getClearing(5).setAdjacentClearing(
					board[1][2].getClearing(1));

			board[2][3].getClearing(2).setAdjacentClearing(
					board[3][2].getClearing(1));
			board[2][3].getClearing(2).setAdjacentClearing(
					board[3][3].getClearing(2));
			board[2][3].getClearing(4).setAdjacentClearing(
					board[2][4].getClearing(2));
			board[2][3].getClearing(5).setAdjacentClearing(
					board[2][2].getClearing(4));
			board[2][3].getClearing(5).setAdjacentClearing(
					board[1][3].getClearing(2));

			board[2][4].getClearing(1).setAdjacentClearing(
					board[1][3].getClearing(1));
			board[2][4].getClearing(1).setAdjacentClearing(
					board[2][5].getClearing(4));
			board[2][4].getClearing(2).setAdjacentClearing(
					board[2][3].getClearing(4));
			board[2][4].getClearing(2).setAdjacentClearing(
					board[3][4].getClearing(1));
			board[2][4].getClearing(4).setAdjacentClearing(
					board[3][3].getClearing(2));
			board[2][4].getClearing(5).setAdjacentClearing(
					board[1][4].getClearing(2));

			board[2][5].getClearing(4).setAdjacentClearing(
					board[2][4].getClearing(1));
			board[2][5].getClearing(5).setAdjacentClearing(
					board[3][4].getClearing(2));

			board[3][0].getClearing(2).setAdjacentClearing(
					board[2][0].getClearing(4));
			board[3][0].getClearing(4).setAdjacentClearing(
					board[2][1].getClearing(1));
			board[3][0].getClearing(5).setAdjacentClearing(
					board[3][1].getClearing(2));

			board[3][1].getClearing(2).setAdjacentClearing(
					board[3][0].getClearing(5));
			board[3][1].getClearing(2).setAdjacentClearing(
					board[4][2].getClearing(5));
			board[3][1].getClearing(4).setAdjacentClearing(
					board[3][2].getClearing(2));
			board[3][1].getClearing(5).setAdjacentClearing(
					board[2][1].getClearing(5));
			board[3][1].getClearing(5).setAdjacentClearing(
					board[2][2].getClearing(2));

			board[3][2].getClearing(1).setAdjacentClearing(
					board[2][2].getClearing(4));
			board[3][2].getClearing(1).setAdjacentClearing(
					board[2][3].getClearing(2));
			board[3][2].getClearing(2).setAdjacentClearing(
					board[3][1].getClearing(4));
			board[3][2].getClearing(2).setAdjacentClearing(
					board[4][2].getClearing(5));
			board[3][2].getClearing(5).setAdjacentClearing(
					board[3][3].getClearing(4));

			board[3][3].getClearing(2).setAdjacentClearing(
					board[2][3].getClearing(2));
			board[3][3].getClearing(2).setAdjacentClearing(
					board[2][4].getClearing(4));
			board[3][3].getClearing(4).setAdjacentClearing(
					board[3][2].getClearing(5));
			board[3][3].getClearing(5).setAdjacentClearing(
					board[4][4].getClearing(5));
			board[3][3].getClearing(5).setAdjacentClearing(
					board[3][4].getClearing(2));

			board[3][4].getClearing(1).setAdjacentClearing(
					board[2][4].getClearing(2));
			board[3][4].getClearing(2).setAdjacentClearing(
					board[3][3].getClearing(5));
			board[3][4].getClearing(2).setAdjacentClearing(
					board[4][4].getClearing(5));
			board[3][4].getClearing(3).setAdjacentClearing(
					board[4][5].getClearing(1));
			board[3][4].getClearing(5).setAdjacentClearing(
					board[2][5].getClearing(5));

			board[4][2].getClearing(5).setAdjacentClearing(
					board[3][1].getClearing(2));
			board[4][2].getClearing(5).setAdjacentClearing(
					board[3][2].getClearing(2));

			board[4][4].getClearing(5).setAdjacentClearing(
					board[3][3].getClearing(5));
			board[4][4].getClearing(5).setAdjacentClearing(
					board[3][4].getClearing(2));

			board[4][5].getClearing(1).setAdjacentClearing(
					board[3][4].getClearing(3));
			board[4][5].getClearing(5).setAdjacentClearing(
					board[4][4].getClearing(4));
		}

		public ArrayList<Clearing> buildPathway() {
			ArrayList<Clearing> path = new ArrayList<Clearing>();
			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					if (board[i][j].getName() != null) {
						for (int k = 0; k < board[i][j].getAllClearings().length; k++) {
							if (board[i][j].getAllClearings()[k] != null) {
								path.add(board[i][j].getAllClearings()[k]);
								System.out
										.print(board[i][j].getAllClearings()[k]
												.getTileName()
												+ board[i][j].getAllClearings()[k]
														.getClearingNum() + " ");
							}
						}
					}

				}
			}
			System.out.println("Done Building Path!");
			System.out.println("Size:" + path.size());
			return path;
		}

		public void boardSetup() {
			lostCastle = Chits.makeLostCastle(appChart);
			lostCity = Chits.makeLostCity(appChart);
			tsc = new TreasureSetUpCard(lostCastle, lostCity);
			ArrayList<Point> cave = new ArrayList<Point>();
			ArrayList<Point> mountain = new ArrayList<Point>();
			ArrayList<Point> valley = new ArrayList<Point>();
			ArrayList<Point> woods = new ArrayList<Point>();
			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					if (board[i][j].getTileType() == 'c') {
						cave.add(new Point(i, j));
					}
					if (board[i][j].getTileType() == 'm') {
						mountain.add(new Point(i, j));
					}
					if (board[i][j].getTileType() == 'v') {
						valley.add(new Point(i, j));
					}
					if (board[i][j].getTileType() == 'w') {
						woods.add(new Point(i, j));
					}

				}
			}
			ArrayList<Point> temp = new ArrayList<Point>();

			temp.addAll(cave);
			int rCount = 0;

			for (int i = 0; i < caveChit.size() / 2; i++) {
				// Random r = new Random();
				// int ran = r.nextInt(temp.size());
				int ran = random.get(rCount++);
				caveChit.get(i)
						.setChitLocation(
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().x + 20,
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().y + 30);
				board[temp.get(ran).x][temp.get(ran).y]
						.addChit(caveChit.get(i));
				pieces.add(caveChit.get(i));
				System.out.println("Adding " + caveChit.get(i).getChitName() + "-" + caveChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
				// System.out.println("deletion: " +
				// temp.remove(ran).getName());
				temp.remove(ran);

			}
			temp.addAll(cave);
			for (int i = caveChit.size() / 2; i < caveChit.size(); i++) {
				// Random r = new Random();
				// int ran = r.nextInt(temp.size());
				int ran = random.get(rCount++);
				//System.out.println("Cave2 : Ran is" + ran);
				caveChit.get(i)
						.setChitLocation(
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().x + 20,
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().y + 30);
				board[temp.get(ran).x][temp.get(ran).y]
						.addChit(caveChit.get(i));
				System.out.println("Adding " + caveChit.get(i).getChitName() + "-" + caveChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
				pieces.add(caveChit.get(i));
				// System.out.println("deletion: " +
				// temp.remove(ran).getName());
				temp.remove(ran);

			}

			temp.addAll(mountain);
			for (int i = 0; i < mountainChit.size() / 2; i++) {
				// Random r = new Random();
				// int ran = r.nextInt(temp.size());
				int ran = random.get(rCount++);
				//System.out.println("Mountain : Ran is" + ran);
				mountainChit
						.get(i)
						.setChitLocation(
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().x + 20,
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().y + 30);
				board[temp.get(ran).x][temp.get(ran).y].addChit(mountainChit
						.get(i));
				System.out.println("Adding " + mountainChit.get(i).getChitName() + "-" + mountainChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
				pieces.add(mountainChit.get(i));
				// System.out.println("deletion: " +
				// temp.remove(ran).getName());
				temp.remove(ran);

			}

			temp.addAll(mountain);
			for (int i = mountainChit.size() / 2; i < mountainChit.size(); i++) {
				// Random r = new Random();
				// int ran = r.nextInt(temp.size());
				int ran = random.get(rCount++);
				//System.out.println("Mountain2 : Ran is" + ran);
				mountainChit
						.get(i)
						.setChitLocation(
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().x + 20,
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().y + 30);
				board[temp.get(ran).x][temp.get(ran).y].addChit(mountainChit
						.get(i));
				System.out.println("Adding " + mountainChit.get(i).getChitName() + "-" + mountainChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
				pieces.add(mountainChit.get(i));
				// System.out.println("deletion: " +h
				// temp.remove(ran).getName());
				temp.remove(ran);

			}

			temp.addAll(valley);
			for (int i = 0; i < valleyChit.size(); i++) {
				// Random r = new Random();
				// int ran = r.nextInt(temp.size());
				int ran = random.get(rCount++);
				//System.out.println("Valley : Ran is" + ran);
				valleyChit
						.get(i)
						.setChitLocation(
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().x + 20,
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().y + 30);
				board[temp.get(ran).x][temp.get(ran).y].addChit(valleyChit
						.get(i));
				System.out.println("Adding " + valleyChit.get(i).getChitName() + "-" + valleyChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
				pieces.add(valleyChit.get(i));
				// System.out.println("deletion: " +
				// temp.remove(ran).getName());
				temp.remove(ran);

			}

			temp.addAll(woods);
			for (int i = 0; i < woodsChit.size(); i++) {
				// Random r = new Random();
				// int ran = r.nextInt(temp.size());
				int ran = random.get(rCount++);
				System.out.println("Woods : Ran is" + ran);
				woodsChit
						.get(i)
						.setChitLocation(
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().x + 20,
								board[temp.get(ran).x][temp.get(ran).y]
										.getTileLocation().y + 30);
				board[temp.get(ran).x][temp.get(ran).y].addChit(woodsChit
						.get(i));
				System.out.println("Adding " + woodsChit.get(i).getChitName() + "-" + woodsChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
				pieces.add(woodsChit.get(i));
				// System.out.println("deletion: " +
				// temp.remove(ran).getName());
				temp.remove(ran);

			}
			placeDwellings();
		}

		// TODO for cheat mode
		// 1 April
		public void CheatModeBoardSetup() {
			
			for (int c = 0; c < Cmview.FinaLCastle.size(); c++) {
				for(int i = 20; i < cmChits.size(); i++){
					if(Cmview.FinaLCastle.get(c).equals(cmChits.get(i).getChitName()+cmChits.get(i).getChitNum())){
						lostCastle.add(cmChits.get(i));
					}
				}
			}
			
			for (int c = 0; c < Cmview.FinaLCity.size(); c++) {
				for(int i = 20; i < cmChits.size(); i++){
					if(Cmview.FinaLCity.get(c).equals(cmChits.get(i).getChitName()+cmChits.get(i).getChitNum())){
						lostCity.add(cmChits.get(i));
					}
				}
			}
			
			tsc = new TreasureSetUpCard(lostCastle, lostCity);
			System.out
					.println("******************CHEAT MODE CALL**********************");

			//Testing lost castle chits from cheatmode gui
			for(int i=0; i < lostCastle.size(); i++){
				System.out.println("*************** LOST CASTLE************: " + lostCastle.get(i).getChitName()+lostCastle.get(i).getChitNum());
			}
			
			for(int i=0; i < lostCity.size(); i++){
				System.out.println("*************** LOST CITY************: " + lostCity.get(i).getChitName()+lostCity.get(i).getChitNum());
			}

			ArrayList<Point> cave = new ArrayList<Point>();
			ArrayList<Point> mountain = new ArrayList<Point>();
			ArrayList<Point> valley = new ArrayList<Point>();
			ArrayList<Point> woods = new ArrayList<Point>();
			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					if (board[i][j].getTileType() == 'c') {
						cave.add(new Point(i, j));
					}
					if (board[i][j].getTileType() == 'm') {
						mountain.add(new Point(i, j));
					}
					if (board[i][j].getTileType() == 'v') {
						valley.add(new Point(i, j));
					}
					if (board[i][j].getTileType() == 'w') {
						woods.add(new Point(i, j));
					}
				}
			}

			// CAVES
			for (int c = 0; c < Cmview.cavesName.size(); c++) {
				for (int i = 0; i < cave.size(); i++) {
					if (Cmview.cavesName.get(c).equals(
							board[cave.get(i).x][cave.get(i).y].getName())) {
						cmChits.get(c)
								.setChitLocation(
										board[cave.get(i).x][cave.get(i).y]
												.getTileLocation().x + 20,
										board[cave.get(i).x][cave.get(i).y]
												.getTileLocation().y + 20);
						board[cave.get(i).x][cave.get(i).y].addChit(cmChits
								.get(c));
						// System.out.println("HEllo: " +
						// board[cave.get(i).x][cave.get(i).y].getName() +
						// "CNAME : " + cmChits.get(c).getChitName());
						// System.out.println("BOard :" +
						// board[cave.get(i).x][cave.get(i).y].getChitAt(0).getChitName());
					}
				}
			}

			// MOUNTAINS
			for (int c = 0; c < Cmview.mountainName.size(); c++) {
				for (int i = 0; i < mountain.size(); i++) {
					if (Cmview.mountainName.get(c).equals(
							board[mountain.get(i).x][mountain.get(i).y]
									.getName())) {
						cmChits.get(c + 5)
								.setChitLocation(
										board[mountain.get(i).x][mountain
												.get(i).y].getTileLocation().x + 20,
										board[mountain.get(i).x][mountain
												.get(i).y].getTileLocation().y + 20);
						board[mountain.get(i).x][mountain.get(i).y]
								.addChit(cmChits.get(c + 5));
						// System.out.println("HEllo: " +
						// board[mountain.get(i).x][mountain.get(i).y].getName()
						// + "CNAME : " + cmChits.get(c).getChitName());
						// System.out.println("BOard :" +
						// board[mountain.get(i).x][mountain.get(i).y].getChitAt(0).getChitName());
					}
				}
			}

			// VALLEY
			for (int c = 0; c < Cmview.valleyName.size(); c++) {
				for (int i = 0; i < valley.size(); i++) {
					if (Cmview.valleyName.get(c).equals(
							board[valley.get(i).x][valley.get(i).y].getName())) {
						cmChits.get(c + 10)
								.setChitLocation(
										board[valley.get(i).x][valley.get(i).y]
												.getTileLocation().x + 20,
										board[valley.get(i).x][valley.get(i).y]
												.getTileLocation().y + 20);
						board[valley.get(i).x][valley.get(i).y].addChit(cmChits
								.get(c + 10));
						// System.out.println("HEllo: " +
						// board[valley.get(i).x][valley.get(i).y].getName() +
						// "CNAME : " + cmChits.get(c).getChitName());
						// System.out.println("BOard :" +
						// board[valley.get(i).x][valley.get(i).y].getChitAt(0).getChitName());
					}
				}
			}

			// WOODS
			for (int c = 0; c < Cmview.woodsName.size(); c++) {
				for (int i = 0; i < woods.size(); i++) {
					if (Cmview.woodsName.get(c).equals(
							board[woods.get(i).x][woods.get(i).y].getName())) {
						cmChits.get(c + 15)
								.setChitLocation(
										board[woods.get(i).x][woods.get(i).y]
												.getTileLocation().x + 20,
										board[woods.get(i).x][woods.get(i).y]
												.getTileLocation().y + 20);
						board[woods.get(i).x][woods.get(i).y].addChit(cmChits
								.get(c + 15));
						// System.out.println("HEllo: " +
						// board[woods.get(i).x][woods.get(i).y].getName() +
						// "CNAME : " + cmChits.get(c).getChitName());
						// System.out.println("BOard :" +
						// board[woods.get(i).x][woods.get(i).y].getChitAt(0).getChitName());
					}
				}
			}

			// Sound chits for Mountain tiles
			// ERROR here 1200 2 April
			// java.lang.StringIndexOutOfBoundsException: String index out of
			// range: 2
			for (int h = 0; h < 10; h++) {
				for (int g = 0; g < mountain.size(); g++) {
					if (Cmview.soundName
							.get(h)
							.substring(0, 2)
							.equals(board[mountain.get(g).x][mountain.get(g).y]
									.getName())) {
						cmChits.get(h + 20)
								.setChitLocation(
										board[mountain.get(g).x][mountain
												.get(g).y].getTileLocation().x + 20,
										board[mountain.get(g).x][mountain
												.get(g).y].getTileLocation().y + 20);
						board[mountain.get(g).x][mountain.get(g).y]
								.addChit(cmChits.get(h + 20));
						System.out.println("Board Name for sound : "
								+ board[mountain.get(g).x][mountain.get(g).y]
										.getName() + "Chit name: "
								+ cmChits.get(h + 20).getChitName());
					}
				}
			}

			// Site chits for Mountain tiles
			for (int h = 0; h < 10; h++) {
				for (int g = 0; g < mountain.size(); g++) {
					if (Cmview.siteName
							.get(h)
							.substring(0, 2)
							.equals(board[mountain.get(g).x][mountain.get(g).y]
									.getName())) {
						cmChits.get(h + 30)
								.setChitLocation(
										board[mountain.get(g).x][mountain
												.get(g).y].getTileLocation().x + 20,
										board[mountain.get(g).x][mountain
												.get(g).y].getTileLocation().y + 20);
						board[mountain.get(g).x][mountain.get(g).y]
								.addChit(cmChits.get(h + 30));
						System.out.println("Board Name for site : "
								+ board[mountain.get(g).x][mountain.get(g).y]
										.getName() + "Chit name: "
								+ cmChits.get(h + 30).getChitName());

					}
				}
			}

			// Sound chits for Cave tiles
			for (int h = 0; h < 10; h++) {
				for (int g = 0; g < cave.size(); g++) {
					if (Cmview.soundName
							.get(h)
							.substring(0, 2)
							.equals(board[cave.get(g).x][cave.get(g).y]
									.getName())) {
						cmChits.get(h + 20)
								.setChitLocation(
										board[cave.get(g).x][cave.get(g).y]
												.getTileLocation().x + 20,
										board[cave.get(g).x][cave.get(g).y]
												.getTileLocation().y + 20);
						board[cave.get(g).x][cave.get(g).y].addChit(cmChits
								.get(h + 20));
						System.out.println("Board Name for Cave sound : "
								+ board[cave.get(g).x][cave.get(g).y].getName()
								+ "Chit name: "
								+ cmChits.get(h + 20).getChitName());
					}
				}
			}

			// Site chits for Cave tiles
			for (int h = 0; h < 10; h++) {
				for (int g = 0; g < cave.size(); g++) {
					if (Cmview.siteName
							.get(h)
							.substring(0, 2)
							.equals(board[cave.get(g).x][cave.get(g).y]
									.getName())) {
						cmChits.get(h + 30)
								.setChitLocation(
										board[cave.get(g).x][cave.get(g).y]
												.getTileLocation().x + 20,
										board[cave.get(g).x][cave.get(g).y]
												.getTileLocation().y + 20);
						board[cave.get(g).x][cave.get(g).y].addChit(cmChits
								.get(h + 30));
						System.out.println("Board Name for CAve site : "
								+ board[cave.get(g).x][cave.get(g).y].getName()
								+ "Chit name: "
								+ cmChits.get(h + 30).getChitName());

					}
				}
			}
			placeDwellings();
		}

		public void placeDwellings() {

			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					if (board[i][j].getName() != null) {
						if (board[i][j].getTileType() == 'v'&& board[i][j].isTilePlaced() == 1) {
							if (board[i][j].getAllChits().size() != 0) {
								if (board[i][j].getChitAt(0).getChitName().equals("BONES")&& board[i][j].isTilePlaced() == 1) {
									Point d = board[i][j].getClearingLocation(5);
									Point p = new Point(board[i][j].getClearing(5).getXLocation(),board[i][j].getClearing(5).getYLocation());
									board[i][j].getClearing(5).addMonster(Monsters.GHOST);
									board[i][j].getClearing(5).addMonster(Monsters.GHOST);
									monsters.add(p);
									monsters.add(p);

								} else if (board[i][j].getChitAt(0).getChitName().equals("DANK")&& board[i][j].isTilePlaced() == 1) {
									Point d = board[i][j].getClearingLocation(5);
									board[i][j].getClearing(5).setDwelling(dwellings.getChapel());
									board[i][j].getClearing(5).getDwelling().setLocation(d);
									board[i][j].getClearing(5).getDwelling().setClearingOn(board[i][j].getClearing(5));
									board[i][j].getClearing(5).getDwelling().getTileON(board[i][j].getName());
									dwellings.getChapel().setLocation(d);

									// PLACING CHARACTERS
									for (int k = 0; k < PlayerController.playerList
											.size(); k++) {
										if (PlayerController.playerList.get(k).getPlayerCharacter().getStartingLocation().equals("CHAPEL")) {

											// only setup for myself and
											// send the details to the server
											// if (k == PlayerController.model
											// .getPlayerId())
											{
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setPLocation(
																board[i][j]
																		.getClearing(
																				5)
																		.getXLocation(),
																board[i][j]
																		.getClearing(
																				5)
																		.getYLocation());
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setClearingLocation(
																board[i][j]
																		.getClearing(5));
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setBoardLocation(
																board[i][j]
																		.getName());
												board[i][j]
														.getClearing(5)
														.placeCharacter(
																PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter());
												System.out
														.println(PlayerController.playerList
																.get(k)
																.getPlayerCharacter()
																.getTileLocation()
																+ ": "
																+ PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter()
																		.getClearingLocation()
																		.getClearingNum());

												// Send own details to the
												// server
												// MRMessage mrm = new
												// MRMessage(
												// MRMessage.PLAYER_POSITION_DETAILS,
												// k
												// + "#"
												// + i
												// + "#"
												// + j
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getXLocation()
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getYLocation()
												// + "#"
												// + 5
												// + "#"
												// + board[i][j]
												// .getName());
												// ClientGUI.mr.sendMessage(mrm);

											}
										}
									}
									// PLACING GARRISONS
									for (int k = 0; k < tsc.garrisons
											.getChapel().size(); k++) {
										if (tsc.garrisons.getChapel().get(k)
												.getItemType() == 'A') {
											board[i][j]
													.getClearing(5)
													.addArmor(
															(Armor) tsc.garrisons
																	.getChapel()
																	.get(k));
										} else if (tsc.garrisons.getChapel()
												.get(k).getItemType() == 'W') {
											board[i][j]
													.getClearing(5)
													.addWeapon(
															(Weapons) tsc.garrisons
																	.getChapel()
																	.get(k));
										} else if (tsc.garrisons.getChapel()
												.get(k).getItemType() == 'M') {
											board[i][j]
													.getClearing(5)
													.addMonster(
															(Monster) tsc.garrisons
																	.getChapel()
																	.get(k));
										} else if (tsc.garrisons.getChapel()
												.get(k).getItemType() == 'N') {
											board[i][j]
													.getClearing(5)
													.addNatives(
															(NativeGroups) tsc.garrisons
																	.getChapel()
																	.get(k));
										} else if (tsc.garrisons.getChapel()
												.get(k).getItemType() == 'T') {
											board[i][j]
													.getClearing(5)
													.addTreasure(
															(TreasureCard) tsc.garrisons
																	.getChapel()
																	.get(k));
										}
									}

									for (int c = 0; c < board[i][j]
											.getClearing(5).getAllItems()
											.size(); c++) {
										tempImg = board[i][j].getClearing(5)
												.getAllItems().get(c)
												.getImage();
										newimg = tempImg
												.getScaledInstance(
														50,
														50,
														java.awt.Image.SCALE_SMOOTH);
										lbl_ChapelImages[c]
												.setIcon(new ImageIcon(newimg));
									}

									int temp = board[i][j].getClearing(5)
											.getAllItems().size();
									for (int p = 0; p < board[i][j]
											.getClearing(5)
											.getAllCharactersOnClearing()
											.size(); p++) {
										tempImg = board[i][j].getClearing(5)
												.getAllCharactersOnClearing()
												.get(p).getImage();
										newimg = tempImg
												.getScaledInstance(
														50,
														50,
														java.awt.Image.SCALE_SMOOTH);
										lbl_ChapelImages[temp + p]
												.setIcon(new ImageIcon(newimg));
									}
								}

								else if (board[i][j].getChitAt(0).getChitName()
										.equals("RUINS")
										&& board[i][j].isTilePlaced() == 1) {
									Point d = board[i][j]
											.getClearingLocation(5);
									board[i][j].getClearing(5).setDwelling(
											dwellings.getGuard());
									board[i][j].getClearing(5).getDwelling()
											.setLocation(d);
									board[i][j]
											.getClearing(5)
											.getDwelling()
											.setClearingOn(
													board[i][j].getClearing(5));
									board[i][j].getClearing(5).getDwelling()
											.setTileOn(board[i][j].getName());
									// PLACING CHARACTERS
									for (int k = 0; k < PlayerController.playerList
											.size(); k++)

									{
										if (PlayerController.playerList.get(k)
												.getPlayerCharacter()
												.getStartingLocation()
												.equals("GUARD")) {

											// // only setup for myself and
											// // send the details to the server
											// if (k == PlayerController.model
											// .getPlayerId())

											{
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setPLocation(
																board[i][j]
																		.getClearing(
																				5)
																		.getXLocation(),
																board[i][j]
																		.getClearing(
																				5)
																		.getYLocation());
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setClearingLocation(
																board[i][j]
																		.getClearing(5));
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setBoardLocation(
																board[i][j]
																		.getName());
												board[i][j]
														.getClearing(5)
														.placeCharacter(
																PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter());

												// Send own details to the
												// server
												// MRMessage mrm = new
												// MRMessage(
												// MRMessage.PLAYER_POSITION_DETAILS,
												// k
												// + "#"
												// + i
												// + "#"
												// + j
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getXLocation()
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getYLocation()
												// + "#"
												// + 5
												// + "#"
												// + board[i][j]
												// .getName());
												// ClientGUI.mr.sendMessage(mrm);
												System.out
														.println(PlayerController.playerList
																.get(k)
																.getPlayerCharacter()
																.getTileLocation()
																+ ": "
																+ PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter()
																		.getClearingLocation()
																		.getClearingNum());
											}
										}
									}
									// PLACING GARRISONS
									for (int k = 0; k < tsc.garrisons
											.getGuard().size(); k++) {
										if (tsc.garrisons.getGuard().get(k)
												.getItemType() == 'A') {
											board[i][j]
													.getClearing(5)
													.addArmor(
															(Armor) tsc.garrisons
																	.getGuard()
																	.get(k));
										} else if (tsc.garrisons.getGuard()
												.get(k).getItemType() == 'W') {
											board[i][j]
													.getClearing(5)
													.addWeapon(
															(Weapons) tsc.garrisons
																	.getGuard()
																	.get(k));
										} else if (tsc.garrisons.getGuard()
												.get(k).getItemType() == 'M') {
											board[i][j]
													.getClearing(5)
													.addMonster(
															(Monster) tsc.garrisons
																	.getGuard()
																	.get(k));
										} else if (tsc.garrisons.getGuard()
												.get(k).getItemType() == 'N') {
											board[i][j]
													.getClearing(5)
													.addNatives(
															(NativeGroups) tsc.garrisons
																	.getGuard()
																	.get(k));
										} else if (tsc.garrisons.getGuard()
												.get(k).getItemType() == 'T') {
											board[i][j]
													.getClearing(5)
													.addTreasure(
															(TreasureCard) tsc.garrisons
																	.getGuard()
																	.get(k));
										}

										for (int c = 0; c < board[i][j]
												.getClearing(5).getAllItems()
												.size(); c++) {
											tempImg = board[i][j]
													.getClearing(5)
													.getAllItems().get(c)
													.getImage();
											newimg = tempImg
													.getScaledInstance(
															50,
															50,
															java.awt.Image.SCALE_SMOOTH);
											lbl_GuardImages[c]
													.setIcon(new ImageIcon(
															newimg));
										}

										int temp = board[i][j].getClearing(5)
												.getAllItems().size();
										for (int p = 0; p < board[i][j]
												.getClearing(5)
												.getAllCharactersOnClearing()
												.size(); p++) {
											tempImg = board[i][j]
													.getClearing(5)
													.getAllCharactersOnClearing()
													.get(p).getImage();
											newimg = tempImg
													.getScaledInstance(
															50,
															50,
															java.awt.Image.SCALE_SMOOTH);
											lbl_GuardImages[temp + p]
													.setIcon(new ImageIcon(
															newimg));
										}
									}

								} else if (board[i][j].getChitAt(0)
										.getChitName().equals("SMOKE")
										&& board[i][j].isTilePlaced() == 1) {
									Point d = board[i][j]
											.getClearingLocation(5);
									board[i][j].getClearing(5).setDwelling(
											dwellings.getHouse());
									board[i][j].getClearing(5).getDwelling()
											.setLocation(d);
									board[i][j]
											.getClearing(5)
											.getDwelling()
											.setClearingOn(
													board[i][j].getClearing(5));
									board[i][j].getClearing(5).getDwelling()
											.setTileOn(board[i][j].getName());
									// PLACING CHARACTERS
									for (int k = 0; k < PlayerController.playerList
											.size(); k++) {
										if (PlayerController.playerList.get(k)
												.getPlayerCharacter()
												.getStartingLocation()
												.equals("HOUSE")) {
											// // only setup for myself and
											// // send the details to the server
											// if (k == PlayerController.model
											// .getPlayerId())

											{
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setPLocation(
																board[i][j]
																		.getClearing(
																				5)
																		.getXLocation(),
																board[i][j]
																		.getClearing(
																				5)
																		.getYLocation());
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setClearingLocation(
																board[i][j]
																		.getClearing(5));
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setBoardLocation(
																board[i][j]
																		.getName());
												board[i][j]
														.getClearing(5)
														.placeCharacter(
																PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter());

												// // Send own details to the
												// server
												// MRMessage mrm = new
												// MRMessage(
												// MRMessage.PLAYER_POSITION_DETAILS,
												// k
												// + "#"
												// + i
												// + "#"
												// + j
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getXLocation()
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getYLocation()
												// + "#"
												// + 5
												// + "#"
												// + board[i][j]
												// .getName());
												// ClientGUI.mr.sendMessage(mrm);
												System.out
														.println(PlayerController.playerList
																.get(k)
																.getPlayerCharacter()
																.getTileLocation()
																+ ": "
																+ PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter()
																		.getClearingLocation()
																		.getClearingNum());
											}
										}
									}
									// PLACING GARRISONS
									for (int k = 0; k < tsc.garrisons
											.getHouse().size(); k++) {
										if (tsc.garrisons.getHouse().get(k)
												.getItemType() == 'A') {
											board[i][j]
													.getClearing(5)
													.addArmor(
															(Armor) tsc.garrisons
																	.getHouse()
																	.get(k));
										} else if (tsc.garrisons.getHouse()
												.get(k).getItemType() == 'W') {
											board[i][j]
													.getClearing(5)
													.addWeapon(
															(Weapons) tsc.garrisons
																	.getHouse()
																	.get(k));
										} else if (tsc.garrisons.getHouse()
												.get(k).getItemType() == 'M') {
											board[i][j]
													.getClearing(5)
													.addMonster(
															(Monster) tsc.garrisons
																	.getHouse()
																	.get(k));
										} else if (tsc.garrisons.getHouse()
												.get(k).getItemType() == 'N') {
											board[i][j]
													.getClearing(5)
													.addNatives(
															(NativeGroups) tsc.garrisons
																	.getHouse()
																	.get(k));
										} else if (tsc.garrisons.getHouse()
												.get(k).getItemType() == 'T') {
											board[i][j]
													.getClearing(5)
													.addTreasure(
															(TreasureCard) tsc.garrisons
																	.getHouse()
																	.get(k));
										}
									}

									for (int c = 0; c < board[i][j]
											.getClearing(5).getAllItems()
											.size(); c++) {
										tempImg = board[i][j].getClearing(5)
												.getAllItems().get(c)
												.getImage();
										newimg = tempImg
												.getScaledInstance(
														50,
														50,
														java.awt.Image.SCALE_SMOOTH);
										lbl_HouseImages[c]
												.setIcon(new ImageIcon(newimg));
									}

									int temp = board[i][j].getClearing(5)
											.getAllItems().size();
									for (int p = 0; p < board[i][j]
											.getClearing(5)
											.getAllCharactersOnClearing()
											.size(); p++) {
										tempImg = board[i][j].getClearing(5)
												.getAllCharactersOnClearing()
												.get(p).getImage();
										newimg = tempImg
												.getScaledInstance(
														50,
														50,
														java.awt.Image.SCALE_SMOOTH);
										lbl_HouseImages[temp + p]
												.setIcon(new ImageIcon(newimg));
									}

								}

								else if (board[i][j].getChitAt(0).getChitName()
										.equals("STINK")
										&& board[i][j].isTilePlaced() == 1) {
									Point d = board[i][j]
											.getClearingLocation(5);
									board[i][j].getClearing(5).setDwelling(
											dwellings.getInn());
									board[i][j].getClearing(5).getDwelling()
											.setLocation(d);
									board[i][j]
											.getClearing(5)
											.getDwelling()
											.setClearingOn(
													board[i][j].getClearing(5));
									board[i][j].getClearing(5).getDwelling()
											.setTileOn(board[i][j].getName());
									// PLACING CHARACTERS
									for (int k = 0; k < PlayerController.playerList
											.size(); k++) {
										if (PlayerController.playerList.get(k)
												.getPlayerCharacter()
												.getStartingLocation()
												.equals("INN")) {
											// // only setup for myself and
											// // send the details to the server
											// if (k == PlayerController.model
											// .getPlayerId())

											{
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setPLocation(
																board[i][j]
																		.getClearing(
																				5)
																		.getXLocation(),
																board[i][j]
																		.getClearing(
																				5)
																		.getYLocation());
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setClearingLocation(
																board[i][j]
																		.getClearing(5));
												PlayerController.playerList
														.get(k)
														.getPlayerCharacter()
														.setBoardLocation(
																board[i][j]
																		.getName());
												board[i][j]
														.getClearing(5)
														.placeCharacter(
																PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter());

												// // Send own details to the
												// server
												// MRMessage mrm = new
												// MRMessage(
												// MRMessage.PLAYER_POSITION_DETAILS,
												// k
												// + "#"
												// + i
												// + "#"
												// + j
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getXLocation()
												// + "#"
												// + board[i][j]
												// .getClearing(
												// 5)
												// .getYLocation()
												// + "#"
												// + 5
												// + "#"
												// + board[i][j]
												// .getName());
												// ClientGUI.mr.sendMessage(mrm);
												System.out
														.println(PlayerController.playerList
																.get(k)
																.getPlayerCharacter()
																.getTileLocation()
																+ ": "
																+ PlayerController.playerList
																		.get(k)
																		.getPlayerCharacter()
																		.getClearingLocation()
																		.getClearingNum());
											}
										}
									}
									// PLACING GARRISONS
									for (int k = 0; k < tsc.garrisons.getInn()
											.size(); k++) {
										if (tsc.garrisons.getInn().get(k)
												.getItemType() == 'A') {
											board[i][j]
													.getClearing(5)
													.addArmor(
															(Armor) tsc.garrisons
																	.getInn()
																	.get(k));
										} else if (tsc.garrisons.getInn()
												.get(k).getItemType() == 'W') {
											board[i][j]
													.getClearing(5)
													.addWeapon(
															(Weapons) tsc.garrisons
																	.getInn()
																	.get(k));
										} else if (tsc.garrisons.getInn()
												.get(k).getItemType() == 'M') {
											board[i][j]
													.getClearing(5)
													.addMonster(
															(Monster) tsc.garrisons
																	.getInn()
																	.get(k));
										} else if (tsc.garrisons.getInn()
												.get(k).getItemType() == 'N') {
											board[i][j]
													.getClearing(5)
													.addNatives(
															(NativeGroups) tsc.garrisons
																	.getInn()
																	.get(k));
										} else if (tsc.garrisons.getInn()
												.get(k).getItemType() == 'T') {
											board[i][j]
													.getClearing(5)
													.addTreasure(
															(TreasureCard) tsc.garrisons
																	.getInn()
																	.get(k));
										}
									}

									for (int c = 0; c < board[i][j]
											.getClearing(5).getAllItems()
											.size(); c++) {
										tempImg = board[i][j].getClearing(5)
												.getAllItems().get(c)
												.getImage();
										newimg = tempImg
												.getScaledInstance(
														50,
														50,
														java.awt.Image.SCALE_SMOOTH);
										lbl_InnImages[c].setIcon(new ImageIcon(
												newimg));
									}

									int temp = board[i][j].getClearing(5)
											.getAllItems().size();
									for (int p = 0; p < board[i][j]
											.getClearing(5)
											.getAllCharactersOnClearing()
											.size(); p++) {
										tempImg = board[i][j].getClearing(5)
												.getAllCharactersOnClearing()
												.get(p).getImage();
										newimg = tempImg
												.getScaledInstance(
														50,
														50,
														java.awt.Image.SCALE_SMOOTH);
										lbl_InnImages[temp + p]
												.setIcon(new ImageIcon(newimg));
									}

								}
							}

						}
					}
				}
			}
			test = 1;
		}

		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
			super.paintComponent(g2);

			// draw grid
			for (int i = 0; i < BSIZE; i++) {
				for (int j = 0; j < BSIZE; j++) {
					Point t = hexmech.drawHex(i, j, g2);
					if (board[i][j].isTilePlaced() == 0) {
						board[i][j].setTileLocation(t.x, t.y);
						System.out.println("board-" + i + "-" + j + " = " + t.x
								+ "," + t.y);
					}
				}
			}

			if (board1 == true) {
				// PAINTING TILES ON BOARD
				for (int i = 0; i < BSIZE; i++) {
					for (int j = 0; j < BSIZE; j++) {
						int s = (int) (HEXSIZE / 1.73205);
						int t = (int) (HEXSIZE / 1.73205);
						hexmech.fillHex(i, j, board[i][j].isTilePlaced(), g2,
								board[i][j].getImage());

					}
				}
			} else {
				// PAINTING TILES ON BOARD
				for (int i = 0; i < BSIZE; i++) {
					for (int j = 0; j < BSIZE; j++) {
						int s = (int) (HEXSIZE / 1.73205);
						int t = (int) (HEXSIZE / 1.73205);
						hexmech.fillHex(i, j, board[i][j].isTilePlaced(), g2,
								board[i][j].getImage1());

					}
				}
			}

			if (tileP == 20 && test == 1) {
				// System.out.println("Initial Setup: " + initialSetup);
				if (board1 == true)
					boardInit();
				else
					boardInit2();

				if (counter >= 2) {
					// PAINTING CHITS ON BOARD
					for (int i = 0; i < BSIZE; i++) {
						for (int j = 0; j < BSIZE; j++) {
							if (board[i][j].getName() != null) {
								for (int k = 0; k < board[i][j].getAllChits()
										.size(); k++) {
									Chit.addChit(
											board[i][j].getChitAt(k)
													.getChitLocation().x
													+ (5 * k),
											board[i][j].getChitAt(k)
													.getChitLocation().y
													- (5 * k),
											g2,
											board[i][j].getChitAt(k).getImage(
													board[i][j].getChitAt(k)
															.isHidden()));
								}
							}
						}
					}
				}
				if (counter == 3) {
					for (int i = 0; i < BSIZE; i++) {
						for (int j = 0; j < BSIZE; j++) {
							if (board[i][j].getName() != null
									&& board[i][j].getTileType() == 'v') {
								for (int k = 0; k < board[i][j].getAllChits()
										.size(); k++) {
									board[i][j].getChitAt(k).unhide();
								}
							}
						}
					}
				} else if (counter == 4) {
					// EXCHANGE VALLEY CHITS FOR DWELLINGS
					for (int i = 0; i < BSIZE; i++) {
						for (int j = 0; j < BSIZE; j++) {
							if (board[i][j].getName() != null
									&& board[i][j].getTileType() == 'v') {
								for (int k = 0; k < board[i][j].getAllChits()
										.size(); k++) {
									board[i][j].removeChit(k);
								}
							}
						}
					}
					// PAINTING DWELLINGS ON BOARD
					for (int i = 0; i < BSIZE; i++) {
						for (int j = 0; j < BSIZE; j++) {
							if (board[i][j].getName() != null) {
								for (int k = 0; k <= board[i][j].getAllClearings().length; k++) {
									if (board[i][j].getClearing(k) != null) {
										if (board[i][j].getClearing(k).getAllItems() != null) {
											if (board[i][j].getClearing(k).getDwelling() != null) {
												Item.addDwelling(board[i][j].getClearing(k).getDwelling(), g2);

											}
										}
									}

								}
							}
						}
					}
				} else if (counter >= 5) {

					int count = 0;
					// PAINTING DWELLINGS BUTTON ON BOARD
					btnDwelling[0].setBounds(dwellings.getChapel().getLocation().x - 17, dwellings.getChapel().getLocation().y - 17, 35, 35);
					btnDwelling[1].setBounds(dwellings.getGuard().getLocation().x - 17,dwellings.getGuard().getLocation().y - 17, 35, 35);
					btnDwelling[2].setBounds(dwellings.getHouse().getLocation().x - 17,dwellings.getHouse().getLocation().y - 17, 35, 35);
					btnDwelling[3].setBounds(dwellings.getInn().getLocation().x - 17, dwellings.getInn().getLocation().y - 17, 35, 35);

					// PAINTING CLEARINGS BUTTON ON BOARD
					for (int i = 0; i < BSIZE; i++) {
						for (int j = 0; j < BSIZE; j++) {
							if (board[i][j].getName() != null) {
								for (int k = 1; k < board[i][j]
										.getAllClearings().length + 1; k++) {
									if (board[i][j].getClearing(k) != null) {
										btnClearings[count].setBounds(board[i][j].getClearing(k).getLocation().x - 16,board[i][j].getClearing(k).getLocation().y - 16,35, 35);
										btnClearings[count].setBorderPainted(true);

							
										count++;
									}
								}
							}
						}

					}

					// PAINTING DWELLINGS ITEMS ON BOARD
					for (int i = 0; i < BSIZE; i++) {
						for (int j = 0; j < BSIZE; j++) {
							if (board[i][j].getName() != null) {
								for (int k = 1; k <= board[i][j].getAllClearings().length+1; k++) {
									if (board[i][j].getClearing(k) != null) {
										if (board[i][j].getClearing(k).getAllItems() != null) {
											if (board[i][j].getClearing(k).getDwelling() != null) {
												Item.addDwelling(board[i][j].getClearing(k).getDwelling(), g2);
											}
											for (int l = 0; l < board[i][j].getClearing(k).getAllItems().size(); l++) {
												//ADEEL
												Item.addItem(board[i][j].getClearing(k).getXLocation(),board[i][j].getClearing(k).getYLocation(),g2, board[i][j].getClearing(k).getItem(l).getImage());
											}
											if (board[i][j].getClearing(k).getAllCharactersOnClearing() != null) {
												for (int l = 0; l < board[i][j].getClearing(k).getAllCharactersOnClearing().size(); l++) {
													Item.addCharacters(board[i][j].getClearing(k).getCharacterOnClearing(l),g2);
												}
											}
											if (board[i][j].getClearing(k).getAllMonsters() != null) {
												for (int l = 0; l < board[i][j].getClearing(k).getAllMonsters().size(); l++) {
													Item.addItem(board[i][j].getClearing(k).getXLocation(),board[i][j].getClearing(k).getYLocation(),g2, board[i][j].getClearing(k).getMonster(l).getImage());
												}
											}
										}
									}
								}

							}
						}
					}
				}

				/*
				 * //PAINTING CHARACTER COUNTERS for(int i = 0; i <
				 * PlayerController.playerList.size(); i++) {
				 * g2.drawImage(PlayerController
				 * .playerList.get(i).getPlayerCharacter().getImage(),
				 * PlayerController
				 * .playerList.get(i).getPlayerCharacter().getPLocation().x-10,
				 * PlayerController
				 * .playerList.get(i).getPlayerCharacter().getPLocation
				 * ().y-10,30,30,null); //System.out.println("Player Location: "
				 * + PlayerController.playerList.get(i).getPlayerCharacter().
				 * getPLocation().toString());
				 * //System.out.println(PlayerController
				 * .playerList.get(i).getPlayerCharacter().getTileLocation());
				 * 
				 * }
				 */

			}

		}

		public void updateBoard() {
			placeDwellings();
			repaint();
		}

		class MyMouseListener extends MouseAdapter { // inner class inside
			// DrawingPanel
			public void mouseClicked(MouseEvent e) {

				Point p = new Point(hexmech.pxtoHex(e.getX(), e.getY()));
				if (p.x < 0 || p.y < 0 || p.x >= BSIZE || p.y >= BSIZE)
					return;

				btnAdd.setEnabled(false);
				;
				// What do you want to do when a hexagon is clicked?

				if (board[p.x][p.y].isTilePlaced() == 0 && tileP < 20) {
					for (int i = 0; i < BSIZE; i++) {
						for (int j = 0; j < BSIZE; j++) {
							if (board[i][j].isTilePlaced() == 0) {
								board[i][j].setTilePlaced(1);
								tileP++;
							}
						}
					}
					if (board1 == true) {
						for (int i = 0; i < BSIZE; i++) {
							for (int j = 0; j < BSIZE; j++) {
								board[i][j].setClearing();

							}
						}

						boardInit();
					} else {

						for (int i = 0; i < BSIZE; i++) {
							for (int j = 0; j < BSIZE; j++) {
								board[i][j].setClearing1();

							}
						}
						boardInit2();
					}

					if (Cmview.isCheatMode() == true) {
						CheatModeBoardSetup();
					} else {
						boardSetup();
					}

					for (int i = 0; i < PlayerController.playerList.size(); i++) {
						PlayerController.playerList.get(i).getPlayerCharacter()
								.setPathways(buildPathway());
					}

					repaint();

				} else if (board[p.x][p.y].isTilePlaced() == 1 && tileP == 20) {
					System.out.println("Board is already setup");
					// System.out.println(e.getX() + ", " + e.getY());
					// boardInit();
					counter++;
				} else if (board[p.x][p.y].isTilePlaced() == 2 && tileP == 20) {
				  //	System.out.println("twooooooooooooooooooooooooooooo");
					// System.out.println(e.getX() + ", " + e.getY());
					// boardInit();
					// counter++;
				}
				repaint();
			}
		} // end of MyMouseListener class
	} // end of DrawingPanel class

	// Function to get Character counter
	public Image getCharCounter(String s) {
		Image img;
		switch (s) {
		case "Amazon":
			return img = new ImageIcon("./images/characters/amazon.png")
					.getImage();
		case "Captain":
			return img = new ImageIcon("./images/characters/captain.png")
					.getImage();
		case "Swordsman":
			return img = new ImageIcon("./images/characters/swordsman.png")
					.getImage();
		case "Dwarf":
			return img = new ImageIcon("./images/characters/dwarf.png")
					.getImage();
		case "Elf":
			return img = new ImageIcon("./images/characters/elf.png")
					.getImage();
		case "Black Knight":
			return img = new ImageIcon("./images/characters/black_knight.png")
					.getImage();
		case "White Knight":
			return img = new ImageIcon("./images/characters/white_knight.png")
					.getImage();
		case "Berserker":
			return img = new ImageIcon("./images/characters/berserker.png")
					.getImage();
		default:
			return null;
		}
	}
	
	
	public Image getCharSpecialAdvantage(String s, String a) {
		Image imgSA;
		switch (s) {
		case "Amazon":
			if(a.equals("AIM")){
				return imgSA = new ImageIcon("./images/badges/aim.gif").getImage();
			}
			else if(a.equals("STAMINA")){
				return imgSA = new ImageIcon("./images/badges/stamina.gif").getImage();
			}
		case "Captain":
			if(a.equals("AIM")){
				return imgSA = new ImageIcon("./images/badges/aim.gif").getImage();
			}
			else if(a.equals("REPUTATION")){
				return imgSA = new ImageIcon("./images/badges/reputation.gif").getImage();
			}
		case "Swordsman":
			if(a.equals("BARTER")){
				return imgSA = new ImageIcon("./images/badges/barter.gif").getImage();
			}
			else if(a.equals("CLEVER")){
				return imgSA = new ImageIcon("./images/badges/clever.gif").getImage();
			}
		case "Dwarf":
			if(a.equals("SHORT LEGS")){
				return imgSA = new ImageIcon("./images/badges/short_legs.gif").getImage();
			}
			else if(a.equals("CAVE KNOWLEDGE")){
				return imgSA = new ImageIcon("./images/badges/cave_knowledge.gif").getImage();
			}
		case "Elf":
			if(a.equals("ELUSIVENESS")){
				return imgSA = new ImageIcon("./images/badges/elusiveness.gif").getImage();
			}
			else if(a.equals("ARCHER")){
				return imgSA = new ImageIcon("./images/badges/archer.gif").getImage();
			}
		case "Black Knight":
			if(a.equals("AIM")){
				return imgSA = new ImageIcon("./images/badges/aim.gif").getImage();
			}
			else if(a.equals("FEAR")){
				return imgSA = new ImageIcon("./images/badges/fear.gif").getImage();
			}
		case "White Knight":
			if(a.equals("HEALTH")){
				return imgSA = new ImageIcon("./images/badges/health.gif").getImage();
			}
			else if(a.equals("HONOR")){
				return imgSA = new ImageIcon("./images/badges/honor.gif").getImage();
			}
		case "Berserker":
			if(a.equals("ROBUST")){
				return imgSA = new ImageIcon("./images/badges/robust.gif").getImage();
			}
			else if(a.equals("BERSERK")){
				return imgSA = new ImageIcon("./images/badges/berserk.gif").getImage();
			}
		default:
			return null;
		}
	}

}