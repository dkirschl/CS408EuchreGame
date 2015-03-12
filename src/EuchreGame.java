import java.awt.Button;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class EuchreGame{
  private String opp1Name;
  private int opp1Difficulty;
  private String opp2Name;
  private int opp2Difficulty;
  private String teamName;
  private int teamDifficulty;
  private int dealer;
  private int yourScore;
  private int compScore;
  private int yourTricks;
  private int compTricks;
  private ArrayList<Card> deck = new ArrayList<Card>();
  private Player you, comp1, comp2, comp3;
  private boolean gameOver;
  
  private static Semaphore human_turn, button_press;
  
  public EuchreGame()
  {
    opp1Name = "";
    opp1Difficulty = 1;
    opp2Name = "";
    opp2Difficulty = 1;
    teamName = "";
    teamDifficulty = 1;
    dealer = 3;
    yourScore = 0;
    compScore = 0;
    yourTricks = 0;
    compTricks = 0;
  }
  public EuchreGame(String o1, int o1d, String o2, int o2d, String t, int td)
  {
    opp1Name = o1;
    opp1Difficulty = o1d;
    opp2Name = o2;
    opp2Difficulty = o2d;
    teamName = t;
    teamDifficulty = td;
    dealer = 3;
    yourScore = 0;
    compScore = 0;
    yourTricks = 0;
    compTricks = 0;
    
    you = new Human();
    comp1 = new Computer(1, o1d);
    comp2 = new Computer(2, o2d);
    comp3 = new Computer(3, td);
    GameInfo.players.add(you); GameInfo.players.add(comp1); GameInfo.players.add(comp2); GameInfo.players.add(comp3);
    human_turn = new Semaphore(1);
    button_press = new Semaphore(0);
    
    GameInfo.ledSuit = "";
  }
  
  public static Semaphore getHuman_turn() {
	return human_turn;
}
public static Semaphore getButton_press() {
	return button_press;
}
public void buildGame(ArrayList<Player> players, Card turnup)	{
	JFrame gameBoard = GameInfo.board.board;
	GameInfo.board.gameBoard.setLayout(null);
	
	MidPanel midPanel;
	if(GameInfo.firstGame == true)
	{
		midPanel = new MidPanel(GameInfo.board.gameBoard.getWidth(), GameInfo.board.gameBoard.getHeight(), turnup);
	}
	else
	{
		midPanel = GameInfo.board.getMidPanel();
		System.out.println("Second mid panel mid card is: " + midPanel.pickOrPassCard.getButton().getLabel());

		midPanel.pickOrPassCard.getButton().setLabel(turnup.getButton().getLabel());
		midPanel.pickOrPassCard.setCardId(turnup.getCardId());
		midPanel.pickOrPassCard.setSuit(turnup.getSuit());
		midPanel.pickOrPassCard.setValue(turnup.getValue());
		
		hideMidPanel(midPanel);
	}
	midPanel.midPanel.setVisible(true);
	
	Opponent1Panel opp1Panel = new Opponent1Panel(GameInfo.board.gameBoard.getWidth(), GameInfo.board.gameBoard.getHeight(), opp1Name, midPanel.getOpp1MiddleCard());
	opp1Panel.opp1Panel.setVisible(true);
	
	TeamPanel teamPanel = new TeamPanel(GameInfo.board.gameBoard.getWidth(), GameInfo.board.gameBoard.getHeight(), teamName, midPanel.getTeamMiddleCard());
	teamPanel.teamPanel.setVisible(true);
	
	Opponent2Panel opp2Panel = new Opponent2Panel(GameInfo.board.gameBoard.getWidth(), GameInfo.board.gameBoard.getHeight(), opp2Name, midPanel.getOpp2MiddleCard());
	opp2Panel.opp2Panel.setVisible(true);
	
	YourPanel yourPanel;
	if(GameInfo.firstGame == true)
		yourPanel = new YourPanel(GameInfo.board.gameBoard.getWidth(), GameInfo.board.gameBoard.getHeight(), "You", midPanel.getYourMiddleCard(), players.get(0).getHand());
	else
	{
		//GameInfo.board.setYourPanel(new YourPanel(gameBoard.getWidth(), gameBoard.getHeight(), "You", midPanel.getYourMiddleCard(), players.get(0).getHand()));
		yourPanel = GameInfo.board.getYourPanel();
		resetYourPanel(yourPanel);
	}
	yourPanel.yourPanel.setVisible(true);
	
	GameInfo.board.setOpp1Panel(opp1Panel);
	GameInfo.board.setOpp2Panel(opp2Panel);
	GameInfo.board.setYourPanel(yourPanel);
	GameInfo.board.setTeamPanel(teamPanel);
	GameInfo.board.setMidPanel(midPanel);

	midPanel.initMidPanel();

	yourPanel.initYourPanel();
	
	GameInfo.board.gameBoard.add(yourPanel.yourPanel);
	GameInfo.board.gameBoard.add(teamPanel.teamPanel);
	GameInfo.board.gameBoard.add(opp1Panel.opp1Panel);
	GameInfo.board.gameBoard.add(opp2Panel.opp2Panel);
	GameInfo.board.gameBoard.add(midPanel.midPanel);
	
	GameInfo.board.gameBoard.revalidate();
	GameInfo.board.gameBoard.repaint();
	GameInfo.board.gameBoard.setVisible(true);
	
	GameInfo.firstGame = false;
}

public void startGame() {
	  
	GameInfo.dealer = dealer;
	
	  while (!isGameOver()) {
		  deal();
		  buildGame(GameInfo.players, GameInfo.middleCard);
		  moveDealer();
		  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
		  System.out.println("Next of the dealer is: " + GameInfo.nextPlayer);
		  System.out.println("Dealer is: " + GameInfo.dealer);
		  if (pickUpOrPass()) {
			  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
			  for (int i = 0; i < 5; i++) {
				  playCard();
				  GameInfo.ledSuit = "";
			  }
			  hideTrump();
		  } else {
			  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
			  if (chooseSuit()) {
				  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
				  for (int i = 0; i < 5; i++) {
					  playCard();
					  GameInfo.ledSuit="";
				  }
				  hideTrump();
			  } else {
				  break;
			  }
		  }
		  GameInfo.dealer = (GameInfo.dealer + 1) % 4;
	  
	  }

	  
  }

public void deal() {

	  Random rand = new Random();
	  int num;
	  deck.clear();
	  for (int i = 0; i < 4; i++) {
		  GameInfo.players.get(i).getHand().clear();
	  }
	  deck.add(new Card(0,9,"clubs", "Card8.jpg")); deck.add(new Card(1,10,"clubs", "Card9.jpg")); deck.add(new Card(2,11,"clubs", "Card10.jpg")); 
	  deck.add(new Card(3,12,"clubs", "Card11.jpg")); deck.add(new Card(4,13,"clubs", "Card12.jpg")); deck.add(new Card(5,14,"clubs", "Card0.jpg"));
	  
	  deck.add(new Card(6,9,"diamonds", "Card34.jpg")); deck.add(new Card(7,10,"diamonds", "Card35.jpg")); deck.add(new Card(8,11,"diamonds", "Card36.jpg")); 
	  deck.add(new Card(9,12,"diamonds", "Card37.jpg")); deck.add(new Card(10,13,"diamonds", "Card38.jpg")); deck.add(new Card(11,14,"diamonds", "Card26.jpg"));
	  
	  deck.add(new Card(12,9,"hearts", "Card47.jpg")); deck.add(new Card(13,10,"hearts", "Card48.jpg")); deck.add(new Card(14,11,"hearts", "Card49.jpg")); 
	  deck.add(new Card(15,12,"hearts", "Card50.jpg")); deck.add(new Card(16,13,"hearts", "Card51.jpg")); deck.add(new Card(17,14,"hearts", "Card39.jpg"));
	  
	  deck.add(new Card(18,9,"spades", "Card21.jpg")); deck.add(new Card(19,10,"spades", "Card22.jpg")); deck.add(new Card(20,11,"spades", "Card23.jpg")); 
	  deck.add(new Card(21,12,"spades", "Card24.jpg")); deck.add(new Card(22,13,"spades", "Card25.jpg")); deck.add(new Card(23,14,"spades", "Card13.jpg"));
	  
	  //deal 5 cards to each player
	  for (int i = 0; i <= 4; i++) {
		  for (int j = 0; j <= 3; j++) {
			  num = rand.nextInt(deck.size());
			  GameInfo.players.get(j).receiveCard(deck.get(num));
			  //Button button = new Button(GameInfo.players.get(j).getHand().get(i).getSuit() + GameInfo.players.get(j).getHand().get(i).getValue());
			  //GameInfo.players.get(j).getHand().get(i).setButton(button);
			  deck.remove(num);
		  }
	  }

	  for (int i = 0; i < 4; i++) {
		  System.out.println("Player " + i + " hand:");
		  for (int j = 0; j < 5; j++) {
		  System.out.println(GameInfo.players.get(i).getHand().get(j).getValue() + GameInfo.players.get(i).getHand().get(j).getSuit() + " ");
		  
		  }
	  }


	  //choose a card to be flipped up in the middle
	  
	  GameInfo.middleCard = deck.get(rand.nextInt(deck.size()));


	  GameInfo.middleSuit = GameInfo.middleCard.getSuit();
	  System.out.println("Turnup is: " + GameInfo.middleCard.getSuit() + GameInfo.middleCard.getValue());
}

public boolean pickUpOrPass() {

	  GameInfo.isPick = 1;
	  boolean choice = false;

	  //******* pick or pass a card *******\\
	  for (int i = 0; i < 4; i++) {
		  GameInfo.players.get(GameInfo.nextPlayer).startTurn(human_turn);
		  GameInfo.players.get(GameInfo.nextPlayer).waitForClick(button_press);
		  choice = GameInfo.players.get(GameInfo.nextPlayer).pickupOrPass();
		  System.out.println("Player " + GameInfo.nextPlayer + " choice: " + choice);
		  if (choice == true) {
			  //pick selected
			  //wait for switch
			  if(GameInfo.dealer == 0)
			  {
				  GameInfo.players.get(GameInfo.nextPlayer).startTurn(human_turn);
				  enableHumanCards(GameInfo.players.get(0).getHand());
				  GameInfo.players.get(GameInfo.nextPlayer).waitForClick(button_press);
				  hideMidPanel(GameInfo.board.getMidPanel());
			  }
			  else
			  {
				  GameInfo.players.get(GameInfo.dealer).removeCard(GameInfo.middleCard);
			  }
			  GameInfo.trump = GameInfo.middleCard.getSuit();
			  displayTrump();
			  return true;
		  }
		  GameInfo.nextPlayer = (GameInfo.nextPlayer + 1) % 4;
	  }
	  return false;
}

public boolean chooseSuit() {
	//******* Everyone passed and now it goes around again to select the suit *******\\
	  String suit = "";
	  ArrayList<Button> chooseSuitButtons = new ArrayList<Button>();
	  
	  System.out.println("No player picked up the card");
	  System.out.println("Suit not available is: " + GameInfo.middleCard.getSuit().toLowerCase());
		  
	  //Display all of the buttons on the screen
	  GameInfo.middleCard.getButton().setVisible(false);
	  chooseSuitButtons = displayChooseSuit(GameInfo.middleCard.getSuit());
		  
	  for (int i = 0; i < 4; i++)
	  {
		  GameInfo.players.get(GameInfo.nextPlayer).startTurn(human_turn);
			  
			  if(GameInfo.players.get(GameInfo.nextPlayer).isHuman() == true)
			  {
	//			  System.out.println("The player is a human so we need to enable all of the buttons");
				  for(int x = 0; x < chooseSuitButtons.size(); x++)
				  {
					  chooseSuitButtons.get(x).setEnabled(true);
				  }
			  }
			  else
			  {
	//			  System.out.println("Disable the buttons while the AI goes");
				  for(int x = 0; x < chooseSuitButtons.size(); x++)
				  {
					  chooseSuitButtons.get(x).setEnabled(false);
				  }

			  }
		  
		  
		  GameInfo.players.get(GameInfo.nextPlayer).waitForClick(button_press);
		  suit = GameInfo.players.get(GameInfo.nextPlayer).chooseSuit();
	  }
	  // Hide the visuals from the screen
	 for(int x = 0; x < chooseSuitButtons.size(); x++)		  
	  {

	  chooseSuitButtons.get(x).setVisible(false);
	  chooseSuitButtons.get(x).setEnabled(false);
	 }
	  if(suit.toLowerCase() != "pass")
	  {
		  GameInfo.trump = suit;
		  System.out.println("Trump was chosen as " + GameInfo.trump);
	//	  return true;
	  }
	  GameInfo.nextPlayer = (GameInfo.nextPlayer + 1) % 4;
	 System.out.println("Re-deal");
	 return false;
}

public void playCard() {

	  GameInfo.isPick = 0;
	  int currentWinner = -1;
	  Card winner1 = null;
	  for (int i = 0; i < 4; i++) {
		  
		  System.out.println("Player " + GameInfo.nextPlayer + " turn");
		  GameInfo.players.get(GameInfo.nextPlayer).startTurn(human_turn);
		  if(GameInfo.players.get(GameInfo.nextPlayer).isHuman() == true)
		  {
			  enableHumanCards(GameInfo.players.get(GameInfo.nextPlayer).getHand());
		  }	
		  GameInfo.players.get(GameInfo.nextPlayer).waitForClick(button_press);

		  Card tmp = GameInfo.players.get(GameInfo.nextPlayer).playCard();
		  System.out.println("Card Played : " + tmp.getValue() + tmp.getSuit());
		  
		  displayAICard(tmp);
		  
		  GameInfo.currentTrick.add(tmp);
		  GameInfo.players.get(GameInfo.nextPlayer).getHand().remove(tmp);
		  
		  if (i == 0) {
			  GameInfo.ledSuit = tmp.getSuit();
		  }
		  if (winner1 == null) {
			  winner1 = tmp;
			  currentWinner = GameInfo.nextPlayer;
		  } else {
			  winner1 = determineWinner(winner1, tmp);
			  System.out.println("Current Winner : " + winner1.getCardId());
			  
			  if (winner1.getCardId() == tmp.getCardId()) {
				  currentWinner = GameInfo.nextPlayer;
			  }
			  System.out.println("Winning player : " + currentWinner);
		  }
		  if(GameInfo.players.get(GameInfo.nextPlayer).isHuman() == true)
		  {
			  disableHumanCards(GameInfo.players.get(GameInfo.nextPlayer).getHand());
		  }
		  try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  GameInfo.nextPlayer = (GameInfo.nextPlayer + 1) % 4;
		  
		  
		  
	  }
	  GameInfo.nextPlayer = currentWinner;
	  
	  try{
		  Thread.sleep(2000);
	  }catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  hideAICards();
	  GameInfo.board.getMidPanel().getYourMiddleCard().setVisible(false);
	  GameInfo.board.getMidPanel().getOpp1MiddleCard().setVisible(false);
	  GameInfo.board.getMidPanel().getOpp2MiddleCard().setVisible(false);
	  GameInfo.board.getMidPanel().getTeamMiddleCard().setVisible(false);
	  
	  System.out.println("Cards Played");
}
  
  public Card determineWinner(Card c1, Card c2) {
	  String trump = GameInfo.trump;
	  String ledSuit = GameInfo.ledSuit;
	  c1 = adjustValue(c1);
	  c2 = adjustValue(c2);
	  if (c1.getSuit().equals(ledSuit) && c2.getSuit().equals(ledSuit)) {
		  //c1 is led suit and c2 is led suit
		  //compare value to see who wins
		  if (c1.getValue() > c2.getValue()) {
			  return c1;
		  } else {
			  return c2;
		  }
	  } else if (c1.getSuit().equals(ledSuit) && c2.getSuit().equals(trump)) {
		  //if c1 is led suit and c2 is trump then trump wins
		  return c2;
	  } else if (c1.getSuit().equals(ledSuit) && (!c2.getSuit().equals(ledSuit) && !c2.getSuit().equals(trump))) {
		  //if c1 is led suit and c2 isnt led or trump, c1 wins
		  return c1;
	  } else if (c1.getSuit().equals(trump) && !c2.getSuit().equals(trump)) {
		  //if c1 is trump and c2 isnt trump, c1 wins
		  return c1;
	  } else if (c1.getSuit().equals(trump) && c2.getSuit().equals(trump)) {
		  //if c1 is trump and c2 is trump, compare to see who wins
		  if (c1.getValue() > c2.getValue()) {
			  return c1;
		  } else {
			  return c2;
		  }
	  }
	  return c1;
  }
  
  public Card adjustValue(Card c) {
	  String trump = GameInfo.trump;
	  if (c.getValue() == 11) {
		  //card is a Jack and might need to be changed
		  if (c.getSuit().equals(trump)) {
			  c.setValue(16); //the jack of trump is the highest value card
		  } else {
			  if ((trump.equals("Spades") && c.getSuit().equals("Clubs")) || (trump.equals("Clubs") && c.getSuit().equals("Spades")) || (trump.equals("Diamonds") && c.getSuit().equals("Hearts")) || (trump.equals("Hearts") && c.getSuit().equals("Diamonds"))) {
				  c.setValue(15); // the jack of the same color as trump is the second strongest card
				  c.setSuit(trump);
			  }
		  }
	  }
	  return c;
  }
  
  public boolean isGameOver() {
	  if (GameInfo.teamOneScore >= 10 || GameInfo.teamTwoScore >= 10) {
		  return true;
	  } else {
		  return false;
	  }
  }

  public ArrayList<Button> displayChooseSuit(String suit)
  {
	  MidPanel midPanel = GameInfo.board.getMidPanel();
	  ArrayList<Button> buttons = new ArrayList<Button>();
	  
	  if(suit == "spades")
	  {
		  buttons.add(midPanel.clubs);
		  buttons.add(midPanel.hearts);
		  buttons.add(midPanel.diamonds);
	  }
	  else if(suit == "clubs")
	  {
		  buttons.add(midPanel.spades);
		  buttons.add(midPanel.hearts);
		  buttons.add(midPanel.diamonds);
	  }
	  else if(suit == "hearts")
	  {
		  buttons.add(midPanel.clubs);
		  buttons.add(midPanel.spades);
		  buttons.add(midPanel.diamonds);
	  }
	  else if(suit == "diamonds")
	  {
		  buttons.add(midPanel.clubs);
		  buttons.add(midPanel.hearts);
		  buttons.add(midPanel.spades);
	  }
	  
	  //if stick the dealer isn't on or else don't add it
	  buttons.add(midPanel.passSuit);
	  
	  for(int x = 0; x < buttons.size(); x++)
	  {
		  buttons.get(x).setVisible(true);
	  }
	  
	  return buttons;
  }
  
  public void resetYourPanel(YourPanel yourPanel)
  {
	  ArrayList<Card> cards = yourPanel.hand;
	  ArrayList<Card> newHand = GameInfo.players.get(0).getHand();
	  for(int x = 0; x < cards.size(); x++)
	  {
		  cards.get(x).setSuit(newHand.get(x).getSuit());
		  cards.get(x).setCardId(newHand.get(x).getCardId());
		  cards.get(x).setValue(newHand.get(x).getValue());
		  cards.get(x).getButton().setLabel(newHand.get(x).getButton().getLabel());
		  cards.get(x).getButton().setVisible(true);
	  }
  }
  
  public void hideMidPanel(MidPanel midPanel)
  {
	  midPanel.getYourMiddleCard().setVisible(false);
	  midPanel.getOpp1MiddleCard().setVisible(false);
	  midPanel.getOpp2MiddleCard().setVisible(false);
	  midPanel.getTeamMiddleCard().setVisible(false);	
	  midPanel.hearts.setVisible(false);
	  midPanel.spades.setVisible(false);
	  midPanel.diamonds.setVisible(false);
	  midPanel.clubs.setVisible(false);
	  midPanel.passSuit.setVisible(false);
  }
  
  public void enableHumanCards(ArrayList<Card> cards)
  {
	  ArrayList<Card> temp = new ArrayList<Card>();
	  for(int x = 0; x < cards.size(); x++)
	  {
		  if(cards.get(x).getButton().isVisible() == true)
		  {
			  if(cards.get(x).getSuit() == GameInfo.ledSuit)
				  temp.add(cards.get(x));
			  else
			  {
				  if(GameInfo.trump.toLowerCase() == "hearts" && cards.get(x).getSuit().toLowerCase() == "diamonds" && cards.get(x).getValue()==11)
					  temp.add(cards.get(x));
				  else if(GameInfo.trump.toLowerCase() == "diamonds" && cards.get(x).getSuit().toLowerCase() == "hearts" && cards.get(x).getValue()==11)
					  temp.add(cards.get(x));
				  else if(GameInfo.trump.toLowerCase() == "clubs" && cards.get(x).getSuit().toLowerCase() == "spades" && cards.get(x).getValue()==11)
					  temp.add(cards.get(x));
				  else if(GameInfo.trump.toLowerCase() == "spades" && cards.get(x).getSuit().toLowerCase() == "clubs" && cards.get(x).getValue()==11)
					  temp.add(cards.get(x));
			  }
		  }
	  }
	  
	  if(temp.size() == 0)
	  {
		  for(int x = 0; x < cards.size(); x++)
		  {
			  if(cards.get(x).getButton().isVisible() == true)
			  {
				  cards.get(x).getButton().setEnabled(true);
			  }
		  }
	  }
	  else
	  {
		  for(int y=0; y<temp.size(); y++)
		  {
			  temp.get(y).getButton().setEnabled(true);
		  }
	  }
	  
  }
  
  public void disableHumanCards(ArrayList<Card> cards)
  {
	  for(int x = 0; x < cards.size(); x++)
	  {
		  if(cards.get(x).getButton().isVisible() == true)
		  {
			  cards.get(x).getButton().setEnabled(false);
		  }
	  }
  }
  
  public void displayAICard(Card card)
  {
	  JLabel opp1Card =   GameInfo.board.getMidPanel().opp1MiddleCard;
	  JLabel opp2Card =   GameInfo.board.getMidPanel().opp2MiddleCard;
	  JLabel teamCard =   GameInfo.board.getMidPanel().teamMiddleCard;
	  
	  if(GameInfo.nextPlayer == 1)
	  {
		  opp1Card.setIcon(card.getButton().getIcon());
		  opp1Card.setVisible(true);
	  }
	  else if(GameInfo.nextPlayer == 2)
	  {
		  teamCard.setIcon(card.getButton().getIcon());
		  teamCard.setVisible(true);
	  }
	  else if(GameInfo.nextPlayer == 3)
	  {
		  opp2Card.setIcon(card.getButton().getIcon());
		  opp2Card.setVisible(true);
	  }
  }
  public void hideAICards()
  {
	  GameInfo.board.getMidPanel().opp1MiddleCard.setVisible(false);
	  GameInfo.board.getMidPanel().opp2MiddleCard.setVisible(false);
	  GameInfo.board.getMidPanel().teamMiddleCard.setVisible(false);
  }
  
  public void displayTrump()
  {
	  System.out.println("Displaying trump for player: " + GameInfo.nextPlayer);
	  if(GameInfo.nextPlayer == 0)
	  {
		  GameInfo.board.getYourPanel().trumpSuit.setLabel(GameInfo.trump);
		  GameInfo.board.getYourPanel().trumpSuit.setVisible(true);
	  }
	  if(GameInfo.nextPlayer == 1)
	  {
		  GameInfo.board.getOpp1Panel().trumpSuit.setLabel(GameInfo.trump);
		  GameInfo.board.getOpp1Panel().trumpSuit.setVisible(true);
	  }
	  if(GameInfo.nextPlayer == 2)
	  {
		  GameInfo.board.getTeamPanel().trumpSuit.setLabel(GameInfo.trump);
		  GameInfo.board.getTeamPanel().trumpSuit.setVisible(true);
	  }
	  if(GameInfo.nextPlayer == 3)
	  {
		  GameInfo.board.getOpp2Panel().trumpSuit.setLabel(GameInfo.trump);
		  GameInfo.board.getOpp2Panel().trumpSuit.setVisible(true);
	  }
  }
  
  public void hideTrump()
  {
	  GameInfo.board.getYourPanel().trumpSuit.setVisible(false);
	  GameInfo.board.getOpp1Panel().trumpSuit.setVisible(false);
	  GameInfo.board.getOpp2Panel().trumpSuit.setVisible(false);
	  GameInfo.board.getTeamPanel().trumpSuit.setVisible(false);
  }
  public void moveDealer()
  {
	  if(GameInfo.dealer == 0)
	  {
		  System.out.println("You are the dealer");
		  GameInfo.board.getOpp2Panel().dealer.setVisible(false);
		  GameInfo.board.getYourPanel().dealer.setVisible(true);  
	  }
	  else if(GameInfo.dealer == 1)
	  {
		  System.out.println("Opp1 is the dealer");
		  GameInfo.board.getYourPanel().dealer.setVisible(false);
		  GameInfo.board.getOpp1Panel().dealer.setVisible(true); 
	  }
	  else if(GameInfo.dealer == 2)
	  {
		  System.out.println("Teammate is the dealer");
		  GameInfo.board.getOpp1Panel().dealer.setVisible(false);
		  GameInfo.board.getTeamPanel().dealer.setVisible(true); 
	  }
	  else if(GameInfo.dealer == 3)
	  {
		  System.out.println("Opponent 2 is the dealer");
		  GameInfo.board.getTeamPanel().dealer.setVisible(false);
		  GameInfo.board.getOpp2Panel().dealer.setVisible(true); 
	  }
  }
  //******* Generate the getters and setters *******//
  public String getOpp1Name()
  {
    return opp1Name;
  }
  public String getOpp2Name()
  {
    return opp2Name;
  }
  public String getTeamName()
  {
    return teamName;
  }
  public int getOpp1Difficulty()
  {
    return opp1Difficulty;
  }
  public int getOpp2Difficulty()
  {
    return opp2Difficulty;
  }
  public int getTeamDifficulty()
  {
    return teamDifficulty;
  }
  public int getDealer()
  {
    return dealer; 
  }
  public int getYourScore()
  {
    return yourScore; 
  }
  public int getCompScore()
  {
    return compScore;
  }
  public int getYourTricks()
  {
    return yourTricks;
  }
  public int getCompTricks()
  {
    return compTricks;
  }
  
}