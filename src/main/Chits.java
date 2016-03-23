package main;
import java.util.ArrayList;
import java.util.Random;


public class Chits 
{
	ArrayList<Chit> chits = new ArrayList<Chit>(40);
	public static ArrayList<Chit> lostCastleChits = new ArrayList<Chit>();
	public static ArrayList<Chit> lostCityChits = new ArrayList<Chit>();
	
	static int SIZE = 10;
	static int SIZE2 = 5;

	public Chits() {
		initWarningChits();
		initSoundChits();
		initSiteChits();
	}

	private void initWarningChits() {
		chits.add(new WarningChits("BONES", "./images/chit/chit.gif", "./images/chit/bonesc.jpg", 'c'));
		chits.add(new WarningChits("DANK", "./images/chit/chit.gif", "./images/chit/dankc.jpg", 'c'));
		chits.add(new WarningChits("RUINS", "./images/chit/chit.gif", "./images/chit/ruinsc.jpg", 'c'));
		chits.add(new WarningChits("SMOKE", "./images/chit/chit.gif", "./images/chit/smokec.jpg", 'c'));
		chits.add(new WarningChits("STINK", "./images/chit/chit.gif", "./images/chit/stinkc.jpg", 'c'));
		chits.add(new WarningChits("BONES", "./images/chit/chit.gif", "./images/chit/bonesm.jpg", 'm'));
		chits.add(new WarningChits("DANK", "./images/chit/chit.gif", "./images/chit/dankm.jpg", 'm'));
		chits.add(new WarningChits("RUINS", "./images/chit/chit.gif", "./images/chit/ruinsm.jpg", 'm'));
		chits.add(new WarningChits("SMOKE", "./images/chit/chit.gif", "./images/chit/smokem.jpg", 'm'));
		chits.add(new WarningChits("STINK", "./images/chit/chit.gif", "./images/chit/stinkm.jpg", 'm'));
		chits.add(new WarningChits("BONES", "./images/chit/chit.gif", "./images/chit/bonesv.jpg", 'v'));
		chits.add(new WarningChits("DANK", "./images/chit/chit.gif", "./images/chit/dankv.jpg", 'v'));
		chits.add(new WarningChits("RUINS", "./images/chit/chit.gif", "./images/chit/ruinsv.jpg", 'v'));
		chits.add(new WarningChits("SMOKE", "./images/chit/chit.gif", "./images/chit/smokev.jpg", 'v'));
		chits.add(new WarningChits("STINK", "./images/chit/chit.gif", "./images/chit/stinkv.jpg", 'v'));
		chits.add(new WarningChits("BONES", "./images/chit/chit.gif", "./images/chit/bonesw.jpg", 'w'));
		chits.add(new WarningChits("DANK", "./images/chit/chit.gif", "./images/chit/dankw.jpg", 'w'));
		chits.add(new WarningChits("RUINS", "./images/chit/chit.gif", "./images/chit/ruinsw.jpg", 'w'));
		chits.add(new WarningChits("SMOKE", "./images/chit/chit.gif", "./images/chit/smokew.jpg", 'w'));
		chits.add(new WarningChits("STINK", "./images/chit/chit.gif", "./images/chit/stinkw.jpg", 'w'));
	}

	private void initSoundChits() {
		chits.add(new SoundChits("HOWL", 4, "./images/chit/chit.gif", "./images/chit/howl4.jpg"));
		chits.add(new SoundChits("HOWL", 5, "./images/chit/chit.gif", "./images/chit/howl5.jpg"));
		chits.add(new SoundChits("FLUTTER", 1, "./images/chit/chit.gif", "./images/chit/flutter1.jpg"));
		chits.add(new SoundChits("FLUTTER", 2, "./images/chit/chit.gif", "./images/chit/flutter2.jpg"));
		chits.add(new SoundChits("PATTER", 2, "./images/chit/chit.gif", "./images/chit/patter2.jpg"));
		chits.add(new SoundChits("PATTER", 5, "./images/chit/chit.gif", "./images/chit/patter5.jpg"));
		chits.add(new SoundChits("ROAR", 4, "./images/chit/chit.gif", "./images/chit/roar4.jpg"));
		chits.add(new SoundChits("ROAR", 6, "./images/chit/chit.gif", "./images/chit/roar6.jpg"));
		chits.add(new SoundChits("SLITHER", 3, "./images/chit/chit.gif", "./images/chit/slither3.jpg"));
		chits.add(new SoundChits("SLITHER", 6, "./images/chit/chit.gif", "./images/chit/slither6.jpg"));
	}	
	private void initSiteChits() {
		chits.add(new SiteChits("LOST CITY", 3, "./images/chit/chit.gif", "./images/chit/lostcity.jpg"));
		chits.add(new SiteChits("LOST CASTLE", 1, "./images/chit/chit.gif", "./images/chit/lostcastle.jpg"));
		chits.add(new SiteChits("STATUE", 2, "./images/chit/chit.gif", "./images/chit/statue.jpg"));
		chits.add(new SiteChits("ALTER", 1, "./images/chit/chit.gif", "./images/chit/alter.jpg"));
		chits.add(new SiteChits("VAULT", 3, "./images/chit/chit.gif", "./images/chit/vault.jpg"));
		chits.add(new SiteChits("POOL", 6, "./images/chit/chit.gif", "./images/chit/pool.jpg"));
		chits.add(new SiteChits("HOARD", 6, "./images/chit/chit.gif", "./images/chit/hoard.jpg"));
		chits.add(new SiteChits("LAIR", 3, "./images/chit/chit.gif", "./images/chit/lair.jpg"));
		chits.add(new SiteChits("CAIRNS", 5, "./images/chit/chit.gif", "./images/chit/cairns.jpg"));
		chits.add(new SiteChits("SHRINE", 4, "./images/chit/chit.gif", "./images/chit/shrine.jpg"));
		
	}	
	public Chit getChit(int loc) {
		return chits.get(loc);
	}
	public ArrayList<Chit> getAllSChit() {
		return chits;
	}
	public Chit getChit(String name) {
		Chit result = null;
		for (int i = 0; i < chits.size(); i++) {
			if (chits.get(i).getChitName().equalsIgnoreCase(name)) {
				result = chits.get(i);
				break;
			}
		}
		return result;
	}

