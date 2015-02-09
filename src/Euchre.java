import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
//import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
//import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;



public class Euchre implements ActionListener, ItemListener {
 JFrame frame;
 JFrame newGamePanel;
 int gameScreenHeight;
 int gameScreenWidth;
 
 static Euchre euchre;
 static EuchreGame currentGame;
 
 public Euchre()
 {
  frame = new JFrame("Euchre Game!");
  newGamePanel = new JFrame("Create a Game");
  gameScreenHeight = 700;
  gameScreenWidth = 900;
 }
 public static void createAndShowGUI()
 {
  
  // Set up the initial frame to be used //
 
  euchre.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  System.out.println(euchre.gameScreenWidth + " - " + euchre.gameScreenHeight);
  euchre.frame.setMinimumSize(new Dimension(euchre.gameScreenWidth,euchre.gameScreenHeight));
  //euchre.frame.setMaximumSize(new Dimension(euchre.gameScreenWidth,euchre.gameScreenHeight));
  
  //JLabel label = new JLabel("HelloWorld");
  //frame.getContentPane().add(label);
  
  euchre.frame.setJMenuBar(euchre.createMenuBar());
  euchre.frame.setContentPane(euchre.createContentPane());
  euchre.frame.pack();
  euchre.frame.setVisible(true);;
 }
 public Container createContentPane()
 {
  JPanel contentPane = new JPanel(new BorderLayout());
  contentPane.setOpaque(true);
  return contentPane;
 }
 public JMenuBar createMenuBar()
 {
  //******* Create the Menu ***********//
    JMenuBar menuBar;
    JMenu menu;//, subMenu;
    JMenuItem menuItem;
//    JRadioButtonMenuItem rbMenuItem;
//    JCheckBoxMenuItem cbMenuItem;
    
    menuBar = new JMenuBar();
    
    menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_A);
    menu.getAccessibleContext().setAccessibleDescription("This menu controls all operation in the application");
    menuBar.add(menu);
    
    menuItem = new JMenuItem("New Game", KeyEvent.VK_T);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("Start a new game!");
    menuItem.addActionListener(this);
    menu.add(menuItem);
    
    menuItem = new JMenuItem("Records", KeyEvent.VK_T);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("See your records");
    menuItem.addActionListener(this);
    menu.add(menuItem);
    
