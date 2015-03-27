import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;

/*
 * This class gets called every time the Human player selects pick
 */
public class Pick implements ActionListener  {
	Card card;
	Button pick;
	Button pass;
	Board board;
	
	public Pick(Card card, Button pick, Button pass, Board board)
	{
		this.card = card;
		this.pick = pick;
		this.pass = pass;
		this.board = board;
	}
	public void actionPerformed(ActionEvent e) {
		try {
			EuchreGame.getHuman_turn().acquire();
			GameInfo.picked = true;
			pick(card);
			disableCards();
			
			EuchreGame.getButton_press().release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	public void disableCards()
	{
		pick.setVisible(false);
		pick.setEnabled(false);
		pass.setVisible(false);
		pass.setEnabled(false);
	}
	public void pick(Card b)
	{
		if(GameInfo.dealer == 0) // If you are the dealer then you have to pick up the card
		{
			enableCards();
		}
		else // else you disable the middle card
		{
			disableMiddleCard();
		}
	}
	public void enableCards()
	{
		YourPanel yourPanel = board.getYourPanel();
		ArrayList<Card> cards = yourPanel.hand;
		
		for(int x = 0; x < 5; x++)
		{
			cards.get(x).getButton().enable();
		}
		GameInfo.isPick = 1;
	}
	public void disableMiddleCard()
	{
		card.getButton().setVisible(false);
	}
}