	public static ArrayList<Chit> randomizeAppearanceChart(ArrayList<Chit> c) {
		ArrayList<Chit> appChart = new ArrayList<Chit>(10);
		int i = 0;
		while(i < SIZE)
		{
			Random r = new Random();
			int loc = r.nextInt(c.size());
			while (c.get(loc).isLost() || c.get(loc).getChitType() == 'W') {
				loc = r.nextInt(c.size());
			}
			appChart.add(c.remove(loc));
			i++;
		}
		return appChart;
	}
	
	public static ArrayList<Chit> makeLostCastle(ArrayList<Chit> c){
		ArrayList<Chit> lostCastle = new ArrayList<Chit>(5);

		for(int i = 0; i < c.size()/2; i++) {
			lostCastle.add(c.remove(i));
		}
		return lostCastle;
	}

	public static ArrayList<Chit> makeLostCity(ArrayList<Chit> c){
		ArrayList<Chit> lostCity = new ArrayList<Chit>(5);
		for(int i = 0; i < c.size(); i++) {
			lostCity.add(c.remove(i));
		}
		return lostCity;
	}
	public static ArrayList<Chit> randomizeCaveChits(ArrayList<Chit> c) {
		ArrayList<Chit> cave = new ArrayList<Chit>();
		int full = 0;
		while (full != 4) {
			Random r = new Random();
			int loc = r.nextInt(c.size());
			if (!c.get(loc).isLost() && c.get(loc).getChitType() != 'W') {
				cave.add(c.remove(loc));
				full++;
			}
		}
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).getChitName().equals("LOST CITY")) {
				cave.add(c.remove(i));
				break;
			}
		}
		int full2 = 0;
		while (full2 != 5) {
			Random r = new Random();
			int loc = r.nextInt(c.size());
			while((c.get(loc).getChitType() != 'W') || (c.get(loc).getWarningType() != 'c')) {

				loc = r.nextInt(c.size());
			}
			if((c.get(loc).getChitType() == 'W') && (c.get(loc).getWarningType() == 'c')) {
				cave.add(c.remove(loc));
			}
			full2++;
		}

		return cave;
	}

	public static ArrayList<Chit> randomizeMountainChits(ArrayList<Chit> c) {
		ArrayList<Chit> mountain = new ArrayList<Chit>();
		int full = 0;
		while (full != 4) {
			Random r = new Random();
			int loc = r.nextInt(c.size());
			if (!c.get(loc).isLost() && c.get(loc).getChitType() != 'W') {
				mountain.add(c.remove(loc));
				full++;
			}
		}
		for (int i = 0; i < c.size(); i++) {
			if (c.get(i).getChitName().equals("LOST CASTLE")) {
				mountain.add(c.remove(i));
				break;
			}
		}
		int full2 = 0;
		while (full2 != 5) {
			Random r = new Random();
			int loc = r.nextInt(c.size());
			while((c.get(loc).getChitType() != 'W') || (c.get(loc).getWarningType() != 'm')) {

				loc = r.nextInt(c.size());
			}
			if((c.get(loc).getChitType() == 'W') && (c.get(loc).getWarningType() == 'm')) {
				mountain.add(c.remove(loc));
			}
			full2++;
		}
		return mountain;
	}	

	public static ArrayList<Chit> randomizeValleyChits(ArrayList<Chit> c) {
		ArrayList<Chit> valley = new ArrayList<Chit>();
		int full = 0;
		while (full != 5) {
			Random r = new Random();
			int loc = r.nextInt(c.size());
			while((c.get(loc).getChitType() != 'W') || (c.get(loc).getWarningType() != 'v')) {

				loc = r.nextInt(c.size());
			}
			if((c.get(loc).getChitType() == 'W') && (c.get(loc).getWarningType() == 'v')) {
				valley.add(c.remove(loc));
			}
			full++;
		}
		return valley;
	}

	public static ArrayList<Chit> randomizeWoodsChits(ArrayList<Chit> c) {
		ArrayList<Chit> woods = new ArrayList<Chit>();
		int full = 0;
		while (full != 5) {
			Random r = new Random();
			int loc = r.nextInt(c.size());
			while((c.get(loc).getChitType() != 'W') || (c.get(loc).getWarningType() != 'w')) {

				loc = r.nextInt(c.size());
			}
			if((c.get(loc).getChitType() == 'W') && (c.get(loc).getWarningType() == 'w')) {
				woods.add(c.remove(loc));
			}
			full++;
		}
		return woods;
	}
}
