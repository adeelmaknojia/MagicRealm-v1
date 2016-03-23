package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tiles.Clearing;

public class Monster implements Item, Serializable {
	private String name;
	private String size;
	private boolean isArmored;
	private int[] bounty;
	private boolean fly;
	private WeaponInst weapon;
	private boolean targetAcquired;
	private int[] sharpness;
	private int[] attackSpeed;
	private char[] harmLetter;
	private int[] harmLevel = {0, 0};
	private int[] moveSpeed;
	private Image img;
	private String imgPath = "";

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeObject(name);
		stream.writeObject(size);
		stream.writeBoolean(isArmored);
		stream.writeObject(bounty);
		stream.writeBoolean(fly);
		stream.writeObject(weapon);
		stream.writeBoolean(targetAcquired);
		stream.writeObject(sharpness);
		stream.writeObject(attackSpeed);
		stream.writeObject(harmLetter);
		stream.writeObject(harmLevel);
		stream.writeObject(moveSpeed);
		stream.writeObject(imgPath);
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		name = (String) stream.readObject();
		size = (String) stream.readObject();
		isArmored = stream.readBoolean();
		bounty = new int[2];
		bounty = (int[]) stream.readObject();
		fly = stream.readBoolean();
		weapon = (WeaponInst) stream.readObject();
		targetAcquired = stream.readBoolean();
		sharpness = new int[2];
		sharpness = (int[]) stream.readObject();

		attackSpeed = new int[2];
		attackSpeed = (int[]) stream.readObject();

		harmLetter = new char[2];
		harmLetter = (char[]) stream.readObject();

		harmLevel = new int[2];
		harmLevel = (int[]) stream.readObject();
		
		moveSpeed = new int[2];
		moveSpeed = (int[]) stream.readObject();

		imgPath = (String) stream.readObject();
		img = new ImageIcon(imgPath).getImage();
	}

	public Monster(String name, String size, boolean isArmored, int[] bounty,
			boolean fly, WeaponInst weapon, int[] sharpness, char[] harmLetter,
			int[] attackSpeed, int[] moveSpeed, String i) {
		this.name = name;
		this.size = size;
		this.isArmored = isArmored;
		this.bounty = bounty;
		this.fly = fly;
		this.weapon = weapon;
		this.sharpness = sharpness;
		this.attackSpeed = attackSpeed;
		this.harmLetter = harmLetter;
		setHarmLevelN(harmLetter[0]);
		setHarmLevelT(harmLetter[1]);
		this.moveSpeed = moveSpeed;
		this.targetAcquired = false;
		this.img = new ImageIcon(i).getImage();
		this.imgPath = i;
	}

	public boolean isArmored() {
		return isArmored;
	}

	public void setArmored(boolean isArmored) {
		this.isArmored = isArmored;
	}

	public boolean isTargetAcquired() {
		return targetAcquired;
	}

	public void setTargetAcquired(boolean targetAcquired) {
		this.targetAcquired = targetAcquired;
	}

	public String getName() {
		return name;
	}

	public String getSize() {
		return size;
	}

	public int[] getBounty() {
		return bounty;
	}

	public int getFame() {
		return bounty[0];
	}

	public int getNotoriety() {
		return bounty[1];
	}

	public boolean isFly() {
		return fly;
	}

	public WeaponInst getWeapon() {
		return weapon;
	}

	public int getSharpnessNormal() {
		return sharpness[0];
	}

	public int getSharpnessTargetAquired() {
		return sharpness[1];
	}

	public int getAttackSpeedNormal() {
		return attackSpeed[0];
	}

	public int getAttackSpeedTargetAcquired() {
		return attackSpeed[1];
	}

	public char getHarmLetterNormal() {
		return harmLetter[0];
	}

	public char getHarmLetterTargetAcquired() {
		return harmLetter[1];
	}
	
	public int getHarmLevelNormal() {
		return harmLevel[0];
	}

	public int getHarmLevelTargetAcquired() {
		return harmLevel[1];
	}

	public int getMoveSpeedNormal() {
		return moveSpeed[0];
	}

	public int getMoveSpeedTargetAcquired() {
		return moveSpeed[1];
	}

	public Image getImage() {
		return img;
	}

	public char getItemType() {
		return 'M';
	}
	public void setHarmLevelN(int w) {
		harmLevel[0]= w;
	}
	public void setHarmLevelT(int w) {
		harmLevel[1]= w;
	}
	public void setHarmLevelN(char w) {
		if(w == 'L') {
			harmLevel[0] = 1;
		} else if(w == 'M') {
			harmLevel[0] = 2;
		} else if(w == 'H') {
			harmLevel[0] = 3;
		} else if(w == 'T') {
			harmLevel[0] = 4;
		} else {
			harmLevel[0] = 0;
		}
	}
	
	public void setHarmLevelT(char w) {
		if(w == 'L') {
			harmLevel[1] = 1;
		} else if(w == 'M') {
			harmLevel[1] = 2;
		} else if(w == 'H') {
			harmLevel[1] = 3;
		} else if(w == 'T') {
			harmLevel[1] = 4;
		} else{
			harmLevel[0] = 0;
		}
	}

}
