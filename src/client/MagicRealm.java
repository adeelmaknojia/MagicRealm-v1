package client;


import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import main.SoundClip;
import java.awt.Font;
import javax.swing.JProgressBar;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MagicRealm {

	static JFrame frame;
	static JProgressBar progressBar;
	static JButton btnStartGame;
	static Boolean ispressed = false;
	
	public boolean getIspressed(){ return this.ispressed;}
	public void setIspressed(boolean v){this.ispressed = v;}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MagicRealm window = new MagicRealm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public MagicRealm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(350, 10, 600,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		JPanel panel_background = new JPanel();
		panel_background.setLayout(null);
		JLabel background=new JLabel(new ImageIcon("./images/Magic-Realm-cover.png"));
		background.setFont(new Font("Arial Narrow", Font.BOLD, 15));
		background.setBounds(0, 0, 600, 673);
		panel_background.add(background);
		panel_background.setBounds(0, 0, 600, 700);
		frame.getContentPane().add(panel_background);
		
		btnStartGame = new JButton("Start Game");
		btnStartGame.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		btnStartGame.setBounds(150, 627, 117, 31);
		btnStartGame.setEnabled(false);
		btnStartGame.addActionListener(sBtnlistener); //Add the button's action listener
		panel_background.add(btnStartGame);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(63, 580, 478, 23);
		progressBar.setVisible(true);
		
		btnQuit = new JButton("Quit Game");
		btnQuit.setFont(new Font("Arial Narrow", Font.BOLD, 14));
		btnQuit.setEnabled(true);
		btnQuit.setBounds(320, 627, 117, 31);
		btnQuit.addActionListener(qBtnlistener); //Add the button's action listener
		panel_background.add(btnQuit);
		
		panel_background.add(progressBar);
		new Thread(new thread1()).start(); //Start the thread
	}
	
	//The action
	ActionListener sBtnlistener = new ActionListener() {
	        public void actionPerformed (ActionEvent e){
	        	
	        	setIspressed(true);
	        	frame.dispose(); // disposing game start frame	
	        	new ClientGUI("localhost", 1500);
	        }
	 };
	    
	    
		//The action
		ActionListener qBtnlistener = new ActionListener() {
		        public void actionPerformed (ActionEvent e){
		        	
		        	// Close the game start menue
		        	System.exit(0);
		        }
		};
	private JButton btnQuit;

	    //The thread
    public static class thread1 implements Runnable{
        public void run(){
        	int i ;
        	SoundClip sound = new SoundClip();
        	sound.start();
            for (i=0; i<=100; i++){ //Progressively increment variable i
                progressBar.setValue(i); //Set value
                progressBar.repaint(); //Refresh graphics
                try{Thread.sleep(100);} //Sleep 100 milliseconds
                catch (InterruptedException err){}
            } 
            if(progressBar.getValue() == 100){
            	btnStartGame.setVisible(true);
              btnStartGame.setEnabled(true); 
            }
        }
    }
}
