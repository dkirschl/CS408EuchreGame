import java.util.ArrayList;

public class MediumAI extends AI{
	
	ArrayList<Card> elCards = new ArrayList<Card>();
	int myValue;
	
	public MediumAI(){
		;
	}
	
	public MediumAI(int value){
		myValue = value;
	}

	/*
	 * 	Medium AI will pick up if it has 2 or more of trump in their hand
	 * 
	 * 	Possibly make this 2+ if your team deal, 3+ if other team deal 
	 */
	@Override
	public boolean passOrPickUp() {
		//int count;
		
		//Determine leading suit
		
		/*
		 	for each card in your hand:
		 		if next card's suit == middle card suit
		 			count++
		 			
		 	if count >= 2 {
		 		return true;
		 	} else {
		 		return false;
		 	}
		 */
		return false;
	}

	/*
	 * 	Medium AI will always pass unless they have 3 of the same suit. 
	 * 	If AI is dealer, they pick the suit they have the most of.
	 */
	@Override
	public String chooseSuit() {
		//int hearts, spades, clubs, diamonds;
		/*
		 * 	Determine which suit cannot be chosen 
		 */
		
		//int suit;
		
		/*
		 	for each suit that can be picked:
		 		if next card's suit == hearts(etc.)	(If card is a jack you have to add to both suits
		 			hearts(etc)++;
		 		if any suit == 3
		 			pick the current suit
		 	
		 	if I am not the dealer
		 		pass
		 	else
		 		pick the suit with the highest number
		 */
		
		return "Hello";
	}
	
	/*
	 * 	Medium AI will determine eligible cards and play the highest
	 * 	Possibly change this to lowest if teammate is winning or highest if opposition is winning
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
		 	play lowest card in hand or trump depending on how difficult we want hard to be
		  } else {
		  	play highest card that is eligible
		  }
		 */
		return new Card(0, 0, null);
	}
}