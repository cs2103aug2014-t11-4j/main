import java.util.ArrayList;
import java.util.Date;

/**
 * This a main program of Indigo. Indigo is a software that can store, process
 * and display tasks on the desktop as a task manager. Indigo also supports
 * audio reminder, auto-correction, and color-coded highlighting. Indigo takes
 * input from keyboard only. Indigo stores data on a local disk.
 * 
 * @author jjlu
 *
 */
public class IndigoMain {
	public String feedback;
	
	public String dateLeft;
	
	private static ParserList ps = new ParserList();
	
	private static Parser parser;

	private static final String FILE_NAME = "myTask";
	
	// Storage of our list of tasks
	private static ArrayList<Task> taskList = new ArrayList<Task>();
	
	//Enum class for commands
	public enum COMMAND{
		CREATE, READ, UPDATE, DELETE, UNDO, COMPLETE
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
	
	public IndigoMain(String userCommand){
		if (taskList.isEmpty()){
			loadData();
		}
		feedback = readCommand(userCommand);
		Date dateCurrent = new Date();
		dateLeft = dateCurrent.toString();
	}
	
	// TODO GUI for user inputs
	private static String readCommand(String userCommand) {
		/*
		 * TODO 
		 * 1.read a command from user and process it 
		 * 2.execute Command
		 */ // TODO simple input for testing
		parser = new Parser(userCommand);
		return executeCommand(parser.getKeyCommand());
	}

	private static String executeCommand(String command) {
		/*
		 * TODO 
		 * 1.executeCommand 
		 * 2.save taskList
		 * 
		 * A standardized command should have String systemMessage returned.
		 */
		String returnMessage = new String();
		COMMAND comm = getCommand(command);
		switch (comm){
			case CREATE:
				create();
				break;
			case READ:
				read();
				break;
			case UPDATE:
				update(parser.getEditIndex(), parser.getCommand());
				break;
			case DELETE:
				delete(parser.getDelIndex());
				break;
			case UNDO:
				undo();
				break;
			case COMPLETE:
				complete(parser.getEditIndex());
			default:
				System.exit(0);
		}
		return returnMessage + saveTaskList();
	}
	
<<<<<<< HEAD
	private static COMMAND getCommand(String hello){
=======
	private static void complete(int index) {
		ps.push(new Parser("UnComplete " + index));
		taskList.get(index).complete();
	}

	private static COMMAND changecomm(String hello){
>>>>>>> origin/Floating-Task
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
			case "complete":
				return COMMAND.COMPLETE;
			default:
				return COMMAND.READ;
		}
	}
	
	private static void create(){
		ps.push(new Parser("delete 0"));
		Task tt = new Task(parser.getCommand());
		taskList.add(tt);
	}
	
	private static void read(){
	}
	
	private static void update(int index, String task){
		ps.push(new Parser("edit "+ index + " " + taskList.get(index).getDescription()));
		Task tt = new Task(task);
		taskList.set(index, tt);
	}
	
	private static void delete(int index){
		System.out.println(taskList.get(index).getDescription());
		ps.push(new Parser("add " + taskList.get(index).getDescription()));
		taskList.remove(index);
	}

	private static void undo(){
		if (ps.isUndoAble() == false){
			return;
		}
		Parser commandPre = ps.undo();
		COMMAND comm = getCommand(commandPre.getKeyCommand());
		switch (comm){
			case CREATE:
				taskList.add(new Task(commandPre.getCommand()));
				break;
			case READ:
				break;
			case UPDATE:
				taskList.set(commandPre.getEditIndex(), new Task(commandPre.getCommand()));
				break;
			case DELETE:
				taskList.remove(taskList.size()-1);
				break;
			case UNDO:
				undo();
				break;
			default:
				;
		}
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
	
	public static String viewDone(){
		StringBuilder str = new StringBuilder("Tasks Completed: \n");
		for (int i=0,j=1; i<taskList.size();i++){
			if (taskList.get(i).isCompleted()){
				str.append( j + ". " + taskList.get(i).getDescription() + "\n");
			}
		}
		return str.toString();
	}
	
	public static String viewUndone(){
		StringBuilder str = new StringBuilder("Tasks Due: \n");
		for (int i=0,j=1; i<taskList.size();i++){
			if (!taskList.get(i).isCompleted()){
				str.append( j + ". " + taskList.get(i).getDescription() + "\n");
			}
		}
		return str.toString();
	}

}
