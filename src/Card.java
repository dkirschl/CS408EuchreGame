import java.awt.Button;


public class Card {
	
	private int cardId;
	private int value;
	private String suit;
	private Button button;
	
	public Button getButton() {
		return button;
	}

	public void setButton(Button button) {
		this.button = button;
	}
	
	public Card()
	{
		cardId = -1;
		value = -1;
		suit = "";
	}

	public Card(int id, int value, String suit) {
		cardId = id;
		this.value = value;
		this.suit = suit;
	}

	public int getCardId() {
		return cardId;
	}

	public void setCardId(int cardId) {
		this.cardId = cardId;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}
}
