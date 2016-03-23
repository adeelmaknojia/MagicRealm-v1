package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tiles.Clearing;

public class Dwelling implements Item, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 179109091571961450L;
	private Point dwellingLoc;
	private Image img;
	private String name;
	private Clearing clearingLocated;
	private String tileOn;
	private String imgPath;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeObject(dwellingLoc);
		stream.writeObject(name);
		stream.writeObject(clearingLocated);
		stream.writeObject(tileOn);
		stream.writeObject(imgPath);
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		dwellingLoc = (Point) stream.readObject();
		name = (String) stream.readObject();
		clearingLocated = (Clearing) stream.readObject();
		tileOn = (String) stream.readObject();
		imgPath = (String) stream.readObject();
		img = new ImageIcon(imgPath).getImage();
	}

	public Dwelling(String n, String i) {
		name = n;
		dwellingLoc = new Point();
		img = new ImageIcon(i).getImage();
		imgPath = i;
		clearingLocated = null;
		tileOn = null;
	}

	public Point getLocation() {
		return dwellingLoc;
	}

	public void setLocation(Point loc) {
		dwellingLoc = loc;
	}

	public Clearing getClearingOn() {
		return clearingLocated;
	}

	public void setClearingOn(Clearing loc) {
		clearingLocated = loc;
	}

	public Image getImage() {
		return img;
	}

	public String getName() {
		return name;
	}

	public void setTileOn(String n) {
		tileOn = n;
	}

	public String getTileON(String n) {
		return tileOn;
	}

	public char getItemType() {
		return 'D';
	}
}
