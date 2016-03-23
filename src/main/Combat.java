package main;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Combat {
	static JFrame frame = new JFrame("COMBAT MODE!");

	
	
	public static int[] combat(Character p1, Character p2) {
		int[] result = new int[4];
		int p1Death = 0;
		int p2Death = 0;
		int[] order = craziness(p1, p2);
		int[] temp = new int[2];
		int p1Wounds = 0;
		int p2Wounds = 0;

		for(int i = 0; i < order.length; i++) {
			System.out.println("ORDER:" +order[i]);
			switch(order[i]) {
			case 0:
				temp = thrustHarmInflicted(p1, p2);
				p2Wounds += temp[0];
				p2Death += temp[1];
				if(p2Death > 0) {
					break;
				}
			case 1:
				temp = swingHarmInflicted(p1, p2);
				p2Wounds += temp[0];
				p2Death += temp[1];
				if(p2Death > 0) {
					break;
				}
			case 2:
				temp = smashHarmInflicted(p1, p2);
				p2Wounds += temp[0];
				p2Death += temp[1];
				if(p2Death > 0) {
					break;
				}
			case 3:
				temp = thrustHarmInflicted(p2, p1);
				p1Wounds += temp[0];
				p1Death += temp[1];
				if(p1Death > 0) {
					break;
				}
			case 4:
				temp = swingHarmInflicted(p2, p1);
				p1Wounds += temp[0];
				p1Death += temp[1];
				if(p1Death > 0) {
					break;
				}
			case 5:
				temp = smashHarmInflicted(p2, p1);
				p1Wounds += temp[0];
				p1Death += temp[1];
				if(p1Death > 0) {
					break;
				}
			}
		}
		System.out.println("p1d: "+result[0]);
		System.out.println("p1w: "+result[1]);
		System.out.println("p2d: "+result[2]);
		System.out.println("p2w: "+result[3]);
		result[0] = p1Death;
		result[1] = p1Wounds;
		result[2] = p2Death;
		result[3] = p2Wounds;
		return result;


	}

	public static void fatigue(Character p) {
		int total = 0;
		if(p.getCombatInfo().getExtraSharpnessUsed() == 2) {
			int count = 0;
			while(total < 3) {
				for(int i = 0; i < p.getCombatInfo().getActionChits().size(); i++){
					if(p.getCombatInfo().getActionChitAt(i).isActive() == 1) {
						count++;
					}
				}
				if(count == 0) {
					String[] ac = new String[count];
					for(int i = 0; i < ac.length; i++){
						if(p.getCombatInfo().getActionChitAt(i).isActive() == 1) {
							ac[i] = p.getCombatInfo().getActionChitAt(i).getName();
						}
					}
					String choice = (String) JOptionPane.showInputDialog(frame, "Fatigue a chit up to two Asterisks:", "Fatigue Chits", 
							JOptionPane.QUESTION_MESSAGE, null, ac, ac[0]);

					for(int i = 0; i < p.getCombatInfo().getActionChits().size(); i++){
						if(choice == p.getCombatInfo().getActionChitAt(i).getName()) {
							total += p.getCombatInfo().getActionChitAt(i).getEffort();
							p.getCombatInfo().getActionChitAt(i).inactivate();
							break;
						}
					}
				}
				else{
					///DEATH????
				}
			}
		}
	}

	public static void wounds(Character p, int wounds) {
		int total = 0;
		if(wounds != 0) {
			while(total <= wounds) {
				int count = 0;
				for(int i = 0; i < p.getCombatInfo().getActionChits().size(); i++){
					if(p.getCombatInfo().getActionChitAt(i).isActive() == 1) {
						count++;
					}
				}
				if(count > 0) {
					String[] ac = new String[count];
					for(int i = 0; i < ac.length; i++){
						if(p.getCombatInfo().getActionChitAt(i).isActive() == 1) {
							ac[i] = p.getCombatInfo().getActionChitAt(i).getName();
						}
					}
					String choice = (String) JOptionPane.showInputDialog(frame, (wounds-total)+" chits need to be wounded:", "Wounded Chits", 
							JOptionPane.QUESTION_MESSAGE, null, ac, ac[0]);

					for(int i = 0; i < p.getCombatInfo().getActionChits().size(); i++){
						if(choice == p.getCombatInfo().getActionChitAt(i).getName()) {
							p.getCombatInfo().getActionChitAt(i).makeWounded();
							break;
						}
					}
				}
				else if(count == 0) {
					//DEATH???
					//no chits to make wounded what happens then?
				}
			}
		}
	}

	public static int[] thrustHarmInflicted(Character p1, Character p2) {
		int[] combat = new int[2];
		int result = 0;
		int wounded = 0;
		int death = 0;
		if(p1.getCombatInfo().getThrust() != null) {
			if(p2.getCombatInfo().getCharge() != null) {
				System.out.println("I AM HERE");
				if (p1.getCombatInfo().getWeaponLocation() == 1) {
					if(p2.getCombatInfo().getShieldThrust() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getShieldThrust().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldThrust());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}
							}
						}
						else if (result > p2.getCombatInfo().getShieldThrust().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldThrust());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldThrust() == null && p2.getCombatInfo().getBreastPlate() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldThrust() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldThrust() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() == null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + (p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result >= p2.getWeightE()) {
							death =1;
						}
						else {
							wounded = 1;
						}
					}
				}
				else if(p1.getCombatInfo().getWeaponLocation() != 1) {
					result = p1.getCombatInfo().getThrust().getHarmLevel();
					if(p2.getCombatInfo().getShieldThrust() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getShieldThrust().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldThrust());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getShieldThrust().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldThrust());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldThrust() == null && p2.getCombatInfo().getBreastPlate() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldThrust() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldThrust() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() == null){
						result = 2;
						if(result >= p2.getWeightE()) {
							death = 1;
						}
						else if(result < p2.getWeightE()) {
							wounded = 1;
						}
					}
				}
			}
			else {
				System.out.println("Player 1 attack is a MISS!");
			}
		}
		combat[0] = wounded;
		combat[1] = death;
		return combat;
	}

	public static int[] swingHarmInflicted(Character p1, Character p2) {
		int[] combat = new int[2];
		int result = 0;
		int wounded = 0;
		int death = 0;
		if(p1.getCombatInfo().getSwing() != null) {
			if(p2.getCombatInfo().getDodge() != null) {
				if (p1.getCombatInfo().getWeaponLocation() == 1) {
					if(p2.getCombatInfo().getShieldSwing() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getShieldSwing().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSwing());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}
							}
						}
						else if (result > p2.getCombatInfo().getShieldSwing().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSwing());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSwing() == null && p2.getCombatInfo().getBreastPlate() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSwing() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + (p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result >= p2.getWeightE()) {
							death =1;
						}
						else {
							wounded = 1;
						}
					}
				}
				else if(p1.getCombatInfo().getWeaponLocation() != 1) {
					result = p1.getCombatInfo().getSwing().getHarmLevel();
					if(p2.getCombatInfo().getShieldSwing() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getShieldSwing().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSwing());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getShieldSwing().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSwing());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSwing() == null && p2.getCombatInfo().getBreastPlate() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSwing() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSwing() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() == null){
						result = 2;
						if(result >= p2.getWeightE()) {
							death = 1;
						}
						else if(result < p2.getWeightE()) {
							wounded = 1;
						}
					}
				}
			}
			else {
				System.out.println("Player 1 attack is a MISS!");
			}
		}
		combat[0] = wounded;
		combat[1] = death;
		return combat;
	}

	public static int[] smashHarmInflicted(Character p1, Character p2) {
		int[] combat = new int[2];
		int result = 0;
		int wounded = 0;
		int death = 0;
		if(p1.getCombatInfo().getSmash() != null) {
			if(p2.getCombatInfo().getDuck() != null) {
				if (p1.getCombatInfo().getWeaponLocation() == 1) {
					if(p2.getCombatInfo().getShieldSmash() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getShieldSmash().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSmash());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}
							}
						}
						else if (result > p2.getCombatInfo().getShieldSmash().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSmash());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSmash() == null && p2.getCombatInfo().getBreastPlate() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSmash() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() != null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + ((p1.getCombatInfo().getActiveWeapon().getSharpness() > 0) ? 0: p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result == p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							wounded = 1;
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSmash() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() == null) {
						result = p1.getCombatInfo().getActiveWeapon().getWeightE() + (p1.getCombatInfo().getActiveWeapon().getSharpness() - 1);
						if(result >= p2.getWeightE()) {
							death =1;
						}
						else {
							wounded = 1;
						}
					}
				}
				else if(p1.getCombatInfo().getWeaponLocation() != 1) {
					result = p1.getCombatInfo().getSmash().getHarmLevel();
					if(p2.getCombatInfo().getShieldSmash() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getShieldSmash().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSmash());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getShieldSmash().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getShieldSmash());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSmash() == null && p2.getCombatInfo().getBreastPlate() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getBreastPlate().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getBreastPlate());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSmash() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() != null) {
						result = 1;
						if(result == p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								if(p2.getArmor().get(loc).isDamaged() == true) {
									p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
									p2.getArmor().remove(loc);
								} else {
									p2.getArmor().get(loc).setDamaged(true);
								}	
							}
						}
						else if (result > p2.getCombatInfo().getSuitOfArmor().getArmorWeightE()) {
							int loc = ArmorLoc(p2, p2.getCombatInfo().getSuitOfArmor());
							if(loc > -1) {
								p2.setGoldRecorded(p2.getGoldRecorded() + p2.getArmor().get(loc).getPrice());
								p2.getArmor().remove(loc);
							}
						}
					}
					else if(p2.getCombatInfo().getShieldSmash() == null && p2.getCombatInfo().getBreastPlate() == null && p2.getCombatInfo().getSuitOfArmor() == null){
						result = 2;
						if(result >= p2.getWeightE()) {
							death = 1;
						}
						else if(result < p2.getWeightE()) {
							wounded = 1;
						}
					}
				}
			}
			else {
				System.out.println("Player 1 attack is a MISS!");
			}
		}
		combat[0] = wounded;
		combat[1] = death;
		return combat;
	}

	
	public static int ArmorLoc(Character p, Armor a) {
		int loc = -1;
		for(int i = 0; i < p.getArmor().size(); i++) {
			if(a.getName() == p.getArmor().get(i).getName()) {
				loc = i;
				break;
			}
		}
		return loc;
	}

	public static int[] checkOrder(Character p1, Character p2) {
		int[] attackTime = new int[6];
		for(int i = 0; i < 3; i ++) {
			if(p1.getCombatInfo().getroundChitAt(i) != null) {
				System.out.println(i + ": " +p1.getCombatInfo().getroundChitAt(i).getName());
				attackTime[i] = p1.getCombatInfo().getroundChitAt(i).getActionNum();
			}
			else if(p1.getCombatInfo().getroundChitAt(i) == null) {
				attackTime[i] = 0;
			}
		}
		int count = 3;
		for(int i = 0; i < 3; i ++) {
			if(p2.getCombatInfo().getroundChitAt(i) != null) {
				System.out.println(i + ": " +p1.getCombatInfo().getroundChitAt(i).getName());
				attackTime[count] = p2.getCombatInfo().getroundChitAt(i).getActionNum();
			}
			else if(p2.getCombatInfo().getroundChitAt(i) == null) {
				attackTime[count] = 0;
			}
			count++;
		}
		for(int i = 1; i <4; i++) {
			if(p1.getCombatInfo().getWeaponLocation() == i) {
				
				attackTime[i-1] = p1.getCombatInfo().getActiveWeapon().getAttackTime();
			}
		}
		for(int i = 1; i <4; i++) {
			if(p2.getCombatInfo().getWeaponLocation() == i) {
				attackTime[i+2] = p1.getCombatInfo().getActiveWeapon().getAttackTime();
			}
		}

		return attackTime;
	}

	public static int[] checkMonsterOrder(Character p1) {
		int[] attackTime = new int[6];
		for(int i = 0; i < 3; i ++) {
			if(p1.getCombatInfo().getroundChitAt(i) != null) {
				System.out.println(i + ": " +p1.getCombatInfo().getroundChitAt(i).getName());
				attackTime[i] = p1.getCombatInfo().getroundChitAt(i).getActionNum();
			}
			else if(p1.getCombatInfo().getroundChitAt(i) == null) {
				attackTime[i] = 0;
			}
		}
		if(p1.getCombatInfo().getEChargeThrust() != null){
			attackTime[3] = p1.getCombatInfo().getEChargeThrust().getActionNum();
		} else {
			attackTime[3] = 0;
		}
		if(p1.getCombatInfo().getEDodgeSwing() != null){
			attackTime[4] = p1.getCombatInfo().getEDodgeSwing().getActionNum();
		} else {
			attackTime[3] = 0;
		}
		if(p1.getCombatInfo().getEDuckSmash() != null){
			attackTime[5] = p1.getCombatInfo().getEChargeThrust().getActionNum();
		} else {
			attackTime[3] = 0;
		}
		
		for(int i = 1; i <4; i++) {
			if(p1.getCombatInfo().getWeaponLocation() == i) {
				
				attackTime[i-1] = p1.getCombatInfo().getActiveWeapon().getAttackTime();
			}
		}
		return attackTime;
	}

	public static int[] craziness(Character p1, Character p2) {
		int[] attack = checkOrder(p1, p2);
		int[] order = {0, 0, 0, 0, 0, 0};
		int temp = 0;
		for(int i = 0; i < attack.length; i++) {
			if(attack[i] < attack[temp]) {
				temp = i;
			}
		}
		order[0] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[1] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[2] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[3] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[4] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[5] = temp;
		return order;

	}
	public static int[] monstercraziness(Character p1) {
		int[] attack = checkMonsterOrder(p1);
		int[] order = {0, 0, 0, 0, 0, 0};
		int temp = 0;
		for(int i = 0; i < attack.length; i++) {
			if(attack[i] < attack[temp]) {
				temp = i;
			}
		}
		order[0] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[1] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[2] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[3] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[4] = temp;
		for(int i = 0; i < attack.length; i++) {
			if(i != temp) {
				if(attack[i] < attack[temp]) {
					temp = i;
				}
			}
		}
		order[5] = temp;
		return order;

	}


	public void meetingTableEvening(Character p, NativeGroups n, int dice) {
		int currLevel = currLevel(p, n);

		if (currLevel == 1) {
			meetingTableFriendly(p, n, dice);
		}
		else if(currLevel == 2) {
			if(dice == 1) {
				//nothing
			}
			else {
				meetingTableUnfriendly(p, n, dice);
			}
		}
		else if(currLevel == 3) {
			if(dice == 2 || dice == 3) {
				//nothing
			}
			else {
				meetingTableNeutral(p, n, dice);
			}
		}
		else if(currLevel == 4) {
			if(dice == 1 || dice == 6) {
				meetingTableAlly(p, n, dice);
			}
			else {
				///NOTHING IN THE EVENING
			}
		}
		else if(currLevel == 5) {
			//NOTHING IN THE EVENING
		}



	}
	public void meetingTableAlly(Character p, NativeGroups n, int diceRoll) {
		if (diceRoll == 1) {

		}
		else if(diceRoll == 2) {

		}
		else if(diceRoll == 3) {

		}
		else if(diceRoll == 4) {

		}
		else if(diceRoll == 5) {

		}
		else if(diceRoll == 6) {

		}
	}
	public void meetingTableFriendly(Character p, NativeGroups n, int diceRoll) {
		if (diceRoll == 1) {
			//roll again
			int dice = 0;
			meetingTableAlly(p, n, dice);
		}
		else if(diceRoll == 2) {

		}
		else if(diceRoll == 3) {

		}
		else if(diceRoll == 4) {

		}
		else if(diceRoll == 5) {

		}
		else if(diceRoll == 6) {
			noDeal();
		}
	}
	public void meetingTableNeutral(Character p, NativeGroups n, int diceRoll) {
		if (diceRoll == 1) {
			//roll again
			int dice = 0;
			meetingTableFriendly(p, n, dice);
		}
		else if(diceRoll == 2) {

		}
		else if(diceRoll == 3) {

		}
		else if(diceRoll == 4) {
			noDeal();
		}
		else if(diceRoll == 5) {
			noDeal();
		}
		else if(diceRoll == 6) {
			//roll again
			int dice = 0;
			meetingTableUnfriendly(p, n, dice);
		}
	}
	public void meetingTableUnfriendly(Character p, NativeGroups n, int diceRoll) {
		if (diceRoll == 1) {

		}
		else if(diceRoll == 2) {
			noDeal();
		}
		else if(diceRoll == 3) {
			noDeal();
		}
		else if(diceRoll == 4) {
			JFrame frame = new JFrame("INSULT");
			int choose = JOptionPane.showConfirmDialog(frame, "Would you like to lose 5 of your Notoriety points and treat this as NO DEAL?", "Insult!", JOptionPane.YES_NO_OPTION);
			if(choose == JOptionPane.YES_OPTION) {
				p.setNotorietyRecorded(p.getNotorityRecordedt()-5);
				noDeal();
			}
			else if(choose == JOptionPane.NO_OPTION) {
				meetingTableUnfriendly(p, n, 6);
			}
		}
		else if(diceRoll == 5) {
			JFrame frame = new JFrame("CHALLENGE");
			int choose = JOptionPane.showConfirmDialog(frame, "Would you like to lose 5 of Fame points and treat this as NO DEAL?", "Challenge!", JOptionPane.YES_NO_OPTION);
			if(choose == JOptionPane.YES_OPTION) {
				p.setFameRecorded(p.getFameRecorded()-5);
				noDeal();
			}
			else if(choose == JOptionPane.NO_OPTION) {
				meetingTableUnfriendly(p, n, 6);
			}
		}
		else if(diceRoll == 6) {

		}
	}
	public void meetingTableEnemy(Character p, NativeGroups n, int diceRoll) {
		if (diceRoll == 1) {
			JFrame frame = new JFrame("INSULT");
			int choose = JOptionPane.showConfirmDialog(frame, "Would you like to lose 5 of your Notoriety points and treat this as NO DEAL?", "Insult!", JOptionPane.YES_NO_OPTION);
			if(choose == JOptionPane.YES_OPTION) {
				p.setNotorietyRecorded(p.getNotorityRecordedt()-5);
				noDeal();
			}
			else if(choose == JOptionPane.NO_OPTION) {
				meetingTableEnemy(p, n, 3);
			}
		}
		else if(diceRoll == 2) {
			JFrame frame = new JFrame("CHALLENGE");
			int choose = JOptionPane.showConfirmDialog(frame, "Would you like to lose 5 of Fame points and treat this as NO DEAL?", "Challenge!", JOptionPane.YES_NO_OPTION);
			if(choose == JOptionPane.YES_OPTION) {
				p.setFameRecorded(p.getFameRecorded()-5);
				noDeal();
			}
			else if(choose == JOptionPane.NO_OPTION) {
				meetingTableEnemy(p, n, 3);
			}
		}
		else if(diceRoll == 3) {

		}
		else if(diceRoll == 4) {

		}
		else if(diceRoll == 5) {

		}
		else if(diceRoll == 6) {

		}
	}

	public void buyDrinks(Character p, NativeGroups n) {
		friendlinessLevel(p, n, 1);
	}

	public void friendlinessLevel(Character p, NativeGroups n, int level) {
		int currLevel = currLevel(p, n);

		if(currLevel == 5) {
			if(level > 0) {
				System.out.println("Already an Ally! Cannot be any more friendly!!");
			}
			else {

			}
		}
		else if(currLevel == 4) {
			if(level > 0) {

			}
			else {

			}
		}
		else if(currLevel == 3) {
			if(level > 0) {

			}
			else {

			}
		}
		else if(currLevel == 2) {
			if(level > 0) {

			}
			else {

			}
		}
		else if(currLevel == 1) {
			if(level > 0) {

			}
			else {
				System.out.println("Already an Enemy! Cannot be any more unfriendly!!");
			}
		}
	}
	public int currLevel(Character p, NativeGroups n) {
		int currLevel = 0;
		//ALLY LEVEL 5
		for(int i = 0; i < p.getAlly().length; i++) {
			if(p.getAlly()[i] == n.getName()) {
				currLevel = 5;
				break;
			}
		}
		//FRIENDLY LEVEL 4
		for(int i = 0; i < p.getFriendly().length; i++) {
			if(p.getFriendly()[i] == n.getName()) {
				currLevel = 4;
				break;
			}
		}
		//NEUTRAL LEVEL 3
		for(int i = 0; i < p.getNeutral().length; i++) {
			if(p.getNeutral()[i] == n.getName()) {
				currLevel = 3;
				break;
			}
		}
		//UNFRIENDLY LEVEL 2
		for(int i = 0; i < p.getUnFriendly().length; i++) {
			if(p.getUnFriendly()[i] == n.getName()) {
				currLevel = 2;
				break;
			}
		}
		//ENEMY LEVEL 1
		for(int i = 0; i < p.getEnemy().length; i++) {
			if(p.getEnemy()[i] == n.getName()) {
				currLevel = 1;
				break;
			}
		}
		if(currLevel == 0) {
			p.setNeutral(n.getName());
			currLevel = 3;
		}
		return currLevel;
	}
	public void noDeal() {
		JFrame frame = new JFrame("No Deal");
		JOptionPane.showMessageDialog(frame, "No deal is made.");
	}
}
