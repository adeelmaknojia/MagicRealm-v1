package main;

import java.util.ArrayList;

public class NativesI {
	private Natives RAIDER;
	private Natives SSWORDSMAN;
	private Natives PIKEMAN;
	private Natives GSWORDSMAN;
	private Natives CROSSBOWMAN;
	private Natives ARCHER;
	private Natives LANCER;
	private Natives ASSASSIN;
	private Natives GAXEMAN;
	private Natives KNIGHT;
	public static ArrayList<NativeGroups> nGroups = new ArrayList<NativeGroups>();
	public static NativeGroups BASHKARS;
	public static NativeGroups COMPANY;
	public static NativeGroups GUARD;
	public static NativeGroups LANCERS;
	public static NativeGroups ORDER;
	public static NativeGroups PATROL;
	public static NativeGroups ROGUES;
	public static NativeGroups SOLDIERS;
	public static NativeGroups WOODFOLK;
	
	public NativesI() {
		RAIDER = new Natives("Raider", WeaponInst.SHORTSWORD, "Light", false, 2, new int[] {4, 2}, 'L', 'L', "./images/natives/raider_b.gif");
		SSWORDSMAN = new Natives("Short Swordsman", WeaponInst.SHORTSWORD, "Medium", true, 2, new int[] {3, 2}, 'M', 'M', "./images/natives/short_swordsman_c.gif");
		PIKEMAN = new Natives("Pikeman", WeaponInst.SPEAR, "Medium", true, 2, new int[] {3, 2}, 'M', 'M', "./images/natives/pikeman_c.gif");
		GSWORDSMAN = new Natives("Great Swordsman", WeaponInst.GREATSWORD, "Heavy", true, 4, new int[] {6, 4}, 'H', 'H', "./images/natives/great_swordsman_c.gif");
		CROSSBOWMAN = new Natives("Crossbowman", WeaponInst.CROSSBOW, "Medium", true, 2, new int[] {4, 2}, 'M', 'M', "./images/natives/crossbowman_c.gif");
		ARCHER = new Natives("Archer", WeaponInst.LIGHTBOW, "Medium", false, 2, new int[] {4, 2}, 'M', 'M', "./images/natives/archer_r.gif");
		LANCER = new Natives("Lancer", WeaponInst.SPEAR, "Light", false, 2, new int[] {4, 2}, 'L', 'L', "./images/natives/lancer_l.gif");
		KNIGHT = new Natives("Knight", WeaponInst.SPEAR, "Light", false, 2, new int[] {4, 2}, 'L', 'L', "./images/natives/knight_o.gif");
		ASSASSIN = new Natives("Assassin", WeaponInst.SPEAR, "Light", false, 2, new int[] {4, 2}, 'L', 'L', "./images/natives/assassin_m.gif");
		GAXEMAN = new Natives("Great Axeman", WeaponInst.SPEAR, "Light", false, 2, new int[] {4, 2}, 'L', 'L', "./images/natives/great_axeman_r.gif");
		initBashkars();
		initCompany();
		initGuard();
		initLancers();
		initOrder();
		initPatrol();
		initRogues();
		initSoldiers();
		initWoodfolk();
		
	}
	
