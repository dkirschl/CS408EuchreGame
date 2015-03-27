import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class Opponent2Panel{
	int width, height, cardWidth, cardHeight;
	String name;
	JLabel middleCard;
	JPanel opp2Panel;
	JLabel dealer;
	
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
		opp2Panel.setBackground(Color.decode("#006600"));
		int opp2XCoord = width - width/4;
		int opp2YCoord = height/5;
		int opp2Width = width/4;
		int opp2Height = 3*(height/5);
		
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

		opp2Panel.setBounds(opp2XCoord, opp2YCoord, opp2Width, opp2Height);
		JLabel opp2 = new JLabel(name);
		
		opp2.setForeground(Color.white);
		opp2Panel.setLayout(null);
		opp2.setHorizontalAlignment(SwingConstants.RIGHT);
		opp2.setBounds(opp2Width-110, opp2Height/2-40/2, 110,40);
		
		opp2Panel.add(opp2);
		
		JButton opp2Card1 = new JButton();
		JButton opp2Card2 = new JButton();
		JButton opp2Card3 = new JButton();
		JButton opp2Card4 = new JButton();
		JButton opp2Card5 = new JButton();
		
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/BackOfCard5Sideways.jpg"));
			Image newImg = test.getScaledInstance(cardHeight, cardWidth, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        opp2Card1.setIcon(normalImage);
	        opp2Card2.setIcon(normalImage);
	        opp2Card3.setIcon(normalImage);
	        opp2Card4.setIcon(normalImage);
	        opp2Card5.setIcon(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
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
		
		// Add in the Labels for dealer and trump suit
		dealer.setEnabled(true);
		dealer.setVisible(false);
		
		dealer.setBounds(opp2Width-20-40, opp2Height/2-40/2-40, 40, 40);
		
		opp2Panel.add(dealer);
	}
}
