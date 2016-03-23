package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class SoundChits implements Chit, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4477042231093584191L;
	private String name;
	private char type;
	private int num;
	private Image[] img = new Image[2];
	private Point location;
	private int hidden;
	private String imgPaths0, imgPaths1;

	public SoundChits(String name, int n, String h, String u) {
		this.name = name;
		imgPaths0 = u;
		imgPaths1 = h;
		this.type = 'R';
		this.num = n;
		this.img[0] = new ImageIcon(u).getImage();
		this.img[1] = new ImageIcon(h).getImage();
		this.location = new Point();
		hide();

	}

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeObject(name);
		stream.writeObject("one");
		stream.writeObject("two");
		stream.writeChar(type);
		stream.writeInt(num);
		stream.writeObject(location);
		stream.writeInt(hidden);
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		name = (String) stream.readObject();
		imgPaths0 = new String((String) stream.readObject());
		imgPaths1 = new String((String) stream.readObject());
		type = stream.readChar();
		num = stream.readInt();
		location = (Point) stream.readObject();
		hidden = stream.readInt();
		img = new Image[2];
		this.img[0] = new ImageIcon(imgPaths0).getImage();
		this.img[1] = new ImageIcon(imgPaths1).getImage();
	}

	@Override
	public char getChitType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void setChitType(char t) {
		// TODO Auto-generated method stub
		type = t;
	}

	@Override
	public String getChitName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public int getChitNum() {
		// TODO Auto-generated method stub
		return num;
	}

	@Override
	public Image getImage(int hidden) {
		// TODO Auto-generated method stub
		return img[hidden];
	}

	@Override
	public boolean isLost() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public char getWarningType() {
		// TODO Auto-generated method stub
		return 0;
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

	public void hide() {
		hidden = 1;
	}

	public void unhide() {
		hidden = 0;
	}

	@Override
	public String toString() {
		return "Sound Chit: \n Name: " + this.getChitName() + " "
				+ this.getChitNum();
	}

}
