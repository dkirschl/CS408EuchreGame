import java.awt.Button;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


public class TeamPanel extends JPanel{
	int width, height, cardWidth, cardHeight;
	String name;
	Card middleCard;
	
	public TeamPanel(int width, int height, String name, Card middleCard)
	{
		//System.out.println("Created a new team panel");
		this.width = width;
		this.height = height;
		this.name = name;
		this.middleCard = middleCard;
		
		initOpponent1Panel();
	}
	
	public void initOpponent1Panel()
	{
		setBackground(Color.yellow);
		int teamXCoord = 0;
		int teamYCoord = 0;
		int teamWidth = width;
		int teamHeight = height/5;
		
		int cardWidth = 70;
		int cardHeight = 100;
		
		//System.out.println("Team Coordinates x: " + teamXCoord + " y: " + teamYCoord + " Dimensions width: " + teamWidth  + " height: " + teamHeight);
		setBounds(teamXCoord, teamYCoord, teamWidth, teamHeight);
		JLabel team = new JLabel(name);
		
		 setLayout(null);
		 team.setHorizontalAlignment(SwingConstants.CENTER);
		 team.setBounds(teamWidth/2 - 60, -10, 120, 40);
		
		add(team);
		
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

		add(teamCard1);
		add(teamCard2);
		add(teamCard3);
		add(teamCard4);
		add(teamCard5);
		
	}
}
