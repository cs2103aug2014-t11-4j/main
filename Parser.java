/** this class is meant to read in the command line from the user in the form of a string
 * and parse it so that the logic can access it simply. 
 * currently implemented: 
 * add function: add buy a fish by OR on 23 dec 2014 OR /23/12/2014
 * delete function: delete 3 (integer)
 * edit: edit 3 buy a cat instead (integer + command)
 * undo: does nothing but return keycommand
 * @author Joanna
 *
 */
import java.util.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

import org.joda.time.DateTime;

public class Parser {
	private static Logger logger = Logger.getLogger("Parser");
	private String sortedCommand;
	String keyWord  			= null;  				//stores the key command "add"/"delete" to return to logic
	String commandWords  		= null; 				//stores the remaining words excluding key command
	String [] commandSentence 	= new String[2]; 		//to help store the splited string command
	String [] details 		  	= null; 				//store the remaining words excluding key command individually
	String toDo               	= "";//stores the final command to return to logic
	private DateTime startTime;
	private DateTime endTime;
	boolean containConj 		= false;				//determine if it is a floating task
	int editIndex;
	private boolean isFloatingTask;
	private boolean isDeadLineTask;
	private boolean isTimedTask;
	ArrayList<String> detailsList = new ArrayList<String>();
	private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());

	
	public static void main(String args[]) {
		ConsoleHandler handler = new ConsoleHandler();
		LOGGER.setLevel(Level.FINER);
		handler.setLevel(Level.FINER);
		LOGGER.addHandler(handler);
		
		String testInput;
		System.out.println("Enter command:");
		Scanner sc = new Scanner(System.in);
		testInput = sc.nextLine();
		
		Parser test = new Parser(testInput);
		sc.close(); 
	}
	
	public String getCommand() {
		toDo = toDo.trim();
		return toDo;
	}

	public Parser(String userCommand) {
		
		//conjWords.add("by"); 
		//conjWords.add("on"); 							// words to filter out dates
		editIndex = -1;
		
		
		if (userCommand.contains(" ")){
			commandSentence = userCommand.split(" ", 2);
			keyWord = commandSentence[0];
			commandWords = commandSentence[1];
			
			switch(keyWord) { 
			case "add":// for instance add buy a cat on 23/12/2014
			case "delete":
			case "edit" :
			case "complete":
			case "uncomplete":
			case "view":
				details = commandWords.split(" ");
				LOGGER.log(Level.FINE, "commandWords: " + commandWords);
				try{
					 editIndex = Integer.parseInt(details[0]);
					 }
				 catch(NumberFormatException er)
				  {
					 System.out.println("exception for editIndex.");
				  }
				 for(int i=0; i<details.length; i++) 
					detailsList.add(details[i]);
				 
 					//floating task
					 int b; 									// to see if there is a index

					 if (editIndex >= 0){
						 b=1;
					 } else {
						 b=0;
					 }
					 for(; b<details.length; b++) { 
						toDo = toDo+ " " + details[b]; 
					}
			LOGGER.log(Level.FINE, "toDo: " + toDo);
			LOGGER.log(Level.FINE, "editIndex " + editIndex);
			break;
			
			case "undo" :
				break;
				
			default:
				keyWord = "view";
			} 
		}	else {
			assert editIndex < 0;
			switch (userCommand){
			case "delete":
				keyWord = "delete";
				break;
			case "undo":
				keyWord = "undo";
				break;
			default:
				keyWord = "view";
			}
		}
		
		TimeParser timeParser = new TimeParser(userCommand);
		sortedCommand = timeParser.getSortedCommand() + "";
		
		if (timeParser.isTimedTask()){
			startTime = timeParser.getStartTime();
			endTime = timeParser.getEndTime();
			isTimedTask = true;
		} else if(timeParser.isDeadLineTask()){
			endTime = timeParser.getEndTime();
			isDeadLineTask = true;
		} else {
			assert timeParser.isFloatingTask();
			isFloatingTask = true;
		}
		assert keyWord !=null;
		assert commandWords !=null;
	}
	
	public String getKeyCommand() { 
		System.out.println("Key command: " +keyWord);
		return keyWord;
	}

	public Integer getEditIndex() { 
		System.out.println("Edit index: " +editIndex);
		if (editIndex == -1){
			return 1;
		}
		return editIndex; 
	}
	
	public DateTime getStartTime(){
		return startTime;
	}
	
	public DateTime getEndTime(){
		return endTime;
	}
	
	public boolean isDeadLineTask(){
		return isDeadLineTask;
	}
	
	public boolean isFloatingTask(){
		return isFloatingTask;
	}
	
	public boolean isTimedTask(){
		return isTimedTask;
	}

}