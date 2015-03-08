import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Board{
	private int boardWidth;
	private int boardHeight;
	
	private YourPanel yourPanel;
	private MidPanel midPanel;
	private JPanel opp1Panel;
	private JPanel opp2Panel;
	private JPanel teamPanel;
	
	JFrame board;
	JPanel gameBoard;

	public Board()
	{
		board = new JFrame();
		boardWidth = 900;
		boardHeight = 700;
		initBoard();
	}
	
	public JFrame getBoard() {
		return board;
	}

	public void setBoard(JFrame board) {
		this.board = board;
	}

	public JPanel getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(JPanel gameBoard) {
		this.gameBoard = gameBoard;
	}

	public void initBoard()
	{		
		board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		board.setMinimumSize(new Dimension(boardWidth + 20,boardHeight + 10));
		//setResizable(false);
		
		GameCreateScreen s = new GameCreateScreen(this);
		s.gameCreateScreen.setVisible(false);
		
		board.setJMenuBar(new Menu(s.gameCreateScreen));
		board.add(s.gameCreateScreen);
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
