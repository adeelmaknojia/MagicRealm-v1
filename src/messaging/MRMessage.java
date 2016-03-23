package messaging;

import java.io.Serializable;

public class MRMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// The different types of message sent by the Client
	// CONNECT to rconnect to the server
	// MESSAGE an ordinary message
	public static final int CONNECT = 0;

	public static final int INFO = 1;

	public static final int DISCONNECT = 2;

	// message to get available characters
	public static final int GET_CHARACTERS = 3;

	// message client sends to server about the details about the Player
	// when he/she first joins
	public static final int PLAYER_DETAILS = 4;

	// message server sends to the clients about the details of the new clients
	// that joined the game
	// or clients that left the game

	public static final int CLIENT_PLAYER_PANEL_INFO = 5;

	// message from client to the server - telling the players victory
	// requirements

	public static final int PLAYER_VICTORY_REQUIREMENTS = 6;

	// message from server to client - telling we are not accepting players at
	// this point
	public static final int NOT_ACCEPTING_PLAYERS = 7;

	// HIDE action
	public static final int ACTION_HIDE_REQ = 8;

	public static final int ACTION_ALERT = 9;

	// Sent by the server to the client to update the different players, their
	// status and locations
	public static final int PLAYER_LIST = 10;

	// Sent by the server to the client to indicate that a particular player
	// needs to be hidden on the board
	public static final int HIDE_PLAYER = 11;

	public static final int PLAYER_POSITION_DETAILS = 12;

	public static final int GET_CHITS = 13;

	public static final int MOVE = 14;

	public static final int ACTIVATE = 15;

	public static final int ASK_GAME_MODE = 16;

	public static final int BOARD_NOT_INITIALIZED = 17;

	public static final int INITIALIZE_FIRST_PLAYER_RANDOM = 18;

	public static final int SET_GAME_MODE = 19;

	public static final int INITIALIZE_FIRST_PLAYER_CHEAT = 20;

	public static final int SET_CHITS = 21;

	public static final int BOARD_INITIALIZED = 22;

	public static final int GET_CHIT_NAMES = 23;

	public static final int SET_GAME_BOARD = 24;

	public static final int SET_MONSTER_ROLL = 25;

	public static final int RECEIVE_MONSTER_DICE = 26;

	public static final int PLAYER_TURN = 27;

	public static final int TODAYS_DICE = 28;

	public static final int FIND_CHARACTER_LOCATION = 29;
	public static final int REVEAL_CHIT = 30;
	public static final int PLAYER_POSITION = 31;
	public static final int CHIT_REVEALED = 32;
	public static final int MAKE_MONSTER_OUT = 33;
	public static final int MONSTER_OUT = 34;
	public static final int NEW_MONSTER = 35;
	public static final int NEW_MONSTER_OUT = 36;
	public static final int CHANGE_TURN = 37;
	public static final int COMBAT = 38;
	public static final int COMBAT_TIME = 39;
	public static final int COMBAT_RESULT = 40;	
	public static final int SEARCH = 41;
	
	public static final int START_COMBAT = 42;
	
	public static final int DEATH = 43;
	public static final int COMBAT_MONSTER = 44;
	public static final int EVERYONE_MOVE = 45;
	public static final int REST = 46;
	public static final int TRADE = 47;
	public static final int BLOCK = 48;
	public static final int WINNER = 49;
	public static final int COMBAT_MONSTER_RESULT = 49;
	public static final int COMBAT_MONSTER_PLAYER = 50;

	private int type;
	private String message;
	private Object obj;

	public MRMessage(int type, String message, Object obj) {
		this.type = type;
		this.message = message;
		this.obj = obj;
	}

	// constructor
	public MRMessage(int type, String message) {
		this.type = type;
		this.message = message;
	}

	// getters
	public int getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String msg) {
		message = msg;
	}

	public Object getObject() {
		return obj;
	}

	public void setObject(Object o) {
		this.obj = o;
	}

}
