import java.awt.Button;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Opponent1Panel extends JPanel{
	int width, height, cardWidth, cardHeight;
	String name;
	Card middleCard;
	
	public Opponent1Panel(int width, int height, String name, Card middleCard)
	{
		System.out.println("Created a new opponent 1 panel");
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;

		initOpponent1Panel();
	}
	
	public void initOpponent1Panel()
	{
		setBackground(Color.yellow);
		
		int opp1XCoord = 0;
		int opp1YCoord = height/5;
		int opp1Width = width/4;
		int opp1Height = 3*(height/5);
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		//System.out.println("Opponent 1 Coordinates x: " + opp1XCoord + " y: " + opp1YCoord + " Dimensions width: " + opp1Width  + " height: " + opp1Height);
		setBounds(opp1XCoord, opp1YCoord, opp1Width, opp1Height);
		JLabel opp1 = new JLabel(name);
		
		setLayout(null);
		opp1.setVerticalAlignment(SwingConstants.CENTER);
		opp1.setBounds(0,opp1Height/2-40,80,40);
		
		add(opp1);
		
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
		 
		add(opp1Card1);
		add(opp1Card2);
		add(opp1Card3);
		add(opp1Card4);
		add(opp1Card5);
	}
}
