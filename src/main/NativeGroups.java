package main;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;

public class NativeGroups implements Item, Serializable {
	private String name;
	private char idCode;
	private int priceToHire;
	private ArrayList<NativeMember> members;

	public NativeGroups(String n, int price) {
		name = n;
		idCode = n.charAt(0);
		priceToHire = price;
		members = new ArrayList<NativeMember>();
	}

	public void addNative(NativeMember n) {
		members.add(n);
	}

	public NativeMember getNative(int i) {
		return members.get(i);
	}

	public ArrayList<NativeMember> getAllNatives() {
		return members;
	}

	public int getPriceToHire() {
		return priceToHire;
	}

	public void setPriceToHire(int priceToHire) {
		this.priceToHire = priceToHire;
	}

	public char getGroupID() {
		return idCode;
	}

	public void setgroupID(char id) {
		this.idCode = id;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		Image r = null;
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).isLeader()) {
				r = members.get(i).getNativeType().getImage();
				break;
			}
		}
		return r;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public char getItemType() {
		return 'N';
	}

}
