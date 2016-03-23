package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

public interface Chit {

	public Point getChitLocation();

	public void setChitLocation(int x, int y);

	public char getChitType();

	public void setChitType(char t);

	public String getChitName();

	public int getChitNum();

	public Image getImage(int hidden);

	public boolean isLost();

	public static void addChit(int i, int j, Graphics2D g2, Image img) {
		int x = i - (30 / 2);
		int y = j - (30 / 2);
		g2.drawImage(img, x, y, 25, 25, null);
	};

	public char getWarningType();

	public int isHidden();

	public void hide();

	public void unhide();

	public String toString();

}
