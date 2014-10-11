import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

/** bugs to be fixed:
 * -undo command for (delete int) operation
 * -(delete someString) cause error
 * 
 *
 */

public class InputWindow  extends JFrame implements ActionListener{
	
	JPanel welcomeWindow = new JPanel(new BorderLayout());
	JTextField readInput = new JTextField();
	static JTextPane textPane = new JTextPane();
//	static JTextArea textArea = new JTextArea("",5, 20);
	
	public static String userCommand;
	public static final ArrayList<String> inputs = new ArrayList<String>();
	
	public static void main(String[] args){
		
		CreateWindow();
		
	}
	
	private static void CreateWindow() {
		inputs.add("call mom");
		inputs.add("catch a dog");
		inputs.add("catch a fish");
		
		JFrame welcome = new InputWindow();
		welcome.setBounds(400, 200, 450, 300);
		welcome.setVisible(true);
		welcome.setBackground(Color.white);
	}

	public InputWindow(){
		
		super("Indigo");
		setResizable(true);
		welcomeWindow.setBorder(new EmptyBorder(20,20,20,20));

		welcomeWindow.add(readInput, BorderLayout.SOUTH);
//		welcomeWindow.add(textArea, BorderLayout.CENTER);
		welcomeWindow.add(textPane, BorderLayout.CENTER);
		add(welcomeWindow);
		
		readInput.addActionListener(this);

		setVisible(true);
	}
	
	
	public static void showFeedback(IndigoMain logic){
//		textArea.setText(logic.dateLeft + "\n");
//		textArea.append(logic.feedback + "\n");
		textPane.setEditable(false);
		textPane.setText(logic.feedback + "\n");
	}
	public static void showTaskList(){
		for (int i=0; i < inputs.size(); i++){
			//textArea.append(inputs.get(i) + '\n');
			textPane.setText(inputs.get(i) + '\n');
		}
			
	}
	public static String getUserCommand(){
		return userCommand;
	}
	
	
	public void actionPerformed(ActionEvent evt) {
		 	String text = readInput.getText();
	        readInput.selectAll();
	//        setUserCommand(text);
	        //textArea.setText("");
	        textPane.setText("");
	        IndigoMain controller = new IndigoMain(text);
	        showFeedback(controller);
	//        showTaskList();
	        
	    }

	private void setUserCommand(String text) {
		userCommand = text;
		
	}

}
