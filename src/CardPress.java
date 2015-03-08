import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CardPress implements ActionListener {
	
	Card yourCard, yourMiddleCard, yourFirstCard, yourSecondCard, yourThirdCard, yourFourthCard, pickOrPassCard;
	
	public CardPress(Card yourCard, Card yourMiddleCard, Card yourFirstCard, Card yourSecondCard, Card yourThirdCard, Card yourFourthCard, Card pickOrPassCard) {
		this.yourCard = yourCard;
		this.yourMiddleCard = yourMiddleCard;
		this.yourFirstCard = yourFirstCard;
		this.yourSecondCard = yourSecondCard;
		this.yourThirdCard = yourThirdCard;
		this.yourFourthCard = yourFourthCard;
		this.pickOrPassCard = pickOrPassCard;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			EuchreGame.getHuman_turn().acquire();
			System.out.println(GameInfo.isPick);
			if(GameInfo.isPick == 1)
			{
				switchCard(yourCard, pickOrPassCard);
			}
			else
			{
				playCard(yourCard, yourMiddleCard);
				disableCards();
			}

			
			EuchreGame.getButton_press().release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public void switchCard(Card card, Card switchCard)
	{
		System.out.println("Switching Cards!!");
		card.getButton().setLabel(switchCard.getButton().getLabel());
		card.setCardId(switchCard.getCardId());
		card.setSuit(switchCard.getSuit());
		card.setValue(switchCard.getValue());
		switchCard.getButton().setVisible(false);
		//Card tempCard = new Card();
		//tempCard = card;
		
		
	}
	public void playCard(Card card, Card middleCard)
	 {
		GameInfo.playedCard = card;
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
