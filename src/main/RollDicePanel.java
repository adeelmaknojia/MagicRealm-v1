package main;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

//////////////////////////////////////////////////////////// class RollDicePanel
public class RollDicePanel extends JPanel{
    //======================================================= instance variables
    private Die D1;     // component for one die 
    private Die D2;
    private Die D3;
    private Die D4;
    private Die D5;
    private Die D6;
    private Die D7;
    private Die D8;
    JButton rollButton;
	private int NumberOfRolls = 500;  // Rolls allowed in each round
	private int roll = 0;           // variable to count number of rolls done by player
	private int [] dice ;			// array hold values for 2 dice
	public static int ans = 0;					// holds the dice face values
	

    //============================================================== constructor
    /** Create border layout panel with one button and two dice. */
    RollDicePanel() {
    	
        //... Create the dice
        D1 = new Die(1);
        D2 = new Die(2);
        D3 = new Die(3);
        D4 = new Die(4);
        D5 = new Die(5);
        D6 = new Die(6);
        D7 = new Die();
        D8 = new Die();
        dice = new int [8];
        
        //...Create the button to roll the dice
        ImageIcon Rimg = new ImageIcon("./images/actions/dice.gif");
        rollButton = new JButton("Roll Dice",Rimg);
       // rollButton.setVerticalTextPosition(SwingConstants.BOTTOM);
      //  rollButton.setVerticalAlignment ( SwingConstants.TOP ) ;
        rollButton.setHorizontalTextPosition ( SwingConstants.LEFT ) ;
        rollButton.setContentAreaFilled(false);
        rollButton.setFont(new Font("Arial", Font.PLAIN, 12));
        
        //... Add listener.
        rollButton.addActionListener(new RollListener());
        
        // cheat mode and setup card roll
        D1.addActionListener(new D1Listener());
        D2.addActionListener(new D2Listener());
        D3.addActionListener(new D3Listener());
        D4.addActionListener(new D4Listener());
        D5.addActionListener(new D5Listener());
        D6.addActionListener(new D6Listener());
        // random roll
       // D7.addActionListener(new D7Listener());
       // D8.addActionListener(new D8Listener());

     
        
        //... Layout components
        this.setLayout(null);
   
        D1.setBounds(10, 30, 40, 40);
        this.add(D1);
        
        D2.setBounds(60, 30, 40, 40);
        this.add(D2);
        
        D3.setBounds(110, 30, 40, 40);
        this.add(D3);
        
        D4.setBounds(160, 30, 40, 40);
        this.add(D4);
        
        D5.setBounds(210, 30, 40, 40);
        this.add(D5);
        
        D6.setBounds(260, 30, 40, 40);
        this.add(D6);

        D7.setBounds(160, 90, 40, 40);
        this.add(D7);
        
        D8.setBounds(210, 90, 40, 40);
        this.add(D8);
        
      //  rollButton.setLocation(10, 310);
        rollButton.setBounds(20, 95, 120, 30);
        this.add(rollButton);

    }
    
    public void disableRollButton(){
    	rollButton.setEnabled(false);
    }
    
    public void enableRollButton(){
    	rollButton.setEnabled(true);
    }
    
     // getter to get array all dice
     public int [] getDiceValues(){
    	 return dice;
     }
    
     
     public int getHDiceValue(){
    	 return ans;
     }
     
     public void selectDiceTrue(Die x){
        x.setHoldState(true);
    	x.setBorderPainted(true);
     	x.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); 	
     }
     
     public void selectDiceFalse(Die x){
     	x.setHoldState(false);
        x.setBorderPainted(false);	
      }
   
    
    //listener class RollListener for rolling dices
    private class RollListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	selectDiceFalse(D7);
        	selectDiceFalse(D8);
        	if(roll < NumberOfRolls){
	            dice[6] = D7.roll();
	            dice[7] = D8.roll();
	            roll++; 
	            if(D7.getFaceValue() >= D8.getFaceValue()){
	            	selectDiceTrue(D7);
	            	ans= D7.getFaceValue();
	            	selectDiceFalse(D1);
	            	selectDiceFalse(D2);
	            	selectDiceFalse(D3);
	            	selectDiceFalse(D4);
	            	selectDiceFalse(D5);
	            	selectDiceFalse(D6);
	            	selectDiceFalse(D8);
	            }
	            
	            else{
	            	
	            	selectDiceTrue(D8);
	            	ans= D8.getFaceValue();
	            	selectDiceFalse(D1);
	            	selectDiceFalse(D2);
	            	selectDiceFalse(D3);
	            	selectDiceFalse(D4);
	            	selectDiceFalse(D5);
	            	selectDiceFalse(D6);
	            	selectDiceFalse(D7);
	            }
        	}
        	
        	else
        		rollButton.setEnabled(false);
        	
        }
    }
    
