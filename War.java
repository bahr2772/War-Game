package war;
import java.util.*;

public class War {
	
	public static void main(String[] args) {
		// create a new deck
		Deck d = new Deck();
		System.out.println("Unshuffled: " + Arrays.toString(d.cards));
		
		// shuffle the deck
		d.shuffle();
		System.out.println("Shuffled: " + Arrays.toString(d.cards));
		
		// create 2 players, plus a 3rd that contains the cards on the table at any given time
		Player p1 = new Player("Fred");
		Player p2 = new Player("Wilma");
		Player table = new Player("cards on the table");
		
		// deal the cards to the 2 players
		d.deal2Players(p1.hand,p2.hand);
		System.out.println("Dealt to " + p1.name + ": " + p1.hand);
		System.out.println("Dealt to " + p2.name + ": " + p2.hand);
		
		// play the game
		// rules: 1) whoever runs out of cards first loses, even if during a war
		//        2) wars consist of three down cards
		int numRounds = 0; int winner=0;		
		while (true) {			
			numRounds += 1;
			winner = playBattle(p1,p2,table,numRounds);	// 0=goto next battle 1=p1 wins game, 2=p2 wins game, 3=tie, goto war, 4=draw (both payers out of cards)
			System.out.println("     (cards remaining " + p1.hand.size() + ", " + p2.hand.size() + ")");
			while (winner==3) {	//	i.e. a war, repeat war until there is a winner
				winner = playWar(p1,p2,table);
			}
			if (winner>0) {
				printWinner(winner);
				break;
			}
		}		
	}	
	// END OF MAIN METHOD
	
	public static int playBattle(Player p1, Player p2, Player table ,int numRounds){
		int battleWinner = playBattleCardsToTable(p1,p2,table,numRounds);
		switch (battleWinner) {
			case 1:
				p1.sweepCards(table, true);					// P1 wins
				return p2.hand.size()==0 ? 1 : 0;	// returns 1 if player 2 ran out of cards
			case 2:
				p2.sweepCards(table, true);					// P2 wins
				return p1.hand.size()==0 ? 2 : 0;	// returns 2 if player 1 ran out of cards											
			default:								// WAR (battle was a draw)
				return 3;	// this return value tells the program to initiate a war	
		}
	}	
	//END OF battle METHOD
	
	public static int playWar(Player p1, Player p2, Player table){
		printWar(p1,p2);
		if (p1.hand.size()<4 || p2.hand.size()<4) {		// one or both players don't have enough cards to war 
			System.out.println(((p1.hand.size()<4 && p2.hand.size()<4) ? "    Both players" :
				p1.hand.size()<4 ? "    Player 1" : "    Player 2") + " ran out of cards");
			return (p1.hand.size()<4 && p2.hand.size()<4) ? 4 :
					p1.hand.size()<4 ? 2 : 1;
		} else {						
			int warWinner = playWarCardsToTable(p1,p2,table);	// FIGHT THE WAR HERE
			switch (warWinner) {
			case 1:
				p1.sweepCards(table, true);					// P1 wins
				return p2.hand.size()==0 ? 1 : 0;	// returns 1 if player 2 ran out of cards
			case 2:
				p2.sweepCards(table, true);					// P2 wins
				return p1.hand.size()==0 ? 2 : 0;	// returns 2 if player 1 ran out of cards											
			default:								// WAR (battle was a draw)
				return 3;	// this return value tells the program to initiate another war	
			}
		}
	}	//END OF makeWar METHOD
	
	public static void printWinner(int input) {
		System.out.println(input == 3 ? "DRAW - BOTH PLAYERS RAN OUT OF CARDS!" 
				: "PLAYER " + input + " WINS!!!");
	}	// END OF printWinner METHOD
	
	public static void printWar(Player p1, Player p2) {
		System.out.print("        1) <");
		for (int x=0; x<Math.min(p1.hand.size(),4); x++) {
			System.out.print(p1.hand.get(x) + (x==Math.min(p1.hand.size()-1,2) ? "> " : " "));
		}
		System.out.print("        2) <");
		for (int y=0; y<Math.min(p2.hand.size(),4); y++) {
			System.out.print(p2.hand.get(y) + (y==Math.min(p2.hand.size()-1,2) ? "> " : " "));
		}
	}	// END OF printWar METHOD
	
	public static int playBattleCardsToTable(Player player1, Player player2, Player table, int round) {
		table.hand.add(player1.hand.get(0));	// put player 1's top card on the table
		table.hand.add(player2.hand.get(0));	// put player 2's top card on the table
		player1.hand.remove(0);			// remove player 1's card from hand 
		player2.hand.remove(0);			// remove player 2's card from hand
		System.out.print(round + ": -- 1) " + table.hand.get(0) + "  2) " + table.hand.get(1) + "  " + (table.cardValue(0) > table.cardValue(1) ? "Winner: P1" : table.cardValue(0) < table.cardValue(1) ? "Winner: P2" : "WAR       "));
		return table.cardValue(0) > table.cardValue(1) ? 1 : table.cardValue(0) < table.cardValue(1) ? 2 : 0;
	}
	
	public static int playWarCardsToTable(Player player1, Player player2, Player table) {
		for (int counter=0; counter<4; counter++) {
			table.hand.add(player1.hand.get(counter));	// put player 1's top card on the table x 4
			table.hand.add(player2.hand.get(counter));	// put player 2's top card on the table x 4
		}
		for (int counter=3; counter>=0; counter--) {
			player1.hand.remove(counter);			// remove player 1's card from hand x 4
			player2.hand.remove(counter);			// remove player 2's card from hand x 4
		}
		System.out.println(table.cardValue(table.hand.size()-2) > table.cardValue(table.hand.size()-1) ? "  Winner: P1" : table.cardValue(table.hand.size()-2) < table.cardValue(table.hand.size()-1) ? "  Winner: P2" : "  ANOTHER WAR!");
		return table.cardValue(table.hand.size()-2) > table.cardValue(table.hand.size()-1) ? 1 : table.cardValue(table.hand.size()-2) < table.cardValue(table.hand.size()-1) ? 2 : 0;
	}
}	// END OF PROGRAM

