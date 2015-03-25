import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;


public class CardPress implements ActionListener {
	
	Card yourCard, yourFirstCard, yourSecondCard, yourThirdCard, yourFourthCard, pickOrPassCard;
	JLabel yourMiddleCard;
	
	public CardPress(Card yourCard, JLabel yourMiddleCard, Card yourFirstCard, Card yourSecondCard, Card yourThirdCard, Card yourFourthCard, Card pickOrPassCard) {
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
			//System.out.println(GameInfo.isPick);
			if(GameInfo.isPick == 1)
			{
				System.out.println(pickOrPassCard.path);
				switchCard(yourCard, pickOrPassCard);
			}
			else
			{
				playCard(yourCard, yourMiddleCard);
				disableCards();
			}
			GameInfo.isPick = 0;
			
			EuchreGame.getButton_press().release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public void switchCard(Card card, Card switchCard)
	{
		card.getButton().setIcon(switchCard.getNormalImage());
		card.setNormalImage(switchCard.getNormalImage());
		card.setCardId(switchCard.getCardId());
		card.setSuit(switchCard.getSuit());
		card.setValue(switchCard.getValue());
		//GameInfo.players.get(0).getHand().set(chosenCard, card);
		switchCard.getButton().setVisible(false);
		//Card tempCard = new Card();
		//tempCard = card;
		
		
	}
	public void playCard(Card card, JLabel middleCard)
	 {
		GameInfo.playedCard = card;
	 	System.out.println(card.getButton().getLabel());
	 	//middleCard.setCardId(card.getCardId());
	 	//middleCard.setSuit(card.getSuit());
	 	//middleCard.setValue(card.getValue());
	 	middleCard.setIcon(card.getNormalImage());
	 	//middleCard.getButton().setLabel(card.getButton().getLabel());
	 	middleCard.setVisible(true);
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
