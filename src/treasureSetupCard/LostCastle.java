package treasureSetupCard;

import java.util.ArrayList;

import main.*;

public class LostCastle {
	private ArrayList<Item> oneM = new ArrayList<Item>();
	private ArrayList<Item> twoM = new ArrayList<Item>();
	private ArrayList<ArrayList<Item>> threeM = new ArrayList<ArrayList<Item>>();
	private ArrayList<ArrayList<Item>> fourM = new ArrayList<ArrayList<Item>>();
	private ArrayList<ArrayList<Item>> fiveM = new ArrayList<ArrayList<Item>>();
	private ArrayList<ArrayList<Item>> sixM = new ArrayList<ArrayList<Item>>();
	private ArrayList<Chit> lcChits = new ArrayList<Chit>();
	
	public int counter1 = 0;
	public int counter2 = 0;
	public int counter3 = 0;
	public int counter31 = 0;
	public int counter32 = 0;
	public int counter4 = 0;
	public int counter5 = 0;
	public int counter6 = 0;

	public LostCastle(ArrayList<Chit> chits) {
		setRollOne();
		setRollTwo();
		setRollThree();
		setRollFour();
		setRollFive();
		setRollSix();
		lcChits = chits;

	}

	public void setRollOne() {
		oneM.add(Monsters.HEAVYFLYINGDRAGON);
		oneM.add(Monsters.HEAVYFLYINGDRAGON);
	}

	public void setRollTwo() {
		twoM.add(Monsters.VIPER);
		twoM.add(Monsters.VIPER);
	}

	public void setRollThree() {
		ArrayList<Item> threeOne = new ArrayList<Item>();
		ArrayList<Item> threeTwo = new ArrayList<Item>();
		for (int i = 0; i < 3; i++) {
			threeOne.add(Monsters.WOLF1);
		}
		for (int i = 0; i < 3; i++) {
			threeOne.add(Monsters.WOLF2);
		}
		
		for (int i = 0; i < 2; i++) {
			threeTwo.add(Monsters.GIANT);		
		}
		threeM.add(threeOne);
		threeM.add(threeTwo);
	}

	public void setRollFour() {
		ArrayList<Item> fourOne = new ArrayList<Item>();
		ArrayList<Item> fourTwo = new ArrayList<Item>();
		fourOne.add(Monsters.GIANT);
		fourTwo.add(Monsters.GIANT);
		fourM.add(fourOne);
		fourM.add(fourTwo);
	}
	
	public void setRollFive() {
		ArrayList<Item> fiveOne = new ArrayList<Item>();
		fiveOne.add(Monsters.HEAVYSPIDER);
		ArrayList<Item> fiveTwo = new ArrayList<Item>();
		fiveTwo.add(Monsters.HEAVYSPIDER);
		ArrayList<Item> fiveThree = new ArrayList<Item>();
		fiveThree.add(Monsters.HEAVYSPIDER);
		fiveM.add(fiveOne);
		fiveM.add(fiveTwo);
		fiveM.add(fiveThree);
	}
	
	public void setRollSix() {
		ArrayList<Item> sixOne = new ArrayList<Item>();
		sixOne.add(Monsters.GIANTBAT);
		ArrayList<Item> sixTwo = new ArrayList<Item>();
		sixTwo.add(Monsters.GIANTBAT);
		sixTwo.add(Monsters.GIANTBAT);
		ArrayList<Item> sixThree = new ArrayList<Item>();
		sixThree.add(Monsters.GIANTBAT);
		sixThree.add(Monsters.GIANTBAT);
		sixThree.add(Monsters.GIANTBAT);
		sixM.add(sixOne);
		sixM.add(sixTwo);
		sixM.add(sixThree);
	}
	public void setLCChits(ArrayList<Chit> c) {
		lcChits = c;
	}

	public Item getRollOne1() {
		return oneM.get(0);
	}

	public Item getRollOne2() {
		return oneM.get(1);
	}

	public Item getRollTwo1() {
		return twoM.get(0);
	}

	public Item getRollTwo2() {
		return twoM.get(1);
	}
	public ArrayList<ArrayList<Item>> getRollFour() {
		return fourM;
	}
	public ArrayList<ArrayList<Item>> getRollFive() {
		return fiveM;
	}
	public ArrayList<ArrayList<Item>> getRollSix() {
		return sixM;
	}
	public ArrayList<Item> getRollThree1() {
		return threeM.get(0);
	}
	public ArrayList<Item> getRollThree2() {
		return threeM.get(1);
	}
	public ArrayList<Item> getRollFour1() {
		return fourM.get(0);
	}
	public ArrayList<Item> getRollFour2() {
		return fourM.get(1);
	}
	public ArrayList<Item> getRollFive1() {
		return fiveM.get(0);
	}
	public ArrayList<Item> getRollFive2() {
		return fiveM.get(1);
	}	
	public ArrayList<Item> getRollFive3() {
		return fiveM.get(2);
	}
	public ArrayList<Item> getRollSix1() {
		return sixM.get(0);
	}
	public ArrayList<Item> getRollSix2() {
		return sixM.get(1);
	}	
	public ArrayList<Item> getRollSix3() {
		return sixM.get(2);
	}
	public ArrayList<Chit> getChits() {
		return lcChits;
	}
	public Chit getChitAt(int i) {
		return lcChits.get(i);
	}
}
