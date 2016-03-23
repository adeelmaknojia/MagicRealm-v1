package main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.*;

import server.MRServer;
import messaging.MRMessage;
import client.ClientGUI;
import Tiles.*;

public class Activity extends JFrame {

	/**
	 * This method is a duplicate of alert, except for the fact that it returns
	 * the string of the chosen type of alert by the player.
	 * 
	 * @param p
	 * @return the chosen weapon or empty string if none selected
	 */

	public static String alertNew(Character p) {
		int alerted = 0;
		for (int i = 0; i < p.getweaponList().size(); i++) {
			if (p.getweaponList().get(i).isActive()) {
				alerted++;
			}

		}
		String[] weapons = new String[alerted];
		for (int i = 0; i < p.getweaponList().size(); i++) {
			if (p.getweaponList().get(i).isActive()) {
				weapons[i] = p.getweaponList().get(i).getName();
			}

		}

		JFrame frame = new JFrame("Alert");

		if (weapons.length < 1) {

			JOptionPane.showMessageDialog(frame,
					"You have no active weapon to alert!");
		} else {
			String choice = (String) JOptionPane.showInputDialog(frame,
					"Which weapon would you like to alert?", "Alert Weapon",
					JOptionPane.QUESTION_MESSAGE, null, weapons, weapons[0]);

			for (int i = 0; i < p.getweaponList().size(); i++) {
				if (choice == p.getweaponList().get(i).getName()) {
					p.getweaponList().get(i).alert();
					JOptionPane.showMessageDialog(frame, p.getName()
							+ ": You have alerted your "
							+ p.getweaponList().get(i).getName(),
							"Weapon Alerted", JOptionPane.INFORMATION_MESSAGE);
					return p.getweaponList().get(i).getName();
				}
			}
		}

		return "";
	}

