package Tiles;

import java.util.ArrayList;

import main.*;

public class LostCity 
{
	private ArrayList<ArrayList<Item>> oneM = new ArrayList<ArrayList<Item>>();
	private ArrayList<ArrayList<Item>> twoM = new ArrayList<ArrayList<Item>>();
	private ArrayList<ArrayList<Item>> threeM = new ArrayList<ArrayList<Item>>();
	private ArrayList<ArrayList<Item>> fourM = new ArrayList<ArrayList<Item>>();
	public ArrayList<Chit> lcChits = new ArrayList<Chit>();
	
	public LostCity(ArrayList<Chit> cityChits ) {
		setRollOne();
		setRollTwo();
		setRollThree();
		setRollFour();
		lcChits = cityChits;
	}
	
	public void setRollOne() {
		ArrayList<Item> oneOne = new ArrayList<Item>();
		ArrayList<Item> oneTwo = new ArrayList<Item>();
		oneOne.add(Monsters.HEAVYDRAGON);
		oneTwo.add(Monsters.TREMENDOUSDRAGON);
		oneM.add(oneOne);
		oneM.add(oneTwo);
	}
	
	public void setRollTwo() {
		ArrayList<Item> twoOne = new ArrayList<Item>();
		ArrayList<Item> twoTwo = new ArrayList<Item>();
		twoOne.add(Monsters.HEAVYSERPENT);
		twoOne.add(Monsters.HEAVYSERPENT);
		twoTwo.add(Monsters.TREMENDOUSSERPENT);
		twoM.add(twoOne);
		twoM.add(twoTwo);
	}
	
	public void setRollThree() {
		ArrayList<Item> threeOne = new ArrayList<Item>();
		ArrayList<Item> threeTwo = new ArrayList<Item>();
		ArrayList<Item> threeThree = new ArrayList<Item>();
		for (int i = 0; i < 6; i++) {
			threeOne.add(Monsters.GOBLINAXE);
		}
		for (int i = 0; i < 6; i++) {
			threeTwo.add(Monsters.GOBLINSPEAR);
		}
		for (int i = 0; i < 6; i++) {
			threeThree.add(Monsters.GOBLINGSWORD);
		}
		threeM.add(threeOne);
		threeM.add(threeTwo);
		threeM.add(threeThree);
	}
	
	public void setRollFour() {
		ArrayList<Item> fourOne = new ArrayList<Item>();
		for (int i = 0; i < 2; i++) {
			fourOne.add(Monsters.HEAVYTROLL);
		}
		ArrayList<Item> fourTwo = new ArrayList<Item>();
		fourTwo.add(Monsters.TREMENDOUSTROLL);
		fourM.add(fourOne);
		fourM.add(fourTwo);
	}
	
	public void setLCCHits(ArrayList<Chit>c) {
		lcChits = c;
	}
	
	public ArrayList<Item> getRollOne1() {
		return oneM.get(0);
	}
	
	public ArrayList<Item> getRollOne2() {
		return oneM.get(1);
	}
	
	public ArrayList<Item> getRollTwo1() {
		return twoM.get(0);
	}
	
	public ArrayList<Item> getRollTwo2() {
		return twoM.get(1);
	}
	public ArrayList<Item> getRollThree1() {
		return threeM.get(0);
	}
	public ArrayList<Item> getRollThree2() {
		return threeM.get(1);
	}
	public ArrayList<Item> getRollThree3() {
		return threeM.get(2);
	}
	public ArrayList<Item> getRollFour1() {
		return fourM.get(0);
	}
	public ArrayList<Item> getRollFour2() {
		return fourM.get(1);
	}
	public ArrayList<Chit> getChits() {
		return lcChits;
	}
	public Chit getChitAt(int i) {
		return lcChits.get(i);
	}
}
