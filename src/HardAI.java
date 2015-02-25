import java.util.ArrayList;


public class HardAI extends AI {
	
	ArrayList<Card> elCards = new ArrayList<Card>();
	int myValue;
	
	public HardAI(){
		;
	}
	
	public HardAI(int value){
		myValue = value;
	}

	@Override
	public boolean passOrPickUp() {
		
		return false;
	}

	@Override
	public char chooseSuit() {
		
		return 0;
	}

	@Override
	public Card playCard() {
		
		// add all cards that are eligible to play this turn into elCards
		determineEligibleCards();
		
		// If there's only one card that's eligible, play it
		if(elCards.size() == 1){
			return elCards.get(0);
		}
		
		// Determine odds that partner will win hand.
		int partnerOdds = calcPartnerWinPerc();
		
		// Determines the odds that each card will win hand.
		

		
		System.out.println("Playing card from hard");
		return elCards.get(0);
	}
	
	public void determineEligibleCards(){
		elCards.clear();
		/*
		for(Card card: hand){
			if(card.suit == ledSuit){
				elCards.add(card);
			}
		}	
		
		// if you have one eligible cards
		if(elCards.size() == 0){
			for(Card card : hand){
				elCards.add(card);
			}
		}
		
		
		//*/
	}
	
	public int calcPartnerWinPerc()
	{
		/*
		 // find partner number in this turn
		 
		 Card winningCard = winningCard;
		 int currentWinner = currentWinner;
		 
		 if(numTurn > 2){
		 	// partner has already played
		 	
		 	partner = numTurn - 2;
		 	
		 } else {
		 	//partner has yet to play
		 	
		 	partner = numTurn + 2;
		 }
		 
		 
		 if(){
		 	
		 	
		 }
		 */
		return 100;
	}
}