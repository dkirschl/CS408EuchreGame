import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class ChooseSuit implements ActionListener{
	String suit;
	JButton suitButton;
	
	public ChooseSuit(JButton suitButton, String suit)
	{
		this.suitButton = suitButton;
		this.suit = suit;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
			//acquire human_turn semaphore which allows human to select a suit
			//returns when suit is selected
			EuchreGame.getHuman_turn().acquire();
			
			GameInfo.selectedSuit = suit;
			//release Button_press semaphore which allows game logic to continue
			EuchreGame.getButton_press().release();
		} catch(InterruptedException e1)
		{
			
		}
		
	}

}
