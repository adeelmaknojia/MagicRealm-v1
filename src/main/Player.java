package main;

public class Player {
	
	private int idCounter;
	private int playerId;
	private String playerName;
	private Character playerCharacter;

	
	// CONSTRUCTOR //
	
	public Player(){
		this.playerId = 0;
		this.playerName = "";
		this.playerCharacter = null;	
	}
	
	public Player(int pID, String pName, Character pChar){
		this.playerId = pID;
		this.playerName = pName;
		this.playerCharacter = pChar;	
	}
	

	// GETTERS //
	public int getPlayerId() {return playerId;}
	public String getPlayerName() {return playerName;}
	public Character getPlayerCharacter() {return playerCharacter;}
	public Player getPlayer(){return this;}
	
	
	// SETTERS //
	public void setPlayerId(int playerId) {this.playerId = playerId;}
	public void setPlayerName(String playerName) {this.playerName = playerName;}
	public void setPlayerCharacter(Character playerCharacter) {this.playerCharacter = playerCharacter;}

		
}
