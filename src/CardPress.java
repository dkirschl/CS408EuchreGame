import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

/*
 * This class happens every time that one of the human cards gets pressed to determine what actions need to take place
 * Card presses occur when the Human player plays a card or when they pick up a card
 */
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
			if(GameInfo.isPick == 1) // this means that you were told to pick up the card and you need to switch it with a card in your hand
			{
				switchCard(yourCard, pickOrPassCard);
			}
			else // the card press means that you have to play a card
			{
				playCard(yourCard, yourMiddleCard);
				disableCards(); // set the card that you played to not visible and disable the rest of them
			}
			GameInfo.isPick = 0; // reset the pick
			
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
		switchCard.getButton().setVisible(false);	
	}
	public void playCard(Card card, JLabel middleCard)
	 {
		GameInfo.playedCard = card;
	 	middleCard.setIcon(card.getNormalImage());
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
