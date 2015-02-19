import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CardPress implements ActionListener {
	
	Button yourCard, yourMiddleCard, yourFirstCard, yourSecondCard, yourThirdCard, yourFourthCard;
	
	public CardPress(Button yourCard, Button yourMiddleCard, Button yourFirstCard, Button yourSecondCard, Button yourThirdCard, Button yourFourthCard) {
		this.yourCard = yourCard;
		this.yourMiddleCard = yourMiddleCard;
		this.yourFirstCard = yourFirstCard;
		this.yourSecondCard = yourSecondCard;
		this.yourThirdCard = yourThirdCard;
		this.yourFourthCard = yourFourthCard;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EuchreGame.getHuman_turn().acquire();
		
			playCard(yourCard, yourMiddleCard);
			disableCards();
			
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
	public void disableCards()
	{
		yourCard.setVisible(false);
		yourFirstCard.setEnabled(false);
		yourSecondCard.setEnabled(false);
		yourThirdCard.setEnabled(false);
		yourFourthCard.setEnabled(false);
	}
}
