import java.util.ArrayList;
import java.util.Scanner;

/**
 * This a main program of Indigo. Indigo is a software that can store, process
 * and display tasks on the desktop as a task manager. Indigo also supports
 * audio remainder, auto-correction, and color-coded highlighting. Indigo takes
 * input from keyboard only. Indigo stores data on a local disk.
 * 
 * @author jjlu
 *
 */
public class IndigoMain {

	private static final String FILE_NAME = "myTask";
	
	// Storage of our list of tasks
	private static ArrayList<Task> taskList = new ArrayList<Task>();

	public static void main(String[] args) {
		/*
		 * outline: 1.welcome message 
		 * 2.check if there is local disk storage
		 * -yes load data 
		 * -no display beginner tutorial message 3.asks for user
		 * command 
		 * 4.process user command 
		 * 5.repeating 3-4 until exit
		 */

		displayWelcomeMessage();

		while (true) {
			String userCommand = readCommand();
			
			IndigoUserInterface(executeCommand(userCommand)); 
			// TODO GUI for displaying system message after each operation.
		}
	}

	// TODO a simple input for testing
	private static Scanner scanner = new Scanner(System.in);
	
	// TODO GUI for user inputs
	private static String readCommand() {
		/*
		 * TODO 
		 * 1.read a command from user and process it 
		 * 2.execute Command
		 */
		String userCommand = scanner.nextLine(); // TODO simple input for testing
		return executeCommand(userCommand);
	}

	private static String executeCommand(String command) {
		/*
		 * TODO 
		 * 1.executeCommand 
		 * 2.save taskList
		 * 
		 * A standardized command should have String systemMessage returned.
		 */
		String returnMessage = new String();;
		return returnMessage + saveTaskList();
	}

	private static String saveTaskList() {
		//  save taskList into TEXT file
		return storeArrayList.saveFile(taskList, FILE_NAME);
	}

	private static String loadData() {
		// load data from the local disk into memory
		return storeArrayList.readFile(taskList, FILE_NAME);
	}


	private static void displayWelcomeMessage() {
		loadData();
		// TODO display welcomeMessage
	}

}
