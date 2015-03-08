import java.util.ArrayList;

public class GameInfo {
	
	static boolean screwDealer;
	static int trumpCaller;
	static int previousLeader;
	static ArrayList<Player> players = new ArrayList<Player>();
	static ArrayList<Card> previousTrick;
	static ArrayList<Card> currentTrick = new ArrayList<Card>();
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
	static String howTo = "          The highest trump is the jack of the trump suit, called the right bower. The second-highest trump\n" +
						  "is the jack of the other suit of the same color called the left bower. The remaining trumps, and\n" +
						  "also the plain suits, rank as follows: A (high), K, Q, J, 10, 9, 8, 7. If a joker has been added to\n" +
						  "the pack, it acts as the highest trump.\n\n" +
						  
						  "          The cards are dealt clockwise, to the left, beginning with the player to the left of the dealer. \n" +
						  "Each player receives five cards. The dealer may give a round of three at a time, then a round of \n" +
						  "two at a time, or may give two, then three; but the dealer must adhere to whichever distribution \n" +
						  "plan he begins with. After the first deal, the deal passes to the player on the dealer's left.\n\n" +
						  
						  "          On completing the deal, the dealer places the rest of the pack in the center of the table and turns\n" +
						  "the top card face up. Should the turn-up be accepted as trump by any player, the dealer has the\n" +
						  "right to exchange the turn-up for another card in his hand. In practice, the dealer does not \n" +
						  "take the turn-up into his hand, but leaves it on the pack until it is played; the dealer signifies \n" +
						  "this exchange by placing his discard face down underneath the pack.\n\n" +
						  
						  "          Beginning with the player to the left of the dealer, each player passes or accepts the turn-up as \n" +
						  "trump. An opponent of the dealer accepts by saying 'I order it up.' The partner of the dealer \n" +
						  "accepts by saying, 'I assist.' The dealer accepts by making his discard, called 'taking it up.'\n\n " +
						  
						  "          The dealer signifies refusal of the turn-up by removing the card from the top and placing it (face up)\n" +
						  "partially underneath the pack; this is called 'turning it down.'\n\n" +
						  
						  "          If all four players pass in the first round, each player in turn, starting with the player to the \n" +
						  "dealer's left, has the option of passing again or of naming the trump suit. The rejected suit may not\n" +
						  "be named. Declaring the other suit of the same color as the reject is called 'making it next'; \n" +
						  "declaring a suit of opposite color is called 'crossing it.'\n\n" +
						  
						  "          If all four players pass in the second round, the cards are gathered and shuffled, and the next \n" +
						  "dealer deals. Once the trump is fixed, either by acceptance of the turn-up or by the naming of \n" +
						  "another suit, the turn-up is rejected, the bidding ends and play begins.\n\n" +
						  
						  "          The goal is to win at least three tricks. If the side that fixed the trump fails to get three \n" +
						  "tricks, it is said to be 'euchred.' Winning all five tricks is called a 'march.'\n\n" +
						  
						  "          The opening lead is made by the player to the dealer's left, or if this player's partner is playing \n" +
						  "alone, it is made by the player across from the dealer. If he can, each player must follow suit to \n" +
						  "a lead. If unable to follow suit, the player may trump or discard any card. A trick is won by the \n" +
						  "highest card of the suit led, or, if it contains trumps, by the highest trump. The winner of a trick \n" +
						  "leads next.\n\n" +
						  
						  "The following shows all scoring situations:\n      Partnership making trump wins 3 or 4 tricks = 1\n" +
						  "     Partnership making trump wins 5 tricks = 2\n     Lone hand wins 3 or 4 tricks = 1\n" +
						  "     Lone hand wins 5 tricks = 4\n     Partnership or lone hand is euchred, opponents score = 2\n\n" +
						  
						  "The first partnership to score 10 points wins the game";
	
}