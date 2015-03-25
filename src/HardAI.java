import java.util.ArrayList;


public class HardAI extends AI {
	
	ArrayList<Card> elCards = new ArrayList<Card>();				// cards that are eligible to be played this trick
	ArrayList<Card> winningCards = new ArrayList<Card>();			// arraylist of cards that beat the previously winning card
	int myValue;						// index of you
	int partnerValue;					// index of your partner
	double []cardValues;				// values of all eligible cards
	double []winningCardValues;			// value of the cards that could beat the previously winning cards
	OpponentOdds oppOdds;				// class that holds the odds each player has each card
	
	int leastValuableIndex;
	
	double currentExpectedTricksToWin = 0;	
	
	int worstElCardWorth;				// the eligible card with the worst worth
	
	int cardWorths[];					// all of the eligible cards' worths
	
	int currentWinner;					// current winning player's index
	Card currentWinningCard;			// current best card
	
	public HardAI(){
		;
	}
	
	public HardAI(int value){
		myValue = value;
		partnerValue = (myValue + 2) % 4;			// calculate your partner's index
		oppOdds = new OpponentOdds(value);
	}

	@Override
	public boolean passOrPickUp() {
		
		ArrayList<Card> hand;
		
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
	
	}
	
	public double gainedValueFromPickUp(){
		
		return .5;
	}

	@Override
	public String chooseSuit() {
		
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
	}

