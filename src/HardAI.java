import java.util.ArrayList;


public class HardAI extends AI {
	
	ArrayList<Card> elCards = new ArrayList<Card>();
	
	public HardAI(){
		;
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
		determineEligibleCards();
		if(elCards.size() == 1){
			return elCards.get(0);
		}
		
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
		
		if(elCards.size() == 0){
			for(Card card : hand){
				elCards.add(card);
			}
		}
		//*/
	}
}