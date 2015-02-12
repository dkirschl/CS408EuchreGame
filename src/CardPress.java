import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CardPress implements ActionListener {
	
	Button yourCard, yourMiddleCard;
	
	public CardPress(Button yourCard, Button yourMiddleCard) {
		this.yourCard = yourCard;
		this.yourMiddleCard = yourMiddleCard;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EuchreGame.getHuman_turn().acquire();
		
			playCard(yourCard, yourMiddleCard);
			
			EuchreGame.getButton_press().release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public void playCard(Button card, Button middleCard)
	 {
	 	System.out.println(card.getLabel());
	 	middleCard.setLabel(card.getLabel());
	 	middleCard.setVisible(true);
	 }
	
	

}
