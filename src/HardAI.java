import java.util.ArrayList;


public class HardAI extends AI {
	
	ArrayList<Card> elCards = new ArrayList<Card>();				// cards that are eligible to be played this trick
	ArrayList<Card> winningCards = new ArrayList<Card>();			// arraylist of cards that beat the previously winning card
	ArrayList<Card> hand = new ArrayList<Card>();
	int myValue;						// index of you
	int partnerValue;					// index of your partner
	double []cardValues;				// values of all eligible cards
	double []winningCardValues;			// value of the cards that could beat the previously winning cards
	OpponentOdds oppOdds;				// class that holds the odds each player has each card
	
	int addingCardValue[] = new int[13];
	
	double []cardOdds;
	
	boolean cardDeposited = false;
	Card depositedCard;
	
	double []leadValues;
	double []myLeadValues;
	
	double []followValues;
	
	int leastValuableIndex;
	
	double currentExpectedTricksToWin = 0;	
	
	int worstElCardWorth;				// the eligible card with the worst worth
	
	int cardWorths[];					// all of the eligible cards' worths
	
	Card currentWinningCard;			// current best card
	
	int threshold = 36; 				//Value threshold to decide whether you want to declare trump

	
	public HardAI(){
		;
	}
	
	public HardAI(int value){
		myValue = value;
		partnerValue = (myValue + 2) % 4;			// calculate your partner's index
		initializeAddingCardValues();
	}

	@Override
	public boolean passOrPickUp() {					// identical structure to mediumAI
		
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
		
		//System.out.println("Hand value is " + handValue);
	    	    
	    /*
	     * If their hand value is more than the minimum value they'd want, then pick up
	     */
	    if(handValue >= threshold){
	    	return true;
	    } else {
	    	return false;
	    }
		
		
		/*		// Incomplete alternative of passOrPickUp
		 
		double optimalThresholdToPickUp = 3.0;
			
		if(GameInfo.dealer == myValue){
			hand = getRemoveCardHand(GameInfo.middleCard);
		} else {
			hand = GameInfo.players.get(myValue).getHand();
			
			if(GameInfo.dealer == partnerValue){
				optimalThresholdToPickUp -= gainedValueFromPickUp();
			} else {
				optimalThresholdToPickUp += gainedValueFromPickUp();
			}
			
		}		
		
		
		double value = expectedTricks(hand, GameInfo.middleSuit);	// get expectedTricks if that suit is trump
			
		if(value > optimalThresholdToPickUp){	
			return true;
		}
		
		return false;
		*/
	
	}
	
	public double gainedValueFromPickUp(){
		
		return .5;
	}

	
	public void initializeAddingCardValues(){
		
		for(int i = 0; i < 13; i++){
			addingCardValue[i] = i;
		}
		
		/*
		addingCardValues = { 0, // non-trump 9
							1, // 10 
							3, // Jack
							5, // Queen
							7, // King
							10, // Ace
							15, // trump 9
							19, // trump 10
		}
		*/
	}	
	
