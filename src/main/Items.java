package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;

public class Items {
	public static Armor SUITOFARMOR = new Armor("SUIT OF ARMOR", 'H', new int[] {17, 2}, "./images/armors/suitofarmor.png", "./images/armors/suitofarmor.png");
	public static Armor BREASTPLATE = new Armor("BREASTPLATE", 'M', new int[] {9, 6}, "./images/armors/breastplate.png", "./images/armors/breastplate.png");
	public static Armor HELMET = new Armor("HELMET", 'M', new int[] {5, 3}, "./images/armors/helmet.png", "./images/armors/helmet.png");
	public static Armor SHIELD = new Armor("SHIELD", 'M', new int[] {7, 5}, "./images/armors/shield.png", "./images/armors/shield.png");
	
//(String treasureName, String section, boolean greatTreasure, String chit, String fameReward, 
	//int fame, int notoriety, int gold) 
	public static TreasureCard CloakOfMist = new TreasureCard("Cloak of Mist", 2, true, true, null, null, 0, 2, 4, "./images/treasures/tcard.gif");
	public static TreasureCard MagicSpectacles = new TreasureCard("Magic Spectacles", 2, false, true, null, null, 0, 2, 6, "./images/treasures/tcard.gif");
	public static TreasureCard DeftGloves = new TreasureCard("Deft Gloves", 5, true, true, "Fight-L-2", "Order", 0, 6, 10, "./images/treasures/tcard.gif");
	public static TreasureCard HandyGloves = new TreasureCard("Handy Gloves", 9, true, true, "Fight-M-3", null, 1, 2, 6, "./images/treasures/tcard.gif");
	public static TreasureCard ShoesOfStealth = new TreasureCard("Shoes of Stealth", 5, true, true, "Move-L-3", null, 0, 2, 7, "./images/treasures/tcard.gif");
	public static TreasureCard LeagueBoots = new TreasureCard("7-League Boots", 2, true, true, "Move-T-5", null, 0, 2, 5, "./images/treasures/tcard.gif");

	public static ArrayList<TreasureCard> treasures  = new ArrayList<TreasureCard>();
	public static ArrayList<Visitor> visitors = new ArrayList<Visitor>();
	public static ArrayList<Weapons> weapons = new ArrayList<Weapons>();
	public static ArrayList<Armor> armors = new ArrayList<Armor>();
	NativesI nativeGroups = new NativesI();
	
	
	
	
	public Items() {
		initTreasureCards();
		initVisitors();
		initWeapons();
		initArmors();
	}
	
	public ArrayList<NativeGroups> getNativeGroups() { 
		return nativeGroups.nGroups;
		}
	
	public void initTreasureCards() {
		TreasureCard treasure1 = new TreasureCard("GOLD", 2, false, false, null, null, -5, 2, 1, "./images/treasures/treasure-small.png");
		TreasureCard treasure2 = new TreasureCard("GOLD", 2, false, false, null, null, -1, 1, 5, "./images/treasures/treasure-small.png");
		TreasureCard treasure3 = new TreasureCard("GOLD", 2, false, false, null, null, 0, 5, 10, "./images/treasures/treasure-small.png");
		TreasureCard treasure4 = new TreasureCard("GOLD", 2, false, false, null, null, 3, 3, 15, "./images/treasures/treasure-small.png");
		TreasureCard treasure5 = new TreasureCard("GOLD", 2, false, false, null, null, 7, 6, 20, "./images/treasures/treasure-small.png");
		TreasureCard treasure6 = new TreasureCard("GOLD", 2, false, false, null, null, 10, 9, 25, "./images/treasures/treasure-small.png");
		TreasureCard treasure7 = new TreasureCard("GOLD", 2, false, false, null, null, 14, 10, 30, "./images/treasures/treasure-small.png");
		TreasureCard treasure8 = new TreasureCard("GOLD", 2, false, false, null, null, 17, 8, 35, "./images/treasures/treasure-small.png");
		TreasureCard treasure9 = new TreasureCard("GOLD", 2, false, true, null, null, 21, 15, 40, "./images/treasures/treasure-large.png");
		TreasureCard treasure10 = new TreasureCard("GOLD", 2, false, true, null, null, 25, 12, 45, "./images/treasures/treasure-large.png");
		TreasureCard treasure11 = new TreasureCard("GOLD", 2, false, true, null, null, 30, 20, 50, "./images/treasures/treasure-large.png");
		treasures.add(treasure1);
		treasures.add(treasure2);
		treasures.add(treasure3);
		treasures.add(treasure4);
		treasures.add(treasure5);
		treasures.add(treasure6);
		treasures.add(treasure7);
		treasures.add(treasure8);
		treasures.add(treasure9);
		treasures.add(treasure10);
		treasures.add(treasure11);
	}
	
	
	public void initVisitors() {
		Visitor scholar = new Visitor("SCHOLAR");
		Visitor shaman = new Visitor("SHAMAN");
		Visitor warlock = new Visitor("WARLOCK");
		Visitor crone = new Visitor("CHRONE");
		visitors.add(scholar);
		visitors.add(warlock);
		visitors.add(shaman);
		visitors.add(crone);
	}
	
	public void initWeapons() {
		weapons.add(WeaponInst.AXE);
		weapons.add(WeaponInst.BANESWORD);
		weapons.add(WeaponInst.BROADSWORD);
		weapons.add(WeaponInst.CROSSBOW);
		weapons.add(WeaponInst.DEVILSWORD);
		weapons.add(WeaponInst.GREATAXE);
		weapons.add(WeaponInst.GREATSWORD);
		weapons.add(WeaponInst.LIGHTBOW);
		weapons.add(WeaponInst.LIVINGSWORD);
		weapons.add(WeaponInst.MACE);
		weapons.add(WeaponInst.MEDIUMBOW);
		weapons.add(WeaponInst.MORNINGSTAR);
		weapons.add(WeaponInst.SHORTSWORD);
		weapons.add(WeaponInst.SPEAR);
		weapons.add(WeaponInst.STAFF);
		weapons.add(WeaponInst.THRUSTINGSWORD);
		weapons.add(WeaponInst.TRUESTEELSWORD);
	}
	
	public void initArmors() {
		armors.add(BREASTPLATE);
		armors.add(HELMET);
		armors.add(SHIELD);
		armors.add(SUITOFARMOR);
	}
	public void initNativeGroups() {
		
	}
	
}