	public void initBashkars() {
		
		BASHKARS = new NativeGroups("Bashkars", 16);
		NativeMember BHQ = new NativeMember("BHQ", "BASHKARS", RAIDER, "L4", 2, "M4", 3);
		NativeMember B1 = new NativeMember("B1", "BASHKARS", RAIDER, "M3", 3, "L3", 3);
		NativeMember B2 = new NativeMember("B2", "BASHKARS", RAIDER, "M4", 2, "L3", 3);
		NativeMember B3 = new NativeMember("B3", "BASHKARS", RAIDER, "L4", 2, "M3", 4);
		NativeMember B4 = new NativeMember("B4", "BASHKARS", RAIDER, "M5", 2, "L2", 4);
		NativeMember B5 = new NativeMember("B5", "BASHKARS", RAIDER, "L2", 4, "L2", 4);
		BASHKARS.addNative(BHQ);
		BASHKARS.addNative(B1);
		BASHKARS.addNative(B2);
		BASHKARS.addNative(B3);
		BASHKARS.addNative(B4);
		BASHKARS.addNative(B5);
		nGroups.add(BASHKARS);
	}
	public void initCompany() {
		
		COMPANY = new NativeGroups("Company", 16);
		NativeMember CHQ = new NativeMember("CHQ", "COMPANY", SSWORDSMAN, "M3", 5, "L4", 3);
		NativeMember C1 = new NativeMember("C1", "COMPANY", SSWORDSMAN, "M3", 4, "M4", 3);
		NativeMember C2 = new NativeMember("C2", "COMPANY", PIKEMAN, "H6", 4, "M4", 5);
		NativeMember C3 = new NativeMember("C3", "COMPANY", PIKEMAN, "H6", 4, "M4", 5);
		NativeMember C4 = new NativeMember("C4", "COMPANY", PIKEMAN, "MH6", 4, "M4", 5);
		NativeMember C5 = new NativeMember("C5", "COMPANY", GSWORDSMAN, "M3", 6, "H5", 5);
		NativeMember C6 = new NativeMember("C6", "COMPANY", CROSSBOWMAN, "M3", 5, "H1", 4);
		COMPANY.addNative(CHQ);
		COMPANY.addNative(C1);
		COMPANY.addNative(C2);
		COMPANY.addNative(C3);
		COMPANY.addNative(C4);
		COMPANY.addNative(C5);
		COMPANY.addNative(C6);
		nGroups.add(COMPANY);
		
	}
	
	public void initWoodfolk() {
		
		WOODFOLK = new NativeGroups("WoodFolk", 6);
		NativeMember WHQ = new NativeMember("WHQ", "WOODFOLK", ARCHER, "", 2, "M1", 4);
		NativeMember W1 = new NativeMember("W1", "WOODFOLK", ARCHER, "", 2, "L1", 4);
		NativeMember W2 = new NativeMember("W2", "WOODFOLK", ARCHER, "", 2, "L1", 4);
		WOODFOLK.addNative(WHQ);
		WOODFOLK.addNative(W1);
		WOODFOLK.addNative(W2);
		nGroups.add(WOODFOLK);
		
	}
	
	public void initPatrol() {
		
		PATROL = new NativeGroups("Patrol", 6);
		NativeMember PHQ = new NativeMember("PHQ", "PATROL", SSWORDSMAN, "M4", 3, "L2", 5);
		NativeMember P1 = new NativeMember("P1", "PATROL", SSWORDSMAN, "L4", 3, "M4", 4);
		NativeMember P2 = new NativeMember("P2", "PATROL", SSWORDSMAN, "M5", 3, "L4", 3);
		PATROL.addNative(PHQ);
		PATROL.addNative(P1);
		PATROL.addNative(P2);
		nGroups.add(PATROL);
		
	}
	
	
	public void initLancers() {
		
		LANCERS = new NativeGroups("Lancers", 6);
		NativeMember LHQ = new NativeMember("LHQ", "LANCERS", LANCER, "H5", 3, "M3", 4);
		NativeMember L1 = new NativeMember("L1", "LANCERS", LANCER, "H6", 2, "M3", 4);
		NativeMember L2 = new NativeMember("L2", "LANCERS", LANCER, "M4", 3, "H4", 4);
		NativeMember L3 = new NativeMember("L3", "LANCERS", LANCER, "H4", 5, "M4", 4);
		LANCERS.addNative(LHQ);
		LANCERS.addNative(L1);
		LANCERS.addNative(L2);
		LANCERS.addNative(L3);
		nGroups.add(LANCERS);
		
	}
	
