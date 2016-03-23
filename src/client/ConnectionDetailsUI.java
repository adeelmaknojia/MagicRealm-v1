package client;

import java.awt.Color;

import javax.swing.*;

public class ConnectionDetailsUI {
	public String createAndDisplayGUI() {

		// int n = JOptionPane.showConfirmDialog(null, getOptionPanel(),
		// "Connect :: Magic Realm ", JOptionPane.OK_CANCEL_OPTION);
		JFrame frame = new JFrame("IP Address");

		// prompt the user to enter their name
		String name = JOptionPane.showInputDialog(frame, "Server's IP Address", "127.0.0.1");

		return name;
	}

	private JPanel getOptionPanel() {
		JPanel panel = new JPanel();
		panel.setOpaque(true);
		panel.setBackground(Color.LIGHT_GRAY);
		try {
			JTextField txtIp = new JTextField("127.0.0.1");
			JLabel label = new JLabel("Server IP Address: ", JLabel.LEFT);
			panel.add(label);
			panel.add(txtIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return panel;
	}
}