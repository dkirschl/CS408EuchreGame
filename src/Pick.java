import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Pick implements ActionListener  {
	Button card;
	Button pick;
	Button pass;
	
	public Pick(Button card, Button pick, Button pass)
	{
		this.card = card;
		this.pick = pick;
		this.pass = pass;
	}
	public void actionPerformed(ActionEvent e) {
		try {
			EuchreGame.getHuman_turn().acquire();
		
			pick(card);
			disableCards();
			
			EuchreGame.getButton_press().release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	public void disableCards()
	{
		card.setVisible(false);
		card.setEnabled(false);
		pick.setVisible(false);
		pick.setEnabled(false);
		pass.setVisible(false);
		pass.setEnabled(false);
	}
	public void pick(Button b)
	{
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

}
