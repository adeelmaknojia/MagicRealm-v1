package treasureSetupCard;

import java.util.ArrayList;
import java.util.Random;

import main.*;

public class Dwellings {
	private ArrayList<Item> rollOne = new ArrayList<Item>();
	private ArrayList<Item> rollTwo = new ArrayList<Item>();
	private ArrayList<Item> rollThree = new ArrayList<Item>();
	private ArrayList<Item> rollFour = new ArrayList<Item>();
	private ArrayList<Item> rollFive = new ArrayList<Item>();
	private ArrayList<Item> rollSix = new ArrayList<Item>();

	public int counter1 = 0;
	public int counter2 = 0;
	public int counter3 = 0;
	public int counter4 = 0;
	public int counter5 = 0;
	public int counter6 = 0;
	public Dwellings() {
		setRollOne();
		setRollTwo();
		setRollThree();
		setRollFour();
		setRollFive();
		setRollSix();
	}

	public void setRollOne() {
		rollOne.add(NativesI.COMPANY);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			rollOne.add(Items.treasures.get(l));
		}
		rollOne.add(Items.HELMET);
		rollOne.add(Items.BREASTPLATE);
		rollOne.add(Items.SHIELD);
	}
	
	public void setRollTwo() {
		rollTwo.add(NativesI.WOODFOLK);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			rollTwo.add(Items.treasures.get(l));
		}
		rollTwo.add(WeaponInst.LIGHTBOW);
		rollTwo.add(WeaponInst.LIGHTBOW);
		rollTwo.add(WeaponInst.MEDIUMBOW);
	}
	
	public void setRollThree() {
		rollThree.add(NativesI.PATROL);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			rollThree.add(Items.treasures.get(l));
		}
		rollThree.add(Items.HELMET);
		rollThree.add(Items.BREASTPLATE);
		rollThree.add(Items.SHIELD);
	}
	
	public void setRollFour() {
		rollFour.add(NativesI.LANCERS);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			rollFour.add(Items.treasures.get(l));
		}
		rollFour.add(WeaponInst.SPEAR);
		rollFour.add(WeaponInst.SPEAR);
		rollFour.add(WeaponInst.SPEAR);
	}
	
	public void setRollFive() {
		rollFive.add(NativesI.BASHKARS);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			rollFour.add(Items.treasures.get(l));
		}
	}
	
	public void setRollSix() {
		
	}
	
	public ArrayList<Item> getRollOne() {
		return rollOne;
	}
	public ArrayList<Item> getRollTwo() {
		return rollTwo;
	}
	public ArrayList<Item> getRollThree() {
		return rollThree;
	}
	public ArrayList<Item> getRollFour() {
		return rollFour;
	}
	public ArrayList<Item> getRollFive() {
		return rollFive;
	}
	public ArrayList<Item> getRollSix() {
		return rollSix;
	}
}
