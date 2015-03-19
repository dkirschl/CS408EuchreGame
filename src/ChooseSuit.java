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
		// TODO Auto-generated method stub
		try{
			EuchreGame.getHuman_turn().acquire();
			
			GameInfo.selectedSuit = suit;
			
			EuchreGame.getButton_press().release();
		} catch(InterruptedException e1)
		{
			
		}
		
	}

}
