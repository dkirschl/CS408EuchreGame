import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;


public class MidPanel{
	int width, height, cardWidth, cardHeight;
	Card yourMiddleCard, teamMiddleCard, opp1MiddleCard, opp2MiddleCard, turnup, pickOrPassCard;
	Board board;
	JPanel midPanel;
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
		System.out.println(this.board.toString());
	}

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
		ymc.setBounds(midWidth/2-30, midHeight-200, cardWidth, cardHeight);
		ymc.setEnabled(false);
		 
		o1c.setVisible(false);
		o1c.setBounds(50, midHeight/2-cardWidth, cardHeight, cardWidth);
		o1c.setEnabled(false);
		 
		o2c.setVisible(false);
		o2c.setBounds(midWidth - 142, midHeight/2-cardWidth, cardHeight,cardWidth);
		o2c.setEnabled(false);
		 
		tmc.setVisible(false);
		tmc.setBounds(midWidth/2-30, 50, cardWidth, cardHeight);
		tmc.setEnabled(false);
		
		midPanel.add(tmc);
		midPanel.add(o1c);
		midPanel.add(o2c);
		midPanel.add(ymc);
		
		System.out.println("About to set the buttons");
		
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
        pickCard.setBounds(pickCardX, pickCardY, cardHeight, cardWidth);
        pickCard.setEnabled(true);
        System.out.println(board.toString());
        pickCard.addActionListener(new Pick(pickOrPassCard, pickCard, passCard, board));
        
        passCard.setVisible(true);
        passCard.setBounds(passCardX, passCardY, cardHeight, cardWidth);
        passCard.setEnabled(true);
        passCard.addActionListener(new Pass(pickOrPassCard, pickCard, passCard, board));

        midPanel.add(pickOrPassCard.getButton());
        midPanel.add(pickCard);
        midPanel.add(passCard);
        midPanel.add(deck);
        
        //******* Setup the other suit buttons *******\\
        Button spades = new Button("Spades");
        Button hearts = new Button("Hearts");
        Button clubs = new Button("Clubs");
        Button diamonds = new Button("Diamonds");
        Button passSuit = new Button("Pass");
        
        int clubTopBorder = 75;
        int clubSideBorder = 110;
        
        spades.setVisible(false);
        spades.setBounds(clubSideBorder, midHeight/2-clubHeight, clubWidth, clubHeight);
        spades.setEnabled(false);
        spades.addActionListener(new ChooseSuit(spades, "spades"));
        
        clubs.setVisible(false);
        clubs.setBounds(midWidth-clubSideBorder-clubWidth, midHeight/2-clubHeight, clubWidth, clubHeight);
        clubs.setEnabled(false);
        clubs.addActionListener(new ChooseSuit(spades, "spades"));
        
        hearts.setVisible(false);
        hearts.setBounds(midWidth/2-clubWidth/2, clubTopBorder, clubWidth, clubHeight);
        hearts.setEnabled(false);
        hearts.addActionListener(new ChooseSuit(spades, "spades"));
        
        diamonds.setVisible(false);
        diamonds.setBounds(midWidth/2-clubWidth/2, midHeight-clubHeight-clubTopBorder-40, clubWidth, clubHeight);
        diamonds.setEnabled(false);
        diamonds.addActionListener(new ChooseSuit(spades, "spades"));
        
        passSuit.setVisible(false);
        passSuit.setBounds(midWidth/2-cardHeight/2, 250, cardHeight, cardWidth);
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
