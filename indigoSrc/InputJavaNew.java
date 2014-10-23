package indigoSrc;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.BorderLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/** bugs to be fixed:
 * -undo command for (delete int) operation
 * -(delete someString) cause error
 * 
 *
 */

public class InputJavaNew  extends JFrame{
	
	private JLayeredPane layerPane = new JLayeredPane();
	private JTextField readInput;
	private JTextPane taskDisplay;
	private JTextArea liveUserFeedback;
	
	private ArrayList<String> inputs = new ArrayList<String>();
	
	//public static String userCommand;
	//public static final ArrayList<String> inputs = new ArrayList<String>();
	
	@Override
	public void setVisible(boolean value)
	{
		super.setVisible(value);
		readInput.requestFocusInWindow();
	}
	
	
	public static void main(String[] args){
		

					InputJavaNew welcome = new InputJavaNew();
					welcome.setVisible(true);
					

	}
	
	//This will create the application
	public InputJavaNew(){
		super("Indigo");
		readInput = new JTextField();
		initialize(this);
		fillUpTheJFrame(this);
		
//		liveUserFeedback.setText("");
		
	}
	
	
	// The following method controls the setting of the main INDIGO frame. 
	
	private void initialize(InputJavaNew InputWindow){
		
		InputWindow.setTitle("INDIGO: \"To-Do to Done\"");
		InputWindow.setResizable(true);
		InputWindow.setBounds(200, 200, 700, 400);
		InputWindow.setVisible(true);
		InputWindow.setBackground(Color.white);
		InputWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	

	
	//Adding contents to the Jframe.
	
	private void fillUpTheJFrame(InputJavaNew InputWindow){
		
			Container contentPane = InputWindow.getContentPane();
			contentPane.add(layerPane);
			JPanel mainPanel = new JPanel();
			mainPanel.setBounds(0,0, 700, 400);
			mainPanel.setLayout(new GridBagLayout());
	
			
			createTaskDisplayArea(mainPanel);
			createBottonPanel(mainPanel);
			layerPane.add(mainPanel, new Integer(2));
			
	}

	//Adding task display area

	private void createTaskDisplayArea(Container mainPanel) {
		JPanel taskListDisplay = new JPanel(new GridBagLayout());
		taskListDisplay.setBackground(Color.WHITE);
		GridBagConstraints panelParameters; 
	
		taskListDisplay.setPreferredSize(new Dimension(640, 350));
		
		panelParameters = setParameters(1);
		Border liveUserFeedbackBorder = new LineBorder(Color.BLUE);
		taskDisplay = new JTextPane();
		taskDisplay.setMaximumSize(taskDisplay.getSize());
		taskDisplay.setBorder(liveUserFeedbackBorder);
		mainPanel.add(taskDisplay);

	}

	//adding bottom part with the input field and the user feedback.
	private void createBottonPanel(Container mainPanel){
		
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setBackground(Color.WHITE);
		bottomPanel.setPreferredSize(new Dimension(550,300));
		GridBagConstraints parameters;
		
		parameters = setParameters(2);
		
		createReadInputTextField(bottomPanel);
		createTextArea(bottomPanel);
		mainPanel.add(bottomPanel, parameters);
		bottomPanel.setOpaque(false);
	}
	
	//adding the input field.
	private void createReadInputTextField(JPanel bottomPanel){
		
		GridBagConstraints bottomPanelParameters;
		bottomPanelParameters = setParameters(3);
		readInput = new JTextField(20);
		bottomPanel.add(readInput,bottomPanelParameters);
	//	readInput.addActionListener(new  ReadInputTextFieldListener());
	}
	
	//adding the userfeedback box.
	private void createTextArea(JPanel bottomPanel) {
		GridBagConstraints liveUserFeedbackParameters;
		liveUserFeedbackParameters = setParameters(4);
		
		Border liveUserFeedbackBorder = new LineBorder(Color.BLUE);
		liveUserFeedback = new JTextArea(3,33);
		liveUserFeedback.setMaximumSize(liveUserFeedback.getSize());
		liveUserFeedback.setBorder(liveUserFeedbackBorder);
		liveUserFeedback.setLineWrap(true);
		bottomPanel.add(liveUserFeedback,liveUserFeedbackParameters);
		
	}
	
	

	//adding layout constraints
	private GridBagConstraints setParameters(int panelParameters) {
		GridBagConstraints parameters;
		Insets insets = new Insets(0,0,0,0);
		Insets taskDisplayInsets = new Insets(20,0,0,0);
		Insets ReadInputTextFieldInsets = new Insets(10,25,5,25);
		Insets liveUserFeedbackInsets = new Insets(10,25,20,20);
		
		
		if(panelParameters == 1){
			parameters = new GridBagConstraints(0,0,3,3,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,taskDisplayInsets,0,0);
			
			
			return parameters;
		}
		else if(panelParameters == 2){
			parameters = new GridBagConstraints(0,3,3,1,0.0,0.3,GridBagConstraints.CENTER,GridBagConstraints.BOTH,insets,0,0);
			
			return parameters;
		}
		
		else if(panelParameters == 3){
			parameters = new GridBagConstraints(0,0,3,1,0.1,0.0,GridBagConstraints.NORTHWEST,GridBagConstraints.BOTH,ReadInputTextFieldInsets,0,0);
			
			return parameters;
		}
		else if(panelParameters == 4){
			parameters = new GridBagConstraints(0,1,2,1,0.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,liveUserFeedbackInsets,0,0);
			return parameters;
		}
		
		return null;
	}


	
	public void showFeedback(IndigoLogic logic){

		taskDisplay.setEditable(false);
		taskDisplay.setText(logic.feedback + "\n");
	}
	public void showTaskList(){
		for (int i=0; i < inputs.size(); i++){
			taskDisplay.setText(inputs.get(i) + '\n');
		}
			
	}

	private class ReadInputTextFieldListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent evt){
			String text = readInput.getText();
	        readInput.selectAll();
	        taskDisplay.setText("");
	        IndigoLogic controller = new IndigoLogic(text);
	        showFeedback(controller);
			
		}
	}
	
	/* Old Code
	public void actionPerformed(ActionEvent evt) {
		 	String text = readInput.getText();
	        readInput.selectAll();
	        taskDisplay.setText("");
	        IndigoMain controller = new IndigoMain(text);
	        showFeedback(controller);

	    } */
//	public static String getUserCommand(){
//	return userCommand;
//}


//	private void setUserCommand(String text) {
//		userCommand = text;
		
//	}

}