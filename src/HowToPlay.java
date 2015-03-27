import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class HowToPlay {
	JInternalFrame howToPlayScreen;
	
	public HowToPlay(Board board, int width, int height)
	{
		howToPlayScreen = new JInternalFrame();
		initHowToPlay(board, width, height);
	}

	public void initHowToPlay(Board board, int width, int height)
	{
		JTextArea rules = new JTextArea();
		JLabel title = new JLabel("<html><U>RULES</U></html");
		//ScrollPane scroll = new JScrollBar();
		
		howToPlayScreen.setMaximumSize(new Dimension(width, height));
		howToPlayScreen.setSize(new Dimension(width, height));
		howToPlayScreen.setVisible(false);
		howToPlayScreen.setClosable(true);
		howToPlayScreen.setLayout(null);
		
		title.setBounds((width-200)/2, 0, width-200, 40);
		Font font = title.getFont();
		Font boldFont = new Font(font.getName(), Font.BOLD, font.getSize());
		title.setFont(boldFont);
		
		rules.setBounds(10,40,width-200,height);
		rules.setText(GameInfo.howTo);
		rules.setEditable(false);
		rules.setCursor(null);
		rules.setLineWrap(true);
		rules.setWrapStyleWord(true);
		rules.setOpaque(false);
		
		System.out.println(GameInfo.howTo);
		
		JScrollPane scroll = new JScrollPane(rules);
		//scroll.setBounds(0, 0, 150, height);
		
		howToPlayScreen.add(title);
		howToPlayScreen.add(scroll);
		howToPlayScreen.add(rules);
		
		JButton okayButton = new JButton("I understand!");
		okayButton.setBounds(width-150, height-150, 125, 75);
		okayButton.setEnabled(true);
		okayButton.setVisible(true);
		okayButton.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent arg0) {
						howToPlayScreen.setVisible(false);	
					}
				});
		
		howToPlayScreen.add(okayButton);
		
		howToPlayScreen.setVisible(false);
	}
}
