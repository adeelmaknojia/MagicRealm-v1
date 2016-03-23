package server;

import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import jdk.nashorn.internal.runtime.regexp.joni.SearchAlgorithm;
import Tiles.AwfulValley;
import Tiles.BadValley;
import Tiles.Borderland;
import Tiles.Cavern;
import Tiles.Caves;
import Tiles.Clearing;
import Tiles.Cliff;
import Tiles.Crag;
import Tiles.CurstValley;
import Tiles.DarkValley;
import Tiles.DeepWoods;
import Tiles.EmptyTile;
import Tiles.EvilValley;
import Tiles.HighPass;
import Tiles.Ledges;
import Tiles.LindenWoods;
import Tiles.MapleWoods;
import Tiles.Mountain;
import Tiles.NutWoods;
import Tiles.OakWoods;
import Tiles.PineWoods;
import Tiles.Ruins;
import Tiles.Tile;
import main.Activity;
import main.Armor;
import main.Character;
import main.CharacterCards;
import main.CheatModeGUI;
import main.Chit;
import main.Chits;
import main.Combat;
import main.Dwellings;
import main.GameGUI;
import main.Item;
import main.Monster;
import main.Monsters;
import main.NativeGroups;
import main.Player;
import main.TreasureCard;
import main.TreasureSetUpCard;
import main.Weapons;
import main.hexmech;
import messaging.MRMessage;
import client.ClientInfo;

public class MRServer extends Thread {

	// variable to store the list of players
	static ArrayList<Player> playerList = new ArrayList<Player>();

	// variable to store the available characters of the game
	ArrayList<String> availableCharacterList = new ArrayList<String>();

	// taken from client:
	// Stores all the game characters as an array
	private CharacterCards cards = new CharacterCards();
	private Character[] gameChars = cards.getClist();

	// counter to store the number of clients
	private int counter = 0;

	// a unique ID for each connection
	private static int uniqueId;

	// an ArrayList to keep the list of the Client
	private ArrayList<ClientThread> al;

	// an ArrayList of clients info .. connected to the server
	private ArrayList<ClientInfo> clients;

	boolean keepGoing;
	int port;
	// to display time
	private SimpleDateFormat sdf;

	private ServerGUI sg;

	private ArrayList<Integer> random = new ArrayList<Integer>();

	/*
	 * Variable to tell whether server is accepting the clients currently or not
	 */
	public boolean acceptClients = false;

	final static int BSIZE = 6; // board size.

	final static int HEXSIZE = 220; // hex size in pixels

	Tile[][] board = new Tile[BSIZE][BSIZE]; // 2d array for board

	int tileP = 0;

	ArrayList<Chit> pieces = new ArrayList<Chit>();
	ArrayList<Point> dw = new ArrayList<Point>();
	ArrayList<Point> monsters = new ArrayList<Point>(2);
	Dwellings dwellings = new Dwellings();
	ArrayList<Chit> sChits = (new Chits()).getAllSChit();
	ArrayList<Chit> appChart = Chits.randomizeAppearanceChart(sChits);
	ArrayList<Chit> caveChit = Chits.randomizeCaveChits(sChits);
	ArrayList<Chit> mountainChit = Chits.randomizeMountainChits(sChits);
	ArrayList<Chit> valleyChit = Chits.randomizeValleyChits(sChits);
	ArrayList<Chit> woodsChit = Chits.randomizeWoodsChits(sChits);
	ArrayList<Chit> lostCastle = new ArrayList<Chit>();
	ArrayList<Chit> lostCity= new ArrayList<Chit>();
	public static TreasureSetUpCard tsc;
	ArrayList<Chit> cmChits = (new Chits()).getAllSChit();

	final static int BORDERS = 15;

	// Taniya
	public static boolean board1;

	/**
	 * Variable to detect if the first player initialization has been
	 * accomplished
	 */
	boolean isGameModeSet = false;

	boolean isFirstPlayer = true;
	boolean isCheatSetup = false;
	int monsterRoll;
	int combatDone;

	// 0 for random
	// 1 for cheat mode
	int gameMode = 0;

	// variable to keep track of the number of activities performed by a player
	// after the roll of a dice.
	// since we allow a player to complete all his/her activities before moving
	// onto other
	// a single variable should be sufficient
	// We need to increment this for each activity performed
	int no_of_activities = 0;
	int currClearing;
	String tileName;

	public CheatModeGUI Cmview = new CheatModeGUI();

	/*
	 * server constructor that receive the port to listen to for connection as
	 * parameter in console
	 */
	public MRServer(int port) {
		// to display hh:mm:ss
		sdf = new SimpleDateFormat("HH:mm:ss");

	}

	public MRServer(int port, ServerGUI sg) {
		// GUI or not
		this.sg = sg;
		// the port
		this.port = port;
		// to display hh:mm:ss
		sdf = new SimpleDateFormat("HH:mm:ss");
		// ArrayList for the Client list
		al = new ArrayList<ClientThread>();

		clients = new ArrayList<ClientInfo>();

		// game logic below
		// add all available characters
		for (String character : getCharList()) {
			availableCharacterList.add(character);
		}


		// availableCharacterList.addAll();
	}

	public ClientInfo getClientInfoById(int id) {
		for (ClientInfo ci : clients) {
			if (ci.getThreadId() == id) {
				return ci;
			}
		}
		return null;
	}

	public ClientInfo getClientInfoByCharacter(String c) {
		for (ClientInfo ci : clients) {
			if (ci.getCharacterName().equals(c)) {
				return ci;
			}
		}
		return null;
	}

