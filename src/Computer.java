
public class Computer extends Player {
	
	AI myAI;
	
	public Computer(){
		;
	}
	
	public Computer(int difficulty){
		if(difficulty == 1){
			myAI = new EasyAI();
		} else if(difficulty == 2){
			myAI = new MediumAI();
		} else if(difficulty == 3){
			myAI = new HardAI();
		} else {
			System.out.println("Invalid number passed to Computer");
			System.exit(0);
		}
	}
	
	@Override
	public Card playCard() {
		Card x = myAI.playCard();
		return x;
	}

	@Override
	public void chooseSuit() {
		return;
	}
	
}