	public static void alert(Character p) {
		int alerted = 0;
		for (int i = 0; i < p.getweaponList().size(); i++) {
			if (p.getweaponList().get(i).isActive()) {
				alerted++;
			}

		}
		String[] weapons = new String[alerted];
		for (int i = 0; i < p.getweaponList().size(); i++) {
			if (p.getweaponList().get(i).isActive()) {
				weapons[i] = p.getweaponList().get(i).getName();
			}

		}

		JFrame frame = new JFrame("Alert");

		if (weapons.length < 1) {

			JOptionPane.showMessageDialog(frame,
					"You have no active weapon to alert!");
		} else {
			String choice = (String) JOptionPane.showInputDialog(frame,
					"Which weapon would you like to alert?", "Alert Weapon",
					JOptionPane.QUESTION_MESSAGE, null, weapons, weapons[0]);

			for (int i = 0; i < p.getweaponList().size(); i++) {
				if (choice == p.getweaponList().get(i).getName()) {
					p.getweaponList().get(i).alert();
					JOptionPane.showMessageDialog(frame, p.getName()
							+ ": You have alerted your "
							+ p.getweaponList().get(i).getName(),
							"Weapon Alerted", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			}
		}

	}

	public static String move(Character p, String move, Tile[][] board) {
		String tileMove = move.substring(0, 2);
		int clMove = Integer.parseInt(move.substring(move.length() - 1));
		int ifMove = 0;
		int cave = 0;
		int loc = 0;//arraylocation of clearing to move
		int locx = 0;//tile x to move
		int locy = 0;//tile y to move
		int pCurr = 0;
		int clCurr = p.getClearingLocation().getClearingNum();
		int px = 0; //tile x current
		int py = 0; //tile y current
		String retValue = "";
		boolean myself = false;

		searching: for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].getName() != null) {
					System.out.println(i + "" + j + ": "
							+ board[i][j].getName());
					if (board[i][j].getName().equalsIgnoreCase(tileMove)) {
						locx = i;
						locy = j;
						break searching;
					}
				}
			}
		}
		search: for (int i = 0; i < p.getPathway().size(); i++) {
			if (p.getClearingOnPathway(i)!=null && p.getClearingOnPathway(i).getTileName().equalsIgnoreCase(tileMove)) {

				if (p.getClearingOnPathway(i).getClearingNum() == clMove) {
					loc = i;
					System.out.println(loc);
					break search;
				}
			}
		}
		pClearing: for (int i = 0; i < p.getPathway().size(); i++) {
			if (p.getClearingOnPathway(i)!=null && p.getClearingOnPathway(i).getTileName().equalsIgnoreCase(p.getTileLocation())) {
				if (p.getClearingOnPathway(i).getClearingNum() == clCurr) {
					pCurr = i;
					System.out.println(pCurr);
					break pClearing;
				}
			}
		}

		pBoard: for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].getName() != null) {
					if (board[i][j].getName().equalsIgnoreCase(p.getTileLocation())) {
						px = i;
						py = j;
						break pBoard;
					}
				}
			}
		}
		if (loc == 0) {
			retValue = "The clearing you want to move to doesnt exist!! WHY??";
			System.out.println(retValue);
		} 
		else if (p.getClearingLocation().getAllAdjacentClearings() != null && myself == false) {
			for (int i = 0; i < p.getClearingLocation().getTotalAdjacentClearings(); i++) {
				if (p.getClearingLocation().getAdjacentClearing(i).getClearingNum() == p.getClearingOnPathway(loc).getClearingNum()) {
					//IF CLEARING TO MOVE IS A MOUNTAIN
					if (p.getClearingOnPathway(loc).getClearingType() == 'm') {
						//IF CLEARING ON IS A MOUNTAIN
						if (p.getClearingLocation().getClearingType() == 'm') {

							//TEMP ALL CURRENT VALUES
							int tempx = p.getClearingOnPathway(loc).getXLocation();
							int tempy = p.getClearingOnPathway(loc).getYLocation();
							String tempT = p.getClearingOnPathway(loc).getTileName();
							Clearing currClearing = p.getClearingOnPathway(loc);

							//remove old position
							board[px][py].getClearing(clCurr).removeCharacter(p);
							p.getClearingOnPathway(pCurr).removeCharacter(p);

							//set new location
							p.setPLocation(tempx, tempy);
							p.setBoardLocation(tempT);
							p.setClearingLocation(currClearing);
							p.getClearingOnPathway(loc).placeCharacter(p);
							//board[locx][locy].getClearing(clMove).placeCharacter(p);

							ifMove = 1;
							break;
						} else if (p.getClearingLocation().getClearingType() != 'm' && p.ifMountainMove() == 0) {
							p.setMountainMove();
							ifMove = 1;
							retValue = "You need one more Move activity to get into this clearing";
							JOptionPane.showMessageDialog(null, "You need one more Move activity to get into this clearing");
							break;

						} else if (p.getClearingLocation().getClearingType() != 'm' && p.ifMountainMove() == 1) {

							//TEMP ALL CURRENT VALUES
							int tempx = p.getClearingOnPathway(loc).getXLocation();
							int tempy = p.getClearingOnPathway(loc).getYLocation();
							String tempT = p.getClearingOnPathway(loc).getTileName();
							Clearing currClearing = p.getClearingOnPathway(loc);

							//remove old position
							board[px][py].getClearing(clCurr).removeCharacter(p);
							p.getClearingOnPathway(pCurr).removeCharacter(p);

							//set new location
							p.setPLocation(tempx, tempy);
							p.setBoardLocation(tempT);
							p.setClearingLocation(currClearing);
							p.getClearingOnPathway(loc).placeCharacter(p);
							//board[locx][locy].getClearing(clMove).placeCharacter(p);

							ifMove = 1;
							p.removeMountainMove();
							break;
						}
					} else if (p.getClearingOnPathway(loc).getClearingType() == 'c') {
						if (p.isSunlightPhase() == true) {
							retValue = p.getName() + ": You cannot enter a cave during a sunlight phase!";
							//JOptionPane.showMessageDialog(null, p.getName()+ ": You cannot enter a cave during a sunlight phase!");
							System.out.println(p.getName()+ ": You cannot enter a cave during a sunlight phase!");

						} else {
							//TEMP ALL CURRENT VALUES
							int tempx = p.getClearingOnPathway(loc).getXLocation();
							int tempy = p.getClearingOnPathway(loc).getYLocation();
							String tempT = p.getClearingOnPathway(loc).getTileName();
							Clearing currClearing = p.getClearingOnPathway(loc);

							//remove old position
							board[px][py].getClearing(clCurr).removeCharacter(p);
							p.getClearingOnPathway(pCurr).removeCharacter(p);

							//set new location
							p.setPLocation(tempx, tempy);
							p.setBoardLocation(tempT);
							p.setClearingLocation(currClearing);
							p.getClearingOnPathway(loc).placeCharacter(p);
							//board[locx][locy].getClearing(clMove).placeCharacter(p);

							ifMove = 1;
							cave = 1;
							break;
						}
					} else {
						//TEMP ALL CURRENT VALUES
						int tempx = p.getClearingOnPathway(loc).getXLocation();
						int tempy = p.getClearingOnPathway(loc).getYLocation();
						String tempT = p.getClearingOnPathway(loc).getTileName();
						Clearing currClearing = p.getClearingOnPathway(loc);

						//remove old position
						board[px][py].getClearing(clCurr).removeCharacter(p);
						p.getClearingOnPathway(pCurr).removeCharacter(p);

						//set new location
						p.setPLocation(tempx, tempy);
						p.setBoardLocation(tempT);
						p.setClearingLocation(currClearing);
						p.getClearingOnPathway(loc).placeCharacter(p);
						//board[locx][locy].getClearing(clMove).placeCharacter(p);
						ifMove = 1;
						break;
					}
				}
			}
			if (ifMove == 1) {
				//p.phasePlayed();
				retValue="1";
			}
			if (cave == 1) {
				p.cavePlayed();
			}
			if (ifMove == 0) {
				System.out.println("You did not move! Why??");
			} else {

			}

		}
		return retValue;

	}

	public static void hide(Character p, int diceRoll) {

		if (diceRoll != 6) {
			if (p.isHidden() == 1) {
				p.unhide();
				;
				System.out.println("To Player: " + p.getName()
						+ ": You are now Unhidden!");
			} else {
				p.hide();
				System.out.println("To Player: " + p.getName()
						+ ": You are now Hidden!");
			}
		} else {
			System.out.println("To Player " + p.getName()
					+ ": You rolled a 6, there is nothing you can do.");

		}
	}

	public static void block(Character p) {

		if (p.isblocked() == false) {
			p.block();
			System.out.println("To Player: " + p.getName()
					+ ": You blocked everyone!");
		} else {
			System.out.println("To Player: " + p.getName()
					+ ": You already blocked!");
		}
	}

	public static String search(Character p, String searchTable, int diceRoll,
			Tile[][] board) {
		int retValue = -1;
		String rets= "";
		JFrame frame = new JFrame("Search Activity");
		Tile t = null;
		int locx = 0;
		int locy = 0;
		search: {
			for (int i = 0; i < board.length; i++) {
				for (int j = 0; j < board[0].length; j++) {
					if (board[i][j].getName() != null) {
						if (board[i][j].getName().equals(p.getTileLocation())) {
							t = board[i][j];
							locx = i;
							locy = j;
							break search;
						}
					}
				}
			}
		}
		int c = 0;
		for (int j = 1; j < t.getAllClearings().length + 1; j++) {
			if(t.getClearing(j) != null) {
				if (t.getClearing(j).getClearingNum() == p.getClearingLocation()
						.getClearingNum()) {
					c = t.getClearing(j).getClearingNum();
				}
			}
		}
		if (searchTable.equalsIgnoreCase("PEER")) {
			if (diceRoll == 1) {// CHOICE
				String choice = searchTableCHOICE();
				rets = choice;
				if (choice.equals("CLUES AND PATHS")) {
					search(p, "PEER", 2, board);
				} else if (choice.equals("HIDDEN ENEMIES AND PATHS")) {
					search(p, "PEER", 3, board);
				} else if (choice.equals("HIDDEN ENEMIES")) {
					search(p, "PEER", 4, board);
				} else if (choice.equals("CLUES")) {
					search(p, "PEER", 5, board);
				} else if (choice.equals("PASSAGES AND CLUES")) {
					search(p, "LOCATE", 2, board);
				} else if (choice.equals("PASSAGES")) {
					search(p, "LOCATE", 3, board);
				} else if (choice.equals("DISCOVER CHITS")) {
					search(p, "LOCATE", 4, board);
				} else if (choice.equals("NOTHING")) {
					search(p, "PEER", 6, board);
				}

			} else if (diceRoll == 2) { // CLUES AND PATHS
				// CLUES
				search(p, "PEER", 5, board);

				// PATHS
				if (board[locx][locy].containsHiddenPaths() == true) {
					int discovered = 0;
					for (int i = 0; i < board[locx][locy].getHiddenPaths()
							.size(); i++) {
						int cl1 = Integer.parseInt(board[locx][locy]
								.getHiddenPaths().get(i).substring(0, 1));
						int cl2 = Integer.parseInt(board[locx][locy]
								.getHiddenPaths().get(i).substring(1, 2));
						if (p.getClearingLocation().getClearingNum() == cl1) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
											.getClearing(cl2));
							JOptionPane.showMessageDialog(frame, p.getName() + "You discovered a Hidden Path leading to Clearing "
									+ board[locx][locy].getClearing(cl2).getClearingNum()); 
							//							System.out.println(p.getName()
							//									+ ": You discovered a Hidden Path!");
							//							System.out
							//							.println(p.getName()
							//									+ ": You discovered a path leading to Clearing "
							//									+ board[locx][locy]
							//											.getClearing(cl2));
							discovered = 1;
							break;
						} else if (p.getClearingLocation().getClearingNum() == cl2) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
											.getClearing(cl1));
							JOptionPane.showMessageDialog(frame, p.getName() + "You discovered a Hidden Path leading to Clearing "
									+ board[locx][locy].getClearing(cl2).getClearingNum());
							//							System.out.println(p.getName()
							//									+ ": You discovered a Hidden Path!");
							//							System.out
							//							.println(p.getName()
							//									+ ": You discovered a path leading to Clearing "
							//									+ board[locx][locy]
							//											.getClearing(cl1));
							discovered = 1;
							break;
						}
					}
					if (discovered == 0) {
						JOptionPane.showMessageDialog(frame, p.getName() + "There are no hidden paths connected to you clearing!");
						//						System.out
						//						.println(p.getName()
						//								+ ": There are no Hidden Paths connected to your clearing!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, p.getName() + "There are no Hidden Paths in this tile for you to discover!");
					//					System.out
					//					.println(p.getName()
					//							+ ": There are no Hidden Paths in this tile for you to discover!");
				}

			} else if (diceRoll == 3) { // HIDDEN ENEMIES AND PATHS
				// HIDDEN ENEMIES
				search(p, "PEER", 4, board);

				// PATHS
				if (board[locx][locy].containsHiddenPaths() == true) {
					int discovered = 0;
					for (int i = 0; i < board[locx][locy].getHiddenPaths()
							.size(); i++) {
						int cl1 = Integer.parseInt(board[locx][locy]
								.getHiddenPaths().get(i).substring(0, 1));
						int cl2 = Integer.parseInt(board[locx][locy]
								.getHiddenPaths().get(i).substring(1, 2));
						if (p.getClearingLocation().getClearingNum() == cl1) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
											.getClearing(cl2));
							JOptionPane.showMessageDialog(frame, p.getName()
									+ ": You discovered a path leading to Clearing " + board[locx][locy]
											.getClearing(cl2).getClearingNum());
							//							System.out.println(p.getName()
							//									+ ": You discovered a Hidden Path!");
							//							System.out
							//							.println(p.getName()
							//									+ ": You discovered a path leading to Clearing "
							//									+ board[locx][locy]
							//											.getClearing(cl2));
							discovered = 1;
							break;
						} else if (p.getClearingLocation().getClearingNum() == cl2) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
											.getClearing(cl1));
							JOptionPane.showMessageDialog(frame, p.getName()
									+ ": You discovered a path leading to Clearing " + board[locx][locy]
											.getClearing(cl1).getClearingNum());
							//											System.out.println(p.getName()
							//													+ ": You discovered a Hidden Path!");
							//											System.out
							//											.println(p.getName()
							//													+ ": You discovered a path leading to Clearing "
							//													+ board[locx][locy]
							//															.getClearing(cl1));
							discovered = 1;
							break;
						}
					}
					if (discovered == 0) {
						JOptionPane.showMessageDialog(frame, p.getName()
								+ ": There are no Hidden Paths connected to your clearing!");
						//						System.out
						//						.println(p.getName()
						//								+ ": There are no Hidden Paths connected to your clearing!");
					}
				} else {
					JOptionPane.showMessageDialog(frame, p.getName()
							+ ": There are no Hidden Paths in this tile for you to discover!");
					//					System.out
					//					.println(p.getName()
					//							+ ": There are no Hidden Paths in this tile for you to discover!");
				}
			} else if (diceRoll == 4) {
				// HIDDEN ENEMIES
				if (board[locx][locy]
						.getClearing(p.getClearingLocation().getClearingNum())
						.getAllCharactersOnClearing().size() > 1) {
					String he = "";
					p.setHiddenEnemies(board[locx][locy].getClearing(
							p.getClearingLocation().getClearingNum())
							.getAllCharactersOnClearing());
					for (int i = 0; i < board[locx][locy]
							.getClearing(
									p.getClearingLocation().getClearingNum())
									.getAllCharactersOnClearing().size(); i++) {
						if (board[locx][locy]
								.getClearing(
										p.getClearingLocation()
										.getClearingNum())
										.getCharacterOnClearing(i).isHidden() == 1 && 
										board[locx][locy]
												.getClearing(
														p.getClearingLocation()
														.getClearingNum())
														.getCharacterOnClearing(i).getName() != p.getName()) {
							he += (board[locx][locy].getClearing(
									p.getClearingLocation().getClearingNum())
									.getCharacterOnClearing(i).getName())
									+ "\n";
						}

					}

					JOptionPane.showMessageDialog(frame, p.getName()
							+ ": You found these hidden enemies " + he,
							"Hidden Enemies Found",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane
					.showMessageDialog(
							frame,
							p.getName()
							+ ": There are no hidden Enemies in this clearing ",
							"Hidden Enemies:",
							JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (diceRoll == 5) {
				// CLUES
				int count = 0;
				for(int i = 0; i < board[locx][locy].getAllChits().size(); i++) {
					if(board[locx][locy].getChitAt(i).isHidden() == 1) {
						count++;
					}
				}
				if (count > 0) {

					System.out.println(p.getName()
							+ ": Sneak Peek at Chits located in "
							+ board[locx][locy].getName() + ":");
					String chts = "";
					for (int i = 0; i < board[locx][locy].getAllChits().size(); i++) {
						if(board[locx][locy].getChitAt(i).isHidden() == 1) {
							chts += board[locx][locy].getChitAt(i).toString() +"\n";
							//							System.out.println(board[locx][locy].getChitAt(i)
							//									.toString());
						}
					}
					JOptionPane.showMessageDialog(frame, p.getName()
							+ ": Sneak Peek at Chits located in"
							+ board[locx][locy].getName() + ": \n" + chts);
				} else {
					JOptionPane.showMessageDialog(frame, p.getName()
							+ "There are no Map chits in this tile for you to Peek at!");
					//					System.out
					//					.println(p.getName()
					//							+ "There are no Map chits in this tile for you to Peek at!");
				}
			} else if (diceRoll == 6) {
				// NOTHING
				JOptionPane.showConfirmDialog(frame, p.getName()
						+ ": You rolled a six. There is nothing you can do!");
				//				System.out.println(p.getName()
				//						+ ": You rolled a five. There is nothing you can do!");
			}
		}
		if (searchTable.equalsIgnoreCase("LOCATE")) {
			if (diceRoll == 1) { // CHOICE
				String choice = searchTableCHOICE();
				rets = choice;
				if (choice.equals("CLUES AND PATHS")) {
					search(p, "PEER", 2, board);
				} else if (choice.equals("HIDDEN ENEMIES AND PATHS")) {
					search(p, "PEER", 3, board);
				} else if (choice.equals("HIDDEN ENEMIES")) {
					search(p, "PEER", 4, board);
				} else if (choice.equals("CLUES")) {
					search(p, "PEER", 5, board);
				} else if (choice.equals("PASSAGES AND CLUES")) {
					search(p, "LOCATE", 2, board);
				} else if (choice.equals("PASSAGES")) {
					search(p, "LOCATE", 3, board);
				} else if (choice.equals("DISCOVER CHITS")) {
					search(p, "LOCATE", 4, board);
				} else if (choice.equals("NOTHING")) {
					search(p, "PEER", 6, board);
				}
			} else if (diceRoll == 2) {// PASSAGES AND CLUES
				// PASSAGES
				search(p, "LOCATE", 3, board);
				// CLUES
				search(p, "PEER", 5, board);

			} else if (diceRoll == 3) {
				// PASSAGES
				if (board[locx][locy].containsSecretPaths() == true) {
					for (int i = 0; i < board[locx][locy].getSecretPaths()
							.size(); i++) {
						int cl1 = Integer.parseInt(board[locx][locy]
								.getSecretPaths().get(i).substring(0, 1));
						int cl2 = Integer.parseInt(board[locx][locy]
								.getSecretPaths().get(i).substring(1, 2));
						if (p.getClearingLocation().getClearingNum() == cl1) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
											.getClearing(cl2));

							JOptionPane.showMessageDialog(frame, p.getName() + "You have discovered a Hidden Passage leading to Clearing" 
									+board[locx][locy].getClearing(cl2).getClearingNum());
							//							System.out.println(p.getName()
							//									+ ": You discovered a Hidden Passageway!");
							//							System.out
							//							.println(p.getName()
							//									+ ": You discovered a path leading to Clearing "
							//									+ board[locx][locy]
							//											.getClearing(cl2));
							break;
						} else if (p.getClearingLocation().getClearingNum() == cl2) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
											.getClearing(cl1));

							JOptionPane.showMessageDialog(frame, p.getName() + "You have discovered a Hidden Passage leading to Clearing" 
									+board[locx][locy].getClearing(cl1).getClearingNum());

							//							System.out.println(p.getName()
							//									+ ": You discovered a Hidden Passageway!");
							//							System.out
							//							.println(p.getName()
							//									+ ": You discovered a path leading to Clearing "
							//									+ board[locx][locy]
							//											.getClearing(cl1));
							break;
						}
					}
				} else {
					JOptionPane.showMessageDialog(frame, p.getName() 
							+ ": There are not SecretPathways in this clearing!");
					//					System.out
					//					.println(p.getName()
					//							+ ": There are not SecretPathways in this clearing!");
				}
			} else if (diceRoll == 4) {
				// DISCOVER CHITS
				int count = 0;
				Chit temp = null;
				ArrayList<Chit> temp2 = new ArrayList<Chit>();
				for(int i = 0; i < board[locx][locy].getAllChits().size(); i++) {
					if(board[locx][locy].getChitAt(i).isHidden() == 1  && board[locx][locy].getChitAt(i).getChitType() == 'G') {
						temp = board[locx][locy].getChitAt(i);
						temp2.add(board[locx][locy].getChitAt(i));
						count++;

					}
				}
				if (count == 1) {
					///QUESTION!!!!!!!!
					if(temp.isLost() == true) {
						String tsc = "";
						if(temp.getChitName().equals("LOST CASTLE")) {
							board[locx][locy].removeChit(0);

							for(int i = 0; i < MRServer.tsc.lostCastle.getChits().size(); i++) {
								board[locx][locy].addChit(MRServer.tsc.lostCastle.getChits().get(i));
								p.addDiscoveredChit(MRServer.tsc.lostCastle.getChits().get(i));
								tsc += MRServer.tsc.lostCastle.getChits().get(i).getChitName() + MRServer.tsc.lostCastle.getChits().get(i).getChitNum() +"\n";
							}

							JOptionPane.showMessageDialog(frame, p.getName() 
									+ "You have discovered the LOST CASTLE chit and has been exchanged for the following: \n" + tsc);

						}
						else if(temp.getChitName().equals( "LOST CITY")){
							board[locx][locy].removeChit(0);
							for(int i = 0; i < MRServer.tsc.lostCity.getChits().size(); i++) {
								board[locx][locy].addChit(MRServer.tsc.lostCity.getChits().get(i));
								p.addDiscoveredChit(MRServer.tsc.lostCity.getChits().get(i));
								tsc += MRServer.tsc.lostCity.getChits().get(i).getChitName() + MRServer.tsc.lostCity.getChits().get(i).getChitNum() +"\n";
							}

							JOptionPane.showMessageDialog(frame, p.getName() 
									+ ": You have discovered the LOST CITY chit and has been exchanged for the following: \n" + tsc);
						}

					}else {
						boolean check = false;
						for(int i = 0; i < p.getDiscoveredChits().size(); i++) {
							if(p.getDiscoveredChits().get(i).getChitName() == temp.getChitName() && p.getDiscoveredChits().get(i).getChitNum() == temp.getChitNum()) {
								check = true;
							}
						}
						if(check == false) {
							p.addDiscoveredChit(temp);
							JOptionPane.showMessageDialog(frame, p.getName() + ": You Have discovered " + temp.getChitName() + temp.getChitNum());
						}
						else {
							JOptionPane.showMessageDialog(frame, p.getName() + ": Nothing new discovered!");
						}
					}

				} else if(count > 1) {
					ArrayList<Chit> temp3 = new ArrayList<Chit>();
					for(int i = 0; i < temp2.size(); i++) {
						boolean check = false;
						for(int j = 0; j < temp2.size(); j++) {
							if(temp2.get(i).getChitName() == p.getDiscoveredChits().get(j).getChitName() && temp2.get(i).getChitNum() == p.getDiscoveredChits().get(j).getChitNum()) {
								check = true;
							}
						}
						if(check == false) {
							temp3.add(temp2.get(i));
						}
					}
					if(temp3.size() > 0) {
						String h ="";
						for(int i = 0; i <temp3.size(); i++) {
							p.addDiscoveredChit(temp3.get(i));
							h += temp3.get(i).getChitName() + temp3.get(i).getChitNum() + "\n";
						}

						JOptionPane.showMessageDialog(frame, p.getName() + "You have discovered the following Site Chits: \n" + h);
					}

				} else if(count == 0) {
					JOptionPane.showMessageDialog(frame, p.getName() + "No Site Chits here to be discovered!");
				}

			} else if (diceRoll == 5) {
				// NOTHING
				JOptionPane.showMessageDialog(frame, p.getName() 
						+ ": You rolled a five. There is nothing you can do!");
				//				System.out.println(p.getName()
				//						+ ": You rolled a five. There is nothing you can do!");
			} else if (diceRoll == 6) {
				// NOTHING
				JOptionPane.showMessageDialog(frame, p.getName() 
						+ ": You rolled a six. There is nothing you can do!");
				//				System.out.println(p.getName()
				//						+ ": You rolled a six. There is nothing you can do!");
			}
		}
		if (searchTable.equalsIgnoreCase("LOOT")) {
			if (diceRoll == 1) {
				// TAKE TOP TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if(result != null) {
					if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 0) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(0);
						retValue = lootingChoice(p, temp, frame, board[locx][locy]);

					}
					else {
						JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": No Site Chits to loot!");
				}

			} else if (diceRoll == 2) {
				// TAKE 2ND TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if(result != null) {
					if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 1) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(1);
						retValue = lootingChoice(p, temp, frame, board[locx][locy]);
					}
					else {
						JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": No Site Chits to loot!");
				}

			} else if (diceRoll == 3) {
				// TAKE 3RD TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if(result != null) {
					if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 2) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(2);
						retValue = lootingChoice(p, temp, frame, board[locx][locy]);
					}
					else {
						JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": No Site Chits to loot!");
				}

			} else if (diceRoll == 4) {
				// TAKE 4TH TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if(result != null) {
					if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 3) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(3);
						retValue = lootingChoice(p, temp, frame, board[locx][locy]);
					}
					else {
						JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": No Site Chits to loot!");
				}

			} else if (diceRoll == 5) {
				// TAKE 5TH TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if(result != null) {
					if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 4) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(4);
						retValue = lootingChoice(p, temp, frame, board[locx][locy]);
					}
					else {
						JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": No Site Chits to loot!");
				}

			} else if (diceRoll == 6) {
				// TAKE 6TH TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if(result != null) {
					if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 5) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(5);
						retValue = lootingChoice(p, temp, frame, board[locx][locy]);
					}
					else {
						JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
					}
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": No Site Chits to loot!");
				}

			}
		}if (searchTable.equalsIgnoreCase("LOOT CLEARING")) {
			if (diceRoll == 1) {
				// TAKE TOP TREASURE IN PILE
				if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getTreasuresInClearing().size() > 0) {
					Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeTreasureInClearing(0);
					retValue = lootingChoice(p, temp, frame, board[locx][locy]);
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
				}
			} else if (diceRoll == 2) {
				// TAKE 2ND TREASURE IN PILE
				if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getTreasuresInClearing().size() > 1) {
					Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeTreasureInClearing(1);
					retValue = lootingChoice(p, temp, frame, board[locx][locy]);
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
				}
			} else if (diceRoll == 3) {
				// TAKE 3RD TREASURE IN PILE
				if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getTreasuresInClearing().size() > 2) {
					Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeTreasureInClearing(2);
					retValue = lootingChoice(p, temp, frame, board[locx][locy]);
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
				}

			} else if (diceRoll == 4) {
				// TAKE 4TH TREASURE IN PILE
				if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getTreasuresInClearing().size() > 3) {
					Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeTreasureInClearing(3);
					retValue =lootingChoice(p, temp, frame, board[locx][locy]);
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
				}

			} else if (diceRoll == 5) {
				// TAKE 5TH TREASURE IN PILE
				if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getTreasuresInClearing().size() > 4) {
					Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeTreasureInClearing(4);
					retValue = lootingChoice(p, temp, frame, board[locx][locy]);
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
				}

			} else if (diceRoll == 6) {
				// TAKE 6TH TREASURE IN PILE
				if(board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getTreasuresInClearing().size() > 5) {
					Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeTreasureInClearing(5);
					retValue = lootingChoice(p, temp, frame, board[locx][locy]);
				}
				else {
					JOptionPane.showMessageDialog(frame, p.getName() + ": Not enough treasures to loot!");
				}

			}
		}
		if(rets.trim().equals("")){
			rets = "N";
		}
		return retValue + "#" + rets;

	}



	//ACTIVATE
	public static String activateSomething(Character p) {
		String retValue="";

		String[] types = {"WEAPONS", "ARMOR", "ACTION CHITS"};
		JFrame frame = new JFrame("Activate");
		String choice = (String) JOptionPane.showInputDialog(frame, "What would you like to activate right now?", "Activate", 
				JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
		// message to be sent to the server
		String serverMessage=choice;
		boolean toSend = true;
		if(choice == types[0]) {
			boolean currActive = false;
			int curr = 0;
			for(int i = 0; i <p.getweaponList().size(); i++) {
				if(p.getweaponList().get(i).isActive()) {
					currActive = true;
					curr = i;
				}
			}
			if(currActive == true) {
				String[] weapons = new String[p.getweaponList().size()-1];
				int op = JOptionPane.showConfirmDialog(frame, "You currently have the " + p.getweaponList().get(curr).getName() + "Weapon activated. \n"
						+ "Would you like to activate a different weapon?", "Activate Weapons", JOptionPane.YES_NO_OPTION);
				serverMessage+="-"+op;
				if(op == JOptionPane.YES_OPTION) {
					p.getweaponList().get(curr).unActivate();
					for(int j = 0; j < curr; j++) {
						weapons[j] = p.getweaponList().get(j).getName();
					}
					for(int j = curr+1; j < p.getweaponList().size(); j++) {
						weapons[j] = p.getweaponList().get(j).getName();
					}

					if(weapons.length>0){
						String w = (String) JOptionPane.showInputDialog(frame, "Which weapon would you like to activate?", "Activating weapon", 
								JOptionPane.QUESTION_MESSAGE, null, weapons, weapons[0]);
						serverMessage+="-"+w;
						for(int j = 0; j < p.getweaponList().size(); j++) {
							if(w == p.getweaponList().get(j).getName()) {
								p.getweaponList().get(j).activate();
								JOptionPane.showMessageDialog(frame, "You have activated "+p.getweaponList().get(j).getName()+".");
								break;
							}
						}
					}else{
						JOptionPane.showMessageDialog(frame, "You have no other weapons to be activated.");
						toSend = false;
					}
				}
				else {
					toSend=false;
					JOptionPane.showMessageDialog(frame, "Nothing will be activated.");
				}
			}
			else {
				String[] weapons = new String[p.getweaponList().size()];
				for(int i = 0; i < p.getweaponList().size(); i++) {
					weapons[i] = p.getweaponList().get(i).getName();
				}
				String w = (String) JOptionPane.showInputDialog(frame, "Which weapon would you like to activate?", "Activating weapon", 
						JOptionPane.QUESTION_MESSAGE, null, weapons, weapons[0]);
				serverMessage+="-"+w;
				for(int j = 0; j < p.getweaponList().size(); j++) {
					if(w == p.getweaponList().get(j).getName()) {
						p.getweaponList().get(j).activate();
						JOptionPane.showMessageDialog(frame, "You have activated "+p.getweaponList().get(j).getName()+".");
						break;
					}
				}

			}
		}
		else if(choice == types[1]) {
			int count = 0;
			for(int i = 0; i < p.getArmor().size(); i++) {
				if(p.getArmor().get(i).isActive() == 0) {
					count++;
				}
			}
			if(count != 0) {
				String[] armors = new String[count];
				for(int i = 0; i < p.getArmor().size(); i++) {
					if(p.getArmor().get(i).isActive() == 0) {
						armors[i] = p.getArmor().get(i).getName();
					}
				}
				String w = (String) JOptionPane.showInputDialog(frame, "Which Armor would you like to activate?", "Activating Armor", 
						JOptionPane.QUESTION_MESSAGE, null, armors, armors[0]);
				serverMessage+="-"+w;
				for(int j = 0; j < p.getweaponList().size(); j++) {
					if(w.equals(p.getArmor().get(j).getName())) {
						p.getArmor().get(j).activate();
						JOptionPane.showMessageDialog(frame, "You have activated "+p.getArmor().get(j).getName()+".");
						break;
					}
				}
			}
			else {
				toSend=false;
				JOptionPane.showMessageDialog(frame, "There are no inactive armors to Activate.");
			}


		}
		else if(choice == types[2]) {
			int count = 0;
			for(int i = 0; i < p.getActionChits().size(); i++) {
				if(p.getActionChits().get(i).isActive() == 0) {
					count++;
				}
			}
			String[] actions = new String[count];
			for(int i = 0; i < p.getActionChits().size(); i++) {
				if(p.getActionChits().get(i).isActive() == 0) {
					actions[i] = p.getActionChits().get(i).getName();
				}
			}			
			String w = (String) JOptionPane.showInputDialog(frame, "Which Action would you like to activate?", "Activating Action Chits", 
					JOptionPane.QUESTION_MESSAGE, null, actions, actions[0]);
			serverMessage+="-"+w;
			for(int j = 0; j < p.getweaponList().size(); j++) {
				if(w == p.getArmor().get(j).getName()) {
					p.getArmor().get(j).activate();
					JOptionPane.showMessageDialog(frame, "You have activated "+p.getActionChits().get(j).getName()+".");
					break;
				}
			}
		}
		if(!toSend)
			retValue="";
		else
			retValue = serverMessage;
		return retValue;

	}

	public static void server_activateSomething(String resp, Character p) {

		String crumbs[]= resp.split("-"); 
		String[] types = {"WEAPONS", "ARMOR", "ACTION CHITS"};
		//JFrame frame = new JFrame("Activate");
		String choice = crumbs[3];
		// message to be sent to the server
		String serverMessage=choice;
		boolean toSend = true;
		if(choice.equals(types[0])) {
			boolean currActive = false;
			int curr = 0;
			for(int i = 0; i <p.getweaponList().size(); i++) {
				if(p.getweaponList().get(i).isActive()) {
					currActive = true;
					curr = i;
				}
			}

			if(currActive == true) {
				String[] weapons = new String[p.getweaponList().size()-1];
				int op = Integer.parseInt(crumbs[4]);
				serverMessage+="-currActive-"+op;
				if(op == JOptionPane.YES_OPTION) {
					p.getweaponList().get(curr).unActivate();
					for(int j = 0; j < curr; j++) {
						weapons[j] = p.getweaponList().get(j).getName();
					}
					for(int j = curr+1; j < p.getweaponList().size(); j++) {
						weapons[j] = p.getweaponList().get(j).getName();
					}

					String w = crumbs[5];
					for(int j = 0; j < p.getweaponList().size(); j++) {
						if(w.equals(p.getweaponList().get(j).getName())) {
							p.getweaponList().get(j).activate();
							System.out.println(p.getName() + ": You have activated "+p.getweaponList().get(j).getName()+".");
							break;
						}
					}
				}
			}
			else {
				String[] weapons = new String[p.getweaponList().size()];
				for(int i = 0; i < p.getweaponList().size(); i++) {
					weapons[i] = p.getweaponList().get(i).getName();
				}
				String w = crumbs[4];
				for(int j = 0; j < p.getweaponList().size(); j++) {
					if(w.equals(p.getweaponList().get(j).getName())) {
						p.getweaponList().get(j).activate();
						System.out.println(p.getName() + ": You have activated "+p.getweaponList().get(j).getName()+".");
						break;
					}
				}

			}
		}
		else if(choice.equals(types[1])) {
			int count = 0;
			for(int i = 0; i < p.getArmor().size(); i++) {
				if(p.getArmor().get(i).isActive() == 0) {
					count++;
				}
			}
			if(count != 0) {
				String[] armors = new String[count];
				for(int i = 0; i < p.getArmor().size(); i++) {
					if(p.getArmor().get(i).isActive() == 0) {
						armors[i] = p.getArmor().get(i).getName();
					}
				}
				String w = crumbs[4];
				for(int j = 0; j < p.getweaponList().size(); j++) {
					if(w.equals(p.getArmor().get(j).getName())) {
						p.getArmor().get(j).activate();
						System.out.println( "You have activated "+p.getArmor().get(j).getName()+".");
						break;
					}
				}
			}
		}
		else if(choice.equals(types[2])) {
			int count = 0;
			for(int i = 0; i < p.getActionChits().size(); i++) {
				if(p.getActionChits().get(i).isActive() == 0) {
					count++;
				}
			}
			String[] actions = new String[count];
			for(int i = 0; i < p.getActionChits().size(); i++) {
				if(p.getActionChits().get(i).isActive() == 0) {
					actions[i] = p.getActionChits().get(i).getName();
				}
			}			
			String w = crumbs[4];
			serverMessage+="-"+w;
			for(int j = 0; j < p.getweaponList().size(); j++) {
				if(w.equals(p.getArmor().get(j).getName())) {
					p.getArmor().get(j).activate();
					System.out.println( "You have activated "+p.getActionChits().get(j).getName()+".");
					break;
				}
			}
		}
		// send Message to the server.

	}

	public static String rest(Character p) {
		String retvalue = "";
		JFrame frame = new JFrame("Rest Activity");
		int[] able = new int[3];
		for(int i = 0 ; i < p.getActionChits().size(); i++) {
			if(p.getActionChits().get(i).isFatigued() && p.getActionChits().get(i).getEffort() <= 1) {
				able[0]++;
			}
			if(p.getActionChits().get(i).isWounded() && p.getActionChits().get(i).getEffort() == 1) {
				able[1]++;
			}
			if(p.getActionChits().get(i).isWounded() && p.getActionChits().get(i).getEffort() == 0) {
				able[2]++;
			}
		}


		if(able[0] > 0 && able[1] > 0 && able[2] > 0) {
			String[] choices = {"Activate One Fatigued Asterisk back to Play", "Convert One Wounded Asterisk to a Fatigued Asterisk", 
			"Convert one wounded chit with no asterisk to an in-play chit"};
			String f = (String) JOptionPane.showInputDialog(frame, "How would you like to rest?", "Resting", 
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			retvalue = restChoice(p, f, frame);
		}else if(able[0] == 0 && able[1] > 0 && able[2] > 0) {
			String[] choices = {"Convert One Wounded Asterisk to a Fatigued Asterisk", "Convert one wounded chit with no asterisk to an in-play chit"};
			String f = (String) JOptionPane.showInputDialog(frame, "How would you like to rest?", "Resting", 
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			retvalue = restChoice(p, f, frame);
		}else if(able[0] > 0 && able[1] == 0 && able[2] > 0) {
			String[] choices = {"Activate One Fatigued Asterisk back to Play", "Convert one wounded chit with no asterisk to an in-play chit"};
			String f = (String) JOptionPane.showInputDialog(frame, "How would you like to rest?", "Resting", 
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			retvalue = restChoice(p, f, frame);
		} else if(able[0] > 0 && able[1] > 0 && able[2] == 0) {
			String[] choices = {"Activate One Fatigued Asterisk back to Play", "Convert One Wounded Asterisk to a Fatigued Asterisk"};
			String f = (String) JOptionPane.showInputDialog(frame, "How would you like to rest?", "Resting", 
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			retvalue = restChoice(p, f, frame);
		} else if(able[0] == 0 && able[1] == 0 && able[2] > 0) {
			String[] choices = {"Convert one wounded chit with no asterisk to an in-play chit"};
			String f = (String) JOptionPane.showInputDialog(frame, "How would you like to rest?", "Resting", 
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			retvalue = restChoice(p, f, frame);
		} else if(able[0] == 0 && able[1] > 0 && able[2] == 0) {
			String[] choices = {"Convert One Wounded Asterisk to a Fatigued Asterisk"};
			String f = (String) JOptionPane.showInputDialog(frame, "How would you like to rest?", "Resting", 
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			retvalue = restChoice(p, f, frame);
		} else if(able[0] > 0 && able[1] == 0 && able[2] == 0) {
			String[] choices = {"Activate One Fatigued Asterisk back to Play"};
			String f = (String) JOptionPane.showInputDialog(frame, "How would you like to rest?", "Resting", 
					JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			retvalue = restChoice(p, f, frame);
		} else if(able[0] == 0 && able[1] == 0 && able[2] == 0) {
			retvalue = "You dont have any chits to rest.";
			JOptionPane.showMessageDialog(frame, "You dont have any chits to rest.");
		}
		return retvalue;
	}
	public static String restChoice(Character p, String choice, JFrame frame) {
		String retvalue="";
		if(choice == "Activate One Fatigued Asterisk back to Play") {
			int count = 0;
			for(int i = 0; i <p.getActionChits().size(); i++) {
				if(p.getActionChits().get(i).isFatigued() && p.getActionChits().get(i).getEffort() <= 1) {
					count++;
				}
			}
			if(count > 0) {
				String[] fatigues = new String[count];
				for(int i = 0; i < p.getActionChits().size(); i++) {
					if(p.getActionChits().get(i).isFatigued() && p.getActionChits().get(i).getEffort() <= 1) {
						fatigues[i] = p.getActionChits().get(i).getName();
					}
				}
				String f = (String) JOptionPane.showInputDialog(frame, "Activate One Fatigued Asterisk back to Play", "Resting", 
						JOptionPane.QUESTION_MESSAGE, null, fatigues, fatigues[0]);
				for(int i = 0; i < p.getActionChits().size(); i++) {
					if(p.getActionChits().get(i).getName() == f) {
						retvalue=p.getActionChits().get(i).getName() +"#"+"FATIGUE";
						p.getActionChits().get(i).unFatigue();
						p.getActionChits().get(i).activate();
						JOptionPane.showMessageDialog(frame, "You Rested "+p.getActionChits().get(i).getName()+" chit.");
						break;
					}
				}
			} else {
				retvalue = "No Fatigued chits to Rest";
				JOptionPane.showMessageDialog(frame, "No Fatigued chits to Rest");
			}
		} 
		else if(choice == "Convert One Wounded Asterisk to a Fatigued Asterisk") {
			int count = 0;
			for(int i = 0; i <p.getActionChits().size(); i++) {
				if(p.getActionChits().get(i).isWounded() && p.getActionChits().get(i).getEffort() == 1) {
					count++;
				}
			}
			if(count > 0) {
				String[] wounded = new String[count];
				for(int i = 0; i < p.getActionChits().size(); i++) {
					if(p.getActionChits().get(i).isWounded() && p.getActionChits().get(i).getEffort() == 1) {
						wounded[i] = p.getActionChits().get(i).getName();
					}
				}
				String f = (String) JOptionPane.showInputDialog(frame, "Convert One Wounded Asterisk to a Fatigued Asterisk", "Resting", 
						JOptionPane.QUESTION_MESSAGE, null, wounded, wounded[0]);
				for(int i = 0; i < p.getActionChits().size(); i++) {
					if(p.getActionChits().get(i).getName() == f) {
						retvalue = p.getActionChits().get(i).getName()+"#"+"WOUNDEDFATIGUES";
						p.getActionChits().get(i).unWounded();
						p.getActionChits().get(i).makeFatigued();
						JOptionPane.showMessageDialog(frame, "You Rested "+p.getActionChits().get(i).getName()+" chit.");
						break;
					}
				}
			}
			else {
				retvalue = "No Wounded chits available";
				JOptionPane.showConfirmDialog(frame, "No Wounded chits available");
			}
		} else if(choice == "Convert one wounded chit with no asterisk to an in-play chit") {
			int count = 0;
			for(int i = 0; i <p.getActionChits().size(); i++) {
				if(p.getActionChits().get(i).isWounded() && p.getActionChits().get(i).getEffort() == 0) {
					count++;
				}
			}
			if(count > 0) {
				String[] fatigues = new String[count];
				for(int i = 0; i < p.getActionChits().size(); i++) {
					if(p.getActionChits().get(i).isWounded() && p.getActionChits().get(i).getEffort() == 0) {
						fatigues[i] = p.getActionChits().get(i).getName();
					}
				}
				String f = (String) JOptionPane.showInputDialog(frame, "Convert one wounded chit with no asterisk to an in-play chit", "Resting", 
						JOptionPane.QUESTION_MESSAGE, null, fatigues, fatigues[0]);
				for(int i = 0; i < p.getActionChits().size(); i++) {
					if(p.getActionChits().get(i).getName() == f) {
						retvalue = p.getActionChits().get(i).getName()+"#"+"WOUNDS";
						p.getActionChits().get(i).unFatigue();
						p.getActionChits().get(i).unWounded();
						p.getActionChits().get(i).activate();
						JOptionPane.showMessageDialog(frame, "You Rested "+p.getActionChits().get(i).getName()+" chit.");
						break;
					}
				}
			} else {
				retvalue = "No Fatigued chits to Rest";
				JOptionPane.showMessageDialog(frame, "No Fatigued chits to Rest");
			}
		}
		return retvalue;
	}



	public static String searchTableCHOICE() {

		String[] choose = { "CLUES AND PATHS", "HIDDEN ENEMIES AND PATHS",
				"HIDDEN ENEMIES", "CLUES", "PASSAGES AND CLUES", "PASSAGES",
				"DISCOVER CHITS", "NOTHING" };

		JFrame frame = new JFrame("Search Table Choice");
		String choice = (String) JOptionPane.showInputDialog(frame,
				"Which table row will you choose?", "Search table",
				JOptionPane.QUESTION_MESSAGE, null, choose, choose[0]);

		return choice;
	}

	public static Chit looting(Character p, Tile board) {
		Chit result = null;
		Chit site = null;
		for(int i = 0; i < board.getAllChits().size(); i++) {
			if(board.getChitAt(i).isHidden() == 0 && board.getChitAt(i).getChitType() == 'G') {
				site = board.getChitAt(i);
			}
		}
		if(site != null) {
			boolean check = false;
			for(int i =0; i < p.getDiscoveredChits().size(); i++) {
				if(site.getChitName() == p.getDiscoveredChits().get(i).getChitName() && site.getChitNum() == p.getDiscoveredChits().get(i).getChitNum()) {
					check = true;
					break;
				}
			} 
			if(check == true) {
				if(site.getChitNum() == p.getClearingLocation().getClearingNum()) {
					result = site;
				}
			}
		}
		return result;
	}



	public static int lootingChoice(Character p, Item m, JFrame frame, Tile board) {
		int retValue = -1;
		String[] choices= {"Activate Treasure", "Keep it InActive", "Abandon Treasure"};
		int n = JOptionPane.showOptionDialog(frame, "What do you want to do with the looted treasure?",
				"Looting the Treasure",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,
				null, choices, choices[0]);
		retValue = n;
		if(n == JOptionPane.YES_OPTION) {
			if(m.getItemType() == 'A') {
				Armor a = (Armor) m;
				a.activate();
				p.getArmor().add(a);
				p.setGoldRecorded(p.getGoldRecorded()+a.getPrice());
			}else if(m.getItemType() == 'W') {
				Weapons w = (Weapons) m;
				w.activate();
				p.getweaponList().add(w);
				p.setGoldRecorded(p.getGoldRecorded()+w.getPrice());
			}else if(m.getItemType() == 'T') {
				TreasureCard t = (TreasureCard) m;
				if(t.isGreatTreasure() == true) {
					String[] type = t.getChit().split("-");
					ActionChit chit = new ActionChit(t.getName(), type[0], type[1].charAt(0), Integer.parseInt(type[3]), 0, null, null);
					chit.setIImage(t.getImage());
					chit.setAImage(t.getImage());
					p.getActionChits().add(chit);
					p.setGoldRecorded(p.getGoldRecorded()+t.getPrice());
					p.setFameRecorded(p.getFameRecorded()+t.getFame());
					p.setNotorietyRecorded(p.getNotorityRecordedt()+ t.getNotoriety());
				}
				p.addTreasureToPile(t);
			}
			JOptionPane.showMessageDialog(frame, m.getName() + " has been activated.");
		}else if (n == JOptionPane.NO_OPTION) {
			if(m.getItemType() == 'A') {
				Armor a = (Armor) m;
				p.getArmor().add(a);
				p.setGoldRecorded(p.getGoldRecorded()+a.getPrice());
			}else if(m.getItemType() == 'W') {
				Weapons w = (Weapons) m;
				p.getweaponList().add(w);
				p.setGoldRecorded(p.getGoldRecorded()+w.getPrice());
			}else if(m.getItemType() == 'T') {
				TreasureCard t = (TreasureCard) m;
				p.addTreasureToPile(t);
			}
			JOptionPane.showMessageDialog(frame, m.getName() + " has been added to your belongings as inactive.");

		}else if (n == JOptionPane.CANCEL_OPTION) {
			if(m.getItemType() == 'A') {
				Armor a = (Armor) m;
				board.getClearing(p.getClearingLocation().getClearingNum()).addArmor(a);
			}else if(m.getItemType() == 'W') {
				Weapons w = (Weapons) m;
				board.getClearing(p.getClearingLocation().getClearingNum()).addWeapon(w);
			}else if(m.getItemType() == 'T') {
				TreasureCard t = (TreasureCard) m;
				if(t.isGreatTreasure() == true) {
					String[] type = t.getChit().split("-");
					ActionChit chit = new ActionChit(t.getName(), type[0], type[1].charAt(0), Integer.parseInt(type[3]), 0, null, null);
					chit.setIImage(t.getImage());
					chit.setAImage(t.getImage());
					p.getActionChits().add(chit);
					p.setGoldRecorded(p.getGoldRecorded()+t.getPrice());
					p.setFameRecorded(p.getFameRecorded()+t.getFame());
					p.setNotorietyRecorded(p.getNotorityRecordedt()+ t.getNotoriety());
				}
				board.getClearing(p.getClearingLocation().getClearingNum()).addTreasure(t);
			}
			JOptionPane.showMessageDialog(frame, m.getName() + " has been abandoned in the clearing.");

		}
		return retValue;

	}


}
