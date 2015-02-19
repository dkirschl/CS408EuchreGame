import java.awt.Button;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public abstract class Player {
	
	ArrayList<Card> hand = new ArrayList<Card>();
	
	public abstract Card playCard();
	public abstract Card pick(Button b);
	public abstract Card pass(Button b);
	public abstract void chooseSuit();
	public abstract void startTurn(Semaphore s);
	public abstract void waitForClick(Semaphore s);
	
	public void receiveCard(Card c) {
		hand.add(c);
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}

}
