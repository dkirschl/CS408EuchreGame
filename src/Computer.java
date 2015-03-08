import java.awt.Button;
import java.util.concurrent.Semaphore;


public class Computer extends Player {
	
	AI myAI;
	int myValue;
	
	public Computer(){
		;
	}
	
	
	public Computer(int value, int difficulty){
		if(difficulty == 1){
			myAI = new EasyAI(value);
		} else if(difficulty == 2){
			myAI = new MediumAI(value);
		} else if(difficulty == 3){
			myAI = new HardAI(value);
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
	public boolean chooseSuit(Card c) {
		return true;
	}

	@Override
	public void startTurn(Semaphore s) {
		//DO NOTHING
		
	}

	@Override
	public void waitForClick(Semaphore s) {
		// DO NOTHING
		
	}

	@Override
	public Card pick(Button b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Card pass(Button b) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
