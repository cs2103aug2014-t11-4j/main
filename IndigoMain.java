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
	
	//Enum class for commands
	public enum COMMAND{
		CREATE, READ, UPDATE, DELETE, UNDO
	}

	public static void main(String[] args) {
		/*
		 * outline: 
		 * 1.welcome message 
		 * 2.check if there is local disk storage
		 * -yes load data 
		 * -no display beginner tutorial message 
		 * 3.asks for user command 
		 * 4.process user command 
		 * 5.repeating 3-4 until exit
		 */
		displayWelcomeMessage();

		while (true) {
			//String userCommand = readCommand();
			
	//	InputWindow.getUserCommand(); 
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
		COMMAND comm = changecomm(command);
		switch (comm){
			case CREATE:
				create();
				break;
			case READ:
				read();
				break;
			case UPDATE:
				update();
				break;
			case DELETE:
				delete();
				break;
			case UNDO:
				undo();
				break;
			default:
				System.exit(0);
		}
		return returnMessage + saveTaskList();
	}
	
	private static COMMAND changecomm(String hello){
		switch (hello){
			case "add":
				return COMMAND.CREATE;
			case "view":
				return COMMAND.READ;
			case "edit":
				return COMMAND.UPDATE;
			case "delete":
				return COMMAND.DELETE;
			case "undo":
				return COMMAND.UNDO;
			default:
				return COMMAND.READ;
		}
	}
	
	private static void create(){
		Task tt = new Task();
		taskList.add(tt);
	}
	
	private static void read(){
		for(int i = 0; i < taskList.size(); i++){
			System.out.println(taskList.get(i));
		}
	}
	
	private static void update(){
		Task tt = new Task();
		int i = 9;
		taskList.set(i, tt);
	}
	
	private static void delete(){
		int i = 9;
		taskList.remove(i);
	}
	
	private static void undo(){
		ParserList ps = new ParserList();
		ps.undo();
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
