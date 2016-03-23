package server;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.Player;
import messaging.MRMessage;

public class ServerGUI extends JFrame implements ActionListener, WindowListener

{
	private static final long serialVersionUID = 1L;
	// the stop and start buttons
	private JButton stopStart;

	private JButton startAcceptingClients;

	private JButton startGame;
	// JTextArea for the data and the events
	private JTextArea data, event;
	// The port number
	private JTextField tPortNumber;
	// my server
	private MRServer server;
	JTextAreaWithScroll textArea;
	public static boolean changePlayer;
	public static boolean monsterTime;
	public static boolean gameStarted = false;
	public static boolean combatDone;
	
	public GameLogicThread glt;

	public ServerGUI() {
		super("Test Frame");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 300);

		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		textArea = new JTextAreaWithScroll(
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		add(textArea.getScrollPane());
		// add(new JButton("Test"));
		setVisible(true);
	}

	// server constructor that receive the port to listen to for connection as
	// parameter
	ServerGUI(int port) {
		super("MRServer");
		server = null;
		// in the NorthPanel the PortNumber the Start and Stop buttons
		JPanel north = new JPanel();
		north.add(new JLabel("Port number: "));
		tPortNumber = new JTextField("  " + port);
		north.add(tPortNumber);
		// to stop or start the server, we start with "Start"
		stopStart = new JButton("Start");
		startAcceptingClients = new JButton("Accept Players");
		startGame = new JButton("Start Game");
		stopStart.addActionListener(this);
		startAcceptingClients.addActionListener(this);
		startGame.addActionListener(this);
		startAcceptingClients.setEnabled(false);
		north.add(stopStart);
		north.add(startAcceptingClients);
		north.add(startGame);
		add(north, BorderLayout.NORTH);

		// the event and data panels
		JPanel center = new JPanel(new GridLayout(2, 1));
		data = new JTextArea(80, 80);
		data.setEditable(false);
		appendData("Data received:.\n");
		center.add(new JScrollPane(data));
		event = new JTextArea(80, 80);
		event.setEditable(false);
		appendEvent("Events log.\n");
		center.add(new JScrollPane(event));
		add(center);

		// need to be informed when the user click the close button on the frame
		addWindowListener(this);
		setSize(600, 600);
		setVisible(true);
	}

	public void addMessage(String msg) {
		textArea.append("\r\n" + msg);
	}

	// append message to the two JTextArea
	// position at the end
	public void appendData(String str) {
		data.append(str);
		data.setCaretPosition(data.getText().length() - 1);
	}

	public void appendEvent(String str) {
		event.append(str);
		// if (data.getText().length() > 0)
		// event.setCaretPosition(data.getText().length() - 1);

	}

