package main;

import java.awt.Graphics2D;
import java.awt.Image;
import java.io.Serializable;

public class Monsters implements Serializable {
	
	//(String name, String size, boolean isArmored, int[] bounty,
	//boolean fly, WeaponInst weapon, int[] sharpness, int[] attackSpeed,
	//char[] harmLetter, int[] moveSpeed, String i) {

	public static Monster GHOST = new Monster("GHOST", "MEDIUM", false, new int[] {0, 2}, false, null, new int[] {0,0}, new char[] {'H', 'L'}, new int[] {4, 2}, new int[] {4,2}, "./images/monsters/ghost.png");
	public static Monster GIANTBAT = new Monster("GIANT BAT", "HEAVY", false, new int[] {3, 3}, false, null, new int[] {0,0}, new char[] {'M', 'M'}, new int[] {2, 3}, new int[] {3,2}, "./images/monsters/bat.png");
	public static Monster HEAVYSPIDER = new Monster("HEAVY SPIDER", "HEAVY", false, new int[] {3, 3}, false, null, new int[] {0,0}, new char[] {'T', 'L'}, new int[] {4, 6}, new int[] {3,4}, "./images/monsters/spider.png");
	public static Monster TREMENDOUSSPIDER = new Monster("TREMENDOUS SPIDER", "TREMENDOUS", false, new int[] {6, 6}, false, null, new int[] {0,0}, new char[] {'M', '\0'}, new int[] {4, 5}, new int[] {3,4}, "./images/monsters/spider-t.png");
	public static Monster TREMENDOUSDRAGON = new Monster("TREMENDOUS DRAGON", "TREMENDOUS", true, new int[] {10, 10}, false, null, new int[] {0,0}, new char[] {'H', '\0'}, new int[] {4, 3}, new int[] {6,6}, "./images/monsters/dragon-t.png");
	public static Monster GIANT = new Monster("GIANT", "TREMENDOUS", false, new int[] {8, 8}, false, null, new int[] {0,0}, new char[] {'H', '\0'}, new int[] {5, 4}, new int[] {5,6}, "./images/monsters/giant.png");
	public static Monster TREMENDOUSTROLL = new Monster("TREMEDOUS TROLL", "TREMENDOUS", true, new int[] {8, 8}, false, null, new int[] {0,0}, new char[] {'H', '\0'}, new int[] {4, 2}, new int[] {4,6}, "./images/monsters/troll-t.png");
	public static Monster OCTOPUS = new Monster("OCTOPUS", "TREMENDOUS", false, new int[] {8, 8}, false, null, new int[] {0,0}, new char[] {'L', '\0'}, new int[] {4, 4}, new int[] {2,3}, "./images/monsters/octopus.png");
	public static Monster TREMENDOUSSERPENT = new Monster("TREMENDOUS SERPENT", "TREMENDOUS", false, new int[] {4, 4}, false, null, new int[] {0,0}, new char[] {'L', '\0'}, new int[] {4, 4}, new int[] {4,5}, "./images/monsters/serpent-t.png");
	public static Monster HEAVYDRAGON = new Monster("HEAVY DRAGON", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'H', 'M'}, new int[] {4, 4}, new int[] {4,3}, "./images/monsters/dragon-h.png");
	public static Monster HEAVYTROLL = new Monster("HEAVY TROLL", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'M','H'}, new int[] {4, 5}, new int[] {4,4}, "./images/monsters/troll-h.png");
	public static Monster HEAVYSERPENT = new Monster("HEAVY SERPENT", "HEAVY", true, new int[] {4, 4}, false, null, new int[] {0,0}, new char[] {'M', 'H'}, new int[] {2, 3}, new int[] {3,4}, "./images/monsters/serpent-h.png");
	public static Monster VIPER = new Monster("VIPER", "MEDIUM", true, new int[] {1, 2}, false, null, new int[] {2,0}, new char[] {'M', 'L'}, new int[] {4, 2}, new int[] {4,2}, "./images/monsters/serpent.png");
	public static Monster WOLF1 = new Monster("WOLF 1", "MEDIUM", false, new int[] {0, 1}, false, null, new int[] {0,0}, new char[] {'L', 'M'}, new int[] {4, 4}, new int[] {3,4}, "./images/monsters/wolf.png");
	public static Monster WOLF2 = new Monster("WOLF 2", "MEDIUM", false, new int[] {0, 1}, false, null, new int[] {0,0}, new char[] {'L', 'M'}, new int[] {4, 4}, new int[] {3,4}, "./images/monsters/imp.png");
	public static Monster TREMENDOUSFLYINGDRAGON = new Monster("TREMENDOUS FLYING DRAGON", "TREMENDOUS", true, new int[] {12, 12}, true, null, new int[] {0,0}, new char[] {'M', '\0'}, new int[] {3, 6}, new int[] {4,4}, "./images/monsters/dragon_flying-t.png");
	public static Monster HEAVYFLYINGDRAGON = new Monster("HEAVY FLYING DRAGON", "HEAVY", true, new int[] {5, 5}, true, null, new int[] {0,0}, new char[] {'M', '\0'}, new int[] {3, 6}, new int[] {4,4}, "./images/monsters/dragon_flying.png");
	public static Monster GOBLINAXE = new Monster("HEAVY TROLL", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'M','H'}, new int[] {4, 5}, new int[] {4,4}, "./images/monsters/goblin_axe.png");
	public static Monster GOBLINSPEAR = new Monster("HEAVY TROLL", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'M','H'}, new int[] {4, 5}, new int[] {4,4}, "./images/monsters/goblin_spear.png");
	public static Monster GOBLINGSWORD = new Monster("HEAVY TROLL", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'M','H'}, new int[] {4, 5}, new int[] {4,4}, "./images/monsters/goblin_sword.png");
	public static Monster DEMON = new Monster("HEAVY TROLL", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'M','H'}, new int[] {4, 5}, new int[] {4,4}, "./images/monsters/demon.png");
	public static Monster WINGEDDEMON = new Monster("HEAVY TROLL", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'M','H'}, new int[] {4, 5}, new int[] {4,4}, "./images/monsters/demon_flying.png");
	public static Monster IMP = new Monster("HEAVY TROLL", "HEAVY", true, new int[] {5, 5}, false, null, new int[] {0,0}, new char[] {'M','H'}, new int[] {4, 5}, new int[] {4,4}, "./images/monsters/imp.png");
	
	public static void addMonster(int i, int j, Graphics2D g2, Image img){
		int x = i - (30/2);
		int y = j - (30/2);
		g2.drawImage(img, x, y, 30, 30, null);
	};
}
