import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


public class MidPanel{
	int width, height, cardWidth, cardHeight;
	Card yourMiddleCard, teamMiddleCard, opp1MiddleCard, opp2MiddleCard, turnup, pickOrPassCard;
	Button spades, clubs, hearts, diamonds, passSuit;
	
	JPanel midPanel;

	public MidPanel(int width, int height, Card turnup)
	{
		this.width = width;
		this.height = height;
		yourMiddleCard = new Card();
		teamMiddleCard = new Card();
		opp1MiddleCard = new Card();
		opp2MiddleCard = new Card();
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
		 
		Button ymc = new Button("");
		Button o1c = new Button("Opp1 Card");
		Button o2c = new Button("Opp2 Card");
		Button tmc = new Button("Team Card");
		 
		ymc.setVisible(false);
		ymc.setBounds(midWidth/2-cardWidth/2, midHeight-cardHeight-10, cardWidth, cardHeight);
		ymc.setEnabled(false);
		 
		o1c.setVisible(false);
		o1c.setBounds(50, midHeight/2-cardWidth/2, cardHeight, cardWidth);
		o1c.setEnabled(false);
		 
		o2c.setVisible(false);
		o2c.setBounds(midWidth - 50-cardHeight, midHeight/2-cardWidth/2, cardHeight,cardWidth);
		o2c.setEnabled(false);
		 
		tmc.setVisible(false);
		tmc.setBounds(midWidth/2-cardWidth/2, 10, cardWidth, cardHeight);
		tmc.setEnabled(false);
		
		midPanel.add(tmc);
		midPanel.add(o1c);
		midPanel.add(o2c);
		midPanel.add(ymc);
		
		//System.out.println("About to set the buttons");
		
		yourMiddleCard.setButton(ymc);
		System.out.println("your middle card");
		System.out.println(yourMiddleCard.getButton().getLabel());
		opp1MiddleCard.setButton(o1c);
		opp2MiddleCard.setButton(o2c);
		teamMiddleCard.setButton(tmc);
		
		//******* Set up the pick or pass fields *******\\
        //Card pickPassCard = turnup;
		pickOrPassCard = turnup;
        Button pickPassButton = turnup.getButton();
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
        spades = new Button("Spades");
        hearts = new Button("Hearts");
        clubs = new Button("Clubs");
        diamonds = new Button("Diamonds");
        passSuit = new Button("Pass");
        
        int clubTopBorder = 115;
        int clubSideBorder = 110;
        
        spades.setVisible(false);
        spades.setBounds(clubSideBorder, midHeight/2-clubHeight/2, clubWidth, clubHeight);
        spades.setEnabled(false);
        spades.addActionListener(new ChooseSuit(spades, "spades"));
        
        clubs.setVisible(false);
        clubs.setBounds(midWidth-clubSideBorder-clubWidth, midHeight/2-clubHeight/2, clubWidth, clubHeight);
        clubs.setEnabled(false);
        clubs.addActionListener(new ChooseSuit(clubs, "clubs"));
        
        hearts.setVisible(false);
        hearts.setBounds(midWidth/2-clubWidth/2, clubTopBorder, clubWidth, clubHeight);
        hearts.setEnabled(false);
        hearts.addActionListener(new ChooseSuit(hearts, "hearts"));
        
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
	}

	public Card getPickOrPassCard() {
		return pickOrPassCard;
	}

	public void setPickOrPassCard(Card pickOrPassCard) {
		this.pickOrPassCard = pickOrPassCard;
	}

	public Card getYourMiddleCard() {
		return yourMiddleCard;
	}

	public void setYourMiddleCard(Card yourMiddleCard) {
		this.yourMiddleCard = yourMiddleCard;
	}

	public Card getTeamMiddleCard() {
		return teamMiddleCard;
	}

	public void setTeamMiddleCard(Card teamMiddleCard) {
		this.teamMiddleCard = teamMiddleCard;
	}

	public Card getOpp1MiddleCard() {
		return opp1MiddleCard;
	}

	public void setOpp1MiddleCard(Card opp1MiddleCard) {
		this.opp1MiddleCard = opp1MiddleCard;
	}

	public Card getOpp2MiddleCard() {
		return opp2MiddleCard;
	}

	public void setOpp2MiddleCard(Card opp2MiddleCard) {
		this.opp2MiddleCard = opp2MiddleCard;
	}
	
	
}
