package client;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import server.MRServer;
import Tiles.Tile;
import main.ActionChit;
import main.Activity;
import main.Armor;
import main.Character;
import main.CheatModeGUI;
import main.Chit;
import main.Chits;
import main.Combat;
import main.CombatGUI;
import main.GameGUI;
import main.Monster;
import main.NativeGroups;
import main.Player;
import main.PlayerController;
import main.PlayerGUI;
import main.RollDicePanel;
import main.TreasureCard;
import main.Weapons;
import messaging.MRMessage;

public class MRClient {
	Socket socket = null;
	ObjectOutputStream out = null;
	ObjectInputStream in = null;
	// ClientGUI tf = null;
	String server = "";
	int port = 0;
	ClientGUI cg = null;

	/**
	 * Stores available characters received from the server
	 */
	public static String[] availableChars;

	/**
	 * Stores whether the game has been started or not on client
	 */
	public static boolean gameStarted = false;

	/**
	 * Stores the current Player Id for the client
	 */

	public static int currentPlayer = 0; 

	public MRClient() {
		super();

	}

	public MRClient(String server, int port) {
		this.server = server;
		this.port = port;
	}

	public MRClient(String server, int port, ClientGUI cg) {
		this.server = server;
		this.port = port;
		this.cg = cg;
	}

	/*
	 * To start the dialog
	 */
	public boolean start() {
		// try to connect to the server
		try {
			socket = new Socket(server, port);
		}
		// if it failed not much I can so
		catch (Exception ec) {
			display("Error connectiong to server:" + ec);
			return false;
		}

		String msg = "Connection accepted " + socket.getInetAddress() + ":"
				+ socket.getPort();
		display(msg);

		/* Creating both Data Stream */
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException eIO) {
			display("Exception creating new Input/output Streams: " + eIO);
			return false;
		}

		// creates the Thread to listen from the server
		new ListenFromServer().start();
		// Send our username to the server this is the only message that we
		// will send as a String. All other messages will be MRMessage objects
		InetAddress IP = null;
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		MRMessage mr = new MRMessage(MRMessage.CONNECT, IP.getHostAddress());
		sendMessage(mr);

