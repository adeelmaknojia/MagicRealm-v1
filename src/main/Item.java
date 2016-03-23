package main;

import java.awt.Graphics2D;
import java.awt.Image;

public interface Item {

	public Image getImage();

	public String getName();

	public char getItemType();

	public static void addItem(int i, int j, Graphics2D g2, Image img) {
		int x = i - (30 / 2);
		int y = j - (30 / 2);
		g2.drawImage(img, x, y, 20, 20, null);
	};

	public static void addDwelling(Dwelling d, Graphics2D g2) {
		int x = d.getLocation().x - (30 / 2);
		int y = d.getLocation().y - (30 / 2);
		g2.drawImage(d.getImage(), x, y, 30, 30, null);
	}

	public static void addCharacters(Character p, Graphics2D g2) {
		int x = p.getPLocation().x - 10;
		int y = p.getPLocation().y - 10;
		g2.drawImage(p.getImage(), x, y, 30, 30, null);
	}

}
