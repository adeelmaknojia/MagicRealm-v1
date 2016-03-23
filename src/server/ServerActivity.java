package server;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import main.ActionChit;
import main.Armor;
import main.Character;
import main.Chit;
import main.GameGUI;
import main.Item;
import main.TreasureCard;
import main.Weapons;
import Tiles.Tile;

public class ServerActivity {
	public static void search(Character p, String searchTable, int diceRoll,
			Tile[][] board, int choiceFromPlayer, String searchTableChoice) {
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
			if (t.getClearing(j) != null) {
				if (t.getClearing(j).getClearingNum() == p
						.getClearingLocation().getClearingNum()) {
					c = t.getClearing(j).getClearingNum();
				}
			}
		}
		if (searchTable.equalsIgnoreCase("PEER")) {
			if (diceRoll == 1) {// CHOICE
				String choice = searchTableChoice;
				if (choice.equals("CLUES AND PATHS")){
					search(p, "PEER", 2, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("HIDDEN ENEMIES AND PATHS")) {
					search(p, "PEER", 3, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals( "HIDDEN ENEMIES")) {
					search(p, "PEER", 4, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("CLUES")) {
					search(p, "PEER", 5, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("PASSAGES AND CLUES")) {
					search(p, "LOCATE", 2, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("PASSAGES")) {
					search(p, "LOCATE", 3, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("DISCOVER CHITS")) {
					search(p, "LOCATE", 4, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("NOTHING")) {
					search(p, "PEER", 6, board, choiceFromPlayer, searchTableChoice);
				}

			} else if (diceRoll == 2) { // CLUES AND PATHS
				// CLUES
				search(p, "PEER", 5, board, choiceFromPlayer, searchTableChoice);

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
							System.out
									.println(p.getName()
											+ ": You discovered a Hidden Path leading to Clearing "
											+ board[locx][locy]
													.getClearing(cl2)
													.getClearingNum());
							discovered = 1;
							break;
						} else if (p.getClearingLocation().getClearingNum() == cl2) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
									.getClearing(cl1));
							System.out
									.println(p.getName()
											+ ": You discovered a Hidden Path leading to Clearing "
											+ board[locx][locy]
													.getClearing(cl1)
													.getClearingNum());
							discovered = 1;
							break;
						}
					}
					if (discovered == 0) {
						System.out
								.println(p.getName()
										+ ": There are no Hidden Paths connected to your clearing!");
					}
				} else {
					System.out
							.println(p.getName()
									+ ": There are no Hidden Paths in this tile for you to discover!");
				}

			} else if (diceRoll == 3) { // HIDDEN ENEMIES AND PATHS
				// HIDDEN ENEMIES
				search(p, "PEER", 4, board, choiceFromPlayer, searchTableChoice);

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
							System.out.println(p.getName()
									+ ": You discovered a Hidden Path!");
							System.out
									.println(p.getName()
											+ ": You discovered a path leading to Clearing "
											+ board[locx][locy]
													.getClearing(cl2));
							discovered = 1;
							break;
						} else if (p.getClearingLocation().getClearingNum() == cl2) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
									.getClearing(cl1));
							System.out.println(p.getName()
									+ ": You discovered a Hidden Path!");
							System.out
									.println(p.getName()
											+ ": You discovered a path leading to Clearing "
											+ board[locx][locy]
													.getClearing(cl1));
							discovered = 1;
							break;
						}
					}
					if (discovered == 0) {
						System.out
								.println(p.getName()
										+ ": There are no Hidden Paths connected to your clearing!");
					}
				} else {
					System.out
							.println(p.getName()
									+ ": There are no Hidden Paths in this tile for you to discover!");
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
								.getCharacterOnClearing(i).isHidden() == 1
								&& board[locx][locy]
										.getClearing(
												p.getClearingLocation()
														.getClearingNum())
										.getCharacterOnClearing(i).getName() != p
										.getName()) {
							he += (board[locx][locy].getClearing(
									p.getClearingLocation().getClearingNum())
									.getCharacterOnClearing(i).getCounterName())
									+ "\n";
						}

					}

					System.out.println(p.getName()
							+ ":You found these hidden enemies " + he);
				} else {
					System.out
							.println(p.getName()
									+ ": There are no hidden Enemies in this clearing ");
				}
			} else if (diceRoll == 5) {
				// CLUES
				int count = 0;
				for (int i = 0; i < board[locx][locy].getAllChits().size(); i++) {
					if (board[locx][locy].getChitAt(i).isHidden() == 1) {
						count++;
					}
				}
				if (count > 0) {

					System.out.println(p.getName()
							+ ": Sneak Peek at Chits located in "
							+ board[locx][locy].getName() + ":");
					String chts = "";
					for (int i = 0; i < board[locx][locy].getAllChits().size(); i++) {
						if (board[locx][locy].getChitAt(i).isHidden() == 1) {
							chts += board[locx][locy].getChitAt(i).toString()
									+ "\n";
							// System.out.println(board[locx][locy].getChitAt(i)
							// .toString());
						}
					}
					System.out.println(p.getName()
							+ ": Sneak Peek at Chits located in"
							+ board[locx][locy].getName() + ": \n" + chts);
				} else {
					System.out.println(p.getName()
							+ ": There are no Map chits in this tile for you to Peek at!");
					// System.out
					// .println(p.getName()
					// +
					// "There are no Map chits in this tile for you to Peek at!");
				}
			} else if (diceRoll == 6) {
				// NOTHING
				System.out.println(p.getName()
						+ ": You rolled a six. There is nothing you can do!");
			}
		}
		if (searchTable.equalsIgnoreCase("LOCATE")) {
			if (diceRoll == 1) { // CHOICE
				String choice = searchTableChoice;
				if (choice.equals("CLUES AND PATHS")) {
					search(p, "PEER", 2, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("HIDDEN ENEMIES AND PATHS")) {
					search(p, "PEER", 3, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("HIDDEN ENEMIES")) {
					search(p, "PEER", 4, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("CLUES")) {
					search(p, "PEER", 5, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("PASSAGES AND CLUES")) {
					search(p, "LOCATE", 2, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("PASSAGES")) {
					search(p, "LOCATE", 3, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("DISCOVER CHITS")) {
					search(p, "LOCATE", 4, board, choiceFromPlayer, searchTableChoice);
				} else if (choice.equals("NOTHING")) {
					search(p, "PEER", 6, board, choiceFromPlayer, searchTableChoice);
				}
			} else if (diceRoll == 2) {// PASSAGES AND CLUES
				// PASSAGES
				search(p, "LOCATE", 3, board, choiceFromPlayer, searchTableChoice);
				// CLUES
				search(p, "PEER", 5, board, choiceFromPlayer, searchTableChoice);

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

							System.out.println(p.getName()
									+ ": You have discovered a Hidden Passage leading to Clearing"
													+ board[locx][locy]
															.getClearing(cl2)
															.getClearingNum());
							break;
						} else if (p.getClearingLocation().getClearingNum() == cl2) {
							p.addPathway(p.getClearingOnPathway(p
									.getClearingLocation()), board[locx][locy]
									.getClearing(cl1));

							System.out.println(p.getName()
									+ ": You have discovered a Hidden Passage leading to Clearing"
													+ board[locx][locy]
															.getClearing(cl1)
															.getClearingNum());

							break;
						}
					}
				} else {
					System.out.println(p.getName()
							+ ": There are not SecretPathways in this clearing!");
				}
			} else if (diceRoll == 4) {
				// DISCOVER CHITS
				int count = 0;
				Chit temp = null;
				ArrayList<Chit> temp2 = new ArrayList<Chit>();
				for (int i = 0; i < board[locx][locy].getAllChits().size(); i++) {
					if (board[locx][locy].getChitAt(i).isHidden() == 1
							&& board[locx][locy].getChitAt(i).getChitType() == 'G') {
						temp = board[locx][locy].getChitAt(i);
						temp2.add(board[locx][locy].getChitAt(i));
						count++;

					}
				}
				if (count == 1) {
					// /QUESTION!!!!!!!!

					if (temp.isLost() == true) {
						String tsc = "";
						if (temp.getChitName().equals("LOST CASTLE")) {
							board[locx][locy].removeChit(0);

							for (int i = 0; i < MRServer.tsc.lostCastle
									.getChits().size(); i++) {
								board[locx][locy]
										.addChit(MRServer.tsc.lostCastle
												.getChits().get(i));
								p.addDiscoveredChit(MRServer.tsc.lostCastle
										.getChits().get(i));
								tsc += MRServer.tsc.lostCastle.getChits().get(i)
										.getChitName()
										+ MRServer.tsc.lostCastle.getChits()
												.get(i).getChitNum() + "\n";
							}

							System.out.println(p.getName()
									+ ": You have discovered the LOST CASTLE chit and has been exchanged for the following: \n"
													+ tsc);

						} else if (temp.getChitName().equals("LOST CITY")) {
							board[locx][locy].removeChit(0);
							for (int i = 0; i < MRServer.tsc.lostCity.getChits()
									.size(); i++) {
								board[locx][locy].addChit(MRServer.tsc.lostCity
										.getChits().get(i));
								p.addDiscoveredChit(MRServer.tsc.lostCity
										.getChits().get(i));
								tsc += MRServer.tsc.lostCity.getChits().get(i)
										.getChitName()
										+ MRServer.tsc.lostCity.getChits()
												.get(i).getChitNum() + "\n";
							}

							System.out.println(p.getName()
									+ ": You have discovered the LOST CITY chit and has been exchanged for the following: \n"
													+ tsc);
						}

					} else {
						boolean check = false;
						for (int i = 0; i < p.getDiscoveredChits().size(); i++) {
							if (p.getDiscoveredChits().get(i).getChitName() == temp
									.getChitName()
									&& p.getDiscoveredChits().get(i)
											.getChitNum() == temp.getChitNum()) {
								check = true;
							}
						}
						if (check == false) {
							p.addDiscoveredChit(temp);
							System.out.println(p.getName()
									+ ": You Have discovered "
											+ temp.getChitName()
											+ temp.getChitNum());
						} else {
							System.out.println(p.getName()
									+ ": Nothing new discovered!");
						}
					}

				} else if (count > 1) {
					ArrayList<Chit> temp3 = new ArrayList<Chit>();
					for (int i = 0; i < temp2.size(); i++) {
						boolean check = false;
						for (int j = 0; j < temp2.size(); j++) {
							if (temp2.get(i).getChitName() == p
									.getDiscoveredChits().get(j).getChitName()
									&& temp2.get(i).getChitNum() == p
											.getDiscoveredChits().get(j)
											.getChitNum()) {
								check = true;
							}
						}
						if (check == false) {
							temp3.add(temp2.get(i));
						}
					}
					if (temp3.size() > 0) {
						String h = "";
						for (int i = 0; i < temp3.size(); i++) {
							p.addDiscoveredChit(temp3.get(i));
							h += temp3.get(i).getChitName()
									+ temp3.get(i).getChitNum() + "\n";
						}

						System.out.println(p.getName()
								+ ": You have discovered the following Site Chits: \n"
												+ h);
					}

				} else if (count == 0) {
					System.out.println(p.getName()
							+ ": No Site Chits here to be discovered!");
				}

			} else if (diceRoll == 5) {
				// NOTHING
				System.out.println(p.getName()
						+ ": You rolled a five. There is nothing you can do!");
				// System.out.println(p.getName()
				// + ": You rolled a five. There is nothing you can do!");
			} else if (diceRoll == 6) {
				// NOTHING
				System.out.println(p.getName()
						+ ": You rolled a six. There is nothing you can do!");
				// System.out.println(p.getName()
				// + ": You rolled a six. There is nothing you can do!");
			}
		}
		if (searchTable.equalsIgnoreCase("LOOT")) {
			if (diceRoll == 1) {
				// TAKE TOP TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if (result != null) {
					if (board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 0) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(0);
						lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);

					} else {
						System.out.println(p.getName()+ ": Not enough treasures to loot!");
					}
				} else {
					System.out.println(p.getName()+ ": No Site Chits to loot!");
				}

			} else if (diceRoll == 2) {
				// TAKE 2ND TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if (result != null) {
					if (board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).getSiteTreasures().size() > 1) {
						Item temp = board[locx][locy].getClearing(p.getClearingLocation().getClearingNum()).removeSiteTreasures(1);
						lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
					} else {
						System.out.println(p.getName()
								+ ": Not enough treasures to loot!");
					}
				} else {
					System.out.println(p.getName()
							+ ": No Site Chits to loot!");
				}

			} else if (diceRoll == 3) {
				// TAKE 3RD TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if (result != null) {
					if (board[locx][locy]
							.getClearing(
									p.getClearingLocation().getClearingNum())
							.getSiteTreasures().size() > 2) {
						Item temp = board[locx][locy].getClearing(
								p.getClearingLocation().getClearingNum())
								.removeSiteTreasures(2);
						lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
					} else {
						System.out.println(p.getName()
								+ ": Not enough treasures to loot!");
					}
				} else {
					System.out.println(p.getName()
							+ ": No Site Chits to loot!");
				}

			} else if (diceRoll == 4) {
				// TAKE 4TH TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if (result != null) {
					if (board[locx][locy]
							.getClearing(
									p.getClearingLocation().getClearingNum())
							.getSiteTreasures().size() > 3) {
						Item temp = board[locx][locy].getClearing(
								p.getClearingLocation().getClearingNum())
								.removeSiteTreasures(3);
						lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
					} else {
						System.out.println(p.getName()
								+ ": Not enough treasures to loot!");
					}
				} else {
					System.out.println(p.getName()
							+ ": No Site Chits to loot!");
				}

			} else if (diceRoll == 5) {
				// TAKE 5TH TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if (result != null) {
					if (board[locx][locy]
							.getClearing(
									p.getClearingLocation().getClearingNum())
							.getSiteTreasures().size() > 4) {
						Item temp = board[locx][locy].getClearing(
								p.getClearingLocation().getClearingNum())
								.removeSiteTreasures(4);
						lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
					} else {
						System.out.println(p.getName()
								+ ": Not enough treasures to loot!");
					}
				} else {
					System.out.println(p.getName()
							+ ": No Site Chits to loot!");
				}

			} else if (diceRoll == 6) {
				// TAKE 6TH TREASURE IN PILE
				Chit result = looting(p, board[locx][locy]);
				if (result != null) {
					if (board[locx][locy]
							.getClearing(
									p.getClearingLocation().getClearingNum())
							.getSiteTreasures().size() > 5) {
						Item temp = board[locx][locy].getClearing(
								p.getClearingLocation().getClearingNum())
								.removeSiteTreasures(5);
						lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
					} else {
						System.out.println(p.getName()
								+ ": Not enough treasures to loot!");
					}
				} else {
					System.out.println(p.getName()
							+ ": No Site Chits to loot!");
				}

			}
		}
		if (searchTable.equalsIgnoreCase("LOOT CLEARING")) {
			if (diceRoll == 1) {
				// TAKE TOP TREASURE IN PILE
				if (board[locx][locy]
						.getClearing(p.getClearingLocation().getClearingNum())
						.getTreasuresInClearing().size() > 0) {
					Item temp = board[locx][locy].getClearing(
							p.getClearingLocation().getClearingNum())
							.removeTreasureInClearing(0);
					lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
				} else {
					System.out.println(p.getName()
							+ ": Not enough treasures to loot!");
				}
			} else if (diceRoll == 2) {
				// TAKE 2ND TREASURE IN PILE
				if (board[locx][locy]
						.getClearing(p.getClearingLocation().getClearingNum())
						.getTreasuresInClearing().size() > 1) {
					Item temp = board[locx][locy].getClearing(
							p.getClearingLocation().getClearingNum())
							.removeTreasureInClearing(1);
					lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
				} else {
					System.out.println(p.getName()
							+ ": Not enough treasures to loot!");
				}
			} else if (diceRoll == 3) {
				// TAKE 3RD TREASURE IN PILE
				if (board[locx][locy]
						.getClearing(p.getClearingLocation().getClearingNum())
						.getTreasuresInClearing().size() > 2) {
					Item temp = board[locx][locy].getClearing(
							p.getClearingLocation().getClearingNum())
							.removeTreasureInClearing(2);
					lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
				} else {
					System.out.println(p.getName()
							+ ": Not enough treasures to loot!");
				}
			} else if (diceRoll == 4) {
				// TAKE 4TH TREASURE IN PILE
				if (board[locx][locy]
						.getClearing(p.getClearingLocation().getClearingNum())
						.getTreasuresInClearing().size() > 3) {
					Item temp = board[locx][locy].getClearing(
							p.getClearingLocation().getClearingNum())
							.removeTreasureInClearing(3);
					lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
				} else {
					System.out.println(p.getName()
							+ ": Not enough treasures to loot!");
				}

			} else if (diceRoll == 5) {
				// TAKE 5TH TREASURE IN PILE
				if (board[locx][locy]
						.getClearing(p.getClearingLocation().getClearingNum())
						.getTreasuresInClearing().size() > 4) {
					Item temp = board[locx][locy].getClearing(
							p.getClearingLocation().getClearingNum())
							.removeTreasureInClearing(4);
					lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
				} else {
					System.out.println(p.getName()
							+ ": Not enough treasures to loot!");
				}

			} else if (diceRoll == 6) {
				// TAKE 6TH TREASURE IN PILE
				if (board[locx][locy]
						.getClearing(p.getClearingLocation().getClearingNum())
						.getTreasuresInClearing().size() > 5) {
					Item temp = board[locx][locy].getClearing(
							p.getClearingLocation().getClearingNum())
							.removeTreasureInClearing(5);
					lootingChoiceServer(p, temp, frame, board[locx][locy], choiceFromPlayer);
				} else {
					System.out.println(p.getName()
							+ ": Not enough treasures to loot!");
				}
			}
		}
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
	
	public static int lootingChoiceServer(Character p, Item m, JFrame frame, Tile board, int choiceFromPlayer) {
		int retValue = -1;
		String[] choices= {"Activate Treasure", "Keep it InActive", "Abandon Treasure"};
		int n = choiceFromPlayer;

		if(n == JOptionPane.YES_OPTION) {
			retValue = n;
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
			retValue = n;
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
			JOptionPane.showMessageDialog(frame, m.getName() + " has been added to your belongings as inactive.");

		}else if (n == JOptionPane.CANCEL_OPTION) {
			retValue = n;
			if(m.getItemType() == 'A') {
				Armor a = (Armor) m;
				board.getClearing(p.getClearingLocation().getClearingNum()).addArmor(a);
			}else if(m.getItemType() == 'W') {
				Weapons w = (Weapons) m;
				board.getClearing(p.getClearingLocation().getClearingNum()).addWeapon(w);
			}else if(m.getItemType() == 'T') {
				TreasureCard t = (TreasureCard) m;
				board.getClearing(p.getClearingLocation().getClearingNum()).addTreasure(t);
			}
			JOptionPane.showMessageDialog(frame, m.getName() + " has been abandoned in the clearing.");

		}

		return retValue;
	}

}
