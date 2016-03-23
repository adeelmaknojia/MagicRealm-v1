package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tiles.Clearing;

public class Weapons implements Item, ItemObject, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9139416135169733681L;
	private int alert; // side of the weapon counter (alerted and unalerted
						// side)
	private String weaponName; // name of the weapon (bow, spear etc)
	private String attack; // method of attack (missile or striking)
	private int length; // length of weapon (0 - 18) larger number indicate
						// longer weapon
	private int[] sharpness; // defines the extra damage it can inflict
	private int[] attackTime; // weapon attack time if present
	private char weaponWeight; // weight of the weapon (H, L, M)
	private int weaponWeightE;
	private int weaponPrice;
	private Image[] img = new Image[2];
	private int fatigued;
	private int wounded;
	private boolean active;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		// TODO
		
		stream.writeInt(alert);
		stream.writeObject(weaponName);
		stream.writeObject(attack);
		stream.writeInt(length);
		stream.writeObject(sharpness);
		stream.writeObject(attackTime);
		stream.writeChar(weaponWeight);
		stream.writeInt(weaponWeightE);
		stream.writeInt(weaponPrice);
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		// TODO
	}

	/* CONSTRUCTOR */
	public Weapons (String Wname, String Wattack, int Wlength, 
			int[] Wsharpness, int[] Wtime, char Wweight, int wPrice, String i){

		this.alert = 0;
		this.weaponName = Wname;
		this.attack = Wattack;
		this.length  = Wlength;
		this.sharpness = Wsharpness;
		this.attackTime = Wtime;
		this.weaponWeight = Wweight;
		setWeightE(this.weaponWeight);
		this.weaponPrice = wPrice;
		this.img[0] = new ImageIcon(i).getImage();
		this.img[1] = new ImageIcon(i).getImage();
		this.fatigued = 0;
		this.wounded = 0;
		active =  false;
	}

	/* GETTERS */

	public int isAlerted() {return alert;}
	public String getName() {return weaponName;}
	public String getAttack() {return attack;}
	public int getLength() {return length;}
	public int getSharpness() {return sharpness[alert];}
	public int getAttackTime() {return attackTime[alert];}
	public char getWeight() {return weaponWeight;}
	public int getWeightE() {return weaponWeightE;}
	public int getPrice() {return weaponPrice;}
	public Image getImage() { return img[alert];}
	public char getItemType() { return 'W';}
	public int getfatigueAmount() { return fatigued;}
	public int getWoundedAmount() { return wounded;}
	public boolean isFatigued() { return (fatigued > 0) ?  true : false;}
	public boolean isWounded() { return (wounded > 0) ? true: false;}
	public boolean isActive() { return active;}

	/* SETTERS*/
	public void alert() {this.alert = 1;}
	public void unAlert() {this.alert = 0;}
	public void setWeaponName(String weaponName) {this.weaponName = weaponName;}
	public void setAttack(String attack) {this.attack = attack;}
	public void setLength(int length) {this.length = length;}
	public void setSharpness(int sharpness) {this.sharpness[alert] = sharpness;}
	public void setAttackTime(int attackTime) {this.attackTime[alert] = attackTime;}
	public void setWeight(char weight) {this.weaponWeight = weight;}
	public void setWeightE(int weight) {this.weaponWeightE = weight;}
	public void setWeaponPrice(int weaponPrice) {this.weaponPrice = weaponPrice;}
	public void makeFatigue() { this.fatigued++;}
	public void makeWounded() { this.wounded++;}
	public void activate() {this.active = true;}
	public void unActivate() { this.active = false;}
	public void setWeightLevel(char w) {
		if(w == 'L') {
			weaponWeightE = 1;
		} else if(w == 'M') {
			weaponWeightE = 2;
		} else if(w == 'H') {
			weaponWeightE = 3;
		} else if(w == 'T') {
			weaponWeightE = 4;
		}
	}

}
