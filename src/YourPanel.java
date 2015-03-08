import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class YourPanel{
	int width, height, cardWidth, cardHeight;
	String name;
	Card middleCard;
	public ArrayList<Card> hand;
	JPanel yourPanel;

	public YourPanel(int width, int height, String name, Card middleCard, ArrayList<Card> hand)
	{
		//System.out.println("Created a new your panel");
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;
		this.hand = hand;
		yourPanel = new JPanel();
		yourPanel.setBackground(Color.blue);

		//initYourPanel();
	}
	
	public void initYourPanel()
	{	
		int yourXCoord = 0;
		int yourYCoord = height - height/5;
		int yourWidth = width;
		int yourHeight = height/5;
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		//System.out.println("Your Coordinates x: " + yourXCoord + " y: " + yourYCoord + " Dimensions width: " + yourWidth  + " height: " + yourHeight);
		yourPanel.setBounds(yourXCoord, yourYCoord, yourWidth, yourHeight);
		JLabel your = new JLabel(name);
		
		yourPanel.setLayout(null);
		your.setHorizontalAlignment(SwingConstants.CENTER);
		//your.setVerticalAlignment(SwingConstants.BOTTOM);
		your.setBounds(yourWidth/2 - 40, yourHeight-15, 80, 20);
		your.setVisible(true);
		
		yourPanel.add(your);
		System.out.println(hand.size());
		 
		int initialCardX = width/2-cardWidth/2-cardWidth-cardWidth-10;
		 
		hand.get(0).getButton().setBounds(initialCardX, 10, cardWidth, cardHeight);
		hand.get(1).getButton().setBounds(initialCardX + (cardWidth+5), 10, cardWidth, cardHeight);
		hand.get(2).getButton().setBounds(initialCardX + 2*(cardWidth+5), 10, cardWidth, cardHeight);
		hand.get(3).getButton().setBounds(initialCardX + 3*(cardWidth+5), 10, cardWidth, cardHeight);
		hand.get(4).getButton().setBounds(initialCardX + 4*(cardWidth+5), 10, cardWidth, cardHeight);
		
		System.out.println("Printing card in your panel");
		System.out.println(hand.get(0).getButton().getLabel());
		
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
