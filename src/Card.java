import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class Card {
	
	private int cardId;
	private int value;
	private String suit;
	private JButton button;
	private int worth;
	private String cardPath;
	private ImageIcon normalImage;
	
	public JButton getButton() {
		return button;
	}

	public void setButton(JButton button) {
		this.button = button;
	}
	
	public Card()
	{
		cardId = -1;
		value = -1;
		suit = "";
	}

	public Card(int id, int value, String suit, String cardPath) {
		cardId = id;
		this.value = value;
		this.suit = suit;
		this.cardPath = cardPath;
        Image test;
		try {
			test = ImageIO.read(getClass().getResourceAsStream("/Images/" + cardPath));
			System.out.println(getClass().getResource("/Images/" + cardPath));
			Image newImg = test.getScaledInstance(70, 100, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        button = new JButton(normalImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ImageIcon getNormalImage() {
		return normalImage;
	}

	public void setNormalImage(ImageIcon normalImage) {
		this.normalImage = normalImage;
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
	
	public void setWorth(int worth) {
		this.worth = worth;
	}
	
	public int getWorth() {
		return worth;
	}
}
