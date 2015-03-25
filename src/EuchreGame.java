import java.awt.Button;
import java.awt.Image;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
    GameInfo.teamOneScore = 0;
    GameInfo.teamTwoScore = 0;
    GameInfo.teamOneTricks = 0;
    GameInfo.teamTwoTricks = 0;
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

		midPanel.pickOrPassCard.getButton().setIcon(turnup.getNormalImage());
		midPanel.pickOrPassCard.setNormalImage(turnup.getNormalImage());
		//midPanel.pickOrPassCard.getButton().setLabel(turnup.getButton().getLabel());
		midPanel.pickOrPassCard.setCardId(turnup.getCardId());
		midPanel.pickOrPassCard.setSuit(turnup.getSuit());
		midPanel.pickOrPassCard.setValue(turnup.getValue());
		
		hideMidPanel(midPanel);
	}
	midPanel.midPanel.setVisible(true);
	
	Opponent1Panel opp1Panel = new Opponent1Panel(GameInfo.board.gameBoard.getWidth(), GameInfo.board.gameBoard.getHeight(), opp1Name, midPanel.getOpp1MiddleCard());
	opp1Panel.opp1Panel.setVisible(true);
	
	TeamPanel teamPanel = new TeamPanel(GameInfo.board.gameBoard.getWidth(), GameInfo.board.gameBoard.getHeight(), teamName, midPanel.getTeamMiddleCard());
	System.out.println(teamPanel.trickScore.getText());
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
	int x = 0;
	clearEverything();
	  while (!isGameOver()) {
		  deal();
		  buildGame(GameInfo.players, GameInfo.middleCard);

		  GameInfo.teamOneTricks = 0;
		  GameInfo.teamTwoTricks = 0;
		  GameInfo.board.getTeamPanel().updateTrickScore();
		  GameInfo.board.getTeamPanel().updateTotalScore();
		  if (x == 0)
		  {
				GameInfo.board.getMidPanel().opp2Dealer.setVisible(true);
				GameInfo.board.getMidPanel().yourDealer.setVisible(true);
				GameInfo.board.getMidPanel().yourDealer.setVisible(false);
		  }
		  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
		  //System.out.println("Next of the dealer is: " + GameInfo.nextPlayer);
		  //System.out.println("Dealer is: " + GameInfo.dealer);
		  if (pickUpOrPass()) {
			  adjustAllCards();
			  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
			  for (int i = 0; i < 5; i++) {
				  playCard();
				  GameInfo.ledSuit = "";
			  }
			  hideTrump();
		  } else {
			  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
			  if (chooseSuit()) {
				  adjustAllCards();
				  GameInfo.nextPlayer = (GameInfo.dealer + 1) % 4;
				  for (int i = 0; i < 5; i++) {
					  playCard();
					  GameInfo.ledSuit="";
					  // test
				  }
				  
				  //Clear the TrumpPlayed array
				  for(int i = 0; i < 7; i++){
					  GameInfo.TrumpPlayed[i] = 0;
				  }
				  
				  hideTrump();
			  } else {
				  continue;
			  }
		  }
		  //updating score
		  System.out.println("Updating the scores for the teams");
		  if (GameInfo.teamOneTricks > GameInfo.teamTwoTricks) {
			  //team one won
			  if (GameInfo.teamOneTricks == 5 || GameInfo.trumpCaller == 1 || GameInfo.trumpCaller == 3)
			  { 
				  GameInfo.teamOneScore += 2; 
			  } 
			  else
			  { 
				  GameInfo.teamOneScore++; 
				  GameInfo.board.getTeamPanel().updateTotalScore();
			  }
		  } else {
			  //team two won
			  if (GameInfo.teamTwoTricks == 5 || GameInfo.trumpCaller == 0 || GameInfo.trumpCaller == 2) 
			  { 
				  GameInfo.teamTwoScore += 2; 
			  } 
			  else 
			  { 
				  GameInfo.teamTwoScore++; 
				  GameInfo.board.getTeamPanel().updateTotalScore();
			  }
		  }
		  
		  
		  GameInfo.dealer = (GameInfo.dealer + 1) % 4;
		  GameInfo.middleCard = null;
		  GameInfo.trump = null;
		  GameInfo.middleSuit = null;
		  GameInfo.ledSuit = null;
		  GameInfo.nextPlayer = -1;
		  GameInfo.playedCard = null;
		  GameInfo.selectedSuit = null;
		  GameInfo.teamOneTricks = 0;
		  GameInfo.teamTwoTricks = 0;
		  GameInfo.board.getTeamPanel().updateTrickScore();
		  GameInfo.board.getTeamPanel().updateTotalScore();
		  moveDealer();
		  x++;
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
	  System.out.println("LOOOOOOOK HERE: " + GameInfo.middleCard.path);


	  GameInfo.middleSuit = GameInfo.middleCard.getSuit();
	  System.out.println("Turnup is: " + GameInfo.middleCard.getSuit() + GameInfo.middleCard.getValue());
	  
	  GameInfo.trump = "";
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
			  GameInfo.trumpCaller = GameInfo.nextPlayer;
			  GameInfo.trump = GameInfo.middleCard.getSuit();
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
				  hideMidPanel(GameInfo.board.getMidPanel());
			  }
			  displayTrump();
			  disableHumanCards(GameInfo.players.get(0).getHand());
			  //GameInfo.nextPlayer = (GameInfo.nextPlayer + 1) % 4;
			  return true;
		  }
		  GameInfo.nextPlayer = (GameInfo.nextPlayer + 1) % 4;
	  }
	  return false;
}

  public boolean chooseSuit() {
	//******* Everyone passed and now it goes around again to select the suit *******\\
	  String suit = "pass";
	  ArrayList<JButton> chooseSuitButtons = new ArrayList<JButton>();
	  
	  System.out.println("No player picked up the card");
	  System.out.println("Suit not available is: " + GameInfo.middleCard.getSuit().toLowerCase());
		  
	  //Display all of the buttons on the screen
	  GameInfo.middleCard.getButton().setVisible(false);
	  chooseSuitButtons = displayChooseSuit(GameInfo.middleCard.getSuit());
		  
	  for (int i = 0; i < 4 && suit == "pass"; i++)
	  {
		  GameInfo.players.get(GameInfo.nextPlayer).startTurn(human_turn);
			  
			  if(GameInfo.players.get(GameInfo.nextPlayer).isHuman() == true)
			  {
				  //Enable the buttons for the human
				  for(int x = 0; x < chooseSuitButtons.size(); x++)
				  {
					  chooseSuitButtons.get(x).setVisible(true);
					  chooseSuitButtons.get(x).setEnabled(true);
				  }
			  }
			  else
			  {
				  //Hide the buttons for the AI's turn
				  for(int x = 0; x < chooseSuitButtons.size(); x++)
				  {
					  chooseSuitButtons.get(x).setVisible(false);
					  chooseSuitButtons.get(x).setEnabled(false);
				  }

			  }
		  	  
		  GameInfo.players.get(GameInfo.nextPlayer).waitForClick(button_press);
		  suit = GameInfo.players.get(GameInfo.nextPlayer).chooseSuit();
		  for(int x = 0; x < chooseSuitButtons.size(); x++)
		  {
			  chooseSuitButtons.get(x).setVisible(false);
			  chooseSuitButtons.get(x).setEnabled(false);
		  }
		  if(suit != "pass"){
			  //Somebody chose a suit
			  GameInfo.trumpCaller = GameInfo.nextPlayer;
			  GameInfo.trump = suit;
			  System.out.println("Trump was chosen as " + GameInfo.trump);
			  displayTrump();
			  disableHumanCards(GameInfo.players.get(0).getHand());

			  return true;
		  } else {
			  //They passed, move on to the next player
			  GameInfo.nextPlayer = (GameInfo.nextPlayer + 1) % 4;
		  }
	  }
	  
	 System.out.println("No suit was chosen -- Re-deal");
	 return false;
}

  public void playCard() {

	  GameInfo.isPick = 0;
	  GameInfo.currentWinner = -1;
	  Card winner1 = null;
	  GameInfo.currentTrickLeader = GameInfo.nextPlayer;
	  
	  for (int i = 0; i < 4; i++) {
		  
		  //System.out.println("Player " + GameInfo.nextPlayer + " turn");
		  GameInfo.players.get(GameInfo.nextPlayer).startTurn(human_turn);
		  if(GameInfo.players.get(GameInfo.nextPlayer).isHuman() == true)
		  {
			  enableHumanCards(GameInfo.players.get(GameInfo.nextPlayer).getHand());
		  }	
		  GameInfo.players.get(GameInfo.nextPlayer).waitForClick(button_press);

		  Card tmp = GameInfo.players.get(GameInfo.nextPlayer).playCard();
		  System.out.println("Card Played : " + tmp.getValue() + tmp.getSuit());
		  
		  updateTrumpPlayed(tmp);
		  displayAICard(tmp);
		  
		  GameInfo.currentTrick.add(tmp);
		  GameInfo.players.get(GameInfo.nextPlayer).getHand().remove(tmp);
		  
		  if (i == 0) {
			  GameInfo.ledSuit = tmp.getSuit();
		  }
		  if (winner1 == null) {
			  winner1 = tmp;
			  GameInfo.currentWinner = GameInfo.nextPlayer;
		  } else {
			  System.out.println("Comparing : " + winner1.getSuit() + winner1.getValue() + " : " + tmp.getSuit() + tmp.getValue());
			  winner1 = determineWinner(winner1, tmp);
			  
			  
			  if (winner1.getCardId() == tmp.getCardId()) {
				  GameInfo.currentWinner = GameInfo.nextPlayer;
			  }
			  System.out.println("Winning player : " + GameInfo.currentWinner);
			  System.out.println("Winning Card : " + winner1.getSuit() + winner1.getValue());
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
	  GameInfo.nextPlayer = GameInfo.currentWinner;
	  if (GameInfo.currentWinner == 0 || GameInfo.currentWinner == 2) {
		  GameInfo.teamOneTricks++;
	  } else {
		  GameInfo.teamTwoTricks++;
	  }
	  GameInfo.board.getTeamPanel().updateTrickScore();
	  
	  try{
		  Thread.sleep(2000);
	  }catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	  }
	  
	  //Clear The previous trick array list
	  while(GameInfo.previousTrick.size() != 0){
		  GameInfo.previousTrick.remove(0);
	  }	 
	  
	  GameInfo.previousTrickLeader = GameInfo.currentTrickLeader;
	  
	  //Clear The current trick array list and add it to the the previous trick array list
	  while(GameInfo.currentTrick.size() != 0){
		  Card temp = GameInfo.currentTrick.remove(0);
		  GameInfo.previousTrick.add(temp);
	  }	  
	  
	  //Reset trump left global array
	  for(int i = 0; i < 7 ; i++){
		  GameInfo.TrumpPlayed[i] = 0;
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
  
  public void adjustAllCards() {
	  for (int i = 0; i < 4; i++) {
		  for (int j = 0; j < 5; j++) {
			  GameInfo.players.get(i).getHand().get(j).adjustValue();
		  }
	  }
  }
  
  public boolean isGameOver() {
	  System.out.println("Score is " + GameInfo.teamOneScore + " : " + GameInfo.teamTwoScore);
	  if (GameInfo.teamOneScore >= 10){
		  JOptionPane.showMessageDialog(GameInfo.board.board, "You Won!!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		  return true;
	  } else if(GameInfo.teamTwoScore >= 10){
		  JOptionPane.showMessageDialog(GameInfo.board.board, "You Lose!!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		  return true;
	  } else {
		  return false;
	  }
  }
  
  /*
   * Check the last card played to see if it was trump
   * If the card played was trump then update the TrumpPlayed array
   */
  public void updateTrumpPlayed(Card played) {
	  String leftSuit = "xxxxx";
		
	  switch(GameInfo.trump){
		  case "spades": 	leftSuit = "clubs";
							break;
							
		  case "clubs": 	leftSuit = "spades";
				 	  		break;
				 	  		
		  case "hearts": 	leftSuit = "diamonds";
					   		break;
					   		
		  case "diamonds": 	leftSuit = "hearts";
				 		 	break;
  	  }
	  
	  if(played.getSuit() == leftSuit && played.getValue() == 11){
		  GameInfo.TrumpPlayed[5] = 1;
	  } else if(played.getSuit() == GameInfo.trump){
		  switch(played.getValue()){
		  	case 9:		GameInfo.TrumpPlayed[0] = 1;
		  				break;
		  	case 10:	GameInfo.TrumpPlayed[1] = 1;
		  				break;
		  	case 11:	GameInfo.TrumpPlayed[6] = 1;
	  					break;
		  	case 12:	GameInfo.TrumpPlayed[2] = 1;
	  					break;
		  	case 13:	GameInfo.TrumpPlayed[3] = 1;
	  					break;
		  	case 14:	GameInfo.TrumpPlayed[4] = 1;
	  					break;
		  }
	  }
	  
	  return;
  }

  public ArrayList<JButton> displayChooseSuit(String suit)
  {
	  MidPanel midPanel = GameInfo.board.getMidPanel();
	  ArrayList<JButton> buttons = new ArrayList<JButton>();
	  
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
	  if(GameInfo.screwDealer == false || GameInfo.dealer != 0){
		  buttons.add(midPanel.passSuit);
	  }
	  
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
		  cards.get(x).getButton().setIcon(newHand.get(x).getButton().getIcon());
		  cards.get(x).getButton().setVisible(true);
	  }
	  yourPanel.yourPanel.repaint();
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
	  midPanel.pickOrPassCard.getButton().setVisible(false);
	  midPanel.pickCard.setVisible(false);
	  midPanel.passCard.setVisible(false);
  }
  
  public void enableHumanCards(ArrayList<Card> cards)
  {
	  System.out.println("EEEENNNNABLING CCCAAARRRDDDSSSSS");
	  ArrayList<Card> temp = new ArrayList<Card>();
	  for(int x = 0; x < cards.size(); x++)
	  {
		  if(cards.get(x).getButton().isVisible() == true)
		  {
			  //System.out.println("GameInfo.ledSuit: " + GameInfo.ledSuit);
			  if(cards.get(x).getSuit() == GameInfo.ledSuit)
				  temp.add(cards.get(x));
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
  
  public void enableAllHumanCards(ArrayList<Card> cards) {
	  for(int x = 0; x < cards.size(); x++)
	  {
		  if(cards.get(x).getButton().isVisible() == true)
		  {
			  cards.get(x).getButton().setEnabled(true);
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
      Image test;
      ImageIcon normalImage;
      System.out.println("Displaying Trump: " + GameInfo.trump);
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/" + GameInfo.trump + "Image.png"));
			System.out.println(getClass().getResource("/Images/dealerChip.jpg"));
			Image newImg = test.getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
			
			if(GameInfo.nextPlayer == 0)
			{
				GameInfo.board.getMidPanel().yourTrumpSuitImage.setIcon(normalImage);
				GameInfo.board.getMidPanel().yourTrumpSuitImage.setVisible(true);
			}
			else if(GameInfo.nextPlayer == 1)
			{
				GameInfo.board.getMidPanel().opp1TrumpSuitImage.setIcon(normalImage);
				GameInfo.board.getMidPanel().opp1TrumpSuitImage.setVisible(true);	
			}
			else if(GameInfo.nextPlayer == 2)
			{
				GameInfo.board.getMidPanel().teamTrumpSuitImage.setIcon(normalImage);
				GameInfo.board.getMidPanel().teamTrumpSuitImage.setVisible(true);	
			}
			else if(GameInfo.nextPlayer == 3)
			{
				GameInfo.board.getMidPanel().opp2TrumpSuitImage.setIcon(normalImage);
				GameInfo.board.getMidPanel().opp2TrumpSuitImage.setVisible(true);	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  }
  
  public void hideTrump()
  {
	  GameInfo.board.getMidPanel().yourTrumpSuitImage.setVisible(false);
	  GameInfo.board.getMidPanel().opp1TrumpSuitImage.setVisible(false);
	  GameInfo.board.getMidPanel().opp2TrumpSuitImage.setVisible(false);
	  GameInfo.board.getMidPanel().teamTrumpSuitImage.setVisible(false);
  }
  public void moveDealer()
  {
	  if(GameInfo.dealer == 0)
	  {
		  System.out.println("You are the dealer");
		  GameInfo.board.getMidPanel().opp2Dealer.setVisible(false);
		  GameInfo.board.getMidPanel().yourDealer.setVisible(true);
	  }
	  else if(GameInfo.dealer == 1)
	  {
		  System.out.println("Opp1 is the dealer");
		  GameInfo.board.getMidPanel().yourDealer.setVisible(false);
		  GameInfo.board.getMidPanel().opp1Dealer.setVisible(true);
	  }
	  else if(GameInfo.dealer == 2)
	  {
		  System.out.println("Teammate is the dealer");
		  GameInfo.board.getMidPanel().teamDealer.setVisible(true);
		  GameInfo.board.getMidPanel().opp1Dealer.setVisible(false);
	  }
	  else if(GameInfo.dealer == 3)
	  {
		  System.out.println("Opponent 2 is the dealer");
		  GameInfo.board.getMidPanel().opp2Dealer.setVisible(true);
		  GameInfo.board.getMidPanel().teamDealer.setVisible(false);
	  }
  }
  
  /*
   * Function to reset all proper variables to start a new game
   */
  public void clearEverything(){
	  GameInfo.trumpCaller = 0;
	  GameInfo.previousTrickLeader = 0;
	  GameInfo.currentTrickLeader = 0;
	  GameInfo.currentWinner = 0;
	  while(!GameInfo.previousTrick.isEmpty()){
		  GameInfo.previousTrick.remove(0);
	  }
	  
	  while(!GameInfo.currentTrick.isEmpty()){
		  GameInfo.currentTrick.remove(0);
	  }
	  
	  for(int i = 0; i < 4; i++){
		  while(!GameInfo.players.get(i).getHand().isEmpty()){
			  GameInfo.players.get(i).getHand().remove(0);
		  }
	  }
	  
	  GameInfo.TrumpPlayed[0]=GameInfo.TrumpPlayed[1]=GameInfo.TrumpPlayed[2]=GameInfo.TrumpPlayed[3]=GameInfo.TrumpPlayed[4]=
			  GameInfo.TrumpPlayed[5] = GameInfo.TrumpPlayed[6]= 0;
	  GameInfo.middleCard = null;
	  GameInfo.middleSuit = null;
	  GameInfo.trump = null;
	  GameInfo.ledSuit = null;
	  GameInfo.selectedSuit = null;
	  GameInfo.nextPlayer = 0;
	  GameInfo.isPick = 0;
	  GameInfo.picked = false;
	  GameInfo.playedCard = null;
	  GameInfo.teamOneTricks = 0;
	  GameInfo.teamTwoTricks = 0;
	  GameInfo.teamOneScore = 0;
	  GameInfo.teamTwoScore = 0;
	  
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