//    // Listener class for D1-D8 buttons to check if any dice is selected or not
//    private class D7Listener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//        	
//        	if(D7.getHoldState() == false && roll > 0){
//        		selectDiceTrue(D7);
//            	ans = D7.getFaceValue();
//            	selectDiceFalse(D1);
//            	selectDiceFalse(D2);
//            	selectDiceFalse(D3);
//            	selectDiceFalse(D4);
//            	selectDiceFalse(D5);
//            	selectDiceFalse(D6);
//            	selectDiceFalse(D8);
//            }
//            else{
//            	selectDiceFalse(D7);
//            	ans = 0; 	
//            }        
//        }
//    }
    
//    private class D8Listener implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//        	
//        	if(D8.getHoldState() == false && roll > 0){
//        		selectDiceTrue(D8);
//            	ans = D8.getFaceValue();
//            	selectDiceFalse(D1);
//            	selectDiceFalse(D2);
//            	selectDiceFalse(D3);
//            	selectDiceFalse(D4);
//            	selectDiceFalse(D5);
//            	selectDiceFalse(D6);
//            	selectDiceFalse(D7);
//            }
//            else{
//            	selectDiceFalse(D8);
//            	ans = 0; 	
//            }        
//        }
//    }

    private class D1Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	   if(D1.getHoldState() == false){
        		   	selectDiceTrue(D1);
	            	ans = D1.getFaceValue();
	            	selectDiceFalse(D7);
	            	selectDiceFalse(D2);
	            	selectDiceFalse(D3);
	            	selectDiceFalse(D4);
	            	selectDiceFalse(D5);
	            	selectDiceFalse(D6);
	            	selectDiceFalse(D8);
	            }
	            else{
	            	selectDiceFalse(D1);
	            	ans = 0;
	            }        
        }
    }
    
    private class D2Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	   if(D2.getHoldState() == false){
        		    selectDiceTrue(D2);
	            	ans = D2.getFaceValue();
	            	selectDiceFalse(D7);
	            	selectDiceFalse(D1);
	            	selectDiceFalse(D3);
	            	selectDiceFalse(D4);
	            	selectDiceFalse(D5);
	            	selectDiceFalse(D6);
	            	selectDiceFalse(D8);
	            }
	            else{
	            	selectDiceFalse(D2);
	            	ans = 0;
	            }        
        }
    }
    
    private class D3Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	   if(D3.getHoldState() == false){
        		    selectDiceTrue(D3);
	            	ans = D3.getFaceValue();
	            	selectDiceFalse(D7);
	            	selectDiceFalse(D2);
	            	selectDiceFalse(D1);
	            	selectDiceFalse(D4);
	            	selectDiceFalse(D5);
	            	selectDiceFalse(D6);
	            	selectDiceFalse(D8);
	            }
	            else{
	            	selectDiceFalse(D3);
	            	ans = 0;
	            }        
        }
    }
    
    private class D4Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	   if(D4.getHoldState() == false){
        		    selectDiceTrue(D4);
	            	ans = D4.getFaceValue();
	            	selectDiceFalse(D7);
	            	selectDiceFalse(D2);
	            	selectDiceFalse(D3);
	            	selectDiceFalse(D1);
	            	selectDiceFalse(D5);
	            	selectDiceFalse(D6);
	            	selectDiceFalse(D8);
	            }
	            else{
	            	selectDiceFalse(D4);
	            	ans = 0;
	            }        
        }
    }
    
    private class D5Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	   if(D5.getHoldState() == false){
        		    selectDiceTrue(D5);
	            	ans = D5.getFaceValue();
	            	selectDiceFalse(D7);
	            	selectDiceFalse(D2);
	            	selectDiceFalse(D3);
	            	selectDiceFalse(D4);
	            	selectDiceFalse(D1);
	            	selectDiceFalse(D6);
	            	selectDiceFalse(D8);
	            }
	            else{
	            	selectDiceFalse(D5);
	            	ans = 0;
	            }        
        }
    }
    
    private class D6Listener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	   if(D6.getHoldState() == false){
        		   	selectDiceTrue(D6);
	            	ans = D6.getFaceValue();
	            	selectDiceFalse(D7);
	            	selectDiceFalse(D2);
	            	selectDiceFalse(D3);
	            	selectDiceFalse(D4);
	            	selectDiceFalse(D5);
	            	selectDiceFalse(D1);
	            	selectDiceFalse(D8);
	            }
	            else{
	            	selectDiceFalse(D6);
	            	ans = 0;
	            }        
        }
    }
      
}