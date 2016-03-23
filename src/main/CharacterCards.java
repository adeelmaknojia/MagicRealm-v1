package main;
import java.util.ArrayList;

public class CharacterCards{

	private static int totalCharacters = 8;
	static Character cList[] = new Character [totalCharacters];
	static Character[] players = new Character[totalCharacters];
	private ArrayList<Weapons> w0 = new ArrayList<Weapons>();
	private ArrayList<Weapons> w1 = new ArrayList<Weapons>();
	private ArrayList<Weapons> w2 = new ArrayList<Weapons>();
	private ArrayList<Weapons> w3 = new ArrayList<Weapons>();
	private ArrayList<Weapons> w4 = new ArrayList<Weapons>();
	private ArrayList<Weapons> w5 = new ArrayList<Weapons>();
	private ArrayList<Weapons> w6 = new ArrayList<Weapons>();
	private ArrayList<Weapons> w7 = new ArrayList<Weapons>();
	private ArrayList<Armor> a0 = new ArrayList<Armor>();
	private ArrayList<Armor> a1 = new ArrayList<Armor>();
	private ArrayList<Armor> a2 = new ArrayList<Armor>();
	private ArrayList<Armor> a3 = new ArrayList<Armor>();
	private ArrayList<Armor> a4 = new ArrayList<Armor>();
	private ArrayList<Armor> a5 = new ArrayList<Armor>();
	private ArrayList<Armor> a6 = new ArrayList<Armor>();
	private ArrayList<Armor> a7 = new ArrayList<Armor>();
	private ArrayList<ActionChit> ac0 = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> ac1 = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> ac2 = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> ac3 = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> ac4 = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> ac5 = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> ac6 = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> ac7 = new ArrayList<ActionChit>();
	

