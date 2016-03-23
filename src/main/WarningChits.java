package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class WarningChits implements Chit, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1194531505497240486L;
	private String name;
	private Image[] img = new Image[2];
	private char warning;
	private char type;
	private Point location;
	private int hidden;
	private String imgPaths0, imgPaths1;

	public WarningChits(String name, String h, String u, char w) {
		this.name = name;
		imgPaths0 = u;
		imgPaths1 = h;
		img[0] = new ImageIcon(u).getImage();
		img[1] = new ImageIcon(h).getImage();
		this.warning = w;
		type = 'W';
		this.location = new Point();
		hide();

	}

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeObject(name);
		stream.writeObject(imgPaths0.toString());
		stream.writeObject(imgPaths1.toString());
		stream.writeChar(warning);
		stream.writeChar(type);
		stream.writeObject(location);
		stream.writeInt(hidden);

	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		name = (String) stream.readObject();
		imgPaths0 = new String((String) stream.readObject());
		imgPaths1 = new String((String) stream.readObject());
		warning = stream.readChar();
		type = stream.readChar();
		location = (Point) stream.readObject();
		hidden = stream.readInt();
		img = new Image[2];
		this.img[0] = new ImageIcon(imgPaths0).getImage();
		this.img[1] = new ImageIcon(imgPaths1).getImage();
	}


	@Override
	public char getWarningType() {
		return warning;
	}

	@Override
	public String getChitName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public Image getImage(int hidden) {
		// TODO Auto-generated method stub
		return img[hidden];
	}

	@Override
	public char getChitType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void setChitType(char t) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getChitNum() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public boolean isLost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Point getChitLocation() {
		// TODO Auto-generated method stub
		return location;
	}

	@Override
	public void setChitLocation(int x, int y) {
		// TODO Auto-generated method stub
		location.move(x, y);
	}

	public int isHidden() {
		return hidden;
	}

	public void unhide() {
		hidden = 0;
	}

	public void hide() {
		hidden = 1;
	}

	public String toString() {
		return "Warning Chit: \n Name: " + this.getChitName() + " "
				+ this.getWarningType();
	}

}
