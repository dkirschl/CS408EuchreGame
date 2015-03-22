import java.awt.Button;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class TeamPanel{
	int width, height, cardWidth, cardHeight;
	String name;
	JLabel middleCard;
	JPanel teamPanel;
	JLabel totalScore, trickScore;
	JLabel dealer;
	
	public TeamPanel(int width, int height, String name, JLabel middleCard)
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
	
		//trumpSuit = new Button("trumpSuit");
		
		//System.out.println("Team Coordinates x: " + teamXCoord + " y: " + teamYCoord + " Dimensions width: " + teamWidth  + " height: " + teamHeight);
		teamPanel.setBounds(teamXCoord, teamYCoord, teamWidth, teamHeight);
		JLabel team = new JLabel(name);
		
		teamPanel.setLayout(null);
		team.setHorizontalAlignment(SwingConstants.CENTER);
		team.setBounds(teamWidth/2 - 60, -10, 120, 40);
		
		teamPanel.add(team);
		
		JButton teamCard1 = new JButton("team Card1");
		JButton teamCard2 = new JButton("team Card2");
		JButton teamCard3 = new JButton("team Card3");
		JButton teamCard4 = new JButton("team Card4");
		JButton teamCard5 = new JButton("team Card5");
		
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/BackOfCard5.jpg"));
			Image newImg = test.getScaledInstance(cardWidth, cardHeight, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        teamCard1.setIcon(normalImage);
	        teamCard2.setIcon(normalImage);
	        teamCard3.setIcon(normalImage);
	        teamCard4.setIcon(normalImage);
	        teamCard5.setIcon(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
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
		
		totalScore = new JLabel();
		totalScore.setBounds(width-140, 0, 140, 20);
		totalScore.setText("Total: You-" + GameInfo.teamOneScore + " Opponent-" + GameInfo.teamTwoScore);
		totalScore.setVisible(true);
		
		trickScore = new JLabel();
		trickScore.setBounds(width-140, 25, 140, 20);
		trickScore.setText("Trick: You-" + GameInfo.teamOneTricks + " Opponent-" + GameInfo.teamTwoTricks);
		trickScore.setVisible(true);
		
		teamPanel.add(totalScore);
		teamPanel.add(trickScore);
		
		// Add in the Buttons for dealer and trump suit
		dealer.setEnabled(true);
		dealer.setVisible(false);

		
		dealer.setBounds(initialCardX-50, initialTeamY, 40, 40);
		
		teamPanel.add(dealer);
	}
	public void updateTotalScore()
	{
		totalScore.setText("Total: You-" + GameInfo.teamOneScore + " Opponent-" + GameInfo.teamTwoScore);
	}
	public void updateTrickScore()
	{
		System.out.println("Team one tricks: " + GameInfo.teamOneTricks);
		System.out.println("Team two tricks: " + GameInfo.teamTwoTricks);
		System.out.println(trickScore.getText());
		trickScore.setText("Total: You-" + GameInfo.teamOneTricks + " Opponent-" + GameInfo.teamTwoTricks);
		teamPanel.repaint();
	}
}
