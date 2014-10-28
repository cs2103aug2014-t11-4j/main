package indigoSrc;



import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class FillUpMainWindow {
	
	private static final int BOTTOM_PANEL_INDEX = 1;
	private static final int TABBED_PANE_INDEX = 2;
	private static final int TOP_PANEL_INDEX = 3;
	private static final int INPUT_FIELD_INDEX = 4;
	private static final int USER_FEEDBACK_INDEX = 5;
	private static final int PROGRESS_BAR_INDEX = 6;
	private static final int CALENDAR_INDEX = 7;	
	private JLayeredPane displayLayers = new JLayeredPane();
	private JTextField readInput;
	private JTextField calendarField;
	private JTextArea liveUserFeedback;
	private JProgressBar taskStatus;
	public TabbedPaneDisplay taskDisplay;	
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

		addCalendar(topPanel);
		addProgressBar(topPanel);
		addReadInput(topPanel);
		addLiveUserFeedback(topPanel);
		mainPanel.add(topPanel, constraints);

	
	}



	private void addCalendar(JPanel topPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(CALENDAR_INDEX);
		calendarField = new JTextField();
		calendarField.setOpaque(false);
		topPanel.add(calendarField,constraints);
		
//		Timer t = new Timer(1000, new Listener);
		
		
	}

	private void addProgressBar(JPanel topPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(PROGRESS_BAR_INDEX);
		taskStatus = new JProgressBar(75,100);
		taskStatus.setValue(0);
		taskStatus.setStringPainted(true);
		topPanel.add(taskStatus, constraints);
		
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
		liveUserFeedback = new JTextArea(3,1);
		Border liveUserFeedbackBorder = new LineBorder(Color.white);
		liveUserFeedback.setMaximumSize(liveUserFeedback.getSize());
		liveUserFeedback.setBorder(liveUserFeedbackBorder);
		liveUserFeedback.setLineWrap(true);
		liveUserFeedback.setEditable(false);
		liveUserFeedback.setText("Welcome to Indigo!");
		topPanel.add(liveUserFeedback, constraints);
	}
	

	
	public class readInputTextFieldListener implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = readInput.getText();
			readInput.selectAll();
			IndigoLogic controller = new IndigoLogic(text);
			liveUserFeedback.setText(controller.feedback);
			taskDisplay.update(text);
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
        	
        }
        

        else if(id == KeyEvent.KEY_PRESSED) {	
        	
        } 
        
        else if(id == KeyEvent.KEY_RELEASED) {
        	if (command.equals("a") || command.equals("ad") || command.equals("add")) 
        		liveUserFeedback.setText( "add (index) <some task>");
        	else if (command.equals("d") ||command.equals("de") || command.equals("del") || command.equals("dele")|| command.equals("delet")|| command.equals("delete"))
        		liveUserFeedback.setText( "delete <index>");
        	else if (command.equals("v") || command.equals("vi") || command.equals("vie") || command.equals("view"))
        		liveUserFeedback.setText( "view <today/this week/this month/over due>");
        	else if (command.equals("e") || command.equals("ed") || command.equals("edi") || command.equals("edit"))
        		liveUserFeedback.setText( "edit <index> <some task>");
        	else if (command.equals("c")||command.equals("co")||command.equals("com")||command.equals("comp")||command.equals("compl")
        			||command.equals("comple")||command.equals("complet")||command.equals("complete")){
        		liveUserFeedback.setText("complete <index>");
        	}

        }
	}
	
	private GridBagConstraints setConstraints(int componentIndex) {
		GridBagConstraints constraints;
		Insets topPanel = new Insets(10,0,5,10);
		Insets insetsOfCalendarField = new Insets(0,70,5,20);
		Insets insetsOfProgressBar = new Insets(0,20,5,70);
		Insets insetsOfReadInput = new Insets(0,20,0,20);
		Insets insetsOfUserFeedback = new Insets(0,20,0,20);
		
		Insets bottomPanel = new Insets(5,0,60,10);
		Insets insetsOfTabbedPane = new Insets(0,20,0,20);
		
		

		
		if(componentIndex ==  TOP_PANEL_INDEX){
			constraints = new GridBagConstraints(0,0,3,3,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,topPanel,0,0);

			return constraints;
		}
		else if(componentIndex == CALENDAR_INDEX){
			constraints = new GridBagConstraints(0,0,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfCalendarField,0,0);
			
			return constraints;
		}
		else if(componentIndex == PROGRESS_BAR_INDEX){
			constraints = new GridBagConstraints(1,0,1,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfProgressBar,0,0);
			
			return constraints;
		}
		else if(componentIndex == INPUT_FIELD_INDEX){
			constraints = new GridBagConstraints(0,1,3,1,0.1,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfReadInput,0,0);

			
			return constraints;
		}
		else if(componentIndex == USER_FEEDBACK_INDEX){

			constraints = new GridBagConstraints(0,2,3,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfUserFeedback,0,0);

			return constraints;
		}
		
		else if(componentIndex ==BOTTOM_PANEL_INDEX){

			constraints = new GridBagConstraints(0,3,3,3,0.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,bottomPanel,0,0);
			return constraints;
		}
		else if(componentIndex == TABBED_PANE_INDEX){
			constraints = new GridBagConstraints(0,0,1,1,0.1,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insetsOfTabbedPane,0,0);

			return constraints;
		}

		
		return null;
	}
}
