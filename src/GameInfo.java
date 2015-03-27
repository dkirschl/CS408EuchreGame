import java.util.ArrayList;

public class GameInfo {
	
	static boolean screwDealer;
	static int trumpCaller;
	static int previousTrickLeader;
	static int currentTrickLeader;
	static int currentWinner;
	static ArrayList<Player> players = new ArrayList<Player>();
	static ArrayList<Card> previousTrick = new ArrayList<Card>();
	static ArrayList<Card> currentTrick = new ArrayList<Card>();
	static int TrumpPlayed[] = new int []{0,0,0,0,0,0,0};
	static Card middleCard;
	static String middleSuit;
	static String trump;
	static String ledSuit;
	static String selectedSuit;
	static int dealer;
	static int nextPlayer;
	static int isPick;
	static boolean firstGame;
	static boolean picked;
	static Board board;
	static Card playedCard;
	static int teamOneTricks = 0;
	static int teamTwoTricks = 0;
	static int teamOneScore = 0;
	static int teamTwoScore = 0;
	static String howTo = 
						  "          The highest trump is the jack of the trump suit, called the right bower. The second-highest trump is the jack of the other " +
						  "suit of the same color called the left bower. The remaining trumps, and also the plain rank as follows: A (high), K, Q, J, 10, 9, 8, 7. " +
						  "If a joker has been added to the pack, it acts as the highest trump.\n" +
						  
						  "          The cards are dealt clockwise, to the left, beginning with the player to the left of the dealer. Each player receives five cards. " +
						  "The dealer may give a round of three at a time, then a round of two at a time, or may give two, then three; but the dealer must adhere " +
						  "to whichever distribution plan he begins with. After the first deal, the deal passes to the player on the dealer's left.\n" +
						  
						  "          On completing the deal, the dealer places the rest of the pack in the center of the table and turns the top card face up. " +
						  "Should the turn-up be accepted as trump by any player, the dealer has the right to exchange the turn-up for another card in his hand. " +
						  "In practice, the dealer does not take the turn-up for another card into his hand, but leaves it on the pack until it is played; the " +
						  "dealer signifies this exchange by placing his discard face down underneath the pack.\n" +
						  
						  "          Beginning with the player to the left of the dealer, each player passes or accepts the turn-up as trump. An opponent of the " +
						  "dealer accepts by saying 'I order it up.' The partner of the dealer accepts by saying, 'I assist.' The dealer accepts by making his discard, " +
						  "called 'taking it up.'\n " +
						  
						  "          The dealer signifies refusal of the turn-up by removing the card from the top and placing it (face up) partially underneath the pack; " +
						  "this is called 'turning it down.'\n" +
						  
						  "          If all four players pass in the first round, each player in turn, starting with the player to the dealer's left, has the option of " +
						  "passing again or of naming the trump suit. The rejected suit may not be named. Declaring the other suit of the same color as the reject is " +
						  "'making it next'; declaring a suit of opposite color is called 'crossing it.'\n" +
						  
						  "          If all four players pass in the second round, the cards are gathered and shuffled, and the dealer redeals. If you play with" +
						  "stick the dealer on and everyone passes ten the dealer is forced to pick a suit. Once the trump is" +
						  "fixed, either by acceptance of the turn-up or by the naming of another suit, the turn-up is rejected, the bidding ends and play begins.\n" +
						  
						  "          The goal is to win at least three tricks. If the side that fixed the trump fails to get three tricks, it is " +
						  "said to be 'euchred.' Winning all five tricks is called a 'march.'\n" +
						  
						  "          The opening lead is made by the player to the dealer's left. If he can, each player must follow suit to a lead. If unable to follow" +
						  "suit, the player may trump or discard any card. A trick is won by the highest card of the suit led, or, if it contains" +
						  "trumps, by the highest trump. The winner of a trick leads next." +
						  "\n\n" +
						  
						  "The following shows all scoring situations:\n     Partnership making trump wins 3 or 4 tricks = 1\n" +
						  "     Partnership making trump wins 5 tricks = 2\n " +
						  "     Partnership is euchred, opponents score = 2\n\n" +
						  
						  "The first partnership to score 10 points wins the game";
	
}