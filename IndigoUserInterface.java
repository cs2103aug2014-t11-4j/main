import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class IndigoUserInterface  extends JFrame implements ActionListener{
	
	JPanel welcomeWindow = new JPanel(new BorderLayout());
	JTextField userCommand = new JTextField();
	JTextArea textArea = new JTextArea(5, 20);
	
	public static void main(String[] args){
		JFrame welcome = new IndigoUserInterface();
		welcome.setBounds(400, 200, 450, 300);
		welcome.setVisible(true);
		welcome.setBackground(Color.white);
	}
	
	public IndigoUserInterface(){
		super("Indigo");
		setResizable(true);
		welcomeWindow.setBorder(new EmptyBorder(20,20,20,20));

	
		welcomeWindow.add(userCommand, BorderLayout.SOUTH);
		welcomeWindow.add(textArea, BorderLayout.CENTER);
		welcomeWindow.add(new TabbedPaneDemo(), BorderLayout.NORTH);
		add(welcomeWindow);
		
	//	textArea.setBorder(new EmptyBorder(20,20,20,20));
	//	userCommand.setBorder(new EmptyBorder(5,5,5,5));
		
		userCommand.addActionListener(this);
		
		String text = userCommand.getText();
		textArea.append(text);
		
		setVisible(true);
	}
	 public void actionPerformed(ActionEvent evt) {
		 	String text = userCommand.getText();
			textArea.append(text + "\n");
	        userCommand.selectAll();

	        //Make sure the new text is visible, even if there
	        //was a selection in the text area.
	   //   textArea.setCaretPosition(textArea.getDocument().getLength());
	    }

}
