import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Pass implements ActionListener  {
	Card card;
	Button pick;
	Button pass;
	
	public Pass(Card card, Button pick, Button pass, Board board)
	{
		this.card = card;
		this.pick = pick;
		this.pass = pass;
	}
	public void actionPerformed(ActionEvent e) {
		try {
			EuchreGame.getHuman_turn().acquire();
		
			pass(card);
			
			EuchreGame.getButton_press().release();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	public void disableCards()
	{
		card.getButton().setVisible(false);
		card.getButton().setEnabled(false);
		pick.setVisible(false);
		pick.setEnabled(false);
		pass.setVisible(false);
		pass.setEnabled(false);
	}
	public void pass(Card b)
	{
		disableCards();
	}
}
