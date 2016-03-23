package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;


public class SetupGUI {

	TreasureSetUpCard tsc = new TreasureSetUpCard(null, null);
	JPanel sidePanel = new JPanel();
	private JFrame frame;

	/***** FOR GARRISON ********/
	JLabel lblImgs [] = new JLabel[tsc.garrisons.getHouse().size()];
	Image tempImg;
	Image newimg;

	/****** FOR LOST CASTLE *******/
	int LostCastleSize = 6;
	JLabel lblLC_Imgs[] = new JLabel[LostCastleSize];
	Image LC_Img;
	Image newLC_img;
	
	/****** FOR LOST CASTLE *******/
	int LostCitySize = 6;
	JLabel lblLCT_Imgs[] = new JLabel[LostCitySize];
	Image LCT_Img;
	Image newLCT_img;
	
	/****** FOR TREASURE LOCATION *******/
	int TreasureLocationSize = 11;
	JLabel lblTL_Imgs[] = new JLabel[TreasureLocationSize];
	Image TL_Img;
	Image newTL_img;

	// Getters
	public JLabel[] getLblImgs() {return lblImgs;}
	public JLabel[] getLblLC_Imgs() {return lblLC_Imgs;}
	public JLabel[] getLblLCT_Imgs() {return lblLCT_Imgs;}
	public JLabel[] getLblTL_Imgs() {return lblTL_Imgs;}
	public JFrame getFrame(){return this.frame;}


	/**
	 * Launch the application.
	 */
	//	public static void main(String[] args) {
	//		EventQueue.invokeLater(new Runnable() {
	//			public void run() {
	//				try {
	//					SetupGUI window = new SetupGUI();
	//					window.frame.setVisible(true);
	//				} catch (Exception e) {
	//					e.printStackTrace();
	//				}
	//			}
	//		});
	//	}

	/**
	 * Create the application.
	 */


	// CONSTRUCTOR
	public SetupGUI() {
		for(int i=0; i< tsc.garrisons.getHouse().size();i++){
			lblImgs[i] = new JLabel("");
		}

		for(int i=0; i < LostCastleSize;i++){
			lblLC_Imgs[i] = new JLabel("");
		}
		
		for(int i=0; i < LostCitySize;i++){
			lblLCT_Imgs[i] = new JLabel("");
		}
		
		for(int i=0; i < TreasureLocationSize;i++){
			lblTL_Imgs[i] = new JLabel("");
		}
		initialize();
	}

	/***********************************************************************************************************/	

	// HELPER FUNCTION TO GET DWELLIGNS ITEMS

	// CHAPEL
	public JLabel[] displayChapel(){
		for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
			lblImgs[j].setIcon(null);
		}
		
		for(int i=0; i < LostCitySize;i++){
			lblLCT_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < LostCastleSize;i++){
			lblLC_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < TreasureLocationSize;i++){
			lblTL_Imgs[i].setIcon(null); 
		}