	@Override
	public String chooseSuit() {			// identical structure to mediumAI
		
		int winner = 0;
		int highest = 0;
		int returned;
		
		//Suit chosen cannot be whatever the middle suit was
		if(GameInfo.middleSuit != "spades"){
			returned = calculateValues("spades");
			//System.out.println("Spade hand value was " + returned);
			if(returned > highest){
				winner = 1;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "clubs"){ 
			returned = calculateValues("clubs");
			//System.out.println("Club hand value was " + returned);
			if(returned > highest){
				winner = 2;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "hearts"){
			returned = calculateValues("hearts");
			//System.out.println("Heart hand value was " + returned);
			if(returned > highest){
				winner = 3;
				highest = returned;
			}
		} else if(GameInfo.middleSuit != "diamonds"){
			returned = calculateValues("diamonds");
			//System.out.println("Diamond hand value was " + returned);
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
		
		/*       Incomplete alternative to chooseSuit
		
		ArrayList<Card> hand = GameInfo.players.get(myValue).getHand();
		
		
		double most = -1;							// this will be the highest value so far
		int mostIndex = -1;							// index of highest value so far
		
		for(int i = 0; i < 4; i++){					// iterate through each suit to see the maximum suit		
			
			String newSuit = "spades";
			
			switch(i){
			case 0:
				newSuit = "spades";
				break;
			case 1:
				newSuit = "clubs";					// get suit string from index
				break;
			case 2:
				newSuit = "hearts";
				break;
			case 3:
				newSuit = "diamonds";
				break;
			}
			
			if(newSuit.equals(GameInfo.middleSuit)){	// if it was the middle suit, don't evaluate because it is ineligible
				continue;
			}
			
			double newValue = expectedTricks(hand, newSuit);	// get expectedTricks if that suit is trump
			
			if(newValue > most){
				mostIndex = i;								// if it is the new maximum, overwrite previous
				most = newValue;
			}
			
			if(most > 3.5 || (GameInfo.dealer == myValue && GameInfo.screwDealer)){									// if it is past a certain threshold or you are the dealer and you are being screwed.
				switch(mostIndex){
				case 0:
					return "spades";
				case 1:
					return "clubs";
				case 2:
					return "hearts";
				case 3:
					return "diamonds";
				}
				
			}
			
		}
		
		return "pass";
		
		*/
	}

	@Override
	public Card playCard() {
		
		System.out.println("Playing card from hard");
		
		
		if(GameInfo.players.get(myValue).getHand().size() == 5){
			currentExpectedTricksToWin = expectedTricks(GameInfo.players.get(myValue).getHand(),GameInfo.trump);			// need the expected tricks to win, and if you have 5 cards then the value is still for the old hand.
			if(!cardDeposited){
				depositedCard = new Card(GameInfo.middleCard);
			}
			cardDeposited = false;
			oppOdds = new OpponentOdds(myValue, depositedCard);
		}
		
		
		
		adjustOpponentOdds();
		
		hand = GameInfo.players.get(myValue).getHand();
		
		// add all cards that are eligible to play this turn into elCards
		determineEligibleCards();
		valueCards();
		
		cardOdds = new double[elCards.size()];
		
		// If there's only one card that's eligible, play it
		if(elCards.size() == 1){
			
			debugPrint("oneOption");
			currentExpectedTricksToWin -= cardValues[0];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			return elCards.remove(0);
		}
		
		if(GameInfo.currentTrick.size() != 0){
			determineWinningCards();
		}
		
		if(GameInfo.currentTrick.size() == 0){
			// leading
			System.out.println("Player "+ myValue +" is leading");
			
			double bestValue = -10;
			int bestIndex = -1;
			
			for(int i = 0; i < elCards.size(); i++){				// iterate through all eligible cards, which is all of them when leading.
				
				
				double leadValue = leadValues[i];	// find the lead value of the current card
				
				cardOdds[i] = leadValue;
				
				if(leadValue >= .96){
					
					debugPrint("leadingWinner");
					currentExpectedTricksToWin -= cardValues[i];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
					return elCards.remove(i);							// if you can lead a card that will win 100% of the time, do it.
				}
				
				if(leadValue - cardValues[i] > bestValue){			// evaluate how much better this card is to lead now than it will be to hold on to.
					bestValue = leadValue - cardValues[i];			// if it the best value so far, store the information
					bestIndex = i;
					
				}
			}
			
			debugPrint("bestImprovement");
			currentExpectedTricksToWin -= cardValues[bestIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			return elCards.remove(bestIndex);							// return the card that improves your percentage chance of winning the most by being lead.
			
			
			
		} else if(GameInfo.currentTrick.size() == 1){
			// second
			System.out.println("Player "+ myValue +" is second");
			
			if(winningCards.size() == 0){
				// if you can't beat the current winner, return worst card
				

				Card lvCard = leastValuableCard();
				debugPrint("leastValuable");
				currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
				return lvCard;
			}
			
			double oddsTeamWins[];
			
			oddsTeamWins = generateOddsOfWinningPlayingWorstCard2(elCards.get(worstElCardWorth));
			
			System.out.println("oddsTeamWinsPlayingWorstCard = "+ oddsTeamWins[worstElCardWorth]);
			
			double bestValue = -10;
			int bestIndex = -1;
			
			for(int i = 0; i < elCards.size(); i++){
				
				double oddsCardWins = oddsTeamWins[i];
				
				cardOdds[i] = oddsCardWins;
				
				if(oddsCardWins - cardValues[i] > bestValue){
					bestValue = oddsCardWins - cardValues[i];
					bestIndex = i;
				}
				
			}
			
			System.out.println("BestValue = "+bestValue+", on card "+ elCards.get(bestIndex).getSuit()+elCards.get(bestIndex).getValue()+" because odds are "+ oddsTeamWins[bestIndex]+" that we win, and I sacrifice "+cardValues[bestIndex]+" value"); 
			
			debugPrint("bestImprovement");
			currentExpectedTricksToWin -= cardValues[bestIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			return elCards.remove(bestIndex);
			
			
			
		} else if(GameInfo.currentTrick.size() == 2){
			// third
			System.out.println("Player "+ myValue +" is third");
			
			if(winningCards.size() == 0){
				// if you can't beat the current winner, return worst card
				

				Card lvCard = leastValuableCard();
				debugPrint("leastValuable");
				currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
				return lvCard;
			}
			
			double oddsTeamWins[];
			
				
			oddsTeamWins = generateOddsOfWinningPlayingWorstCard3(elCards.get(worstElCardWorth));
			
			double bestValue = -10;
			int bestIndex = -1;
			
			for(int i = 0; i < elCards.size(); i++){
				
				double oddsCardWins = oddsTeamWins[i];
				
				cardOdds[i] = oddsCardWins;
				
				if(oddsCardWins - cardValues[i] > bestValue){
					bestValue = oddsCardWins - cardValues[i];
					bestIndex = i;
				}
				
			}
			
			System.out.println("BestValue = "+bestValue+", on card "+ elCards.get(bestIndex).getSuit()+elCards.get(bestIndex).getValue()+" because odds are "+  oddsTeamWins[bestIndex]+" that we win, and I sacrifice "+cardValues[bestIndex]+" value"); 
			
			debugPrint("bestImprovement");
			currentExpectedTricksToWin -= cardValues[bestIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played4
			return elCards.remove(bestIndex);
			
			
		} else if(GameInfo.currentTrick.size() == 3){				
			// last to play
			System.out.println("Player "+ myValue +" is last");
			
			if (GameInfo.currentWinner == partnerValue){
				// partner is winning
				
				System.out.println("Partner is winning");
				
				
				Card lvCard = leastValuableCard();
				debugPrint("leastValuable");
				currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			 	return lvCard;
			
			} else {
				// partner is not winning. Play winner if you can.
				
				System.out.println("Partner isn't winning...");
				
				determineWinningCards();
				if(winningCards.size() == 0){
					// can't win this hand
					System.out.println("unfortunately I can't win it either");

					Card lvCard = leastValuableCard();
					debugPrint("leastValuable");
					currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
					return lvCard;
				
				} else {
					// can win, return worst card that will win.
					System.out.println("so I will win it");

					Card lvwCard = leastValuableWinningCard();
					debugPrint("leastValuableWinning");
					currentExpectedTricksToWin -= winningCardValues[leastValuableIndex];
					return lvwCard;
				}
			} 
			
			
		} else {
			System.out.println("ERROR: CURRENT TRICK SIZE IS OVER 3");
			
			return elCards.remove(0);
		}
		
	}
	
	public void debugPrint(String type){
		
		System.out.println("Hand is:");
		
		System.out.println("ledSuit = "+GameInfo.ledSuit);
		System.out.println("trumpSuit = "+GameInfo.trump);
		
		for(int i = 0; i < hand.size();i++){
			System.out.println(i+": "+hand.get(i).getSuit()+hand.get(i).getValue());
		}
		
		System.out.println("EligibleCards are:");
		
		for(int i = 0; i < elCards.size(); i++){
			System.out.println(i+": "+elCards.get(i).getSuit()+elCards.get(i).getValue()+"     leadValue="+leadValues[i]+"   actualValue="+cardValues[i]+"      followValue ="+followValues[i]+"     oddsCardWins = "+cardOdds[i]);
		}
		
		System.out.println("WinningCards are:");
		for(int i = 0; i < winningCards.size(); i++){
			System.out.println(i+": "+winningCards.get(i).getSuit()+winningCards.get(i).getValue());
		}
		
		System.out.println("CurrentExpectedTricksToWin = "+currentExpectedTricksToWin);
		
		switch(type){
		
		case "leastValuable":
			System.out.println("Returning least Valuable");
			break;
		case "leastValuableWinning":
			System.out.println("Return least Valuable Winning");
			break;
		case "bestImprovement":
			System.out.println("Return best improvement");
			break;
		case "oneOption":
			System.out.println("Return only eligible card");
			break;
		case "leadingWinner":
			System.out.println("Playing sure winner");
			break;
		}
		
	}
	
	public double[] generateOddsOfWinningPlayingWorstCard2(Card worstCard){
		
		Card newBestCard;
		
		if(cardWorth(worstCard) > cardWorth(currentWinningCard)){
			newBestCard = worstCard;
		} else {
			newBestCard = currentWinningCard;
		}
		
		int nextOppValue = (myValue + 1) % 4;								// calculates the index of the opponent to your left.
		
		int ledIndex = oppOdds.getColNumFromSuit(GameInfo.ledSuit);			// finds the index of the led suit in your cardIndex array within OpponentOdds
		int trumpIndex = oppOdds.getColNumFromSuit(GameInfo.trump);			// finds index of trump ""            ""                       ""
		
		double odds = 0.0;
		double oddsOppFollowsSuit = oppOdds.oddsPlayerHasSuit(nextOppValue, ledIndex);						// gets the odds the opponent will have to follow the led suit
		double partOdds = 0.0;
		double oddsPartFollowsSuit = oppOdds.oddsPlayerHasSuit(partnerValue,ledIndex);						// gets odd partner will.
		
		
		double[] oddsTeamWins = new double[5];			// will be the return value. Odds you will win with that eligible card
		
		for(int i = 0; i < 5; i++){
			oddsTeamWins[i] = -1;
		}
		
		if(GameInfo.ledSuit == GameInfo.trump){			// if trump is led, odds they will be forced to follow suit instead of trump will be 0.
			oddsOppFollowsSuit = 0.0;
			oddsPartFollowsSuit = 0.0;
		}
		
		
		
		
		for(int i = 0; i < 7; i++){
			CardIndex currentCardOdds = oppOdds.cardOdds[i][trumpIndex];
			
			int cardWorth = cardWorth(currentCardOdds.card);
			
			for(int j = 0; j < elCards.size();j++){
				if(cardWorth == cardWorths[j]){
					oddsTeamWins[j] = 1.0 - odds;
				}
			}
			
			if(currentCardOdds.card.getValue() == newBestCard.getValue() && currentCardOdds.card.getSuit().equals(newBestCard.getSuit())){	// if the currentCard is the new best card, we've reached the end, switch breaker and break from loop.
				for(int j = 0; j < elCards.size(); j++){
					if(oddsTeamWins[j] < 0){
						// hasn't been filled in yet.
						oddsTeamWins[j] = 1.0 - odds;				// fill in with the remaining odds. This is the odds that your partner wins.
					}
				}
				return oddsTeamWins;
			}
			
			odds = 1.0 - odds;																
			odds *= 1.0 - ( (currentCardOdds.odds[nextOppValue] * (1.0 - oddsOppFollowsSuit)) * (1.0 - partOdds) );	// calculate new odds by getting the inverse of current odds, multiplying that by the odds that the opponent has the current card, and then getting the inverse again.
																													// multiplies by 1.0 - partOdds, because those are the odds that the partner can't beat that card.
			odds = 1.0 - odds;	
			
			
			// calculate odds partner has a higher value than this card.
			partOdds = 1.0 - partOdds;					
			partOdds *= 1.0 - (currentCardOdds.odds[partnerValue] * (1.0 - oddsPartFollowsSuit));
			partOdds = 1.0 - partOdds;
		}
		
		
		
		
		for(int i = 0; i < 7; i++){											// if you still haven't reached the new best card, traverse the led suits
			CardIndex currentCardOdds = oppOdds.cardOdds[i][ledIndex];
			
			int cardWorth = cardWorth(currentCardOdds.card);
			
			for(int j = 0; j < elCards.size(); j++){
				if(cardWorth == cardWorths[j]){
					oddsTeamWins[j] = 1.0 - odds;
				}
			}
			
			if(currentCardOdds.card.getValue() == newBestCard.getValue() && currentCardOdds.card.getSuit().equals(newBestCard.getSuit())){	// if the currentCard is the new best card, we've reached the end, switch breaker and break from loop.
				for(int j = 0; j < elCards.size(); j++){
					if(oddsTeamWins[j] < 0){
						// hasn't been filled in yet.
						oddsTeamWins[j] = 1.0 - odds;				// fill in with the remaining odds. This is the odds that your partner wins.
					}
				}
				return oddsTeamWins;
			}
			
			odds = 1.0 - odds;																
			odds *= 1.0 -  (currentCardOdds.odds[nextOppValue] * (1.0 - partOdds) );	// calculate new odds by getting the inverse of current odds, multiplying that by the odds that the opponent has the current card, and then getting the inverse again.
																													// multiplies by 1.0 - partOdds, because those are the odds that the partner can't beat that card.
			odds = 1.0 - odds;	
			
			
			// calculate odds partner has a higher value than this card.
			partOdds = 1.0 - partOdds;					
			partOdds *= 1.0 - (currentCardOdds.odds[partnerValue]);
			partOdds = 1.0 - partOdds;
		}		
		
		for(int j = 0; j < elCards.size(); j++){
			if(oddsTeamWins[j] < 0){
				// hasn't been filled in yet.
				oddsTeamWins[j] = 1.0 - odds;				// fill in with the remaining odds. This is the odds that your partner wins.
			}
		}
		
		return oddsTeamWins;
	}
	
	
	public double[] generateOddsOfWinningPlayingWorstCard3(Card worstCard){		// for when you are 3rd

		Card newBestCard;
		
		if(cardWorth(worstCard) > cardWorth(currentWinningCard)){			// assigns
			newBestCard = worstCard;										// this means your worst card still beats the current winning card. Assign newBestCard to your worst card
		} else {
			newBestCard = currentWinningCard;		// if your worst card doesn't beat the previously winning card, newBestCard will be set to previously winning card
		}
		
		
		
		double odds = 0.0;							// this will serve as odds that the next opponent has the current card, or better.
		double oddsFollowSuit;						// odds that the opponent will be forced to follow the suit that has been led.
		
		double[] oddsTeamWinsCard = new double[elCards.size()];
		
		for(int i = 0; i < elCards.size(); i++){
			oddsTeamWinsCard[i] = -1.0;
		}
		
		int ledIndex = oppOdds.getColNumFromSuit(GameInfo.ledSuit);			// finds the index of the led suit in your cardIndex array within OpponentOdds
		int trumpIndex = oppOdds.getColNumFromSuit(GameInfo.trump);			// finds index of trump ""            ""                       ""
		
		int nextOppValue = (myValue + 1) % 4;								// calculates the index of the opponent to your left.
		
		if(GameInfo.ledSuit == GameInfo.trump){
			oddsFollowSuit = 0.0;											// set oddsFollowSuit to the odds the opponent won't be able to trump your card, because they are forced to follow the led suit. If ledSuit == trump, odds are zero.
		} else {
			oddsFollowSuit = oppOdds.oddsPlayerHasSuit(nextOppValue,ledIndex); // if trump wasn't led, get the return value from the function that calculates it in OpponentOdds
		}
				
		for(int i = 0; i < 7; i++){
			
			CardIndex currentCardOdds = oppOdds.cardOdds[i][trumpIndex];			// extract the cardIndex object from the array to analyze odds opponent has that card
			
			int cardWorth = cardWorth(currentCardOdds.card);
			
			for(int j = 0; j < elCards.size();j++){
				if(cardWorth == cardWorths[j]){
					oddsTeamWinsCard[j] = 1.0 - odds;
				}
			}
			
			if(currentCardOdds.card.getValue() == newBestCard.getValue() && currentCardOdds.card.getSuit().equals(newBestCard.getSuit())){	// if the currentCard is the new best card, we've reached the end, switch breaker and break from loop.
				for(int j = 0; j < elCards.size(); j++){
					if(oddsTeamWinsCard[j] < 0){
						// hasn't been filled in yet.
						if(GameInfo.currentWinner == partnerValue){				// if the eligible card has not been encountered, but new best card has, then it will not be a winner. If partner is winning, odds are current odds. If not, odds are 1.0 that the opponent will win if that card is played because you will not gain the lead.
							oddsTeamWinsCard[j] = 1.0 - odds;
						} else {
							oddsTeamWinsCard[j] = 0;
						}
					}
				}
				return oddsTeamWinsCard;
			}
			
			odds = 1.0 - odds;																
			odds *= 1.0 - (currentCardOdds.odds[nextOppValue] * (1.0 - oddsFollowSuit));	// calculate new odds by getting the inverse of current odds, multiplying that by the odds that the opponent has the current card, and then getting the inverse again.
			odds = 1.0 - odds;
			
		}
		
			for(int i = 0; i < 7; i++){
				
				// follow the same process as for the trump cards until the new card has been found.
				
				CardIndex currentCardOdds = oppOdds.cardOdds[i][ledIndex];
				
				if(currentCardOdds.card.getValue() == newBestCard.getValue() && currentCardOdds.card.getSuit().equals(newBestCard.getSuit())){
					for(int j = 0; j < elCards.size(); j++){
						if(oddsTeamWinsCard[j] < 0){
							// hasn't been filled in yet.
							
							if(GameInfo.currentWinner == partnerValue){
								oddsTeamWinsCard[j] = 1.0 - odds;
							} else {
								oddsTeamWinsCard[j] = 0;
							}
						}
					}
					return oddsTeamWinsCard;	
				}
				
				odds = 1.0 - odds;
				odds *= 1.0 - currentCardOdds.odds[nextOppValue];
				odds = 1.0 - odds;
				
		}
			
			for(int j = 0; j < elCards.size(); j++){
				if(oddsTeamWinsCard[j] < 0){
					// hasn't been filled in yet.
					if(GameInfo.currentWinner == partnerValue){
						oddsTeamWinsCard[j] = 1.0 - odds;
					} else {
						oddsTeamWinsCard[j] = 0;
					}
				}
			}
		
		
		
		return oddsTeamWinsCard;
	}
	
	
	public void valueCards(){
		
		System.out.println("Value Cards");
		
		cardValues = new double[elCards.size()];
		leadValues = new double[elCards.size()];
		myLeadValues = new double[elCards.size()];
		followValues = new double[elCards.size()];
		
		double expectedPercentageOfWinningTricks = currentExpectedTricksToWin / GameInfo.players.get(myValue).getHand().size();	
		currentExpectedTricksToWin = 0;
		
		int elCardCounter = 0;
		
		for(int i = 0; i < hand.size(); i++){
			
			Card currentCard = hand.get(i);
			
			System.out.println("currentCard = "+currentCard.getSuit()+currentCard.getValue());
			
			//oppOdds.printOutCardIndices();
			
			// calculate all values needed
			double percHasLessValueSameSuit[] = new double[4];				// calculate these
			double percHasHigherValueSameSuit[] = new double[4];			// calculate these
			double percHasTrump[] = new double[4];
			
			double oddsWillWinLeading[] = new double[4];
			
			double tricksPerPerson[] = new double[4];					// calculate this
			double tricksTotal = 0;
			
			double oddsUnderValueLed[] = new double[4];
			double oddsWillBeatIfLed[] = new double[4];
			
			for(int j = 0; j < 4; j++){
				
				//System.out.println("OppNum =" +j);
				
				if(j == myValue){
					tricksPerPerson[j] = 0;
					percHasTrump[j] = 0;
					percHasLessValueSameSuit[j] = 0;
					percHasHigherValueSameSuit[j] = 0;
					continue;
				}
				
				if(currentCard.getSuit().equals(GameInfo.trump)){
					percHasLessValueSameSuit[j] = 1.0 - oppOdds.higherValueSameSuit(j,currentCard);
				}else{
					percHasLessValueSameSuit[j] = oppOdds.lessValueSameSuit(j, currentCard);
				}
				
				percHasHigherValueSameSuit[j] = oppOdds.higherValueSameSuit(j,currentCard);
				
				if(currentCard.getSuit().equals(GameInfo.trump)){
					percHasTrump[j] = 0.0;
				} else {
					percHasTrump[j] = oppOdds.oddsPlayerHasSuit(j, oppOdds.getColNumFromSuit(GameInfo.trump));					// for each player, get these values
				}
				tricksPerPerson[j] = oppOdds.averageTricks(j);
				tricksTotal += tricksPerPerson[j];
				
				System.out.println("Player "+j);
				
				System.out.println("hasLessValueSameSuit = "+percHasLessValueSameSuit[j]);
				System.out.println("hasHigherValueSameSuit = "+percHasHigherValueSameSuit[j]);
				System.out.println("percHasTrump ="+percHasTrump[j]);
				System.out.println("tricksPerPerson ="+tricksPerPerson[j]);
				
				
			}
			
			
			for(int j = 0; j < 4; j++){
				if(j == myValue){
					oddsWillWinLeading[j] = 0;
					oddsUnderValueLed[j] = 0;
					oddsWillBeatIfLed[j] = 0;
					continue;
				}
				
				// lead value calculation
				
				double notForcedToUnderplayCard = 1.0 - ( percHasLessValueSameSuit[j] *  (1.0 - percHasHigherValueSameSuit[j]) );		// odds that you have a card of the same suit but less value, but dont also have a card of the same suit with higher value. This is the odds that you must play a card lower than the card played
				
				double oddsCanWin = 1.0 - (  (1.0 - percHasHigherValueSameSuit[j]) * (1.0 - percHasTrump[j])  );
				
				oddsWillWinLeading[j] = oddsCanWin * notForcedToUnderplayCard;	
				
				
				// follow value calculation
				
				
				oddsUnderValueLed[j] = (1.0 - notForcedToUnderplayCard) * (tricksPerPerson[j] / tricksTotal);			// Odds that they have a card same suit less value to lead, and then weighed by how likely they are to lead (expected percentage of tricks won)
				oddsWillBeatIfLed[j] = oddsWillWinLeading[j];			 // odds that person will beat my card if a card less than it of the same value is led. This is calculated by odds that person doesn't
				
				
			}
			
			// calculate leadValues
			int opp1 = (myValue + 1) % 4;
			int opp2 = (myValue + 3) % 4;
			double oddsIWin = (1.0 - oddsWillWinLeading[opp1]) * (1.0 - oddsWillWinLeading[opp2]) * (1.0 -oddsWillWinLeading[partnerValue]); // odds that I win the trick leading are  the odds that opp1 won't win and opp2 won't win.
			double oddsPartnerWins = (1.0 - oddsWillWinLeading[opp1]) * (1.0 - oddsWillWinLeading[opp2]) * (1.0 - oddsIWin);			// this isn't ideal
			double leadValue =  1.0 - ( (1.0 - oddsIWin) * (1.0 - oddsPartnerWins) );		// odds that the team will win if i lead this card is the complement of the odds that neither of us will win.
			
			
			// calculate followValues
			
			double oddsIWinIfLeads[] = new double[4];
			
			for(int j = 0; j < 4; j++){
				if(j == myValue){
					continue;
				}
				
				double oddsOppDoesntWin = 1;
				for(int k = 0; k < 4; k++){
					if( (j + k) % 4 != myValue){ //|| (j + k) % 4 != partnerValue){
						oddsOppDoesntWin *= (1.0 - oddsWillBeatIfLed[k]);					// odds an opponent doesnt win = the odds every opponent that doesn't lead can beat that card.
					}
				}
				
				oddsIWinIfLeads[j] = oddsUnderValueLed[j] * oddsOppDoesntWin;						// odds the I win if that person leads = the odds they lead an undervalued suit and that the opponents wont beat my card.
				
			}
			
			double oddsIDontWin = 1.0;
			for(int j = 0; j < 4; j++){
				
				oddsIDontWin *= (1.0 - oddsIWinIfLeads[j]);		// the chances I dont win all multiplied together
			}
			
			double followValue = 1.0 - oddsIDontWin; 			// odds I win is the complement of the odds I dont win any scenario
			
			double value = (leadValue * expectedPercentageOfWinningTricks) + (followValue * ( 1.0 - expectedPercentageOfWinningTricks) );				// this weighs the follow values of a card and the lead values by how likely you are to win a trick, because if you aren't likely to lead, the lead values aren't as important as the follow values.
			
			for(int j = 0; j < elCards.size(); j++){
				if(elCards.get(j).getSuit().equals(currentCard.getSuit()) && elCards.get(j).getValue() == currentCard.getValue()){				// add to cardValues if it matches an eligible card
					cardValues[elCardCounter] = value;
					leadValues[elCardCounter] = leadValue;
					followValues[elCardCounter] = followValue;
					elCardCounter++;
					break;
				}
			}
			
			double adjustedValue = (oddsIWin * expectedPercentageOfWinningTricks) + (followValue * ( 1.0 - expectedPercentageOfWinningTricks) );
			
			currentExpectedTricksToWin += adjustedValue;		// even if it isn't eligible, update the currentExpectedTricksToWin for next cardValues.
		}
		
	}
	
	public void adjustOpponentOdds(){
		
		if(GameInfo.players.get(myValue).getHand().size() != 5){					// don't go through and check off the previous trick if its the start of a new hand.
			for(int i = 0; i < GameInfo.previousTrick.size(); i++){
				
				Card currentCard = new Card(GameInfo.previousTrick.get(i));
				
				int[] xy = oppOdds.getIndexFromCard(currentCard);
				if(oppOdds.cardOdds[xy[0]][xy[1]].accountedFor == false){					// check if you already accounted for that card, meaning you played after that card in the previous trick and already adjusted the data structure accordingly.
					// find out who played the card, 
					oppOdds.oppPlayedCard((GameInfo.previousTrickLeader + i) % 4,currentCard, false);
				}
			}
		}
		
		System.out.println("currentTrick size = "+GameInfo.currentTrick.size());
		
		for(int i = 0; i < GameInfo.currentTrick.size(); i++){
			System.out.println(i);
			Card currentCard = new Card(GameInfo.currentTrick.get(i));
			
			int[] xy = oppOdds.getIndexFromCard(currentCard);
			if(oppOdds.cardOdds[xy[0]][xy[1]].accountedFor == false){
				// find out who played the card, 
				oppOdds.oppPlayedCard((GameInfo.currentTrickLeader + i) % 4, currentCard, true);
			}
		}
		
		//oppOdds.printOutCardIndices();
		
		
	}
	
	public Card leastValuableCard(){	// used to find the least valuable of the eligible cards. Likely in an instance when you know you can't win or contribute to your partner's liklihood of winning
		double worstValue = 2;
		int least = -1;
				
		for(int i = 0; i < elCards.size(); i++){		// iterates through eligible cards
			if(cardValues[i] < worstValue){					// if the value is greater than the previous max,
				least = i;								// save the index of the current least valuable card
				worstValue = cardValues[i];					// store the value of that card
			}
		}
		
		// once that loop finishes, least will be the index in eligible cards that is the least valuable
		
		leastValuableIndex = least;
		
		return elCards.get(least);	// return that card
	}
	
	public Card leastValuableWinningCard(){		// used to find the minimum card you can play but still beat the previous best card. Used when you're the last person to play.
		double worstValue = 2;
		int least = -1;
		
		for(int i = 0; i < winningCards.size(); i++){	// functions identically to the leastValuableCard, but only evaluates cards in the winningCards ArrayList instead
			if(winningCardValues[i] < worstValue){
				least = i;
				worstValue = winningCardValues[i];
			}
		}
		
		leastValuableIndex = least;
		
		return winningCards.get(least);
	}

	public void determineEligibleCards(){
		
		elCards.clear();		// eliminates previously calculated eligible cards
		
		ArrayList<Card> currentHand = GameInfo.players.get(myValue).getHand();	// extract hand from GameInfo
		
		if(GameInfo.currentTrick.size() == 0){					// if you are leading, every card is eligible.
			for(int i = 0; i < currentHand.size();i++){
				elCards.add(currentHand.get(i));
			}
			return;
		}
		
		// if you aren't leading, find all cards that follow suit.
		
		for(int i = 0; i < currentHand.size(); i++){							// iterates through hand
			if(currentHand.get(i).equals(GameInfo.ledSuit)){				// if it is the led suit, add the card to elCards. getEffectiveSuit is used to detect when the left is found
				elCards.add(currentHand.get(i));
			}
		}	
		
		
		if(elCards.size() == 0){							// if there are no eligible cards, this means you can't follow suit
			for(int i = 0; i < currentHand.size(); i++){	// so your entire hand is eligible to play
				elCards.add(currentHand.get(i));
			}
		}
		
	}
	
	public String suitColor(String suit){
		switch (suit){							// for determining if the jack has the same color as trump
		
		case "spades":
		case "clubs":
			return "black";
		case "hearts":
		case "diamonds":
			return "red";
		}
		
		return "error";
	}
	
	
	public void determineWinningCards(){
		
		winningCards.clear();							// clear old winning cards
		
		//System.out.println("Calculating winning cards");
		
		int winningCardValue;							// worth of the previously winning card
		cardWorths = new int[elCards.size()];		// worth of all eligible cards
		winningCardValues = new double[elCards.size()];	// global variable to save all eligible cards' values 

		int winningIndex = 0;
		
		for(int i = 0; i < 4; i++){
			if(((GameInfo.currentTrickLeader + i) % 4) == GameInfo.currentWinner){
				winningIndex = i;
				break;
			}
		}
		
		currentWinningCard = GameInfo.currentTrick.get(winningIndex);
		
		winningCardValue = cardWorth(currentWinningCard); // set worth of previously winning card
				
		for(int i = 0; i < elCards.size(); i++){
			cardWorths[i] = cardWorth(elCards.get(i));		// find card worth of every eligible card
		}
		
		int winningCardNum = 0;								// counter of winningCards, in order to store the values in the correct index of winningCardValues
		
		int worstCard = -1;
		int worstCardValue = 40;
		
		for(int i = 0; i < elCards.size(); i++){			// iterate through all eligible cards
			
			
			if(cardWorths[i] > winningCardValue){			// if that card's worth is higher than the previously winning card
				winningCards.add(elCards.get(i));			// add it to the winningCards global ArrayList
				winningCardValues[winningCardNum] = cardValues[i];	// set the value of that winningCard to the corresponding index of the global winningCardValues. This is used for computing the least valuable winning card
				winningCardNum++;							// increment counter
			}
			
			if(cardWorths[i] < worstCardValue){
				worstCardValue = cardWorths[i];				// keep track of worst eligible card value to use later
				worstCard = i;
			}
		}
		
		worstElCardWorth = worstCard;
		
	}
	
	public int cardWorth(Card currentCard){     //Returns worth of card depending on led suit
		
		int ledBonus = 7;			// bonus that will be added to the worth of the card if the suit was led. 
									// this allows the 9 of the led suit, (9 + ledBonus = 16), to be more valuable than a non-trump, non-led ace (14), which gets no bonuses
		
		int trumpBonus = 14;		// value added to trump to allow for the 9 of trump (9 + trumpBonus = 23) to be more valuable than a led ace (14 + ledBonus = 21)
		
		String cardSuit = currentCard.getSuit();
		
		if(currentCard.getValue() == 11 && suitColor(cardSuit) == suitColor(GameInfo.trump)){		// card value 11 means it's a jack
			// jack of the trump color. (Either the right or left jack)
			
			if(cardSuit == GameInfo.trump){		// card suit is trump, which means it's the left
				return trumpBonus + 16;			// return the highest possible worth (Higher than the ace of trump, which would be trumpBonus + 14 (value of an ace)  )
			} else {
				return trumpBonus + 15;			// returns the second highest possible worth (1 more than ace, but 1 lower than the right)
			}
		}
		
		// not the left or the right 
		
		if(cardSuit != GameInfo.ledSuit && cardSuit != GameInfo.trump){				// card is not the led suit or trump suit. Value is 0
			return 0;
			
		} else if(cardSuit == GameInfo.ledSuit && cardSuit != GameInfo.trump){		// card is the led suit but not trump
			return currentCard.getValue() + ledBonus;								// return actual value + ledBonus
			
		} else {																	// card is trump, trump may have been led. Doesn't matter if it was
			return currentCard.getValue() + trumpBonus;								// return actual value + trumpBonus (already handled the jacks)
		}
		
	}
	

	@Override
	public int calculateValues(String suit) {				// identical structure to mediumAI
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
				totalValue += addingCardValue[12];
				nextCard.setWorth(addingCardValue[12]);
			} else if(nextCard.getSuit() == suit){
				
				switch(nextCard.getValue()){
					case 9: totalValue += addingCardValue[7];
							nextCard.setWorth(addingCardValue[7]);
							break;
					case 10:totalValue += addingCardValue[8];
							nextCard.setWorth(addingCardValue[8]);
							break;
					case 11: totalValue += addingCardValue[13];
							 nextCard.setWorth(addingCardValue[13]);
							 break;
					case 12: totalValue += addingCardValue[9];
							 nextCard.setWorth(addingCardValue[9]);
							 break;
					case 13: totalValue += addingCardValue[10];
							 nextCard.setWorth(addingCardValue[10]);
							 break;
					case 14: totalValue += addingCardValue[11];
							 nextCard.setWorth(addingCardValue[11]);
							 break;
				}
			} else {
				totalValue += addingCardValue[nextCard.getValue() - 8];
				nextCard.setWorth(addingCardValue[nextCard.getValue() - 8]);
					
			}
			
			
		}
		return totalValue;
	}

	@Override
	public void removeCard(Card middle) {
		// TODO Auto-generated method stub
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
			hand.add(GameInfo.players.get(myValue).getHand().get(i));
		}		
		
		double most = -1;
		int mostIndex = -1;
		
		for(int i = 0; i < 5; i++){
			
			ArrayList<Card> newHand = new ArrayList<Card>();
			
			for(int j = 0; j < hand.size(); j++){
				newHand.add(hand.get(j));
			}	
			
			newHand.remove(i);
			newHand.add(middle);
			
			double newValue = expectedTricks(newHand, middle.getSuit());
			
			if(newValue > most){
				mostIndex = i;
				most = newValue;
			}
			
		}
		
		System.out.println("Removing Player "+ myValue+ "'s " + GameInfo.players.get(myValue).getHand().get(mostIndex).getSuit() + GameInfo.players.get(myValue).getHand().get(mostIndex).getValue());
		System.out.println("Adding card "+middle.getSuit() + middle.getValue());
		
		
		depositedCard = new Card(GameInfo.players.get(myValue).getHand().remove(mostIndex));
		cardDeposited = true;
		GameInfo.players.get(myValue).receiveCard(middle);		
		// GameInfo.players.get(myValue).hand = hand;
		
	}
	
	public ArrayList<Card> getRemoveCardHand(Card middle) {
		
		ArrayList<Card> hand = new ArrayList<Card>();
		
		for(int i = 0; i < GameInfo.players.get(myValue).getHand().size(); i++){
			hand.add(GameInfo.players.get(myValue).getHand().get(i));
		}
		
		
		double most = -1;
		int mostIndex = -1;
		
		for(int i = 0; i < 5; i++){
			ArrayList<Card> newHand = hand;
			
			newHand.remove(0);
			hand.add(middle);
			
			double newValue = expectedTricks(newHand, middle.getSuit());
			
			if(newValue > most){
				mostIndex = i;
				most = newValue;
			}
			
		}
		
		hand.remove(mostIndex);
		hand.add(middle);
		
		return hand;
	}

	public double expectedTricks(ArrayList<Card> hand, String trumpSuit){
		
		int numOfEachSuit[] = new int[4];						// frequency of each suit
		
		for(int i = 0; i < 4; i++){
			numOfEachSuit[i] = 0;					// init number of each suit to 0
		}
		
		for(int i = 0; i < hand.size();i++){
			numOfEachSuit[indexOfSuit(hand.get(i).getSuit())]++;			// get the effective suit based on the trump that's a paramter
		}
		
		int numTrump = numOfEachSuit[indexOfSuit(trumpSuit)];				// extract number of trump	
		int numSuited = 0;													// number of different suits
		
		for(int i = 0; i < 4; i++){
			if(numOfEachSuit[i] > 0){
				numSuited++;											// increment numSuited if you encounter a non-zero index
			}
		}
		
		double expectedTricks = 0;
		
		for(int i = 0; i < hand.size(); i++){
			expectedTricks += expectedTrickFromCard(hand.get(i), trumpSuit, numTrump, numSuited);			// iterate through the hand and add up expected tricks
		}	
		
		return expectedTricks;
	}
	
	public double expectedTrickFromCard(Card card, String trumpSuit, int numTrump, int numSuited){
		// get the expected Tricks from that card in the arrays
		// THIS NEEDS TO BE COMPLETED
		
		int trumpBonus = 14;
		
		if(card.getSuit().equals(trumpSuit)){
			return (card.getValue() + trumpBonus) / 28.0;
		} else {
			return (card.getValue()) / 28.0;
		}
		
	}
	
	public int indexOfSuit(String suit){
		switch(suit){
		case "spades":
			return 0;
		case "clubs":
			return 1;
		case "hearts":
			return 2;
		case "diamonds":
			return 3;
		default:
			return 0;
		}
	}
	
	
	public int calculateValuesAsDealer(Card middle) {				// identical structure to mediumAI
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
				totalValue += addingCardValue[12];
				nextCard.setWorth(addingCardValue[12]);
			} else if(nextCard.getSuit() == suit){
				
				switch(nextCard.getValue()){
					case 9: totalValue += addingCardValue[7];
							nextCard.setWorth(addingCardValue[7]);
							break;
					case 10:totalValue += addingCardValue[8];
							nextCard.setWorth(addingCardValue[8]);
							break;
					case 11: totalValue += addingCardValue[13];
							 nextCard.setWorth(addingCardValue[13]);
							 break;
					case 12: totalValue += addingCardValue[9];
							 nextCard.setWorth(addingCardValue[9]);
							 break;
					case 13: totalValue += addingCardValue[10];
							 nextCard.setWorth(addingCardValue[10]);
							 break;
					case 14: totalValue += addingCardValue[11];
							 nextCard.setWorth(addingCardValue[11]);
							 break;
				}
			} else {
				totalValue += addingCardValue[nextCard.getValue() - 8];
				nextCard.setWorth(addingCardValue[nextCard.getValue() - 8]);
					
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
