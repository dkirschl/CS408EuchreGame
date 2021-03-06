import java.awt.Button;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public abstract class Player {
	
	private ArrayList<Card> hand = new ArrayList<Card>();
	private int team;
	
	public abstract Card playCard();
	public abstract Card pick(Button b);
	public abstract Card pass(Button b);
	public abstract String chooseSuit();
	public abstract void startTurn(Semaphore s);
	public abstract void waitForClick(Semaphore s);
	public abstract boolean pickupOrPass();
	public abstract void removeCard(Card middle);
	public abstract boolean isHuman();
	
	public void receiveCard(Card c) {
		hand.add(c);
	}
	
	public ArrayList<Card> getHand() {
		return hand;
	}
	
	public void setTeam(int v) {
		team = v;
	}
	
	public int getTeam() {
		return team;
	}

}
