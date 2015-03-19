import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;


public class HowToPlay {
	JPanel howToPlayScreen;
	
	public HowToPlay(Board board, int width, int height)
	{
		howToPlayScreen = new JPanel();
		initHowToPlay(board, width, height);
	}

	public void initHowToPlay(Board board, int width, int height)
	{
		JLabel rules = new JLabel();
		JScrollBar scroll = new JScrollBar();
		
		howToPlayScreen.setMaximumSize(new Dimension(width, height));
		howToPlayScreen.setSize(new Dimension(width, height));
		howToPlayScreen.setVisible(false);
		howToPlayScreen.setLayout(null);
		
		rules.setBounds(0,0,width,height);
		rules.setText(GameInfo.howTo);
		System.out.println(GameInfo.howTo);
		
		//howToPlayScreen.add(scroll);
		howToPlayScreen.add(rules);
		
		howToPlayScreen.setVisible(false);
	}
}