	// Constructor //
	public CharacterCards() {

		// Amazon
		w0.add(WeaponInst.SHORTSWORD);
		a0.add(Items.HELMET);
		a0.add(Items.BREASTPLATE);
		a0.add(Items.SHIELD);
		///Action chits: FIGHT M4* FIGHT M3** MOVE M3*
		ac0.add(new ActionChit("FIGHT M4*", "FIGHT", 'M', 4, 1,  "./images/action-chits/Fm4s.png", "./images/action-chits/Fm4s-r.png"));
		ac0.add(new ActionChit("FIGHT M3**", "FIGHT", 'M', 3, 2, "./images/action-chits/Fm3ss.png", "./images/action-chits/Fm3ss-r.png"));
		ac0.add(new ActionChit("MOVE M3*", "MOVE", 'M', 3, 1, "./images/action-chits/Mm3s.png", "./images/action-chits/Mm3s-r.png"));
		
		//CAPTION
		w1.add(WeaponInst.SHORTSWORD);
		a1.add(Items.HELMET);
		a1.add(Items.BREASTPLATE);
		a1.add(Items.SHIELD);
		//Action chits: MOVE M4* FIGHT H6* FIGHT M4*
		ac1.add(new ActionChit("FIGHT H6", "FIGHT", 'M', 6, 0, "./images/action-chits/Fh6.png", "./images/action-chits/Fh6-r.png"));
		ac1.add(new ActionChit("FIGHT M4*", "FIGHT", 'M', 4, 1, "./images/action-chits/Fm4s.png", "./images/action-chits/Fm4s-r.png"));
		ac1.add(new ActionChit("MOVE M4*", "MOVE", 'M', 4, 1, "./images/action-chits/Mm4s.png", "./images/action-chits/Mm4s-r.png"));
		
		//SWORDSMAN
		w2.add(WeaponInst.THRUSTINGSWORD);
		//ACTION CHITS: FIGHT L4 FIGHT M5 FIGHT L2**
		ac2.add(new ActionChit("FIGHT L4", "FIGHT", 'L', 4, 0, "./images/action-chits/Fl4.png", "./images/action-chits/Fl4-r.png"));
		ac2.add(new ActionChit("FIGHT M5", "FIGHT", 'M', 5, 0, "./images/action-chits/Fm5.png", "./images/action-chits/Fm5-r.png"));
		ac2.add(new ActionChit("FIGHT L2**", "FIGHT", 'L', 2, 2, "./images/action-chits/Fl2ss.png", "./images/action-chits/Fl2ss-r.png"));
		
		//DWARF
		w3.add(WeaponInst.GREATAXE);
		a3.add(Items.HELMET);
		///Action chits: FIGHT T5** FIGHT T5* MOVE T5*
		ac3.add(new ActionChit("FIGHT T5**", "FIGHT", 'T', 5, 2, "./images/action-chits/Ft5ss.png", "./images/action-chits/Ft5ss-r.png"));
		ac3.add(new ActionChit("FIGHT T5**", "FIGHT", 'T', 5, 2, "./images/action-chits/Ft5ss.png", "./images/action-chits/Ft5ss-r.png"));
		ac3.add(new ActionChit("MOVE T5**", "MOVE", 'T', 5, 2, "./images/action-chits/Mt5ss.png", "./images/action-chits/Mt5ss-r.png"));
		
		//ELF
		w4.add(WeaponInst.LIGHTBOW);
		//Action chits: FIGHT M4 FIGHT M3* MOVE M4
		ac4.add(new ActionChit("FIGHT M4", "FIGHT", 'M', 4, 0, "./images/action-chits/Fm4.png", "./images/action-chits/Fm4-r.png"));
		ac4.add(new ActionChit("FIGHT M3*", "FIGHT", 'M', 3, 1, "./images/action-chits/Fm3s.png", "./images/action-chits/Fm3s-r.png"));
		ac4.add(new ActionChit("MOVE M4", "MOVE", 'M', 4, 0, "./images/action-chits/Mm4.png", "./images/action-chits/Mm4-r.png"));
		
		//BLACK KNIGHT
		w5.add(WeaponInst.MACE);
		a5.add(Items.SUITOFARMOR);
		a5.add(Items.SHIELD);
		//Action chits: FIGHT M3** FIGHT H4** MOVE H4**
		ac5.add(new ActionChit("FIGHT M3**", "FIGHT", 'M', 3, 2, "./images/action-chits/Fm3ss.png", "./images/action-chits/Fm3ss-r.png"));
		ac5.add(new ActionChit("FIGHT H4**", "FIGHT", 'M', 4, 2, "./images/action-chits/Fh4ss.png", "./images/action-chits/Fh4ss-r.png"));
		ac5.add(new ActionChit("MOVE M4**", "MOVE", 'M', 4, 2, "./images/action-chits/Mm4ss.png", "./images/action-chits/Mm4ss-r.png"));
		
		//WHITE KNIGHT
		w6.add(WeaponInst.GREATSWORD);
		a6.add(Items.SUITOFARMOR);
		//Action chits: FIGHT T4** FIGHT T5* MOVE T6*
		ac6.add(new ActionChit("FIGHT T4**", "FIGHT", 'T', 4, 2, "./images/action-chits/Ft4ss.png", "./images/action-chits/Ft4ss-r.png"));
		ac6.add(new ActionChit("FIGHT T5*", "FIGHT", 'T', 5, 1, "./images/action-chits/Ft5s.png", "./images/action-chits/Ft5s-r.png"));
		ac6.add(new ActionChit("MOVE T6*", "MOVE", 'T', 6, 1, "./images/action-chits/Mt6s.png", "./images/action-chits/Mt6s-r.png"));
		
		//BERSERKER
		w7.add(WeaponInst.GREATAXE);
		a7.add(Items.HELMET);
		//Action chits: Berserk FIGHT T4** FIGHT T5* MOVE T4**
		ac7.add(new ActionChit("FIGHT T4**", "FIGHT", 'T', 4, 2, "./images/action-chits/Ft4ss.png", "./images/action-chits/Ft4ss-r.png"));
		ac7.add(new ActionChit("FIGHT T5*", "FIGHT", 'T', 5, 1, "./images/action-chits/Ft5s.png", "./images/action-chits/Ft5s-r.png"));
		ac7.add(new ActionChit("BERSERK T4**", "BERSERK", 'T', 4, 2, "./images/action-chits/Bt4ss.png", "./images/action-chits/Bt4ss-r.png"));
		




		// Generating Characters
		
		Character c0 = new Character("Amazon","Sword and Shield",'M',w0,a0,ac0,"./images/characters/amazon-g.png","./images/characters/amazon-r.png", new String[] {"AIM", "STAMINA"}, 
				new String[] {"LANCERS", "PATROL", "SHAMAN"}, new String[] {"COMPANY", "BASHKARS"}, null, null, null);
		Character c1 = new Character("Captain","Chevron",'M',w1,a1,ac1,"./images/characters/captain-g.png","./images/characters/captain-r.png", new String[] {"AIM", "REPUTATION"},
				new String[] {"PATROL", "SOLDIERS", "GUARD", "SCHOLAR"}, new String[] {"WOODFOLK"}, null, null, new String[] {"BASHKARS"});
		Character c2 = new Character("Swordsman","Wolf's Hook",'L',w2,a2,ac2,"./images/characters/swordsman-g.png","./images/characters/swordsman-r.png", new String[] {"BARTER", "CLEVER"},
				new String[] {"ROGUES", "COMPANY", "WARLOCK"}, null, null, null, new String[] {"PATROL"});
		Character c3 = new Character("Dwarf","Opposition",'H',w3,a3,ac3,"./images/characters/dwarf-g.png","./images/characters/dwarf-r.png", new String[] {"SHORT LEGS", "CAVE KNOWLEDGE"},
				new String[] {"LANCERS"}, new String[] {"ORDER", "SHAMAN"}, null, new String[] {"BASHKARS"}, new String[] {"WOODFOLK"});
		Character c4 = new Character("Elf","Sextile",'L',w4,a4,ac4,"./images/characters/elf-g.png","./images/characters/elf-r.png", new String[] {"ELUSIVENESS", "ARCHER"},
				new String[] {"BASHKARS"}, new String[] {"ORDER", "SCHOLAR"}, null, new String[] {"WOODFOLK"}, new String[] {"LANCERS"});
		Character c5 = new Character("Black Knight","Mars",'M',w5,a5,ac5,"./images/characters/black_knight-g.png","./images/characters/black_knight-r.png", new String[] {"AIM", "FEAR"},
				new String[] {"SOLDIERS", "CRONE"}, new String[] {"LANCERS"}, null, new String[] {"COMPANY"}, new String[] {"GUARD"});
		Character c6 = new Character("White Knight","Cross Pommee",'H',w6,a6,ac6,"./images/characters/white_knight-g.png","./images/characters/white_knight-r.png", new String[] {"HEALTH", "HONOR"},
				new String[] {"LANCERS"}, new String[] {"BASHKARS", "CRONE"}, null, new String[] {"ORDER"}, new String[] {"COMPANY"});
		Character c7 = new Character("Berserker","Letophoro",'H',w7,a7,ac7,"./images/characters/berserker-g.png","./images/characters/berserker-r.png", new String[] {"ROBUST", "BERSERK"},
				new String[] {"LANCERS", "ROGUES", "SHAMAN"}, new String[] {"PATROL", "GUARD"}, null, null, null);

		// Creating list of characters
		cList[0] = c0;
		cList[1] = c1;
		cList[2] = c2;
		cList[3] = c3;
		cList[4] = c4;
		cList[5] = c5;
		cList[6] = c6;
		cList[7] = c7;

	}


	// Getter for clist
	public Character[] getClist(){
		return this.cList;
	}

	// Function to print characters information
	static void PrintCharacters(){
		for(int i=0; i < totalCharacters; i++){
			System.out.println("Name: " + players[i].getName() + "| Counter: " + players[i].getCounterName() + 
					"| Weight: " + players[i].getWeight());

		}
	}

}