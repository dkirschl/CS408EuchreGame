import java.util.ArrayList;

public class MediumAI extends AI{
	
	ArrayList<Card> myHand;
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
		return false;
	}


	@Override
	public String chooseSuit() {
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
		
		return "Hello";
	}
	

	@Override
	public Card playCard() {
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
		return new Card(0, 0, null);
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