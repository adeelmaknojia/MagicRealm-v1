package main;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Tiles.Clearing;

public class Visitor implements Item, Serializable  {

	private static final long serialVersionUID = 7279618187236418151L;
	private String name;
	private ArrayList<Item> items = new ArrayList<Item>();
	private Image img;
	private String imgPath;
	
	
	public Visitor(String string) {
		this.name = string;
		//this.img = new ImageIcon(i).getImage();
		//this.items = items;
	}
	public String getName() {
		return name;
	}
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Item getItem(int i) {
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
	
	public Image getImage() {
		// TODO Auto-generated method stub
		return img;
	}
	public char getItemType() {
		return 'V';
	}

}
