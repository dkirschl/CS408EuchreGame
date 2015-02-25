import java.util.ArrayList;

public class EasyAI extends AI{
	
	ArrayList<Card> elCards = new ArrayList<Card>();
	int myValue;
	
	public EasyAI(){
		;
	}
	
	public EasyAI(int value){
		myValue = value;
	}

	/*
	 * 	Easy AI will always pass
	 */
	@Override
	public boolean passOrPickUp() {
		
		return false;
	}

	/*
	 * 	Easy AI will always pass. If AI is dealer they pick a random suit
	 */
	@Override
	public char chooseSuit() {
		/*
		if(I am not the dealer){
			return pass value;
		}
		*/
		
		/*
		 * 	Determine which suit cannot be chosen 
		 */
		
		//int suit;
		/*
		 * 	Randomly pick one of the remaining suits
		 */
		return 0;
	}
	
	/*
	 * 	Easy AI will determine eligible cards and play a random one
	 */
	@Override
	public Card playCard() {
		//Determine the leading suit
		
		/*
		 for each card in hand:
		 	if next card in hand's suit == leading suit {
		 		add card to eligible cards
		 	}
		 
		 if elCards.size() == 0 {
		 	play random card from hand since they are all eligible
		  } else {
		  	play random card from eligible cards since they should all follow suit
		  }
		 */
		return new Card(0, 0, null);
	}
}