
public class CardIndex {
	public double[] odds;
	public Card card;
	public double total = 0;
	public boolean accountedFor;
	public int myValue;
	
	public CardIndex(Card c, int myValue){
		card = c;
		odds = new double[5];
		this.myValue = myValue;
		
		
		
		for(int i = 0; i < 5; i++){
			
			if(i != myValue){
				if(i == 4){
					odds[i] = 4.0 / 19.0;
				} else {
					odds[i] = 5.0 / 19.0;
				}
			} else {
				odds[i] = 0;
			}
			
			total += odds[i];
			
		}
		accountedFor = false;
	}
	
	public void seen(){
		accountedFor = true;
	}
	
	public void zeroOdds(){
		seen();
		for(int i = 0; i < 5; i++){
			odds[i] = 0;
		}
	}
	
	public void calcTotal(){
		total = odds[0]+odds[1]+odds[2]+odds[3]+odds[4];
	}
	
	public double getTotal(){
		calcTotal();
		return total;
	}
}
