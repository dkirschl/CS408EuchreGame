
public class CardIndex {
	public double[] odds;
	public Card card;
	public int total = 0;
	public boolean accountedFor;
	
	public CardIndex(Card c){
		card = c;
		odds = new double[5];
		accountedFor = false;
	}
	
	public void seen(){
		accountedFor = true;
	}
}
