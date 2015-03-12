import java.awt.Button;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Opponent2Panel{
	int width, height, cardWidth, cardHeight;
	String name;
	JLabel middleCard;
	JPanel opp2Panel;
	Button dealer, trumpSuit;
	
	public Opponent2Panel(int width, int height, String name, JLabel middleCard)
	{
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;
		opp2Panel = new JPanel();

		initOpponent2Panel();
	}
	
	public void initOpponent2Panel()
	{		
		opp2Panel.setBackground(Color.red);
		int opp2XCoord = width - width/4;
		int opp2YCoord = height/5;
		int opp2Width = width/4;
		int opp2Height = 3*(height/5);
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		dealer = new Button("Dealer");
		trumpSuit = new Button("trumpSuit");
		
		System.out.println("Opponent 2 Coordinates x: " + opp2XCoord + " y: " + opp2YCoord + " Dimensions width: " + opp2Width  + " height: " + opp2Height);
		opp2Panel.setBounds(opp2XCoord, opp2YCoord, opp2Width, opp2Height);
		JLabel opp2 = new JLabel(name);
		
		opp2Panel.setLayout(null);
		opp2.setHorizontalAlignment(SwingConstants.RIGHT);
		opp2.setBounds(opp2Width-110, opp2Height/2-40/2, 110,40);
		
		opp2Panel.add(opp2);
		
		Button opp2Card1 = new Button("opp2 Card1");
		Button opp2Card2 = new Button("opp2 Card2");
		Button opp2Card3= new Button("opp2 Card3");
		Button opp2Card4 = new Button("opp2 Card4");
		Button opp2Card5 = new Button("opp2 Card5");
		 
		int intialOpp2CardY = 0;
		 
		opp2Card1.setBounds(10, intialOpp2CardY, cardHeight, cardWidth);
		opp2Card2.setBounds(10, intialOpp2CardY+(cardWidth+5), cardHeight, cardWidth);
		opp2Card3.setBounds(10, intialOpp2CardY+2*(cardWidth+5), cardHeight, cardWidth);
		opp2Card4.setBounds(10, intialOpp2CardY+3*(cardWidth+5), cardHeight, cardWidth);
		opp2Card5.setBounds(10, intialOpp2CardY+4*(cardWidth+5), cardHeight, cardWidth);
		 
		opp2Panel.add(opp2Card1);
		opp2Panel.add(opp2Card2);
		opp2Panel.add(opp2Card3);
		opp2Panel.add(opp2Card4);
		opp2Panel.add(opp2Card5);
		// Add in the Buttons for dealer and trump suit
		dealer.setEnabled(false);
		dealer.setVisible(false);
		trumpSuit.setEnabled(false);
		trumpSuit.setVisible(false);
		
		dealer.setBounds(opp2Width-20-40, opp2Height/2-40/2-40, 40, 40);
		trumpSuit.setBounds(opp2Width-20-40, opp2Height/2+40/2, 40,40);
		
		opp2Panel.add(dealer);
		opp2Panel.add(trumpSuit);
	}
}
