import java.awt.Button;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MidPanel{
	int width, height, cardWidth, cardHeight;
	JLabel yourMiddleCard, teamMiddleCard, opp1MiddleCard, opp2MiddleCard;
	Card turnup, pickOrPassCard;
	JButton spades, clubs, hearts, diamonds, passSuit;
	JLabel trumpSuitImage;
	
	JPanel midPanel;

	public MidPanel(int width, int height, Card turnup)
	{
		this.width = width;
		this.height = height;
		yourMiddleCard = new JLabel();
		teamMiddleCard = new JLabel();
		opp1MiddleCard = new JLabel();
		opp2MiddleCard = new JLabel();
		this.turnup = turnup;
		
		midPanel = new JPanel();
		//initMidPanel();
	}
	
	public void initMidPanel()
	{
		midPanel.setBackground(Color.green);
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		int clubWidth = 40;
		int clubHeight = 40;
		
		int midXCoord = width/4;
		int midYCoord = height/5;
		int midWidth = width/2;
		int midHeight = 3*(height/5);
		
		midPanel.setBounds(midXCoord, midYCoord, midWidth, midHeight);
		
		midPanel.setLayout(null);
		 
		yourMiddleCard.setVisible(false);
		yourMiddleCard.setBounds(midWidth/2-cardWidth/2, midHeight-cardHeight-10, cardWidth, cardHeight);
		//ymc.setEnabled(false);
		 
		opp1MiddleCard.setVisible(false);
		opp1MiddleCard.setBounds(50, midHeight/2-cardHeight/2, cardWidth, cardHeight);
		//opp1MiddleCard.setEnabled(false);
		 
		opp2MiddleCard.setVisible(false);
		opp2MiddleCard.setBounds(midWidth - 50-cardWidth, midHeight/2-cardHeight/2, cardWidth,cardHeight);
		//opp2MiddleCard.setEnabled(false);
		 
		teamMiddleCard.setVisible(false);
		teamMiddleCard.setBounds(midWidth/2-cardWidth/2, 10, cardWidth, cardHeight);
		//tmc.setEnabled(false);
		
		midPanel.add(teamMiddleCard);
		midPanel.add(opp1MiddleCard);
		midPanel.add(opp2MiddleCard);
		midPanel.add(yourMiddleCard);
		
		//System.out.println("About to set the buttons");
		
		//yourMiddleCard.setIcon(ymc.getIcon());
		//System.out.println("your middle card");
		//System.out.println(yourMiddleCard.getButton().getLabel());
		//opp1MiddleCard.setIcon(o1c.getIcon());
		//opp2MiddleCard.setButton(o2c);
		//teamMiddleCard.setButton(tmc);
		
		//******* Set up the pick or pass fields *******\\
        //Card pickPassCard = turnup;
		pickOrPassCard = turnup;
        JButton pickPassButton = turnup.getButton();
        System.out.println(turnup.getButton().getIcon().toString());
        Button pickCard = new Button("Pick");
        Button passCard = new Button("Pass");
        Button deck = new Button("Deck");
        
        int pickPassCardX = midWidth/2 - cardWidth/2;
        int pickPassCardY = midWidth/2-cardHeight+15;
        int pickCardX = midWidth/2-cardHeight-10;
        int pickCardY = 250;
        int passCardX = midWidth/2 + 10;
        int passCardY = 250;
        
        pickPassButton.setVisible(true);
        pickPassButton.setBounds(pickPassCardX, pickPassCardY, cardWidth, cardHeight);
        pickPassButton.setEnabled(true);
        
        pickCard.setVisible(true);
        pickCard.setBounds(pickCardX, pickCardY, cardHeight, 30);
        pickCard.setEnabled(true);
        //System.out.println(board.toString());
        pickCard.addActionListener(new Pick(pickOrPassCard, pickCard, passCard, GameInfo.board));
        
        passCard.setVisible(true);
        passCard.setBounds(passCardX, passCardY, cardHeight, 30);
        passCard.setEnabled(true);
        passCard.addActionListener(new Pass(pickOrPassCard, pickCard, passCard, GameInfo.board));

        midPanel.add(pickOrPassCard.getButton());
        midPanel.add(pickCard);
        midPanel.add(passCard);
        midPanel.add(deck);
        
        //******* Setup the other suit buttons *******\\

        spades = new JButton();
        hearts = new JButton();
        clubs = new JButton();
        diamonds = new JButton();
        passSuit = new JButton("Pass");
        
        int clubTopBorder = 115;
        int clubSideBorder = 110;
        
        Image test;
        ImageIcon normalImage;
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/spadesImage.png"));
			Image newImg = test.getScaledInstance(clubWidth, clubHeight, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        spades.setIcon(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        spades.setVisible(false);
        spades.setBounds(clubSideBorder, midHeight/2-clubHeight/2, clubWidth, clubHeight);
        spades.setEnabled(false);
        spades.addActionListener(new ChooseSuit(spades, "spades"));
        
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/clubsImage.png"));
			Image newImg = test.getScaledInstance(clubWidth, clubHeight, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        clubs.setIcon(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        clubs.setVisible(false);
        clubs.setBounds(midWidth-clubSideBorder-clubWidth, midHeight/2-clubHeight/2, clubWidth, clubHeight);
        clubs.setEnabled(false);
        clubs.addActionListener(new ChooseSuit(clubs, "clubs"));
        
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/heartsImage.png"));
			Image newImg = test.getScaledInstance(clubWidth, clubHeight, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        hearts.setIcon(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        hearts.setVisible(false);
        hearts.setBounds(midWidth/2-clubWidth/2, clubTopBorder, clubWidth, clubHeight);
        hearts.setEnabled(false);
        hearts.addActionListener(new ChooseSuit(hearts, "hearts"));
        
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/diamondsImage.png"));
			Image newImg = test.getScaledInstance(clubWidth, clubHeight, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        diamonds.setIcon(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        diamonds.setVisible(false);
        diamonds.setBounds(midWidth/2-clubWidth/2, midHeight-clubHeight-clubTopBorder, clubWidth, clubHeight);
        diamonds.setEnabled(false);
        diamonds.addActionListener(new ChooseSuit(diamonds, "diamonds"));
        
        passSuit.setVisible(false);
        passSuit.setBounds(midWidth/2-cardHeight/2, midHeight/2-30/2, cardHeight, 30);
        passSuit.setEnabled(false);
        passSuit.addActionListener(new ChooseSuit(passSuit, "pass"));
        
        midPanel.add(spades);
        midPanel.add(clubs);
        midPanel.add(hearts);
        midPanel.add(diamonds);
        midPanel.add(passSuit);
        
        trumpSuitImage = new JLabel();
        trumpSuitImage.setVisible(false);
        trumpSuitImage.setBounds(0,0,clubWidth, clubHeight);
        trumpSuitImage.setEnabled(true);
        
        midPanel.add(trumpSuitImage);
	}

	public Card getPickOrPassCard() {
		return pickOrPassCard;
	}

	public void setPickOrPassCard(Card pickOrPassCard) {
		this.pickOrPassCard = pickOrPassCard;
	}

	public JLabel getYourMiddleCard() {
		return yourMiddleCard;
	}

	/*public void setYourMiddleCard(Card yourMiddleCard) {
		this.yourMiddleCard = yourMiddleCard;
	}*/

	public JLabel getTeamMiddleCard() {
		return teamMiddleCard;
	}

	/*public void setTeamMiddleCard(Card teamMiddleCard) {
		this.teamMiddleCard = teamMiddleCard;
	}*/

	public JLabel getOpp1MiddleCard() {
		return opp1MiddleCard;
	}

	/*public void setOpp1MiddleCard(Card opp1MiddleCard) {
		this.opp1MiddleCard = opp1MiddleCard;
	}*/

	public JLabel getOpp2MiddleCard() {
		return opp2MiddleCard;
	}

	/*public void setOpp2MiddleCard(Card opp2MiddleCard) {
		this.opp2MiddleCard = opp2MiddleCard;
	}*/
	
	
}
