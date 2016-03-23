package Tiles;

import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import main.Chit;

public class PineWoods implements Tile, Serializable  {
	
	private String imgPath = "./images/tiles/pinewoods1.gif";
	private Image img = new ImageIcon(imgPath).getImage();
	
	private String imgPath1 = "./images/tiles2/pinewoods3.gif";
	private Image img1 = new ImageIcon(imgPath1).getImage();
	
	private Clearing[] clearings = new Clearing[6];
	private ArrayList<Chit> chits = new ArrayList<Chit>();
	private int tilePlaced;
	private char type;
	private String name;
	private Point tileLocation;

	private void writeObject(java.io.ObjectOutputStream stream)
			throws IOException {
		stream.writeObject(imgPath);
	//	stream.writeObject(imgPath1);
		stream.writeObject(clearings);
		stream.writeObject(chits);
		stream.writeInt(tilePlaced);
		stream.writeChar(type);
		stream.writeObject(name);
		stream.writeObject(tileLocation);
	}

	private void readObject(java.io.ObjectInputStream stream)
			throws IOException, ClassNotFoundException {
		imgPath = (String) stream.readObject();
		img = new ImageIcon(imgPath).getImage();
		
//		imgPath1 = (String) stream.readObject();
//		img1 = new ImageIcon(imgPath1).getImage();
		
		clearings = new Clearing[6];
		clearings = (Clearing[]) stream.readObject();
		chits = new ArrayList<Chit>();
		chits = (ArrayList<Chit>) stream.readObject();
		tilePlaced = stream.readInt();
		type = stream.readChar();
		name = (String) stream.readObject();
		tileLocation = (Point) stream.readObject();

	}
	
	public PineWoods() {
		type = 'w';
		tilePlaced = 0;
		name = "PW";
		tileLocation = new Point();
		chits = new ArrayList<Chit>();
		
		
	}
	
	public char getTileType() { 
		return type;
	}

	@Override
	public Point getClearingLocation(int num) {
		// TODO Auto-generated method stub
		return getClearing(num).getLocation();
	}

	@Override
	public void setClearing() {
		// TODO Auto-generated method stub
		int tileWidthA = ((img.getWidth(null)/2)*3)/4;
		//int tileWidthB = img.getWidth(null)/4;
		int tileHeight = ((img.getHeight(null)+8)/2);
		
		clearings[0] = null;
		clearings[1] = new Clearing(new Point((int) (tileLocation.x-(tileWidthA*0.03)), (int) (tileLocation.y+(tileHeight*0.45))), 2, 'w');
		clearings[2] = null;
		clearings[3] = new Clearing(new Point((int) (tileLocation.x+(tileWidthA*0.29)), (int) (tileLocation.y+(tileHeight*0.19))), 4, 'w');
		clearings[4] = new Clearing(new Point((int) (tileLocation.x+(tileWidthA*0.63)), (int) (tileLocation.y+(tileHeight*0.49))), 5, 'w');
		clearings[5] = null;
		for(int i = 0; i < clearings.length; i++) {
			if(clearings[i] != null){
				clearings[i].setTileName(name);
			}
		}
		setAdjacentClearings();
	}
	
	public void setAdjacentClearings() {
		clearings[1].setAdjacentClearing(clearings[3]);
		clearings[3].setAdjacentClearing(clearings[1]);
				
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
		return img;
	}
	public boolean isMouseOnClearing(Point p) {
		boolean result = false;
		for (int i = 0; i < clearings.length; i++) {
			if ((p.x <= (clearings[i].getXLocation()+15)) && (p.x >= (clearings[i].getXLocation() -15)) && 
					(p.y <= (clearings[i].getYLocation() + 15)) && (p.y >= (clearings[i].getYLocation() -15))) {
				result = true;
				break;
			}
		}
		return result;
	}
	@Override
	public Clearing getClearing(int i) {
		// TODO Auto-generated method stub
		Clearing c = null;
		if (i == 0) {
			c = clearings[0];
		} else if(i == 1) {
			c = clearings[0];
		} else if (i == 2) {
			c = clearings[1];
		} else if (i == 3) {
			c = clearings[2];
		} else if (i == 4) {
			c = clearings[3];
		} else if (i == 5) {
			c = clearings[4];
		} else if (i == 6) {
			c = clearings[5];
		}
		return c;
	}
	@Override
	public Clearing[] getAllClearings() {
		// TODO Auto-generated method stub
		return clearings;
	}

	public Chit getChitAt(int i) {
		return chits.get(i);
	}
	
	public ArrayList<Chit> getAllChits() {
		return chits;
	}
	
	public void addChit(Chit c) {
		chits.add(c);
	}
	
	public void makeChitArray(ArrayList<Chit> c) {
		chits = c;
	}
	
	public void removeChit(int i) {
		chits.remove(i);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
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
		setClearing();
	}
	public boolean containsHiddenPaths() {
		return false;
	}
	
	public boolean containsSecretPaths() {
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
		return img1;
	}

	@Override
	public void setClearing1() {
		// TODO Auto-generated method stub
		int tileWidthA = ((img1.getWidth(null)/2)*3)/4;
		//int tileWidthB = img.getWidth(null)/4;
		int tileHeight = ((img1.getHeight(null)+8)/2);
		
		clearings[0] = null;
		clearings[1] = new Clearing(new Point((int) (tileLocation.x+(tileWidthA*0.69)), (int) (tileLocation.y+(tileHeight*0.54))), 2, 'w');
		clearings[2] = null;
		clearings[3] = new Clearing(new Point((int) (tileLocation.x+(tileWidthA*0.40)), (int) (tileLocation.y+(tileHeight*0.79))), 4, 'w');
		clearings[4] = new Clearing(new Point((int) (tileLocation.x+(tileWidthA*0.04)), (int) (tileLocation.y+(tileHeight*0.51))), 5, 'w');
		clearings[5] = null;
		for(int i = 0; i < clearings.length; i++) {
			if(clearings[i] != null){
				clearings[i].setTileName(name);
			}
		}
		setAdjacentClearings();
	}
}
