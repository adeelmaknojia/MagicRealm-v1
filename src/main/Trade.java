package main;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Tiles.Tile;

public class Trade {

	//commenting for Tania :D
	public static String trade(Character p, Tile[][] board) {
		String retvalue="";
		JFrame frame = new JFrame("Trade Activity");
		Tile t = null;
		int locx = 0;
		int locy = 0;
		//Searches board for players location on board -> t is result tile
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
		//searches the tile for character's clearing location -> c is clearing number
		int c = 0;
		for (int j = 1; j < t.getAllClearings().length + 1; j++) {
			if(t.getClearing(j) != null) {
				if (t.getClearing(j).getClearingNum() == p.getClearingLocation()
						.getClearingNum()) {
					c = t.getClearing(j).getClearingNum();
				}
			}
		}

		//checks if the clearing player located at has any natives or visitors
		//if native found checkN is true if visitor is found checkV is found
		if(board[locx][locy].getClearing(c).getAllNatives().size() > 0) {
			boolean checkN = false;
			int loc = 0;
			int locL = -1;
			for(int j = 0; j < board[locx][locy].getClearing(c).getNativeGroup(0).getAllNatives().size(); j++) {
				if(board[locx][locy].getClearing(c).getNativeGroup(0).getNative(j).isLeader() == true) {
					checkN = true;
					locL = j;
				}
			}

			//if both visitors and native located in clearing give choice to buy/sell with native and visitor
			if(checkN == true) {
				String[] choice = {"Sell to Native Leader", "Buy from Native Leader"};
				String chosen = (String) JOptionPane.showInputDialog(frame,
						"Which table row will you choose?", "Search table",
						JOptionPane.QUESTION_MESSAGE, null, choice, choice[0]);
				if(chosen == choice[0]) {
					retvalue = sellItem(p, board[locx][locy].getClearing(c).getNativeGroup(loc).getNative(locL), frame);
				} 
				else if(chosen == choice[1]) {
					retvalue = buyItem(p, board[locx][locy].getClearing(c).getNativeGroup(loc).getNative(locL), frame);
				}
			}else {
				retvalue ="No one here to trade with!";
				JOptionPane.showMessageDialog(frame, "No one here to trade with!");
			}
		}
		retvalue+="-"+locx+"-"+locy + "-" + c;
		return retvalue;
	}
	public static String sellItem(Character p, NativeMember leader, JFrame frame) {
		String retvalue="";
		//Stores names of items character owns
		String selling = "";
			String[] items = new String[p.getItemsOwned().size()+1];
			for(int i = 0; i < items.length-1; i ++) {
				items[i] = p.getItemsOwned().get(i).getName();
			}
			items[items.length-1] = "Nothing";
			if(p.getItemsOwned().size() > 0) {
				selling = (String) JOptionPane.showInputDialog(frame,
						"What will you sell?", "Search table",
						JOptionPane.QUESTION_MESSAGE, null, items, items[0]);

				if(selling != "Nothing") {
					ItemObject m = null;
					for(int i = 0; i < p.getItemsOwned().size(); i ++) {
						//finds the item removes it from character and gives it to native and character owns gold/notoriety/fame??
						if(selling == p.getItemsOwned().get(i).getName()) {
							m = (ItemObject) p.getItemsOwned().get(i);
							p.setGoldRecorded(p.getGoldRecorded() + m.getPrice());
							retvalue = "SELL"+"#"+p.getItemsOwned().get(i).getName()+"#"+p.getGoldRecorded();
							leader.addItem(p.getItemsOwned().remove(i));
							break;
						}
					}
					JOptionPane.showMessageDialog(frame, "You sold " + selling + " and received " + m.getPrice() + " gold!");
				}
			}
			else {
				retvalue="You don't have anything to sell at the moment!";
				JOptionPane.showMessageDialog(frame, "You don't have anything to sell at the moment!");
			}
	
		return retvalue;
	}

	public static String buyItem(Character p, NativeMember leader, JFrame frame) {
		String retvalue="";
		String[] items = new String[leader.getItems().size()];
		for(int i = 0; i < items.length; i++) {
			items[i] = leader.getItems().get(i).getName();
		}
		String buy = (String) JOptionPane.showInputDialog(frame, "What do you want to buy?", "Trade", JOptionPane.QUESTION_MESSAGE, null, items, items[0]);
		ItemObject m = null;
		for(int i = 0; i < p.getItemsOwned().size(); i ++) {
			//finds the item removes it from character and gives it to native and character owns gold/notoriety/fame??
			if(buy == leader.getItems().get(i).getName()) {
				m = (ItemObject) leader.getItems().get(i);
				int gold = p.getGoldRecorded() - m.getPrice();
				if(gold < 0) {
					retvalue = "You don't have enough money to buy this item!";
					JOptionPane.showMessageDialog(frame, "You don't have enough money to buy this item!");
				}
				else{
					p.setGoldRecorded(gold);
					p.getItemsOwned().add(leader.getItems().get(i));
					retvalue="BUY"+"#"+leader.getItems().get(i).getName()+"#"+p.getGoldRecorded();

				}
				break;
			}
		}
		return retvalue;
	}

	//////////////////////////////////////////////////////////MEETING TABLE//////////////////////////////////////////////

