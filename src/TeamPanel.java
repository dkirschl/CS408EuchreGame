import java.awt.Button;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class TeamPanel{
	int width, height, cardWidth, cardHeight;
	String name;
	Card middleCard;
	JPanel teamPanel;
	JLabel totalScore, trickScore;
	Button dealer, trumpSuit;
	
	public TeamPanel(int width, int height, String name, Card middleCard)
	{
		//System.out.println("Created a new team panel");
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;
		teamPanel = new JPanel();
		
		initTeamPanel();
	}
	
	public void initTeamPanel()
	{
		teamPanel.setBackground(Color.yellow);
		int teamXCoord = 0;
		int teamYCoord = 0;
		int teamWidth = width;
		int teamHeight = height/5;
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		dealer = new Button("Dealer");
		trumpSuit = new Button("trumpSuit");
		
		//System.out.println("Team Coordinates x: " + teamXCoord + " y: " + teamYCoord + " Dimensions width: " + teamWidth  + " height: " + teamHeight);
		teamPanel.setBounds(teamXCoord, teamYCoord, teamWidth, teamHeight);
		JLabel team = new JLabel(name);
		
		teamPanel.setLayout(null);
		team.setHorizontalAlignment(SwingConstants.CENTER);
		team.setBounds(teamWidth/2 - 60, -10, 120, 40);
		
		teamPanel.add(team);
		
		Button teamCard1 = new Button("team Card1");
		Button teamCard2 = new Button("team Card2");
		Button teamCard3= new Button("team Card3");
		Button teamCard4 = new Button("team Card4");
		Button teamCard5 = new Button("team Card5");
		 
		int initialCardX = width/2-cardWidth/2-cardWidth-cardWidth-10;
		int initialTeamY = 30;
		 
		teamCard1.setBounds(initialCardX, initialTeamY, cardWidth, cardHeight);
		teamCard2.setBounds(initialCardX + (cardWidth+5), initialTeamY, cardWidth, cardHeight);
		teamCard3.setBounds(initialCardX + 2*(cardWidth+5), initialTeamY, cardWidth, cardHeight);
		teamCard4.setBounds(initialCardX + 3*(cardWidth+5), initialTeamY, cardWidth, cardHeight);
		teamCard5.setBounds(initialCardX + 4*(cardWidth+5), initialTeamY, cardWidth, cardHeight);

		teamPanel.add(teamCard1);
		teamPanel.add(teamCard2);
		teamPanel.add(teamCard3);
		teamPanel.add(teamCard4);
		teamPanel.add(teamCard5);
		
		//******* Add in the scoring for the panels
		
		JLabel totalScore = new JLabel();
		totalScore.setBounds(width-140, 0, 140, 20);
		totalScore.setText("Total: You-" + GameInfo.teamOneScore + " Opponent-" + GameInfo.teamTwoScore);
		totalScore.setVisible(true);
		
		JLabel trickScore = new JLabel();
		trickScore.setBounds(width-140, 25, 140, 20);
		trickScore.setText("Trick: You-" + GameInfo.teamOneTricks + " Opponent-" + GameInfo.teamTwoTricks);
		trickScore.setVisible(true);
		
		teamPanel.add(totalScore);
		
		// Add in the Buttons for dealer and trump suit
		dealer.setEnabled(false);
		dealer.setVisible(false);
		trumpSuit.setEnabled(false);
		trumpSuit.setVisible(false);
		
		dealer.setBounds(initialCardX-50, initialTeamY, 40, 40);
		trumpSuit.setBounds(initialCardX+4*(cardWidth+5)+cardWidth+10, initialTeamY, 40,40);
		
		teamPanel.add(dealer);
		teamPanel.add(trumpSuit);
	}
	public void updateTotalScore()
	{
		totalScore.setText("Total: You-" + GameInfo.teamOneScore + " Opponent-" + GameInfo.teamTwoScore);
	}
	public void updateTrickScore()
	{
		trickScore.setText("Total: You-" + GameInfo.teamOneTricks + " Opponent-" + GameInfo.teamTwoTricks);
	}
}
