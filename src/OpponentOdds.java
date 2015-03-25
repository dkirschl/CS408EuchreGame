
public class OpponentOdds {
	CardIndex[][] cardOdds;
	int myNum;
	int[] oppTotals;
	
	public OpponentOdds(int num){
		cardOdds = new CardIndex[7][4];
		myNum = num;
		oppTotals = new int[5];
		
		for(int j = 0; j < 4; j++){
			oppTotals[j] = 500;
		}
		
		oppTotals[myNum] = 0;
		oppTotals[4] = 400;
	}
	
	
	
	public double oddsPlayerHasSuit(int oppNum, int suitNum){
		double oddsSuit = 1;
		
		for(int j = 0; j < 7; j++){
			oddsSuit *= 1.0 - ( ( (double) cardOdds[j][suitNum].odds[oppNum]) / 100.0);
		}
		
		return 1.0 - (double) oddsSuit;
	}
	
	public void oppPlayedCard(int playerNum, Card card){
		
	}
	
	public void adjustPlayerOdds(int playerNum, double[] changeInOdds){
		
	}
	
	public int[] getIndexFromCard(Card card){
		
		
		return new int[2];
	}
	
	public int getColNumFromSuit(String suit){
		
		for(int i = 0; i < 4; i++){
			if(suit.equals(cardOdds[0][i].card.getSuit())){
				return i;
			}
		}
		
		return -1;
		
	}
	
}
