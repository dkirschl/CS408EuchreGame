import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Board{
	private int boardWidth;
	private int boardHeight;
	
	private YourPanel yourPanel;
	private MidPanel midPanel;
	private Opponent1Panel opp1Panel;
	private Opponent2Panel opp2Panel;
	private TeamPanel teamPanel;
	
	JFrame board;
	JPanel gameBoard;

	public Board()
	{
		board = new JFrame();
		boardWidth = 900;
		boardHeight = 700;
		initBoard();
	}

	public void initBoard()
	{		
		gameBoard = new JPanel(new FlowLayout());
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setMinimumSize(new Dimension(boardWidth,boardHeight));
		board.setResizable(false);
		
		GameCreateScreen s = new GameCreateScreen(this, boardWidth, boardHeight);
		s.gameCreateScreen.setVisible(false);
		
		HowToPlay htp = new HowToPlay(this, boardWidth, boardHeight);
		htp.howToPlayScreen.setVisible(false);
		
		System.out.println("HTP width: " + htp.howToPlayScreen.getWidth() + " height: " +  htp.howToPlayScreen.getHeight());
		
		board.setJMenuBar(new Menu(s.gameCreateScreen, htp.howToPlayScreen));
		board.add(s.gameCreateScreen);
		board.add(htp.howToPlayScreen);
		
		//gameBoard.setBackground(Color.blue);

		board.add(gameBoard);
		
		board.pack();
		System.out.println(gameBoard.getWidth());
		System.out.println(gameBoard.getHeight());
		
		GameInfo.board = this;
		GameInfo.firstGame = true;
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
	public YourPanel getYourPanel() {
		return yourPanel;
	}

	public void setYourPanel(YourPanel yourPanel) {
		this.yourPanel = yourPanel;
	}

	public MidPanel getMidPanel() {
		return midPanel;
	}

	public void setMidPanel(MidPanel midPanel) {
		this.midPanel = midPanel;
	}

	public Opponent1Panel getOpp1Panel() {
		return opp1Panel;
	}

	public void setOpp1Panel(Opponent1Panel opp1Panel) {
		this.opp1Panel = opp1Panel;
	}

	public Opponent2Panel getOpp2Panel() {
		return opp2Panel;
	}

	public void setOpp2Panel(Opponent2Panel opp2Panel) {
		this.opp2Panel = opp2Panel;
	}

	public TeamPanel getTeamPanel() {
		return teamPanel;
	}
	public JFrame getBoard() {
		return board;
	}

	public void setBoard(JFrame board) {
		this.board = board;
	}

	public void setTeamPanel(TeamPanel teamPanel) {
		this.teamPanel = teamPanel;
	}
}
