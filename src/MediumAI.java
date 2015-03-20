import java.util.ArrayList;
import java.util.Random;


/*
 * Medium AI will act as a computer player with the approximate intelligence and gameplay strategy
 * of an average, experienced Human might play. One major advantage Medium AI has over Easy AI is that
 * Medium knows which trump have been played and if certain players no longer have trump based on what
 * they have played
 */
public class MediumAI extends AI{
	
	ArrayList<Card> elCards = new ArrayList<Card>();
	int myValue; //Players global integer value
	int partnerValue; //Player's partner's global integer value
	boolean hasTrump[] = new boolean[] {true, true, true, true};
	int threshold = 36; //Value threshold to decide whether you want to declare trump
	
	public MediumAI(){
		;
	}
	
	public MediumAI(int value){
		if(value < 1 || value > 3){
			System.err.println("Incorrect value for AI");
		}
		myValue = value;
		switch(value){
			case 1: partnerValue = 3;
					break;
			case 2: partnerValue = 0;
					break;
			case 3: partnerValue = 1;
		}
	}

	/*
	 * Calculate the players hand value based on the middle card's suit and make adjustments on that
	 * depending on who the dealer is before they make the decision whether to pick it up or pass
	 */
	@Override
	public boolean passOrPickUp() {

		int handValue;
		
		if(GameInfo.dealer == myValue){
			handValue = calculateValuesAsDealer(GameInfo.middleCard);
		} else if(GameInfo.dealer == partnerValue){
			/*
			 * If partner is dealer, add half of the middle cards value to your hand value to symbolize
			 * that your partners hand will gain at least some value
			 */
			handValue = calculateValues(GameInfo.middleSuit);
			switch(GameInfo.middleCard.getValue()){
				case 9: handValue += 1;
						break;
				case 10: handValue += 2;
						 break;
				case 11: handValue += 6;
						 break;
				case 12: handValue += 3;
						 break;
				case 13: handValue += 4;
						 break;
				case 14: handValue += 5;
						 break;
			}
			
		} else {
			/*
			 * The opponent is the dealer so subtract half of the middle cards value from your hand to symbolize
			 * that an opponents hand will gain at least some value
			 */
			handValue = calculateValues(GameInfo.middleSuit);
			switch(GameInfo.middleCard.getValue()){
				case 9: handValue -= 1;
						break;
				case 10: handValue -= 2;
						 break;
				case 11: handValue -= 6;
						 break;
				case 12: handValue -= 3;
						 break;
				case 13: handValue -= 4;
						 break;
				case 14: handValue -= 5;
						 break;
			}
		}
		
		System.out.println("Hand value is " + handValue);
	    	    
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
	 * Calculate the value of your hand based off of each suit and play your highest valued hand if it 
	 * is higher than the call trump threshold
	 */
	@Override
	public String chooseSuit() {
		int winner = 0;
		int highest = 0;
		int returned;
		
		//Suit chosen cannot be whatever the middle suit was
		if(GameInfo.middleSuit != "spades"){
			returned = calculateValues("spades");
			System.out.println("Spade hand value was " + returned);
			if(returned > highest){
				winner = 1;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "clubs"){ 
			returned = calculateValues("clubs");
			System.out.println("Club hand value was " + returned);
			if(returned > highest){
				winner = 2;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "hearts"){
			returned = calculateValues("hearts");
			System.out.println("Heart hand value was " + returned);
			if(returned > highest){
				winner = 3;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "diamonds"){
			returned = calculateValues("diamonds");
			System.out.println("Diamond hand value was " + returned);
			if(returned > highest){
				winner = 4;
				highest = returned;
			}
		}
		
		/*
		 * If the highest valued hand is higher than the threshold or this is the dealer and
		 * they must choose a suit, then return the highest valued hand they would have
		 * 
		 * Else pass
		 */
		if(highest >= threshold || (GameInfo.screwDealer && GameInfo.dealer == myValue)){
			switch(winner){	
				case 1:	return "spades";
				case 2:	return "clubs";
				case 3: return "hearts";
				case 4: return "diamonds";
			}
		} else {
			return "pass";
		}
		
		return "pass";
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
	
	/*
	 * Function to calculate the worth of each card based on the trump suit
	 */
	public int calculateValuesAsDealer(Card middle) {
		int totalValue = 0;
		String leftSuit = "xxxxx";
		String suit = middle.getSuit();
		
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
		
		/*
		 * Subtract the lowest valued card you have from the total value as the card you would hypothetically
		 * get rid of if you picked it up as the dealer
		 */
		int low = GameInfo.players.get(myValue).getHand().get(0).getWorth();
		
		for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
			Card nextCard = GameInfo.players.get(myValue).getHand().get(i);

			if(nextCard.getWorth() < low){

				low = nextCard.getWorth();
			}
		}
		
		totalValue -= low;
		
		/*
		 * Add the value of the middle card to the total value as the value you would add if you hypothetically
		 * picked it up
		 */
		switch(middle.getValue()){
			case 9: totalValue += 7;
					break;
			case 10: totalValue += 8;
					 break;
			case 11: totalValue += 13;
					 break;
			case 12: totalValue += 9;
					 break;
			case 13: totalValue += 10;
					 break;
			case 14: totalValue += 11;
					 break;
		}
		
		return totalValue;
	}
}