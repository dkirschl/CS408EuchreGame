import java.awt.Checkbox;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;


public class GameCreateScreen{
	JPanel gameCreateScreen;
	
	public GameCreateScreen(Board board)
	{
		gameCreateScreen = new JPanel();
		initScreen(board);
	}
	public void initScreen(final Board board)
	{
		
		JButton createGameButton;
		JLabel opp1NameLabel;
		JLabel opp1Difficulty;
     	final JTextField opp1NameText;
	    JRadioButton opp1Easy;
	    JRadioButton opp1Medium;
	    JRadioButton opp1Hard;
	    final ButtonGroup opp1RadioButtons;
	    JLabel opp2NameLabel;
	    JLabel opp2Difficulty;
	    final JTextField opp2NameText;
	    JRadioButton opp2Easy;
	    JRadioButton opp2Medium;
	    JRadioButton opp2Hard;
	    final ButtonGroup opp2RadioButtons;
	    JLabel opp3NameLabel;
	    JLabel opp3Difficulty;
	    final JTextField opp3NameText;
	    JRadioButton opp3Easy;
	    JRadioButton opp3Medium;
	    JRadioButton opp3Hard;
	    final ButtonGroup opp3RadioButtons;
	    final Checkbox stickDealer;

	    
	    JFrame gameBoard = board.board;
	  
	  int opp1Height = 3;
	  int opp2Height = 35;
	  int opp3Height = 67;
	  int checkBoxHeight = 99;
	  
	  int windowHeight = 225;
	  int windowWidth = 556;
	  
	  //****** Create the initial frame ******//
	  //newGamePanel = new JFrame("Creating a new game!");
	  
	  gameCreateScreen.setMaximumSize(new Dimension(windowWidth, windowHeight));
	  gameCreateScreen.setSize(new Dimension(windowWidth, windowHeight));
	  gameCreateScreen.setLayout(null);
	  
	  
	  //******* Opponent 1 Settings ********//
	  opp1NameLabel = new JLabel("Opponent #1 Name: ");
	  opp1NameLabel.setBounds(3,opp1Height,150,20);
	  opp1NameText = new JTextField("Billy Bob");
	  opp1NameText.setBounds(125,opp1Height,150,20);
	  opp1Difficulty = new JLabel("Difficulty: ");
	  opp1Difficulty.setBounds(290,opp1Height,100,20);
	  opp1Easy = new JRadioButton("Easy");
	  opp1Easy.setSelected(true);
	  opp1Easy.setBounds(360,opp1Height,60,20);
	  opp1Medium = new JRadioButton("Medium");
	  opp1Medium.setSelected(false);
	  opp1Medium.setBounds(417,opp1Height,75,20);
	  opp1Hard = new JRadioButton("Hard");
	  opp1Hard.setSelected(false);
	  opp1Hard.setBounds(493,opp1Height,75,20);
	  opp1RadioButtons = new ButtonGroup();
	  opp1RadioButtons.add(opp1Easy);
	  opp1RadioButtons.add(opp1Medium);
	  opp1RadioButtons.add(opp1Hard);
	   
	  gameCreateScreen.add(opp1NameLabel);
	  gameCreateScreen.add(opp1NameText);
	  gameCreateScreen.add(opp1Difficulty);
	  gameCreateScreen.add(opp1Easy);
	  gameCreateScreen.add(opp1Medium);
	  gameCreateScreen.add(opp1Hard);
	  
	  //******* Opponent 2 Settings ********//
	  opp2NameLabel = new JLabel("Opponent #2 Name: ");
	  opp2NameLabel.setBounds(3,opp2Height,150,20);
	  opp2NameText = new JTextField("Billy the Kid");
	  opp2NameText.setBounds(125,opp2Height,150,20);
	  opp2Difficulty = new JLabel("Difficulty: ");
	  opp2Difficulty.setBounds(290,opp2Height,100,20);
	  opp2Easy = new JRadioButton("Easy");
	  opp2Easy.setSelected(true);
	  opp2Easy.setBounds(360,opp2Height,60,20);
	  opp2Medium = new JRadioButton("Medium");
	  opp2Medium.setSelected(false);
	  opp2Medium.setBounds(417,opp2Height,75,20);
	  opp2Hard = new JRadioButton("Hard");
	  opp2Hard.setSelected(false);
	  opp2Hard.setBounds(493,opp2Height,75,20);
	  opp2RadioButtons = new ButtonGroup();
	  opp2RadioButtons.add(opp2Easy);
	  opp2RadioButtons.add(opp2Medium);
	  opp2RadioButtons.add(opp2Hard);
	   
	  gameCreateScreen.add(opp2NameLabel);
	  gameCreateScreen.add(opp2NameText);
	  gameCreateScreen.add(opp2Difficulty);
	  gameCreateScreen.add(opp2Easy);
	  gameCreateScreen.add(opp2Medium);
	  gameCreateScreen.add(opp2Hard);
	  
	  //******* Team-mate Settings ********//
	  opp3NameLabel = new JLabel("Teammate Name: ");
	  opp3NameLabel.setBounds(3,opp3Height,150,20);
	  opp3NameText = new JTextField("The Doctor");
	  opp3NameText.setBounds(125,opp3Height,150,20);
	  opp3Difficulty = new JLabel("Difficulty: ");
	  opp3Difficulty.setBounds(290,opp3Height,100,20);
	  opp3Easy = new JRadioButton("Easy");
	  opp3Easy.setSelected(true);
	  opp3Easy.setBounds(360,opp3Height,60,20);
	  opp3Medium = new JRadioButton("Medium");
	  opp3Medium.setSelected(false);
	  opp3Medium.setBounds(417,opp3Height,75,20);
	  opp3Hard = new JRadioButton("Hard");
	  opp3Hard.setSelected(false);
	  opp3Hard.setBounds(493,opp3Height,75,20);
	  opp3RadioButtons = new ButtonGroup();
	  opp3RadioButtons.add(opp3Easy);
	  opp3RadioButtons.add(opp3Medium);
	  opp3RadioButtons.add(opp3Hard);
	   
	  gameCreateScreen.add(opp3NameLabel);
	  gameCreateScreen.add(opp3NameText);
	  gameCreateScreen.add(opp3Difficulty);
	  gameCreateScreen.add(opp3Easy);
	  gameCreateScreen.add(opp3Medium);
	  gameCreateScreen.add(opp3Hard);
	  
	  //******* Stick the Dealer *******//
	  stickDealer = new Checkbox("Stick the Dealer");
	  stickDealer.setBounds(3, checkBoxHeight,125,20);
	  
	  gameCreateScreen.add(stickDealer);
	  
	  //******* Create Game Button *******//
	  createGameButton = new JButton("Create Game");
	  createGameButton.setBounds(windowWidth-152,windowHeight-62,140,35);
	  System.out.println("Here2");
	  createGameButton.addActionListener(new ActionListener(){
	    @Override
	    public void actionPerformed(ActionEvent e) {
	      GameInfo.screwDealer = stickDealer.getState();
	      final EuchreGame currentGame = new EuchreGame(opp1NameText.getText(), getSelectedButton(opp1RadioButtons), opp2NameText.getText(), getSelectedButton(opp2RadioButtons), opp3NameText.getText(), getSelectedButton(opp3RadioButtons));
	      System.out.println("Here");
	      Thread t = new Thread(new Runnable(){
	    	  @Override
	    	  public void run(){
	    		  System.out.println("Should be creating a new game");

	    		  if(GameInfo.firstGame == true)
	    		  {
		    		 // GameInfo.firstGame = false;
	    			  currentGame.startGame(board); 
	    		  }
	    		  else
	    		  {
	    			  System.out.println("Second or more new game");
	    			  Board board = GameInfo.board;
	    			  currentGame.startGame(board);
	    		  }
	    	  }
	      });
	      t.start();
	      gameCreateScreen.setVisible(false);
	      
	      //setupGameBoard();
	    }
	  });
	  
	  gameCreateScreen.add(createGameButton);
	 // gameBoard.removeAll();
	 // gameBoard.revalidate();

	 // gameBoard.repaint();
	  
	//  gameBoard.add(gameCreateScreen);
	 // JMenuBar menuBar = new Menu(gameCreateScreen);
	 // gameBoard.setJMenuBar(menuBar);

	  //gameCreateScreen.setVisible(true);
	}
	int getSelectedButton(ButtonGroup name)
	{  
	  String helper = "";
	  for (Enumeration<AbstractButton> buttons = name.getElements(); buttons.hasMoreElements();) {
	    AbstractButton button = buttons.nextElement();
	    if (button.isSelected()) {
	      helper = button.getText();
	    }
	  }
	  switch(helper)
	  {
	    case "Easy":
	     return 1;
	    case "Medium":
	      return 2;
	    case "Hard":
	      return 3;
	    default:
	      return 0;
	  }
	}
}
