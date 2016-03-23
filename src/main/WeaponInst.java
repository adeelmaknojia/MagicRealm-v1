package main;

import java.io.Serializable;

public class WeaponInst implements Serializable{
	//private ArrayList<Weapons> weapons;
	public static Weapons MEDIUMBOW = new Weapons("Medium Bow", "missile", 16, new int[] {0, 2}, new int[] {0, 1}, 'M', 8, "./images/weapons/medium_bow.png");
	public static Weapons LIGHTBOW = new Weapons("Light Bow", "missile", 14, new int[] {0,2}, new int[] {0, 1}, 'L', 6, "./images/weapons/light_bow.png");
	public static Weapons CROSSBOW = new Weapons("Cross Bow", "missile", 12, new int[] {0,1}, new int[] {0, 1}, 'H', 10, "./images/weapons/crossbow.png");
	public static Weapons SPEAR = new Weapons("Spear", "striking", 10, new int[] {0,1}, new int[] {6, 0}, 'M', 6, "./images/weapons/spear.png");
	public static Weapons STAFF = new Weapons("Staff", "striking", 8, new int[] {0,0}, new int[] {0, 0}, 'L', 1, "./images/weapons/staff.png");
	public static Weapons GREATSWORD = new Weapons("Great Sword", "striking", 8, new int[] {1,1}, new int[] {6, 0}, 'H', 10, "./images/weapons/great_sword.png");
	public static Weapons BANESWORD = new Weapons("Bane Sword", "striking", 8, new int[] {1,1}, new int[] {8, 2}, 'T', 20, "./images/weapons/bane_sword.png");
	public static Weapons BROADSWORD = new Weapons("Broad Sword", "striking", 7, new int[] {1,1}, new int[] {5, 0}, 'M', 8, "./images/weapons/broadsword.png");
	public static Weapons DEVILSWORD = new Weapons("Devil Sword", "striking", 7, new int[] {1,1}, new int[] {4, 3}, 'H', 20, "./images/weapons/devil_sword.png");
	public static Weapons TRUESTEELSWORD = new Weapons("Truesteel Sword", "striking", 7, new int[] {2,2}, new int[] {0, 0}, 'M', 25, "./images/weapons/truesteel_sword.png");
	public static Weapons MORNINGSTAR = new Weapons("Morning Star", "striking", 6, new int[] {0,0}, new int[] {0, 3}, 'H', 8, "./images/weapons/morning_star.png");
	public static Weapons GREATAXE = new Weapons("Great Axe", "striking", 5, new int[] {1,1}, new int[] {0, 4}, 'H', 8, "./images/weapons/great_axe.png");
	public static Weapons THRUSTINGSWORD = new Weapons("Thrusting Sword", "striking", 4, new int[] {1,1}, new int[] {4, 0}, 'L', 6, "./images/weapons/thrusting_sword.png");
	public static Weapons LIVINGSWORD = new Weapons("Living Sword", "striking", 4, new int[] {1,1}, new int[] {3, 2}, 'L', 25, "./images/weapons/living_sword.png");
	public static Weapons SHORTSWORD = new Weapons("Short Sword", "striking", 3, new int[] {1,1}, new int[] {0, 0}, 'L', 4, "./images/weapons/short_sword.png");
	public static Weapons AXE = new Weapons("Axe", "striking", 2, new int[] {1,1}, new int[] {5, 0}, 'M', 4, "./images/weapons/axe.png");
	public static Weapons MACE = new Weapons("Mace", "striking", 1, new int[] {0,0}, new int[] {0, 3}, 'M', 6, "./images/weapons/mace.png");
	
	
	public WeaponInst() {
		
	}
	/*
	private void init()
	{
		//public Weapons (boolean Walerted, String Wname, String Wattack, int Wlength, 
		//int Wsharpness, int Wtime, char Wweight){
		Weapons mediumBow = new Weapons("Medium Bow", "missile", 16, 2, new int[] {0, 1}, 'M', 8);
		Weapons lightBow = new Weapons("Light Bow", "missile", 14, 2, new int[] {0, 1}, 'L', 6);
		Weapons crossBow = new Weapons("Cross Bow", "missile", 12, 1, new int[] {0, 1}, 'H', 10);
		Weapons spear = new Weapons("Spear", "striking", 10, 1, new int[] {6, 0}, 'M', 6);
		Weapons staff = new Weapons("Staff", "striking", 8, 1, new int[] {0, 0}, 'L', 1);
		Weapons greatSword = new Weapons("Great Sword", "striking", 8, 1, new int[] {6, 0}, 'H', 10);
		Weapons baneSword = new Weapons("Bane Sword", "striking", 8, 1, new int[] {8, 2}, 'T', 20);
		Weapons broadsword = new Weapons("Broad Sword", "striking", 7, 1, new int[] {5, 0}, 'M', 8);
		Weapons devilSword = new Weapons("Devil Sword", "striking", 7, 1, new int[] {4, 3}, 'H', 20);
		Weapons truesteekSword = new Weapons("Treuesteel Sword", "striking", 7, 2, new int[] {0, 0}, 'M', 25);
		Weapons morningStar = new Weapons("Morning Star", "striking", 6, 0, new int[] {0, 3}, 'H', 8);
		Weapons greatAxe = new Weapons("Great Axe", "striking", 5, 1, new int[] {0, 4}, 'H', 8);
		Weapons thrustingSword = new Weapons("Broad Sword", "striking", 4, 1, new int[] {4, 0}, 'L', 6);
		Weapons livingSword = new Weapons("Broad Sword", "striking", 4, 1, new int[] {3, 2}, 'L', 25);
		Weapons shortSword = new Weapons("Broad Sword", "striking", 3, 1, new int[] {0, 0}, 'L', 4);
		Weapons axe = new Weapons("Broad Sword", "striking", 2, 1, new int[] {5, 0}, 'M', 4);
		Weapons mace = new Weapons("Broad Sword", "striking", 1, 0, new int[] {0, 3}, 'M', 6);
		weapons.add(mediumBow);
		weapons.add(lightBow);
		weapons.add(crossBow);
		weapons.add(spear);
		weapons.add(staff);
		weapons.add(greatSword);
		weapons.add(baneSword);
		weapons.add(broadsword);
		weapons.add(devilSword);	
	}
	*/
}