	public void initOrder() {
		
		ORDER = new NativeGroups("Order", 0);
		NativeMember OHQ = new NativeMember("OHQ", "ORDER", KNIGHT, "H6", 4, "T4", 4);
		NativeMember O1 = new NativeMember("O1", "ORDER", KNIGHT, "H4", 6, "T5", 4);
		NativeMember O2 = new NativeMember("O2", "ORDER", KNIGHT, "H5", 5, "T5", 4);
		NativeMember O3 = new NativeMember("O3", "ORDER", KNIGHT, "H4", 6, "T5", 4);
		ORDER.addNative(OHQ);
		ORDER.addNative(O1);
		ORDER.addNative(O2);
		ORDER.addNative(O3);
		nGroups.add(ORDER);
		
	}
	
	public void initSoldiers() {
		
		SOLDIERS = new NativeGroups("Soldiers", 10);
		NativeMember SHQ = new NativeMember("SHQ", "SOLDIERS", GSWORDSMAN, "T4", 6, "H5", 5);
		NativeMember S1 = new NativeMember("S1", "SOLDIERS", PIKEMAN, "H6", 4, "M4", 5);
		NativeMember S2 = new NativeMember("S2", "SOLDIERS", PIKEMAN, "H6", 4, "M4", 5);
		NativeMember S3 = new NativeMember("S3", "SOLDIERS", CROSSBOWMAN, "H6", 5, "H1", 4);
		SOLDIERS.addNative(SHQ);
		SOLDIERS.addNative(S1);
		SOLDIERS.addNative(S2);
		SOLDIERS.addNative(S3);
		nGroups.add(SOLDIERS);
		
	}
	
	public void initRogues() {//NOT DONE
		
		ROGUES = new NativeGroups("Rogues", 0);
		NativeMember RHQ = new NativeMember("RHQ", "ROGUES", ASSASSIN, "M3", 6, "H5", 5);
		NativeMember R1 = new NativeMember("R1", "ROGUES", GAXEMAN, "H6", 4, "M4", 5);
		NativeMember R2 = new NativeMember("R2", "ROGUES", GAXEMAN, "H6", 4, "M4", 5);
		NativeMember R3 = new NativeMember("R3", "ROGUES", SSWORDSMAN, "H6", 5, "H1", 4);
		NativeMember R4 = new NativeMember("R4", "ROGUES", ARCHER, "H6", 4, "M4", 5);
		NativeMember R5 = new NativeMember("R5", "ROGUES", ASSASSIN, "H6", 4, "M4", 5);
		NativeMember R6 = new NativeMember("R6", "ROGUES", SSWORDSMAN, "H6", 4, "M4", 5);
		NativeMember R7 = new NativeMember("R7", "ROGUES", SSWORDSMAN, "H6", 4, "M4", 5);
		ROGUES.addNative(RHQ);
		ROGUES.addNative(R1);
		ROGUES.addNative(R2);
		ROGUES.addNative(R3);
		ROGUES.addNative(R4);
		ROGUES.addNative(R5);
		ROGUES.addNative(R6);
		ROGUES.addNative(R7);
		nGroups.add(ROGUES);
		
	}
	
	public void initGuard() {
		
		GUARD = new NativeGroups("Guard", 12);
		NativeMember GHQ = new NativeMember("GHQ", "GUARD", GSWORDSMAN, "H5", 5, "T4", 6);
		NativeMember G1 = new NativeMember("G1", "GUARD", GSWORDSMAN, "H5", 5, "T4", 6);
		NativeMember G2 = new NativeMember("G2", "GUARD", GSWORDSMAN, "H5", 5, "T4", 6);
		GUARD.addNative(GHQ);
		GUARD.addNative(G1);
		GUARD.addNative(G2);
		nGroups.add(GUARD);
		
	}
	
	public NativeGroups getBashkars() { return BASHKARS; }
	public NativeGroups getCompany() { return COMPANY; }
	public NativeGroups getGuard() { return GUARD; }
	public NativeGroups getLancers() { return LANCERS; }
	public NativeGroups getOrder() { return ORDER; }
	public NativeGroups getPatrol() { return PATROL; }
	public NativeGroups getRogues() { return ROGUES; }
	public NativeGroups getSoldiers() { return SOLDIERS; }
	public NativeGroups getWoodfolk() { return WOODFOLK; }

}
