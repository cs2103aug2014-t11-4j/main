package indigoSrc;
import java.util.ArrayList;

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
	public String display;
	public String feedback;
	private static ParserList ps = new ParserList();;
	private static Parser parser;
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yy, HH:mm");
	public static final String FILE_NAME = "myTask";
	private static TaskList taskList = new TaskList();
	
	//Default constructor
	public IndigoLogic(){
		this("view");
	}
	
	public IndigoLogic(String userInput){
		loadData();
		feedback = readCommand(userInput);
		Read rc = new Read(parser, taskList);
		if(!userInput.contains("view")){
			display = rc.view();
		} else {
			Parser p = new Parser(userInput);
			rc = new Read(p, taskList);
			rc.execute();
			display = rc.resulting;
		}
		saveData();
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
				return classAdd.execute();
			case READ:
				Read classView = new Read(parser, taskList);
				if(parser.getCommand().equals("view")){
					return classView.viewToday();
				}
				return classView.execute();
			case UPDATE:
				Update classEdit = new Update(parser, ps, taskList);
				return classEdit.execute();
			case DELETE:
				Delete classDelete = new Delete(parser, ps, taskList);
				return classDelete.execute();
			case UNDO:
				Undo classUndo = new Undo(parser, ps, taskList);
				return classUndo.execute();
			case REDO:
				Undo classRedo = new Undo(parser, ps, taskList);
				return classRedo.rexecute();
			case COMPLETE:
				int indexC = parser.getEditIndex();
				taskList.get(indexC).complete();
				return "Task marked as complete";
			case UNCOMPLETE:
				int indexU = parser.getEditIndex();
				taskList.get(indexU).unComplete();
				return "Task marked as uncoplete";	
			case SEARCH:
				String keyWords = parser.getCommand();
				ArrayList<Integer> indices = new ArrayList<Integer>();
				indices = taskList.search(keyWords);
				Read classView2 = new Read(parser, taskList);
				display = classView2.viewSearch(indices);
				return "Search has found";
			default:
				System.exit(0);
		}
		return "Saved";
	}
/*	
	private static void complete(int index) {
		ps.push(new Parser("UnComplete " + index));
		taskList.complete(index);
	}

*/
	private static void saveData() {
		//  save taskList into TEXT file
		taskList.writeXMLDocument(FILE_NAME);
	}

	private static String loadData() {
		// load data from the local disk into memory
		return taskList.readFromXML(FILE_NAME);
	}


}
