package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tiles.Clearing;

public class Character implements Item, Serializable {

	private int characterId;
	private String name;
	private String counterName;
	private char weight;
	private int weightE;
	private String startingLocation;
	private String unfriendly[];
	private String friendly[];
	private String neutral[];
	private String ally[];
	private String enemy[];
	private ArrayList<Weapons> weaponList = new ArrayList<Weapons>();
	private ArrayList<Armor> armor = new ArrayList<Armor>();
	private Weapons activeWeapon;
	private ArrayList<ActionChit> actionChits = new ArrayList<ActionChit>();
	private Image cImg[] = new Image[2];
	private Point pLocation = new Point();
	private String boardLocation;
	private Clearing clearingLocation;
	private int hidden;
	private int moves;
	private int mountainMove;
	private String[] specialAdvantage;
	private int phases;
	private int sunlight;
	private ArrayList<Clearing> pathway;
	private ArrayList<Character> hiddenEnemies;
	private CombatInfo combatInfo;
	private ArrayList<TreasureCard> lootedTreasures = new ArrayList<TreasureCard>();
	private ArrayList<Chit> discoveredChits = new ArrayList<Chit>();
	private ArrayList<Item> itemsOwned = new ArrayList<Item>();
	private boolean extraFeature;
	private boolean blocked;

