import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Opponent1Panel{
	int width, height, cardWidth, cardHeight;
	String name;
	JLabel middleCard;
	JPanel opp1Panel;
	JLabel dealer;
	
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
		opp1Panel.setBackground(Color.decode("#006600"));
		int opp1XCoord = 0;
		int opp1YCoord = height/5;
		int opp1Width = width/4;
		int opp1Height = 3*(height/5);
		
		int cardWidth = 70;
		int cardHeight = 100;
        Image test;
        ImageIcon normalImage;
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/dealerChip.jpg"));
			System.out.println(getClass().getResource("/Images/dealerChip.jpg"));
			Image newImg = test.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        dealer = new JLabel(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		opp1Panel.setBounds(opp1XCoord, opp1YCoord, opp1Width, opp1Height);
		JLabel opp1 = new JLabel(name);
		
		opp1.setForeground(Color.white);
		opp1Panel.setLayout(null);
		opp1.setVerticalAlignment(SwingConstants.CENTER);
		opp1.setBounds(0,opp1Height/2-20,80,40);
		
		opp1Panel.add(opp1);
		
		JButton opp1Card1 = new JButton();
		JButton opp1Card2 = new JButton();
		JButton opp1Card3 = new JButton();
		JButton opp1Card4 = new JButton();
		JButton opp1Card5 = new JButton();
		
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/BackOfCard5Sideways.jpg"));
			Image newImg = test.getScaledInstance(cardHeight, cardWidth, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        opp1Card1.setIcon(normalImage);
	        opp1Card2.setIcon(normalImage);
	        opp1Card3.setIcon(normalImage);
	        opp1Card4.setIcon(normalImage);
	        opp1Card5.setIcon(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
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
		dealer.setEnabled(true);
		dealer.setVisible(false);
		
		dealer.setBounds(20, opp1Height/2-40/2-40, 40, 40);
		
		opp1Panel.add(dealer);
	}
}
