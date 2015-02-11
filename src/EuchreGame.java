import java.util.ArrayList;
import java.util.Random;

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
  private ArrayList<Player> players = new ArrayList<Player>();
  private Player you, comp1, comp2, comp3;
  private boolean gameOver;
  public EuchreGame()
  {
    opp1Name = "";
    opp1Difficulty = 1;
    opp2Name = "";
    opp2Difficulty = 1;
    teamName = "";
    teamDifficulty = 1;
    dealer = 0;
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
    dealer = 0;
    yourScore = 0;
    compScore = 0;
    yourTricks = 0;
    compTricks = 0;
    
    you = new Human();
    comp1 = new Computer(o1d);
    comp2 = new Computer(o2d);
    comp3 = new Computer(td);
    players.add(you); players.add(comp1); players.add(comp2); players.add(comp3);
    
  }
  
  public void startGame() {
	  Random rand = new Random();
	  int num;
	  gameOver = false;
	  Card turnup;
	  String trump = null;
	  while (!gameOver) {
	  //initialize deck
	  deck.add(new Card(0,9,"clubs")); deck.add(new Card(1,10,"clubs")); deck.add(new Card(2,11,"clubs")); deck.add(new Card(3,12,"clubs")); 
	  deck.add(new Card(4,13,"clubs")); deck.add(new Card(5,14,"clubs"));
	  
	  deck.add(new Card(6,9,"diamonds")); deck.add(new Card(7,10,"diamonds")); deck.add(new Card(8,11,"diamonds")); deck.add(new Card(9,12,"diamonds")); 
	  deck.add(new Card(10,13,"diamonds")); deck.add(new Card(11,14,"diamonds"));
	  
	  deck.add(new Card(12,9,"hearts")); deck.add(new Card(13,10,"hearts")); deck.add(new Card(14,11,"hearts")); deck.add(new Card(15,12,"hearts")); 
	  deck.add(new Card(16,13,"hearts")); deck.add(new Card(17,14,"hearts"));
	  
	  deck.add(new Card(18,9,"spades")); deck.add(new Card(19,10,"spades")); deck.add(new Card(20,11,"spades")); deck.add(new Card(21,12,"spades")); 
	  deck.add(new Card(22,13,"spades")); deck.add(new Card(23,14,"spades"));
	  
	  
	  
	  //deal 5 cards to each player
	  for (int i = 0; i < 4; i++) {
		  for (int j = 0; j < 3; j++) {
			  num = rand.nextInt(deck.size());
			  players.get(j).receiveCard(deck.get(num));
			  deck.remove(num);
		  }
	  }
	  //choose a card to be flipped up in the middle
	  turnup = deck.get(rand.nextInt(deck.size()));
	  for (int i = 0; i < 3; i++) {
		  players.get(i).chooseSuit();
		  
		  trump = turnup.getSuit();
	  }
	  
	  Card winner = null;
	  String ledSuit = null;
	  for (int i = 0; i < 3; i++) {
		  Card tmp = players.get(i).playCard();
		  if (i == 0) {
			  ledSuit = tmp.getSuit();
		  }
		  if (winner == null) {
			  winner = tmp;
		  } else {
			  winner = determineWinner(winner, tmp, trump, ledSuit);
		  }
	  }
	  
	  }
	  
  }
  
  public Card determineWinner(Card c1, Card c2, String trump, String ledSuit) {
	  c1 = adjustValue(c1, trump);
	  c2 = adjustValue(c2, trump);
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
  
  public Card adjustValue(Card c, String trump) {
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