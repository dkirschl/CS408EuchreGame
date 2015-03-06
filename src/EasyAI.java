import java.util.ArrayList;
import java.util.Random;

public class EasyAI extends AI{
	
	ArrayList<Card> elCards = new ArrayList<Card>();
	int myValue;
	
	public EasyAI(){
		;
	}
	
	public EasyAI(int value){
		myValue = value;
	}

	/*
	 * 	Easy AI will pick up 20% of the time based on random number generator
	 */
	@Override
	public boolean passOrPickUp() {
		Random randomGenerator = new Random();
	    int randomInt = randomGenerator.nextInt(5);
	    
	    /*
	     * If the random integer is 0: pick up, else: pass
	     */
	    if(randomInt == 0){
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
		for(int i = 0; i < Player.hand.size(); i++){
			switch(Player.hand.get(i).getSuit()){
				
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
			//Return value for spades
		} else if(clubs >= 3){
			//Return value for clubs
		} else if(hearts >= 3){
			//Return value for hearts
		} else if(diamonds >= 3){
			//Return value for diamonds
		} else {
			//Check to see if you are the dealer and screw the dealer is on, else pass
		}

		return "Hello";
	}
	
	/*
	 * 	Easy AI will determine eligible cards and play their highest valued card
	 */
	@Override
	public Card playCard() {
		//Determine the leading suit
		String leadSuit;
		
		/*
		 * Clear elCards from previous trick
		 */
		while(elCards.size() != 0){
			elCards.remove(0);
		}
		
		if(GameInfo.currentTrick.size() == 0){
			//Play highest valued card since you are the leader
		} else {
			leadSuit = GameInfo.currentTrick.get(0).getSuit();
			for(int i = 0; i < Player.hand.size(); i++){
				Card nextCard = Player.hand.get(i);
				
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
			return elCards.get(0);
		} else if(elCards.size() == 0){
			/*
			 * Play lowest valued card since you are going to lose unless you play trump
			 */
		} else {
			/*
			 * Play highest valued eligible card
			 */
		}
		return new Card(0, 0, null);
	}
}