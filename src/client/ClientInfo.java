package client;

import java.io.Serializable;

import server.MRServer;

public class ClientInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String clientName;
	String joinDateTime;
	String ipAddress;
	String characterName;
	String characterLocation;

	int threadId;

	public ClientInfo(String clientname, String joinDate, String ipAddress,
			int threadId) {
		this.clientName = clientname;
		this.joinDateTime = joinDate;
		this.ipAddress = ipAddress;
		this.threadId = threadId;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getJoinDateTime() {
		return joinDateTime;
	}

	public void setJoinDateTime(String joinDateTime) {
		this.joinDateTime = joinDateTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCharacterName() {
		return characterName;
	}

	public void setCharacterName(String characterName) {
		this.characterName = characterName;
	}

	public String getCharacterLocation() {
		return characterLocation;
	}

	public void setCharacterLocation(String characterLocation) {
		this.characterLocation = characterLocation;
	}

}
