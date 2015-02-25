import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CardPress implements ActionListener {
	
	Card yourCard, yourMiddleCard, yourFirstCard, yourSecondCard, yourThirdCard, yourFourthCard;
	
	public CardPress(Card yourCard, Card yourMiddleCard, Card yourFirstCard, Card yourSecondCard, Card yourThirdCard, Card yourFourthCard) {
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
	
	public void playCard(Card card, Card middleCard)
	 {
	 	System.out.println(card.getButton().getLabel());
	 	middleCard.getButton().setLabel(card.getButton().getLabel());
	 	middleCard.getButton().setVisible(true);
	 }
	public void disableCards()
	{
		yourCard.getButton().setVisible(false);
		yourFirstCard.getButton().setEnabled(false);
		yourSecondCard.getButton().setEnabled(false);
		yourThirdCard.getButton().setEnabled(false);
		yourFourthCard.getButton().setEnabled(false);
	}
}
