import java.util.ArrayList;
import java.util.Random;


/*
 * Easy AI will act a low level, beginner Human play style with some randomness added to increase the variability of the
 * decision making to demonstrate the inconsistent decisions of a beginner player
 */
public class EasyAI extends AI{
	
	ArrayList<Card> elCards = new ArrayList<Card>();
	int myValue;//Player's global integer value
	
	public EasyAI(){
		;
	}
	
	public EasyAI(int value){
		if(value < 1 || value > 3){
			System.err.println("Incorrect value for AI");
		}
		myValue = value;
	}

	/*
	 * 	Easy AI will pick up based on a stated hand value threshold.
	 *  Easy AI's threshold will be randomly +-5 each hand to make them more random
	 */
	@Override
	public boolean passOrPickUp() {
		
		
		int handValue = calculateValues(GameInfo.middleSuit);
		System.out.println("Hand value is " + handValue);
		Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(10);
	    randomInt -= 5;
	    System.out.println("Random int is " + randomInt);
	    int threshold = 36;
	    threshold += randomInt;
	    
	    /*
	     * If their hand value is more than the minimum value they'd want, then pick up
	     */
	    if(handValue >= threshold){
	    	return true;
	    } else {
	    	return false;
	    }
	}

	/*
	 * 	Easy AI will choose a suit if they have 3 trump, else pass
	 */
	@Override
	public String chooseSuit() {
		
		int spades = 0;
		int clubs = 0;
		int hearts = 0;
		int diamonds = 0;

				
		/*
		 * Cancel chance to call trump already passed on
		 */
		switch(GameInfo.middleSuit){
		
			case "spades":		spades = -6;
								break;
			
			case "clubs":		clubs = -6;
								break;
			
			case "hearts":		hearts = -6;
								break;
			
			case "diamonds":	diamonds = -6;
								break;
			default:
						break;
		}
		
		/*
		 * Count how many of each suit in player's hand
		 */
		for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
			switch(GameInfo.players.get(myValue).getHand().get(i).getSuit()){
				
				case "spades":		spades++;
									break;
				
				case "clubs":		clubs++;
									break;
				
				case "hearts":		hearts++;
									break;
				
				case "diamonds":	diamonds++;
									break;
				default:
							break;
			}
		}
		
		
		/*
		 * If they have 3 of an eligible suit, choose that suit
		 */
		if(spades >= 3){
			return "spades";
		} else if(clubs >= 3){
			return "clubs";
		} else if(hearts >= 3){
			return "hearts";
		} else if(diamonds >= 3){
			return "diamonds";
		} else {
			
			/*
			 * If we are the dealer and we must chose a suit, choose the highest valued suit
			 * Else pass
			 */
			if(GameInfo.screwDealer && GameInfo.dealer == myValue){ //Choose the suit with your highest valued hand
				int winner = 0;
				int highest = 0;
				int returned;
				if(spades >= 1){
					returned = calculateValues("spades");
					if(returned > highest){
						winner = 1;
						highest = returned;
					}
				} 
				if(clubs >= 1){
					returned = calculateValues("clubs");
					if(returned > highest){
						winner = 2;
						highest = returned;
					}
				}
				if(hearts >= 1){
					returned = calculateValues("hearts");
					if(returned > highest){
						winner = 3;
						highest = returned;
					}
				} 
				if(diamonds >= 1){
					returned = calculateValues("diamonds");
					if(returned > highest){
						winner = 4;
						highest = returned;
					}
				}
				switch(winner){
					case 1:	return "spades";
					case 2:	return "clubs";
					case 3: return "hearts";
					case 4: return "diamonds";
				}
			} else {
				return "pass";
			}
		}

