public class EasyAI extends AI{
	public EasyAI(){
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
		System.out.println("Playing card from easy");
		return 1;
	}
}