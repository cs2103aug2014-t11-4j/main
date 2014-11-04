package indigoSrc;



import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.jws.soap.SOAPBinding.Style;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;


public class FillUpMainWindow {
	
	private static final int BOTTOM_PANEL_INDEX = 1;
	private static final int TABBED_PANE_INDEX = 2;
	private static final int TOP_PANEL_INDEX = 3;
	private static final int INPUT_FIELD_INDEX = 4;
	private static final int USER_FEEDBACK_INDEX = 5;
	private static final int FLOATING_TASKS_INDEX = 6;
	private static final int CALENDAR_INDEX = 7;

	private JLayeredPane displayLayers = new JLayeredPane();
	private JTextField readInput;
	private JTextField calendarField;
	private JTextArea liveUserFeedback;
	private JTextPane floatingTextPane;

	public TabbedPaneDisplay taskDisplay;
	public DefiningConstraints gridBag = new DefiningConstraints();
	
	public void addComponentsToTheFrame(JFrame mainWindow){
		Container contentPane = mainWindow.getContentPane();
		contentPane.add(displayLayers);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 600, 400);
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
			img = ImageIO.read(new File("src/gui/wood.jpg"));
			JLabel background = new JLabel(new ImageIcon(img));
			background.setBounds(0,0,600, 400);
			displayLayers.add(background,new Integer(0));
		} catch (IOException e) {
		}
		
	}

	private void createOutputPanel(Container mainPanel) {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setPreferredSize(new Dimension(500,150));
		bottomPanel.setOpaque(false);
		GridBagConstraints constraints;
		constraints =  gridBag.setConstraints(BOTTOM_PANEL_INDEX);

		addTabbedPane(bottomPanel);
		createFloatingPanel(bottomPanel);
		mainPanel.add(bottomPanel, constraints);

	}
	
	
	
	
	
	
	
	
	
	
	
	private void createFloatingPanel(JPanel bottomPanel){
		GridBagConstraints constraints;
		constraints =  gridBag.setConstraints(FLOATING_TASKS_INDEX);
		JPanel floatingPanel = new JPanel();
		
		addFloatingTextPane(floatingPanel);
		JScrollPane scroll = new JScrollPane(floatingTextPane);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		floatingPanel.add(scroll);
		floatingTextPane.setCaretPosition(0);
		floatingPanel.setLayout(new GridLayout(1,1));
		bottomPanel.add(floatingPanel, constraints);
		
	}

	private void addFloatingTextPane(JPanel floatingPanel) {
		
		GridBagConstraints constraints;
		constraints =  gridBag.setConstraints(FLOATING_TASKS_INDEX);
		floatingTextPane = new JTextPane();
		StyledDocument doc = floatingTextPane.getStyledDocument();
		addStylesToDocument(doc);
		
		LogicFacade lc = new LogicFacade("view -f");
		String display = lc.display;
		
		floatingTextPane.setText(display);
		floatingTextPane.setEditable(false);
		
		floatingPanel.add(floatingTextPane,constraints);
	}
	
	protected void addStylesToDocument(StyledDocument doc){
	
		
	}
	private void addTabbedPane(JPanel bottomPanel) {
		GridBagConstraints constraints;
		constraints =  gridBag.setConstraints(TABBED_PANE_INDEX);
		taskDisplay = new TabbedPaneDisplay();	
		bottomPanel.add(taskDisplay, constraints);	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	private void createUserInputPanel(Container mainPanel){
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setPreferredSize(new Dimension(600,100));
		topPanel.setOpaque(false);
		GridBagConstraints constraints;

		constraints =  gridBag.setConstraints(TOP_PANEL_INDEX);
		
		
		addReadInput(topPanel);
		addLiveUserFeedback(topPanel);
		mainPanel.add(topPanel, constraints);

	
	}

	private void addReadInput(JPanel topPanel) {
		GridBagConstraints constraints;
		constraints =  gridBag.setConstraints(INPUT_FIELD_INDEX);
		readInput = new JTextField();
		topPanel.add(readInput, constraints);	
		readInput.addActionListener(new readInputTextFieldListener());
		readInput.addKeyListener(new readInputTextFieldListener());	
	}
	private void addLiveUserFeedback(JPanel topPanel) {
		GridBagConstraints constraints;
		constraints =  gridBag.setConstraints(USER_FEEDBACK_INDEX);
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
			LogicFacade controller = new LogicFacade(text);
			liveUserFeedback.setText(controller.feedback);
			taskDisplay.update(controller.setTab, controller.display);		
			
			controller = new LogicFacade("view -f");
			floatingTextPane.setText(controller.display);
			
			floatingTextPane.setCaretPosition(0);
		
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
        	if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
        		LogicFacade lc = new LogicFacade("undo");
        		taskDisplay.update(0, lc.display);
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Y){
        		LogicFacade lc = new LogicFacade("redo");
        		taskDisplay.update(0, lc.display);
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_D){
        		readInput.setText("delete ");
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_N){
        		readInput.setText("add ");
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_E){
        		readInput.setText("edit ");
        	} else if(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_F){
        		readInput.setText("search ");
        	}
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
	

}