	// FIRST BOARD
	void initGame1() {
		System.out.println("Within initGame1");

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

	// Second Board
	void initGame2() {
		System.out.println("Within initGame2");

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

	// Initializing board 1 clearings
	public void boardInit() {
		System.out.println("Within boardInit1");
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

	// Intialize board 2 clearings
	public void boardInit2() {
		System.out.println("Within boardInit2");

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
						path.add(board[i][j].getAllClearings()[k]);
						//						System.out.print(board[i][j].getAllClearings()[k]
						//								.getTileName()
						//								+ board[i][j].getAllClearings()[k]
						//										.getClearingNum() + " ");
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
		for (int i = 0; i < caveChit.size() / 2; i++) {
			Random r = new Random();
			int ran = r.nextInt(temp.size());
			random.add(ran);
			caveChit.get(i)
			.setChitLocation(
					board[temp.get(ran).x][temp.get(ran).y]
							.getTileLocation().x + 20,
							board[temp.get(ran).x][temp.get(ran).y]
									.getTileLocation().y + 30);
			board[temp.get(ran).x][temp.get(ran).y].addChit(caveChit.get(i));
			System.out.println("Adding " + caveChit.get(i).getChitName() + "-" + caveChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
			pieces.add(caveChit.get(i));
			// System.out.println("deletion: " +
			// temp.remove(ran).getName());
			temp.remove(ran);

		}
		temp.addAll(cave);
		for (int i = caveChit.size() / 2; i < caveChit.size(); i++) {
			Random r = new Random();

			int ran = r.nextInt(temp.size());
			random.add(ran);
			caveChit.get(i)
			.setChitLocation(
					board[temp.get(ran).x][temp.get(ran).y]
							.getTileLocation().x + 20,
							board[temp.get(ran).x][temp.get(ran).y]
									.getTileLocation().y + 30);
			board[temp.get(ran).x][temp.get(ran).y].addChit(caveChit.get(i));
			System.out.println("Adding " + caveChit.get(i).getChitName() + "-" + caveChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
			pieces.add(caveChit.get(i));
			// System.out.println("deletion: " +
			// temp.remove(ran).getName());
			temp.remove(ran);

		}

		temp.addAll(mountain);
		for (int i = 0; i < mountainChit.size() / 2; i++) {
			Random r = new Random();
			int ran = r.nextInt(temp.size());
			random.add(ran);
			mountainChit
			.get(i)
			.setChitLocation(
					board[temp.get(ran).x][temp.get(ran).y]
							.getTileLocation().x + 20,
							board[temp.get(ran).x][temp.get(ran).y]
									.getTileLocation().y + 30);
			board[temp.get(ran).x][temp.get(ran).y]
					.addChit(mountainChit.get(i));
			System.out.println("Adding " + mountainChit.get(i).getChitName() + "-" + mountainChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
			pieces.add(mountainChit.get(i));
			// System.out.println("deletion: " +
			// temp.remove(ran).getName());
			temp.remove(ran);

		}

		temp.addAll(mountain);
		for (int i = mountainChit.size() / 2; i < mountainChit.size(); i++) {
			Random r = new Random();
			int ran = r.nextInt(temp.size());
			random.add(ran);
			mountainChit
			.get(i)
			.setChitLocation(
					board[temp.get(ran).x][temp.get(ran).y]
							.getTileLocation().x + 20,
							board[temp.get(ran).x][temp.get(ran).y]
									.getTileLocation().y + 30);
			board[temp.get(ran).x][temp.get(ran).y]
					.addChit(mountainChit.get(i));
			System.out.println("Adding " + mountainChit.get(i).getChitName() + "-" + mountainChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
			pieces.add(mountainChit.get(i));
			// System.out.println("deletion: " +
			// temp.remove(ran).getName());
			temp.remove(ran);

		}

		temp.addAll(valley);
		for (int i = 0; i < valleyChit.size(); i++) {
			Random r = new Random();
			int ran = r.nextInt(temp.size());
			random.add(ran);
			valleyChit
			.get(i)
			.setChitLocation(
					board[temp.get(ran).x][temp.get(ran).y]
							.getTileLocation().x + 20,
							board[temp.get(ran).x][temp.get(ran).y]
									.getTileLocation().y + 30);
			System.out.println("Adding " + valleyChit.get(i).getChitName() + "-" + valleyChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
			board[temp.get(ran).x][temp.get(ran).y].addChit(valleyChit.get(i));
			pieces.add(valleyChit.get(i));
			// System.out.println("deletion: " +
			// temp.remove(ran).getName());
			temp.remove(ran);

		}

		temp.addAll(woods);
		for (int i = 0; i < woodsChit.size(); i++) {
			Random r = new Random();
			int ran = r.nextInt(temp.size());
			random.add(ran);
			woodsChit
			.get(i)
			.setChitLocation(
					board[temp.get(ran).x][temp.get(ran).y]
							.getTileLocation().x + 20,
							board[temp.get(ran).x][temp.get(ran).y]
									.getTileLocation().y + 30);
			board[temp.get(ran).x][temp.get(ran).y].addChit(woodsChit.get(i));
			System.out.println("Adding " + woodsChit.get(i).getChitName() + "-" + woodsChit.get(i).getWarningType() + "  to  " +board[temp.get(ran).x][temp.get(ran).y].getName());
			pieces.add(woodsChit.get(i));
			// System.out.println("deletion: " +
			// temp.remove(ran).getName());
			temp.remove(ran);

		}
		placeDwellings();
	}

	public void CheatModeBoardSetup() {
		System.out
		.println("******************CHEAT MODE CALL**********************");

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
					board[cave.get(i).x][cave.get(i).y].addChit(cmChits.get(c));
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
						board[mountain.get(i).x][mountain.get(i).y].getName())) {
					cmChits.get(c + 5)
					.setChitLocation(
							board[mountain.get(i).x][mountain.get(i).y]
									.getTileLocation().x + 20,
									board[mountain.get(i).x][mountain.get(i).y]
											.getTileLocation().y + 20);
					board[mountain.get(i).x][mountain.get(i).y].addChit(cmChits
							.get(c + 5));
					// System.out.println("HEllo: " +
					// board[mountain.get(i).x][mountain.get(i).y].getName() +
					// "CNAME : " + cmChits.get(c).getChitName());
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
		for (int h = 0; h < 10; h++) {
			for (int g = 0; g < mountain.size(); g++) {
				if (Cmview.soundName.get(h).substring(0, 2).equals(board[mountain.get(g).x][mountain.get(g).y].getName())) {
					cmChits.get(h + 20).setChitLocation(board[mountain.get(g).x][mountain.get(g).y].getTileLocation().x + 20,board[mountain.get(g).x][mountain.get(g).y].getTileLocation().y + 20);
					board[mountain.get(g).x][mountain.get(g).y].addChit(cmChits.get(h + 20));
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
							board[mountain.get(g).x][mountain.get(g).y]
									.getTileLocation().x + 20,
									board[mountain.get(g).x][mountain.get(g).y]
											.getTileLocation().y + 20);
					board[mountain.get(g).x][mountain.get(g).y].addChit(cmChits
							.get(h + 30));
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
				if (Cmview.soundName.get(h).substring(0, 2)
						.equals(board[cave.get(g).x][cave.get(g).y].getName())) {
					cmChits.get(h + 20)
					.setChitLocation(
							board[cave.get(g).x][cave.get(g).y]
									.getTileLocation().x + 20,
									board[cave.get(g).x][cave.get(g).y]
											.getTileLocation().y + 20);
					board[cave.get(g).x][cave.get(g).y].addChit(cmChits
							.get(h + 20));
					System.out
					.println("Board Name for Cave sound : "
							+ board[cave.get(g).x][cave.get(g).y]
									.getName() + "Chit name: "
									+ cmChits.get(h + 20).getChitName());
				}
			}
		}

		// Site chits for Cave tiles
		for (int h = 0; h < 10; h++) {
			for (int g = 0; g < cave.size(); g++) {
				if (Cmview.siteName.get(h).substring(0, 2)
						.equals(board[cave.get(g).x][cave.get(g).y].getName())) {
					cmChits.get(h + 30)
					.setChitLocation(
							board[cave.get(g).x][cave.get(g).y]
									.getTileLocation().x + 20,
									board[cave.get(g).x][cave.get(g).y]
											.getTileLocation().y + 20);
					board[cave.get(g).x][cave.get(g).y].addChit(cmChits
							.get(h + 30));
					System.out
					.println("Board Name for CAve site : "
							+ board[cave.get(g).x][cave.get(g).y]
									.getName() + "Chit name: "
									+ cmChits.get(h + 30).getChitName());

				}
			}
		}
		placeDwellings();
	}

	public void setClearingsForPlayer(int playerId) {

		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				if (board[i][j].getTileType() == 'v'
						) {
					if (board[i][j].getAllChits().size() != 0) {
						if (board[i][j].getChitAt(0).getChitName()
								.equals("DANK")
								) {

							// PLACING CHARACTERS
							// for (int k = 0; k < playerList.size(); k++)
							{
								if (playerList.get(playerId)
										.getPlayerCharacter()
										.getStartingLocation().equals("CHAPEL")) {

									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(playerId)
											.getPlayerCharacter());

								}
							}
						}

						else if (board[i][j].getChitAt(0).getChitName()
								.equals("RUINS")
								) {

							// PLACING CHARACTERS
							// for (int k = 0; k < playerList.size(); k++)
							{
								if (playerList.get(playerId)
										.getPlayerCharacter()
										.getStartingLocation().equals("GUARD")) {

									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(playerId)
											.getPlayerCharacter());

								}
							}

						} else if (board[i][j].getChitAt(0).getChitName()
								.equals("SMOKE")
								) {
							// PLACING CHARACTERS
							// for (int k = 0; k < playerList.size(); k++)
							{
								if (playerList.get(playerId)
										.getPlayerCharacter()
										.getStartingLocation().equals("HOUSE")) {

									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(playerId)
											.getPlayerCharacter());

								}
							}
						}

						else if (board[i][j].getChitAt(0).getChitName()
								.equals("STINK")
								) {
							// PLACING CHARACTERS
							// for (int k = 0; k < playerList.size(); k++)
							{
								if (playerList.get(playerId)
										.getPlayerCharacter()
										.getStartingLocation().equals("INN")) {
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(playerId)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(playerId)
											.getPlayerCharacter());

								}
							}

						}
					}

				}
			}
		}
		playerList.get(playerId).getPlayerCharacter()
		.setPathways(buildPathway());
		// test = 1;
	}

	public void placeDwellings() {

		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				if (board[i][j].getTileType() == 'v'
						&& board[i][j].isTilePlaced() == 1) {
					if (board[i][j].getAllChits().size() != 0) {
						if (board[i][j].getChitAt(0).getChitName()
								.equals("BONES")
								&& board[i][j].isTilePlaced() == 1) {
							Point d = board[i][j].getClearingLocation(5);
							Point p = new Point(board[i][j].getClearing(5)
									.getXLocation(), board[i][j].getClearing(5)
									.getYLocation());
							board[i][j].getClearing(5).addMonster(
									Monsters.GHOST);
							board[i][j].getClearing(5).addMonster(
									Monsters.GHOST);
							monsters.add(p);
							monsters.add(p);

						} else if (board[i][j].getChitAt(0).getChitName()
								.equals("DANK")
								&& board[i][j].isTilePlaced() == 1) {
							Point d = board[i][j].getClearingLocation(5);
							board[i][j].getClearing(5).setDwelling(
									dwellings.getChapel());
							board[i][j].getClearing(5).getDwelling()
							.setLocation(d);
							board[i][j].getClearing(5).getDwelling()
							.setClearingOn(board[i][j].getClearing(5));
							board[i][j].getClearing(5).getDwelling()
							.getTileON(board[i][j].getName());
							dwellings.getChapel().setLocation(d);

							// PLACING CHARACTERS
							for (int k = 0; k < playerList.size(); k++) {
								if (playerList.get(k).getPlayerCharacter()
										.getStartingLocation().equals("CHAPEL")) {

									playerList
									.get(k)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(k)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(k)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(k)
											.getPlayerCharacter());
									System.out.println(playerList.get(k)
											.getPlayerCharacter()
											.getTileLocation()
											+ ": "
											+ playerList.get(k)
											.getPlayerCharacter()
											.getClearingLocation()
											.getClearingNum());

									// Send own details to the server
									// MRMessage mrm = new MRMessage(
									// MRMessage.PLAYER_POSITION_DETAILS,
									// k
									// + "#"
									// + i
									// + "#"
									// + j
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getXLocation()
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getYLocation()
									// + "#" + 5 + "#"
									// + board[i][j].getName());
									// ClientGUI.mr.sendMessage(mrm);

								}
							}
							// PLACING GARRISONS
							for (int k = 0; k < tsc.garrisons.getChapel()
									.size(); k++) {
								if (tsc.garrisons.getChapel().get(k)
										.getItemType() == 'A') {
									board[i][j].getClearing(5).addArmor(
											(Armor) tsc.garrisons.getChapel()
											.get(k));
								} else if (tsc.garrisons.getChapel().get(k)
										.getItemType() == 'W') {
									board[i][j].getClearing(5).addWeapon(
											(Weapons) tsc.garrisons.getChapel()
											.get(k));
								} else if (tsc.garrisons.getChapel().get(k)
										.getItemType() == 'M') {
									board[i][j].getClearing(5).addMonster(
											(Monster) tsc.garrisons.getChapel()
											.get(k));
								} else if (tsc.garrisons.getChapel().get(k)
										.getItemType() == 'N') {
									board[i][j].getClearing(5).addNatives(
											(NativeGroups) tsc.garrisons
											.getChapel().get(k));
								} else if (tsc.garrisons.getChapel().get(k)
										.getItemType() == 'T') {
									board[i][j].getClearing(5).addTreasure(
											(TreasureCard) tsc.garrisons
											.getChapel().get(k));
								}
							}

							for (int c = 0; c < board[i][j].getClearing(5)
									.getAllItems().size(); c++) {
								// tempImg = board[i][j].getClearing(5)
								// .getAllItems().get(c).getImage();
								// newimg = tempImg.getScaledInstance(50, 50,
								// java.awt.Image.SCALE_SMOOTH);
								// lbl_ChapelImages[c].setIcon(new ImageIcon(
								// newimg));
							}

							int temp = board[i][j].getClearing(5).getAllItems()
									.size();
							for (int p = 0; p < board[i][j].getClearing(5)
									.getAllCharactersOnClearing().size(); p++) {
								// tempImg = board[i][j].getClearing(5)
								// .getAllCharactersOnClearing().get(p)
								// .getImage();
								// newimg = tempImg.getScaledInstance(50, 50,
								// java.awt.Image.SCALE_SMOOTH);
								// lbl_ChapelImages[temp + p]
								// .setIcon(new ImageIcon(newimg));
							}
						}

						else if (board[i][j].getChitAt(0).getChitName()
								.equals("RUINS")
								&& board[i][j].isTilePlaced() == 1) {
							Point d = board[i][j].getClearingLocation(5);
							board[i][j].getClearing(5).setDwelling(
									dwellings.getGuard());
							board[i][j].getClearing(5).getDwelling()
							.setLocation(d);
							board[i][j].getClearing(5).getDwelling()
							.setClearingOn(board[i][j].getClearing(5));
							board[i][j].getClearing(5).getDwelling()
							.setTileOn(board[i][j].getName());
							// PLACING CHARACTERS
							for (int k = 0; k < playerList.size(); k++) {
								if (playerList.get(k).getPlayerCharacter()
										.getStartingLocation().equals("GUARD")) {

									playerList
									.get(k)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(k)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(k)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(k)
											.getPlayerCharacter());

									// Send own details to the server
									// MRMessage mrm = new MRMessage(
									// MRMessage.PLAYER_POSITION_DETAILS,
									// k
									// + "#"
									// + i
									// + "#"
									// + j
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getXLocation()
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getYLocation()
									// + "#" + 5 + "#"
									// + board[i][j].getName());
									// ClientGUI.mr.sendMessage(mrm);
									System.out.println(playerList.get(k)
											.getPlayerCharacter()
											.getTileLocation()
											+ ": "
											+ playerList.get(k)
											.getPlayerCharacter()
											.getClearingLocation()
											.getClearingNum());

								}
							}
							// PLACING GARRISONS
							for (int k = 0; k < tsc.garrisons.getGuard().size(); k++) {
								if (tsc.garrisons.getGuard().get(k)
										.getItemType() == 'A') {
									board[i][j].getClearing(5).addArmor(
											(Armor) tsc.garrisons.getGuard()
											.get(k));
								} else if (tsc.garrisons.getGuard().get(k)
										.getItemType() == 'W') {
									board[i][j].getClearing(5).addWeapon(
											(Weapons) tsc.garrisons.getGuard()
											.get(k));
								} else if (tsc.garrisons.getGuard().get(k)
										.getItemType() == 'M') {
									board[i][j].getClearing(5).addMonster(
											(Monster) tsc.garrisons.getGuard()
											.get(k));
								} else if (tsc.garrisons.getGuard().get(k)
										.getItemType() == 'N') {
									board[i][j].getClearing(5).addNatives(
											(NativeGroups) tsc.garrisons
											.getGuard().get(k));
								} else if (tsc.garrisons.getGuard().get(k)
										.getItemType() == 'T') {
									board[i][j].getClearing(5).addTreasure(
											(TreasureCard) tsc.garrisons
											.getGuard().get(k));
								}

								for (int c = 0; c < board[i][j].getClearing(5)
										.getAllItems().size(); c++) {
									// tempImg = board[i][j].getClearing(5)
									// .getAllItems().get(c).getImage();
									// newimg = tempImg.getScaledInstance(50,
									// 50,
									// java.awt.Image.SCALE_SMOOTH);
									// lbl_GuardImages[c].setIcon(new ImageIcon(
									// newimg));
								}

								int temp = board[i][j].getClearing(5)
										.getAllItems().size();
								for (int p = 0; p < board[i][j].getClearing(5)
										.getAllCharactersOnClearing().size(); p++) {
									// tempImg = board[i][j].getClearing(5)
									// .getAllCharactersOnClearing()
									// .get(p).getImage();
									// newimg = tempImg.getScaledInstance(50,
									// 50,
									// java.awt.Image.SCALE_SMOOTH);
									// lbl_GuardImages[temp + p]
									// .setIcon(new ImageIcon(newimg));
								}
							}

						} else if (board[i][j].getChitAt(0).getChitName()
								.equals("SMOKE")
								&& board[i][j].isTilePlaced() == 1) {
							Point d = board[i][j].getClearingLocation(5);
							board[i][j].getClearing(5).setDwelling(
									dwellings.getHouse());
							board[i][j].getClearing(5).getDwelling()
							.setLocation(d);
							board[i][j].getClearing(5).getDwelling()
							.setClearingOn(board[i][j].getClearing(5));
							board[i][j].getClearing(5).getDwelling()
							.setTileOn(board[i][j].getName());
							// PLACING CHARACTERS
							for (int k = 0; k < playerList.size(); k++) {
								if (playerList.get(k).getPlayerCharacter()
										.getStartingLocation().equals("HOUSE")) {

									playerList
									.get(k)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(k)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(k)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(k)
											.getPlayerCharacter());

									// Send own details to the server
									// MRMessage mrm = new MRMessage(
									// MRMessage.PLAYER_POSITION_DETAILS,
									// k
									// + "#"
									// + i
									// + "#"
									// + j
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getXLocation()
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getYLocation()
									// + "#" + 5 + "#"
									// + board[i][j].getName());
									// ClientGUI.mr.sendMessage(mrm);
									System.out.println(playerList.get(k)
											.getPlayerCharacter()
											.getTileLocation()
											+ ": "
											+ playerList.get(k)
											.getPlayerCharacter()
											.getClearingLocation()
											.getClearingNum());

								}
							}
							// PLACING GARRISONS
							for (int k = 0; k < tsc.garrisons.getHouse().size(); k++) {
								if (tsc.garrisons.getHouse().get(k)
										.getItemType() == 'A') {
									board[i][j].getClearing(5).addArmor(
											(Armor) tsc.garrisons.getHouse()
											.get(k));
								} else if (tsc.garrisons.getHouse().get(k)
										.getItemType() == 'W') {
									board[i][j].getClearing(5).addWeapon(
											(Weapons) tsc.garrisons.getHouse()
											.get(k));
								} else if (tsc.garrisons.getHouse().get(k)
										.getItemType() == 'M') {
									board[i][j].getClearing(5).addMonster(
											(Monster) tsc.garrisons.getHouse()
											.get(k));
								} else if (tsc.garrisons.getHouse().get(k)
										.getItemType() == 'N') {
									board[i][j].getClearing(5).addNatives(
											(NativeGroups) tsc.garrisons
											.getHouse().get(k));
								} else if (tsc.garrisons.getHouse().get(k)
										.getItemType() == 'T') {
									board[i][j].getClearing(5).addTreasure(
											(TreasureCard) tsc.garrisons
											.getHouse().get(k));
								}
							}

							for (int c = 0; c < board[i][j].getClearing(5)
									.getAllItems().size(); c++) {
								// tempImg = board[i][j].getClearing(5)
								// .getAllItems().get(c).getImage();
								// newimg = tempImg.getScaledInstance(50, 50,
								// java.awt.Image.SCALE_SMOOTH);
								// lbl_HouseImages[c]
								// .setIcon(new ImageIcon(newimg));
							}

							int temp = board[i][j].getClearing(5).getAllItems()
									.size();
							for (int p = 0; p < board[i][j].getClearing(5)
									.getAllCharactersOnClearing().size(); p++) {
								// tempImg = board[i][j].getClearing(5)
								// .getAllCharactersOnClearing().get(p)
								// .getImage();
								// newimg = tempImg.getScaledInstance(50, 50,
								// java.awt.Image.SCALE_SMOOTH);
								// lbl_HouseImages[temp + p]
								// .setIcon(new ImageIcon(newimg));
							}

						}

						else if (board[i][j].getChitAt(0).getChitName()
								.equals("STINK")
								&& board[i][j].isTilePlaced() == 1) {
							Point d = board[i][j].getClearingLocation(5);
							board[i][j].getClearing(5).setDwelling(
									dwellings.getInn());
							board[i][j].getClearing(5).getDwelling()
							.setLocation(d);
							board[i][j].getClearing(5).getDwelling()
							.setClearingOn(board[i][j].getClearing(5));
							board[i][j].getClearing(5).getDwelling()
							.setTileOn(board[i][j].getName());
							// PLACING CHARACTERS
							for (int k = 0; k < playerList.size(); k++) {
								if (playerList.get(k).getPlayerCharacter()
										.getStartingLocation().equals("INN")) {
									playerList
									.get(k)
									.getPlayerCharacter()
									.setPLocation(
											board[i][j].getClearing(5)
											.getXLocation(),
											board[i][j].getClearing(5)
											.getYLocation());
									playerList
									.get(k)
									.getPlayerCharacter()
									.setClearingLocation(
											board[i][j].getClearing(5));
									playerList
									.get(k)
									.getPlayerCharacter()
									.setBoardLocation(
											board[i][j].getName());
									board[i][j].getClearing(5).placeCharacter(
											playerList.get(k)
											.getPlayerCharacter());

									// Send own details to the server
									// MRMessage mrm = new MRMessage(
									// MRMessage.PLAYER_POSITION_DETAILS,
									// k
									// + "#"
									// + i
									// + "#"
									// + j
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getXLocation()
									// + "#"
									// + board[i][j]
									// .getClearing(5)
									// .getYLocation()
									// + "#" + 5 + "#"
									// + board[i][j].getName());
									// ClientGUI.mr.sendMessage(mrm);
									System.out.println(playerList.get(k)
											.getPlayerCharacter()
											.getTileLocation()
											+ ": "
											+ playerList.get(k)
											.getPlayerCharacter()
											.getClearingLocation()
											.getClearingNum());

								}
							}
							// PLACING GARRISONS
							for (int k = 0; k < tsc.garrisons.getInn().size(); k++) {
								if (tsc.garrisons.getInn().get(k).getItemType() == 'A') {
									board[i][j].getClearing(5).addArmor(
											(Armor) tsc.garrisons.getInn().get(
													k));
								} else if (tsc.garrisons.getInn().get(k)
										.getItemType() == 'W') {
									board[i][j].getClearing(5).addWeapon(
											(Weapons) tsc.garrisons.getInn()
											.get(k));
								} else if (tsc.garrisons.getInn().get(k)
										.getItemType() == 'M') {
									board[i][j].getClearing(5).addMonster(
											(Monster) tsc.garrisons.getInn()
											.get(k));
								} else if (tsc.garrisons.getInn().get(k)
										.getItemType() == 'N') {
									board[i][j].getClearing(5).addNatives(
											(NativeGroups) tsc.garrisons
											.getInn().get(k));
								} else if (tsc.garrisons.getInn().get(k)
										.getItemType() == 'T') {
									board[i][j].getClearing(5).addTreasure(
											(TreasureCard) tsc.garrisons
											.getInn().get(k));
								}
							}

							for (int c = 0; c < board[i][j].getClearing(5)
									.getAllItems().size(); c++) {
								// tempImg = board[i][j].getClearing(5)
								// .getAllItems().get(c).getImage();
								// newimg = tempImg.getScaledInstance(50, 50,
								// java.awt.Image.SCALE_SMOOTH);
								// lbl_InnImages[c].setIcon(new
								// ImageIcon(newimg));
							}

							int temp = board[i][j].getClearing(5).getAllItems()
									.size();
							for (int p = 0; p < board[i][j].getClearing(5)
									.getAllCharactersOnClearing().size(); p++) {
								// tempImg = board[i][j].getClearing(5)
								// .getAllCharactersOnClearing().get(p)
								// .getImage();
								// newimg = tempImg.getScaledInstance(50, 50,
								// java.awt.Image.SCALE_SMOOTH);
								// lbl_InnImages[temp + p].setIcon(new
								// ImageIcon(
								// newimg));
							}

						}
					}

				}
			}
		}
		// test = 1;
	}

	public void setTilePlaced1() {
		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				if (board[i][j].isTilePlaced() == 0) {
					board[i][j].setTilePlaced(1);
					// tileP++;
				}
			}
		}
	}

	public void unsetTilePlaced1() {
		for (int i = 0; i < BSIZE; i++) {
			for (int j = 0; j < BSIZE; j++) {
				if (board[i][j].isTilePlaced() == 1) {
					board[i][j].setTilePlaced(0);
					// tileP++;
				}
			}
		}
	}

	public void startSetupBoard() {
		if (board1 == true)
			initGame1();
		else
			initGame2();
		if (tileP < 20) {
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
			for (int i = 0; i < playerList.size(); i++) {
				playerList.get(i).getPlayerCharacter()
				.setPathways(buildPathway());
			}

		} else if (tileP == 20) {
			System.out.println("Board is already setup");
			// System.out.println(e.getX() + ", " + e.getY());
			// boardInit();
			// counter++;
		}
	}

	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	private void display(String msg) {
		String time = sdf.format(new Date()) + " " + msg;
		if (sg == null)
			System.out.println(time);
		else
			sg.appendEvent(time + "\n");
	}

	public void start() {
		keepGoing = true;
		/* create socket server and wait for connection requests */
		try {
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while (keepGoing) {
				// format message saying we are waiting
				display("Server waiting for Clients on port " + port + ".");

				Socket socket = serverSocket.accept(); // accept connection

				if (!acceptClients) {
					// respond with not accepting clients at this moment
					// create output first
					ObjectOutputStream oos = new ObjectOutputStream(
							socket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(
							socket.getInputStream());
					// if Client is still connected send the message to it
					if (!socket.isConnected()) {
						continue;
					}
					// write the message to the stream
					try {
						MRMessage msgg = new MRMessage(
								MRMessage.NOT_ACCEPTING_PLAYERS, "");
						oos.writeObject(msgg);
						ois.close();
						oos.close();
						socket.close();

					}
					// if an error occurs, do not abort just inform the user
					catch (IOException e) {
						display("Error sending message to client");
						display(e.toString());
					}
					continue;
				}
				// if I was asked to stop
				if (!keepGoing)
					break;
				ClientThread t = new ClientThread(socket); // make a thread of
				// it
				al.add(t); // save it in the ArrayList
				t.start();
			}
			// I was asked to stop
			try {
				serverSocket.close();
				for (int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
						tc.sInput.close();
						tc.sOutput.close();
						tc.socket.close();
					} catch (IOException ioE) {
						// not much I can do
					}
				}
			} catch (Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
			String msg = sdf.format(new Date())
					+ " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}

	/*
	 * To run as a console application just open a console window and:
	 * 
	 * > java Server > java Server portNumber
	 * 
	 * If the port number is not specified 1500 is used
	 */
	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified
		int portNumber = 1500;
		switch (args.length) {
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Invalid port number.");
				System.out.println("Usage is: > java Server [portNumber]");
				return;
			}
		case 0:
			break;
		default:
			System.out.println("Usage is: > java Server [portNumber]");
			return;

		}
		// create a server object and start it
		MRServer server = new MRServer(portNumber);
		server.start();
	}

	/*
	 * to broadcast a message to all Clients
	 */
	public synchronized void broadcast(String message) {
		// add HH:mm:ss and \n to the message
		String time = sdf.format(new Date());
		String messageLf = time + " " + message + "\n";
		MRMessage mr = new MRMessage(MRMessage.INFO, messageLf);
		// display message on console or GUI
		if (sg == null)
			System.out.print(messageLf);
		else
			sg.appendData(messageLf); // append in the room window

		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if (!ct.writeMsg(mr)) {
				al.remove(i);
				display("Disconnected Client " + ct.username
						+ " removed from list.");
			}
		}
	}

	/*
	 * This function broadcasts the necessary elements of playerList variable to
	 * all the clients
	 */
	public synchronized void broadcast(int messageType, String msg) {

		MRMessage mr = new MRMessage(messageType, msg);
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if (!ct.writeMsg(mr)) {
				al.remove(i);
				ClientInfo ci = getClientInfoById(clients, i);
				clients.remove(ci);
				display("Disconnected Client " + ct.username
						+ " removed from list.");
			}
		}
	}

	/*
	 * This function broadcasts the necessary elements of playerList variable to
	 * all the clients
	 */
	public synchronized void broadcast(int messageType, MRMessage mr) {

		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if (!ct.writeMsg(mr)) {
				al.remove(i);
				ClientInfo ci = getClientInfoById(clients, i);
				clients.remove(ci);
				display("Disconnected Client " + ct.username
						+ " removed from list.");
			}
		}
	}

	/*
	 * to broadcast a message to all Clients withtime if set, sends time details
	 * alongwith the message
	 */
	public synchronized void broadcast(String message, boolean withtime,
			int messageType, ClientThread exceptThisClient, Object obj) {
		// add HH:mm:ss and \n to the message
		String time = sdf.format(new Date());
		String messageLf;
		MRMessage mr = null;
		if (withtime)
			messageLf = time + " " + message + "\n";
		else
			messageLf = "";

		// display message on console or GUI
		if (sg == null)
			System.out.print(messageLf);
		else
			sg.appendData(messageLf); // append in the room window
		if (obj != null) {
			// is an object
			if (obj instanceof ArrayList<?>) {
				ArrayList<ClientInfo> arCI = (ArrayList<ClientInfo>) obj;
				for (ClientInfo ci : arCI) {
					messageLf += ci.getClientName() + "#";
				}
				if (messageLf.length() > 1)
					messageLf = messageLf.substring(0, messageLf.length() - 1);
			}
			mr = new MRMessage(messageType, messageLf);
		} else {
			mr = new MRMessage(messageType, messageLf);
		}

		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected
		for (int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			if (exceptThisClient != null && ct == exceptThisClient) {
				continue;
			}
			// try to write to the Client if it fails remove it from the list
			if (!ct.writeMsg(mr)) {
				al.remove(i);
				ClientInfo ci = getClientInfoById(clients, i);
				clients.remove(ci);
				display("Disconnected Client " + ct.username
						+ " removed from list.");
			}
		}
	}

	// for a client who logoff using the LOGOUT message
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for (int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// found it
			if (ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}

	// /////////////////////////////////////////////////////////////

	// Functions borrowed from PlayerGUI Below
	// //////////////////////////////////////////////////////////////

	// Function to get character List
	public String[] getCharList() {
		String[] characterStrings = { "Amazon", "Captain", "Swordsman",
				"Dwarf", "Elf", "Black Knight", "White Knight", "Berserker" };
		return characterStrings;
	}

	// Function to get character Location list
	public String[] getCharLocation() {
		String[] strList = new String[] { "INN", "HOUSE", "GUARD", "CHAPEL" };
		return strList;
	}

	// Function to get Character Object from String name
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

	// ///////////////////////////////////////////////////////////

	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for disconnection)
		int id;

		String username;

		// the only type of message a will receive
		MRMessage mrMessage;
		// the date I connect
		String date;

		// Constructore
		ClientThread(Socket socket) {
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			/* Creating both Data Stream */
			System.out
			.println("Thread trying to create Object Input/Output Streams");
			try {
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput = new ObjectInputStream(socket.getInputStream());
				// read the username
				Object obj = (Object) sInput.readObject();
				if (obj instanceof String) {
					username = (String) sInput.readObject();
				} else if (obj instanceof MRMessage) {
					username = ((MRMessage) obj).getMessage();
				}

				ClientInfo ci = new ClientInfo(username, "", username, id);
				clients.add(ci);
				// broadcast("JOIN", false, MRMessage.CLIENT_PLAYER_PANEL_INFO,
				// null, clients);
				display(username + " just connected.");
			} catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			// have to catch ClassNotFoundException
			// but I read a String, I am sure it will work
			catch (ClassNotFoundException e) {
			}
			date = new Date().toString() + "\n";
		}

		// what will run forever
		public void run() {
			// to loop until EXIT
			boolean keepGoing = true;
			while (keepGoing) {
				// read a String (which is an object)
				try {
					mrMessage = (MRMessage) sInput.readObject();
				} catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					// Possibly client lost connection or client closed his/her
					// application
					// remove from our list of clients

					break;
				} catch (ClassNotFoundException e2) {
					e2.printStackTrace();
					break;
				}
				// the message part of the MRMessage
				String message = mrMessage.getMessage();

				MRMessage mr;
				// Switch on the type of message receive

				String cInfo = message;
				String[] ccis;
				int playerNumber = -1;

				switch (mrMessage.getType()) {

				case MRMessage.SET_GAME_MODE:
					cInfo = message;
					String [] ciss = cInfo.split("-");
					gameMode = Integer.parseInt(ciss[0]);
					board1 = (Integer.parseInt(ciss[1])==0)?true:false;

					if (gameMode == 0) {
						Cmview.setCheatMode(false);
						startSetupBoard();
						mr = new MRMessage(
								MRMessage.INITIALIZE_FIRST_PLAYER_RANDOM, "");
						writeMsg(mr);
						isGameModeSet = true;

					} else {
						// gameMode = 1
						// CheatMode
						Cmview.setCheatMode(true);
						mr = new MRMessage(
								MRMessage.INITIALIZE_FIRST_PLAYER_CHEAT, "");
						writeMsg(mr);
					}

					break;

				case MRMessage.CONNECT:
					// broadcast(username + ": " + message);
					broadcast("JOIN", false,
							MRMessage.CLIENT_PLAYER_PANEL_INFO, null, clients);

					break;

				case MRMessage.DISCONNECT:
					display(username + " disconnected with a LOGOUT message.");
					keepGoing = false;
					break;

				case MRMessage.INFO:

					String msg = "List of the users connected at "
							+ sdf.format(new Date()) + "";
					mr = new MRMessage(MRMessage.INFO,
							"List of the users connected at "
									+ sdf.format(new Date()) + "");
					// writeMsg(mr);
					// scan al the users connected
					for (int i = 0; i < al.size(); ++i) {
						ClientThread ct = al.get(i);

						writeMsg(new MRMessage(MRMessage.INFO, (i + 1) + ") "
								+ ct.username + " since " + ct.date));

					}
					break;

				case MRMessage.GET_CHARACTERS:
					if (!isGameModeSet && !isFirstPlayer) {
						// First Player initialization has not yet finished
						// We can not accept other players as board details are
						// unknown
						mr = new MRMessage(MRMessage.BOARD_NOT_INITIALIZED, "");
						writeMsg(mr);
						break;
					} else if (!isGameModeSet && isFirstPlayer) {
						// case for the first player
						// ask him for the game mode
						if (isCheatSetup == false) {
							mr = new MRMessage(MRMessage.ASK_GAME_MODE, "");
							writeMsg(mr);
							isCheatSetup = true;
						}
						if (isCheatSetup == true) {
							//							mr = new MRMessage(MRMessage.SET_GAME_BOARD, "");
							//							writeMsg(mr);
							isFirstPlayer = false;
						}

						//
						break;
					}
					String chars = "";
					for (String a : availableCharacterList)
						chars += a + ",";
					chars.substring(0, chars.length() - 1);
					mr = new MRMessage(MRMessage.GET_CHARACTERS, chars);
					writeMsg(mr);
					break;

					/**
					 * Client sends details of itself on joining the game
					 */
				case MRMessage.PLAYER_DETAILS:
					String[] details = message.split("#");

					String playerName = details[0];
					String characterChosen = details[1];
					String locationChosen = details[2];

					// TODO do we really need counter from client?
					// It is rather a server variable
					// String counter = details[3];
					Player p = new Player(counter, playerName,
							getCharObj(characterChosen));
					p.getPlayerCharacter().setStartingLocation(locationChosen);
					playerList.add(counter++, p);

					setClearingsForPlayer(p.getPlayerId());
					// startSetupBoard();
					//setTilePlaced1();
					//placeDwellings();
					//unsetTilePlaced1();

					// remove the chosen character from the available list
					// of characters
					availableCharacterList.remove(characterChosen);

					// send success or failure message
					// along with the game mode 0=random, 1=cheat
					mr = new MRMessage(MRMessage.PLAYER_DETAILS, "SUCCESS#"
							+ message + "#" + p.getPlayerId() + "#"
							+ (CheatModeGUI.CheatMode ? "1" : "0") + "#"
							+ (board1 ? "0" : "1"));
					writeMsg(mr);

					// broadcast all players to all clients
					// TODO
					//
					String msgg = "";
					for (int i = 0; i < playerList.size(); i++) {
						msgg += playerList.get(i).getPlayerName()
								+ "-"
								+ playerList.get(i).getPlayerCharacter()
								.getName()
								+ "-"
								+ playerList.get(i).getPlayerId()
								+ "-"
								+ playerList.get(i).getPlayerCharacter()
								.getStartingLocation() + "#";
					}
					broadcast(MRMessage.PLAYER_LIST, msgg);

					break;

					/**
					 * Set players victory requirements
					 */
				case MRMessage.PLAYER_VICTORY_REQUIREMENTS:
					String[] reqs = message.split("#");
					String clientInfo = reqs[0];
					String[] cis = clientInfo.split("-");
					int buttonNumber = getPlayerIndexFromNameAndCharacter(
							cis[0], cis[1]);
					int nG = Integer.parseInt(reqs[1]);
					int nF = Integer.parseInt(reqs[2]);
					int nN = Integer.parseInt(reqs[3]);

					int gP = Integer.parseInt(reqs[1]);
					int fP = Integer.parseInt(reqs[2]);
					int nP = Integer.parseInt(reqs[3]);

					playerList.get(buttonNumber).getPlayerCharacter()
					.setNeedGold(nG);
					playerList.get(buttonNumber).getPlayerCharacter()
					.setNeedFame(nF);
					playerList.get(buttonNumber).getPlayerCharacter()
					.setNeedNotoriety(nN);

					playerList.get(buttonNumber).getPlayerCharacter()
					.setGoldPoint(gP);
					playerList.get(buttonNumber).getPlayerCharacter()
					.setFamePoint(fP);
					playerList.get(buttonNumber).getPlayerCharacter()
					.setNotorietyPoint(nP);

					break;

				case MRMessage.ACTION_HIDE_REQ:
					// This message is sent by the client
					// The server needs to send this update to all the clients
					// 0 - Player Name
					// 1 - Character Name
					// 2 - Player Location
					// 3 - DicePanel ans

					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("-");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],
							ccis[1]);
					playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
					Activity.hide(playerList.get(playerNumber).getPlayerCharacter(), Integer.parseInt(ccis[3]));
					msgg = playerNumber
							+ "-"
							+ playerList.get(playerNumber).getPlayerCharacter()
							.isHidden();

					broadcast(MRMessage.HIDE_PLAYER, msgg);
					if (ServerGUI.gameStarted && playerList.get(playerNumber).getPlayerCharacter().getPhases() == playerList.get(playerNumber).getPlayerCharacter().getMoves()) {
						// we have finished the turn of this player
						if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1) {
							playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
						}
						else{
							playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
							playerList.get(playerNumber).getPlayerCharacter().resetPhase();
							ServerGUI.monsterTime = true;
						}
					}
					break;
				case MRMessage.BLOCK:
					// This message is sent by the client
					// The server needs to send this update to all the clients
					// 0 - Player Name
					// 1 - Character Name
					// 2 - Player Location
					// 3 - DicePanel ans

					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("-");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],ccis[1]);
					Activity.block(playerList.get(playerNumber).getPlayerCharacter());
					if (ServerGUI.gameStarted && playerList.get(playerNumber).getPlayerCharacter().getPhases() == playerList.get(playerNumber).getPlayerCharacter().getMoves()) {
						// we have finished the turn of this player
						if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1) {
							playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
						}
						else{
							playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
							playerList.get(playerNumber).getPlayerCharacter().resetPhase();
							ServerGUI.monsterTime = true;
						}
					}
					break;
				case MRMessage.ACTION_ALERT:
					// There is an alert weapon message from the client
					// 0 - Player Name
					// 1 - Character Name
					// 2 - Player Location
					// 3 - selected Weapon as String

					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("-");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],
							ccis[1]);
					playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
					//Character c = playerList.get(playerNumber).getPlayerCharacter();
					// TODO 27 March
					for (int i = 0; i < playerList.get(playerNumber).getPlayerCharacter().getweaponList().size(); i++) {
						if (ccis[3].toLowerCase().equals(playerList.get(playerNumber).getPlayerCharacter().getweaponList().get(i).getName().toLowerCase())) {
							playerList.get(playerNumber).getPlayerCharacter().getweaponList().get(i).alert();
							System.out.println("Changed the player "
									+ playerList.get(playerNumber)
									.getPlayerName()
									+ "'s alert weapon to " + ccis[3]);

							break;
						}
					}
					if (ServerGUI.gameStarted && playerList.get(playerNumber).getPlayerCharacter().getPhases() == playerList.get(playerNumber).getPlayerCharacter().getMoves()) {
						// we have finished the turn of this player
						if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 2) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && !playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Dwarf")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
							else{
								playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
								playerList.get(playerNumber).getPlayerCharacter().resetPhase();
								ServerGUI.monsterTime = true;
							}
						}if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 3) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Captain")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
						}else{
							playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
							playerList.get(playerNumber).getPlayerCharacter().resetPhase();
							ServerGUI.monsterTime = true;
						}
					}
					break;
				case MRMessage.REST:
					// There is an alert weapon message from the client
					// 0 - Player Name
					// 1 - Character Name
					// 2 - Player Location
					// 3 - selected Weapon as String

					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("-");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],
							ccis[1]);
					if(playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Berserker")||playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("White Knight")) {
						if(playerList.get(playerNumber).getPlayerCharacter().isFeatureUsed() == false) {
							//allow extra turn
							playerList.get(playerNumber).getPlayerCharacter().extraFeatureUsed();
						}
						else {
							playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
						}
					}
					else{
						playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
					}

					//Character c = playerList.get(playerNumber).getPlayerCharacter();
					// TODO 27 March
					String[] choice = ccis[3].trim().split("#");
					if(choice[1] == "FATIGUE"){
						for(int i = 0; i < playerList.get(playerNumber).getPlayerCharacter().getActionChits().size(); i++) {
							if(playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).getName().equalsIgnoreCase(choice[0])) {
								playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).unFatigue();
								playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).activate();
							}
						}
					}else if(choice[1] =="WOUNDEDFATIGUES") {
						for(int i = 0; i < playerList.get(playerNumber).getPlayerCharacter().getActionChits().size(); i++) {
							if(playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).getName().equalsIgnoreCase(choice[0])) {
								playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).unWounded();
								playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).makeFatigued();
							}
						}
					}else if(choice[1] == "WOUNDS") {
						for(int i = 0; i < playerList.get(playerNumber).getPlayerCharacter().getActionChits().size(); i++) {
							if(playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).getName().equalsIgnoreCase(choice[0])) {
								playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).unFatigue();
								playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).unWounded();
								playerList.get(playerNumber).getPlayerCharacter().getActionChits().get(i).activate();
							}
						}
					}
					if (ServerGUI.gameStarted && playerList.get(playerNumber).getPlayerCharacter().getPhases() == playerList.get(playerNumber).getPlayerCharacter().getMoves()) {
						// we have finished the turn of this player
						if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 2) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && !playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Dwarf")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
							else{
								playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
								playerList.get(playerNumber).getPlayerCharacter().resetPhase();
								ServerGUI.monsterTime = true;
							}
						}if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 3) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Captain")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
						}else{
							playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
							playerList.get(playerNumber).getPlayerCharacter().resetPhase();
							ServerGUI.monsterTime = true;
						}
					}
					break;
				case MRMessage.TRADE:
					// There is an alert weapon message from the client
					// 0 - Player Name
					// 1 - Character Name
					// 2 - Player Location
					// 3 - selected as String

					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("-");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],
							ccis[1]);
					int clearing = Integer.parseInt(ccis[5].trim());
					int nx = Integer.parseInt(ccis[3].trim());
					int ny = Integer.parseInt(ccis[4].trim());
					playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
					//Character c = playerList.get(playerNumber).getPlayerCharacter();
					// TODO 27 March
					choice = ccis[2].trim().split("#");
					if(choice[0] == "SELL") {
						for(int i = 0; i < playerList.get(playerNumber).getPlayerCharacter().getItemsOwned().size(); i++) {
							if(playerList.get(playerNumber).getPlayerCharacter().getItemsOwned().get(i).getName().equalsIgnoreCase(choice[1])) {
								playerList.get(playerNumber).getPlayerCharacter().getItemsOwned().remove(i);
								playerList.get(playerNumber).getPlayerCharacter().setGoldRecorded(Integer.parseInt(choice[2]));
								break;
							}
						}
					}
					else if(choice[0] == "BUY"){
						for(int j = 0; j < board[nx][ny].getClearing(clearing).getNativeGroup(0).getAllNatives().size(); j++) {
							if(board[nx][ny].getClearing(clearing).getNativeGroup(0).getNative(j).isLeader() == true) {
								for(int i = 0; i < board[nx][ny].getClearing(clearing).getNativeGroup(0).getNative(j).getItems().size(); i++) {
									if(board[nx][ny].getClearing(clearing).getNativeGroup(0).getNative(j).getItems().get(i).getName().equalsIgnoreCase(choice[1])){
										Item temp = board[nx][ny].getClearing(clearing).getNativeGroup(0).getNative(j).getItems().get(i);
										playerList.get(playerNumber).getPlayerCharacter().getItemsOwned().add(temp);
										playerList.get(playerNumber).getPlayerCharacter().setGoldRecorded(Integer.parseInt(choice[2]));
									}
								}
							}
						}
					}
					else {
						System.out.println(choice);
					}
					if (ServerGUI.gameStarted && playerList.get(playerNumber).getPlayerCharacter().getPhases() == playerList.get(playerNumber).getPlayerCharacter().getMoves()) {
						// we have finished the turn of this player
						if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 2) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && !playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Dwarf")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
							else{
								playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
								playerList.get(playerNumber).getPlayerCharacter().resetPhase();
								ServerGUI.monsterTime = true;
							}
						}if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 3) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Captain")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
						}else{
							playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
							playerList.get(playerNumber).getPlayerCharacter().resetPhase();
							ServerGUI.monsterTime = true;
						}
					}
					break;
				case MRMessage.PLAYER_POSITION_DETAILS:
					// 0 - playerId
					// 1 - Clearing X Location
					// 2 - Clearing Y Location
					// 3 - Get Clearing
					// 4 - Board Name

					cInfo = message;
					ccis = cInfo.split("#");
					playerNumber = Integer.parseInt(ccis[0]);
					int ii = Integer.parseInt(ccis[1]);
					int jj = Integer.parseInt(ccis[2]);
					int xloc = Integer.parseInt(ccis[3]);
					int yloc = Integer.parseInt(ccis[4]);
					int cid = Integer.parseInt(ccis[5]);
					String boardName = ccis[6];

					if(playerList.get(playerNumber).getPlayerCharacter().getName().toLowerCase() == "captain") {
						playerList.get(playerNumber).getPlayerCharacter().setMoves(3);
					}
					playerList.get(playerNumber).getPlayerCharacter().setPLocation(board[ii][jj].getClearing(5).getXLocation(),board[ii][jj].getClearing(5).getYLocation());
					playerList.get(playerNumber).getPlayerCharacter().setClearingLocation(board[ii][jj].getClearing(5));
					playerList.get(playerNumber).getPlayerCharacter().setBoardLocation(board[ii][jj].getName());
					board[ii][jj].getClearing(5).placeCharacter(playerList.get(playerNumber).getPlayerCharacter());
					currClearing = playerList.get(playerNumber).getPlayerCharacter().getClearingLocation().getClearingNum();
					// broadcast this information to all clients
					msgg = "";

					for (Player pp : playerList) {
						msgg += pp.getPlayerId() + "#"
								+ pp.getPlayerCharacter().getPLocation().x
								+ "#"
								+ pp.getPlayerCharacter().getPLocation().y + "";
					}

					broadcast(MRMessage.PLAYER_POSITION_DETAILS, msgg
							+ "#"
							+ playerList.get(playerNumber).getPlayerCharacter()
							.isHidden());
					break;

				case MRMessage.GET_CHITS:

					switch (message.toLowerCase()) {

					case "app":
						//mr = new MRMessage(MRMessage.GET_CHITS, "board");
						//mr.setObject(board);
						//writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "app");
						mr.setObject(appChart);
						writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "LostCity");
						mr.setObject(lostCity);
						writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "LostCastle");
						mr.setObject(lostCastle);
						writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "cave");
						mr.setObject(caveChit);
						writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "mountain");
						mr.setObject(mountainChit);
						writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "wood");
						mr.setObject(woodsChit);
						writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "valley");
						mr.setObject(valleyChit);
						writeMsg(mr);
						mr = new MRMessage(MRMessage.GET_CHITS, "random");
						mr.setObject(random);
						writeMsg(mr);
						break;

					}
					break;
				case MRMessage.GET_CHIT_NAMES:
					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "cave");
					mr.setObject(CheatModeGUI.cavesName);
					writeMsg(mr);
					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "mountain");
					mr.setObject(CheatModeGUI.mountainName);
					writeMsg(mr);
					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "wood");
					mr.setObject(CheatModeGUI.woodsName);
					writeMsg(mr);
					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "valley");
					mr.setObject(CheatModeGUI.valleyName);
					writeMsg(mr);
					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "sound");
					mr.setObject(CheatModeGUI.soundName);
					writeMsg(mr);
					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "site");
					mr.setObject(CheatModeGUI.siteName);
					writeMsg(mr);

					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "LostCity");
					mr.setObject(CheatModeGUI.FinaLCity);
					writeMsg(mr);

					mr = new MRMessage(MRMessage.GET_CHIT_NAMES, "LostCastle");
					mr.setObject(CheatModeGUI.FinaLCastle);
					writeMsg(mr);
					break;

				case MRMessage.SET_CHITS:
					switch (message.toLowerCase()) {

					case "cave":
						ArrayList<String> chits = ((ArrayList<String>) mrMessage
								.getObject());
						CheatModeGUI.cavesName = chits;
						break;
					case "mountain":
						chits = ((ArrayList<String>) mrMessage.getObject());
						CheatModeGUI.mountainName = chits;
						break;
					case "wood":
						chits = ((ArrayList<String>) mrMessage.getObject());
						CheatModeGUI.woodsName = chits;
						break;
					case "valley":
						chits = ((ArrayList<String>) mrMessage.getObject());
						CheatModeGUI.valleyName = chits;
						break;
					case "sound":
						chits = ((ArrayList<String>) mrMessage.getObject());
						CheatModeGUI.soundName = chits;
						break;
					case "site":
						chits = ((ArrayList<String>) mrMessage.getObject());
						CheatModeGUI.siteName = chits;
						break;

					case "LostCity":
						chits = ((ArrayList<String>) mrMessage.getObject());
						CheatModeGUI.FinaLCity = chits;
						break;

					case "LostCastle":
						chits = ((ArrayList<String>) mrMessage.getObject());
						CheatModeGUI.FinaLCastle= chits;
						break;

					}

					break;

				case MRMessage.BOARD_INITIALIZED:
					startSetupBoard();
					mr = new MRMessage(
							MRMessage.INITIALIZE_FIRST_PLAYER_RANDOM, "");
					writeMsg(mr);
					isGameModeSet = true;

					break;

				case MRMessage.MOVE:
					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("-");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],ccis[1]);

					if(playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Amazon")) {
						if(playerList.get(playerNumber).getPlayerCharacter().isFeatureUsed() == false) {
							playerList.get(playerNumber).getPlayerCharacter().extraFeatureUsed();
						}
						else{
							playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
						}
					}else{
						playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
					}

					String result = Activity.move(playerList.get(playerNumber).getPlayerCharacter(), ccis[3], board);

					String move = playerNumber + "-" + message;
					mr = new MRMessage(MRMessage.EVERYONE_MOVE, move);
					// mr.setObject(playerList.get(playerNumber)
					// .getPlayerCharacter());
					//mr.setMessage(playerNumber + "-" + message);
					//writeMsg(mr);
					broadcast(MRMessage.EVERYONE_MOVE, mr);

					if (ServerGUI.gameStarted && playerList.get(playerNumber).getPlayerCharacter().getPhases() == playerList.get(playerNumber).getPlayerCharacter().getMoves()) {
						// we have finished the turn of this player
						if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 2) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && !playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Dwarf")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
							else{
								playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
								playerList.get(playerNumber).getPlayerCharacter().resetPhase();
								ServerGUI.monsterTime = true;
							}
						}if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 3) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Captain")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
						}else{
							playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
							playerList.get(playerNumber).getPlayerCharacter().resetPhase();
							ServerGUI.monsterTime = true;
						}
					}

					break;
				case MRMessage.ACTIVATE:
					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("-");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],ccis[1]);
					Activity.server_activateSomething(cInfo,playerList.get(playerNumber).getPlayerCharacter());
					break;

				case MRMessage.SET_GAME_BOARD:
					board1 = (message.equals("0") ? true : false);

					break;

				case MRMessage.SEARCH:
					//no_of_activities++;
					cInfo = message;
					ccis = cInfo.split("#");
					playerNumber = getPlayerIndexFromNameAndCharacter(ccis[0],ccis[1]);
					playerList.get(playerNumber).getPlayerCharacter().phasePlayed();
					int choiceFromPlayer = Integer.parseInt(ccis[3]);
					String searchText = ccis[4].trim();
					int diceRoll = Integer.parseInt(ccis[5]);
					String searchTableChoice = ccis[6].trim();
					ServerActivity.search(playerList.get(playerNumber).getPlayerCharacter(), searchText, diceRoll, board, choiceFromPlayer, searchTableChoice);
					if (ServerGUI.gameStarted && playerList.get(playerNumber).getPlayerCharacter().getPhases() == playerList.get(playerNumber).getPlayerCharacter().getMoves()) {
						// we have finished the turn of this player
						if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 2) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && !playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Dwarf")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
							else{
								playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
								playerList.get(playerNumber).getPlayerCharacter().resetPhase();
								ServerGUI.monsterTime = true;
							}
						}if(playerList.get(playerNumber).getPlayerCharacter().getPhases() == 3) {
							if(playerList.get(playerNumber).getPlayerCharacter().getSunlight() == 1 && playerList.get(playerNumber).getPlayerCharacter().getName().equalsIgnoreCase("Captain")) {
								playerList.get(playerNumber).getPlayerCharacter().setMoves(playerList.get(playerNumber).getPlayerCharacter().getMoves()+2);
							}
						}else{
							playerList.get(playerNumber).getPlayerCharacter().resetSunlight();
							playerList.get(playerNumber).getPlayerCharacter().resetPhase();
							ServerGUI.monsterTime = true;
						}
					}
					break;
				case MRMessage.RECEIVE_MONSTER_DICE:
					monsterRoll = Integer.parseInt(message.trim());
					msgg = "Todays Monster Roll is " + monsterRoll;
					mr = new MRMessage(MRMessage.TODAYS_DICE, msgg);
					//writeMsg(mr);
					broadcast(MRMessage.TODAYS_DICE, msgg);
					break;

				case MRMessage.REVEAL_CHIT:
					String[] info = message.split("#");
					tileName = info[0];
					currClearing = Integer.parseInt(info[1]);
					int locx = 0;
					int locy = 0;
					for(int i = 0; i < BSIZE; i++) {
						for(int j = 0; j <BSIZE; j++) {
							if(board[i][j].getName() != null)  {
								if(board[i][j].getName().equalsIgnoreCase(tileName)) {
									locx = i;
									locy = j;
									for(int k = 0; k < board[i][j].getAllChits().size(); k++) {
										board[i][j].getChitAt(k).unhide();
										if(board[i][j].getChitAt(k).getChitName() == "LOST CITY") {
											board[locx][locy].removeChit(k);
											for(int lc = 0; lc < tsc.lostCity.getChits().size(); lc++) {
												Chit temp = tsc.lostCity.getChitAt(lc);
												temp.unhide();
												board[locx][locy].addChit(temp);
											}
										}
										else if(board[i][j].getChitAt(k).getChitName() == "LOST CASTLE") {
											board[locx][locy].removeChit(k);
											for(int lc = 0; lc < tsc.lostCastle.getChits().size(); lc++) {
												Chit temp = tsc.lostCastle.getChitAt(lc);
												temp.unhide();
												board[locx][locy].addChit(temp);
											}
										}
									}
								}
							}
						}
					}
					String ub = locx + "#" + locy + "#"+ currClearing + "";
					broadcast(MRMessage.CHIT_REVEALED, ub);
					break;

				case MRMessage.MONSTER_OUT:
					info = message.split("#");
					tileName = info[0];
					currClearing = Integer.parseInt(info[1]);
					for(int i = 0; i < BSIZE; i++) {
						for(int j = 0; j <BSIZE; j++) {
							if(board[i][j].getName() != null)  {
								if(board[i][j].getName().equalsIgnoreCase(tileName)) {
									System.out.println("I AM HERE: " + tileName);
									for(int k = 0; k < board[i][j].getAllClearings().length; k++) {
										if(board[i][j].getClearing(k) != null) {
											for(int mon = 0; mon < board[i][j].getClearing(k).getAllMonsters().size(); mon++) {
												board[i][j].getClearing(currClearing).addMonster(board[i][j].getClearing(k).removeMonster(mon));
											}
										}
									}
								}
							}
						}
					}
					String m = tileName + "#" + currClearing + "";
					broadcast(MRMessage.MAKE_MONSTER_OUT, m); //move monsters to players clearing
					break;
				case MRMessage.NEW_MONSTER:
					info = message.split("#");
					currClearing = Integer.parseInt(info[0]);
					locx = Integer.parseInt(info[1]);
					locy = Integer.parseInt(info[2]);
					int size = Integer.parseInt(info[3]);
					String[] mons = new String[size];
					for(int i = 0; i < size; i++) {
						mons[i] = info[i+4];
					}
					System.out.println("CurrClearing: " + currClearing);
					System.out.println("locx: " + locx);
					System.out.println("locy: " + locy);
					System.out.println("size: " + size);

					for(int i = 0; i < size; i++) {

						System.out.println("mons: " + mons[i]);
						String[] split = mons[i].split("-");

						System.out.println("split: " + split[0]);
						System.out.println("split: " + split[1]);
						char type = split[1].charAt(0);
						if(monsterRoll == 1) {
							if(split[0] == "FLUTTER") {
								if(tsc.lostCastle.counter1 == 0) {
									int loc = Integer.parseInt(split[1]);
									board[locx][locy].getClearing(loc).addMonster((Monster) tsc.lostCastle.getRollOne1());
									board[locx][locy].getClearing(loc).addMonster((Monster) tsc.lostCastle.getRollOne2());
									tsc.lostCastle.counter1++;
								}
							}
							if(split[0] == "SMOKE" && type == 'm') {
								if(tsc.lostCastle.counter1 == 0) {
									board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCastle.getRollOne1());
									board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCastle.getRollOne2());
									tsc.lostCastle.counter1++;
								}
							}
							if((split[0] == "SMOKE" && type == 'c') || split[0] == "SLITHER" || (split[0] == "ROAR" && type =='c')) {
								if(tsc.lostCity.counter1 < 2) {
									if(split[0] == "SLITHER") {
										int loc = Integer.parseInt(split[1]);
										for(int roll = 0; roll < tsc.lostCity.getRollOne().get(tsc.lostCity.counter1).size(); roll++) {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.lostCity.getRollOne().get(tsc.lostCity.counter1).get(roll));
										}
									}
									else {
										for(int roll = 0; roll < tsc.lostCity.getRollOne().get(tsc.lostCity.counter1).size(); roll++) {
											board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCity.getRollOne().get(tsc.lostCity.counter1).get(roll));
										}
									}
									tsc.lostCastle.counter1++;
								}
							}
							if(split[0] == "HOARD") {
								int loc = Integer.parseInt(split[1]);
								if(tsc.treasureLocation.counter1==0) {
									for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollOne1().size(); roll++) {
										if(tsc.treasureLocation.getTreasuresRollOne1().get(roll).getItemType() == 'M') {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollOne1().get(roll));
										}
										else {
											board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollOne1().get(roll));
										}

									}
								}
								tsc.treasureLocation.counter1++;
							}
							if(split[0] == "LAIR") {
								int loc = Integer.parseInt(split[1]);
								if(tsc.treasureLocation.counter2==0){
									for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollOne1().size(); roll++) {
										if(tsc.treasureLocation.getTreasuresRollOne2().get(roll).getItemType() == 'M') {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollOne2().get(roll));
										}
										else {
											board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollOne2().get(roll));
										}

									}
								}
								tsc.treasureLocation.counter2++;
							}
							if(split[0] == "D") {
								if(split[1] == "INN") {
									int loc = Integer.parseInt(split[2].trim());
									if(tsc.dwellings.counter1 == 0) {
										for(int dw = 0; dw <tsc.dwellings.getRollOne().size(); dw++) {
											if(tsc.dwellings.getRollOne().get(i).getItemType() == 'N') {
												NativeGroups temp = (NativeGroups) tsc.dwellings.getRollOne().get(dw);
												board[locx][locy].getClearing(loc).addNatives(temp);
											}else if(tsc.dwellings.getRollOne().get(i).getItemType() == 'N') {
												board[locx][locy].getClearing(loc).addSiteTreasures(tsc.dwellings.getRollOne().get(dw));
											}
										}
										tsc.dwellings.counter1++;
									}

								}

							}
						}
						else if(monsterRoll == 2) {
							if(split[0] == "DANK" && type == 'w') {
								if(tsc.lostCastle.counter2 == 0) {
									int loc = Integer.parseInt(split[1]);
									board[locx][locy].getClearing(loc).addMonster((Monster) tsc.lostCastle.getRollTwo1());
									board[locx][locy].getClearing(loc).addMonster((Monster) tsc.lostCastle.getRollTwo2());
									tsc.lostCastle.counter1++;
								}
							}
							if((split[0] == "DANK" && type == 'c') || split[0] == "SLITHER") {
								if(tsc.lostCity.counter2 < 2) {
									if(split[0] == "SLITHER") {
										int loc = Integer.parseInt(split[1]);
										for(int roll = 0; roll < tsc.lostCity.getRollTwo().get(tsc.lostCity.counter2).size(); roll++) {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.lostCity.getRollTwo().get(tsc.lostCity.counter2).get(roll));
										}
									}
									else {
										for(int roll = 0; roll < tsc.lostCity.getRollTwo().get(tsc.lostCity.counter2).size(); roll++) {
											board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCity.getRollTwo().get(tsc.lostCity.counter2).get(roll));
										}
									}
									tsc.lostCastle.counter2++;
								}
							}
							if(split[0] == "ALTER") {
								int loc = Integer.parseInt(split[1]);
								for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollTwo1().size(); roll++) {
									if(tsc.treasureLocation.getTreasuresRollTwo1().get(roll).getItemType() == 'M') {
										board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollTwo1().get(roll));
									}
									else {
										board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollTwo1().get(roll));
									}

								}
							}
							if(split[0] == "SHRINE") {
								int loc = Integer.parseInt(split[1]);
								for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollTwo2().size(); roll++) {
									if(tsc.treasureLocation.getTreasuresRollTwo2().get(roll).getItemType() == 'M') {
										board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollTwo2().get(roll));
									}
									else {
										board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollTwo2().get(roll));
									}

								}
							}
							if(split[0] == "D") {
								if(split[1] == "HOUSE") {
									int loc = Integer.parseInt(split[2].trim());
									if(tsc.dwellings.counter2 == 0) {
										for(int dw = 0; dw <tsc.dwellings.getRollTwo().size(); dw++) {
											if(tsc.dwellings.getRollTwo().get(i).getItemType() == 'N') {
												NativeGroups temp = (NativeGroups) tsc.dwellings.getRollTwo().get(dw);
												board[locx][locy].getClearing(loc).addNatives(temp);
											}else if(tsc.dwellings.getRollTwo().get(i).getItemType() == 'N') {
												board[locx][locy].getClearing(loc).addSiteTreasures(tsc.dwellings.getRollTwo().get(dw));
											}
										}
										tsc.dwellings.counter2++;
									}


								}

							}
						}
						else if(monsterRoll == 3) {
							if(split[0] == "RUINS" && type == 'w') {
								if(tsc.lostCastle.counter31 == 0) {
									for(int roll = 0; roll < tsc.lostCastle.getRollThree1().size(); roll++) {
										board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCastle.getRollThree1().get(roll));
									}
									tsc.lostCastle.counter31++;
								}
							}
							if(split[0] == "BONES" && type == 'W') {
								if(tsc.lostCastle.counter32 == 0) {
									for(int roll = 0; roll < tsc.lostCastle.getRollThree2().size(); roll++) {
										board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCastle.getRollThree2().get(roll));
									}
									tsc.lostCastle.counter32++;
								}
							}
							if((split[0] == "RUIN" && type == 'c') || (split[0] == "PATTER" && type == 'c') || (split[0] == "HOWL" && type == 'c')) {
								if(tsc.lostCity.counter3 < 2){
									for(int roll = 0; roll < tsc.lostCity.getRollThree().get(tsc.lostCity.counter3).size(); roll++) {
										board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCity.getRollThree().get(tsc.lostCity.counter3).get(roll));

									}
									tsc.lostCity.counter3++;
								}
							}
							if(split[0] == "POOL") {
								int loc = Integer.parseInt(split[1]);
								if(tsc.treasureLocation.counter5==0){
									for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollThree().size(); roll++) {
										if(tsc.treasureLocation.getTreasuresRollThree().get(roll).getItemType() == 'M') {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollThree().get(roll));
										}
										else {
											board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollThree().get(roll));
										}

									}
									tsc.treasureLocation.counter5++;
								}
							}
							if(split[0] == "D") {
								if(split[1] == "INN" || split[1] == "CHAPEL" || split[1] == "HOUSE" || split[1] == "GUARD") {
									int loc = Integer.parseInt(split[2].trim());
									if(tsc.dwellings.counter3 == 0) {
										for(int dw = 0; dw <tsc.dwellings.getRollThree().size(); dw++) {
											if(tsc.dwellings.getRollThree().get(i).getItemType() == 'N') {
												NativeGroups temp = (NativeGroups) tsc.dwellings.getRollThree().get(dw);
												board[locx][locy].getClearing(loc).addNatives(temp);
											}else if(tsc.dwellings.getRollThree().get(i).getItemType() == 'N') {
												board[locx][locy].getClearing(loc).addSiteTreasures(tsc.dwellings.getRollThree().get(dw));
											}
										}
										tsc.dwellings.counter3++;

									}

								}

							}
						}
						else if(monsterRoll == 4) {
							if((split[0] == "BONES" && type == 'm') || (split[0] == "ROAR" && type == 'm') || (split[0] == "STINK" && type == 'm')) {
								if(tsc.lostCastle.counter4 < 2){
									for(int roll = 0; roll < tsc.lostCastle.getRollFour().get(tsc.lostCastle.counter4).size(); roll++) {
										board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCastle.getRollFour().get(tsc.lostCastle.counter4).get(roll));

									}
									tsc.lostCastle.counter4++;
								}
							}
							if((split[0] == "STINK" && type == 'c') || (split[0] == "BONES" && type == 'c') || (split[0] == "ROAR" && type == 'c')) {
								if(tsc.lostCity.counter4 < 2){
									for(int roll = 0; roll < tsc.lostCity.getRollFour().get(tsc.lostCity.counter4).size(); roll++) {
										board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCity.getRollFour().get(tsc.lostCity.counter4).get(roll));
									}
									tsc.lostCity.counter4++;
								}
							}
							if(split[0] == "VAULT") {
								int loc = Integer.parseInt(split[1]);
								if(tsc.treasureLocation.counter6==0){
									for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollFour().size(); roll++) {
										if(tsc.treasureLocation.getTreasuresRollFour().get(roll).getItemType() == 'M') {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollFour().get(roll));
										}
										else {
											board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollFour().get(roll));
										}

									}
									tsc.treasureLocation.counter6++;
								}
							}
							if(split[0] == "D") {
								if(split[1] == "CHAPEL") {
									int loc = Integer.parseInt(split[2].trim());
									if(tsc.dwellings.counter4 == 0) {
										for(int dw = 0; dw <tsc.dwellings.getRollFour().size(); dw++) {
											if(tsc.dwellings.getRollFour().get(i).getItemType() == 'N') {
												NativeGroups temp = (NativeGroups) tsc.dwellings.getRollFour().get(dw);
												board[locx][locy].getClearing(loc).addNatives(temp);
											}else if(tsc.dwellings.getRollFour().get(i).getItemType() == 'N') {
												board[locx][locy].getClearing(loc).addSiteTreasures(tsc.dwellings.getRollFour().get(dw));
											}
										}

									}

								}

							}
						}
						else if(monsterRoll == 5) {
							if((split[0] == "STINK" && type == 'm') || (split[0] == "DANK" && type == 'm') || (split[0] == "PATTER" && type == 'm')) {
								if(tsc.lostCastle.counter5 < 2){
									for(int roll = 0; roll < tsc.lostCastle.getRollFive().get(tsc.lostCastle.counter5).size(); roll++) {
										board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCastle.getRollFive().get(tsc.lostCastle.counter5).get(roll));

									}
									tsc.lostCastle.counter5++;
								}
							}
							if(split[0] == "CAIRNS") {
								int loc = Integer.parseInt(split[1]);
								if(tsc.treasureLocation.counter7 == 0) {
									for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollFive1().size(); roll++) {
										if(tsc.treasureLocation.getTreasuresRollFive1().get(roll).getItemType() == 'M') {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollFive1().get(roll));
										}
										else {
											board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollFive1().get(roll));
										}

									}
									tsc.treasureLocation.counter7++;
								}
							}
							if(split[0] == "STATUE") {
								int loc = Integer.parseInt(split[1]);
								if(tsc.treasureLocation.counter8 == 0) {
									for(int roll = 0; roll < tsc.treasureLocation.getTreasuresRollFour().size(); roll++) {
										if(tsc.treasureLocation.getTreasuresRollFive2().get(roll).getItemType() == 'M') {
											board[locx][locy].getClearing(loc).addMonster((Monster) tsc.treasureLocation.getTreasuresRollFive2().get(roll));
										}
										else {
											board[locx][locy].getClearing(loc).addSiteTreasures(tsc.treasureLocation.getTreasuresRollFive2().get(roll));
										}

									}
									tsc.treasureLocation.counter8++;
								}
							}
						}
						if(monsterRoll == 6) {
							if((split[0] == "RUIN" && type == 'm') || (split[0] == "BONES" && type == 'm') || (split[0] == "PATTER" && type == 'm')) {
								if(tsc.lostCastle.counter6 < 3){
									for(int roll = 0; roll < tsc.lostCastle.getRollSix().get(tsc.lostCastle.counter6).size(); roll++) {
										board[locx][locy].getClearing(currClearing).addMonster((Monster) tsc.lostCastle.getRollSix().get(tsc.lostCastle.counter6).get(roll));

									}
									tsc.lostCastle.counter6++;
								}
							}
						}
					}
					String  mon= monsterRoll + "#" + currClearing + "#" + locx + "#" + locy + "#" + size + "#";
					for(int i = 0; i < size; i++) {
						mon += mons[i] +"#";
					}
					broadcast(MRMessage.NEW_MONSTER_OUT, mon);
					break;

				case MRMessage.CHANGE_TURN:
					ServerGUI.changePlayer = true;
					break;	

				case MRMessage.COMBAT:
					cInfo = message;
					ccis = cInfo.split("#");
					//player 1 character
					int player1 = getPlayerIndexFromCharacter(ccis[0].trim());
					System.out.println(ccis.length);
					//thrust
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[1].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setThrust(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[0] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getThrust();
							break;
						}
					}//swing
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[2].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setSwing(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[1] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getSwing();
							break;
						}
					}//ssmash
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[3].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setSmash(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[2] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getSmash();
							break;
						}
					}//charge
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[4].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setCharge(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[0] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getCharge();
							break;
						}
					}//dodge
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[5].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setDodge(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[1] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getDodge();
							break;
						}
					}//duck
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[6].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setDuck(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[2] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getDuck();
							break;
						}
					}//weaponloc
					playerList.get(player1).getPlayerCharacter().getCombatInfo().setWeapon(Integer.parseInt(ccis[7].trim()));
					//weaponofuse
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getweaponList().size(); i++) {
						if(ccis[8].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getweaponList().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setActiveWeapon(playerList.get(player1).getPlayerCharacter().getweaponList().get(i));
							break;
						}
					}//thrust shield
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[9].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setShieldThrust(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}//smash shield
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[10].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setShieldSwing(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[11].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setShieldSmash(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[12].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setHelmet(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[13].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setSuitOfArmor(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}///player2
					int player2 = Integer.parseInt(ccis[14].trim());
					combatDone++;
					if(combatDone == 2) {
						System.out.println("1: RESULT GOES HERE!");
						int[] combatResult = Combat.combat(playerList.get(player1).getPlayerCharacter(), playerList.get(player2).getPlayerCharacter());
						System.out.println("RESULT GOES HERE!");

						String send = player1 + "-"+combatResult[0] +"-" + combatResult[1] + "#" + player2 + "-" + combatResult[2] + "-" + combatResult[3]; 
						combatDone = 0;
						System.out.println(send);
						MRMessage cr = new MRMessage(MRMessage.COMBAT_RESULT,  send);
						broadcast(MRMessage.COMBAT_RESULT, cr);
					}
					//
					break;
				case MRMessage.COMBAT_MONSTER_RESULT:
					cInfo = message;
					ccis = cInfo.split("#");
					//player 1 character
					player1 = getPlayerIndexFromCharacter(ccis[0].trim());
					System.out.println(ccis.length);
					//thrust
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[1].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setThrust(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[0] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getThrust();
							break;
						}
					}//swing
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[2].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setSwing(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[1] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getSwing();
							break;
						}
					}//ssmash
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[3].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setSmash(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[2] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getSmash();
							break;
						}
					}//charge
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[4].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setCharge(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[0] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getCharge();
							break;
						}
					}//dodge
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[5].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setDodge(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[1] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getDodge();
							break;
						}
					}//duck
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getActionChits().size(); i++) {
						if(ccis[6].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getActionChits().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setDuck(playerList.get(player1).getPlayerCharacter().getActionChits().get(i));
							playerList.get(player1).getPlayerCharacter().getCombatInfo().getRoundChits()[2] = playerList.get(player1).getPlayerCharacter().getCombatInfo().getDuck();
							break;
						}
					}//weaponloc
					playerList.get(player1).getPlayerCharacter().getCombatInfo().setWeapon(Integer.parseInt(ccis[7].trim()));
					//weaponofuse
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getweaponList().size(); i++) {
						if(ccis[8].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getweaponList().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setActiveWeapon(playerList.get(player1).getPlayerCharacter().getweaponList().get(i));
							break;
						}
					}//thrust shield
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[9].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setShieldThrust(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}//smash shield
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[10].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setShieldSwing(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[11].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setShieldSmash(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[12].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setHelmet(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}
					for(int i = 0; i < playerList.get(player1).getPlayerCharacter().getArmor().size(); i++) {
						if(ccis[13].trim().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getArmor().get(i).getName())) {
							playerList.get(player1).getPlayerCharacter().getCombatInfo().setSuitOfArmor(playerList.get(player1).getPlayerCharacter().getArmor().get(i));
							break;
						}
					}///player2
					player2 = Integer.parseInt(ccis[14].trim());
						Random r = new Random();
						int comR = r.nextInt(2);
						int px = 0;
						int py = 0;
						int pc = 0;
						for(int i = 0; i < BSIZE; i++) {
							for(int j = 0; j < BSIZE; j++) {
								if(board[i][j].getName() != null) {
									if(board[i][j].getName().equalsIgnoreCase(playerList.get(player1).getPlayerCharacter().getTileLocation())) {
										px = i;
										py=j;
										for(int k = 0; k <board[i][j].getAllClearings().length; k++) {
											if(board[i][j].getClearing(k) != null) {
												if(board[i][j].getClearing(k).getClearingNum() == playerList.get(player1).getPlayerCharacter().getClearingLocation().getClearingNum()) {
													pc = k;
												}
											}
										}
									}
											
								}
							}
						}
						if(comR == 0) {
							String send = player1 + "#"+ px + "#"+ py + "#"+ pc + "#"+"WON";
							MRMessage cr = new MRMessage(MRMessage.COMBAT_MONSTER_PLAYER,  send);
							broadcast(MRMessage.COMBAT_MONSTER_PLAYER, cr);
						}
						else {
							String send = player1 + "#"+ px + "#"+ py + "#"+ pc + "#"+"LOST";
							MRMessage cr = new MRMessage(MRMessage.COMBAT_MONSTER_PLAYER,  send);
							broadcast(MRMessage.COMBAT_MONSTER_PLAYER, cr);
						}			//
					break;
				case MRMessage.DEATH:
					cInfo = message;
					//int player = Integer.parseInt(cInfo.trim());

					///CLIENT GETS REMOVED
					ServerGUI.combatDone = true;
					break;



				}

				// see if we have finished number of activities for the player
				if (ServerGUI.gameStarted && no_of_activities == 4) {
					// we have finished the turn of this player
					ServerGUI.monsterTime = true;
					no_of_activities = 0;

				}
			}

			// remove myself from the arrayList containing the list of the
			// connected Clients
			ClientInfo ci = getClientInfoById(clients, id);
			clients.remove(ci);
			System.out.println("Client left - " + ci.getClientName());
			// broadcast(message, withtime, messageType, exceptThisClient, obj);
			broadcast("LEAVE", false, MRMessage.CLIENT_PLAYER_PANEL_INFO, null,
					clients);
			remove(id);
			close();
		}

		private int getPlayerIndexFromNameAndCharacter(String name,
				String character) {
			// System.out.println("Searching for name=" + name + " & character="
			// + character);

			for (Player p : playerList) {
				// System.out.println("player name=" + p.getPlayerName()
				// + "charactername="
				// + p.getPlayerCharacter().getName().equals(character));
				if (p.getPlayerName().equals(name)
						&& p.getPlayerCharacter().getName().equals(character)) {
					return playerList.indexOf(p);
				}
			}
			return -1;
		}
		public int getPlayerIndexFromCharacter(String character) {
			// System.out.println("Searching for name=" + name + " & character="
			// + character);

			for (Player p : playerList) {
				// System.out.println("player name=" + p.getPlayerName()
				// + "charactername="
				// + p.getPlayerCharacter().getName().equals(character));
				if (p.getPlayerCharacter().getName().equals(character)) {
					return playerList.indexOf(p);
				}
			}
			return -1;
		}

		// try to close everything
		private void close() {
			// try to close the connection
			try {
				if (sOutput != null)
					sOutput.close();
			} catch (Exception e) {
			}
			try {
				if (sInput != null)
					sInput.close();
			} catch (Exception e) {
			}
			;
			try {
				if (socket != null)
					socket.close();
			} catch (Exception e) {
			}
		}

		/*
		 * Write a String to the Client output stream
		 */
		// private boolean writeMsg(String msg) {
		// // if Client is still connected send the message to it
		// if (!socket.isConnected()) {
		// close();
		// return false;
		// }
		// // write the message to the stream
		// try {
		// sOutput.writeObject(msg);
		// }
		// // if an error occurs, do not abort just inform the user
		// catch (IOException e) {
		// display("Error sending message to " + username);
		// display(e.toString());
		// }
		// return true;
		// }

		/*
		 * Write a MRMessage to the Client output stream
		 */
		private boolean writeMsg(MRMessage msgg) {
			// if Client is still connected send the message to it
			if (!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msgg);
				sOutput.flush();
			}
			// if an error occurs, do not abort just inform the user
			catch (IOException e) {
				display("Error sending message to " + username);
				display(e.toString());
			}
			return true;
		}
	} // ClientThread End

	public ClientInfo getClientInfoById(ArrayList<ClientInfo> al, int id) {
		for (ClientInfo ci : al) {
			if (ci.getThreadId() == id) {
				return ci;
			}
		}
		return null;
	}

}
