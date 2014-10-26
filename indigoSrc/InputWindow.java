package indigoSrc;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class InputWindow extends JFrame {
	
	
	private static final int BOTTOM_PANEL_INDEX = 1;
	private static final int TABBED_PANE_INDEX = 2;
	private static final int TOP_PANEL_INDEX = 3;
	private static final int INPUT_FIELD_INDEX = 4;
	private static final int USER_FEEDBACK_INDEX = 5;
	static final String newline = System.getProperty("line.separator");
	private JLayeredPane displayLayers = new JLayeredPane();
	private JTextField readInput;
	private JTextArea liveUserFeedback;
	private JTextArea liveUserFeedback2;
	TabbedPaneDisplay taskDisplay;
	
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
		addComponentsToTheFrame(this);
	}

	public void initialize(JFrame mainWindow){	
		mainWindow.setTitle("Indigo");
		mainWindow.setBounds(300, 150, 700, 500);
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setIconImage(new ImageIcon("src/IndigoLogo.jpg").getImage());
	}
	
	public void addComponentsToTheFrame(JFrame mainWindow){
		Container contentPane = mainWindow.getContentPane();
		contentPane.add(displayLayers);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 700, 500);
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setOpaque(false);
		
		setBackGroundImage();
		createUserInputPanel(mainPanel);
		createOutputPanel(mainPanel);
		displayLayers.add(mainPanel,new Integer(1)); 
	}
	
	private void setBackGroundImage() {
		BufferedImage img;
		try {
			img = ImageIO.read(new File("src/wood.jpg"));
			JLabel background = new JLabel(new ImageIcon(img));
			background.setBounds(0,0,700, 500);
			displayLayers.add(background,new Integer(0));
		} catch (IOException e) {
			//liveUserFeedback.setText("Cannot load image");
		}
		
	}

	private void createOutputPanel(Container mainPanel) {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setPreferredSize(new Dimension(600,200));
		bottomPanel.setOpaque(false);
		GridBagConstraints constraints;
		constraints = setConstraints(BOTTOM_PANEL_INDEX);
		addTabbedPane(bottomPanel);
		mainPanel.add(bottomPanel, constraints);
	}

	private void addTabbedPane(JPanel bottomPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(TABBED_PANE_INDEX);
		taskDisplay = new TabbedPaneDisplay();	
		bottomPanel.add(taskDisplay, constraints);	
	}

	private void createUserInputPanel(Container mainPanel){
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setPreferredSize(new Dimension(600,100));
		topPanel.setOpaque(false);
		GridBagConstraints constraints;
		constraints = setConstraints(TOP_PANEL_INDEX);
		addReadInput(topPanel);
		addLiveUserFeedback(topPanel);
		addLiveUserFeedback2(topPanel);
		mainPanel.add(topPanel, constraints);
	
	}


	private void addReadInput(JPanel topPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(INPUT_FIELD_INDEX);
		readInput = new JTextField();
		topPanel.add(readInput, constraints);	
		readInput.addActionListener(new readInputTextFieldListener());
		readInput.addKeyListener(new readInputTextFieldListener());	
	}
	private void addLiveUserFeedback(JPanel topPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(USER_FEEDBACK_INDEX);
		liveUserFeedback = new JTextArea(1,1);
		Border liveUserFeedbackBorder = new LineBorder(Color.white);
		liveUserFeedback.setMaximumSize(liveUserFeedback.getSize());
		liveUserFeedback.setBorder(liveUserFeedbackBorder);
		liveUserFeedback.setLineWrap(true);
		liveUserFeedback.setEditable(false);
		topPanel.add(liveUserFeedback, constraints);
	}
	
	private void addLiveUserFeedback2(JPanel topPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(6);
		liveUserFeedback2 = new JTextArea(1,1);
		Border liveUserFeedbackBorder = new LineBorder(Color.white);
		liveUserFeedback2.setMaximumSize(liveUserFeedback2.getSize());
		liveUserFeedback2.setBorder(liveUserFeedbackBorder);
		liveUserFeedback2.setLineWrap(true);
		liveUserFeedback2.setEditable(false);
		topPanel.add(liveUserFeedback2, constraints);
	}

	
	public class readInputTextFieldListener implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = readInput.getText();
			readInput.selectAll();
			IndigoLogic controller = new IndigoLogic(text);
			liveUserFeedback2.setText(controller.feedback);
			taskDisplay.update();
			//showFeedback(controller);
			readInput.requestFocusInWindow();
		}

		@Override
		public void keyPressed(KeyEvent e) {
			 displayInfo(e, readInput.getText());
		}

		@Override
		public void keyReleased(KeyEvent e) {
			displayInfo(e, readInput.getText());
		}

		@Override
		public void keyTyped(KeyEvent e) {
			displayInfo(e, readInput.getText());
		}

	}
	private void displayInfo(KeyEvent e, String command) {
        int id = e.getID();
        if (id == KeyEvent.KEY_TYPED) {
        	liveUserFeedback.setText( "");	
        }
        
        else if(id == KeyEvent.KEY_PRESSED) {
        	liveUserFeedback.setText( "");	
        } 
        
        else if(id == KeyEvent.KEY_RELEASED) {
			if (command.equals("a") || command.equals("ad") || command.equals("add"))
        		liveUserFeedback.setText( "add help text here");
        	else if (command.equals("d") ||command.equals("de") || command.equals("del") || command.equals("dele")|| command.equals("delet")|| command.equals("delete"))
        		liveUserFeedback.setText( "delete help text here");
        	else if (command.equals("v") || command.equals("vi") || command.equals("vie") || command.equals("view"))
        		liveUserFeedback.setText( "view help text here");
        	else if (command.equals("e") || command.equals("ed") || command.equals("edi") || command.equals("edit"))
        		liveUserFeedback.setText( "edit help text here");
        }
	}
	
	private GridBagConstraints setConstraints(int componentIndex) {
		GridBagConstraints constraints;
		Insets topPanel = new Insets(10,0,0,10);
		Insets bottomPanel = new Insets(0,0,70,10);
		Insets tabbedPaneDisplayInsets = new Insets(10,20,0,20);
		Insets readInputInsets = new Insets(0,20,0,20);
		Insets liveUserFeedbackInsets = new Insets(0,20,10,20);
		
		if(componentIndex == TOP_PANEL_INDEX){
			constraints = new GridBagConstraints(0,0,3,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,topPanel,0,0);
			return constraints;
		}
		else if(componentIndex == TABBED_PANE_INDEX){
			constraints = new GridBagConstraints(0,0,1,1,0.1,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,tabbedPaneDisplayInsets,0,0);
			return constraints;
		}
		else if(componentIndex == BOTTOM_PANEL_INDEX){
			constraints = new GridBagConstraints(0,1,3,3,0.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,bottomPanel,0,0);
			return constraints;
		}
		else if(componentIndex == INPUT_FIELD_INDEX){
			constraints = new GridBagConstraints(0,0,3,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,readInputInsets,0,0);
			
			return constraints;
		}
		else if(componentIndex == USER_FEEDBACK_INDEX){
			constraints = new GridBagConstraints(0,1,3,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,liveUserFeedbackInsets,0,0);
			return constraints;
		}
		else if(componentIndex == 6){
			constraints = new GridBagConstraints(0,2,3,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,liveUserFeedbackInsets,0,0);
			return constraints;
		}
		
		
		return null;
	}
}
