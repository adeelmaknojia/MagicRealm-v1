package main;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

import treasureSetupCard.*;
import main.*;


public class TreasureSetUpCard {

	
	public Items Items = new Items();
	public LostCastle lostCastle;
	public LostCity lostCity;
	public TreasureLocation treasureLocation = new TreasureLocation();
	public treasureSetupCard.Dwellings dwellings = new treasureSetupCard.Dwellings();
	public Garrisons garrisons = new Garrisons();
	
	public TreasureSetUpCard(ArrayList<Chit> castleChits, ArrayList<Chit> cityChits) {
		lostCastle = new LostCastle(castleChits);
		lostCity = new LostCity(cityChits);
	}
	
	
}