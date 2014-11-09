package gui;

import indigoSrc.LogicFacade;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**This is the class which is used to fill the the main window when it is called.
 * It returns components like the Input line, Feedback line, and the Tab Pane display.  
 * This class also contains two listeners on is implemented on the command line JTextField and the other 
 * listens to the keyboard. The listener sub class also contains a method which performs tasks according to the 
 * keyboard input.
 * 
 * @author Sritam
 *
 */

public class FillUpMainWindow {
	
	private static final int BOTTOM_PANEL_INDEX = 1;
	private static final int TABBED_PANE_INDEX = 2;
	private static final int TOP_PANEL_INDEX = 3;
	private static final int INPUT_FIELD_INDEX = 4;
	private static final int USER_FEEDBACK_INDEX = 5;
	private static final int MAIN_PANEL_POS_X= 0;
	private static final int MAIN_PANEL_POS_Y = 0;
	private static final int MAIN_PANEL_WIDTH = 450;
	private static final int MAIN_PANEL_HEIGHT = 650;
	private static final int FONT_SIZE = 14;
	

	private JLayeredPane displayLayers = new JLayeredPane();
	private JTextField readInput;
	private JTextArea liveUserFeedback;
	public TabbedPaneDisplay taskDisplay;
	
	public DefiningConstraints gridBag = new DefiningConstraints();
	
	public void addComponentsToTheFrame(JFrame mainWindow){
		Container contentPane = mainWindow.getContentPane();
		contentPane.add(displayLayers);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(MAIN_PANEL_POS_X, MAIN_PANEL_POS_Y, MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT);
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.setOpaque(false);
		
		GridBagConstraints bottomPanelConstraints, topPanelConstraints;
		bottomPanelConstraints =  gridBag.setConstraints(BOTTOM_PANEL_INDEX);
		topPanelConstraints =  gridBag.setConstraints(TOP_PANEL_INDEX);
		
		setBackGroundImage();
		
		JPanel inputPanel = createUserInputPanel();
		mainPanel.add(inputPanel, topPanelConstraints);
		
		JPanel outputPanel = createOutputPanel();
		mainPanel.add(outputPanel, bottomPanelConstraints);
		
		displayLayers.add(mainPanel,new Integer(1)); 
	}

	private void setBackGroundImage() {
		BufferedImage img;
		try {
			img = ImageIO.read(new File("src/gui/wood.jpg"));
			JLabel background = new JLabel(new ImageIcon(img));
			background.setBounds(MAIN_PANEL_POS_X,MAIN_PANEL_POS_Y, MAIN_PANEL_WIDTH, MAIN_PANEL_HEIGHT );
			displayLayers.add(background,new Integer(0));
		} catch (IOException e) {
		}
		
	}

	private JPanel createUserInputPanel(){
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setOpaque(false);	
		
		GridBagConstraints inputFieldConstraints, userFeedbackConstraints;
		inputFieldConstraints =  gridBag.setConstraints(INPUT_FIELD_INDEX);
		userFeedbackConstraints = gridBag.setConstraints(USER_FEEDBACK_INDEX);
		
		JTextField readInput = setReadInput();
		topPanel.add(readInput,inputFieldConstraints);	
		JPanel feedbackPanel = UserFeedbackPanel();
		topPanel.add(feedbackPanel, userFeedbackConstraints);
		
		return topPanel;
		
	}

	private JTextField setReadInput() {
	
		readInput = new JTextField();
		readInput.addActionListener(new readInputTextFieldListener());
		readInput.addKeyListener(new readInputTextFieldListener());	
		return readInput;
	}
	private JPanel UserFeedbackPanel(){
		JPanel feedbackPanel = new JPanel(new GridBagLayout());
		feedbackPanel.setOpaque(false);
		feedbackPanel.setLayout(new GridLayout(1,1));
		JTextArea liveUserFeedback = setLiveUserFeedback();
		JScrollPane scroll = new JScrollPane(liveUserFeedback);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		feedbackPanel.add(scroll);
		
		return feedbackPanel;
	}

	private JTextArea setLiveUserFeedback() {
	
		liveUserFeedback = new JTextArea();
		Border liveUserFeedbackBorder = new LineBorder(Color.blue);
		liveUserFeedback.setBorder(liveUserFeedbackBorder);
		liveUserFeedback.setLineWrap(true);
		liveUserFeedback.setEditable(false);
		liveUserFeedback.setText("Welcome to Indigo!");
		Font font = new Font("Monospaces", Font.PLAIN, FONT_SIZE);
		liveUserFeedback.setFont(font);
		return liveUserFeedback;
	}
	
	private JPanel createOutputPanel() {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setOpaque(false);
		
		GridBagConstraints tabbedPaneConstraints;
		tabbedPaneConstraints =  gridBag.setConstraints(TABBED_PANE_INDEX);
		
		addTabbedPane(bottomPanel);
		bottomPanel.add(taskDisplay, tabbedPaneConstraints);	
		return bottomPanel; 
	
	}

	
	private TabbedPaneDisplay addTabbedPane(JPanel bottomPanel) {
		taskDisplay = new TabbedPaneDisplay();		
		return taskDisplay;
		
	}

	public class readInputTextFieldListener implements ActionListener, KeyListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String text = readInput.getText();
			readInput.selectAll();
			LogicFacade controller = new LogicFacade(text);
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
        } else if(id == KeyEvent.KEY_PRESSED) {	
        	if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
        		LogicFacade lc = new LogicFacade("undo");
        		taskDisplay.update(lc.display);
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
        		LogicFacade lc = new LogicFacade("redo");
        		taskDisplay.update(lc.display);
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D){
        		readInput.setText("delete ");
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
        		readInput.setText("add ");
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E){
        		readInput.setText("edit ");
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F){
        		readInput.setText("search ");
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C){
        		
        	}
        } 
        
        else if(id == KeyEvent.KEY_RELEASED) {
        	if (command.equals("a") || command.equals("ad") || command.equals("add")) 
        		liveUserFeedback.setText( "add <some task>");
        	else if (command.equals("d") ||command.equals("de") || command.equals("del") || command.equals("dele")|| command.equals("delet")|| command.equals("delete"))
        		liveUserFeedback.setText( "delete <index>");
        	else if (command.equals("v") || command.equals("vi") || command.equals("vie"))
        		liveUserFeedback.setText( "view <today/thisweek/thismonth/done/undone>");
        	else if (command.equals("e") || command.equals("ed") || command.equals("edi") || command.equals("edit"))
        		liveUserFeedback.setText( "edit <index> <some task>");
        	else if (command.equals("c")||command.equals("co")||command.equals("com")||command.equals("comp")||command.equals("compl")
        			||command.equals("comple")||command.equals("complet")||command.equals("complete")){
        		liveUserFeedback.setText("complete <index>");
        	}

        }
	}
	

}
