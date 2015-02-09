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
  }
  
  public void startGame() {
	  //run game logic
	  
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