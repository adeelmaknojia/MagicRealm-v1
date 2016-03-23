package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tiles.Clearing;

public class TreasureCard implements Item, ItemObject, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6040871489434912118L;
	private String treasureName;
	private int section;
	private boolean greatTreasure;
	private boolean largeTreasure;
	private String chit;
	private String fameReward;// ??
	private int fame;
	private int notoriety;
	private int gold;
	private Image img;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		// TODO
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		// TODO
	}

	public TreasureCard(String treasureName, int section,
			boolean greatTreasure, boolean lgTreasure, String chit,
			String fameReward, int fame, int notoriety, int gold, String i) {
		this.treasureName = treasureName;
		this.section = section;
		this.greatTreasure = greatTreasure;
		this.largeTreasure = lgTreasure;
		this.chit = chit;
		this.fameReward = fameReward;
		this.fame = fame;
		this.notoriety = notoriety;
		this.gold = gold;
		this.img = new ImageIcon(i).getImage();
	}

	public boolean isGreatTreasure() {
		return greatTreasure;
	}

	public void setGreatTreasure(boolean greatTreasure) {
		this.greatTreasure = greatTreasure;
	}

	public boolean isLargeTreasure() {
		return largeTreasure;
	}

	public void setLargeTreasure(boolean lTreasure) {
		this.largeTreasure = lTreasure;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int s) {
		this.section = s;
	}

	public String getChit() {
		return chit;
	}

	public void setChit(String chit) {
		this.chit = chit;
	}

	public String getFameReward() {
		return fameReward;
	}

	public void setFameReward(String fameReward) {
		this.fameReward = fameReward;
	}

	public int getNotoriety() {
		return notoriety;
	}

	public void setNotoriety(int notoriety) {
		this.notoriety = notoriety;
	}

	public String getName() {
		return treasureName;
	}

	public int getFame() {
		return fame;
	}

	public int getPrice() {
		return gold;
	}

	public Image getImage() {
		return img;
	}

	public char getItemType() {
		return 'T';
	}
}