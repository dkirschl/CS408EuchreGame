import java.util.ArrayList;
import java.util.Random;

public class EasyAI extends AI{
	
	ArrayList<Card> myHand;
	ArrayList<Card> elCards = new ArrayList<Card>();
	int myValue;
	
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
		
		//Resets AI hand
		if(myValue == 1){
			myHand = GameInfo.AI_1_Hand;
		} else if(myValue == 2){
			myHand = GameInfo.AI_2_Hand;
		} else if(myValue == 3){
			myHand = GameInfo.AI_3_Hand;
		} else {
			System.err.println("Incorrect value for AI");
		}
		
		int handValue = calculateValues(GameInfo.middleSuit);
		
		Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(10);
	    randomInt -= 5;
	    
	    int threshold = 38;
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
		
		//Resets AI hand
		if(myValue == 1){
			myHand = GameInfo.AI_1_Hand;
		} else if(myValue == 2){
			myHand = GameInfo.AI_2_Hand;
		} else if(myValue == 3){
			myHand = GameInfo.AI_3_Hand;
		} else {
			System.err.println("Incorrect value for AI");
		}
				
		/*
		 * Cancel chance to call trump already passed on
		 */
		switch(GameInfo.middleSuit){
		
			case "Spades":		spades = -6;
								break;
			
			case "Clubs":		clubs = -6;
								break;
			
			case "Hearts":		hearts = -6;
								break;
			
			case "Diamonds":	diamonds = -6;
								break;
			default:
						break;
		}
		
		/*
		 * Count how many of each suit in player's hand
		 */
		for(int i = 0; i < myHand.size(); i++){
			switch(myHand.get(i).getSuit()){
				
				case "Spades":		spades++;
									break;
				
				case "Clubs":		clubs++;
									break;
				
				case "Hearts":		hearts++;
									break;
				
				case "Diamonds":	diamonds++;
									break;
				default:
							break;
			}
		}
		
		if(spades >= 3){
			return "spades";
		} else if(clubs >= 3){
			return "clubs";
		} else if(hearts >= 3){
			return "hearts";
		} else if(diamonds >= 3){
			return "diamonds";
		} else {
			if(GameInfo.screwDealer){
				if(spades >= 2){
					return "spades";
				} else if(clubs >= 2){
					return "clubs";
				} else if(hearts >= 2){
					return "hearts";
				} else if(diamonds >= 2){
					return "diamonds";
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
		
		//Resets AI hand
		if(myValue == 1){
			myHand = GameInfo.AI_1_Hand;
		} else if(myValue == 2){
			myHand = GameInfo.AI_2_Hand;
		} else if(myValue == 3){
			myHand = GameInfo.AI_3_Hand;
		} else {
			System.err.println("Incorrect value for AI");
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
			int high = 0;
			Card highestValued = new Card();
			for(int i = 0; i < myHand.size(); i++){
				Card nextCard = myHand.get(i);
				
				if(nextCard.getValue() > high){
					highestValued = nextCard;
				}
			}
			
			//Remove card from AI hand
			if(myValue == 1){
				GameInfo.AI_1_Hand.remove(highestValued);
			} else if(myValue == 2){
				GameInfo.AI_2_Hand.remove(highestValued);
			} else if(myValue == 3){
				GameInfo.AI_3_Hand.remove(highestValued);
			}
			
			return highestValued;
			
		} else {
			leadSuit = GameInfo.currentTrick.get(0).getSuit();
			for(int i = 0; i < myHand.size(); i++){
				Card nextCard = myHand.get(i);
				
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
			
			//Remove card from AI hand
			if(myValue == 1){
				GameInfo.AI_1_Hand.remove(elCards.get(0));
			} else if(myValue == 2){
				GameInfo.AI_2_Hand.remove(elCards.get(0));
			} else if(myValue == 3){
				GameInfo.AI_3_Hand.remove(elCards.get(0));
			}
			
			return elCards.get(0);
		
		} else if(elCards.size() == 0){
			/*
			 * Play lowest valued card since you are going to lose unless you play trump
			 */
			int low = 100;
			Card lowestValued = new Card();
			for(int i = 0; i < myHand.size(); i++){
				Card nextCard = myHand.get(i);
				
				if(nextCard.getValue() < low){
					lowestValued = nextCard;
				}
			}
			
			//Remove card from AI hand
			if(myValue == 1){
				GameInfo.AI_1_Hand.remove(lowestValued);
			} else if(myValue == 2){
				GameInfo.AI_2_Hand.remove(lowestValued);
			} else if(myValue == 3){
				GameInfo.AI_3_Hand.remove(lowestValued);
			}
			
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
			
			//Remove card from AI hand
			if(myValue == 1){
				GameInfo.AI_1_Hand.remove(highestValued);
			} else if(myValue == 2){
				GameInfo.AI_2_Hand.remove(highestValued);
			} else if(myValue == 3){
				GameInfo.AI_3_Hand.remove(highestValued);
			}
			
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
		for(int i = 0; i < myHand.size(); i++){
			Card nextCard = myHand.get(i);
			
			if(nextCard.getSuit() == leftSuit && nextCard.getCardId() == 11){
				totalValue += 12;
				nextCard.setValue(12);
			} else if(nextCard.getSuit() == suit){
				
				switch(nextCard.getCardId()){
					case 9: totalValue += 7;
							nextCard.setValue(7);
							break;
					case 10: totalValue += 8;
							 nextCard.setValue(8);
							 break;
					case 11: totalValue += 13;
							 nextCard.setValue(13);
							 break;
					case 12: totalValue += 9;
							 nextCard.setValue(9);
							 break;
					case 13: totalValue += 10;
							 nextCard.setValue(10);
							 break;
					case 14: totalValue += 11;
							 nextCard.setValue(11);
							 break;
				}
			} else {
				switch(nextCard.getCardId()){
					case 9:  totalValue += 1;
							 nextCard.setValue(1);
							 break;
					case 10: totalValue += 2;
							 nextCard.setValue(2);
							 break;
					case 11: totalValue += 3;
							 nextCard.setValue(3);
							 break;
					case 12: totalValue += 4;
							 nextCard.setValue(4);
							 break;
					case 13: totalValue += 5;
							 nextCard.setValue(5);
							 break;
					case 14: totalValue += 6;
							 nextCard.setValue(6);
							 break;
				}
			}
			
			
		}
		return totalValue;
	}
}