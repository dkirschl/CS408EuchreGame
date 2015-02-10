import java.util.ArrayList;


public abstract class Player {
	
	ArrayList<Card> hand;
	
	public abstract void playCard();
	public abstract void chooseSuit();

}
