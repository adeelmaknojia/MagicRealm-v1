package treasureSetupCard;

import java.util.ArrayList;
import java.util.Random;

import main.Item;
import main.Items;
import main.NativesI;
import main.WeaponInst;

public class Garrisons {
	
	private ArrayList<Item> chapel = new ArrayList<Item>();
	private ArrayList<Item> house = new ArrayList<Item>();
	private ArrayList<Item> inn = new ArrayList<Item>();
	private ArrayList<Item> guard = new ArrayList<Item>();
	
	
	public Garrisons() {
		setChapel();
		setHouse();
		setInn();
		setGuard();
	}
	
	public void setChapel(){
		chapel.add(NativesI.ORDER);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			chapel.add(Items.treasures.get(l));
		}
		chapel.add(Items.SUITOFARMOR);
		chapel.add(Items.SUITOFARMOR);
		chapel.add(WeaponInst.CROSSBOW);
		chapel.add(WeaponInst.GREATSWORD);
		chapel.add(WeaponInst.DEVILSWORD);
		chapel.add(WeaponInst.MORNINGSTAR);
		chapel.add(WeaponInst.GREATAXE);
		
	}
	
	public void setHouse(){
		house.add(NativesI.SOLDIERS);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			house.add(Items.treasures.get(l));
		}
		house.add(Items.HELMET);
		house.add(Items.HELMET);
		house.add(Items.HELMET);
		house.add(Items.SHIELD);
		house.add(Items.SHIELD);
		house.add(WeaponInst.LIGHTBOW);
		house.add(WeaponInst.STAFF);
		house.add(WeaponInst.THRUSTINGSWORD);
		house.add(WeaponInst.LIVINGSWORD);
		house.add(WeaponInst.SHORTSWORD);
		
	}
	
	public void setInn(){
		inn.add(NativesI.ROGUES);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			inn.add(Items.treasures.get(l));
		}
		
	}
	
	public void setGuard(){
		guard.add(NativesI.GUARD);
		Random r = new Random();
		for(int i = 0; i < 2; i++) {
			int l = r.nextInt(Items.treasures.size());
			guard.add(Items.treasures.get(l));
		}
		guard.add(Items.HELMET);
		guard.add(Items.BREASTPLATE);
		guard.add(WeaponInst.MEDIUMBOW);
		guard.add(WeaponInst.SPEAR);
		guard.add(WeaponInst.BROADSWORD);
		guard.add(WeaponInst.TRUESTEELSWORD);
		guard.add(WeaponInst.AXE);
		
	}
	
	public ArrayList<Item> getChapel() {
		return chapel;
	}
	public ArrayList<Item> getHouse() {
		return house;
	}
	public ArrayList<Item> getInn() {
		return inn;
	}
	public ArrayList<Item> getGuard() {
		return guard;
	}

}
