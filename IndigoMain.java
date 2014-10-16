import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import org.joda.time.DateTime;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * This a main program of Indigo. Indigo is a software that can store, process
 * and display tasks on the desktop as a task manager. Indigo also supports
 * audio reminder, auto-correction, and color-coded highlighting. Indigo takes
 * input from keyboard only. Indigo stores data on a local disk.
 * 
 * @author jjlu & Ken
 *
 */
public class IndigoMain {
	public String feedback;
	public String dateLeft;
	private static ParserList ps = new ParserList();
	private static Parser parser;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yy");
	private static final String FILE_NAME = "myTask";
	
	// Storage of our list of tasks
	private static TaskList taskList = new TaskList();
	
	public enum COMMAND{
		CREATE, READ, UPDATE, DELETE, UNDO, COMPLETE, UNCOMPLETE
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
		IndigoMain test1 = new IndigoMain("add go to school");
		IndigoMain test2 = new IndigoMain("add buy a fish");
		IndigoMain test3 = new IndigoMain("add do revision");
		System.out.println("test1: " + test1.feedback);
		System.out.println("test2: " + test2.feedback);
		System.out.println("test3: " + test3.feedback);
	}
	
	public IndigoMain(String userCommand){
		loadData();
		feedback = readCommand(userCommand);
//		Date dateCurrent = new Date();
//		DateFormat dateForm = DateFormat.getDateInstance();
//		dateLeft = dateForm.format(dateCurrent);
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
				return read(parser.getCommand());
			case UPDATE:
				update(parser.getEditIndex(), parser.getCommand());
				break;
			case DELETE:
				String type = new String();
				if (parser.getCommand().contains("-d")){
					type = "-d";
				} else if (parser.getCommand().contains("-t")){
					type = "-t";
				} else {
					type = "-f";
				}
				delete(type, parser.getDelIndex());
				break;
			case UNDO:
				undo();
				break;
			case COMPLETE:
				complete(parser.getEditIndex());
				break;
			default:
				System.exit(0);
		}
		return returnMessage + saveTaskList();
	}
	
	private static void complete(int index) {
		ps.push(new Parser("UnComplete " + index));
		taskList.complete(index);
	}

	private static COMMAND getCommand(String hello){

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
		FloatingTask tt = new FloatingTask(parser.getCommand());
		int[] dateA = parser.getDate();
		System.out.print(dateA[2] + dateA[1] + dateA[0]);
		if (dateA!=null){
			DeadlineTask dt = new DeadlineTask(tt.getDescription(), new DateTime(dateA[2], dateA[1], dateA[0], 12, 00, 00));			
			taskList.addTask(dt);
			ps.push(new Parser("delete " + taskList.getList().size()));
		}
		if (parser.getEditIndex() == null){
			ps.push(new Parser("delete " + taskList.getList().size()));
			taskList.addTask(tt);
		} else {
			taskList.addTask(parser.getEditIndex(),tt);
			ps.push(new Parser("delete "+ parser.getEditIndex()));
		}
		//taskList.sort();
	}
	
	private static String read(String command){
		if (command.contains("-done")){
			return viewDone();
		} else if (command.contains("-undone")){
			return viewUndone();
		}
		return saveTaskList();
	}
	
	private static void update(int index, String task){
		ps.push(new Parser("edit "+ index + " " + taskList.get(index).getDescription()));
		FloatingTask tt = new FloatingTask(task);
		taskList.editTask(index, tt);
	}
	
	private static void delete(String type, int index){
		if(type.equals("-d")){
			ArrayList<FloatingTask> arr = taskList.getList();
			int i=0;
			while(!(arr.get(i) instanceof DeadlineTask)){
				i++;
			}
			index += i;
		} else if(type.equals("-t")){
			ArrayList<FloatingTask> arr = taskList.getList();
			int i=0;
			while(!(arr.get(i) instanceof TimedTask)){
				i++;
			}
			index += i;
		}
		ps.push(new Parser("add " + index + " " + taskList.get(index).getDescription()));
		taskList.deleteTask(index);
	}

	private static void undo(){
		if (ps.isUndoAble() == false){
			return;
		}
		Parser commandPre = ps.undo();
		COMMAND comm = getCommand(commandPre.getKeyCommand());
		switch (comm){
			case CREATE:
				taskList.addTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				break;
			case READ:
				break;
			case UPDATE:
				taskList.editTask(commandPre.getEditIndex(), new FloatingTask(commandPre.getCommand()));
				break;
			case DELETE:
				taskList.deleteTask(commandPre.getDelIndex());
				break;
			case UNDO:
				undo();
				break;
			case UNCOMPLETE:
				taskList.get(commandPre.getEditIndex()).unComplete();
				break;
			default:
				;
		}
	}

	private static String saveTaskList() {
		//  save taskList into TEXT file
		return taskList.write(FILE_NAME, DATE_FORMAT);
	}

	private static String loadData() {
		// load data from the local disk into memory
		return taskList.read(FILE_NAME, DATE_FORMAT);
	}
	
	public static String viewDone(){
		return taskList.viewDone();
	}
	
	public static String viewUndone(){
		return taskList.viewUndone();
	}

}
