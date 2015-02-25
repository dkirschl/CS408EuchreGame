import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Board extends JFrame{
	private int boardWidth;
	private int boardHeight;
	
	private JPanel yourPanel;
	private JPanel midPanel;
	private JPanel opp1Panel;
	private JPanel opp2Panel;
	private JPanel teamPanel;

	public Board()
	{
		boardWidth = 900;
		boardHeight = 700;
		initBoard();
	}
	
	public void initBoard()
	{		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(boardWidth + 20,boardHeight + 10));
		//setResizable(false);
		
		GameCreateScreen s = new GameCreateScreen();
		s.setVisible(false);
		
		setJMenuBar(new Menu(s));
		add(s);
	}

	public int getBoardWidth() {
		return boardWidth;
	}

	public void setBoardWidth(int boardWidth) {
		this.boardWidth = boardWidth;
	}

	public int getBoardHeight() {
		return boardHeight;
	}

	public void setBoardHeight(int boardHeight) {
		this.boardHeight = boardHeight;
	}
	public JPanel getYourPanel() {
		return yourPanel;
	}

	public void setYourPanel(JPanel yourPanel) {
		this.yourPanel = yourPanel;
	}

	public JPanel getMidPanel() {
		return midPanel;
	}

	public void setMidPanel(JPanel midPanel) {
		this.midPanel = midPanel;
	}

	public JPanel getOpp1Panel() {
		return opp1Panel;
	}

	public void setOpp1Panel(JPanel opp1Panel) {
		this.opp1Panel = opp1Panel;
	}

	public JPanel getOpp2Panel() {
		return opp2Panel;
	}

	public void setOpp2Panel(JPanel opp2Panel) {
		this.opp2Panel = opp2Panel;
	}

	public JPanel getTeamPanel() {
		return teamPanel;
	}

	public void setTeamPanel(JPanel teamPanel) {
		this.teamPanel = teamPanel;
	}
}
