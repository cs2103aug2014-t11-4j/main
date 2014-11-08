package indigoSrc;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.UIManager;

public class InputWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

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
		mainWindow.setBounds(875, 40, 450, 650);
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setIconImage(new ImageIcon("src/gui/goindigologo.jpg").getImage());
	}

}

