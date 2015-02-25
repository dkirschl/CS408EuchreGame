import java.awt.Button;
import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class YourPanel extends JPanel{
	int width, height, cardWidth, cardHeight;
	String name;
	Card middleCard;
	ArrayList<Card> hand;
	
	public YourPanel(int width, int height, String name, Card middleCard, ArrayList<Card> hand)
	{
		System.out.println("Created a new your panel");
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;
		this.hand = hand;

		initYourPanel();
	}
	
	public void initYourPanel()
	{
		setBackground(Color.blue);
		
		int yourXCoord = 0;
		int yourYCoord = height - height/5 - 62;
		int yourWidth = width;
		int yourHeight = height/5;
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		//System.out.println("Your Coordinates x: " + yourXCoord + " y: " + yourYCoord + " Dimensions width: " + yourWidth  + " height: " + yourHeight);
		setBounds(yourXCoord, yourYCoord, yourWidth, yourHeight);
		JLabel your = new JLabel(name);
		
		setLayout(null);
		your.setHorizontalAlignment(SwingConstants.CENTER);
		//your.setVerticalAlignment(SwingConstants.BOTTOM);
		your.setBounds(yourWidth/2 - 40, yourHeight-25, 80, 40);
		
		add(your);
		System.out.println(hand.size());
		 
		int initialCardX = 270;
		 
		hand.get(0).getButton().setBounds(initialCardX, 15, cardWidth, cardHeight);
		hand.get(1).getButton().setBounds(initialCardX + (cardWidth+5), 15, cardWidth, cardHeight);
		hand.get(2).getButton().setBounds(initialCardX + 2*(cardWidth+5), 15, cardWidth, cardHeight);
		hand.get(3).getButton().setBounds(initialCardX + 3*(cardWidth+5), 15, cardWidth, cardHeight);
		hand.get(4).getButton().setBounds(initialCardX + 4*(cardWidth+5), 15, cardWidth, cardHeight);
		
		System.out.println("Printing card in your panel");
		System.out.println(hand.get(0).getButton().getLabel());
		
		hand.get(0).getButton().addActionListener(new CardPress(hand.get(0), middleCard, hand.get(1), hand.get(2), hand.get(3), hand.get(4)));
		hand.get(1).getButton().addActionListener(new CardPress(hand.get(1), middleCard, hand.get(0), hand.get(2), hand.get(3), hand.get(4)));
		hand.get(2).getButton().addActionListener(new CardPress(hand.get(2), middleCard, hand.get(1), hand.get(0), hand.get(3), hand.get(4)));
		hand.get(3).getButton().addActionListener(new CardPress(hand.get(3), middleCard, hand.get(1), hand.get(2), hand.get(0), hand.get(4)));
		hand.get(4).getButton().addActionListener(new CardPress(hand.get(4), middleCard, hand.get(1), hand.get(2), hand.get(3), hand.get(0)));
		 
		add(hand.get(1).getButton());
		add(hand.get(2).getButton());
		add(hand.get(3).getButton());
		add(hand.get(4).getButton());
		add(hand.get(0).getButton());
	}
}