	@Override
	public Card playCard() {
		
		System.out.println("Playing card from hard");
		
		if(GameInfo.players.get(myValue).getHand().size() == 5){
			currentExpectedTricksToWin = expectedTricks(GameInfo.players.get(myValue).getHand(),GameInfo.trump);			// need the expected tricks to win, and if you have 5 cards then the value is still for the old hand.
		}
		
		// add all cards that are eligible to play this turn into elCards
		determineEligibleCards();
		valueCards();
		
		// If there's only one card that's eligible, play it
		if(elCards.size() == 1){
			
			currentExpectedTricksToWin -= cardValues[0];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			return elCards.get(0);
		}
		
		determineCurrentWinner();
		
		
		if(GameInfo.currentTrick.size() == 0){
			// leading
			System.out.println("Player "+ myValue +" is leading");
			
			double bestValue = -10;
			int bestIndex = -1;
			
			for(int i = 0; i < elCards.size(); i++){				// iterate through all eligible cards, which is all of them when leading.
				
				double leadValue = calcLeadValue(elCards.get(i));	// find the lead value of the current card
				
				if(leadValue == 1.0){
					
					currentExpectedTricksToWin -= cardValues[i];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
					return elCards.get(i);							// if you can lead a card that will win 100% of the time, do it.
				}
				
				if(leadValue - cardValues[i] > bestValue){			// evaluate how much better this card is to lead now than it will be to hold on to.
					bestValue = leadValue - cardValues[i];			// if it the best value so far, store the information
					bestIndex = i;
					
				}
			}
			
			
			currentExpectedTricksToWin -= cardValues[bestIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			return elCards.get(bestIndex);							// return the card that improves your percentage chance of winning the most by being lead.
			
			
			
		} else if(GameInfo.currentTrick.size() == 1){
			// second
			System.out.println("Player "+ myValue +" is second");
			
			if(winningCards.size() == 0){
				// if you can't beat the current winner, return worst card
				
				Card lvCard = leastValuableCard();
				currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
				return lvCard;
			}
			
			double oddsTeamWins[];
			
			oddsTeamWins = generateOddsOfWinningPlayingWorstCard2(elCards.get(worstElCardWorth));
			
			double bestValue = -10;
			int bestIndex = -1;
			
			for(int i = 0; i < elCards.size(); i++){
				
				double oddsCardWins = 1.0 - oddsTeamWins[i];
				
				if(oddsCardWins - cardValues[i] > bestValue){
					bestValue = oddsCardWins - cardValues[i];
					bestIndex = i;
				}
				
			}
			
			currentExpectedTricksToWin -= cardValues[bestIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			return elCards.get(bestIndex);
			
			
			
		} else if(GameInfo.currentTrick.size() == 2){
			// third
			System.out.println("Player "+ myValue +" is third");
			
			if(winningCards.size() == 0){
				// if you can't beat the current winner, return worst card
				
				Card lvCard = leastValuableCard();
				currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
				return lvCard;
			}
			
			double oddsOppBeatsCard[];
			
				
			oddsOppBeatsCard = generateOddsOfWinningPlayingWorstCard3(elCards.get(worstElCardWorth));
			
			double bestValue = -10;
			int bestIndex = -1;
			
			for(int i = 0; i < elCards.size(); i++){
				
				double oddsCardWins = 1.0 - oddsOppBeatsCard[i];
				
				if(oddsCardWins - cardValues[i] > bestValue){
					bestValue = oddsCardWins - cardValues[i];
					bestIndex = i;
				}
				
			}
			
			currentExpectedTricksToWin -= cardValues[bestIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played4
			return elCards.get(bestIndex);
			
			
		} else if(GameInfo.currentTrick.size() == 3){				
			// last to play
			System.out.println("Player "+ myValue +" is last");
			
			if (currentWinner == partnerValue){
				// partner is winning
				
				Card lvCard = leastValuableCard();
				currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
			 	return lvCard;
			
			} else {
				// partner is not winning. Play winner if you can.
				determineWinningCards();
				if(winningCards.size() == 0){
					// can't win this hand
					
					Card lvCard = leastValuableCard();
					currentExpectedTricksToWin -= cardValues[leastValuableIndex];		//adjust currentExpectedTricksToWin for next turn. Must subtract the card that you just played
					return lvCard;
				
				} else {
					// can win, return worst card that will win.
					
					Card lvwCard = leastValuableWinningCard();
					currentExpectedTricksToWin -= winningCardValues[leastValuableIndex];
					return lvwCard;
				}
			}	
			
			
		} else {
			System.out.println("ERROR: CURRENT TRICK SIZE IS OVER 3");
			
			return elCards.get(0);
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
	
		
		
		
		
		System.out.println("I BELIEVE IT SHOULDN'T GET HERE");
		
		return oddsTeamWins;
	}
	
	
	public double[] generateOddsOfWinningPlayingWorstCard3(Card worstCard){		// for when you are 3rd

		Card newBestCard;
		
		
		if(cardWorth(worstCard) > cardWorth(currentWinningCard)){			// assigns
			newBestCard = worstCard;				// this means your worst card still beats the current winning card. Assign newBestCard to your worst card
		} else {
			newBestCard = currentWinningCard;		// if your worst card doesn't beat the previously winning card, newBestCard will be set to previously winning card
		}
		
		double odds = 0.0;							// this will serve as odds that the next opponent has the current card, or better.
		double oddsFollowSuit;						// odds that the opponent will be forced to follow the suit that has been led.
		
		double[] oddsTeamWinsCard = new double[elCards.size()];
		
		for(int i = 0; i < 5; i++){
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
						if(currentWinner == partnerValue){				// if the eligible card has not been encountered, but new best card has, then it will not be a winner. If partner is winning, odds are current odds. If not, odds are 1.0 that the opponent will win if that card is played because you will not gain the lead.
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
							if(currentWinner == partnerValue){
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
		
		
		
		return oddsTeamWinsCard;
	}
	
	
	public void determineCurrentWinner(){
		
		int max = -1;				// current maximum card worth found in currentTrick
		int currentLeader = -1;		// index of current leader of the trick
		
		for(int i = 0; i < GameInfo.currentTrick.size(); i++){				// iterate through entire trick
			int nextCardWorth = cardWorth(GameInfo.currentTrick.get(i));	// get card worth of current card in the trick
			
			if(nextCardWorth > max){										// if it's a new maximum worth
				currentLeader = i;											// set currentLeader to the current index of i
				max = nextCardWorth;										// and set the current max card worth to the current card's worth
			}
		}
		if(GameInfo.currentTrick.size() != 0){								// if you are not leading, because then currentLeader would be -1
			
			currentWinningCard = GameInfo.currentTrick.get(currentLeader);	// extract the current winning card from the current trick
			
			currentWinner = (currentLeader + GameInfo.previousLeader) % 4;	// find actual value of currentLeader (as opposed to the number of spots away from the leader like currentLeader found.)
		}
	}
	
	public void valueCards(){
		
		double followValues[] = new double[elCards.size()];
		double leadValues[] = new double[elCards.size()];
		
		for(int i = 0; i < elCards.size(); i++){
			followValues[i] = calcFollowValue(elCards.get(i));
			leadValues[i] = calcLeadValue(elCards.get(i));
		}
			
		double expectedPercentageOfWinningTricks = currentExpectedTricksToWin / GameInfo.players.get(myValue).getHand().size();	
			
		currentExpectedTricksToWin = 0;
		
		for(int i = 0; i < elCards.size(); i++){
			cardValues[i] = (leadValues[i] * expectedPercentageOfWinningTricks) + (followValues[i] * ( 1.0 - expectedPercentageOfWinningTricks) );				// this weighs the follow values of a card and the lead values by how likely you are to win a trick, because if you aren't likely to lead, the lead values aren't as important as the follow values.
			currentExpectedTricksToWin += cardValues[i];
		}
		
	}
	

	
	public double calcLeadValue(Card card){
		// NOT DONE
		
		return ( (double) cardWorth(card) ) / 30.0;
	}
	
	public double calcFollowValue(Card card){
		// NOT DONE
		
		return ( (double) cardWorth(card) ) / 30.0;
	}
	

	
	public Card leastValuableCard(){	// used to find the least valuable of the eligible cards. Likely in an instance when you know you can't win or contribute to your partner's liklihood of winning
		double max = -1;
		int least = -1;
				
		for(int i = 0; i < elCards.size(); i++){		// iterates through eligible cards
			if(cardValues[i] > max){					// if the value is greater than the previous max,
				least = i;								// save the index of the current least valuable card
				max = cardValues[i];					// store the value of that card
			}
		}
		
		// once that loop finishes, least will be the index in eligible cards that is the least valuable
		
		leastValuableIndex = least;
		
		return elCards.get(least);	// return that card
	}
	
	public Card leastValuableWinningCard(){		// used to find the minimum card you can play but still beat the previous best card. Used when you're the last person to play.
		double max = -1;
		int least = -1;
		
		for(int i = 0; i < winningCards.size(); i++){	// functions identically to the leastValuableCard, but only evaluates cards in the winningCards ArrayList instead
			if(winningCardValues[i] > max){
				least = i;
				max = cardValues[i];
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
			if(getEffectiveSuit(currentHand.get(i),GameInfo.trump).equals(GameInfo.ledSuit)){				// if it is the led suit, add the card to elCards. getEffectiveSuit is used to detect when the left is found
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
		
		int winningCardValue;							// worth of the previously winning card
		cardWorths = new int[elCards.size()];		// worth of all eligible cards
		winningCardValues = new double[elCards.size()];	// global variable to save all eligible cards' values 
		
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
	
	
	public String getEffectiveSuit(Card c, String trumpSuit){
	
		if(c.getValue() == 11 && c.getSuit() != trumpSuit && suitColor(c.getSuit()).equals(suitColor(trumpSuit)) ){	// if the card is a jack, not trump, but the same color, return trump
			System.out.println("card value: "+ c.getValue()+" and suit: "+ c.getSuit()+" when trump is "+trumpSuit+" is the left");
			return trumpSuit;
		} else {
			return c.getSuit();	// if not, return regular suit
		}
		
	}

	@Override
	public int calculateValues(String suit) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void removeCard(Card middle) {
		// TODO Auto-generated method stub
		
		ArrayList<Card> hand = GameInfo.players.get(myValue).getHand();
		
		
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
		
		// GameInfo.players.get(myValue).hand = hand;
		
	}
	
	public ArrayList<Card> getRemoveCardHand(Card middle) {
		ArrayList<Card> hand = GameInfo.players.get(myValue).getHand();
		
		
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
			numOfEachSuit[indexOfSuit(getEffectiveSuit(hand.get(i),trumpSuit))]++;			// get the effective suit based on the trump that's a paramter
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
		
		return 0.25;
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

}
