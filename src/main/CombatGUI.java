package main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import client.ClientGUI;
import messaging.MRMessage;

public class CombatGUI {

	private JFrame frame;
	private int strengthUsed = 0;
	private int round = 0;
	private int done = 1;
	Image tmp_img;
	Image n_img;
	int secondPlayer;
	SoundClip combatSound = new SoundClip();
	Character p1;
	
	public CombatGUI(Character p, int round, int sPlayer) {
		p1 = p;
		this.round = round;
		secondPlayer = sPlayer;
		initialize();
	}


	public int isDone() {
		return done;
	}
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JOptionPane.showMessageDialog(frame, "Round "+ round +"!");
		frame = new JFrame();
		frame.setBounds(100, 0, 595, 748);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		combatSound.combatSong();

		JPanel panel_background = new JPanel();
		panel_background.setLayout(null);
		JLabel background=new JLabel(new ImageIcon("./images/combat/char_melee2.gif"));
		background.setBounds(0, 0, 595, 748);
		panel_background.add(background);
		panel_background.setBounds(0, 0, 595, 748);
		frame.getContentPane().add(panel_background);

		JButton btnCharge = new JButton("");
		btnCharge.setBounds(52, 44, 79, 77);
		//		btnCharge.setOpaque(false);
		//		btnCharge.setContentAreaFilled(false);
		panel_background.add(btnCharge);

		JButton btnDodge = new JButton("");
		btnDodge.setBounds(169, 140, 79, 77);
		//		btnDodge.setOpaque(false);
		//		btnDodge.setContentAreaFilled(false);
		panel_background.add(btnDodge);

		JButton btnDuck = new JButton("");
		btnDuck.setBounds(283, 236, 79, 77);
		//		btnDuck.setOpaque(false);
		//		btnDuck.setContentAreaFilled(false);
		panel_background.add(btnDuck);

		JButton btnAttack1 = new JButton("");
		btnAttack1.setBounds(399, 44, 63, 77);
		//		btnAttack1.setOpaque(false);
		//		btnAttack1.setContentAreaFilled(false);
		panel_background.add(btnAttack1);

		JButton btnAttack2 = new JButton("");
		btnAttack2.setBounds(399, 143, 63, 77);
		//		btnAttack2.setOpaque(false);
		//		btnAttack2.setContentAreaFilled(false);
		panel_background.add(btnAttack2);

		JButton btnAttack3 = new JButton("");
		btnAttack3.setBounds(399, 237, 63, 77);
		//		btnAttack3.setOpaque(false);
		//		btnAttack3.setContentAreaFilled(false);
		panel_background.add(btnAttack3);

		JButton btnShieldTh = new JButton("");
		btnShieldTh.setBounds(42, 372, 98, 77);
		//		btnShieldTh.setOpaque(false);
		//		btnShieldTh.setContentAreaFilled(false);
		panel_background.add(btnShieldTh);

		JButton btnShieldSw = new JButton("");
		btnShieldSw.setBounds(160, 372, 98, 77);
		//		btnShieldSw.setOpaque(false);
		//		btnShieldSw.setContentAreaFilled(false);
		panel_background.add(btnShieldSw);

		JButton btnShieldSm = new JButton("");
		btnShieldSm.setBounds(275, 372, 98, 77);
		//		btnShieldSm.setOpaque(false);
		//		btnShieldSm.setContentAreaFilled(false);
		panel_background.add(btnShieldSm);

		JButton btnBreastpts = new JButton("");
		btnBreastpts.setBounds(42, 468, 216, 77);
		//		btnBreastpts.setOpaque(false);
		//		btnBreastpts.setContentAreaFilled(false);
		panel_background.add(btnBreastpts);

		JButton btnHelmetSm = new JButton("");
		btnHelmetSm.setBounds(277, 468, 98, 77);
		//		btnHelmetSm.setOpaque(false);
		//		btnHelmetSm.setContentAreaFilled(false);
		panel_background.add(btnHelmetSm);

		JButton btnSuitarmor = new JButton("");
		btnSuitarmor.setBounds(42, 566, 331, 77);
		//		btnSuitarmor.setOpaque(false);
		//		btnSuitarmor.setContentAreaFilled(false);
		panel_background.add(btnSuitarmor);

		JButton btnManeuverCharge = new JButton("");
		btnManeuverCharge.setBounds(51, 658, 79, 63);
		//		btnManeuverCharge.setOpaque(false);
		//		btnManeuverCharge.setContentAreaFilled(false);
		panel_background.add(btnManeuverCharge);

