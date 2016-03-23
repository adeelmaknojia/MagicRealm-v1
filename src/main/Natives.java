/**
 * 
 */
package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tiles.Clearing;

public class Natives implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5429279122811677294L;
	private String nativeName;
	private Weapons weapon;
	private String vulnerability; // make enum?
	private boolean isArmored;
	private int wageCost;
	private int[] bounty = new int[2];
	private char moveStrength;
	private char weight;
	private Image img;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		// TODO
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		// TODO
	}

	public Natives(String name, Weapons weapon, String vulnerability,
			boolean isArmored, int wageCost, int[] bounty, char moveStrength,
			char weight, String i) {
		super();
		this.nativeName = name;
		this.weapon = weapon;
		this.vulnerability = vulnerability;
		this.isArmored = isArmored;
		this.wageCost = wageCost;
		this.bounty = bounty;
		this.moveStrength = moveStrength;
		this.weight = weight;
		this.img = new ImageIcon(i).getImage();
	}

	public String getNativeName() {
		return nativeName;
	}

	public void setNativeName(String nativeName) {
		this.nativeName = nativeName;
	}

	public Image getImage() {
		return img;
	}

	public void setImage(Image i) {
		this.img = i;
	}

	public int getWageCost() {
		return wageCost;
	}

	public void setWageCost(Integer wageCost) {
		this.wageCost = wageCost;
	}

	public char getMoveStrength() {
		return moveStrength;
	}

	public void setMoveStrength(char moveStrength) {
		this.moveStrength = moveStrength;
	}

	public char getWeight() {
		return weight;
	}

	public void setWeight(char weight) {
		this.weight = weight;
	}

	public Weapons getWeapon() {
		return weapon;
	}

	public String getVulnerability() {
		return vulnerability;
	}

	public boolean getIsArmored() {
		return isArmored;
	}

	public int[] getBounty() {
		return bounty;
	}

}