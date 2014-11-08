package indigoSrc;
import logic.Complete;
import logic.Create;
import logic.Delete;
import logic.Read;
import logic.Search;
import logic.UndoList;
import logic.Update;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import parser.CommandKey;
import parser.TaskIdentifiers;

/**
 * This a main Logic of Indigo. Indigo is a software that can store, process
 * and display tasks on the desktop as a task manager. Indigo also supports
 * audio reminder, auto-correction, and color-coded highlighting. Indigo takes
 * input from keyboard only. Indigo stores data on a local disk.
 * 
 * This class acts like a facade pattern that the GUI will call. This facade then calls 
 * the other classes of logic and components like storage and parser to execute a task.
 * 
 * @author jjlu & KenHua
 *
 */

public class LogicFacade {
	public String display;			//The one which is displayed on the tabbed pane.
	public String feedback;			//The one which is displayed below the user input line
	private static UndoList uList = new UndoList();;	//The invoker for the command class pattern
	private static Parser parser;	
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy, HH:mm");
	public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm");
	public static final String FILE_NAME = "myTask";	//By default. Only can save here.
	private static TaskList taskList = new TaskList();	
	public int setTab = 0;			//The tab number indicator on tabbedpane display
	
	//Default constructor
	public LogicFacade(){
		this("view");
	}
	
	public LogicFacade(String userInput){
		loadData();
		Parser parseString = new Parser(userInput);
		parseString.getKeyCommand();
		if(parseString.isValid()==false){
			feedback = "Invalid input";
		}else{
			feedback = readCommand(parseString);
		}
		if (display==null){
			display = new Read(taskList).resultString;
		}
		saveData();
	}

	//Reads in a command.
	private String readCommand(Parser parseString) {
		return executeCommand(parseString.getKeyCommand());
	}
	
	//Execute the task.
	private String executeCommand(CommandKey commandKey) {
		switch (commandKey){
			case CREATE:
				Create classAdd = new Create(parser, taskList);
				if(classAdd.isValid){
					uList.push(classAdd);		//Push into the invoker for undo
				}
				return classAdd.execute();
			case READ:
				Read classView = new Read(parser, taskList);
				setTab = classView.tabNo;		//Tells which tab to display on GUI
				display = classView.resultString;
				return classView.feedback;
			case UPDATE:
				Update classEdit = new Update(parser, taskList);
				if(classEdit.isValid){
					uList.push(classEdit);		//Push into the invoker for undo
				}
				return classEdit.execute();
			case DELETE:
				Delete classDelete = new Delete(parser, taskList);
				if(classDelete.isValid){
					uList.push(classDelete);	//Push into the invoker for undo
				}
				return classDelete.execute();
			case UNDO:
				//This is for multiple undo on specific types of tasks
				if(parser.taskWord.equals(TaskIdentifiers.ALL)){
					int count = 0;
					while(uList.isUndoAble()){
						undoFunction();
						count++;
					}
					return printMoves(count, "Undo");
				//This is for mutliple undo.
				} else if (parser.getEditIndex()>0){
					int index = parser.getEditIndex();
					int count = 0; 
					while(index > 0 && uList.isUndoAble()){
						undoFunction();
						index--;
						count++;
					}
					return printMoves(count, "Undo");
				//This is the default value=0;
				} else {
					return printMoves(0, "Undo");
				}
			case REDO:
				//This is for multiple undo on specific types of tasks
				if(parser.taskWord.equals(TaskIdentifiers.ALL)){
					int count = 0;
					while(uList.isRedoAble()){
						redoFunction();
						count++;
					}
					return printMoves(count, "Redo");
				//This is for mutliple indices.
				}else if(parser.getEditIndex()>0){
					int index = parser.getEditIndex();
					int count = 0; 
					while(index > 0 && uList.isRedoAble()){
						redoFunction();
						index--;
						count++;
					}
					return printMoves(count, "Redo");
				}else {
					return printMoves(0, "Redo");
				}
			case COMPLETE:
				Complete classCheck = new Complete(parser, taskList, true);
				if(classCheck.isValid){
					uList.push(classCheck);		//Push into the invoker for undo
				}
				return classCheck.execute();
			case UNCOMPLETE:
				Complete classUnCheck = new Complete(parser, taskList, false);
				if(classUnCheck.isValid){
					uList.push(classUnCheck);	//Push into the invoker for undo
				}
				return classUnCheck.execute(); 
			case SEARCH:
				//searches for keywords only. Not for time. 
				Search classSearch = new Search(parser, taskList);
				String result = classSearch.execute();
				display = classSearch.searchResult;
				return result;
			case CLEAR:
				//By default, this will clear all 
				return taskList.clear();
			default:
				System.exit(0);
		}
		return "Saved";
	}

	public void redoFunction() {
		if(uList.isRedoAble()){
			uList.redo().execute();
		}
	}

	public void undoFunction() {
		if(uList.isUndoAble()){
			uList.undo().undo();
		}
	}
	
	public String printMoves(int count, String word){
		assert count >= 0;
		if(count == 0){
			return "There's nothing to " + word.toLowerCase();
		} else {
			return word + " " + count + " time(s).";
		}
	}

	private static void saveData() {
		//  save taskList into TEXT file
		taskList.writeXMLDocument(FILE_NAME);
	}

	private static String loadData() {
		// load data from the local disk into memory
		return taskList.readFromXML(FILE_NAME);
	}

	public TaskList getTasks() {
		return taskList;
	}
}
