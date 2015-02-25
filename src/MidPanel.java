import java.awt.Button;
import java.awt.Color;

import javax.swing.JPanel;


public class MidPanel extends JPanel {
	int width, height, cardWidth, cardHeight;
	Card yourMiddleCard, teamMiddleCard, opp1MiddleCard, opp2MiddleCard, turnup;
	
	public MidPanel(int width, int height, Card turnup)
	{
		this.width = width;
		this.height = height;
		yourMiddleCard = new Card();
		teamMiddleCard = new Card();
		opp1MiddleCard = new Card();
		opp2MiddleCard = new Card();
		this.turnup = turnup;
		
		initMidPanel();
	}
	
	public void initMidPanel()
	{
		setBackground(Color.gray);
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		int midXCoord = width/4;
		int midYCoord = height/5;
		int midWidth = width/2;
		int midHeight = 3*(height/5);
		
		setBounds(midXCoord, midYCoord, midWidth, midHeight);
		
		setLayout(null);
		 
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
		
		add(tmc);
		add(o1c);
		add(o2c);
		add(ymc);
		
		System.out.println("About to set the buttons");
		
		yourMiddleCard.setButton(ymc);
		System.out.println("your middle card");
		System.out.println(yourMiddleCard.getButton().getLabel());
		opp1MiddleCard.setButton(o1c);
		opp2MiddleCard.setButton(o2c);
		teamMiddleCard.setButton(tmc);
		
		//******* Set up the pick or pass fields *******\\
        Card pickPassCard = turnup;
        Button pickPassButton = turnup.getButton();
        Button pickCard = new Button("Pick");
        Button passCard = new Button("Pass");
        Button deck = new Button("Deck");
        
        int pickPassCardX = midWidth/2 - cardWidth - 10;
        int pickPassCardY = 125;
        int deckX = midWidth/2 + 10;
        int deckY = 125;
        int pickCardX = midWidth/2-cardHeight-10;
        int pickCardY = 225;
        int passCardX = midWidth/2 + 10;
        int passCardY = 225;
        
        pickPassButton.setVisible(true);
        pickPassButton.setBounds(pickPassCardX, pickPassCardY, cardWidth, cardHeight);
        pickPassButton.setEnabled(true);
        
        pickCard.setVisible(true);
        pickCard.setBounds(pickCardX, pickCardY, cardHeight, cardWidth);
        pickCard.setEnabled(true);
        pickCard.addActionListener(new Pick(pickPassCard, pickCard, passCard));
        
        passCard.setVisible(true);
        passCard.setBounds(passCardX, passCardY, cardHeight, cardWidth);
        passCard.setEnabled(true);
        passCard.addActionListener(new Pass(pickPassCard, pickCard, passCard));
        
        deck.setVisible(true);
        deck.setBounds(deckX, deckY, cardWidth, cardHeight);
        deck.setEnabled(false);
        
        add(pickPassCard.getButton());
        add(pickCard);
        add(passCard);
        add(deck);
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
