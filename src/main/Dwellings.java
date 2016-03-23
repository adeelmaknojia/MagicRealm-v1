package main;
import java.awt.Graphics2D;


public class Dwellings {
	public static Dwelling[] dwellings;
	
	public Dwellings() {
		dwellings =  new Dwelling[7];
		initialize();
		
	}
	
	private void initialize() {
		dwellings[0] = new Dwelling("Chapel", "./images/dwellings/chapel.png");
		dwellings[1] = new Dwelling("Guard", "./images/dwellings/guard.png");
		dwellings[2] = new Dwelling("House", "./images/dwellings/house.png");
		dwellings[3] = new Dwelling("Hut", "./images/dwellings/hut.png");
		dwellings[4] = new Dwelling("Inn", "./images/dwellings/inn.png");
		dwellings[5] = new Dwelling("Large Fire", "./images/dwellings/large_fire.gif");
		dwellings[6] = new Dwelling("Small Fire", "./images/dwellings/small_fire.gif");
	}
	
	
	
	public Dwelling getDwelling(int i) {
		return dwellings[i];
	}
	
	public Dwelling getDwelling(String name) {
		Dwelling d = null;
		for (int i = 0; i< dwellings.length; i++) {
			if (dwellings[i].getName().equalsIgnoreCase(name)) {
				d = dwellings[i];
				break;
			}
		}
		return d;
	}
	
	public Dwelling getChapel() {
		return dwellings[0];
	}
	
	public Dwelling getGuard() {
		return dwellings[1];
	}
	
	public Dwelling getHouse() {
		return dwellings[2];
	}
	
	public Dwelling getHut() {
		return dwellings[3];
	}
	
	public Dwelling getInn() {
		return dwellings[4];
	}
	
	public Dwelling getLFire() {
		return dwellings[5];
	}
	
	public Dwelling getSFire() {
		return dwellings[6];
	}
	

}
