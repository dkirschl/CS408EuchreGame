import java.util.ArrayList;

public class OpponentOdds {						// THIS CLASS ONLY MATTERS TO HARDAI. It's purpose is to keep track of what the odds are that each player has a certain card.
	CardIndex[][] cardOdds;
	int myNum;
	double[] oppTotals;
	String suitOrder[] = {"spades", "clubs", "hearts", "diamonds"};
	int[] initialCardsInHand;
	int[] cardsPlayed;
	int[] numSureCards;
	int[] numRuledOutCards;
	
	public OpponentOdds(int num, Card depositedCard){
		cardOdds = new CardIndex[7][4];
		myNum = num;
		oppTotals = new double[5];
		cardsPlayed = new int[5];
		numSureCards = new int[5];
		numRuledOutCards = new int[5];
		initialCardsInHand = new int[5];
		
		for(int j = 0; j < 4; j++){
			cardsPlayed[j] = 0;
			oppTotals[j] = 500;
			initialCardsInHand[j] = 5;
		}
		
		oppTotals[myNum] = 0;
		oppTotals[4] = 400;
		initialCardsInHand[4] = 4;
		
		initCardOdds(depositedCard);
	}
	
	public void printOutCardIndices(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 7; j++){
				System.out.println("cardOdds["+j+"]["+i+"].card ="+cardOdds[j][i].card.getSuit()+cardOdds[j][i].card.getValue()+" odds: 0 - "+cardOdds[j][i].odds[0]+", 1 - "+cardOdds[j][i].odds[1]+", 2 - "+cardOdds[j][i].odds[2]+", 3 - "+cardOdds[j][i].odds[3]+", buried - "+cardOdds[j][i].odds[4]+"   TOTAL = "+cardOdds[j][i].total);
			}
		}
		System.out.print("TOTALS: ");
		for(int i =0; i < 5; i++){
			System.out.print(" "+i+": "+oppTotals[i]);
		}
	}
	
	public void initCardOdds(Card depositedCard){
		for(int i = 0; i < 4; i++){
			Card currentCard = new Card();
			currentCard.setSuit(suitOrder[i]);
			int cardIndex = 0;
			if(suitOrder[i].equals(GameInfo.trump)){
				// suit is trump
				cardIndex = 0;
				for(int j = 0; j < 7; j++){
					
					if((16 - cardIndex) == 11){
						cardIndex++;
					}
					
					currentCard.setValue(16 - cardIndex);
					cardOdds[j][i] = new CardIndex(new Card(currentCard), myNum);
					
					cardIndex++;
				}
				
				
			}else if(currentCard.getSuit().equals(getOtherColorSuit(GameInfo.trump)) ){
				// skip jack
				
				cardIndex = 0;
				for(int j = 0; j < 7; j++){						// go through each index
					if( (14 - cardIndex) == 11 ){
						cardIndex++;							// if it's the jack, skip because its in the trump array
					}
					
					currentCard.setValue(14 - cardIndex);		
					cardOdds[j][i] = new CardIndex(new Card(currentCard), myNum);
					
					if(j == 5 || j == 6){
						cardOdds[j][i].zeroOdds();				// if it's 5 or 6 then they are useless indices so set them all to 0.
					}
					cardIndex++;
				}
				
			} else {
				// every value in consecutive order
				//System.out.println("trump ="+GameInfo.trump+" and otherColorSuit="+getOtherColorSuit(GameInfo.trump)+" != currentCard suit="+currentCard.getSuit());
				for(int j = 0; j < 7; j++){
					currentCard.setValue(14 - j);
					cardOdds[j][i] = new CardIndex(new Card(currentCard), myNum);
					
					if(j == 6){
						cardOdds[j][i].zeroOdds();
					}
				}
				
			}
			
			
			
		}
		
		// zero odds of all cards in your hand.
		// zero odds of the middle card.
		ArrayList<Card> myHand = new ArrayList<Card>(GameInfo.players.get(myNum).getHand());
		
		int[] cardCoordinates;
		for(int i = 0; i < myHand.size(); i++){
			cardCoordinates = getIndexFromCard(myHand.get(i));
			cardOdds[cardCoordinates[0]][cardCoordinates[1]].zeroOdds();
		}
		cardCoordinates = getIndexFromCard(GameInfo.middleCard);
		cardOdds[cardCoordinates[0]][cardCoordinates[1]].zeroOdds();
		
		// Give middle card to somebody
		if(GameInfo.trump.equals(GameInfo.middleCard.getSuit())){
			if(GameInfo.dealer == myNum){
				givePersonCard(4,depositedCard);
			} else {
				givePersonCard(GameInfo.dealer,GameInfo.middleCard);
			}
			// card was asked to be picked up
		} else {
			// chose suit, middle card is buried.
			givePersonCard(4,GameInfo.middleCard);		
		}
		// if you're the dealer, buried played card you discarded. 
		if(GameInfo.trumpCaller != myNum){
			playerCalledTrump(GameInfo.trumpCaller, GameInfo.trump);
		}
		
		//printOutCardIndices();
		
	}
	

	public void recalculateTotals(){
		
		for(int i = 0; i < 5; i++){
			oppTotals[i] = 0;
			numSureCards[i] = 0;
			numRuledOutCards[i] = 0;
		}
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 7; j++){
				cardOdds[j][i].calcTotal();
				CardIndex currentCardIndex = cardOdds[j][i];
				for(int k = 0; k < 5; k++){
					if(currentCardIndex.odds[k] >= 1.0){
						numSureCards[k]++;
					} else if(currentCardIndex.odds[k] <= 0){
						numRuledOutCards[k]++;
					}
					oppTotals[k] += currentCardIndex.odds[k]; 
				}
			}
		}
		
	}
	
	public double lessValueSameSuit(int oppNum, Card currentCard){
		
		double oddsDoesntHaveAny = 1.0;
		
		for(int i = 6; i >= 0; i--){
			

			CardIndex cardIndex = cardOdds[i][getColNumFromSuit(currentCard.getSuit())];
			
			if(currentCard.getValue() == cardIndex.card.getValue() && currentCard.getSuit().equals(cardIndex.card.getSuit())){
			
				break;
			} 
			oddsDoesntHaveAny *= 1.0 - cardIndex.odds[oppNum];
		}
		
		
		return 1.0 - oddsDoesntHaveAny;
	}
	
	
	public double higherValueSameSuit(int oppNum, Card currentCard){
		
		double oddsDoesntHaveAny = 1.0;
		
		for(int i = 0; i < 7; i++){
			

			CardIndex cardIndex = cardOdds[i][getColNumFromSuit(currentCard.getSuit())];
			
			if(currentCard.getValue() == cardIndex.card.getValue() && currentCard.getSuit().equals(cardIndex.card.getSuit())){
				break;
			}
			oddsDoesntHaveAny *= 1.0 - cardIndex.odds[oppNum];
		}
		
		
		return 1.0 - oddsDoesntHaveAny;
	}
	
	public double oddsPlayerHasSuit(int oppNum, int suitNum){
		double oddsSuit = 1;
		
		for(int j = 0; j < 7; j++){
			oddsSuit *= 1.0 - ( (double) cardOdds[j][suitNum].odds[oppNum]);
		}
		
		return 1.0 - (double) oddsSuit;
	}
	
	public void givePersonCard(int playerNum, Card card){
		
		//printOutCardIndices();
		
		//System.out.println("player "+playerNum+" received card"+ card.getSuit() + card.getValue());

		double diffOdds[][] = new double[7][4];
		
		int [] xy = getIndexFromCard(card);
		
		diffOdds[ xy[0] ][ xy[1] ] = 1.0 - (double) cardOdds[ xy[0] ][ xy[1] ].odds[playerNum];
		
		//System.out.print("Previous odds were "+ cardOdds[ xy[0] ][ xy[1] ].odds[playerNum]);
		
		cardOdds[ xy[0] ][ xy[1] ].odds[playerNum] = 1.0;
		
		recalculateTotals();
		
		//System.out.println("Recalculated totals and it now equals ="+ oppTotals[playerNum]);
		
		double percChange;
		
		int targetTotal = initialCardsInHand[playerNum] - cardsPlayed[playerNum] - numSureCards[playerNum];
		
		//System.out.println("Target Total = "+targetTotal);
		
		
		
		double currentTotal = oppTotals[playerNum] - numSureCards[playerNum];
		
		//System.out.println("current Total ="+currentTotal);
		
		if(currentTotal != 0){
			percChange = (double) targetTotal / currentTotal;
		} else {
			percChange = 0;
		}
		
		//System.out.println("percChange = "+percChange);
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 7; j++){
				if(xy[0] == j && xy[1] == i){
					continue;						// don't adjust diff value because it is the middle card
				}
				double beforeValue = cardOdds[j][i].odds[playerNum];
				if(beforeValue > 0 && beforeValue < 1.0){
					double afterValue;
					afterValue = cardOdds[j][i].odds[playerNum] = beforeValue * percChange; 
					diffOdds[j][i] = afterValue - beforeValue;
				} else if(beforeValue >= 1.0){
					diffOdds[j][i] = 0;
				} else {
					diffOdds[j][i] = 0;
				}
			}
		}
		
		adjustPlayerOdds(playerNum, diffOdds);
		
	}
	
	public void oppPlayedCard(int playerNum, Card card, boolean currentTrick){
		
		//System.out.println("Opponent Played Card:"+ card.getSuit() + card.getValue());
		
		double diffOdds[][] = new double[7][4];
		
		
		int[] xy = getIndexFromCard(card);
		
		diffOdds[ xy[0] ][ xy[1] ] = -1 * cardOdds[xy[0]][xy[1]].odds[playerNum];			
		cardOdds[ xy[0] ][ xy[1] ].odds[playerNum] = 1.0;

		
		String ledSuit;
		
		if(currentTrick){
			ledSuit = GameInfo.currentTrick.get(0).getSuit();
		} else {
			ledSuit = GameInfo.previousTrick.get(0).getSuit();
		}

		if(!ledSuit.equals(card.getSuit())){				// if they didnt follow suit
				
			int suitNum = getColNumFromSuit(ledSuit);
				
			for(int i = 0; i < 7; i++){
				diffOdds[i][suitNum] = -1 * cardOdds[i][suitNum].odds[playerNum];
				cardOdds[i][suitNum].odds[playerNum] = 0;
			}
		}
		
		
		
		recalculateTotals();
		
		double percChange; 
		
		int targetTotal = initialCardsInHand[playerNum] - cardsPlayed[playerNum] - numSureCards[playerNum];				// number of cards that you aren't sure of in their hand
		
		double currentTotal = oppTotals[playerNum] - numSureCards[playerNum];
		
		if(currentTotal != 0){
			percChange = (double) targetTotal / currentTotal;
		} else {
			percChange = 0;
		}
		
		
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 7; j++){
				if(xy[0] == j && xy[1] == i){
					continue;						// don't adjust diff value because it is the middle card
				}
				double beforeValue = cardOdds[j][i].odds[playerNum];
				if(beforeValue > 0 && beforeValue < 1.0){
					double afterValue;
					afterValue = cardOdds[j][i].odds[playerNum] = beforeValue * percChange; 
					diffOdds[j][i] = afterValue - beforeValue;
				} else if(beforeValue >= 1.0){
					diffOdds[j][i] = 0;
				} else {
					diffOdds[j][i] = 0;
				}
			}
		}
		
		adjustPlayerOdds(playerNum, diffOdds);
		
		cardsPlayed[playerNum]++;
		cardOdds[ xy[0] ][ xy[1] ].zeroOdds();

		
	}
	
	public void playerCalledTrump(int playerNum, String suit){
		
		
		//System.out.println("playerCalledTrump");
		
		double diffOdds[][] = new double[7][4];
		
		int trumpNum = getColNumFromSuit(suit);
		
		// adjust playerNum's values around the change in trump
		
		int numUnaccountedTrump = 0;
		int numOwnedTrump = 0;
		for(int i = 0; i < 7; i++){
			if(cardOdds[i][trumpNum].odds[playerNum] != 0){
				if(cardOdds[i][trumpNum].odds[playerNum] != 1){
					numUnaccountedTrump++;
				} else {
					numOwnedTrump++;
				}
			}
		}
		
		double averageTrumpsExpected;
		
		if(GameInfo.screwDealer && playerNum == GameInfo.dealer){
			averageTrumpsExpected = 2.5;
		} else {
			averageTrumpsExpected = 3.3;
		}
		
		if(GameInfo.middleCard.getSuit().equals(suit)){
			if(GameInfo.dealer == playerNum){
				// gave it to himself
				averageTrumpsExpected = 3.6;
			}
			else if(GameInfo.dealer == ((playerNum + 2) % 4)){
				// if he gave it to his
				averageTrumpsExpected = 3.2;
				
			} else {
				// gave to other team
				averageTrumpsExpected = 3.8;
			}
		}
		
		double trumpPerc = (averageTrumpsExpected - numOwnedTrump) / numUnaccountedTrump;				// odds he has unaccountedfor trump is the number of unknown trump we expected him to have (totalExpected - numberKnown), divided by the total number of unaccounted for (number of trump that are possible for him to have)
		
		if(trumpPerc > 1){
			trumpPerc = 1;
		}
		
		for(int i = 0; i < 7; i++){
			double beforeValue = cardOdds[i][trumpNum].odds[playerNum];
			if(beforeValue > 0 && beforeValue < 1){
				cardOdds[i][trumpNum].odds[playerNum] = trumpPerc;
				diffOdds[i][trumpNum] = trumpPerc - beforeValue;
			}
		}
		
		recalculateTotals();
		
		double percChange; 
		
		int targetTotal = initialCardsInHand[playerNum] - cardsPlayed[playerNum] - numSureCards[playerNum];				// number of cards that you aren't sure of in their hand
		
		double currentTotal = oppTotals[playerNum] - numSureCards[playerNum];
		
		if(currentTotal != 0){
			percChange = (double) targetTotal / currentTotal;
		} else {
			percChange = 0;
		}
		
		
		for(int i = 0; i < 4; i++){
			if(i == trumpNum){
				// don't readjust trump odds
				continue;
			}
			for(int j = 0; j < 7; j++){
				double beforeValue = cardOdds[j][i].odds[playerNum];
				if(beforeValue > 0 && beforeValue < 1.0){
					double afterValue;
					afterValue = cardOdds[j][i].odds[playerNum] = beforeValue * percChange; 
					diffOdds[j][i] = afterValue - beforeValue;
				} else if(beforeValue >= 1.0){
					diffOdds[j][i] = 0;
				} else {
					diffOdds[j][i] = 0;
				}
			}
		}
		
		adjustPlayerOdds(playerNum, diffOdds);
		
	}
	
	public void adjustPlayerOdds(int playerNum, double[][] changeInOdds){
		// BIG TO DO!
		/*
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 7; j++){
				
				System.out.println("Player "+playerNum+"'s  diff["+j+"]["+i+"].card ="+cardOdds[j][i].card.getSuit()+cardOdds[j][i].card.getValue()+" changeInOdds: "+changeInOdds[j][i]);
				
			}
		}
		*/
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 7; j++){
				if(changeInOdds[j][i] == 0){
					continue;						// no change in this one.
				}
				CardIndex currentCardIndex = cardOdds[j][i];
				
				double oldSupply = 1.0 - (currentCardIndex.odds[playerNum] - changeInOdds[j][i]);
				double newSupply = 1.0 - currentCardIndex.odds[playerNum];
				
				for(int k = 0; k < 5; k++){
					if(k == playerNum){
						continue;
					}
					
					double oldPerc = currentCardIndex.odds[k];
					
					if(oldPerc == 0){
						continue;			// don't need to evaluate, because you have already been ruled out of having that card
					}
					
					double oldSharePerc = oldPerc / oldSupply;
					double newShare = oldSharePerc * newSupply;
					
					cardOdds[j][i].odds[k] = newShare;
					
					// might want to add something to go through all of k's supplies to adjust values. Might be too much.
				}
				
			}
		}
		
	}
	
	public int[] getIndexFromCard(Card card){
		
		System.out.println("Trump is "+GameInfo.trump);
		
		System.out.println("Card "+card.getSuit()+card.getValue());
		
		int retVal[] = new int[2];
		retVal[1] = getColNumFromSuit(card.getSuit());

		if(card.getSuit().equals(GameInfo.trump)){
			if(card.getValue() > 11){
				retVal[0] = 16 - card.getValue();
			} else {
				retVal[0] = 15 - card.getValue();
			}
		} else if(card.getSuit().equals(getOtherColorSuit(GameInfo.trump))){
			if(card.getValue() > 11){
				retVal[0] = 14 - card.getValue();
			} else {
				retVal[0] = 13 - card.getValue();
			}
		
		
		} else {
			retVal[0] = 14 - card.getValue();
		}
		
		
		System.out.println("Coordinates are x:"+retVal[0]+" y:"+retVal[1]);
		
		return retVal;
	}
	
	public int getColNumFromSuit(String suit){
		
		for(int i = 0; i < 4; i++){
			if(suit.equals(cardOdds[0][i].card.getSuit())){
				return i;
			}
		}
		
		return -1;
		
	}
	
	public double averageTricks(int playerNum){
		
		return 1;
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
	
	public String getOtherColorSuit(String suit){
		switch(suit){
		
		case "spades":
			return "clubs";
		case "clubs":
			return "spades";
		case "hearts":
			return "diamonds";
		case "diamonds":
			return "hearts";
			
		}
		
		return "error";
	}
	
}
