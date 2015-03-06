import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class Menu extends JMenuBar{
	public Menu(JPanel gcs)
	{
		initMenu(gcs);
	}
	
	public void initMenu(JPanel gameCreateScreen)
	{
	    JMenu menu;
	    JMenuItem menuItem;
	    
	    menu = new JMenu("File");
	    menu.setMnemonic(KeyEvent.VK_A);
	    menu.getAccessibleContext().setAccessibleDescription("This menu controls all operation in the application");
	    add(menu);
	    
	    menuItem = new JMenuItem("New Game", KeyEvent.VK_T);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
	    menuItem.getAccessibleContext().setAccessibleDescription("Start a new game!");
	    menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Clicked new game");
				gameCreateScreen.setVisible(true);
			}
	    	
	    });
	   // menuItem.addActionListener();
	    menu.add(menuItem);
	    
	    menuItem = new JMenuItem("Records", KeyEvent.VK_T);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
	    menuItem.getAccessibleContext().setAccessibleDescription("See your records");
	    //menuItem.addActionListener(startGame);
	    menu.add(menuItem);
	}

}
