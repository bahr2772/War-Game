package war;
import java.util.*;

public class Deck {
	public String[] cards = new String[52];
	public static final String[] valueList = {"2","3","4","5","6","7","8","9","T","J","Q","K","A"};
	public static final String[] suitList = {"S","H","D","C"};
	
	public Deck() {
		for (int i=0; i<valueList.length; i++) {
			for (int j=0; j<suitList.length; j++) {
				cards[(i*4)+j] = valueList[i] + suitList[j];
			}
		}
	}
	
	public void shuffle() {
		Collections.shuffle(Arrays.asList(cards));
	}
	
	public void deal2Players(ArrayList<String> hand1, ArrayList<String> hand2) {
		for (int i=0; i<cards.length; i++) {
			if (i%2==0) {
				hand1.add(cards[i]);
			} else {
				hand2.add(cards[i]);
			}
		}
	}
	
	public void deal4Players(ArrayList<String> hand1, ArrayList<String> hand2, ArrayList<String> hand3, ArrayList<String> hand4) {
		for (int i=0; i<cards.length; i++) {
			if (i%4==0) {
				hand1.add(cards[i]);
			} else if (i%4==1) {
				hand2.add(cards[i]);
			} else if (i%4==2) {
				hand3.add(cards[i]);
			} else {	// (i%4==3)
				hand4.add(cards[i]);
			}
		}
	}
}
