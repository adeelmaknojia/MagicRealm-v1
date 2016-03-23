package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class Die extends JButton{
    //CONSTANTS
    private static final int SPOT_DIAM = 6;  // Diameter of spots
    private int _faceValue;     // value that shows on face of die
    private boolean holdState;  // check if die is on hold
    private int [] diceCollection;
    private int maxDice = 8;


    public int getFaceValue(){return this._faceValue;}
    // CONSTRUCTOR
    public Die() {
        //-- Preferred size is set, but layout may change it.
        setPreferredSize(new Dimension(40,40));
        roll();  // Set to random initial value
        holdState = false;
    }
    
    public Die(int faceValue) {
        //-- Preferred size is set, but layout may change it.
        setPreferredSize(new Dimension(40,40));
        setValue(faceValue);
        holdState = false;
    }
    
   
    // ROLL METHOD (return random number for the die)
    public int roll() {
    	int val = 0;
    	if( !holdState){
            val = (int)(6*Math.random() + 1);   // Range 1-6
        	setValue(val);
    	}
    	return this.getValue(); // will return hold value of the dice
    }
    
    // Return value of the die
    public int getValue() {
        return _faceValue;
    }

    // Set value of die to paint
    public void setValue(int spots) {
        _faceValue = spots;
        repaint();    // Value has changed, must repaint
    }
    
    // return the holdState of die (i.e. die is hold for Re-roll or not)
    public boolean getHoldState(){
    	return holdState;
    }
    
    // set the holdState of the die when die is clicked
    public void setHoldState(boolean flag){
    	holdState = flag;
    }
    
    //==================================================== method paintComponent
    /** Draws spots of die face. */
    @Override public void paintComponent(Graphics g) {
        int w = getWidth();  // Get height and width
        int h = getHeight();
        
        //... Change to Graphic2D for smoother spots.
        Graphics2D g2 = (Graphics2D)g;  // See note below
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        //... Paint background
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, w, h);
        g2.setColor(Color.BLACK);
        
        g2.drawRect(0, 0, w-1, h-1);  // Draw border
        
        switch (_faceValue) {
            case 1:
                drawSpot(g2, w/2, h/2);
                break;
            case 3:
                drawSpot(g2, w/2, h/2);
                // Fall thru to next case
            case 2:
                drawSpot(g2, w/4, h/4);
                drawSpot(g2, 3*w/4, 3*h/4);
                break;
            case 5:
                drawSpot(g2, w/2, h/2);
                // Fall thru to next case
            case 4:
                drawSpot(g2, w/4, h/4);
                drawSpot(g2, 3*w/4, 3*h/4);
                drawSpot(g2, 3*w/4, h/4);
                drawSpot(g2, w/4, 3*h/4);
                break;
            case 6:
                drawSpot(g2, w/4, h/4);
                drawSpot(g2, 3*w/4, 3*h/4);
                drawSpot(g2, 3*w/4, h/4);
                drawSpot(g2, w/4, 3*h/4);
                drawSpot(g2, w/4, h/2);
                drawSpot(g2, 3*w/4, h/2);
                break;
        }
    }
    
    //========================================================== method drawSpot
    /** Utility method used by paintComponent(). */
    private void drawSpot(Graphics2D g2, int x, int y) {
        g2.fillOval(x-SPOT_DIAM/2, y-SPOT_DIAM/2, SPOT_DIAM, SPOT_DIAM);
    }


    

}