	// ******* Character Victory requirements *********//
	private int startingGold;
	private int startingFame;
	private int startingNotoriety;
	private int needGold;
	private int needFame;
	private int needNotoriety;
	private int GoldPoint;
	private int FamePoint;
	private int NotorietyPoint;
	private int GoldRecorded;
	private int FameRecorded;
	private int NotorietyRecorded;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeInt(characterId);
		stream.writeObject(counterName);
		stream.writeObject(weight);
		stream.writeObject(weightE);
		stream.writeObject(startingLocation);
		stream.writeObject(name);

	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		name = (String) stream.readObject();
	}

	// CONSTRUCTORS //

	public Character() {
		this.name = "Default";
		this.counterName = "Default";
		this.weight = 'D';
		this.startingLocation = null;
		this.pLocation = new Point();
		this.boardLocation = "";
		this.clearingLocation = null;
		this.hidden = 0;
		this.startingGold = 10;
		this.startingFame = 0;
		this.startingNotoriety = 0;
		this.needGold = 0;
		this.needFame = 0;
		this.needNotoriety = 0;
		this.GoldPoint = 0;
		this.FamePoint = 0;
		this.NotorietyPoint = 0;
		this.GoldRecorded = 0;
		this.FameRecorded = 0;
		this.NotorietyRecorded = 0;
		this.moves = 0;
		this.mountainMove = 0;
		this.phases = 0;
		this.sunlight = 1;
		pathway = new ArrayList<Clearing>();
		hiddenEnemies = null;
	}

	public Character(String Cname, String cCounter, char cWeight,
			ArrayList<Weapons> cWeaponList, ArrayList<Armor> cArmor,
			ArrayList<ActionChit> ac, String imgUn, String imgHid, String[] sa,
			String[] f, String[] u, String[] n, String[] a, String[] en) {
		this.name = Cname;
		this.counterName = cCounter;
		this.weight = cWeight;
		setWeightE(cWeight);
		this.startingGold = 10;
		this.weaponList = cWeaponList;
		this.armor = cArmor;
		this.actionChits = ac;
		this.cImg[0] = new ImageIcon(imgUn).getImage();
		this.cImg[1] = new ImageIcon(imgHid).getImage();
		this.friendly = f;
		this.unfriendly = u;
		this.neutral = n;
		this.ally = a;
		this.enemy = en;
		this.boardLocation = "None";
		this.hidden = 0;
		this.moves = 2; // /ADD SPECIAL ADVANTAGES!!!!!!!
		this.specialAdvantage = sa;
		this.mountainMove = 0;
		this.phases = 0;
		if (Cname == "Dwarf") {
			this.sunlight = 0;
		} else {
			this.sunlight = 1;
		}
		hiddenEnemies = null;
		this.activeWeapon = null;
		combatInfo = new CombatInfo(this.actionChits, this.armor, this.activeWeapon);
		itemsOwned.addAll(cArmor);
		itemsOwned.addAll(cWeaponList);
		this.extraFeature = false;
		blocked = false;

	}

	// GETTERS //
	public String getName() {
		return name;
	}

	public String getCounterName() {
		return counterName;
	}

	public char getWeight() {
		return weight;
	}
	public int getWeightE() {
		return weightE;
	}

	public String getStartingLocation() {
		return startingLocation;
	}

	public String[] getFriendly() {
		return friendly;
	}

	public String[] getUnFriendly() {
		return unfriendly;
	}

	public String[] getNeutral() {
		return neutral;
	}

	public String[] getAlly() {
		return ally;
	}

	public String[] getEnemy() {
		return enemy;
	}

	public ArrayList<Weapons> getweaponList() {
		return weaponList;
	}

	public ArrayList<Armor> getArmor() {
		return armor;
	}

	public ArrayList<ActionChit> getActionChits() {
		return actionChits;
	}

	public ArrayList<TreasureCard> getTreasures() {
		return lootedTreasures;
	}

	public ArrayList<Item> getItemsOwned() {
		return itemsOwned;
	}

	public ArrayList<Chit> getDiscoveredChits() {
		return discoveredChits;
	}
	public Image getImage() {
		return this.cImg[hidden];
	}

	// public Image getImage() {return this.cImg[1];}
	public Point getPLocation() {
		return this.pLocation;
	}

	public String getTileLocation() {
		return this.boardLocation;
	}

	public Clearing getClearingLocation() {
		return this.clearingLocation;
	}

	public int isHidden() {
		return this.hidden;
	}

	public int getStartingGold() {
		return startingGold;
	}

	public int getFame() {
		return startingFame;
	}

	public int getNotoriety() {
		return startingNotoriety;
	}

	public int getNeedGold() {
		return this.needGold;
	}

	public int getNeedFame() {
		return needFame;
	}

	public int getNeedNotority() {
		return needNotoriety;
	}

	public int getGoldPoint() {
		return GoldPoint;
	}

	public int getFamePoint() {
		return FamePoint;
	}

	public int getNotorityPoint() {
		return NotorietyPoint;
	}

	public int getGoldRecorded() {
		return GoldRecorded;
	}

	public int getFameRecorded() {
		return FameRecorded;
	}

	public int getNotorityRecordedt() {
		return NotorietyRecorded;
	}

	public int getMoves() {
		return this.moves;
	}

	public String getSpecialAdvantage(int i) {
		return this.specialAdvantage[i];
	}

	public String[] getAllSpecialAdvantages() {
		return this.specialAdvantage;
	}

	public int ifMountainMove() {
		return this.mountainMove;
	}

	public char getItemType() {
		return 'C';
	}

	public int getPhases() {
		return phases;
	}

	public int getSunlight() {
		return sunlight;
	}

	public boolean isSunlightPhase() {
		return (phases > 2) ? true : false;
	}

	public ArrayList<Clearing> getPathway() {
		return pathway;
	}

	public Clearing getClearingOnPathway(int i) {
		return pathway.get(i);
	}

	public int getClearingOnPathway(Clearing n) {
		int loc = 0;
		for (int i = 0; i < pathway.size(); i++) {
			if(pathway.get(i) != null) {
				if (pathway.get(i).getTileName() == n.getTileName()
						&& pathway.get(i).getClearingNum() == n.getClearingNum()) {
					loc = i;
					break;
				}
			}
		}
		return loc;
	}

	public ArrayList<Character> getHiddenEnemies() {
		return hiddenEnemies;
	}

	public CombatInfo getCombatInfo() {
		return combatInfo;
	}

	// SETTERS //
	public void setName(String name) {
		this.name = name;
	}

	public void setCounterName(String counterName) {
		this.counterName = counterName;
	}

	public void setWeight(char weight) {
		this.weight = weight;
	}

	public void setStartingLocation(String startingLocation) {
		this.startingLocation = startingLocation;
	}

	public void setFriendly(String friends) {
		if (this.friendly == null) {
			this.friendly = new String[1];
			this.friendly[0] = friends;
		} else {
			String[] temp = new String[friendly.length + 1];
			for (int i = 0; i < friendly.length; i++) {
				temp[i] = friendly[i];
			}
			temp[temp.length - 1] = friends;
			friendly = temp;
		}
	}

	public void setUnfriendly(String unfriendly) {
		if (this.unfriendly == null) {
			this.unfriendly = new String[1];
			this.unfriendly[0] = unfriendly;
		} else {
			String[] temp = new String[this.unfriendly.length + 1];
			for (int i = 0; i < this.unfriendly.length; i++) {
				temp[i] = this.unfriendly[i];
			}
			temp[temp.length - 1] = unfriendly;
			this.enemy = temp;
		}
	}

	public void setNeutral(String neutral) {
		if (this.neutral == null) {
			this.neutral = new String[1];
			this.neutral[0] = neutral;
		} else {
			String[] temp = new String[this.neutral.length + 1];
			for (int i = 0; i < this.neutral.length; i++) {
				temp[i] = this.neutral[i];
			}
			temp[temp.length - 1] = neutral;
			this.neutral = temp;
		}

	}

	public void setAlly(String ally) {
		if (this.ally == null) {
			this.ally = new String[1];
			this.ally[0] = ally;
		} else {
			String[] temp = new String[this.ally.length + 1];
			for (int i = 0; i < this.ally.length; i++) {
				temp[i] = this.ally[i];
			}
			temp[temp.length - 1] = ally;
			this.ally = temp;
		}
	}

	public void setEnemy(String enemy) {
		if (this.enemy == null) {
			this.enemy = new String[1];
			this.enemy[0] = enemy;
		} else {
			String[] temp = new String[this.enemy.length + 1];
			for (int i = 0; i < this.enemy.length; i++) {
				temp[i] = this.enemy[i];
			}
			temp[temp.length - 1] = enemy;
			this.enemy = temp;
		}
	}

	public void setweaponList(ArrayList<Weapons> weaponList) {
		this.weaponList = weaponList;
		this.itemsOwned.addAll(weaponList);
	}
	public void addWeapon(Weapons w) {
		this.weaponList.add(w);
		this.itemsOwned.add(w);
	}

	public void setArmor(ArrayList<Armor> armor) {
		this.armor = armor;
		this.itemsOwned.addAll(armor);
	}

	public void addArmor(Armor a) {
		this.armor.add(a);
		this.itemsOwned.add(a);
	}

	public void setActionChit(ArrayList<ActionChit> ac) {
		this.actionChits = ac;
	}

	public void setTreasurePile(ArrayList<TreasureCard> lt) {
		this.lootedTreasures = lt;
	}

	public void addTreasureToPile(TreasureCard lt) {
		this.lootedTreasures.add(lt);
		this.itemsOwned.add(lt);
	}

	public void setDiscoveredChits(ArrayList<Chit> chts) {
		this.discoveredChits = chts;
	}

	public void addDiscoveredChit(Chit cht) {
		this.discoveredChits.add(cht);
	}

	public void setPLocation(int x, int y) {
		this.pLocation.move(x, y);
	}

	public void setBoardLocation(String i) {
		this.boardLocation = i;
	}

	public void setClearingLocation(Clearing c) {
		this.clearingLocation = c;
	}

	public void hide() {
		this.hidden = 1;
	}

	public void unhide() {
		this.hidden = 0;
	}

	public void setGold(int gold) {
		this.startingGold = gold;
	}

	public void setFame(int fame) {
		this.startingFame = fame;
	}

	public void setNotoriety(int notoriety) {
		this.startingNotoriety = notoriety;
	}

	public void setNeedGold(int nGold) {
		this.needGold = nGold;
	}

	public void setNeedFame(int nFame) {
		this.needFame = nFame;
	}

	public void setNeedNotoriety(int nNotoriety) {
		this.needNotoriety = nNotoriety;
	}

	public void setGoldPoint(int pGold) {
		this.GoldPoint = pGold;
	}

	public void setFamePoint(int pFame) {
		this.FamePoint = pFame;
	}

	public void setNotorietyPoint(int pNotoriety) {
		this.NotorietyPoint = pNotoriety;
	}

	public void setGoldRecorded(int rGold) {
		this.GoldRecorded = rGold;
	}

	public void setFameRecorded(int rFame) {
		this.FameRecorded = rFame;
	}

	public void setNotorietyRecorded(int rNotoriety) {
		this.NotorietyRecorded = rNotoriety;
	}

	public void setMoves(int m) {
		this.moves = m;
	}

	public void setSpecialAdvantage(String[] sa) {
		this.specialAdvantage = sa;
	}

	public void setMountainMove() {
		this.mountainMove++;
	}

	public void removeMountainMove() {
		this.mountainMove--;
	}

	public void resetPhase() {
		this.phases = 0;
	}

	public void phasePlayed() {
		this.phases++;
		if(this.name.toLowerCase() != "dwarf") {
			if (phases == 2 && sunlight == 1) {
				this.moves = 4;
			}
		}
	}

	public void cavePlayed() {
		this.sunlight = 0;
	}

	public void resetSunlight() {
		this.sunlight = 1;
	}

	public void setPathways(ArrayList<Clearing> p) {
		this.pathway = p;
	}

	public void addPathway(int i, Clearing c) {
		this.pathway.get(i).setAdjacentClearing(c);
	}

	public void setHiddenEnemies(ArrayList<Character> h) {
		hiddenEnemies = h;
	}

	public void setCombatInfo(CombatInfo ci) {
		combatInfo = ci;
	}

	public Item removeItemOwned(int i) {
		Item m = itemsOwned.remove(i);
		if(m.getItemType() == 'A') {
			for(int j = 0; i < armor.size(); i++) {
				if(m.getName() == armor.get(j).getName()) {
					armor.remove(j);
					break;
				}
			}
		}else if(m.getItemType() == 'W') {
			for(int j = 0; i < weaponList.size(); i++) {
				if(m.getName() == weaponList.get(j).getName()) {
					weaponList.remove(j);
					break;
				}
			}
		}else if(m.getItemType() == 'T') {
			for(int j = 0; i < lootedTreasures.size(); i++) {
				if(m.getName() == lootedTreasures.get(j).getName()) {
					lootedTreasures.remove(j);
					break;
				}
			}
		}
		return m;
	}

	public void setActiveWeapon(Weapons w) { this.activeWeapon = w;}
	public void setWeightE(int w) { weightE = w;}
	public void setWeightE(char w) {
		if(w == 'L') {
			weightE = 1;
		} else if(w == 'M') {
			weightE = 2;
		} else if(w == 'H') {
			weightE = 3;
		} else if(w == 'T') {
			weightE = 4;
		}
	}

	public String removeAlly(String n) {
		String name="";
		int loc = -1;
		for(int i = 0; i < ally.length; i++) {
			if(n == ally[i]) {
				loc = i;
				break;
			}
		}
		String[] temp = new String[ally.length-1];
		for(int i = 0; i < loc; i++) {
			temp[i] = ally[i];
		}
		name = ally[loc];
		for(int i = loc+1; i < ally.length; i++) {
			temp[i] = ally[i];
		}
		ally = temp;
		return name;
	}
	public String removeFriendly(String n) {
		String name="";
		int loc = -1;
		for(int i = 0; i < friendly.length; i++) {
			if(n == friendly[i]) {
				loc = i;
				break;
			}
		}
		String[] temp = new String[friendly.length-1];
		for(int i = 0; i < loc; i++) {
			temp[i] = friendly[i];
		}
		name = friendly[loc];
		for(int i = loc+1; i < friendly.length; i++) {
			temp[i] = ally[i];
		}
		ally = temp;
		return name;
	}
	public String removeNeutral(String n) {
		String name="";
		int loc = -1;
		for(int i = 0; i < neutral.length; i++) {
			if(n == neutral[i]) {
				loc = i;
				break;
			}
		}
		String[] temp = new String[neutral.length-1];
		for(int i = 0; i < loc; i++) {
			temp[i] = neutral[i];
		}
		name = neutral[loc];
		for(int i = loc+1; i < neutral.length; i++) {
			temp[i] = neutral[i];
		}
		neutral = temp;
		return name;
	}
	public String removeUnFriendly(String n) {
		String name="";
		int loc = -1;
		for(int i = 0; i < unfriendly.length; i++) {
			if(n == ally[i]) {
				loc = i;
				break;
			}
		}
		String[] temp = new String[unfriendly.length-1];
		for(int i = 0; i < loc; i++) {
			temp[i] = unfriendly[i];
		}
		name = unfriendly[loc];
		for(int i = loc+1; i < unfriendly.length; i++) {
			temp[i] = unfriendly[i];
		}
		unfriendly = temp;
		return name;
	}
	public String removeEnemy(String n) {
		String name="";
		int loc = -1;
		for(int i = 0; i < enemy.length; i++) {
			if(n == enemy[i]) {
				loc = i;
				break;
			}
		}
		String[] temp = new String[enemy.length-1];
		for(int i = 0; i < loc; i++) {
			temp[i] = enemy[i];
		}
		name = enemy[loc];
		for(int i = loc+1; i < enemy.length; i++) {
			temp[i] = enemy[i];
		}
		enemy = temp;
		return name;
	}

	public boolean isFeatureUsed() { return this.extraFeature;}
	public void extraFeatureUsed() {this.extraFeature = true;}
	public void resetExtraFeature() {this.extraFeature = false;}
	
	public boolean isblocked(){return this.blocked;}
	public void block(){this.blocked = true;}
	public void unBlock(){this.blocked = true;}


	// SPECIAL ADVANTAGE
	public void doSpecialAdvantage(String sa) {

	}

}
