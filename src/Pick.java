import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;


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
		//card.getButton().setVisible(false);
		//card.getButton().setEnabled(false);
		pick.setVisible(false);
		pick.setEnabled(false);
		pass.setVisible(false);
		pass.setEnabled(false);
	}
	public void pick(Card b)
	{
		//******* The human player is the dealer *******\\
		if(GameInfo.dealer == 0)
		{
			System.out.println("YOU ARE THE DEALER");
			enableCards();
		}
		else
		{
			disableMiddleCard();
		}
		//******* Need to change out the cards if they are the dealer *******\\
		/*
		 Going to need a function or a variable that can get who the current dealer in the game is other than in the while loop
		
		 If(player is dealer)
		 	enable the card buttons
		 	whichever card button is selected gets switched out with the current card you want to pick up
		 	game then starts as normal and player plays
		 else if (player isn't dealer)
		 	ai has to pick up the cards
		 */
	}
	public void enableCards()
	{
	//	System.out.println(board.toString());
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
