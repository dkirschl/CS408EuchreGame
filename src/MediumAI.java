import java.util.ArrayList;


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
	int opponent1Value; //Opponents global integer value
	int opponent2Value; //Opponents global integer value
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
			/*
			 * Human team is index 0 and 2
			 * AI team is index 1 and 3
			 */
			case 1: partnerValue = 3;
					opponent1Value = 0;
					opponent2Value = 2;
					break;
			case 2: partnerValue = 0;
					opponent1Value = 1;
					opponent2Value = 3;
					break;
			case 3: partnerValue = 1;
					opponent1Value = 2;
					opponent2Value = 0;
					break;
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
			/*
			 * You are the dealer so check see what your hand value would be if you picked it up
			 */
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
			if(returned > highest){
				winner = 1;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "clubs"){ 
			returned = calculateValues("clubs");
			if(returned > highest){
				winner = 2;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "hearts"){
			returned = calculateValues("hearts");
			if(returned > highest){
				winner = 3;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "diamonds"){
			returned = calculateValues("diamonds");
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
	
	/*
	 * Medium AI will try to play the smartest card possible based on what the average human would play
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
		
		//If it is the first trick of the hand, reset the hasTrump variable
		if(GameInfo.teamOneTricks == 0 && GameInfo.teamTwoTricks == 0){
			hasTrump[0] = hasTrump[1] = hasTrump[2] = hasTrump[3] = true;
		} else {
			/*
			 * Go through last trick to determine which if any players no longer have trump
			 * 
			 * If trump was lead than anybody who didn't play trump must not have any
			 */
			if(GameInfo.previousTrick.get(0).getSuit() == GameInfo.trump || 
					(GameInfo.previousTrick.get(0).getSuit() == leftSuit && GameInfo.previousTrick.get(0).getValue() == 11)){
				for(int i = 1, j = (GameInfo.previousTrickLeader + 1) % 4; i < 4; j = (j + 1) % 4, i++){
					//If the next player didn't play trump
					if(GameInfo.previousTrick.get(i).getSuit() != GameInfo.trump){
						//And if they didn't play the left
						if(GameInfo.previousTrick.get(i).getSuit() != leftSuit || GameInfo.previousTrick.get(0).getValue() != 11){
							//Then they have no trump
							hasTrump[j] = false;
						}
					}
				}
			}
		}
		
		/*
		 * Clear elCards from previous trick
		 */
		while(elCards.size() != 0){
			elCards.remove(0);
		}
		
		if(GameInfo.currentTrick.size() == 0){
			/*
			 * If you have the highest valued trump left, or if you have trump and the opponent doesn't, play it. 
			 * Otherwise, play your highest valued off suit
			 */
			int j = 6; //Variable to determine the highest trump card left
			while(GameInfo.TrumpPlayed[j] != 0){
				j--;
			}
			int value = 0;
			
			switch(j){
				//Sets value to the worth that it would be to compare it to my cards
				case 0: value = 7; break;
				case 1: value = 8; break;
				case 2: value = 9; break;
				case 3: value = 10; break;
				case 4: value = 11; break;
				case 5: value = 12; break;
				case 6: value = 13; break;
			}
			
			Card highestValued = new Card();
			int high = 0;
			
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				
				if(nextCard.getSuit() == GameInfo.trump || (nextCard.getSuit() == leftSuit && nextCard.getValue() == 11)){
					if(nextCard.getWorth() == value || (hasTrump[opponent1Value] == false && hasTrump[opponent2Value] == false)){
						//You either have the highest trump or your opponents don't have trump so you'll automatically win
						return nextCard;
					} else {
						//Don't play this trump since you are not sure if it'll win
						continue;
					}
					
				} else {
					if(nextCard.getWorth() > high){
						highestValued = nextCard;
						high = nextCard.getWorth();
					}
				}
			}
			
			//All we have is trump so play your higher trump
			if(high == 0){
				highestValued = GameInfo.players.get(myValue).getHand().get(0);
				high = GameInfo.players.get(myValue).getHand().get(0).getWorth();
				
				for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
					Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
					if(nextCard.getWorth() > high){
						highestValued = nextCard;
						high = nextCard.getWorth();
					}
					
				}
			}

			return highestValued;
			
		} else {
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
		}
		
		if(elCards.size() == 1){
			/*
			 * Play the only eligible card
			 */

			return elCards.get(0);
		
		} else if(elCards.size() == 0){
			/*
			 * If an opponent is winning and you have trump that can win, play your that trump
			 * 
			 * Otherwise play your lowest card
			 */
			if(GameInfo.currentWinner == opponent1Value || GameInfo.currentWinner == opponent2Value){
				//Figure out the current card that is winning the hand
				int index = GameInfo.currentTrickLeader;
				int difference = 0;
				
				while(index != GameInfo.currentWinner){
					index = (index + 1) % 4;
					difference++;
				}
				Card highCard = GameInfo.currentTrick.get(difference);
				
				if(highCard.getSuit() == GameInfo.trump || (highCard.getSuit() == leftSuit && highCard.getValue() == 11)){
					//They have trump so see if I have a trump that can beat it, if not play lowest card
					//Figure out the winning card and how high of trump it is
					Card possibleWinner = new Card();
					int winWorth = 100;
					int highWorth = 0;
					if(highCard.getSuit() == leftSuit && highCard.getValue() == 11){
						highWorth = 12;
					} else {
						switch(highCard.getValue()){
							case 9: highWorth = 7; break;
							case 10: highWorth = 8; break;
							case 11: highWorth = 13; break;
							case 12: highWorth = 9; break;
							case 13: highWorth = 10; break;
							case 14: highWorth = 11; break;
						}
					}
					//Go through my hand to get the lowest trump that I have that would still win
					for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
						Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
						
						if(nextCard.getSuit() == GameInfo.trump || (nextCard.getSuit() == leftSuit && nextCard.getValue() == 11)){
							if(nextCard.getWorth() < winWorth && nextCard.getWorth() > highWorth){
								possibleWinner = nextCard;
								winWorth = nextCard.getWorth();
							}
						}
					}
					
					if(winWorth != 100){
						//We have a trump that can beat the trump already played
						return possibleWinner;
					} else {
						//Can't beat their trump so play your lowest card
						Card lowestValued = GameInfo.players.get(myValue).getHand().get(0);
						int low = lowestValued.getWorth();
						for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
							Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
							
							if(nextCard.getWorth() < low){
								lowestValued = nextCard;
								low = nextCard.getWorth();
							}
						}

						return lowestValued;
					}
				} else {
					Card lowestTrump = new Card();
					int low = 100;
					for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
						Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
						
						if(nextCard.getSuit() == GameInfo.trump || (nextCard.getSuit() == leftSuit && nextCard.getValue() == 11)){
							if(nextCard.getWorth() < low){
								lowestTrump = nextCard;
								low = nextCard.getWorth();
							}
						}
					}
					if(low != 100){
						return lowestTrump;
					}
				}
			}
			
			Card lowestValued = GameInfo.players.get(myValue).getHand().get(0);
			int low = lowestValued.getWorth();
			for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
				Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
				
				if(nextCard.getWorth() < low){
					lowestValued = nextCard;
					low = nextCard.getWorth();
				}
			}

			return lowestValued;
			
		} else {
			/*
			 * If your highest eligible card will potentially win, play it
			 * 
			 * If not, play your lowest eligible card
			 */
			
			//Figure out the current card that is winning the hand
			int index = GameInfo.currentTrickLeader;
			int difference = 0;
			
			while(index != GameInfo.currentWinner){
				index = (index + 1) % 4;
				difference++;
			}
			Card highCard = GameInfo.currentTrick.get(difference);
			
			//Figure out your highest valued current card
			Card highestValued = elCards.get(0);
			int high = highestValued.getWorth();
			for(int i = 1; i < elCards.size(); i++){
				Card nextCard = elCards.get(i);
				
				if(nextCard.getWorth() > high){
					highestValued = nextCard;
					high = nextCard.getWorth();
				}
			}
			
			if(highCard.getSuit() == GameInfo.trump || (highCard.getSuit() == leftSuit && highCard.getValue() == 11)){
				if(GameInfo.currentTrick.get(0).getSuit() != GameInfo.trump){
					//Somebody trumped an off suit lead hand so you cannot beat them
					Card lowestValued = elCards.get(0);
					int low = lowestValued.getWorth();
					for(int i = 1; i < elCards.size(); i++){
						Card nextCard = elCards.get(i);
						
						if(nextCard.getWorth() < low){
							lowestValued = nextCard;
							low = nextCard.getWorth();
						}
					}
	
					return lowestValued;
				} else {
					//HighCard is trump and trump was lead
					int worth = 0;
					if(highCard.getSuit() == leftSuit && highCard.getValue() == 11){
						worth = 12;
					} else {
						switch(highCard.getValue()){
							case 9: worth = 7; break;
							case 10: worth = 8; break;
							case 11: worth = 13; break;
							case 12: worth = 9; break;
							case 13: worth = 10; break;
							case 14: worth = 11; break;
						}
						if(highestValued.getWorth() > worth){
							return highestValued;
						} else {
							Card lowestValued = elCards.get(0);
							int low = lowestValued.getWorth();
							for(int i = 1; i < elCards.size(); i++){
								Card nextCard = elCards.get(i);
								
								if(nextCard.getWorth() < low){
									lowestValued = nextCard;
									low = nextCard.getWorth();
								}
							}
							return lowestValued;
						}
					}
				}
			} else {
				//HighCard isn't trump so they must've followed suit
				if(highestValued.getValue() > highCard.getValue()){
					//Your card is higher
					return highestValued;
				} else {
					//Their card is higher than yours so play your lower card
					Card lowestValued = elCards.get(0);
					int low = lowestValued.getWorth();
					for(int i = 1; i < elCards.size(); i++){
						Card nextCard = elCards.get(i);
						
						if(nextCard.getWorth() < low){
							lowestValued = nextCard;
							low = nextCard.getWorth();
						}
					}
					return lowestValued;
				}
			}

			return highestValued;
		}
	}
	
	/*
	 * If the computer picks up the middle card, remove the least useful card from your hand
	 * If you have a relatively low card as the only card of an off suit than get rid of that
	 * 
	 * Add the middle card to their hand.
	 */
	@Override
	public void removeCard(Card middle) {
		
		//Resets values for the appropriate suit
		calculateValues(GameInfo.middleSuit);
		String leftSuit = "xxxxx";
		
		switch(middle.getSuit()){
			case "spades": 		leftSuit = "clubs";
								break;
								
			case "clubs": 		leftSuit = "spades";
					 	  		break;
					 	  		
			case "hearts": 		leftSuit = "diamonds";
						   		break;
						   		
			case "diamonds": 	leftSuit = "hearts";
					 		 	break;
		}
		
		int spades = 0;
		int clubs = 0;
		int hearts = 0;
		int diamonds = 0;
		
		/*
		 * Caluculate how many of each suit you have in your hand
		 */
		Card lowestValued = GameInfo.players.get(myValue).getHand().get(0);
		int low = lowestValued.getWorth();
		
		switch(lowestValued.getSuit()){
			case "spades":		spades++;
								break;
			case "clubs":		clubs++;
								break;
			case "hearts":		hearts++;
								break;
			case "diamonds":	diamonds++;
								break;
			default:			break;
		}

		for(int i = 1; i < GameInfo.players.get(myValue).getHand().size(); i++){
			Card nextCard = GameInfo.players.get(myValue).getHand().get(i);
			
			switch(nextCard.getSuit()){
				case "spades":		spades++;
									break;
				case "clubs":		clubs++;
									break;
				case "hearts":		hearts++;
									break;
				case "diamonds":	diamonds++;
									break;
				default:			break;
			}
			
			if(nextCard.getWorth() < low){

				lowestValued = nextCard;
				low = nextCard.getWorth();
			}
		}		
		
		/*
		 * Check every card that is the only one of its suit. Remove either the lowest card that is the only one
		 * of its own suit if it is a queen or lower, or remove the lowest valued card that you have
		 */
		Card newLow = lowestValued;
		Card compare = lowestValued;
		
		if(spades == 1 && middle.getSuit() != "spades"){
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				if(GameInfo.players.get(myValue).getHand().get(i).getSuit() == "spades"){
					newLow = GameInfo.players.get(myValue).getHand().get(i);
					break;
				}
			}
			if(newLow.getValue() <= 12){
				if(newLow.getValue() != 11 || newLow.getSuit() != leftSuit){
					compare = newLow;
				}
			}
		}
		
		if(clubs == 1 && middle.getSuit() != "clubs"){
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				if(GameInfo.players.get(myValue).getHand().get(i).getSuit() == "clubs"){
					newLow = GameInfo.players.get(myValue).getHand().get(i);
					break;
				}
			}
			if(newLow.getValue() <= 12){
				if(newLow.getValue() != 11 || newLow.getSuit() != leftSuit){
					if(compare == lowestValued){
						compare = newLow;
					} else if(newLow.getValue() < compare.getValue()){
						compare = newLow;
					}
				}
			}
		}
		
		if(hearts == 1 && middle.getSuit() != "hearts"){
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				if(GameInfo.players.get(myValue).getHand().get(i).getSuit() == "hearts"){
					newLow = GameInfo.players.get(myValue).getHand().get(i);
					break;
				}
			}
			if(newLow.getValue() <= 12){
				if(newLow.getValue() != 11 || newLow.getSuit() != leftSuit){
					if(compare == lowestValued){
						compare = newLow;
					} else if(newLow.getValue() < compare.getValue()){
						compare = newLow;
					}
				}
			}
		}
		
		if(diamonds == 1 && middle.getSuit() != "diamonds"){
			for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
				if(GameInfo.players.get(myValue).getHand().get(i).getSuit() == "diamonds"){
					newLow = GameInfo.players.get(myValue).getHand().get(i);
					break;
				}
			}
			if(newLow.getValue() <= 12){
				if(newLow.getValue() != 11 || newLow.getSuit() != leftSuit){
					if(compare == lowestValued){
						compare = newLow;
					} else if(newLow.getValue() < compare.getValue()){
						compare = newLow;
					}
				}
			}
		}

		GameInfo.players.get(myValue).getHand().remove(compare);
		GameInfo.players.get(myValue).getHand().add(middle);
		return;
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
				//This is the left bauer so it is treated as trump
				totalValue += 12;
				nextCard.setWorth(12);
			} else if(nextCard.getSuit() == suit){
				
				switch(nextCard.getValue()){
					//The card is a trump so set its worth appropriately
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
					//The card is not trump so set its worth lower
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