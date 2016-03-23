package client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import main.Player;
import main.PlayerController;
import main.PlayerGUI;
import messaging.MRMessage;

public class ClientGUI {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String defaultHost;
	int defaultPort;

	public static MRClient mr;

	/**
	 * Variable that stores whether the client is currently connected to the
	 * server or not
	 */
	boolean connected;

	PlayerController controller;


	public ClientGUI(MRClient mr) {
	}

	public PlayerController getPlayerController() {
		return this.controller;
	}

	// Constructor connection receiving a socket number
	ClientGUI(String host, int port) {
		defaultPort = port;
		defaultHost = host;

		String choice;
		choice = new ConnectionDetailsUI().createAndDisplayGUI();
		if (choice.trim().length() > 8) {
			connectToServer(choice, String.valueOf(defaultPort));
			if (connected) {
				// TODO 1. obtain the details about player characters and
				// character locations from the server
				MRMessage getcharMessage = new MRMessage(
						MRMessage.GET_CHARACTERS, "");
				mr.sendMessage(getcharMessage);
				//

			} else
				JOptionPane.showMessageDialog(null, "Could not connect to "
						+ host + " at this point in time.");

		}
		// if (choice == JOptionPane.NO_OPTION) { //
		// // Exit the application
		// System.out.println("Exitting the application");
		// System.exit(0);
		// }
		// if (choice == JOptionPane.CLOSED_OPTION) {
		// // Do nothing
		// }

	}

	public void append(String msg) {
		// textArea.append("\n" + msg);
		System.out.println(msg);
	}

	/**
	 * Connect to server Called when the application starts
	 */

	public void connectToServer(String server, String portNumber) {

		InetAddress IP = null;
		try {
			IP = InetAddress.getLocalHost();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if (server.length() == 0)
			return;

		if (portNumber.length() == 0)
			return;

		int port = 0;
		try {
			port = Integer.parseInt(portNumber);
		} catch (Exception en) {
			return; // nothing I can do if port number is not valid
		}

		// try creating a new Client with GUI
		mr = new MRClient(server, port, this);

		// test if we can start the Client
		if (!mr.start())
			return;

		connected = true;

	}

	// called by the GUI is the connection failed
	// we reset our buttons, label, textfield
	public void connectionFailed() {
		connected = false;
	}


	// to start the whole thing the server
	public static void main(String[] args) {
		new ClientGUI("localhost", 1500);
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