		// success we inform the caller that it worked
		return true;
	}

	/*
	 * To send a message to the server
	 */
	public void sendMessage(MRMessage msg) {
		try {
			out.writeObject(msg);
			out.flush();
			System.out.println("Sending MRMessage type=" + msg.getType()
					+ ", content=" + msg.getMessage());
		} catch (Exception e) {
			display("Exception writing to server: " + e);
		}
	}

	/*
	 * When something goes wrong Close the Input/Output streams and disconnect
	 * not much to do in the catch clause
	 */
	private void disconnect() {
		try {
			if (in != null)
				in.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (out != null)
				out.close();
		} catch (Exception e) {
		} // not much else I can do
		try {
			if (socket != null)
				socket.close();
		} catch (Exception e) {
		} // not much else I can do

		// inform the GUI
		if (cg != null)
			cg.connectionFailed();

	}

	/*
	 * To send a message to the console or the
	 * GUIbroadcast(MRMessage.PLAYER_LIST, msgg);
	 */
	private void display(String msg) {
		if (cg == null)
			System.out.println(msg); // println in console mode
		else
			cg.append(msg + "\n"); // append to the ClientGUI JTextArea (or
		// whatever)
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

	public static void main(String[] args) throws IOException {
		// default values
		int portNumber = 1500;
		String serverAddress = "localhost";

		// depending of the number of arguments provided we fall through
		switch (args.length) {
		// > javac MRClient portNumber serverAddr
		case 2:
			serverAddress = args[1];
			// > javac MRClient portNumber
		case 1:
			try {
				portNumber = Integer.parseInt(args[0]);
			} catch (Exception e) {
				System.out.println("Invalid port number.");
				System.out
				.println("Usage is: > java MRClient [portNumber] [serverAddress]");
				return;
			}
			// > javac MRClient
		case 0:
			break;
			// invalid number of arguments
		default:
			System.out
			.println("Usage is: > java MRClient [portNumber] [serverAddress]");
			return;
		}

		MRClient mr = new MRClient();
	}

	/*
	 * a class that waits for the message from the server and append them to the
	 * JTextArea if we have a GUI or simply System.out.println() it in console
	 * mode
	 */
	class ListenFromServer extends Thread {

		/**
		 * The type of message received from the server
		 */
		MRMessage msg;

		public void run() {
			while (true) {
				try {
					msg = (MRMessage) in.readObject();
					System.out.println("Received message from server - type="
							+ msg.getType() + " and content="
							+ msg.getMessage());
					// Switch on the type of message received
					switch (msg.getType()) {

					case MRMessage.ASK_GAME_MODE:
						String[] choices = { "Random Mode", "Cheat Mode" };
						int n = JOptionPane.showOptionDialog(null,
								"What mode do you want to play the game in?",
								"Game Mode", JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, choices,
								choices[0]);

						if (n == JOptionPane.YES_OPTION) {
							// Random Mode
							CheatModeGUI.CheatMode = false;
							//							MRMessage mr = new MRMessage(
							//									MRMessage.SET_GAME_MODE, "0");
							//							sendMessage(mr);
						} else if (n == JOptionPane.NO_OPTION) {
							// Cheat Mode
							CheatModeGUI.CheatMode = true;
							//							MRMessage mr = new MRMessage(
							//									MRMessage.SET_GAME_MODE, "1");
							//							sendMessage(mr);
						} else if (n == JOptionPane.CANCEL_OPTION) {
							// No mode selected

						}

						String[] board = { "Board 1", "Board 2" };
						int b = JOptionPane.showOptionDialog(null,
								"Select Board", "Board",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, board,
								board[0]);

						if (b == JOptionPane.YES_OPTION) {
							// Board1
							MRServer.board1 = true;
							GameGUI.board1 = true;
							//							MRMessage mr = new MRMessage(
							//									MRMessage.SET_GAME_BOARD, "0");
							//							sendMessage(mr);
						} else if (b == JOptionPane.NO_OPTION) {
							// Board2
							MRServer.board1 = false;
							GameGUI.board1 = false;
							//							MRMessage mr = new MRMessage(
							//									MRMessage.SET_GAME_BOARD, "1");
							//							sendMessage(mr);
						}

						MRMessage mr = new MRMessage(MRMessage.SET_GAME_MODE, n + "-" +b);
						sendMessage(mr);

						break;

					case MRMessage.INITIALIZE_FIRST_PLAYER_RANDOM:
						MRMessage getcharMessage = new MRMessage(
								MRMessage.GET_CHARACTERS, "");
						sendMessage(getcharMessage);
						break;

					case MRMessage.INITIALIZE_FIRST_PLAYER_CHEAT:
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									CheatModeGUI window = new CheatModeGUI();
									window.frmCheatMode.setVisible(true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
						// MRMessage getcharMessage = new MRMessage(
						// MRMessage.GET_CHARACTERS, "");
						// sendMessage(getcharMessage);
						break;
					case MRMessage.SET_GAME_BOARD:
						//						String[] board = { "Board 1", "Board 2" };
						//						int b = JOptionPane.showOptionDialog(null,
						//								"Select Board", "Board",
						//								JOptionPane.YES_NO_OPTION,
						//								JOptionPane.QUESTION_MESSAGE, null, board,
						//								board[0]);
						//
						//						if (b == JOptionPane.YES_OPTION) {
						//							// Board1
						//							MRServer.board1 = true;
						//							GameGUI.board1 = true;
						//							MRMessage mr = new MRMessage(
						//									MRMessage.SET_GAME_BOARD, "0");
						//							sendMessage(mr);
						//						} else if (b == JOptionPane.NO_OPTION) {
						//							// Board2
						//							MRServer.board1 = false;
						//							GameGUI.board1 = false;
						//							MRMessage mr = new MRMessage(
						//									MRMessage.SET_GAME_BOARD, "1");
						//							sendMessage(mr);
						//						}
						break;

					case MRMessage.BOARD_NOT_INITIALIZED:
						JOptionPane
						.showMessageDialog(null,
								"Board has not yet been initialized. Please retry in a bit!");
						System.out.println("Exitting. Board not initialized.");
						System.exit(2);
						break;

					case MRMessage.GET_CHARACTERS:
						availableChars = msg.getMessage().split(",");
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								Player model = new Player();
								PlayerGUI view = new PlayerGUI();
								cg.controller = new PlayerController(model,
										view);
								cg.controller.control();
							}
						});
						break;

					case MRMessage.PLAYER_DETAILS:

						String[] response = msg.getMessage().split("#");
						int playerId = Integer.parseInt(response[4]);
						CheatModeGUI.CheatMode = response[5].trim().equals("0") ? false
								: true;
						GameGUI.board1 = response[6].trim().equals("0")? true: false;
						// Updating Player model with the information

						if (response[0].toLowerCase().equals("success")) {
							cg.controller.model.setPlayerId(playerId);
							cg.controller.updateUI(response);
							// now we have the UI, ask for all connected clients

							InetAddress IP = null;
							try {
								IP = InetAddress.getLocalHost();
							} catch (UnknownHostException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							mr = new MRMessage(MRMessage.CONNECT,
									IP.getHostAddress());
							sendMessage(mr);

						}
						break;

					case MRMessage.CLIENT_PLAYER_PANEL_INFO:

						@SuppressWarnings("unchecked")
						String clientstr = msg.getMessage();
						String[] clients = clientstr.split("#");
						System.out.println("No of clients connected now = "
								+ clients.length);
						for (String c : clients) {
							System.out.println(c);
						}
						// cg.controller.updateClientPanel(clients);
						break;

					case MRMessage.NOT_ACCEPTING_PLAYERS:
						JOptionPane.showMessageDialog(null,
								"The server is not accepting new players now.");
						System.out.println("Not accepting players");
						break;

					case MRMessage.PLAYER_LIST:
						// update player list from the message obtained
						response = msg.getMessage().split("#");
						for (int i = 0; i < response.length; i++) {
							String vals[] = response[i].split("-");
							// 0 - PlayerName
							// 1 - CharacterName
							// 2 - playerId
							// 3 - Player Starting Location
							try {
								Player p = cg.controller.playerList.get(Integer
										.parseInt(vals[2]));
							} catch (IndexOutOfBoundsException iobe) {
								// Player does not exist create this player
								Player p = new Player(
										Integer.parseInt(vals[2]), vals[0],
										cg.controller.getCharObj(vals[1]));
								p.getPlayerCharacter().setStartingLocation(
										vals[3]);
								cg.controller.playerList.add(
										Integer.parseInt(vals[2]), p);
								cg.controller.updateOnRButton();
							}
						}

						String[] cl = new String[cg.controller.playerList
						                         .size()];
						System.out.println("No of clients connected now = "
								+ cg.controller.playerList.size());

						int cn = 0;
						for (Player pp : cg.controller.playerList) {
							cl[cn++] = pp.getPlayerName() + " - "
									+ pp.getPlayerCharacter().getName();
						}
						cg.controller.updateClientPanel(cl);

						if (!CheatModeGUI.CheatMode) {

							MRMessage mrr = new MRMessage(MRMessage.GET_CHITS,
									"app");
							sendMessage(mrr);
						} else {
							MRMessage mrr = new MRMessage(
									MRMessage.GET_CHIT_NAMES, "app");
							sendMessage(mrr);
						}

						break;
					case MRMessage.HIDE_PLAYER:
						response = msg.getMessage().split("-");
						int id = Integer.parseInt(response[0]);
						int hidden = Integer.parseInt(response[1]);
						Player p = cg.controller.playerList.get(id);
						if (hidden == 1) {
							p.getPlayerCharacter().hide();
							cg.controller.gameView.hexPanel.repaint();
							System.out.println("To Player: "
									+ p.getPlayerCharacter().getName()
									+ ": You are now Hidden!");
						} else {
							p.getPlayerCharacter().unhide();
							cg.controller.gameView.hexPanel.repaint();
							System.out.println("To Player: "
									+ p.getPlayerCharacter().getName()
									+ ": You are now Unhidden!");
						}

						break;
					case MRMessage.PLAYER_POSITION:
						response = msg.getMessage().split("-");
						int playerNumber = Integer.parseInt(response[0]);
						int ii = Integer.parseInt(response[1]);
						int jj = Integer.parseInt(response[2]);
						int xloc = Integer.parseInt(response[3]);
						int yloc = Integer.parseInt(response[4]);
						int cid = Integer.parseInt(response[5]);
						String boardName = response[6];
						int tempx = cg.controller.gameView.board[ii][jj].getClearing(5).getXLocation();
						int tempy = cg.controller.gameView.board[ii][jj].getClearing(5).getYLocation();

						cg.controller.getPCharacter().setPLocation(tempx, tempy);
						cg.controller.getPCharacter().setClearingLocation(cg.controller.gameView.board[ii][jj].getClearing(5));
						cg.controller.getPCharacter().setBoardLocation(cg.controller.gameView.board[ii][jj].getName());
						PlayerController.playerList.get(playerNumber).getPlayerCharacter().setPLocation(tempx,tempy);
						PlayerController.playerList.get(playerNumber).getPlayerCharacter().setClearingLocation(cg.controller.gameView.board[ii][jj].getClearing(5));
						PlayerController.playerList.get(playerNumber).getPlayerCharacter().setBoardLocation(cg.controller.gameView.board[ii][jj].getName());
						cg.controller.gameView.board[ii][jj].getClearing(5).placeCharacter(PlayerController.playerList.get(playerNumber).getPlayerCharacter());

						// update the interface
						cg.controller.gameView.hexPanel.repaint();
						break;

					case MRMessage.GET_CHITS:

						switch (msg.getMessage().toLowerCase()) {

						case "board":
							// GameGUI.board = (Tile[][]) msg.getObject();
							break;
						case "app":
							ArrayList<Chit> chits = ((ArrayList<Chit>) msg
									.getObject());
							GameGUI.appChart = chits;
							break;
						case "lostCastle":
							chits = ((ArrayList<Chit>) msg
									.getObject());
							GameGUI.lostCastle = chits;
							break;
						case "lostCity":
							chits = ((ArrayList<Chit>) msg
									.getObject());
							GameGUI.lostCity = chits;
							break;
						case "cave":
							chits = ((ArrayList<Chit>) msg.getObject());
							GameGUI.caveChit = chits;
							break;
						case "mountain":
							chits = ((ArrayList<Chit>) msg.getObject());
							GameGUI.mountainChit = chits;
							break;
						case "wood":
							chits = ((ArrayList<Chit>) msg.getObject());
							GameGUI.woodsChit = chits;
							break;
						case "valley":
							chits = ((ArrayList<Chit>) msg.getObject());
							GameGUI.valleyChit = chits;
							break;
						case "random":
							GameGUI.random = (ArrayList<Integer>) msg
							.getObject();

						}
						break;

					case MRMessage.GET_CHIT_NAMES:
						switch (msg.getMessage().toLowerCase()) {

						case "cave":
							ArrayList<String> chits = ((ArrayList<String>) msg
									.getObject());
							CheatModeGUI.cavesName = chits;
							break;
						case "mountain":
							chits = ((ArrayList<String>) msg.getObject());
							CheatModeGUI.mountainName = chits;
							break;
						case "wood":
							chits = ((ArrayList<String>) msg.getObject());
							CheatModeGUI.woodsName = chits;
							break;
						case "valley":
							chits = ((ArrayList<String>) msg.getObject());
							CheatModeGUI.valleyName = chits;
							break;
						case "sound":
							chits = ((ArrayList<String>) msg.getObject());
							CheatModeGUI.soundName = chits;
							break;
						case "site":
							chits = ((ArrayList<String>) msg.getObject());
							CheatModeGUI.siteName = chits;
							break;

						case "LostCity":
							chits = ((ArrayList<String>) msg.getObject());
							CheatModeGUI.FinaLCity = chits;
							break;

						case "LostCastle":
							chits = ((ArrayList<String>) msg.getObject());
							CheatModeGUI.FinaLCastle= chits;
							break;

						}
						break;
					case MRMessage.EVERYONE_MOVE:
						String msgg = msg.getMessage();
						String[] ccis = msgg.split("-");
						playerId = Integer.parseInt(ccis[0]);
						Activity.move(PlayerController.playerList.get(playerId).getPlayerCharacter(), ccis[4], cg.controller.gameView.board);
						cg.getPlayerController().gameView.setMessageText("");
						cg.getPlayerController().gameView.getActionField()
						.setText("");


						// m = m-1;
						cg.getPlayerController().gameView.hexPanel.repaint(); // updating
						// game
						// board

						break;

					case MRMessage.PLAYER_TURN:
						playerId = Integer.parseInt(msg.getMessage());
						if (PlayerController.model.getPlayerId() == playerId) {
							cg.getPlayerController().gameView.unfreeze();
							JOptionPane.showMessageDialog(cg
									.getPlayerController().gameView.getFrame(),
									PlayerController.model.getPlayerCharacter().getName() + " : Play Your Turn.");
						} else {
							cg.getPlayerController().gameView.freeze();
						}
						break;
					case MRMessage.SET_MONSTER_ROLL:
						playerId = Integer.parseInt(msg.getMessage());
						cg.getPlayerController().gameView.freeze();
						int diceroll = 0;
						if (PlayerController.model.getPlayerId() == playerId) {
							String[] diceNum = {"1", "2", "3", "4", "5", "6"};
							String dice = "";
							while (dice == "") {
								dice = (String) JOptionPane.showInputDialog(cg
										.getPlayerController().gameView.getFrame(),
										PlayerController.model.getPlayerCharacter().getName() + " : Roll The Monster Dice", "Monster Roll", JOptionPane.QUESTION_MESSAGE, null, diceNum, diceNum[0]);
								diceroll = Integer.parseInt(dice);
								MRMessage md = new MRMessage(MRMessage.RECEIVE_MONSTER_DICE, dice);
								sendMessage(md);
							}
						}
						break;
					case MRMessage.TODAYS_DICE:
						JOptionPane.showMessageDialog(cg
								.getPlayerController().gameView.getFrame(),
								msg.getMessage());
						break;
					case MRMessage.FIND_CHARACTER_LOCATION:
						playerId =Integer.parseInt(msg.getMessage());
						if(PlayerController.model.getPlayerId() == playerId) {
							String info = PlayerController.playerList.get(playerId).getPlayerCharacter().getTileLocation()
									+ "#"
									+ PlayerController.playerList.get(playerId).getPlayerCharacter().getClearingLocation().getClearingNum()
									+"";

							MRMessage mi = new MRMessage(MRMessage.MONSTER_OUT, info);
							sendMessage(mi);
						}
						break;
					case MRMessage.MAKE_MONSTER_OUT:
						response = msg.getMessage().split("#");
						String tile = response[0].trim();
						int clearing = Integer.parseInt(response[1]);

						for(int i = 0; i < cg.controller.gameView.BSIZE; i++) {
							for(int j = 0; j <cg.controller.gameView.BSIZE; j++) {
								if(cg.controller.gameView.board[i][j].getName() != null) {
									if(cg.controller.gameView.board[i][j].getName().equalsIgnoreCase(tile)) {
										for(int k = 0; k < cg.controller.gameView.board[i][j].getAllClearings().length; k++) {
											if(cg.controller.gameView.board[i][j].getClearing(k) != null) {
												for(int mon = 0; mon < cg.controller.gameView.board[i][j].getClearing(k).getAllMonsters().size(); mon++) {
													cg.controller.gameView.board[i][j].getClearing(clearing).addMonster(cg.controller.gameView.board[i][j].getClearing(k).removeMonster(mon));
												}
											}
										}
									}
								}
							}
						}
						cg.controller.gameView.hexPanel.repaint();
						String mess = tile + "#" + clearing + "";
						MRMessage mon = new MRMessage(MRMessage.REVEAL_CHIT, mess);
						sendMessage(mon);
						break;

					case MRMessage.CHIT_REVEALED:
						response = msg.getMessage().split("#");
						int locx = Integer.parseInt(response[0]);
						int locy = Integer.parseInt(response[1]);
						clearing = Integer.parseInt(response[2]);
						String c = "";

						for(int k = 0; k < cg.controller.gameView.board[locx][locy].getAllChits().size(); k++) {

							if(cg.controller.gameView.board[locx][locy].getChitAt(k).getChitName() == "LOST CITY") {
								//ACCESS APP CHART
								cg.controller.gameView.board[locx][locy].removeChit(k);
								for(int lc = 0; lc < cg.controller.gameView.tsc.lostCity.getChits().size(); lc++) {
									Chit temp = cg.controller.gameView.tsc.lostCity.getChitAt(lc);
									temp.unhide();
									cg.controller.gameView.board[locx][locy].addChit(temp);
								}
							}
							else if(cg.controller.gameView.board[locx][locy].getChitAt(k).getChitName() == "LOST CASTLE") {
								//ACCESS APP CHART
								cg.controller.gameView.board[locx][locy].removeChit(k);
								for(int lc = 0; lc < cg.controller.gameView.tsc.lostCastle.getChits().size(); lc++) {
									Chit temp = cg.controller.gameView.tsc.lostCastle.getChitAt(lc);
									temp.unhide();
									cg.controller.gameView.board[locx][locy].addChit(temp);
								}
							}
						}

						for(int k = 0; k <cg.controller.gameView.board[locx][locy].getAllChits().size(); k++) {
							cg.controller.gameView.board[locx][locy].getChitAt(k).unhide();
							if(cg.controller.gameView.board[locx][locy].getChitAt(k).getChitType() == 'W') {
								c += cg.controller.gameView.board[locx][locy].getChitAt(k).getChitName()
										+"-"
										+cg.controller.gameView.board[locx][locy].getChitAt(k).getWarningType()
										+"#";
							}
							else{
								c += cg.controller.gameView.board[locx][locy].getChitAt(k).getChitName()
										+"-"
										+cg.controller.gameView.board[locx][locy].getChitAt(k).getChitNum()
										+"#";
							}
						}
						for(int k = 0; k < cg.controller.gameView.board[locx][locy].getAllClearings().length; k++) {
							if(cg.controller.gameView.board[locx][locy].getClearing(k) != null) {
								if(cg.controller.gameView.board[locx][locy].getClearing(k).getDwelling() != null) {
									c+= cg.controller.gameView.board[locx][locy].getClearing(k).getDwelling().getItemType()
											+ "-"
											+cg.controller.gameView.board[locx][locy].getClearing(k).getDwelling().getName()
											+"-"
											+cg.controller.gameView.board[locx][locy].getClearing(k).getDwelling().getClearingOn().getClearingNum()
											+"#";
								}
							}
						}
						cg.controller.gameView.hexPanel.repaint();
						mess = clearing + "#" + locx + "#" + locy + "#" + cg.controller.gameView.board[locx][locy].getAllChits().size() + "#" + c;
						MRMessage monr = new MRMessage(MRMessage.NEW_MONSTER, mess);
						sendMessage(monr);
						break;

					case MRMessage.NEW_MONSTER_OUT:
						response = msg.getMessage().split("#");
						int monsterRoll = Integer.parseInt(response[0]);
						clearing = Integer.parseInt(response[1]);
						locx = Integer.parseInt(response[2]);
						locy = Integer.parseInt(response[3]);
						int size = Integer.parseInt(response[4]);
						String[] mons = new String[size];
						for(int i = 0; i < size; i++) {
							mons[i] = response[i+5];
						}


						for(int i = 0; i < size; i++) {
							String[] split = mons[i].split("-");
							char type = split[1].charAt(0);
							if(monsterRoll == 1) {
								if(split[0].trim().equalsIgnoreCase("FLUTTER")) {
									JOptionPane.showMessageDialog(null, "FLUTTER!");
									if(cg.controller.gameView.tsc.lostCastle.counter1 == 0) {
										int loc = Integer.parseInt(split[1]);
										cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollOne1());
										cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollOne2());
										cg.controller.gameView.tsc.lostCastle.counter1++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("SMOKE") && type == 'm') {
									JOptionPane.showMessageDialog(null, "SMOKE!");
									if(cg.controller.gameView.tsc.lostCastle.counter1 == 0) {
										cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollOne1());
										cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollOne2());
										cg.controller.gameView.tsc.lostCastle.counter1++;
									}
								}
								if((split[0].trim().equalsIgnoreCase("SMOKE") && type == 'c') || split[0].trim().equalsIgnoreCase("SLITHER") || (split[0].trim().equalsIgnoreCase("ROAR") && type =='c')) {
									if(cg.controller.gameView.tsc.lostCity.counter1 < 2) {
										if(split[0].trim().equalsIgnoreCase("SLITHER")) {
											int loc = Integer.parseInt(split[1]);
											for(int roll = 0; roll < cg.controller.gameView.tsc.lostCity.getRollOne().get(cg.controller.gameView.tsc.lostCity.counter1).size(); roll++) {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.lostCity.getRollOne().get(cg.controller.gameView.tsc.lostCity.counter1).get(roll));
											}
										}
										else {
											for(int roll = 0; roll < cg.controller.gameView.tsc.lostCity.getRollOne().get(cg.controller.gameView.tsc.lostCity.counter1).size(); roll++) {
												cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCity.getRollOne().get(cg.controller.gameView.tsc.lostCity.counter1).get(roll));
											}
										}
										cg.controller.gameView.tsc.lostCastle.counter1++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("HOARD")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter1 == 0){
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne1().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne1().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne1().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne1().get(roll));
											}

										}

										cg.controller.gameView.tsc.treasureLocation.counter1++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("LAIR")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter2 == 0) {
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne1().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne2().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne2().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollOne2().get(roll));
											}

										}

										cg.controller.gameView.tsc.treasureLocation.counter2++;
									}
								}
								if(split[0] == "D") {
									if(split[1] == "INN") {
										int loc = Integer.parseInt(split[2].trim());
										if(cg.controller.gameView.tsc.dwellings.counter1 == 0) {
											for(int dw = 0; dw <cg.controller.gameView.tsc.dwellings.getRollOne().size(); dw++) {
												if(cg.controller.gameView.tsc.dwellings.getRollOne().get(i).getItemType() == 'N') {
													NativeGroups temp = (NativeGroups) cg.controller.gameView.tsc.dwellings.getRollOne().get(dw);
													cg.controller.gameView.board[locx][locy].getClearing(loc).addNatives(temp);
												}else if(cg.controller.gameView.tsc.dwellings.getRollOne().get(i).getItemType() == 'N') {
													cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.dwellings.getRollOne().get(dw));
												}
											}
											cg.controller.gameView.tsc.dwellings.counter1++;
										}

									}

								}
							}
							else if(monsterRoll == 2) {
								if(split[0].trim().equalsIgnoreCase("DANK") && type == 'w') {
									if(cg.controller.gameView.tsc.lostCastle.counter2 == 0) {
										int loc = Integer.parseInt(split[1]);
										cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollTwo1());
										cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollTwo2());
										cg.controller.gameView.tsc.lostCastle.counter1++;
									}
								}
								if((split[0].equalsIgnoreCase("DANK") && type == 'c') || split[0].equalsIgnoreCase("SLITHER")) {
									if(cg.controller.gameView.tsc.lostCity.counter2 < 2) {
										if(split[0].equalsIgnoreCase("SLITHER")) {
											int loc = Integer.parseInt(split[1]);
											for(int roll = 0; roll < cg.controller.gameView.tsc.lostCity.getRollTwo().get(cg.controller.gameView.tsc.lostCity.counter2).size(); roll++) {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.lostCity.getRollTwo().get(cg.controller.gameView.tsc.lostCity.counter2).get(roll));
											}
										}
										else {
											for(int roll = 0; roll < cg.controller.gameView.tsc.lostCity.getRollTwo().get(cg.controller.gameView.tsc.lostCity.counter2).size(); roll++) {
												cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCity.getRollTwo().get(cg.controller.gameView.tsc.lostCity.counter2).get(roll));
											}
										}
										cg.controller.gameView.tsc.lostCastle.counter2++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("ALTER")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter3==0){
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo1().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo1().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo1().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo1().get(roll));
											}

										}

										cg.controller.gameView.tsc.treasureLocation.counter3++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("SHRINE")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter4==0) {
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo2().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo2().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo2().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollTwo2().get(roll));
											}

										}

										cg.controller.gameView.tsc.treasureLocation.counter4++;
									}
								}
								if(split[0] == "D") {
									if(split[1] == "HOUSE") {
										int loc = Integer.parseInt(split[2].trim());
										if(cg.controller.gameView.tsc.dwellings.counter2 == 0) {
											for(int dw = 0; dw <cg.controller.gameView.tsc.dwellings.getRollTwo().size(); dw++) {
												if(cg.controller.gameView.tsc.dwellings.getRollTwo().get(i).getItemType() == 'N') {
													NativeGroups temp = (NativeGroups) cg.controller.gameView.tsc.dwellings.getRollTwo().get(dw);
													cg.controller.gameView.board[locx][locy].getClearing(loc).addNatives(temp);
												}else if(cg.controller.gameView.tsc.dwellings.getRollTwo().get(i).getItemType() == 'N') {
													cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.dwellings.getRollTwo().get(dw));
												}
											}
											cg.controller.gameView.tsc.dwellings.counter2++;
										}
									}
								}
							}
							else if(monsterRoll == 3) {
								if(split[0].equalsIgnoreCase("RUINS") && type == 'w') {
									if(cg.controller.gameView.tsc.lostCastle.counter31==0) {
										for(int roll = 0; roll < cg.controller.gameView.tsc.lostCastle.getRollThree1().size(); roll++) {
											cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollThree1().get(roll));
										}
										cg.controller.gameView.tsc.lostCastle.counter31++;
									}
								}
								if(split[0].equalsIgnoreCase("BONES") && type == 'W') {
									if(cg.controller.gameView.tsc.lostCastle.counter32==0) {
										for(int roll = 0; roll < cg.controller.gameView.tsc.lostCastle.getRollThree2().size(); roll++) {
											cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollThree2().get(roll));
										}
										cg.controller.gameView.tsc.lostCastle.counter32++;
									}
								}
								if((split[0].equalsIgnoreCase("RUIN") && type == 'c') || (split[0].trim().equalsIgnoreCase("PATTER") && type == 'c') || (split[0].trim().equalsIgnoreCase("HOWL") && type == 'c')) {
									if(cg.controller.gameView.tsc.lostCity.counter3 < 2){
										for(int roll = 0; roll < cg.controller.gameView.tsc.lostCity.getRollThree().get(cg.controller.gameView.tsc.lostCity.counter3).size(); roll++) {
											cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCity.getRollThree().get(cg.controller.gameView.tsc.lostCity.counter3).get(roll));

										}
										cg.controller.gameView.tsc.lostCity.counter3++;
									}
								}
								if(split[0].equalsIgnoreCase("POOL")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter5 == 0) {
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollThree().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollThree().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollThree().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollThree().get(roll));
											}

										}
										cg.controller.gameView.tsc.treasureLocation.counter5++;
									}
								}
								if(split[0] == "D") {
									if(split[1] == "INN" || split[1] == "CHAPEL" || split[1] == "HOUSE" || split[1] == "GUARD") {
										int loc = Integer.parseInt(split[2].trim());
										if(cg.controller.gameView.tsc.dwellings.counter3 == 0) {
											for(int dw = 0; dw <cg.controller.gameView.tsc.dwellings.getRollThree().size(); dw++) {
												if(cg.controller.gameView.tsc.dwellings.getRollThree().get(i).getItemType() == 'N') {
													NativeGroups temp = (NativeGroups) cg.controller.gameView.tsc.dwellings.getRollThree().get(dw);
													cg.controller.gameView.board[locx][locy].getClearing(loc).addNatives(temp);
												}else if(cg.controller.gameView.tsc.dwellings.getRollThree().get(i).getItemType() == 'N') {
													cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.dwellings.getRollThree().get(dw));
												}
											}
											cg.controller.gameView.tsc.dwellings.counter3++;

										}

									}

								}
							}
							else if(monsterRoll == 4) {
								if((split[0].equalsIgnoreCase("BONES") && type == 'm') || (split[0].trim().equalsIgnoreCase("ROAR") && type == 'm') || (split[0].trim().equalsIgnoreCase("STINK") && type == 'm')) {
									if(cg.controller.gameView.tsc.lostCastle.counter4 < 2){
										for(int roll = 0; roll < cg.controller.gameView.tsc.lostCastle.getRollFour().get(cg.controller.gameView.tsc.lostCastle.counter4).size(); roll++) {
											cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollFour().get(cg.controller.gameView.tsc.lostCastle.counter4).get(roll));

										}
										cg.controller.gameView.tsc.lostCastle.counter4++;
									}
								}
								if((split[0].trim().equalsIgnoreCase("STINK") && type == 'c') || (split[0].trim().equalsIgnoreCase("BONES") && type == 'c') || (split[0].equalsIgnoreCase("ROAR") && type == 'c')) {
									if(cg.controller.gameView.tsc.lostCity.counter4 < 2){
										for(int roll = 0; roll < cg.controller.gameView.tsc.lostCity.getRollFour().get(cg.controller.gameView.tsc.lostCity.counter4).size(); roll++) {
											cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCity.getRollFour().get(cg.controller.gameView.tsc.lostCity.counter4).get(roll));
										}
										cg.controller.gameView.tsc.lostCity.counter4++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("VAULT")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter6 == 0){
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFour().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFour().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFour().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFour().get(roll));
											}

										}
										cg.controller.gameView.tsc.treasureLocation.counter6++;
									}
								}
								if(split[0] == "D") {
									if(split[1] == "CHAPEL") {
										int loc = Integer.parseInt(split[2].trim());
										if(cg.controller.gameView.tsc.dwellings.counter4 == 0) {
											for(int dw = 0; dw <cg.controller.gameView.tsc.dwellings.getRollFour().size(); dw++) {
												if(cg.controller.gameView.tsc.dwellings.getRollFour().get(i).getItemType() == 'N') {
													NativeGroups temp = (NativeGroups) cg.controller.gameView.tsc.dwellings.getRollFour().get(dw);
													cg.controller.gameView.board[locx][locy].getClearing(loc).addNatives(temp);
												}else if(cg.controller.gameView.tsc.dwellings.getRollFour().get(i).getItemType() == 'N') {
													cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.dwellings.getRollFour().get(dw));
												}
											}
										}
									}
								}
							}
							else if(monsterRoll == 5) {
								if((split[0].trim().equalsIgnoreCase("STINK") && type == 'm') || (split[0].trim().equalsIgnoreCase("DANK") && type == 'm') || (split[0].trim().equalsIgnoreCase("PATTER") && type == 'm')) {
									if(cg.controller.gameView.tsc.lostCastle.counter5 < 2){
										for(int roll = 0; roll < cg.controller.gameView.tsc.lostCastle.getRollFive().get(cg.controller.gameView.tsc.lostCastle.counter5).size(); roll++) {
											cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollFive().get(cg.controller.gameView.tsc.lostCastle.counter5).get(roll));

										}
										cg.controller.gameView.tsc.lostCastle.counter5++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("CAIRNS")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter7 == 0){
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFive1().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFive1().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFive1().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFive1().get(roll));
											}

										}
										cg.controller.gameView.tsc.treasureLocation.counter7++;
									}
								}
								if(split[0].trim().equalsIgnoreCase("STATUE")) {
									int loc = Integer.parseInt(split[1]);
									if(cg.controller.gameView.tsc.treasureLocation.counter8 == 0){
										for(int roll = 0; roll < cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFour().size(); roll++) {
											if(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFive2().get(roll).getItemType() == 'M') {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addMonster((Monster) cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFive2().get(roll));
											}
											else {
												cg.controller.gameView.board[locx][locy].getClearing(loc).addSiteTreasures(cg.controller.gameView.tsc.treasureLocation.getTreasuresRollFive2().get(roll));
											}

										}
										cg.controller.gameView.tsc.treasureLocation.counter8++;
									}
								}
							}
							if(monsterRoll == 6) {
								if((split[0] == "RUIN" && type == 'm') || (split[0] == "BONES" && type == 'm') || (split[0] == "PATTER" && type == 'm')) {
									if(cg.controller.gameView.tsc.lostCastle.counter6 < 3){
										for(int roll = 0; roll < cg.controller.gameView.tsc.lostCastle.getRollSix().get(cg.controller.gameView.tsc.lostCastle.counter6).size(); roll++) {
											cg.controller.gameView.board[locx][locy].getClearing(clearing).addMonster((Monster) cg.controller.gameView.tsc.lostCastle.getRollSix().get(cg.controller.gameView.tsc.lostCastle.counter6).get(roll));

										}
										cg.controller.gameView.tsc.lostCastle.counter6++;
									}
								}
							}
						}

						cg.controller.gameView.hexPanel.repaint();
						MRMessage done = new MRMessage(MRMessage.CHANGE_TURN, "1");
						sendMessage(done);
						break;
					case MRMessage.START_COMBAT:
						response = msg.getMessage().split("-");
						int player1 = Integer.parseInt(response[0]);
						int player2 = Integer.parseInt(response[1]);
						if(cg.controller.model.getPlayerId() == player1) {
							CombatGUI combat = new CombatGUI(cg.controller.model.getPlayerCharacter(), 1, player2);
						}
						if(cg.controller.model.getPlayerId() == player2) {
							CombatGUI combat = new CombatGUI(cg.controller.model.getPlayerCharacter(), 1, player1);
						}

						break;


					case MRMessage.COMBAT_RESULT:
						response = msg.getMessage().split("#");
						String[] p1 = response[0].split("-");
						String[] p2 = response[1].split("-");
						player1 = Integer.parseInt(p1[0]);
						int p1Death = Integer.parseInt(p1[1]);
						int p1Wound = Integer.parseInt(p1[2]);
						player2 = Integer.parseInt(p2[0]);
						int p2Death = Integer.parseInt(p2[1]);
						int p2Wounds = Integer.parseInt(p2[2]);
						String death = "";
						if(cg.controller.model.getPlayerId() == player1) {
							if(p1Death >0 && p2Death == 0){
								JOptionPane.showMessageDialog(null, "Player 1 Died! Player 2 Won! ");
								death += player1;
							}
							else if(p2Death > 0 && p1Death == 0) {
								JOptionPane.showMessageDialog(null, "Player 2 Died! Player 1 Won! ");
								Combat.fatigue(cg.controller.model.getPlayerCharacter());
								Combat.wounds(cg.controller.model.getPlayerCharacter(), p1Wound);
							}
							else if(p1Death > 0 && p2Death > 0) {
								JOptionPane.showMessageDialog(null, "Both Players died! ");
								death+= player1;
							}
							else if(p1Death == 0 && p2Death == 0) {
								Combat.fatigue(cg.controller.model.getPlayerCharacter());
								Combat.wounds(cg.controller.model.getPlayerCharacter(), p1Wound);
							}
						}
						else if (cg.controller.model.getPlayerId() == player2) {
							if(p1Death >0 && p2Death == 0){
								JOptionPane.showMessageDialog(null, "Player 1 Died! Player 2 Won! ");
								Combat.fatigue(cg.controller.model.getPlayerCharacter());
								Combat.wounds(cg.controller.model.getPlayerCharacter(), p2Wounds);
							}
							else if(p2Death > 0 && p1Death == 0) {
								JOptionPane.showMessageDialog(null, "Player 2 Died! Player 1 Won! ");
								death += player2;

							}
							else if(p1Death > 0 && p2Death > 0) {
								JOptionPane.showMessageDialog(null, "Both Players died! ");
								death += player2;
							}
							else if(p1Death == 0 && p2Death == 0) {
								Combat.fatigue(cg.controller.model.getPlayerCharacter());
								Combat.wounds(cg.controller.model.getPlayerCharacter(), p2Wounds);
							}
						}
						MRMessage dead = new MRMessage(MRMessage.DEATH, death);
						sendMessage(dead);
						break;
					case MRMessage.COMBAT_MONSTER_PLAYER:
						response = msg.getMessage().split("#");
						player1 = Integer.parseInt(response[0].trim());
						locx = Integer.parseInt(response[1].trim());
						locy = Integer.parseInt(response[2].trim());
						clearing = Integer.parseInt(response[3].trim());
						if(cg.controller.model.getPlayerId() == player1) {
							int result = 0;
							if(response[2].trim().equalsIgnoreCase("WON")) {
								JOptionPane.showMessageDialog(null, "YOU WON! MONSTER DIED");
								cg.controller.gameView.board[locx][locy].getClearing(clearing).removeMonster(0);
								result = 1;
							}
							else {
								JOptionPane.showMessageDialog(null, "YOU LOST! MONSTER KILLED YOU");
								result = 1;
							}
							String send = player1 + "#" + result;
							MRMessage mcom = new MRMessage(MRMessage.DEATH, send);
							sendMessage(mcom);
						}
						break;
					case MRMessage.COMBAT_MONSTER:
						response = msg.getMessage().split("-");
						player1 = Integer.parseInt(response[0].trim());
						//int monster = Integer.parseInt(response[1].trim());
						locx = Integer.parseInt(response[2].trim());
						locy = Integer.parseInt(response[2].trim());
						clearing = Integer.parseInt(response[3].trim());
						if(cg.controller.model.getPlayerId() == player1) {
							Monster m = cg.controller.gameView.board[locx][locy].getClearing(clearing).getMonster(0);

							ActionChit monster = new ActionChit(m.getName(), "FIGHT", m.getHarmLetterNormal(), 
									m.getAttackSpeedNormal(), m.getSharpnessNormal(), null, null);
							monster.setAImage(m.getImage());
							monster.setIImage(m.getImage());
							Random r = new Random();
							int loc = r.nextInt(3);
							if(loc == 0) {
								cg.controller.model.getPlayerCharacter().getCombatInfo().seteChargeThrust(monster);
							} else if(loc == 1){
								cg.controller.model.getPlayerCharacter().getCombatInfo().seteDodgeSwing(monster);
							} else if(loc == 2) {
								cg.controller.model.getPlayerCharacter().getCombatInfo().seteDuckSmash(monster);
							}
							CombatGUI combat = new CombatGUI(cg.controller.model.getPlayerCharacter(), 1, -1);
							break;
						}
						break;
					case MRMessage.WINNER:
						playerId = Integer.parseInt(msg.getMessage());
						cg.getPlayerController().gameView.freeze();
						if (PlayerController.model.getPlayerId() == playerId) {
							JOptionPane.showMessageDialog(null, "CONGRATULATIONS! YOU WON THE GAME!!");
						}
						else {
							JOptionPane.showMessageDialog(null, PlayerController.playerList.get(playerId).getPlayerName() + "  WON THE GAME!!");
						}
						break;
					}

				} catch (IOException e) {
					display("Server has close the connection: " + e);
					e.printStackTrace();
					if (cg != null)
						cg.connectionFailed();
					break;
				}
				// can't happen with a String object but need the catch anyhow
				catch (ClassNotFoundException e2) {
				}
			}
		}
	}

}
