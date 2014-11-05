package indigoSrc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.StyledDocument;

public class FillUpMainWindow {
	
	private static final int BOTTOM_PANEL_INDEX = 1;
	private static final int TABBED_PANE_INDEX = 2;
	private static final int TOP_PANEL_INDEX = 3;
	private static final int INPUT_FIELD_INDEX = 4;
	private static final int USER_FEEDBACK_INDEX = 5;
	//private static final int FLOATING_TASKS_INDEX = 6;
	//private static final int CALENDAR_INDEX = 7;

	private JLayeredPane displayLayers = new JLayeredPane();
	private JTextField readInput;
	//private JTextField calendarField;
	private JTextArea liveUserFeedback;
	//private JTextPane floatingTextPane;

	public TabbedPaneDisplay taskDisplay;
	public DefiningConstraints gridBag = new DefiningConstraints();
	
	public void addComponentsToTheFrame(JFrame mainWindow){
		Container contentPane = mainWindow.getContentPane();
		contentPane.add(displayLayers);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 450, 650);
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
			background.setBounds(-100,-100, 550, 750);
			displayLayers.add(background,new Integer(0));
		} catch (IOException e) {
		}
		
	}

	private JPanel createUserInputPanel(){
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setPreferredSize(new Dimension(600,100));
		topPanel.setOpaque(false);	
		
		GridBagConstraints inputFieldConstraints, userFeedbackConstraints;
		inputFieldConstraints =  gridBag.setConstraints(INPUT_FIELD_INDEX);
		userFeedbackConstraints = gridBag.setConstraints(USER_FEEDBACK_INDEX);
		
		JTextField readInput = setReadInput(topPanel);
		topPanel.add(readInput,inputFieldConstraints);	
		JTextArea liveUserFeedback = setLiveUserFeedback(topPanel);
		topPanel.add(liveUserFeedback, userFeedbackConstraints);
		
		return topPanel;
		
	}

	private JTextField setReadInput(JPanel topPanel) {
	
		readInput = new JTextField();
		readInput.addActionListener(new Listener());
		readInput.addKeyListener(new Listener());	
		return readInput;
	}
	private JTextArea setLiveUserFeedback(JPanel topPanel) {
	
		liveUserFeedback = new JTextArea(3,1);
		Border liveUserFeedbackBorder = new LineBorder(Color.white);
		liveUserFeedback.setMaximumSize(liveUserFeedback.getSize());
		liveUserFeedback.setBorder(liveUserFeedbackBorder);
		liveUserFeedback.setLineWrap(true);
		liveUserFeedback.setEditable(false);
		liveUserFeedback.setText("Welcome to Indigo!");
		
		return liveUserFeedback;
	}

	private JPanel createOutputPanel() {
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setPreferredSize(new Dimension(500,150));
		bottomPanel.setOpaque(false);
		
		GridBagConstraints tabbedPaneConstraints;
		//floatingTasksConstraints =  gridBag.setConstraints(FLOATING_TASKS_INDEX);
		tabbedPaneConstraints =  gridBag.setConstraints(TABBED_PANE_INDEX);
		
		addTabbedPane(bottomPanel);
		bottomPanel.add(taskDisplay, tabbedPaneConstraints);	
		return bottomPanel; 
	
	}

	protected void addStylesToDocument(StyledDocument doc){
	
		
	}
	private TabbedPaneDisplay addTabbedPane(JPanel bottomPanel) {
		taskDisplay = new TabbedPaneDisplay();		
		return taskDisplay;
		
	}
	
	public String getReadInputText(){
		return readInput.getText();
		
	}
	public void readInputFocus(){
		readInput.requestFocusInWindow();
	}
	public void setReadInputText(String text){
		readInput.setText(text);	
	}
	public void selectAllReadInput(){
		readInput.selectAll();	
	}
	public void setUserFeedbackText(String text){
		liveUserFeedback.setText(text);	
	}

}