	public static int meetingTable(Character p, NativeMember n, int dice) {
		int currLevel = currLevel(p, n);
		int price = 0;

		if (currLevel == 1) {
			price = meetingTableFriendly(p, n, dice);
		}
		else if(currLevel == 2) {
			if(dice == 1) {
				//nothing
			}
			else {
				price = meetingTableUnfriendly(p, n, dice);
			}
		}
		else if(currLevel == 3) {
			if(dice == 2 || dice == 3) {
				//nothing
			}
			else {
				price = meetingTableNeutral(p, n, dice);
			}
		}
		else if(currLevel == 4) {
			if(dice == 1 || dice == 6) {
				price = meetingTableAlly(p, n, dice);
			}
			else {
				///NOTHING IN THE EVENING
			}
		}
		else if(currLevel == 5) {
			//NOTHING IN THE EVENING
		}
		return price;
	}

	public static int meetingTableAlly(Character p, NativeMember n, int diceRoll) {
		int price = 0;
		if (diceRoll == 1) {
			price = 1;
		}
		else if(diceRoll == 2) {
			price = 1;
		}
		else if(diceRoll == 3) {
			price = 2;
		}
		else if(diceRoll == 4) {
			price = 3;
		}
		else if(diceRoll == 5) {
			price = 4;
		}
		else if(diceRoll == 6) {
			price = 5;
		}
		return price;
	}
	public static int meetingTableFriendly(Character p, NativeMember n, int diceRoll) {
		int price = 0;
		if (diceRoll == 1) {
			//roll again
			JOptionPane.showMessageDialog(null, "Roll dice again!");
			int dice = RollDicePanel.ans;
			meetingTableAlly(p, n, dice);
		}
		else if(diceRoll == 2) {
			price = 2;
		}
		else if(diceRoll == 3) {
			price = 2;
		}
		else if(diceRoll == 4) {
			price = 3;
		}
		else if(diceRoll == 5) {
			price = 4;
		}
		else if(diceRoll == 6) {
			noDeal();
		}
		return price;
	}
	public static int meetingTableNeutral(Character p, NativeMember n, int diceRoll) {
		int price = 0;
		if (diceRoll == 1) {
			//roll again
			JOptionPane.showMessageDialog(null, "Roll dice again!");
			int dice = RollDicePanel.ans;
			meetingTableFriendly(p, n, dice);
		}
		else if(diceRoll == 2) {
			price = 3;
		}
		else if(diceRoll == 3) {
			price = 4;
		}
		else if(diceRoll == 4) {
			noDeal();
		}
		else if(diceRoll == 5) {
			noDeal();
		}
		else if(diceRoll == 6) {
			//roll again
			JOptionPane.showMessageDialog(null, "Roll dice again!");
			int dice = RollDicePanel.ans;
			meetingTableUnfriendly(p, n, dice);
		}
		return price;
	}
	public static int meetingTableUnfriendly(Character p, NativeMember n, int diceRoll) {
		int price =0;
		if (diceRoll == 1) {
			price = 4;
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
			//BLOCK
		}
		return price;
	}
	public int meetingTableEnemy(Character p, NativeMember n, int diceRoll) {
		int price = 0;
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
		return price;
	}

	public static void buyDrinks(Character p, NativeMember n) {
		friendlinessLevel(p, n, 1);
	}

	public static void friendlinessLevel(Character p, NativeMember n, int level) {
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
				p.setAlly(p.removeFriendly(n.getNativeGroup()));
			}
			else {

			}
		}
		else if(currLevel == 3) {
			if(level > 0) {
				p.setFriendly(p.removeNeutral(n.getNativeGroup()));
			}
			else {

			}
		}
		else if(currLevel == 2) {
			if(level > 0) {
				p.setNeutral(p.removeUnFriendly(n.getNativeGroup()));
			}
			else {

			}
		}
		else if(currLevel == 1) {
			if(level > 0) {
				p.setUnfriendly(p.removeEnemy(n.getNativeGroup()));
			}
			else {
				System.out.println("Already an Enemy! Cannot be any more unfriendly!!");
			}
		}
	}
	public static int currLevel(Character p, NativeMember n) {
		int currLevel = 0;
		//ALLY LEVEL 5
		for(int i = 0; i < p.getAlly().length; i++) {
			if(p.getAlly()[i] == n.getNativeGroup()) {
				currLevel = 5;
				break;
			}
		}
		//FRIENDLY LEVEL 4
		for(int i = 0; i < p.getFriendly().length; i++) {
			if(p.getFriendly()[i] == n.getNativeGroup()) {
				currLevel = 4;
				break;
			}
		}
		//NEUTRAL LEVEL 3
		for(int i = 0; i < p.getNeutral().length; i++) {
			if(p.getNeutral()[i] == n.getNativeGroup()) {
				currLevel = 3;
				break;
			}
		}
		//UNFRIENDLY LEVEL 2
		for(int i = 0; i < p.getUnFriendly().length; i++) {
			if(p.getUnFriendly()[i] == n.getNativeGroup()) {
				currLevel = 2;
				break;
			}
		}
		//ENEMY LEVEL 1
		for(int i = 0; i < p.getEnemy().length; i++) {
			if(p.getEnemy()[i] == n.getNativeGroup()) {
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
	public static void noDeal() {
		JFrame frame = new JFrame("No Deal");
		JOptionPane.showMessageDialog(frame, "No deal is made.");
	}
}
