package main;

import java.awt.Image;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.ImageIcon;

public class Armor implements Item, ItemObject, Serializable {

	private String name;            // name of the armor (helmet, shield etc)
	private int active;         // check if armor is active or deactive
	private char armorWeight;       // weight of the armor (H, L, M)
	private int armorWeightE;
//	private int harmPonts;          // points needed to damage the armor 
	private boolean damaged;        // find out if armor is damaged
	//private String protectAgainst;  // direction from where it can protect player
	private int[] price;
	private Image[] img = new Image[2];

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		// TODO
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		// TODO
	}

	/* CONSTRUCTOR */
	public Armor(String Aname, char Aweight, int[] p, String u, String a){
		this.name = Aname;
		this.active = 0;
		this.armorWeight = Aweight;
		setArmorWeightE(Aweight);
		price = p;
		img[0] = new ImageIcon(u).getImage();
		img[1] = new ImageIcon(a).getImage();
		
	}
	/* GETTERS */
	
	public String getName() {return name;}
	public int isActive() {return active;}
	public char getArmorWeight() {return armorWeight;}
	public int getArmorWeightE() {return armorWeightE;}
//	public int getHarmPonts() {return harmPonts;}
	public boolean isDamaged() {return damaged;}
	private int damaged() {return (isDamaged() == true) ? 1 : 0;} //NEW LINE
	public int getPrice() {return price[damaged()];}
	public Image getImage() {return img[active];}
	public char getItemType() { return 'A';}

	/* SETTERS */
	
	public void setName(String name) {this.name = name;}
	public void activate() {this.active = 1;}
	public void inActivate() {this.active = 0;}
	public void setArmorWeight(char armorWeight) {this.armorWeight = armorWeight;}
	public void setArmorWeightE(int armorWeight) {this.armorWeightE = armorWeight;}
//	public void setHarmPonts(int harmPonts) {this.harmPonts = harmPonts;}
	public void setDamaged(boolean damaged) {this.damaged = damaged;}
	public void setPriceIntact(int i) {price[0] = i;}
	public void setPriceDamaged(int i) {price[1] = i;}
	public void setWeightLevel(char w) {
		if(w == 'L') {
			armorWeightE = 1;
		} else if(w == 'M') {
			armorWeightE = 2;
		} else if(w == 'H') {
			armorWeightE = 3;
		} else if(w == 'T') {
			armorWeightE = 4;
		}
	}
}
