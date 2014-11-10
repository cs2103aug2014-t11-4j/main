package indigoSrc;

import gui.FillUpMainWindow;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;
//@author A0115530W
/**This is the class initializes the main JFrame of the GUI.
 */


public class InputWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int WINDOW_POS_X= 400;
	private static final int WINDOW_POS_Y = 10;
	private static final int WINDOW_WIDTH = 450;
	private static final int WINDOW_HEIGHT = 650;
	
	
	@Override
	public void setVisible(boolean value){
		super.setVisible(value);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					InputWindow mainWindow = new InputWindow();
					mainWindow.setVisible(true);
				}
				catch (Exception e){
						e.printStackTrace();
				}
			}
		});
	}
	
	public InputWindow(){
		initialize(this);
		FillUpMainWindow filler = new FillUpMainWindow();
		filler.addComponentsToTheFrame(this);
	}

	public void initialize(JFrame mainWindow){	
		mainWindow.setTitle("Indigo");
		mainWindow.setBounds(WINDOW_POS_X, WINDOW_POS_Y, WINDOW_WIDTH, WINDOW_HEIGHT);
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setIconImage(new ImageIcon("gui/goindigologo.jpg").getImage());
	}

}

