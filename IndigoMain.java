import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/** This a main program of Indigo.
 *  Indigo is a software that can store, process and display tasks on the desktop as a task manager.
 *  Indigo also supports audio remainder, auto-correction, and color-coded highlighting.
 *  Indigo takes input from keyboard only.
 *  Indigo stores data on a local disk.
 * @author jjlu
 *
 */
public class IndigoMain {
	
	private static final String FILE_NAME = "myTask";
	private static final String MESSAGE_ERROR_FILE_NOT_FOUND = FILE_NAME + " is not found!";
	// Storage of our list of tasks
	private static ArrayList<Task> taskList = new ArrayList<Task>();

	public static void main(String[] args) {
		/* outline:
		 * 1.welcome message
		 * 2.check if there is local disk storage
		 * -yes load data
		 * -no display beginner tutorial message
		 * 3.asks for user command
		 * 4.process user command
		 * 5.repeating 3-4 until exit
		 */

		displayWelcomeMessage();
		
		if (isDataPresent()){
			loadData();
		} else {
			displayTutorialMessage();
		}
		
		while (true){
			readCommand();
		} 
	}

	private static void readCommand() {
		/* TODO read a command from user and process it
		 * parser
		 * execute Command
		 */		
	}

	private static void displayTutorialMessage() {
		// TODO ask user if want to enter tutorial mode
		
	}

	private static void loadData() {
		// TODO load data from the local disk into memory
		try{
			FileInputStream fis = new FileInputStream(FILE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			if (obj instanceof ArrayList<?>){
				ArrayList<?> al = (ArrayList<?>) obj;
			if (al.size() > 0){
				for (int i = 0; i < al.size(); i++){
					Object o = al.get(i);
					if (o instanceof Task){
						taskList.add((Task) o);
					}
				}
			
			}
			ois.close();
			fis.close();
			}
		} catch (IOException ioe){
			ioe.printStackTrace();
			return;
		} catch (ClassNotFoundException c){
			System.out.println("ioe exception");
			c.printStackTrace();
			return;
		}
	
	}

	private static boolean isDataPresent() {
		// TODO check if there is data on the disk
		try {
			FileInputStream fis = new FileInputStream(FILE_NAME);
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.println(MESSAGE_ERROR_FILE_NOT_FOUND);
			return false;
		} catch (IOException ioe){
			System.out.println("ioe exception");
			ioe.printStackTrace();
			return false;
		}
		return true;
	}

	private static void displayWelcomeMessage() {
		// TODO display welcomeMessage
		
	}

}
