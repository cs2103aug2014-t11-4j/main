import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
	private static final String MESSAGE_ERROR_FILE_NOT_FOUND = FILE_NAME
			+ " is not found!";
	
	// Storage of our list of tasks
	private static ArrayList<Task> taskList = new ArrayList<Task>();
	
	public enum Commands {
		ADD,DELETE,EDIT,DISPLAY,SAVE,EXIT
	}

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

		if (isDataPresent()) {
			loadData();
		} else {
			displayTutorialMessage();
		}

		while (true) {
			String userCommand = readCommand();
			
			System.out.println(userCommand); 
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
		//  save taskList into raw bytes
		if (taskList.size() > 0) {
			try {
				FileOutputStream fos = new FileOutputStream(FILE_NAME);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(taskList);
				oos.close();
				fos.close();
			} catch (IOException ioe) {
				return "ioe Exception.";
			}
			return FILE_NAME + "is saved!";
		}
		return "taskList is empty().";
	}

	private static String displayTutorialMessage() {
		// TODO ask user if want to enter tutorial mode
		return "Congragulations! You have completed tutorials.";
	}

	private static String loadData() {
		// load data from the local disk into memory
		try {
			FileInputStream fis = new FileInputStream(FILE_NAME);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object obj = ois.readObject();
			if (obj instanceof ArrayList<?>) {
				ArrayList<?> al = (ArrayList<?>) obj;
				if (al.size() > 0) {
					for (int i = 0; i < al.size(); i++) {
						Object o = al.get(i);
						if (o instanceof Task) {
							taskList.add((Task) o);
						}
					}

				}
				ois.close();
				fis.close();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return "ioe exception while loading.";
		} catch (ClassNotFoundException c) {
			System.out.println("ioe exception.");
			c.printStackTrace();
			return "ClassNotFoundException while loading.";
		}
		return FILE_NAME + " is loaded.";
	}

	private static boolean isDataPresent() {
		// check if there is data on the disk
		try {
			FileInputStream fis = new FileInputStream(FILE_NAME);
			fis.close();
		} catch (FileNotFoundException e) {
			System.out.println(MESSAGE_ERROR_FILE_NOT_FOUND);
			return false;
		} catch (IOException ioe) {
			System.out.println("ioe exception.");
			ioe.printStackTrace();
			return false;
		}
		return true;
	}

	private static void displayWelcomeMessage() {
		// TODO display welcomeMessage
	}

}
