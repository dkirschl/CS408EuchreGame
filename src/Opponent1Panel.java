import java.awt.Button;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Opponent1Panel{
	int width, height, cardWidth, cardHeight;
	String name;
	JLabel middleCard;
	JPanel opp1Panel;
	Button dealer, trumpSuit;
	
	public Opponent1Panel(int width, int height, String name, JLabel middleCard)
	{
		//System.out.println("Created a new opponent 1 panel");
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;
		opp1Panel = new JPanel();

		initOpponent1Panel();
	}
	
	public void initOpponent1Panel()
	{		
		opp1Panel.setBackground(Color.cyan);
		int opp1XCoord = 0;
		int opp1YCoord = height/5;
		int opp1Width = width/4;
		int opp1Height = 3*(height/5);
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		dealer = new Button("Dealer");
		trumpSuit = new Button("trumpSuit");
		
		System.out.println("Opponent 1 Coordinates x: " + opp1XCoord + " y: " + opp1YCoord + " Dimensions width: " + opp1Width  + " height: " + opp1Height);
		opp1Panel.setBounds(opp1XCoord, opp1YCoord, opp1Width, opp1Height);
		JLabel opp1 = new JLabel(name);
		
		opp1Panel.setLayout(null);
		opp1.setVerticalAlignment(SwingConstants.CENTER);
		opp1.setBounds(0,opp1Height/2-20,80,40);
		
		opp1Panel.add(opp1);
		
		Button opp1Card1 = new Button("Opp1 Card1");
		Button opp1Card2 = new Button("Opp1 Card2");
		Button opp1Card3= new Button("Opp1 Card3");
		Button opp1Card4 = new Button("Opp1 Card4");
		Button opp1Card5 = new Button("Opp1 Card5");
		 
		int intialOpp1CardY = 0;
		 
		opp1Card1.setBounds(120, intialOpp1CardY, cardHeight, cardWidth);
		opp1Card2.setBounds(120, intialOpp1CardY+(cardWidth+5), cardHeight, cardWidth);
		opp1Card3.setBounds(120, intialOpp1CardY+2*(cardWidth+5), cardHeight, cardWidth);
		opp1Card4.setBounds(120, intialOpp1CardY+3*(cardWidth+5), cardHeight, cardWidth);
		opp1Card5.setBounds(120, intialOpp1CardY+4*(cardWidth+5), cardHeight, cardWidth);
		 
		opp1Panel.add(opp1Card1);
		opp1Panel.add(opp1Card2);
		opp1Panel.add(opp1Card3);
		opp1Panel.add(opp1Card4);
		opp1Panel.add(opp1Card5);
		
		// Add in the Buttons for dealer and trump suit
		dealer.setEnabled(false);
		dealer.setVisible(false);
		trumpSuit.setEnabled(false);
		trumpSuit.setVisible(false);
		
		dealer.setBounds(20, opp1Height/2-40/2-40, 40, 40);
		trumpSuit.setBounds(20, opp1Height/2+40/2, 40,40);
		
		opp1Panel.add(dealer);
		opp1Panel.add(trumpSuit);
	}
}