		for(int i=0; i < tsc.garrisons.getChapel().size(); i++){
			tempImg = tsc.garrisons.getChapel().get(i).getImage();
			newimg = tempImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH );
			lblImgs[i].setIcon(new ImageIcon(newimg));
		}
		return lblImgs;
	}

	// GUARD
	public JLabel[] displayGuard(){
		for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
			lblImgs[j].setIcon(null);
		}
		
		for(int i=0; i < LostCitySize;i++){
			lblLCT_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < LostCastleSize;i++){
			lblLC_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < TreasureLocationSize;i++){
			lblTL_Imgs[i].setIcon(null); 
		}

		for(int i=0; i < tsc.garrisons.getGuard().size(); i++){
			tempImg = tsc.garrisons.getGuard().get(i).getImage();
			newimg = tempImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH );
			lblImgs[i].setIcon(new ImageIcon(newimg));
		}

		return lblImgs;
	}

	//INN
	public JLabel[] displayInn(){
		for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
			lblImgs[j].setIcon(null);
		}
		
		for(int i=0; i < LostCitySize;i++){
			lblLCT_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < LostCastleSize;i++){
			lblLC_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < TreasureLocationSize;i++){
			lblTL_Imgs[i].setIcon(null); 
		}

		for(int i=0; i < tsc.garrisons.getInn().size(); i++){
			tempImg = tsc.garrisons.getInn().get(i).getImage();
			newimg = tempImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH );
			lblImgs[i].setIcon(new ImageIcon(newimg));
		}

		return lblImgs;
	}

	//HOUSE
	public JLabel[] displayHouse(){
		for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
			lblImgs[j].setIcon(null);
		}
		
		for(int i=0; i < LostCitySize;i++){
			lblLCT_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < LostCastleSize;i++){
			lblLC_Imgs[i].setIcon(null); 
		}
		
		for(int i=0; i < TreasureLocationSize;i++){
			lblTL_Imgs[i].setIcon(null); 
		}

		for(int i=0; i < tsc.garrisons.getHouse().size(); i++){
			tempImg = tsc.garrisons.getHouse().get(i).getImage();
			newimg = tempImg.getScaledInstance( 50, 50,  java.awt.Image.SCALE_SMOOTH );
			lblImgs[i].setIcon(new ImageIcon(newimg));
		}

		return lblImgs;
	}

	/**************************************************************************************/

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(260, 0, 1100, 740);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(sidePanel);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		JPanel panel_background = new JPanel();
		panel_background.setLayout(null);
		JLabel background=new JLabel(new ImageIcon("./images/SetupCard/blanksetup.gif"));
		background.setBounds(-9, 5, 915, 701);
		panel_background.add(background);
		panel_background.setBounds(0, 0, 915, 701);
		frame.getContentPane().add(panel_background);

		JButton btnViewChapel = new JButton("View Chapel");
		btnViewChapel.setBounds(604, 508, 146, 115);
		btnViewChapel.setOpaque(false);
		btnViewChapel.setContentAreaFilled(false);
		btnViewChapel.setBorderPainted(true);
		panel_background.add(btnViewChapel);


		JButton btnViewhouse = new JButton("View House");
		btnViewhouse.setBounds(759, 509, 132, 114);
		btnViewhouse.setOpaque(false);
		btnViewhouse.setContentAreaFilled(false);
		panel_background.add(btnViewhouse);

		JButton btnViewinn = new JButton("View Inn");
		btnViewinn.setBounds(604, 633, 146, 64);
		btnViewinn.setOpaque(false);
		btnViewinn.setContentAreaFilled(false);
		panel_background.add(btnViewinn);

		JButton btnViewguard = new JButton("View Guard");
		btnViewguard.setBounds(759, 634, 132, 63);
		btnViewguard.setOpaque(false);
		btnViewguard.setContentAreaFilled(false);
		panel_background.add(btnViewguard);


		JButton btnSmokeM = new JButton("");
		btnSmokeM.setBounds(51, 232, 57, 56);
		btnSmokeM.setOpaque(false);
		btnSmokeM.setContentAreaFilled(false);
		panel_background.add(btnSmokeM);
		
		JButton btnDankw = new JButton("");
		btnDankw.setBounds(51, 315, 46, 47);
		btnDankw.setOpaque(false);
		btnDankw.setContentAreaFilled(false);
		panel_background.add(btnDankw);

		JButton btnRuinsw = new JButton("");
		btnRuinsw.setBounds(51, 395, 46, 47);
		btnRuinsw.setOpaque(false);
		btnRuinsw.setContentAreaFilled(false);
		panel_background.add(btnRuinsw);


		JButton btnBonesw = new JButton();
		btnBonesw.setBounds(107, 395, 51, 47);
		btnBonesw.setOpaque(false);
		btnBonesw.setContentAreaFilled(false);
		panel_background.add(btnBonesw);

		JButton btnGTrolls1 = new JButton("");
		btnGTrolls1.setBounds(46, 477, 66, 64);
		btnGTrolls1.setOpaque(false);
		btnGTrolls1.setContentAreaFilled(false);
		panel_background.add(btnGTrolls1);

		JButton btnGTrolls2 = new JButton("");
		btnGTrolls2.setOpaque(false);
		btnGTrolls2.setContentAreaFilled(false);
		btnGTrolls2.setBounds(114, 477, 66, 64);
		panel_background.add(btnGTrolls2);
		
		JButton btnSpider1 = new JButton("");
		btnSpider1.setBounds(51, 560, 57, 56);
		btnSpider1.setOpaque(false);
		btnSpider1.setContentAreaFilled(false);
		panel_background.add(btnSpider1);

		JButton btnSpider2 = new JButton("");
		btnSpider2.setOpaque(false);
		btnSpider2.setContentAreaFilled(false);
		btnSpider2.setBounds(107, 560, 57, 56);
		panel_background.add(btnSpider2);

		JButton btnSpider3 = new JButton("");
		btnSpider3.setOpaque(false);
		btnSpider3.setContentAreaFilled(false);
		btnSpider3.setBounds(164, 560, 57, 56);
		panel_background.add(btnSpider3);

		JButton btnBat1 = new JButton("");
		btnBat1.setBounds(51, 641, 57, 56);
		btnBat1.setOpaque(false);
		btnBat1.setContentAreaFilled(false);
		panel_background.add(btnBat1);

		JButton btnBat2 = new JButton("");
		btnBat2.setBounds(107, 641, 57, 56);
		btnBat2.setOpaque(false);
		btnBat2.setContentAreaFilled(false);
		panel_background.add(btnBat2);

		JButton btnBat3 = new JButton("");
		btnBat3.setBounds(164, 640, 57, 57);
		btnBat3.setOpaque(false);
		btnBat3.setContentAreaFilled(false);
		panel_background.add(btnBat3);

		JButton btnLcdragon1 = new JButton("");
		btnLcdragon1.setBounds(122, 232, 57, 56);
		btnLcdragon1.setOpaque(false);
		btnLcdragon1.setContentAreaFilled(false);
		panel_background.add(btnLcdragon1);

		JButton btnLcdragon2 = new JButton("");
		btnLcdragon2.setBounds(180, 232, 66, 64);
		btnLcdragon2.setOpaque(false);
		btnLcdragon2.setContentAreaFilled(false);
		panel_background.add(btnLcdragon2);

		JButton btnLCSerpent1 = new JButton("");
		btnLCSerpent1.setBounds(122, 315, 57, 56);
		btnLCSerpent1.setOpaque(false);
		btnLCSerpent1.setContentAreaFilled(false);
		panel_background.add(btnLCSerpent1);

		JButton btnLCSerpent2 = new JButton("");
		btnLCSerpent2.setBounds(181, 315, 66, 64);
		btnLCSerpent2.setOpaque(false);
		btnLCSerpent2.setContentAreaFilled(false);
		panel_background.add(btnLCSerpent2);

		JButton btnLcgoblin1 = new JButton("");
		btnLcgoblin1.setBounds(173, 397, 46, 45);
		btnLcgoblin1.setOpaque(false);
		btnLcgoblin1.setContentAreaFilled(false);
		panel_background.add(btnLcgoblin1);

		JButton btnLcgoblin2 = new JButton("");
		btnLcgoblin2.setBounds(223, 397, 46, 45);
		btnLcgoblin2.setOpaque(false);
		btnLcgoblin2.setContentAreaFilled(false);
		panel_background.add(btnLcgoblin2);

		JButton btnLcgoblin3 = new JButton("");
		btnLcgoblin3.setBounds(272, 397, 46, 45);
		btnLcgoblin3.setOpaque(false);
		btnLcgoblin3.setContentAreaFilled(false);
		panel_background.add(btnLcgoblin3);

		JButton btnLCTrolls1 = new JButton("");
		btnLCTrolls1.setBounds(198, 477, 55, 56);
		btnLCTrolls1.setOpaque(false);
		btnLCTrolls1.setContentAreaFilled(false);
		panel_background.add(btnLCTrolls1);

		JButton btnLCTrolls2 = new JButton("");
		btnLCTrolls2.setBounds(255, 477, 66, 64);
		btnLCTrolls2.setOpaque(false);
		btnLCTrolls2.setContentAreaFilled(false);
		panel_background.add(btnLCTrolls2);

		JButton btnHoard = new JButton("");
		btnHoard.setBounds(263, 232, 80, 65);
		btnHoard.setOpaque(false);
		btnHoard.setContentAreaFilled(false);
		panel_background.add(btnHoard);

		JButton btnLair = new JButton("");
		btnLair.setBounds(353, 232, 80, 65);
		btnLair.setOpaque(false);
		btnLair.setContentAreaFilled(false);
		panel_background.add(btnLair);

		JButton btnAltar = new JButton("");
		btnAltar.setBounds(263, 315, 80, 65);
		btnAltar.setOpaque(false);
		btnAltar.setContentAreaFilled(false);
		panel_background.add(btnAltar);

		JButton btnShrine = new JButton("");
		btnShrine.setBounds(353, 315, 80, 65);
		btnShrine.setOpaque(false);
		btnShrine.setContentAreaFilled(false);
		panel_background.add(btnShrine);

		JButton btnPool = new JButton("");
		btnPool.setBounds(351, 397, 80, 65);
		btnPool.setOpaque(false);
		btnPool.setContentAreaFilled(false);
		panel_background.add(btnPool);

		JButton btnVault = new JButton("");
		btnVault.setBounds(352, 476, 80, 65);
		btnVault.setOpaque(false);
		btnVault.setContentAreaFilled(false);
		panel_background.add(btnVault);

		JButton btnStatue = new JButton("");
		btnStatue.setBounds(353, 560, 80, 65);
		btnStatue.setOpaque(false);
		btnStatue.setContentAreaFilled(false);
		panel_background.add(btnStatue);

		JButton btnCairns = new JButton("");
		btnCairns.setBounds(262, 558, 80, 65);
		btnCairns.setOpaque(false);
		btnCairns.setContentAreaFilled(false);
		panel_background.add(btnCairns);

		// SIDE PANEL
		sidePanel.setBorder(new LineBorder(SystemColor.textHighlight));
		sidePanel.setBounds(909, 0, 185, 701);
		sidePanel.setLayout(null);
		sidePanel.setVisible(false);

		//*******************************************************************************************************************************//
		// ACTION LISTENERS FOR THE GARRISON PANEL

		//Add action listener For VIEW CHAPEL button
		btnViewChapel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				JLabel[] display = displayChapel();

				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int i=0; i < display.length; i++){
						display[i].setBounds(50, ((60*i)+5), 50, 50);
						sidePanel.add(display[i]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
				//btnSetupCard.setVisible(false);
			}
		});


		//Add action listener For VIEW INN btn
		btnViewinn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				JLabel[] display = displayInn();

				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int i=0; i < display.length; i++){
						display[i].setBounds(50, ((60*i)+5), 50, 50);
						sidePanel.add(display[i]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
				//btnSetupCard.setVisible(false);
			}
		});


		//Add action listener For VIEW GUARD btn
		btnViewguard.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				JLabel[] display = displayGuard();

				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int i=0; i < display.length; i++){
						display[i].setBounds(50, ((60*i)+5), 50, 50);
						sidePanel.add(display[i]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
				//btnSetupCard.setVisible(false);
			}
		});

		//Add action listener For VIEW GUARD btn
		btnViewhouse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{   JLabel[] display = displayHouse();

			if( sidePanel.isVisible() == false){
				sidePanel.setVisible(true);
				for(int i=0; i < display.length-1; i++){
					display[i].setBounds(50, ((51*i)+5), 50, 50);
					sidePanel.add(display[i]);
				}
			}
			else{
				sidePanel.setVisible(false);
			}

			sidePanel.setBackground(Color.WHITE);
			//btnSetupCard.setVisible(false);
			}
		});

		//********************************************LOST CASTLE****************************************************//

		btnSmokeM.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					LC_Img = tsc.lostCastle.getRollOne1().getImage();
					newLC_img = LC_Img.getScaledInstance( 92, 92,  java.awt.Image.SCALE_SMOOTH );
					lblLC_Imgs[0].setBounds(50, 20, 92, 92);
					lblLC_Imgs[0].setIcon(new ImageIcon(newLC_img));
					sidePanel.add(lblLC_Imgs[0]);

					LC_Img = tsc.lostCastle.getRollOne2().getImage();
					newLC_img = LC_Img.getScaledInstance( 92, 92,  java.awt.Image.SCALE_SMOOTH );
					lblLC_Imgs[1].setBounds(50, 120, 92, 92);
					lblLC_Imgs[1].setIcon(new ImageIcon(newLC_img));
					sidePanel.add(lblLC_Imgs[1]);


				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnDankw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					LC_Img = tsc.lostCastle.getRollTwo1().getImage();
					newLC_img = LC_Img.getScaledInstance( 76, 76,  java.awt.Image.SCALE_SMOOTH );
					lblLC_Imgs[0].setBounds(50, 20, 76, 76);
					lblLC_Imgs[0].setIcon(new ImageIcon(newLC_img));
					sidePanel.add(lblLC_Imgs[0]);

					LC_Img = tsc.lostCastle.getRollTwo2().getImage();
					newLC_img = LC_Img.getScaledInstance( 76, 76,  java.awt.Image.SCALE_SMOOTH );
					lblLC_Imgs[1].setBounds(50, 110, 76, 76);
					lblLC_Imgs[1].setIcon(new ImageIcon(newLC_img));
					sidePanel.add(lblLC_Imgs[1]);


				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnRuinsw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 6; j++){
						LC_Img = tsc.lostCastle.getRollThree1().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 76, 76,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (86*j)+20, 76, 76);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnBonesw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 2; j++){
						LC_Img = tsc.lostCastle.getRollThree2().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 76, 76,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (86*j)+20, 76, 76);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnGTrolls1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LC_Img = tsc.lostCastle.getRollFour1().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 103, 103,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (113*j)+20, 103, 103);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnGTrolls2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LC_Img = tsc.lostCastle.getRollFour2().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 103, 103,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (113*j)+20, 103, 103);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnSpider1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LC_Img = tsc.lostCastle.getRollFive1().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnSpider2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LC_Img = tsc.lostCastle.getRollFive2().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnSpider3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LC_Img = tsc.lostCastle.getRollFive3().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});


		btnBat1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LC_Img = tsc.lostCastle.getRollSix1().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});

		btnBat2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 2; j++){
						LC_Img = tsc.lostCastle.getRollSix2().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});

		btnBat3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 3; j++){
						LC_Img = tsc.lostCastle.getRollSix3().get(j).getImage();
						newLC_img = LC_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLC_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLC_Imgs[j].setIcon(new ImageIcon(newLC_img));
						sidePanel.add(lblLC_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		//********************************************LOST CITY****************************************************//
		
		btnLcdragon1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LCT_Img = tsc.lostCity.getRollOne1().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		btnLcdragon2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LCT_Img = tsc.lostCity.getRollOne2().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 100, 100,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (110*j)+20, 100, 100);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnLCSerpent1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 2; j++){
						LCT_Img = tsc.lostCity.getRollTwo1().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 88, 88,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (98*j)+20, 88, 88);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		btnLCSerpent2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LCT_Img = tsc.lostCity.getRollTwo2().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 99, 99,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (109*j)+20, 99, 99);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		btnLcgoblin1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 6; j++){
						LCT_Img = tsc.lostCity.getRollThree1().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 74, 74,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (84*j)+20, 74, 74);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnLcgoblin2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 6; j++){
						LCT_Img = tsc.lostCity.getRollThree2().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 76, 76,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (86*j)+20, 76, 76);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnLcgoblin3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 6; j++){
						LCT_Img = tsc.lostCity.getRollThree3().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 73, 73,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (83*j)+20, 73, 73);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		
		btnLCTrolls1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 2; j++){
						LCT_Img = tsc.lostCity.getRollFour1().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 87, 87,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (97*j)+20, 87, 87);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnLCTrolls2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					for(int j=0; j < 1; j++){
						LCT_Img = tsc.lostCity.getRollFour2().get(j).getImage();
						newLCT_img = LCT_Img.getScaledInstance( 103, 103,  java.awt.Image.SCALE_SMOOTH );
						lblLCT_Imgs[j].setBounds(50, (113*j)+20, 103, 103);
						lblLCT_Imgs[j].setIcon(new ImageIcon(newLCT_img));
						sidePanel.add(lblLCT_Imgs[j]);
					}
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		//********************************************TREASURE LOCATION****************************************************//
		
		btnHoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollOne1().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance( 95, 95,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 95, 95);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollOne1().get(1).getImage();
					newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[1].setBounds(50, 125, 32, 32);
					lblTL_Imgs[1].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[1]);
					
					for(int j=2; j < 6; j++){
						TL_Img = tsc.treasureLocation.getTreasuresRollOne1().get(j).getImage();
						newTL_img = TL_Img.getScaledInstance( 91, 114,  java.awt.Image.SCALE_SMOOTH );
						lblTL_Imgs[j].setBounds(50, (114*j)-60, 91, 114);
						lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
						sidePanel.add(lblTL_Imgs[j]);
					}		
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnLair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollOne2().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance(100, 103,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 100, 103);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollOne2().get(1).getImage();
					newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[1].setBounds(50, 133, 32, 32);
					lblTL_Imgs[1].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[1]);
					
					for(int j=2; j < 6; j++){
						TL_Img = tsc.treasureLocation.getTreasuresRollOne2().get(j).getImage();
						newTL_img = TL_Img.getScaledInstance( 91, 114,  java.awt.Image.SCALE_SMOOTH );
						lblTL_Imgs[j].setBounds(50, (114*j)-60, 91, 114);
						lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
						sidePanel.add(lblTL_Imgs[j]);
					}		
				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnAltar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollTwo1().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance(99, 99,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 99, 99);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
				for(int j=1; j < 3; j++){
					TL_Img = tsc.treasureLocation.getTreasuresRollTwo1().get(j).getImage();
					newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[j].setBounds(50, (42*j)+ 90, 32, 32);
					lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[j]);
				}
					

				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnShrine.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					
					TL_Img = tsc.treasureLocation.getTreasuresRollTwo2().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance(100, 101,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 100, 101);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollTwo2().get(1).getImage();
					newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[1].setBounds(50, 131, 32, 32);
					lblTL_Imgs[1].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[1]);
					
				for(int j=2; j < 4; j++){
					TL_Img = tsc.treasureLocation.getTreasuresRollTwo2().get(j).getImage();
					newTL_img = TL_Img.getScaledInstance( 91, 114,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[j].setBounds(50, (114*j)-60, 91, 114);
					lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[j]);
				}

				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		btnPool.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					
					TL_Img = tsc.treasureLocation.getTreasuresRollThree().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance(100, 101,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 100, 101);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollThree().get(1).getImage();
					newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[1].setBounds(50, 131, 32, 32);
					lblTL_Imgs[1].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[1]);
					
				for(int j=2; j < 4; j++){
					TL_Img = tsc.treasureLocation.getTreasuresRollThree().get(j).getImage();
					newTL_img = TL_Img.getScaledInstance( 91, 114,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[j].setBounds(50, (114*j)-60, 91, 114);
					lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[j]);
				}

				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		btnVault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					
					TL_Img = tsc.treasureLocation.getTreasuresRollFour().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance(100, 101,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 100, 101);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
					for(int j=1; j < 3; j++){
						TL_Img = tsc.treasureLocation.getTreasuresRollFour().get(j).getImage();
						newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
						lblTL_Imgs[j].setBounds(50, (42*j)+ 90, 32, 32);
						lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
						sidePanel.add(lblTL_Imgs[j]);
					}

				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnCairns.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					
					TL_Img = tsc.treasureLocation.getTreasuresRollFive1().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance(100, 101,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 100, 101);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollFive1().get(1).getImage();
					newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[1].setBounds(50, 131, 32, 32);
					lblTL_Imgs[1].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[1]);
					
				for(int j=2; j < 8; j++){
					TL_Img = tsc.treasureLocation.getTreasuresRollFive1().get(j).getImage();
					newTL_img = TL_Img.getScaledInstance( 51, 74,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[j].setBounds(50, (84*j), 51, 74);
					lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[j]);
				}

				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
		
		
		btnStatue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{  
				for (int j=0; j < tsc.garrisons.getHouse().size(); j++){
					lblImgs[j].setIcon(null);
				}

				for(int i=0; i < LostCitySize;i++){
					lblLCT_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < LostCastleSize;i++){
					lblLC_Imgs[i].setIcon(null); 
				}
				
				for(int i=0; i < TreasureLocationSize;i++){
					lblTL_Imgs[i].setIcon(null); 
				}
				
				if( sidePanel.isVisible() == false){
					sidePanel.setVisible(true);
					
					
					TL_Img = tsc.treasureLocation.getTreasuresRollFive2().get(0).getImage();
					newTL_img = TL_Img.getScaledInstance(77, 76,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[0].setBounds(50, 20, 77, 76);
					lblTL_Imgs[0].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[0]);
					
					TL_Img = tsc.treasureLocation.getTreasuresRollFive2().get(1).getImage();
					newTL_img = TL_Img.getScaledInstance( 32, 32,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[1].setBounds(50, 105, 32, 32);
					lblTL_Imgs[1].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[1]);
					
				for(int j=2; j < 4; j++){
					TL_Img = tsc.treasureLocation.getTreasuresRollFive2().get(j).getImage();
					newTL_img = TL_Img.getScaledInstance( 91, 114,  java.awt.Image.SCALE_SMOOTH );
					lblTL_Imgs[j].setBounds(50, (114*j)-60, 91, 114);
					lblTL_Imgs[j].setIcon(new ImageIcon(newTL_img));
					sidePanel.add(lblTL_Imgs[j]);
				}

				}
				else{
					sidePanel.setVisible(false);
				}

				sidePanel.setBackground(Color.WHITE);
			}
		});
	}
}
