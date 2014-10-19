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

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.awt.event.ActionEvent;

public class InputWindow extends JFrame {
	
	private JLayeredPane displayLayers = new JLayeredPane();
	private JTextField readInput;
	private JTextArea liveUserFeedback;
	
	@Override
	public void setVisible(boolean value){
		super.setVisible(value);
	}
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				try {
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
		fillUpTheMainWindow(this);
	}

	public void initialize(JFrame mainWindow){	
		mainWindow.setBounds(300, 150, 700, 400);
		mainWindow.setResizable(false);
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void fillUpTheMainWindow(JFrame mainWindow){
		Container contentPane = mainWindow.getContentPane();
		contentPane.add(displayLayers);
		JPanel mainPanel = new JPanel();
		mainPanel.setBounds(0, 0, 700, 400);
		mainPanel.setLayout(new GridBagLayout());
		
		
		createUserInputPanel(mainPanel);
		createTabbedOutputPanel(mainPanel);
		
		displayLayers.add(mainPanel); 
	}
	
	private void createTabbedOutputPanel(Container mainPanel) {
		JPanel topPanel = new JPanel(new GridBagLayout());
		topPanel.setPreferredSize(new Dimension(540,225));
		GridBagConstraints constraints;
		constraints = setConstraints(4);
		
		mainPanel.add(new TabbedPaneDisplay(), constraints);
		
		
	}

	private void createUserInputPanel(Container mainPanel){
		JPanel bottomPanel = new JPanel(new GridBagLayout());
		bottomPanel.setPreferredSize(new Dimension(600,100));
		GridBagConstraints constraints;
		
		constraints = setConstraints(1);
		
		addReadInput(bottomPanel);
		addLiveUserFeedback(bottomPanel);
		mainPanel.add(bottomPanel, constraints);
		
		
<<<<<<< HEAD
		readInput.addActionListener(this);

		setVisible(true);
	}
	
	
	public static void showFeedback(IndigoLogic logic){
//		textArea.setText(logic.dateLeft + "\n");
//		textArea.append(logic.feedback + "\n");
		textPane.setEditable(false);
		textPane.setText(logic.feedback + "\n");
=======
>>>>>>> 90a2dd3a2abb1446c0ea9377a8c04d7d9fba36bc
	}


	private void addReadInput(JPanel bottomPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(2);
		JTextField readInput = new JTextField();
		bottomPanel.add(readInput, constraints);	
		readInput.addActionListener(new readInputTextFieldListener());
		
	}
	private void addLiveUserFeedback(JPanel bottomPanel) {
		GridBagConstraints constraints;
		constraints = setConstraints(3);
		liveUserFeedback = new JTextArea(3,3);
		Border liveUserFeedbackBorder = new LineBorder(Color.white);
		liveUserFeedback.setMaximumSize(liveUserFeedback.getSize());
		liveUserFeedback.setBorder(liveUserFeedbackBorder);
		liveUserFeedback.setLineWrap(true);
		bottomPanel.add(liveUserFeedback, constraints);
		
		
	}
	
<<<<<<< HEAD
	
	public void actionPerformed(ActionEvent evt) {
		 	String text = readInput.getText();
	        readInput.selectAll();
	        setUserCommand(text);
	        //textArea.setText("");
	        textPane.setText("");
	        IndigoLogic controller = new IndigoLogic(text);
	        showFeedback(controller);
	//        showTaskList();
	        
	    }
=======
>>>>>>> 90a2dd3a2abb1446c0ea9377a8c04d7d9fba36bc

public class readInputTextFieldListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		
		String input = readInput.getText();
        readInput.selectAll();

        liveUserFeedback.setText("HELLO");
	}

}


	private GridBagConstraints setConstraints(int componentIndex) {
		GridBagConstraints constraints;
		Insets bottomPanel = new Insets(0,0,0,0);
		Insets tabbedPaneDisplayInsets = new Insets(5,20,0,20);
		Insets readInputInsets = new Insets(5,20,5,20);
		Insets liveUserFeedbackInsets = new Insets(5,20,30,20);
		
		if(componentIndex == 1){
			constraints = new GridBagConstraints(0,3,3,1,0.0,0.0,GridBagConstraints.CENTER,GridBagConstraints.BOTH,bottomPanel,0,0);
			return constraints;
		}
		else if(componentIndex == 2){
			constraints = new GridBagConstraints(0,2,3,1,0.1,0.0,GridBagConstraints.NORTH,GridBagConstraints.BOTH,readInputInsets,0,0);
			
			return constraints;
		}
		else if(componentIndex == 3){
			constraints = new GridBagConstraints(0,3,3,1,0.0,0.1,GridBagConstraints.CENTER,GridBagConstraints.BOTH,liveUserFeedbackInsets,0,0);
			return constraints;
		}
		else if(componentIndex == 4){
			constraints = new GridBagConstraints(0,0,3,3,0.1,0.0,GridBagConstraints.NORTH,GridBagConstraints.BOTH,tabbedPaneDisplayInsets,0,0);
			return constraints;
		}
		return null;
	}
}

