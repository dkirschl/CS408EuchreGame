import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;


public class Menu extends JMenuBar{
	public Menu(JInternalFrame gcs, JInternalFrame howToPlay)
	{
		initMenu(gcs, howToPlay);
	}
	
	public void initMenu(final JInternalFrame gameCreateScreen, final JInternalFrame howToPlay)
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
				howToPlay.setVisible(false);
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
	    
	    menuItem = new JMenuItem("How To Play", KeyEvent.VK_T);
	    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, ActionEvent.ALT_MASK));
	    menuItem.getAccessibleContext().setAccessibleDescription("Open How To Play");
	    menuItem.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				gameCreateScreen.setVisible(false);
				howToPlay.setVisible(true);
			}
	    	
	    });
	    menu.add(menuItem);
	}

}
