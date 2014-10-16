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

public class IndigoLogic {
	public String feedback;
	public String dateLeft;
	private static ParserList ps = new ParserList();
	private static Parser parser;
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yy");
	private static final String FILE_NAME = "myTask";
	private static TaskList taskList = new TaskList();
	
	//Default constructor
	public IndigoLogic(){
		this("view");
	}
	
	public IndigoLogic(String userInput){
		//loadData();
		feedback = readCommand(userInput);
	}
	
	private String readCommand(String userCommand) {
		/*
		 * TODO 
		 * 1.read a command from user and process it 
		 * 2.execute Command
		 */ // TODO simple input for testing
		parser = new Parser(userCommand);
		Command commandInput = new Command(parser.getKeyCommand());

		return executeCommand(commandInput);
	}

	private String executeCommand(Command commandInput) {
		/*
		 * TODO 
		 * 1.executeCommand 
		 * 2.save taskList
		 * 
		 * A standardized command should have String systemMessage returned.
		 */
		switch (commandInput.getKey()){
			case CREATE:
				Create classAdd = new Create(parser, ps, taskList);
				return classAdd.add();
			case READ:
				Read classView = new Read(parser, ps, taskList);
				if(!classView.view().equals("view all")){
					assert !(classView.view().equals("view all"));
				}{	return classView.view();
				}
			case UPDATE:
				Update classEdit = new Update(parser, ps, taskList);
				return classEdit.edit();
			case DELETE:
				Delete classDelete = new Delete(parser, ps, taskList);
				return classDelete.delete();
/*			case UNDO:
				undo();
				break;
			case COMPLETE:
				complete(parser.getEditIndex());
				break; */
			default:
				System.exit(0);
		}
		return  saveTaskList();
	}
/*	
	private static void complete(int index) {
		ps.push(new Parser("UnComplete " + index));
		taskList.complete(index);
	}
	
	private static void create(){
		Create classy = new Create()
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
	
	private static void delete(int index){
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
*/
	private static String saveTaskList() {
		//  save taskList into TEXT file
		return taskList.write(FILE_NAME, DATE_FORMAT);
	}
/*
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
*/
}
