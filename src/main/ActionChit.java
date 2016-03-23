package main;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ActionChit {
	private String name;
	private String actionType;
	private char actionLevel;
	private int harmLevel;
	private int actionNum;
	private int effort;//??
	private int active;
	private int fatiguedEffort = effort;
	private Image[] img = new Image[2];
	private boolean wounded;
	private boolean fatigued;
	
	public ActionChit(String n, String aT, char l, int num, int e, String u, String a) {
		name = n;
		actionType = aT;
		actionLevel = l;
		setHarmLevel(l);
		actionNum = num;
		effort = e;
		active = 0;
		img[0] = new ImageIcon(u).getImage();
		img[1] = new ImageIcon(a).getImage();
		wounded = false;
		fatigued = false;
	}
	
	public String getName() {return name;}
	
	public String getActionType() {return actionType;}
	public char getActionLevel() {return actionLevel;}
	public int getActionNum()  {return actionNum;}
	public int getEffort() {return effort;}
	public int isActive() {return active;}
	public Image getImage() {return img[isActive()];}
	public boolean isWounded() { return wounded;}
	public char getItemType() {return 'F';}
	public int getHarmLevel(){return harmLevel;}
	public boolean isFatigued(){return fatigued;}
	
	public void setAImage(Image i) {img[0] = i;}
	public void setIImage(Image i) {img[1] = i;}
	public void activate() { active = 1;}
	public void inactivate(){ active = 0;}
	//public void fatigueEffort(int i) { effort -= i;}
	public void makeWounded() { wounded = true; active = 0;}
	public void unWounded() { wounded = false;}
	public void makeFatigued() { fatigued = true; effort -= fatiguedEffort; }
	public void unFatigue() { fatigued = false; effort += fatiguedEffort;}
	public void setHarmLevel(int h) { harmLevel = h;}
	public void setHarmLevel(char w) {
		if(w == 'L') {
			harmLevel = 1;
		} else if(w == 'M') {
			harmLevel = 2;
		} else if(w == 'H') {
			harmLevel = 3;
		} else if(w == 'T') {
			harmLevel = 4;
		}
	}
	

}
