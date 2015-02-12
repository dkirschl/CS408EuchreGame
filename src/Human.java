import java.util.concurrent.Semaphore;


public class Human extends Player {

	@Override
	public Card playCard() {
		return null;
	}

	@Override
	public void chooseSuit() {

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

}
