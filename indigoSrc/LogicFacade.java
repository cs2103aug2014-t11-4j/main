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
	public String display;
	public String feedback;
	private static UndoList uList = new UndoList();;
	private static Parser parser;
	public static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("dd/MM/yyyy, HH:mm");
	public static final DateTimeFormatter TIME_FORMAT = DateTimeFormat.forPattern("HH:mm");
	public static final String FILE_NAME = "myTask";
	private static TaskList taskList = new TaskList();
	public int setTab = 0;
	
	//Default constructor
	public LogicFacade(){
		this("view");
	}
	
	public LogicFacade(String userCommand){
		loadData();
		String userInput = userCommand;
		Parser parse = new Parser(userInput);
		parse.getKeyCommand();
		if(parse.isValid()==false){
			feedback = "Invalid input";
		}else{
			feedback = readCommand(userInput);
		}
		//System.out.println(display);
		if (display==null){
			display = new Read(taskList).resultString;
		}
		
		/*if(!(now.equals(CommandKey.CREATE)) || (now.equals(CommandKey.SEARCH))){
			Read rc = new Read(taskList);
		}*/
		saveData();
	}

	private String readCommand(String userCommand) {
		/*
		 * TODO 
		 * 1.read a command from user and process it 
		 * 2.execute Command
		 */ // TODO simple input for testing
		parser = new Parser(userCommand);
		return executeCommand(parser.getKeyCommand());
	}

	private String executeCommand(CommandKey commandKey) {
		/*
		 * TODO 
		 * 1.executeCommand 
		 * 2.save taskList
		 * 
		 * A standardized command should have String systemMessage returned.
		 */
		switch (commandKey){
			case CREATE:
				Create classAdd = new Create(parser, taskList);
				if(classAdd.isValid){
					uList.push(classAdd);
				}
				return classAdd.execute();
			case READ:
				Read classView = new Read(parser, taskList);
				setTab = classView.tabNo;
				display = classView.resultString;
				return classView.feedback;
			case UPDATE:
				Update classEdit = new Update(parser, taskList);
				if(classEdit.isValid){
					uList.push(classEdit);
				}
				return classEdit.execute();
			case DELETE:
				Delete classDelete = new Delete(parser, taskList);
				if(classDelete.isValid){
					uList.push(classDelete);
				}
				return classDelete.execute();
			case UNDO:
				if(parser.taskWord.equals(TaskIdentifiers.ALL)){
					int count = 0;
					while(uList.isUndoAble()){
						undoFunction();
						count++;
					}
					return printMoves(count, "Undo");
				} else if (parser.getEditIndex()>0){
					int index = parser.getEditIndex();
					int count = 0; 
					while(index > 0 && uList.isUndoAble()){
						undoFunction();
						index--;
						count++;
					}
					return printMoves(count, "Undo");
				} else {
					return printMoves(0, "Undo");
				}
			case REDO:
				if(parser.taskWord.equals(TaskIdentifiers.ALL)){
					int count = 0;
					while(uList.isRedoAble()){
						redoFunction();
						count++;
					}
					return printMoves(count, "Redo");
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
					uList.push(classCheck);
				}
				return classCheck.execute();
			case UNCOMPLETE:
				int indexU = parser.getEditIndex();
				taskList.get(indexU).unComplete();
				return "Task marked as uncomplete";	
			case SEARCH:
				System.out.println("come to me");
				Search classSearch = new Search(parser, taskList);
				String result = classSearch.execute();
				display = classSearch.searchResult;
				return result;
			case CLEAR:
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