		JButton btnManeuverDoge = new JButton("");
		btnManeuverDoge.setBounds(168, 658, 79, 63);
		//		btnManeuverDoge.setOpaque(false);
		//		btnManeuverDoge.setContentAreaFilled(false);
		panel_background.add(btnManeuverDoge);

		JButton btnManeuverDuck = new JButton("");
		btnManeuverDuck.setBounds(284, 658, 79, 63);
		//		btnManeuverDuck.setOpaque(false);
		//		btnManeuverDuck.setContentAreaFilled(false);
		panel_background.add(btnManeuverDuck);

		JButton btnUsedthisround = new JButton("");
		btnUsedthisround.setBounds(418, 387, 153, 135);
		//		btnUsedthisround.setOpaque(false);
		//		btnUsedthisround.setContentAreaFilled(false);
		panel_background.add(btnUsedthisround);

		JButton btnSubmit = new JButton("Submit");
		btnSubmit.setBounds(436, 647, 89, 23);
		panel_background.add(btnSubmit);

		////////////////////////////


		//*******************************************************************************************************************************//
		// ACTION LISTENERS


		//thrust
		btnAttack1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{

				if(p1.getCombatInfo().getCharge() == null) {
					if(p1.getCombatInfo().getThrust() == null) {
						int count = 0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
								count++;
							}
						}

						String[] ac = new String[count];
						int l =0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
								ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
								l++;
							}
						}
						if(count == 0) {
							JOptionPane.showMessageDialog(frame, "No More Chits to Place!");
						} else {
							p1.getCombatInfo().setThrust(chitPlaced(ac, null));
							tmp_img = p1.getCombatInfo().getThrust().getImage();
							n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
							btnAttack1.setIcon(new ImageIcon(n_img));
							strengthUsed += p1.getCombatInfo().getThrust().getEffort();
						}
					}
					else {
						String[] choice = {"Change Chit", "Active Weapon", "Nothing"};
						int n = JOptionPane.showOptionDialog(frame,
								"What do you want to do?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choice,choice[2]);
						if(n == JOptionPane.YES_OPTION){
							p1.getCombatInfo().addActiveChit(p1.getCombatInfo().getThrust());
							strengthUsed -= p1.getCombatInfo().getThrust().getEffort();
							int count = 0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
									count++;
								}
							}
							String[] ac = new String[count+1];
							int l =0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
									ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
									l++;
								}
							}
							ac[count] = "Empty";
							ActionChit r = chitPlaced(ac, p1.getCombatInfo().getThrust());
							if(r != null) {
								p1.getCombatInfo().setSwing(r);
								tmp_img = p1.getCombatInfo().getThrust().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack1.setIcon(null);

								strengthUsed += p1.getCombatInfo().getThrust().getEffort();
								tmp_img = p1.getCombatInfo().getThrust().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
							} else {
								p1.getCombatInfo().setSwing(null);
							}
						} else if (n == JOptionPane.NO_OPTION) {
							if(p1.getCombatInfo().getActiveWeapon() != null) {
								tmp_img = p1.getCombatInfo().getActiveWeapon().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack1.setIcon(new ImageIcon(n_img));

								p1.getCombatInfo().setWeapon(1);
							} else {
								JOptionPane.showMessageDialog(frame, "You do not have any active weapons at the momemnt.");
							}
						} else if (n == JOptionPane.CANCEL_OPTION) {

						}

					}

				}
				else {
					JOptionPane.showMessageDialog(frame, "You already placed a Move chit in Dodge. You can't Fight here!");
				}
			}
		});

		//Swing
		btnAttack2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getDodge() == null) {
					if(p1.getCombatInfo().getSwing() == null) {
						int count = 0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
								count++;
							}
						}

						String[] ac = new String[count];
						int l =0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
								ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
								l++;
							}
						}
						if(count == 0) {
							JOptionPane.showMessageDialog(frame, "No More Chits to Place!");
						} else {
							p1.getCombatInfo().setSwing(chitPlaced(ac, null));
							tmp_img = p1.getCombatInfo().getSwing().getImage();
							n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
							btnAttack2.setIcon(new ImageIcon(n_img));

							strengthUsed += p1.getCombatInfo().getSwing().getEffort();
						}
					}
					else {
						String[] choice = {"Change Chit", "Active Weapon", "Nothing"};
						int n = JOptionPane.showOptionDialog(frame,
								"What do you want to do?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choice,choice[2]);
						if(n == JOptionPane.YES_OPTION){
							p1.getCombatInfo().addActiveChit(p1.getCombatInfo().getSwing());
							//p1.getCombatInfo().setSwing(null);
							strengthUsed -= p1.getCombatInfo().getSwing().getEffort();
							int count = 0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
									count++;
								}
							}
							String[] ac = new String[count+1];
							int l =0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
									ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
									l++;
								}
							}
							ac[count] = "Empty";
							ActionChit temp = p1.getCombatInfo().getSwing();
							System.out.println("I am here: "+p1.getCombatInfo().getSwing());
							ActionChit r = chitPlaced(ac, temp);
							if(r != null) {
								p1.getCombatInfo().setSwing(r);
								tmp_img = p1.getCombatInfo().getSwing().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack2.setIcon(new ImageIcon(n_img));
								strengthUsed += p1.getCombatInfo().getSwing().getEffort();
							}
						} else if (n == JOptionPane.NO_OPTION) {
							if(p1.getCombatInfo().getActiveWeapon() != null) {
								tmp_img = p1.getCombatInfo().getActiveWeapon().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack2.setIcon(new ImageIcon(n_img));

								p1.getCombatInfo().setWeapon(1);
							} else {
								JOptionPane.showMessageDialog(frame, "You do not have any active weapons at the momemnt.");
							}
						} 

					}

				}
				else {
					JOptionPane.showMessageDialog(frame, "You already placed a Move chit in Dodge. You can't Fight here!");
				}

			}
		});

		//smash
		btnAttack3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getDuck() == null) {
					if(p1.getCombatInfo().getSmash() == null) {
						int count = 0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
								count++;
							}
						}

						String[] ac = new String[count];
						int l =0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
								ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
								l++;
							}
						}
						if(count == 0) {
							JOptionPane.showMessageDialog(frame, "No More Chits to Place!");
						} else  {
							p1.getCombatInfo().setSmash(chitPlaced(ac, null));
							if(p1.getCombatInfo().getSmash() != null){
								tmp_img = p1.getCombatInfo().getSmash().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack3.setIcon(new ImageIcon(n_img));
								strengthUsed += p1.getCombatInfo().getSmash().getEffort();
							}
							else {
								tmp_img = null;
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack3.setIcon(new ImageIcon(n_img));
							}
						}
					}
					else {
						String[] choice = {"Change Chit", "Active Weapon", "Nothing"};
						int n = JOptionPane.showOptionDialog(frame,
								"What do you want to do?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choice,choice[2]);
						if(n == JOptionPane.YES_OPTION){
							p1.getCombatInfo().addActiveChit(p1.getCombatInfo().getSmash());
							//p1.getCombatInfo().setSmash(null);
							strengthUsed -= p1.getCombatInfo().getSmash().getEffort();
							int count = 0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
									count++;
								}
							}
							String[] ac = new String[count+1];
							int l =0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "FIGHT") {
									ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
									l++;
								}
							}
							ac[count] = "Empty";
							ActionChit r = chitPlaced(ac, p1.getCombatInfo().getSmash());
							if(r != null) {
								p1.getCombatInfo().setSmash(r);
								tmp_img = p1.getCombatInfo().getSmash().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack3.setIcon(new ImageIcon(n_img));
								strengthUsed += p1.getCombatInfo().getSmash().getEffort();
							}
						} else if (n == JOptionPane.NO_OPTION) {
							if(p1.getCombatInfo().getActiveWeapon() != null) {
								tmp_img = p1.getCombatInfo().getActiveWeapon().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnAttack3.setIcon(new ImageIcon(n_img));
								p1.getCombatInfo().setWeapon(1);
							} else {
								JOptionPane.showMessageDialog(frame, "You do not have any active weapons at the momemnt.");
							};
						} 

					}

				}
				else {
					JOptionPane.showMessageDialog(frame, "You already placed a Move chit in Duck. You can't Fight here!");
				}
			}
		});



		/************************************************************************/
		btnCharge.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{

				System.out.println("CHARGE Button is pressed");
				if(p1.getCombatInfo().getEChargeThrust() != null) {
					tmp_img = p1.getCombatInfo().getEChargeThrust().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnCharge.setIcon(new ImageIcon(n_img));

				}
			}
		});


		btnDodge.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getEDodgeSwing() != null) {

					tmp_img = p1.getCombatInfo().getEDodgeSwing().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnDodge.setIcon(new ImageIcon(n_img));


				}
			}
		});


		btnDuck.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getEDuckSmash() != null) {
					tmp_img = p1.getCombatInfo().getEDuckSmash().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnDuck.setIcon(new ImageIcon(n_img));
				}
			}
		});

		/***********************************************************************/

		btnShieldSm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Shield Smash Button is pressed");
				if(p1.getCombatInfo().getShields().size() >0) {
					p1.getCombatInfo().setShieldSmash(p1.getCombatInfo().removeShield(0));
					tmp_img = p1.getCombatInfo().getShieldSmash().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnShieldSm.setIcon(new ImageIcon(n_img));
				}
				else {
					JOptionPane.showMessageDialog(frame, "You dont own enough Shields to place one here.");
				}

			}
		});


		btnShieldSw.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getShields().size() >0) {
					p1.getCombatInfo().setShieldSwing(p1.getCombatInfo().removeShield(0));
					tmp_img = p1.getCombatInfo().getShieldSwing().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnShieldSw.setIcon(new ImageIcon(n_img));
				}
				else {
					JOptionPane.showMessageDialog(frame, "You dont own enough Shields to place one here.");
				}
			}
		});


		btnShieldTh.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getShields().size() >0) {
					p1.getCombatInfo().setShieldThrust(p1.getCombatInfo().removeShield(0));
					tmp_img = p1.getCombatInfo().getShieldThrust().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnShieldTh.setIcon(new ImageIcon(n_img));
				}
				else {
					JOptionPane.showMessageDialog(frame, "You dont own enough Shields to place one here.");
				}
			}
		});

		/***********************************************************************/

		btnBreastpts.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				boolean check = false;
				int loc = 0;
				System.out.println("BreastPlate Button is pressed");
				for(int i = 0; i < p1.getArmor().size(); i++) {
					if(p1.getArmor().get(i).getName() == "BREASTPLATE") {
						check = true;
						loc = i;
					}
				}

				if(check == true) {
					p1.getCombatInfo().setBreastPlate(p1.getArmor().get(loc));
					tmp_img = p1.getCombatInfo().getBreastPlate().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnBreastpts.setIcon(new ImageIcon(n_img));

				}
				else {
					JOptionPane.showMessageDialog(frame, "You dont have any Breast Plates to place here.");
				}
			}
		});


		btnHelmetSm.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Helmet Button is pressed");
				boolean check = false;
				int loc = 0;
				for(int i = 0; i < p1.getArmor().size(); i++) {
					if(p1.getArmor().get(i).getName() == "HELMET") {
						check = true;
						loc = i;
						break;
					}
				}

				if(check == true) {
					p1.getCombatInfo().setHelmet(p1.getArmor().get(loc));
					tmp_img = p1.getCombatInfo().getHelmet().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnHelmetSm.setIcon(new ImageIcon(n_img));
				}
				else {
					JOptionPane.showMessageDialog(frame, "You dont have any Helmets to place here.");
				}

			}
		});


		btnSuitarmor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Suit of Armor Button is pressed");
				boolean check = false;
				int loc = 0;
				for(int i = 0; i < p1.getArmor().size(); i++) {
					if(p1.getArmor().get(i).getName() == "SUIT OF ARMOR") {
						check = true;
						loc = i;
						break;
					}
				}

				if(check == true) {
					p1.getCombatInfo().setSuitOfArmor(p1.getArmor().get(loc));
					tmp_img = p1.getCombatInfo().getSuitOfArmor().getImage();
					n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
					btnSuitarmor.setIcon(new ImageIcon(n_img));

				}
				else {
					JOptionPane.showMessageDialog(frame, "You dont have any Suit of Armor to place here.");
				}
			}
		});

		/***********************************************************************/

		btnManeuverCharge.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getThrust() == null) {
					if(p1.getCombatInfo().getCharge() == null) {
						int count = 0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
								count++;
							}
						}

						String[] ac = new String[count];
						int l =0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
								ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
								l++;
							}
						}
						if(count == 0) {
							JOptionPane.showMessageDialog(frame, "No More Chits to Place!");
						} else {
							ActionChit r = chitPlaced(ac, null);
							if(r != null) {
								p1.getCombatInfo().setCharge(r);
								tmp_img = p1.getCombatInfo().getCharge().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnManeuverCharge.setIcon(new ImageIcon(n_img));

								strengthUsed += p1.getCombatInfo().getCharge().getEffort();
							}
						}
					}
					else {
						String[] choice = {"Change Chit", "Nothing"};
						int n = JOptionPane.showOptionDialog(frame,
								"What do you want to do?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choice,choice[2]);
						if(n == JOptionPane.YES_OPTION){
							p1.getCombatInfo().addActiveChit(p1.getCombatInfo().getCharge());
							//p1.getCombatInfo().setCharge(null);
							strengthUsed -= p1.getCombatInfo().getCharge().getEffort();
							int count = 0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
									count++;
								}
							}
							String[] ac = new String[count+1];
							int l =0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
									ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
									l++;
								}
							}
							ac[count] = "Empty";
							ActionChit r = chitPlaced(ac, p1.getCombatInfo().getCharge());
							if(r != null) {
								p1.getCombatInfo().setCharge(r);
								tmp_img = p1.getCombatInfo().getCharge().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnManeuverCharge.setIcon(new ImageIcon(n_img));

								strengthUsed += p1.getCombatInfo().getCharge().getEffort();

							}
						}

					}

				}
				else {
					JOptionPane.showMessageDialog(frame, "You already placed a Fight chit in Thrust. You can't Move here!");
				}
			}
		});


		btnManeuverDoge.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getSwing() == null) {
					if(p1.getCombatInfo().getDodge() == null) {
						int count = 0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
								count++;
							}
						}

						String[] ac = new String[count];
						int l =0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
								ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
								l++;
							}
						}
						if(count == 0) {
							JOptionPane.showMessageDialog(frame, "No More Chits to Place!");
						} else {
							p1.getCombatInfo().setDodge(chitPlaced(ac, null));
							tmp_img = p1.getCombatInfo().getDodge().getImage();
							n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
							btnManeuverDoge.setIcon(new ImageIcon(n_img));

							strengthUsed += p1.getCombatInfo().getDodge().getEffort();
						}
					}
					else {
						String[] choice = {"Change Chit", "Nothing"};
						int n = JOptionPane.showOptionDialog(frame,
								"What do you want to do?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choice,choice[2]);
						if(n == JOptionPane.YES_OPTION){
							p1.getCombatInfo().addActiveChit(p1.getCombatInfo().getDodge());
							//p1.getCombatInfo().setDodge(null);
							strengthUsed -= p1.getCombatInfo().getDodge().getEffort();
							int count = 0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
									count++;
								}
							}
							String[] ac = new String[count+1];
							int l =0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
									ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
									l++;
								}
							}
							ac[count] = "Empty";
							ActionChit r = chitPlaced(ac, p1.getCombatInfo().getDodge());
							if(r != null) {
								p1.getCombatInfo().setDodge(r);
								tmp_img = p1.getCombatInfo().getDodge().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnManeuverDoge.setIcon(new ImageIcon(n_img));

								strengthUsed += p1.getCombatInfo().getDodge().getEffort();

							}
						} 

					}

				}
				else {
					JOptionPane.showMessageDialog(frame, "You already placed a Fight chit in Swing. You can't Move here!");
				}
			}
		});


		btnManeuverDuck.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(p1.getCombatInfo().getSmash() == null) {
					if(p1.getCombatInfo().getDuck() == null) {
						int count = 0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
								count++;
							}
						}

						String[] ac = new String[count];
						int l =0;
						for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
							if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
								ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
								l++;
							}
						}
						if(count == 0) {
							JOptionPane.showMessageDialog(frame, "No More Chits to Place!");
						} else {
							p1.getCombatInfo().setDuck(chitPlaced(ac, null));
							tmp_img = p1.getCombatInfo().getDuck().getImage();
							n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
							btnManeuverDuck.setIcon(new ImageIcon(n_img));

							strengthUsed += p1.getCombatInfo().getDuck().getEffort();
						}
					}
					else {
						//FIX THIS!!
						String[] choice = {"Change Chit", "Nothing"};
						int n = JOptionPane.showOptionDialog(frame,
								"What do you want to do?","",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null,choice,choice[2]);
						if(n == JOptionPane.YES_OPTION){
							p1.getCombatInfo().addActiveChit(p1.getCombatInfo().getDuck());
							strengthUsed -= p1.getCombatInfo().getDuck().getEffort();
							int count = 0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
									count++;
								}
							}
							String[] ac = new String[count+1];
							int l =0;
							for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
								if(p1.getCombatInfo().getActionChits().get(i).getActionType() == "MOVE") {
									ac[l] = p1.getCombatInfo().getActionChits().get(i).getName();
									l++;
								}
							}
							ac[count] = "Empty";
							ActionChit r = chitPlaced(ac, p1.getCombatInfo().getDuck());
							if(r != null) {
								p1.getCombatInfo().setDuck(r);
								tmp_img = p1.getCombatInfo().getDuck().getImage();
								n_img = tmp_img.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
								btnManeuverDuck.setIcon(new ImageIcon(n_img));

								strengthUsed += p1.getCombatInfo().getDuck().getEffort();

							}
						} else if (n == JOptionPane.NO_OPTION) {
						} else if (n == JOptionPane.CANCEL_OPTION) {

						}

					}

				}
				else {
					JOptionPane.showMessageDialog(frame, "You already placed a Fight chit in Smash. You can't Move here!");
				}
			}
		});

		/***********************************************************************/
		btnUsedthisround.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{

			}
		});

		/*************************************************************/
		// submit button

		btnSubmit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				if(strengthUsed > 2) {
					JOptionPane.showMessageDialog(frame, "You have exceeded your effor limit!");
				}
				else {

					JOptionPane.showMessageDialog(frame, "Combat Submited. \n" + strengthUsed);
					p1.getCombatInfo().setExtraSharpness(strengthUsed);

					String result = "";
					result += p1.getName() 
							+ "#" 
							+ (p1.getCombatInfo().getThrust() != null ? p1.getCombatInfo().getThrust().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getSwing() != null ? p1.getCombatInfo().getSwing().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getSmash() != null ? p1.getCombatInfo().getSmash().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getCharge() != null ? p1.getCombatInfo().getCharge().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getDodge() != null ? p1.getCombatInfo().getDodge().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getDuck() != null ? p1.getCombatInfo().getDuck().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getWeaponLocation())
							+ "#"
							+ (p1.getCombatInfo().getActiveWeapon() != null ? p1.getCombatInfo().getActiveWeapon().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getShieldThrust() != null ? p1.getCombatInfo().getShieldThrust().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getShieldSwing() != null ? p1.getCombatInfo().getShieldSwing().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getShieldSmash() != null ? p1.getCombatInfo().getShieldSmash().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getHelmet() != null ? p1.getCombatInfo().getHelmet().getName() : "null")
							+ "#"
							+ (p1.getCombatInfo().getSuitOfArmor() != null ? p1.getCombatInfo().getSuitOfArmor().getName() : "null")
							+ "#"
							+ secondPlayer;
					if(secondPlayer >= 0) {
						MRMessage msg = new MRMessage(MRMessage.COMBAT, result);
						ClientGUI.mr.sendMessage(msg);
					}
					else {
						result = "";
						result += "#"
								+ (p1.getCombatInfo().getEChargeThrust() != null ? p1.getCombatInfo().getEChargeThrust().getName() : "null")
								+ "#"
								+ (p1.getCombatInfo().getEDodgeSwing() != null ? p1.getCombatInfo().getEDodgeSwing().getName() : "null")
								+ "#"
								+ (p1.getCombatInfo().getEDuckSmash() != null ? p1.getCombatInfo().getEDuckSmash().getName() : "null");
						MRMessage msg = new MRMessage(MRMessage.COMBAT_MONSTER_RESULT, result);
						ClientGUI.mr.sendMessage(msg);
					}




					frame.setVisible(false);
					//frame.dispose();
				}
			}
		});

	}



	public ActionChit chitPlaced(String[] ac, ActionChit c) {
		ActionChit cp = null;
		ActionChit temp = c;
		if(ac.length > 0) {
			String w = (String) JOptionPane.showInputDialog(frame, "Which Action Chit would you like to place here?", "Activating Action Chits", 
					JOptionPane.QUESTION_MESSAGE, null, ac, ac[0]);
			for(int i = 0; i < p1.getCombatInfo().getActionChits().size(); i++) {
				if(w == p1.getCombatInfo().getActionChitAt(i).getName()) {
					cp = p1.getCombatInfo().getActionChitAt(i);
					p1.getCombatInfo().addUsedChit(p1.getCombatInfo().removeActionChitAt(i));
					break;
				}
			}
			if(w == "Empty") {

				p1.getCombatInfo().addActiveChit(temp);
				cp = null;
			}
		}
		else {
			JOptionPane.showMessageDialog(frame, "You do not have any chits to place");
		}
		return cp;
	}

}
