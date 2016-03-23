package Tiles;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;

import main.Chit;


public interface Tile {
	
	
	public char getTileType();
	public void setClearing();
	public void setClearing1();
	public int isTilePlaced();
	public void setTilePlaced(int x);
	public Image getImage();
	public Image getImage1();
	Point getClearingLocation(int num);
	public Clearing getClearing(int i);
	public boolean isMouseOnClearing(Point p);
	public Clearing[] getAllClearings();
	public Chit getChitAt(int i);
	public ArrayList<Chit> getAllChits();
	public void addChit(Chit c);
	public void makeChitArray(ArrayList<Chit> c);	
	public void removeChit(int i);
	public String getName();
	public Point getTileLocation();
	public void setTileLocation(int x, int y);
	public boolean containsHiddenPaths();
	public boolean containsSecretPaths();
	public ArrayList<String> getHiddenPaths();
	public ArrayList<String> getSecretPaths();

}

