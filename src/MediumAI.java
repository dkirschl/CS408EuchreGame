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


	@Override
	public boolean passOrPickUp() {

		return false;
	}


	@Override
	public String chooseSuit() {

		
		return "Hello";
	}
	

	@Override
	public Card playCard() {
		//Determine the leading suit
		String leadSuit;
			
		calculateValues(GameInfo.trump);
				
		/*
		 * Clear elCards from previous trick
		 */
		while(elCards.size() != 0){
			elCards.remove(0);
		}
		
		if(GameInfo.currentTrick.size() == 0){
			//Play highest valued card since you are the leader
			int high = 0;
			Card highestValued = new Card();
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				
				if(nextCard.getValue() > high){
					highestValued = nextCard;
				}
			}
			
			System.out.println("A : " + highestValued.getCardId());
			return highestValued;
			
		} else {
			leadSuit = GameInfo.currentTrick.get(0).getSuit();
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				
				/*
				 * Card is eligible to be played
				 */
				if(nextCard.getSuit() == leadSuit){
					elCards.add(nextCard);
				}
			}
		}
		
		if(elCards.size() == 1){
			/*
			 * Play the only eligible card
			 */
			System.out.println("B : " + elCards.get(0).getCardId());
			return elCards.get(0);
		
		} else if(elCards.size() == 0){
			/*
			 * Play lowest valued card since you are going to lose unless you play trump
			 */
			int low = 100;
			Card lowestValued = new Card();
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				
				if(nextCard.getValue() < low){
					lowestValued = nextCard;
				}
			}
				System.out.println("C : " + lowestValued.getCardId());
			return lowestValued;
			
		} else {
			/*
			 * Play highest valued eligible card
			 */
			int high = 0;
			Card highestValued = new Card();
			for(int i = 0; i < elCards.size(); i++){
				Card nextCard = elCards.get(i);
				
				if(nextCard.getValue() > high){
					highestValued = nextCard;
				}
			}
				System.out.println("D : " + highestValued.getCardId());
			return highestValued;
		}
	}

	@Override
	public int calculateValues(String suit) {
		int totalValue = 0;
		String leftSuit = "xxxxx";
		
		switch(suit){
			case "spades": 		leftSuit = "clubs";
								break;
								
			case "clubs": 		leftSuit = "spades";
					 	  		break;
					 	  		
			case "hearts": 		leftSuit = "diamonds";
						   		break;
						   		
			case "diamonds": 	leftSuit = "hearts";
					 		 	break;
		}
		for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
			Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
			
			if(nextCard.getSuit() == leftSuit && nextCard.getValue() == 11){
				totalValue += 12;
				nextCard.setWorth(12);
			} else if(nextCard.getSuit() == suit){
				
				switch(nextCard.getValue()){
					case 9: totalValue += 7;
							nextCard.setWorth(7);
							break;
					case 10: totalValue += 8;
							 nextCard.setWorth(8);
							 break;
					case 11: totalValue += 13;
							 nextCard.setWorth(13);
							 break;
					case 12: totalValue += 9;
							 nextCard.setWorth(9);
							 break;
					case 13: totalValue += 10;
							 nextCard.setWorth(10);
							 break;
					case 14: totalValue += 11;
							 nextCard.setWorth(11);
							 break;
				}
			} else {
				switch(nextCard.getValue()){
					case 9:  totalValue += 1;
							 nextCard.setWorth(1);
							 break;
					case 10: totalValue += 2;
							 nextCard.setWorth(2);
							 break;
					case 11: totalValue += 3;
							 nextCard.setWorth(3);
							 break;
					case 12: totalValue += 4;
							 nextCard.setWorth(4);
							 break;
					case 13: totalValue += 5;
							 nextCard.setWorth(5);
							 break;
					case 14: totalValue += 6;
							 nextCard.setWorth(6);
							 break;
				}
			}
			
			
		}
		return totalValue;
	}
	
	/*
	 * If the computer picks up the middle card, remove the lowest valued card from their hand.
	 * Add the middle card to their hand.
	 */
	@Override
	public void removeCard(Card middle) {
		
		//Resets values for the appropriate suit
		calculateValues(GameInfo.middleSuit);
		
		int low = 100;
		Card lowestValued = new Card();
		for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
			Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
			
			if(nextCard.getWorth() < low){
				lowestValued = nextCard;
			}
		}
		
		GameInfo.players.get(myValue).getHand().remove(lowestValued);
		GameInfo.players.get(myValue).getHand().add(middle);
		
		return;
		
	}
}