    return menuBar;
 }
 public void actionPerformed(ActionEvent e)
 {
  String command = e.getActionCommand();
  switch(command)
  {
  case "New Game":
   createNewGame();
   break;
  case "Create Game":
   System.out.println("Creating the new game finally!");
   int x = 0;
   System.out.println("Component count: " + euchre.newGamePanel.getContentPane().getComponentCount());
   Component[] components = euchre.newGamePanel.getContentPane().getComponents();
   while(components[x] != null)
   {
    System.out.println(x + ": " + components[x].getName());
    x++;
   }
   euchre.newGamePanel.setVisible(false);
   break;
  default:
   System.out.println("Shouldn't be here!");
   break;
  }
 }
 public void createNewGame()
 {
  //newGamePanel = euchre.newGamePanel;
  JButton createGameButton;
  JLabel opp1NameLabel;
  JLabel opp1Difficulty;
  JTextField opp1NameText;
  JRadioButton opp1Easy;
  JRadioButton opp1Medium;
  JRadioButton opp1Hard;
  ButtonGroup opp1RadioButtons;
  JLabel opp2NameLabel;
  JLabel opp2Difficulty;
  JTextField opp2NameText;
  JRadioButton opp2Easy;
  JRadioButton opp2Medium;
  JRadioButton opp2Hard;
  ButtonGroup opp2RadioButtons;
  JLabel opp3NameLabel;
  JLabel opp3Difficulty;
  JTextField opp3NameText;
  JRadioButton opp3Easy;
  JRadioButton opp3Medium;
  JRadioButton opp3Hard;
  ButtonGroup opp3RadioButtons;
  Checkbox stickDealer;
  
  int opp1Height = 3;
  int opp2Height = 35;
  int opp3Height = 67;
  int checkBoxHeight = 99;
  
  int windowHeight = 225;
  int windowWidth = 556;
  
  //****** Create the initial frame ******//
  //newGamePanel = new JFrame("Creating a new game!");
  
  euchre.newGamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  euchre.newGamePanel.setMinimumSize(new Dimension(windowWidth,windowHeight));
  euchre.newGamePanel.setMaximumSize(new Dimension(windowWidth, windowHeight));
  euchre.newGamePanel.setLayout(null);
  
  
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
   
  euchre.newGamePanel.add(opp1NameLabel);
  euchre.newGamePanel.add(opp1NameText);
  euchre.newGamePanel.add(opp1Difficulty);
  euchre.newGamePanel.add(opp1Easy);
  euchre.newGamePanel.add(opp1Medium);
  euchre.newGamePanel.add(opp1Hard);

  
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
   
  euchre.newGamePanel.add(opp2NameLabel);
  euchre.newGamePanel.add(opp2NameText);
  euchre.newGamePanel.add(opp2Difficulty);
  euchre.newGamePanel.add(opp2Easy);
  euchre.newGamePanel.add(opp2Medium);
  euchre.newGamePanel.add(opp2Hard);
  
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
   
  euchre.newGamePanel.add(opp3NameLabel);
  euchre.newGamePanel.add(opp3NameText);
  euchre.newGamePanel.add(opp3Difficulty);
  euchre.newGamePanel.add(opp3Easy);
  euchre.newGamePanel.add(opp3Medium);
  euchre.newGamePanel.add(opp3Hard);
  
  //******* Stick the Dealer *******//
  stickDealer = new Checkbox("Stick the Dealer");
  stickDealer.setBounds(3, checkBoxHeight,125,20);
  
  euchre.newGamePanel.add(stickDealer);
  
  //******* Create Game Button *******//
  createGameButton = new JButton("Create Game");
  createGameButton.setBounds(windowWidth-152,windowHeight-62,140,35);
  createGameButton.addActionListener(new ActionListener(){
    @Override
    public void actionPerformed(ActionEvent e) {
      currentGame = new EuchreGame(opp1NameText.getText(), getSelectedButton(opp1RadioButtons), opp2NameText.getText(), getSelectedButton(opp2RadioButtons), opp3NameText.getText(), getSelectedButton(opp3RadioButtons));
      euchre.newGamePanel.setVisible(false);
      setupGameBoard();
    }
  });
  
  euchre.newGamePanel.add(createGameButton);
  
  //******* Make the pane visible *******//
  euchre.newGamePanel.setVisible(true);
 }
 public JLabel updateScore(int you, int cpu)
 {
	 JLabel x = new JLabel("Score: " + you + " - " + cpu);
	 return x;
 }
 
 public JLabel updateTricks(int you, int cpu)
 {
	 JLabel x = new JLabel("Tricks: " + you + " - " + cpu);
	 return x;
 }
 
 public void playCard(Button card, Button middleCard)
 {
 	System.out.println(card.getLabel());
 	middleCard.setLabel(card.getLabel());
 	middleCard.setVisible(true);
 }
 //******* Creates the game board and places the names on the board *******//
 public void setupGameBoard()
 {
	 JPanel gameBoard = new JPanel(new GridBagLayout());
	 gameBoard.setLayout(null);
	 
	 JPanel yourPanel = new JPanel();
	 JPanel teamPanel = new JPanel();
	 JPanel opp1Panel = new JPanel();
	 JPanel opp2Panel = new JPanel();
	 JPanel midPanel = new JPanel();
	
	 yourPanel.setBackground(Color.cyan);
	 teamPanel.setBackground(Color.green);
	 opp1Panel.setBackground(Color.yellow);
	 opp2Panel.setBackground(Color.blue);
	 midPanel.setBackground(Color.gray);
	 
	 int teamXCoord = 0;
	 int teamYCoord = 0;
	 int teamWidth = euchre.gameScreenWidth;
	 int teamHeight = euchre.gameScreenHeight/5;
	 
	 int yourXCoord = 0;
	 int yourYCoord = euchre.gameScreenHeight - euchre.gameScreenHeight/5 - 62;
	 int yourWidth = euchre.gameScreenWidth;
	 int yourHeight = euchre.gameScreenHeight/5;
	 
	 int opp1XCoord = 0;
	 int opp1YCoord = euchre.gameScreenHeight/5;
	 int opp1Width = euchre.gameScreenWidth/4;
	 int opp1Height = 3*(euchre.gameScreenHeight/5);
	 
	 int opp2XCoord = euchre.gameScreenWidth - euchre.gameScreenWidth/4;
	 int opp2YCoord = euchre.gameScreenHeight/5;
	 int opp2Width = euchre.gameScreenWidth/4;
	 int opp2Height = 3*(euchre.gameScreenHeight/5);
	 
	 int midXCoord = euchre.gameScreenWidth/4;
	 int midYCoord = euchre.gameScreenHeight/5;
	 int midWidth = euchre.gameScreenWidth/2;
	 int midHeight = 3*(euchre.gameScreenHeight/5);
	 
	 //******* Create the panels to be added to the game board *******\\
	 
	 teamPanel.setBounds(teamXCoord, teamYCoord, teamWidth, teamHeight);
	 yourPanel.setBounds(yourXCoord, yourYCoord, yourWidth, yourHeight);
	 opp1Panel.setBounds(opp1XCoord, opp1YCoord, opp1Width, opp1Height);
	 opp2Panel.setBounds(opp2XCoord, opp2YCoord, opp2Width, opp2Height);
	 midPanel.setBounds(midXCoord, midYCoord, midWidth, midHeight);

	 JLabel you = new JLabel("You");
	 JLabel opp1 = new JLabel(currentGame.getOpp1Name());
	 JLabel opp2 = new JLabel(currentGame.getOpp2Name());
	 JLabel team = new JLabel(currentGame.getTeamName());
	 JLabel totalScore = updateScore(currentGame.getYourScore(), currentGame.getCompScore());
	 JLabel trickScore = updateTricks(currentGame.getYourTricks(), currentGame.getCompTricks());
	 
	 //******* Add the name labels to the game board *******\\
	 
	 teamPanel.setLayout(null);
	 team.setHorizontalAlignment(SwingConstants.CENTER);
	 team.setBounds(teamWidth/2 - 60, -10, 120, 40);;
	 yourPanel.setLayout(null);
	 you.setHorizontalAlignment(SwingConstants.CENTER);
	 you.setBounds(yourWidth/2 - 40, yourHeight-25, 80, 40);
	 opp1Panel.setLayout(null);
	 opp1.setVerticalAlignment(SwingConstants.CENTER);
	 opp1.setBounds(0,opp1Height/2-40,80,40);
	 opp2Panel.setLayout(null);
	 opp2.setHorizontalAlignment(SwingConstants.CENTER);
	 opp2.setBounds(opp2Width-110, opp2Height/2-40, 110,40);
	 
	 teamPanel.add(team);
	 yourPanel.add(you);
	 opp1Panel.add(opp1);
	 opp2Panel.add(opp2);
	 
	 //******* Add the total and trick scores to the board *******\\ 
	 
	 totalScore.setBounds(teamWidth-85, -10, 100, 40);
	 trickScore.setBounds(teamWidth-87, 10, 100, 40);
	
	 teamPanel.add(totalScore);
	 teamPanel.add(trickScore);
	 
	 //******* Add the card buttons to your side of the screen *******\\
	 
	 Button yourCard1 = new Button("Card 1");
	 Button yourCard2 = new Button("Card 2");
	 Button yourCard3 = new Button("Card 3");
	 Button yourCard4 = new Button("Card 4");
	 Button yourCard5 = new Button("Card 5");
	 
	 int initialCardX = 290;
	 int cardHeight = 2*yourHeight/3;
	 int cardWidth = 60;
	 
	 yourCard1.setBounds(initialCardX, 15, cardWidth, cardHeight);
	 yourCard2.setBounds(initialCardX + (cardWidth+5), 15, cardWidth, cardHeight);
	 yourCard3.setBounds(initialCardX + 2*(cardWidth+5), 15, cardWidth, cardHeight);
	 yourCard4.setBounds(initialCardX + 3*(cardWidth+5), 15, cardWidth, cardHeight);
	 yourCard5.setBounds(initialCardX + 4*(cardWidth+5), 15, cardWidth, cardHeight);
	 
	 //******* Add the card buttons to the teammate side of the screen *******\\
	 
	 Button teamCard1 = new Button("Card 1");
	 Button teamCard2 = new Button("Card 2");
	 Button teamCard3 = new Button("Card 3");
	 Button teamCard4 = new Button("Card 4");
	 Button teamCard5 = new Button("Card 5");
	 
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
	 
	 //******* Add the card buttons to the opponent 1 side of the screen *******\\
	 
	 Button opp1Card1 = new Button("Opp1 Card1");
	 Button opp1Card2 = new Button("Opp1 Card2");
	 Button opp1Card3= new Button("Opp1 Card3");
	 Button opp1Card4 = new Button("Opp1 Card4");
	 Button opp1Card5 = new Button("Opp1 Card5");
	 
	 int intialOpp1CardY = 20;
	 
	 opp1Card1.setBounds(120, intialOpp1CardY, cardHeight, cardWidth);
	 opp1Card2.setBounds(120, intialOpp1CardY+(cardWidth+5), cardHeight, cardWidth);
	 opp1Card3.setBounds(120, intialOpp1CardY+2*(cardWidth+5), cardHeight, cardWidth);
	 opp1Card4.setBounds(120, intialOpp1CardY+3*(cardWidth+5), cardHeight, cardWidth);
	 opp1Card5.setBounds(120, intialOpp1CardY+4*(cardWidth+5), cardHeight, cardWidth);
	 
	 opp1Panel.add(opp1Card1);
	 opp1Panel.add(opp1Card2);
	 opp1Panel.add(opp1Card3);
	 opp1Panel.add(opp1Card4);
	 opp1Panel.add(opp1Card5);
	 
	 //******* Add the card buttons to the opponent 2 side of the screen *******\\
	 
	 Button opp2Card1 = new Button("Opp2 Card1");
	 Button opp2Card2 = new Button("Opp2 Card2");
	 Button opp2Card3= new Button("Opp2 Card3");
	 Button opp2Card4 = new Button("Opp2 Card4");
	 Button opp2Card5 = new Button("Opp2 Card5");
	 
	 int intialOpp2CardY = 20;
	 
	 opp2Card1.setBounds(10, intialOpp2CardY, cardHeight, cardWidth);
	 opp2Card2.setBounds(10, intialOpp2CardY+(cardWidth+5), cardHeight, cardWidth);
	 opp2Card3.setBounds(10, intialOpp2CardY+2*(cardWidth+5), cardHeight, cardWidth);
	 opp2Card4.setBounds(10, intialOpp2CardY+3*(cardWidth+5), cardHeight, cardWidth);
	 opp2Card5.setBounds(10, intialOpp2CardY+4*(cardWidth+5), cardHeight, cardWidth);
	 
	 opp2Panel.add(opp2Card1);
	 opp2Panel.add(opp2Card2);
	 opp2Panel.add(opp2Card3);
	 opp2Panel.add(opp2Card4);
	 opp2Panel.add(opp2Card5);
	 
	 //******* Create the middle card option *******\\
	 
	 midPanel.setLayout(null);
	 
	 Button yourMiddleCard = new Button("");
	 Button opp1MiddleCard = new Button("Opp1 Card");
	 Button opp2MiddleCard = new Button("Opp2 Card");
	 Button teamMiddleCard = new Button("Team Card");
	 
	 yourMiddleCard.setVisible(false);
	 yourMiddleCard.setBounds(midWidth/2-30, midHeight-200, cardWidth, cardHeight);
	 yourMiddleCard.setEnabled(false);
	 
	 opp1MiddleCard.setVisible(false);
	 opp1MiddleCard.setBounds(50, midHeight/2-cardWidth, cardHeight, cardWidth);
	 opp1MiddleCard.setEnabled(false);
	 
	 opp2MiddleCard.setVisible(false);
	 opp2MiddleCard.setBounds(midWidth - 142, midHeight/2-cardWidth, cardHeight,cardWidth);
	 opp2MiddleCard.setEnabled(false);
	 
	 teamMiddleCard.setVisible(false);
	 teamMiddleCard.setBounds(midWidth/2-30, 50, cardWidth, cardHeight);
	 teamMiddleCard.setEnabled(false);
	 
	 //******* Add the played card to the middle of the field *******\\
	 yourCard1.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	playCard(yourCard1, yourMiddleCard);
		    }
	 });
	 yourCard2.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	playCard(yourCard2, yourMiddleCard);
		    }
	 });
	 yourCard3.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	playCard(yourCard3, yourMiddleCard);
		    }
	 });
	 yourCard4.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	playCard(yourCard4, yourMiddleCard);
		    }
	 });
	 yourCard5.addActionListener(new ActionListener(){
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	playCard(yourCard5, yourMiddleCard);
		    }
	 });
	 yourPanel.add(yourCard1);
	 yourPanel.add(yourCard2);
	 yourPanel.add(yourCard3);
	 yourPanel.add(yourCard4);
	 yourPanel.add(yourCard5);
	 
	 midPanel.add(yourMiddleCard);
	 midPanel.add(opp1MiddleCard);
	 midPanel.add(opp2MiddleCard);
	 midPanel.add(teamMiddleCard);
	 
	 gameBoard.add(teamPanel);
	 gameBoard.add(yourPanel);
	 gameBoard.add(opp1Panel);
	 gameBoard.add(opp2Panel);
	 gameBoard.add(midPanel);	 
	 
	 euchre.frame.add(gameBoard);
	 euchre.frame.setVisible(true);
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
 public void itemStateChanged(ItemEvent e)
 {
  System.out.println("Item");
  System.out.println(e.toString());
 }
 public static void main(String[] args)
 {
  euchre = new Euchre();
  javax.swing.SwingUtilities.invokeLater(new Runnable() {
   public void run(){
    createAndShowGUI();
   }
  });
 }
}
