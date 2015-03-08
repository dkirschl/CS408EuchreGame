import java.awt.Button;
import java.util.concurrent.Semaphore;


public class Human extends Player {

	@Override
	public Card playCard() {
		return GameInfo.playedCard;
	}

	@Override
	public String chooseSuit() {
		return GameInfo.selectedSuit;
	}

	@Override
	public void startTurn(Semaphore s) {
		//this will notify the action listeners of the cards that they can now be clicked
		System.out.println("Now accepting card presses");
		s.release();
		
	}

	@Override
	public void waitForClick(Semaphore s) {
		try {
			s.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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

	@Override
	public boolean pickupOrPass() {
		return GameInfo.picked;
	}

	@Override
	public boolean isHuman() {
		return true;
	}

}
