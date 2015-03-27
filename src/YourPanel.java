import java.awt.Button;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class YourPanel{
	int width, height, cardWidth, cardHeight;
	String name;
	JLabel middleCard;				// What the middle card is
	public ArrayList<Card> hand;	// Your cards in your hand
	JPanel yourPanel;

	public YourPanel(int width, int height, String name, JLabel middleCard, ArrayList<Card> hand)
	{
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;
		this.hand = hand;
		yourPanel = new JPanel();
		yourPanel.setBackground(Color.decode("#006600"));
	}
	
	public void initYourPanel()
	{	
		int yourXCoord = 0;
		int yourYCoord = height - height/5;
		int yourWidth = width;
		int yourHeight = height/5;
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		yourPanel.setBounds(yourXCoord, yourYCoord, yourWidth, yourHeight);
		JLabel your = new JLabel(name);
		
		your.setForeground(Color.white);
		yourPanel.setLayout(null);
		your.setHorizontalAlignment(SwingConstants.CENTER);
		your.setBounds(yourWidth/2 - 40, yourHeight-15, 80, 20);
		your.setVisible(true);
		
		yourPanel.add(your);
		 
		int initialCardX = width/2-cardWidth/2-cardWidth-cardWidth-10;
		 
		hand.get(0).getButton().setBounds(initialCardX, 10, cardWidth, cardHeight);
		hand.get(1).getButton().setBounds(initialCardX + (cardWidth+5), 10, cardWidth, cardHeight);
		hand.get(2).getButton().setBounds(initialCardX + 2*(cardWidth+5), 10, cardWidth, cardHeight);
		hand.get(3).getButton().setBounds(initialCardX + 3*(cardWidth+5), 10, cardWidth, cardHeight);
		hand.get(4).getButton().setBounds(initialCardX + 4*(cardWidth+5), 10, cardWidth, cardHeight);
		
		// Adds the action listener for the buttons in your hand and goes to the function card press
		hand.get(0).getButton().addActionListener(new CardPress(hand.get(0), middleCard, hand.get(1), hand.get(2), hand.get(3), hand.get(4), GameInfo.board.getMidPanel().getPickOrPassCard()));
		hand.get(1).getButton().addActionListener(new CardPress(hand.get(1), middleCard, hand.get(0), hand.get(2), hand.get(3), hand.get(4), GameInfo.board.getMidPanel().getPickOrPassCard()));
		hand.get(2).getButton().addActionListener(new CardPress(hand.get(2), middleCard, hand.get(1), hand.get(0), hand.get(3), hand.get(4), GameInfo.board.getMidPanel().getPickOrPassCard()));
		hand.get(3).getButton().addActionListener(new CardPress(hand.get(3), middleCard, hand.get(1), hand.get(2), hand.get(0), hand.get(4), GameInfo.board.getMidPanel().getPickOrPassCard()));
		hand.get(4).getButton().addActionListener(new CardPress(hand.get(4), middleCard, hand.get(1), hand.get(2), hand.get(3), hand.get(0), GameInfo.board.getMidPanel().getPickOrPassCard()));
		
		hand.get(0).getButton().setEnabled(false);
		hand.get(1).getButton().setEnabled(false);
		hand.get(2).getButton().setEnabled(false);
		hand.get(3).getButton().setEnabled(false);
		hand.get(4).getButton().setEnabled(false);
		 
		yourPanel.add(hand.get(1).getButton());
		yourPanel.add(hand.get(2).getButton());
		yourPanel.add(hand.get(3).getButton());
		yourPanel.add(hand.get(4).getButton());
		yourPanel.add(hand.get(0).getButton());
	}
}
