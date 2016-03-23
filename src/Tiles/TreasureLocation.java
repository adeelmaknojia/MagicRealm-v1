package Tiles;

import java.util.ArrayList;
import java.util.Random;

import main.*;

public class TreasureLocation 
{
	private ArrayList<ArrayList<Item>> rollOne = new ArrayList<ArrayList<Item>>();
	private ArrayList<ArrayList<Item>> rollTwo = new ArrayList<ArrayList<Item>>();
	private ArrayList<Item> rollThree = new ArrayList<Item>();
	private ArrayList<Item> rollFour = new ArrayList<Item>();
	private ArrayList<ArrayList<Item>> rollFive = new ArrayList<ArrayList<Item>>();

	public TreasureLocation() {
		setRollOne();
		setRollTwo();
		setRollThree();
		setRollFour();
		setRollFive();
	}


	public void setRollOne() {
		ArrayList<Item> oneOne = new ArrayList<Item>();
		ArrayList<Item> oneTwo = new ArrayList<Item>();
		
		oneOne.add(Monsters.TREMENDOUSFLYINGDRAGON);
		oneOne.add(Items.CloakOfMist);
		Random r = new Random();
		int t = r.nextInt(Items.treasures.size());
		for (int i = 0; i < 4; i++) {
			t = r.nextInt(Items.treasures.size());
			oneOne.add(Items.treasures.get(t));
		}
		
		oneOne.add(Monsters.TREMENDOUSDRAGON);
		oneTwo.add(Items.HandyGloves);
		for (int i = 0; i < 4; i++) {
			t = r.nextInt(Items.treasures.size());
			oneTwo.add(Items.treasures.get(t));
		}
		
		rollOne.add(oneOne);
		rollOne.add(oneTwo);
	}
	public void setRollTwo() {
		ArrayList<Item> twoOne = new ArrayList<Item>();
		twoOne.add(Monsters.DEMON);
		twoOne.add(Items.ShoesOfStealth);
		twoOne.add(Items.LeagueBoots);
		Random r = new Random();
		ArrayList<Item> twoTwo = new ArrayList<Item>();
		twoTwo.add(Monsters.WINGEDDEMON);
		twoTwo.add(Items.DeftGloves);
		for (int i = 0; i < 2; i++) {
			int t = r.nextInt(Items.treasures.size());
			twoTwo.add(Items.treasures.get(t));
		}
		
		rollTwo.add(twoOne);
		rollTwo.add(twoTwo);
	}
	public void setRollThree() {
		Random r = new Random();
		rollThree.add(Monsters.OCTOPUS);
		for (int i = 0; i < 2; i++) {
			int t = r.nextInt(Items.treasures.size());
			rollThree.add(Items.treasures.get(t));
		}
		rollThree.add(Items.CloakOfMist);
		
	}
	public void setRollFour() {
		rollFour.add(Monsters.TREMENDOUSTROLL);
		rollFour.add(Items.MagicSpectacles);
		rollFour.add(Items.LeagueBoots);
	}
	public void setRollFive() {
		ArrayList<Item> fiveOne = new ArrayList<Item>();
		fiveOne.add(Monsters.TREMENDOUSSPIDER);
		fiveOne.add(Items.ShoesOfStealth);
		Random r = new Random();
		int t = r.nextInt(Items.treasures.size());
		for (int i = 0; i < 6; i++) {
			t = r.nextInt(Items.treasures.size());
			fiveOne.add(Items.treasures.get(t));
		}
		ArrayList<Item> fiveTwo = new ArrayList<Item>();
		fiveTwo.add(Monsters.IMP);
		fiveTwo.add(Items.DeftGloves);
		for (int i = 0; i < 2; i++) {
			t = r.nextInt(Items.treasures.size());
			fiveTwo.add(Items.treasures.get(t));
		}
		
		rollFive.add(fiveOne);
		rollFive.add(fiveTwo);

	}
	
	public ArrayList<Item> getTreasuresRollOne1() {
		return rollOne.get(0);
	}
	public ArrayList<Item> getTreasuresRollOne2() {
		return rollOne.get(1);
	}
	public ArrayList<Item> getTreasuresRollTwo1() {
		return rollTwo.get(0);
	}
	public ArrayList<Item> getTreasuresRollTwo2() {
		return rollTwo.get(1);
	}
	public ArrayList<Item> getTreasuresRollThree() {
		return rollThree;
	}
	public ArrayList<Item> getTreasuresRollFour() {
		return rollFour;
	}
	public ArrayList<Item> getTreasuresRollFive1() {
		return rollFive.get(0);
	}
	public ArrayList<Item> getTreasuresRollFive2() {
		return rollOne.get(1);
	}





}
