package Tiles;

import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import main.*;
import main.Character;

public class Clearing implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Point location;
	private int num = 0;
	private char clearingType;
	private String tileName;
	private ArrayList<Clearing> nClearing = new ArrayList<Clearing>();
	private Dwelling dwelling;
	private ArrayList<Character> players = new ArrayList<Character>();
	private ArrayList<NativeGroups> natives = new ArrayList<NativeGroups>();
	private ArrayList<Monster> monsters = new ArrayList<Monster>();
	//private Visitor visitor;
	private ArrayList<TreasureCard> treasures = new ArrayList<TreasureCard>();
	private ArrayList<Weapons> weapons = new ArrayList<Weapons>();
	private ArrayList<Armor> armors = new ArrayList<Armor>();
	private ArrayList<Item> items;
	private ArrayList<Item> siteTreasures = new ArrayList<Item>();
	private ArrayList<Item> treasuresInClearing = new ArrayList<Item>();
	private int itemCounter;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeObject(location);
		stream.writeInt(num);
		stream.writeChar(clearingType);
		stream.writeObject(tileName);
		stream.writeObject(nClearing);
		stream.writeObject(dwelling);
		stream.writeObject(players);
		stream.writeObject(natives);
		stream.writeObject(monsters);
	//	stream.writeObject(visitor);
		stream.writeObject(treasures);
		stream.writeObject(weapons);
		stream.writeObject(armors);
		stream.writeObject(items);
		stream.writeObject(siteTreasures);
		stream.writeObject(treasuresInClearing);
		stream.writeInt(itemCounter);
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		location = (Point) stream.readObject();
		num = stream.readInt();
		clearingType = stream.readChar();
		tileName = (String) stream.readObject();
		nClearing = (ArrayList<Clearing>) stream.readObject();
		dwelling = (Dwelling) stream.readObject();
		//visitor = (Visitor) stream.readObject();
		players = (ArrayList<Character>) stream.readObject();
		natives = (ArrayList<NativeGroups>) stream.readObject();
		monsters = (ArrayList<Monster>) stream.readObject();
		treasures = (ArrayList<TreasureCard>) stream.readObject();
		weapons = (ArrayList<Weapons>) stream.readObject();
		armors = (ArrayList<Armor>) stream.readObject();
		items = (ArrayList<Item>) stream.readObject();
		siteTreasures = (ArrayList<Item>) stream.readObject();
		treasuresInClearing = (ArrayList<Item>) stream.readObject();
		itemCounter = stream.readInt();
	}

	public Clearing(Point p, int num, char type) {
		location = p;
		this.num = num;
		clearingType = type;
		tileName = "";
		dwelling = null;
		items = new ArrayList<Item>();
		itemCounter = 0;
		//visitor = null;
	}

	// public getClearin

	public void setLocation(int x, int y) {
		location = new Point(x, y);
	}

	public Point getLocation() {
		return location;
	}

	public void setTileName(String n) {
		tileName = n;
	}

	public String getTileName() {
		return tileName;
	}

	public void setClearingNume(int x) {
		num = x;
	}

	public int getClearingNum() {
		return num;
	}

	public void setClearingType(char t) {
		clearingType = t;
	}

	public char getClearingType() {
		return clearingType;
	}

	public int getXLocation() {
		return location.x;
	}

	public int getYLocation() {
		return location.y;
	}

	public Clearing getAdjacentClearing(int i) {
		return nClearing.get(i);
	}

	public void setAdjacentClearing(Clearing n) {
		nClearing.add(n);
	}

	public int getTotalAdjacentClearings() {
		return nClearing.size();
	}

	public ArrayList<Clearing> getAllAdjacentClearings() {
		return nClearing;
	}

	// DWELLINGS POSITION 0
	public Dwelling getDwelling() {
		return dwelling;
	}

	public void setDwelling(Dwelling d) {
		dwelling = d;
		items.add(dwelling);
		itemCounter++;
	}
	
	public Visitor getVisitor() {
		return null;//visitor;
	}

	public void setVisitor(Visitor v) {
		//visitor = v;
		//items.add(visitor);
	//	itemCounter++;
	}

	// CHARACTERS POSITION 1
	public void placeCharacter(Character p) {
		players.add(p);
	}

	public Character removeCharacter(Character p) {
		Character r = null;
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).equals(p)) {
				r = players.remove(i);
			}
		}

		itemCounter--;
		return r;
	}

	public Character getCharacterOnClearing(int i) {
		return players.get(i);
	}

	public ArrayList<Character> getAllCharactersOnClearing() {
		return players;
	}

	// NATIVE GROUPS POSITION 2
	public void addNatives(NativeGroups n) {
		items.add(n);
		natives.add(n);
		itemCounter++;
	}

	public NativeGroups removeNativeGroup(NativeGroups m) {
		NativeGroups ng = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(2).getName() == m.getName()) {
				ng = (NativeGroups) items.remove(i);
				break;
			}
		}
		for (int i = 0; i < natives.size(); i++) {
			if (natives.get(i).getName() == m.getName()) {
				natives.remove(i);
				break;
			}
		}
		itemCounter--;
		return ng;
	}

	public NativeGroups getNativeGroup(int i) {
		return natives.get(i);
	}

	public ArrayList<NativeGroups> getAllNatives() {
		return natives;
	}

	public ArrayList<Item> findAllNativeItems() {
		ArrayList<Item> ng = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemType() == 'N') {
				ng.add(items.get(i));
			}
		}
		return ng;
	}

	// MONSTERS POSITION 3
	public void addMonster(Monster m) {
		monsters.add(m);
		itemCounter++;
	}

	public Monster removeMonster(Monster m) {
		Monster mon = null;
		for (int i = 0; i < monsters.size(); i++) {
			if (monsters.get(i).getName() == m.getName()) {
				monsters.remove(i);
				break;
			}
		}
		itemCounter--;
		return mon;
	}
	
	public Monster removeMonster(int m) {
		Monster mon = monsters.remove(m);
		
		itemCounter--;
		return mon;
	}

	public Monster getMonster(int i) {
		return monsters.get(i);
	}

	public ArrayList<Monster> getAllMonsters() {
		return monsters;
	}

	// TREASURE CARDS POSITION 4
	public void addTreasure(TreasureCard t) {
		items.add(t);
		treasures.add(t);
		treasuresInClearing.add(t);
		itemCounter++;
	}

	public TreasureCard removeTreasure(Item n) {
		TreasureCard t = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).equals(n)) {
				t = (TreasureCard) items.remove(i);
				break;
			}
		}
		for (int i = 0; i < treasures.size(); i++) {
			if (treasures.get(i).equals(n)) {
				treasures.remove(i);
				break;
			}
		}
		for (int i = 0; i < treasuresInClearing.size(); i++) {
			if (treasuresInClearing.get(i).equals(n)) {
				treasuresInClearing.remove(i);
				break;
			}
		}
		itemCounter++;
		return t;
	}

	public TreasureCard getTreasureCard(int loc) {
		return treasures.get(loc);
	}

	public ArrayList<TreasureCard> getAllTreasureCards() {
		return treasures;
	}

	public ArrayList<Item> findAllTreasureItems() {
		ArrayList<Item> t = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemType() == 'T') {
				t.add(items.get(i));
			}
		}
		return t;
	}

	// WEAPONS POSITION 5
	public void addWeapon(Weapons n) {
		weapons.add(n);
		items.add(n);
		treasuresInClearing.add(n);
		itemCounter++;
	}

	public Weapons removeWeapon(Weapons m) {
		Weapons w = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName() == m.getName()) {
				w = (Weapons) items.remove(i);
				break;
			}
		}
		for (int i = 0; i < weapons.size(); i++) {
			if (weapons.get(i).getName() == m.getName()) {
				weapons.remove(i);
				break;
			}
		}
		for (int i = 0; i < treasuresInClearing.size(); i++) {
			if (treasuresInClearing.get(i).getName() == m.getName()) {
				treasuresInClearing.remove(i);
				break;
			}
		}
		itemCounter--;
		return w;
	}

	public Weapons getWeapon(int i) {
		return weapons.get(i);
	}

	public ArrayList<Weapons> getAllWeapons() {
		return weapons;
	}

	public ArrayList<Item> findAllWeaponItems() {
		ArrayList<Item> w = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemType() == 'W') {
				w.add(items.get(i));
			}
		}
		return w;
	}

	// ARMORS POSITION 6
	public void addArmor(Armor n) {
		items.add(n);
		armors.add(n);
		treasuresInClearing.add(n);
		itemCounter++;
	}

	public Armor removeArmor(Armor m) {
		Armor ng = null;
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getName() == m.getName()) {
				ng = (Armor) items.remove(i);
				break;
			}
		}
		for (int i = 0; i < armors.size(); i++) {
			if (armors.get(i).getName() == m.getName()) {
				armors.remove(i);
				break;
			}
		}
		for (int i = 0; i < treasuresInClearing.size(); i++) {
			if (treasuresInClearing.get(i).getName() == m.getName()) {
				treasuresInClearing.remove(i);
				break;
			}
		}
		itemCounter--;
		return ng;
	}

	public Armor getArmor(int i) {
		return armors.get(i);
	}

	public ArrayList<Armor> getAllArmors() {
		return armors;
	}

	public ArrayList<Item> findAllArmorItems() {
		ArrayList<Item> a = new ArrayList<Item>();
		for (int i = 0; i < items.size(); i++) {
			if (items.get(i).getItemType() == 'A') {
				a.add(items.get(i));
			}
		}
		return a;
	}

	public ArrayList<Item> getAllItems() {
		return items;
	}

	public Item getItem(int i) {
		return items.get(i);
	}
	
	public ArrayList<Item> getSiteTreasures() {
		return siteTreasures;
	}

	public Item getSiteTreasureAt(int i) {
		return siteTreasures.get(i);
	}
	
	public void setSiteTreasures(ArrayList<Item> st) {
		siteTreasures = st;
	}
	
	public void addSiteTreasures(Item st) {
		siteTreasures.add(st);
	}
	
	public ArrayList<Item> getTreasuresInClearing() {
		return treasuresInClearing;
	}
	
	public void setTreasuresInClearing(ArrayList<Item> st) {
		treasuresInClearing = st;
		items.addAll(st);
		itemCounter += st.size();
	}
	
	public void addTreasureInClearing(Item m) {
		treasuresInClearing.add(m);
		items.add(m);
		if(m.getItemType() == 'A') {
			Armor a = (Armor) m;
			armors.add(a);
		} else if(m.getItemType() == 'W') {
			Weapons w = (Weapons) m;
			weapons.add(w);
		} else if(m.getItemType() == 'T') {
			TreasureCard t = (TreasureCard) m;
			treasures.add(t);
		}
		itemCounter++;
	}
	
	public Item removeTreasureInClearing(int loc) {
		Item m = treasuresInClearing.remove(loc);
		for(int i = 0; i < items.size(); i++) {
			if(m.getName() == items.get(i).getName()) {
				items.remove(i);
				break;
			}
		}
		if(m.getItemType() == 'A') {
			for(int i = 0; i < armors.size(); i++) {
				if(m.getName() == armors.get(i).getName()) {
					armors.remove(i);
					break;
				}
			}
		} else if(m.getItemType() == 'W') {
			for(int i = 0; i < weapons.size(); i++) {
				if(m.getName() == weapons.get(i).getName()) {
					weapons.remove(i);
					break;
				}
			}
		} else if(m.getItemType() == 'T') {
			for(int i = 0; i < weapons.size(); i++) {
				if(m.getName() == weapons.get(i).getName()) {
					weapons.remove(i);
					break;
				}
			}
		}
		itemCounter--;
		return m;
	}
	
	public Item removeSiteTreasures(int i) {
		return siteTreasures.remove(i);
	}
	
	public int getItemCounter() {
		return itemCounter;
	}
}