		return "pass";
	}
	
	/*
	 * 	Easy AI will determine eligible cards and play their highest valued card
	 */
	@Override
	public Card playCard() {
		//Determine the leading suit
		String leadSuit;
		String leftSuit = "xxxxx";
		
		switch(GameInfo.trump){
			case "spades": 		leftSuit = "clubs";
								break;
								
			case "clubs": 		leftSuit = "spades";
					 	  		break;
					 	  		
			case "hearts": 		leftSuit = "diamonds";
						   		break;
						   		
			case "diamonds": 	leftSuit = "hearts";
					 		 	break;
		}		
		
		calculateValues(GameInfo.trump);
		
		/*
		 * Clear elCards from previous trick
		 */
		while(elCards.size() != 0){
			elCards.remove(0);
		}
		
		if(GameInfo.currentTrick.size() == 0){
			//Play highest valued card since you are the leader
			Card highestValued = GameInfo.players.get(myValue).getHand().get(0);
			int high = highestValued.getWorth();
			
			for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				
				if(nextCard.getWorth() > high){
					highestValued = nextCard;
					high = nextCard.getWorth();
				}
			}
			
			
			//System.out.println("Leading trick : worth of " + highestValued.getWorth());

			return highestValued;
			
		} else {
			//System.out.println("Not Leading");
			leadSuit = GameInfo.ledSuit;
			
			//If the left is lead, the real suit that is lead is trump
			if(leadSuit == leftSuit && GameInfo.currentTrick.get(0).getValue() == 11){
				leadSuit = GameInfo.trump;
			}
			
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				/*
				 * Card is eligible to be played
				 */
				if(nextCard.getSuit() == leadSuit){
					if(nextCard.getValue() == 11 && leadSuit == leftSuit){
						//Check to make sure the left isn't an eligible card when it's suit is played
					} else {
						elCards.add(nextCard);
					}
				} else if(nextCard.getValue() == 11 && leadSuit == GameInfo.trump && nextCard.getSuit() == leftSuit){
					//Make sure to add left to eligible cards when trump is lead
					elCards.add(nextCard);
				}
				
				
			}
			//System.out.println("There are " + elCards.size() + " eligible cards");
		}
		
		if(elCards.size() == 1){
			/*
			 * Play the only eligible card
			 */
			//System.out.println("One Eligible Card : worth of " + elCards.get(0).getWorth());

			return elCards.get(0);
		
		} else if(elCards.size() == 0){
			/*
			 * Play lowest valued card since you are going to lose unless you play trump
			 */
			Card lowestValued = GameInfo.players.get(myValue).getHand().get(0);
			int low = lowestValued.getWorth();
			for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				
				if(nextCard.getWorth() < low){
					lowestValued = nextCard;
					low = nextCard.getWorth();
				}
			}
			

			//System.out.println("No Eligibles: worth of " + lowestValued.getWorth());

			return lowestValued;
			
		} else {
			/*
			 * Play highest valued eligible card
			 */
			//System.out.println("There are " + elCards.size() + " eligible Cards");
			Card highestValued = elCards.get(0);
			int high = highestValued.getWorth();
			for(int i = 1; i < elCards.size(); i++){
				Card nextCard = elCards.get(i);
				
				if(nextCard.getWorth() > high){
					highestValued = nextCard;
					high = nextCard.getWorth();
				}
			}
			

			//System.out.println("Multiple Eligibles: worth of " + highestValued.getWorth());

			return highestValued;
		}
	}
	
	/*
	 * Function to calculate the worth of each card based on the trump suit
	 */
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
		System.out.println("Removing Card.........");
		
		Card lowestValued = GameInfo.players.get(myValue).getHand().get(0);
		int low = lowestValued.getWorth();
		

		for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
			Card nextCard = GameInfo.players.get(myValue).getHand().get(i);

			if(nextCard.getWorth() < low){

				lowestValued = nextCard;
				low = nextCard.getWorth();
			}
		}
		
		System.out.println("Removing " + lowestValued.getValue() + " " + lowestValued.getSuit());
		GameInfo.players.get(myValue).getHand().remove(lowestValued);
		GameInfo.players.get(myValue).getHand().add(middle);
		
		return;
		
	}
}