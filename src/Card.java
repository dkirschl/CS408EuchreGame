import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.*;
import java.awt.image.*;
import java.awt.*;


public class Card {
	
	private int cardId;
	private int value;
	private String suit;
	private JButton button;
	private int worth;
	private String cardPath;
	private ImageIcon normalImage;
	private ImageIcon greyImage;
	public String path;
	
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
//id : unique identification of the card
//value : 9 = 9, 10 = 10, J = 11, Q = 12... etc
//suit : the suit of the card
//cardPath : location of the card image
	public Card(int id, int value, String suit, String cardPath) {
		cardId = id;
		this.value = value;
		this.suit = suit;
		this.cardPath = cardPath;
        Image test;
		try {
			path = "/Images/"+ cardPath;
			test = ImageIO.read(getClass().getResourceAsStream("/Images/" + cardPath));
			Image newImg = test.getScaledInstance(70, 100, java.awt.Image.SCALE_SMOOTH);
			normalImage = new ImageIcon(newImg);
	        button = new JButton(normalImage);
	        button.setDisabledIcon(normalImage);
	        
	        Toolkit tk = Toolkit.getDefaultToolkit();
	        ImageFilter filter = new GrayFilter(true, 10);
	        ImageProducer producer = new FilteredImageSource(newImg.getSource(),filter);
	        Image newGreyImage = tk.createImage(producer);
	        greyImage = new ImageIcon(newGreyImage);
	        
	        //button.setDisabledIcon(greyImage);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Card(Card another){
		this.cardId = another.getCardId();
		this.value = another.getValue();
		this.suit = another.getSuit();
		this.button = another.getButton();
		this.worth = another.getWorth();
	}
	
	public void setGray() {
		button.setDisabledIcon(greyImage);
	}
	
	public void unsetGray(){
		button.setDisabledIcon(normalImage);
	}
	
	public void adjustValue() {
		//Find the left and right bauer and changes their values
		
		  String trump = GameInfo.trump;
		  if (getValue() == 11) {
			  //card is a Jack and might need to be changed
			  if (getSuit().equals(trump)) {
				  setValue(16); //the jack of trump is the highest value card
			  } else {
				  if ((trump.equals("spades") && getSuit().equals("clubs")) || (trump.equals("clubs") && getSuit().equals("spades")) || (trump.equals("diamonds") && getSuit().equals("hearts")) || (trump.equals("hearts") && getSuit().equals("diamonds"))) {
					  setValue(15); // the jack of the same color as trump is the second strongest card
					  setSuit(trump);
				  }
			  }
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
