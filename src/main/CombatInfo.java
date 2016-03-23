package main;

import java.util.ArrayList;

public class CombatInfo {

	//MY COMBAT INFO
	private ArrayList<ActionChit> actionChits = new ArrayList<ActionChit>();
	private ArrayList<ActionChit> usedChits = new ArrayList<ActionChit>();
	private ActionChit[] roundChits = new ActionChit[3];
	private Weapons activeWeapon;
	private ActionChit dodge;
	private ActionChit duck;
	private ActionChit charge;
	private ActionChit smash;
	private ActionChit swing;
	private ActionChit thrust;
	private ArrayList<Armor> shields = new ArrayList<Armor>();
	private Armor shieldSmash;
	private Armor shieldSwing;
	private Armor shieldThrust;
	private Armor helmet;
	private Armor breastPlate;
	private Armor suitOfArmor;
	private int weapon = 0;
	private int extraSharpness = 0;

	//OPPOSING ENEMY COMBAT INFO
	private int oppWeapon;
	private Weapons eWeapon;
	private ActionChit eDodgeSwing;
	private ActionChit eDuckSmash;
	private ActionChit eChargeThrust;

	public CombatInfo(ArrayList<ActionChit> ac, ArrayList<Armor> ar, Weapons w) {

		activeWeapon = w;
		actionChits = ac;
		smash = null;
		swing = null;
		thrust = null;
		dodge = null;
		duck = null;
		charge = null;
		shields = ar;
		shieldSmash = null; 
		shieldSwing = null;
		shieldThrust = null;
		helmet = null;
		breastPlate = null;
		suitOfArmor = null;
		if(w != null) {
			weapon = 0;
		}
		//
		oppWeapon = 0;
		eDodgeSwing = null;
		eDuckSmash = null;
		eChargeThrust = null;

		eWeapon = null;
	}

	//MY COMBAT INFO
	public int getWeaponLocation() {return weapon;}
	public Weapons getActiveWeapon() {return activeWeapon;}
	public ArrayList<ActionChit> getActionChits() {return actionChits;}
	public ActionChit getActionChitAt(int i) {	return actionChits.get(i);}
	public ArrayList<ActionChit> getUsedChits() {return usedChits;}
	public ActionChit getUsedChitAt(int i) {return usedChits.get(i);}
	public ActionChit[] getRoundChits() {return roundChits;}
	public ActionChit getroundChitAt(int i) {	return roundChits[i];}
	public ActionChit getDodge() {return dodge;}
	public ActionChit getDuck() {return duck;}
	public ActionChit getCharge() {return charge;}
	public ActionChit getSmash() {return smash;}
	public ActionChit getSwing() {return swing;}
	public ActionChit getThrust() {return thrust;}
	public ArrayList<Armor> getShields() {return shields;}
	public Armor getShieldAt(int i) {return shields.get(i);}
	public Armor getShieldSmash() {return shieldSmash;}
	public Armor getShieldSwing() {return shieldSwing;}
	public Armor getShieldThrust() {return shieldThrust;}
	public Armor getBreastPlate() {return breastPlate;}
	public Armor getHelmet() {return helmet;}
	public Armor getSuitOfArmor() {return suitOfArmor;}
	public int getExtraSharpnessUsed() {return extraSharpness;}

	public void setActiveWeapon(Weapons w) {this.activeWeapon = w;}
	public void setActionChits(ArrayList<ActionChit> actionChits) {this.actionChits = actionChits;}
	public void setUsedChits(ArrayList<ActionChit> usedChits) {this.usedChits = usedChits;}
	public void setRoundChits(ActionChit[] roundChits) {this.roundChits = roundChits;}
	public void setDodge(ActionChit dodge) {this.dodge = dodge;}
	public void setDuck(ActionChit duck) {this.duck = duck;}
	public void setCharge(ActionChit charge) {this.charge = charge;}
	public void setSmash(ActionChit smash) {this.smash = smash;}
	public void setSwing(ActionChit swing) {this.swing = swing;}
	public void setThrust(ActionChit thrust) {this.thrust = thrust;}
	public void setExtraSharpness(int i) {this.extraSharpness = i;}
	public void addExtraSharpness(int i) {this.extraSharpness += i;}
	public void addUsedChit(ActionChit usedChit) {this.usedChits.add(usedChit);}
	public void addActiveChit(ActionChit aChit) {this.actionChits.add(aChit);}
	public ActionChit removeActionChitAt(int i) {return actionChits.remove(i);}
	public Armor removeShield(int i) {return shields.remove(i);}
	

	//OPPOSING COMBAT INFO
	public int getOppWeaponLocation() {return oppWeapon;}
	public Weapons getEWeapon() {return eWeapon;}
	public ActionChit getEDodgeSwing() {return eDodgeSwing;}
	public ActionChit getEDuckSmash() {return eDuckSmash;}
	public ActionChit getEChargeThrust() {return eChargeThrust;}
	

	public void setEWeapon(Weapons w) {this.eWeapon = w;}
	public void seteDodgeSwing(ActionChit eDodgeSwing) {this.eDodgeSwing = eDodgeSwing;}
	public void seteChargeThrust(ActionChit eChargeThrust) {this.eChargeThrust = eChargeThrust;}
	public void seteDuckSmash(ActionChit ducksmash) {this.eDuckSmash = ducksmash;}
	public void setShields(ArrayList<Armor> shields) {this.shields = shields;}
	public void setShieldSmash(Armor shieldSmash) {this.shieldSmash = shieldSmash;}
	public void setShieldSwing(Armor shieldSwing) {this.shieldSwing = shieldSwing;}
	public void setShieldThrust(Armor shieldThrust) {this.shieldThrust = shieldThrust;}
	public void setBreastPlate(Armor bp) {this.breastPlate = bp;}
	public void setHelmet(Armor helmet) {this.helmet = helmet;}
	public void setSuitOfArmor(Armor suitOfArmor) {this.suitOfArmor = suitOfArmor;}
	public void setWeapon(int loc) { weapon = loc;}
	

	public void setOpposingEnemy(ActionChit eDSw, ActionChit eDSm, ActionChit eCT, Weapons w, int loc) {
		eDodgeSwing = eDSw;
		eDuckSmash = eDSm;
		eChargeThrust = eCT;
		eWeapon = w;
		oppWeapon = loc;

	}

}