	// start or stop where clicked
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(stopStart)) {
			// if running we have to stop
			if (server != null) {
				// server.stop();
				server.interrupt();
				server.keepGoing = false;
				tPortNumber.setEditable(true);
				stopStart.setText("Start");
				return;
			}
			// OK start the server
			int port;
			try {
				port = Integer.parseInt(tPortNumber.getText().trim());
			} catch (Exception er) {
				appendEvent("Invalid port number");
				return;
			}
			// create a new Server
			server = new MRServer(port, this);
			// and start it as a thread
			new ServerRunning().start();
			stopStart.setText("Stop");
			startAcceptingClients.setEnabled(true);
			stopStart.setEnabled(false);
			tPortNumber.setEditable(false);
		} else if (e.getSource().equals(startAcceptingClients)) {
			// toggle between accept/not accept players
			if (startAcceptingClients.getText().startsWith("Don")) {
				startAcceptingClients.setText("Accept Players");
				server.acceptClients = false;
			} else {
				startAcceptingClients.setText("Dont Accept Players");
				server.acceptClients = true;
			}
			
			
		} 
		
		 else if (e.getSource().equals(startGame)) {
				startGame.setEnabled(false);
				if (glt == null) {
					glt = new GameLogicThread();
				}
				glt.start();
			}
	}

	public void combat(){
		for(int i = 0; i < MRServer.BSIZE; i++) {
			for(int j = 0; j < MRServer.BSIZE; j++) {
				if(server.board[i][j].getName() != null) {
					for(int k = 0; k < server.board[i][j].getAllClearings().length; k++) {
						if(server.board[i][j].getClearing(k) != null) {
							if(server.board[i][j].getClearing(k).getAllCharactersOnClearing().size() > 1) {

								//TIME TO COMBAT!!!!!
								main.Character playerNumber1 = server.board[i][j].getClearing(k).getCharacterOnClearing(0);
								main.Character playerNumber2 = server.board[i][j].getClearing(k).getCharacterOnClearing(1);
								if(!playerNumber1.getName().equals(playerNumber2)){
									if(playerNumber1.isHidden() == 0 && playerNumber1.isblocked() == false && playerNumber2.isHidden() == 0 && playerNumber2.isblocked() == false) {
										System.out.println("TIME TO COMBAT!!");
										System.out.println("We are in " + server.board[i][j].getClearing(k).getTileName() + " - " + server.board[i][j].getClearing(k).getClearingNum());
										System.out.println("Combat between " + playerNumber1 + " & " + playerNumber2);
										//send a start combat message
										int pl = getPlayerIdFromName(playerNumber1.getName());
										int pl2 = getPlayerIdFromName(playerNumber2.getName());
										String mess = pl + "-" + pl2;
										MRMessage mr = new MRMessage(MRMessage.START_COMBAT, mess);
										server.broadcast(MRMessage.START_COMBAT, mr);
										combatDone = false;
										while(!combatDone) {
											try {
												Thread.sleep(50);
											} catch (InterruptedException e1) {
												// TODO Auto-generated catch block
												e1.printStackTrace();
											}
										}
									}

								}
							}
							if(server.board[i][j].getClearing(k).getAllCharactersOnClearing().size() > 0 && server.board[i][j].getClearing(k).getAllMonsters().size()>0) {
								main.Character playerNumber1 = server.board[i][j].getClearing(k).getCharacterOnClearing(0);
								if(playerNumber1.isHidden() == 0 || playerNumber1.isblocked()){
									int pl = getPlayerIdFromName(playerNumber1.getName());
									String monster = server.board[i][j].getClearing(k).getMonster(0).getName();
									String mess = pl + "-" + monster + "-" + i + "-" + j +"-" + k;
									MRMessage mr = new MRMessage(MRMessage.COMBAT_MONSTER, mess);
									server.broadcast(MRMessage.COMBAT_MONSTER, mr);
									while(!combatDone) {
										try {
											Thread.sleep(50);
										} catch (InterruptedException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									}
									///combat with monster
									
								}
							}
						}
					}
				}
			}
		}
		//		String result = playerNumber1 + "#" + playerNumber2;
		//		MRMessage combat = new MRMessage(MRMessage.COMBAT_RESULT, result);
		//		sendMessage(combat);
	}

	public int getPlayerIdFromName(String name){
		for(Player p : server.playerList){
			if(p.getPlayerCharacter().getName().equals(name))
				return server.playerList.indexOf(p);
		}
		return -1;
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * If the user click the X button to close the application I need to close
	 * the connection with the server to free the port
	 */
	public void windowClosing(WindowEvent e) {
		// if my Server exist
		if (server != null) {
			try {
				server.stop(); // ask the server to close the conection
			} catch (Exception eClose) {
			}
			server = null;
		}
		// dispose the frame
		dispose();
		System.exit(0);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * A thread to run the Server
	 */
	class ServerRunning extends Thread {
		public void run() {
			server.start(); // should execute until if fails
			// the server failed
			stopStart.setText("Start");
			tPortNumber.setEditable(true);
			appendEvent("Server crashed\n");
			server = null;
		}
	}

	// Game logic 
	class GameLogicThread extends Thread {
		public void run() {
			// Attention areej
			// Game logic comes here
			gameStarted = true;
			int gameDay = 0;
			while (gameDay != 5) {
				// ASK PLAYER ONE TO ROLL DICE
				MRMessage mr = new MRMessage(MRMessage.SET_MONSTER_ROLL, "" + 0);
				server.broadcast(MRMessage.SET_MONSTER_ROLL, mr);

				// LOOP THROUGH ALL PLAYERS
				for (int turn = 0; turn < server.playerList.size(); turn++) {

					// CURRENT PLAYER TIME ALL REST DISABLED
					MRMessage ms = new MRMessage(MRMessage.PLAYER_TURN, ""
							+ turn);
					server.broadcast(MRMessage.PLAYER_TURN, ms);

					// CONTINUE TILL REACH LIMIT
					monsterTime = false;
					while (!monsterTime) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					// ONCE DONE TURN FIND CURR PLAYER LOCATION
					MRMessage mi = new MRMessage(
							MRMessage.FIND_CHARACTER_LOCATION, "" + turn);
					server.broadcast(MRMessage.FIND_CHARACTER_LOCATION, mi);

					System.out.println("Roll: " + server.monsterRoll);
					System.out.println(turn + ": " + server.tileName);
					System.out.println(turn + ": " + server.currClearing);

					changePlayer = false;
					while (!changePlayer) {
						try {
							Thread.sleep(50);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
				combat();
				gameDay++;
			}

			// Game logic ends
			
			//FIND WHO WON
			int[] score = new int[server.playerList.size()];
			for (int turn = 0; turn < server.playerList.size(); turn++) {
				int fame = server.playerList.get(turn).getPlayerCharacter().getFamePoint() - server.playerList.get(turn).getPlayerCharacter().getFameRecorded();
				int notoriety = server.playerList.get(turn).getPlayerCharacter().getNeedNotority() - server.playerList.get(turn).getPlayerCharacter().getNotorityRecordedt();
				int gold = server.playerList.get(turn).getPlayerCharacter().getGoldRecorded() - server.playerList.get(turn).getPlayerCharacter().getGoldPoint();
				score[turn] = fame + notoriety + gold;
			}
			int winner = 0;
			for(int turn = 0; turn < server.playerList.size(); turn++) {
				
				if(score[winner] > score[turn]) {
					winner = turn;
				}
			}
			//WINNER WON
			MRMessage mr = new MRMessage(MRMessage.WINNER, "" + winner);
			server.broadcast(MRMessage.WINNER, mr);
		}
	}
	// entry point to start the Server
	public static void main(String[] arg) {
		// start server default port 1500
		new ServerGUI(1500);
	}

}

class JTextAreaWithScroll extends JTextArea {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JScrollPane scrollPane;

	public JTextAreaWithScroll(int vsbPolicy, int hsbPolicy) {
		scrollPane = new JScrollPane(this, vsbPolicy, hsbPolicy);
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}
}