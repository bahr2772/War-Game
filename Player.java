package war;
import java.util.*;

public class Player {
	public String name;
	public ArrayList<String> hand = new ArrayList<String>();
	
	public Player(String name){
		this.name = name;
	}
	
	public int cardValue(int whichCard) {	// this method takes string parameter of a card such as "9S" and returns the numerical value 9 
		String card = hand.get(whichCard).substring(0, 1);
		return card.equals("T") ? 10
				: card.equals("J") ? 11
				: card.equals("Q") ? 12
				: card.equals("K") ? 13
				: card.equals("A") ? 14
				: Integer.parseInt(card);	// i.e. a card of value 2 thru 9
	}
	
	public void sweepCards(Player table, boolean shuffle) {	// allows a player to take all of another players cards (usually the table,  i.e. sweeping the table)
		if (shuffle) table.shuffle(); // includes option to shuffle the table cards (good for games like war)
		for (int counter=0; counter<table.hand.size(); counter++) {
			hand.add(table.hand.get(counter));	// add the table cards to the players hand
		}
		table.hand.clear();						// now sweep off the table	
	}
	
	public void shuffle() {
		Collections.shuffle(hand);
	}
	
}
