public class HardAI extends AI {
	public HardAI(){
		;
	}

	@Override
	public boolean passOrPickUp() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public char chooseSuit() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int playCard() {
		// TODO Auto-generated method stub
		System.out.println("Playing card from hard");
		return 1;
	}
}