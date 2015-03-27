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



public class Euchre{
 JFrame frame;
 JFrame newGamePanel;
 int gameScreenHeight;
 int gameScreenWidth;
 
 static Button teamCard1;
 static Button yourCard1;
 static Button yourCard2;
 static Button yourCard3;
 static Button yourCard4;
 static Button yourCard5;
 
 static Euchre euchre;
 static EuchreGame currentGame;
 
 public Euchre()
 {

 }
 public static void createAndShowGUI()
 {
  Board board = new Board();
  board.board.pack();
  board.board.setVisible(true);
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
 	middleCard.setLabel(card.getLabel());
 	middleCard.setVisible(true);
 }

 public void itemStateChanged(ItemEvent e)
 {
  System.out.println("Item");
  System.out.println(e.toString());
 }
 public static void main(String[] args)
 {
	 final Euchre euchre = new Euchre();
	 javax.swing.SwingUtilities.invokeLater(new Runnable() {
		 public void run(){
			 euchre.createAndShowGUI();
		 }
	 });
 }
}
