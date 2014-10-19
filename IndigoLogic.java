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
			case UNDO:
				Undo classUndo = new Undo(parser, ps, taskList);
				return classUndo.execute();
				/*
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

*/
}
