package main;

import java.io.Serializable;
import java.util.ArrayList;

public class NativeMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3289056184437903009L;
	private String name;
	private String nativeGroup;
	private Natives nativeType;
	private boolean isLeader;
	private String unalertedAttack;
	private int unalertedMove;
	private String alertedAttack;
	private int alertedMove;
	private ArrayList<Item> items = new ArrayList<Item>();

	public NativeMember(String name, String nGroup, Natives nativeType,
			String unalertedAttack, int unalertedMove, String alertedAttack,
			int alertedMove) {
		super();
		this.name = name;
		this.nativeGroup = nGroup;
		this.nativeType = nativeType;
		this.unalertedAttack = unalertedAttack;
		this.unalertedMove = unalertedMove;
		this.alertedAttack = alertedAttack;
		this.alertedMove = alertedMove;
		if (name.contains("HQ")) {
			isLeader = true;
		} else {
			isLeader = false;
		}
	}

	public String getName() {
		return name;
	}
	
	public String getNativeGroup() {
		return nativeGroup;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Natives getNativeType() {
		return nativeType;
	}

	public void setNativeType(Natives nativeType) {
		this.nativeType = nativeType;
	}

	public boolean isLeader() {
		return isLeader;
	}

	public String getUnalertedAttack() {
		return unalertedAttack;
	}

	public int getUnalertedMove() {
		return unalertedMove;
	}

	public String getAlertedAttack() {
		return alertedAttack;
	}

	public int getAlertedMove() {
		return alertedMove;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item getAnItem(int i) {
		return items.get(i);
	}
	
	public void setItems(ArrayList<Item> i) {
		items = i;
	}
	
	public void addItem(Item i) {
		items.add(i);
	}
	
	public Item removeItem(int i) {
		return items.remove(i);
	}
}
