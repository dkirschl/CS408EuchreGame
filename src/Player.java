import java.util.ArrayList;


public abstract class Player {
	
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public abstract Card playCard();
	public abstract void chooseSuit();
	
	public void receiveCard(Card c) {
		hand.add(c);
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

}
