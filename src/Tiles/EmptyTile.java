package Tiles;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Chit;

public class EmptyTile implements Tile, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 82703372640068733L;
	private int tilePlaced;
	private Point tileLocation;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeInt(tilePlaced);
		stream.writeObject(tileLocation);
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		tilePlaced = stream.readInt();
		tileLocation = (Point) stream.readObject();
	}

	public EmptyTile() {
		tilePlaced = 2;
		tileLocation = new Point();
	}

	@Override
	public Point getClearingLocation(int num) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClearing() {
		// TODO Auto-generated method stub

	}

	@Override
	public int isTilePlaced() {
		// TODO Auto-generated method stub
		return tilePlaced;
	}

	@Override
	public void setTilePlaced(int x) {
		// TODO Auto-generated method stub
		tilePlaced = x;

	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clearing getClearing(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char getTileType() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isMouseOnClearing(Point p) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Clearing[] getAllClearings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chit getChitAt(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Chit> getAllChits() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addChit(Chit c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeChitArray(ArrayList<Chit> c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeChit(int i) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point getTileLocation() {
		// TODO Auto-generated method stub
		return tileLocation;
	}

	@Override
	public void setTileLocation(int x, int y) {
		// TODO Auto-generated method stub
		tileLocation.setLocation(x, y);
	}

	@Override
	public boolean containsHiddenPaths() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsSecretPaths() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<String> getHiddenPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<String> getSecretPaths() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getImage1() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClearing1() {
		// TODO Auto-generated method stub
		
	}

}
