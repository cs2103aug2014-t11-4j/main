package indigoSrc;
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
import java.util.ArrayList;
import java.util.Scanner;
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
	String keyWord  			;  				//stores the key command "add"/"delete" to return to logic
	String commandWords  		; 				//stores the remaining words excluding key command
	String [] commandSentence 	= new String[2]; 		//to help store the splited string command
	String [] details 		  	; 				//store the remaining words excluding key command individually
	String toDo               	= "";//stores the final command to return to logic
	private DateTime startTime;
	private DateTime endTime;
	boolean containConj 		= false;				//determine if it is a floating task
	private int editIndex;
	private boolean isFloatingTask;
	private boolean isDeadlineTask;
	private boolean isTimedTask; 
	static TimeParser testParser;	
	ArrayList<String> detailsList = new ArrayList<String>();
	private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());

	
	public static void main(String args[]) {
	/*	ConsoleHandler handler = new ConsoleHandler();
		LOGGER.setLevel(Level.FINER);
		handler.setLevel(Level.FINER);
		LOGGER.addHandler(handler);
	*/	
		String testInput;
		System.out.println("Enter command:");
		Scanner sc = new Scanner(System.in);
		testInput = sc.nextLine();
		Parser test = new Parser(testInput);
		testParser = new TimeParser(testInput);	
		try {
			System.out.println(testParser.parser.get(0).getText());
		} catch (IndexOutOfBoundsException err){
			System.out.println("There are no such thing as time! Hurhur");
		}
		System.out.println(test.getCommand());
	}
	
	public String getCommand() {
		toDo = toDo.trim();
		int index = toDo.length();
		
		ArrayList<String> prepWordsList = new ArrayList<String>();
		prepWordsList.add("on");
		prepWordsList.add("by");
		prepWordsList.add("at");
		prepWordsList.add("from");
		String[] description;
		int size = 0;
		try {
			size = testParser.parser.size();
		} catch (Exception err){
			
		}
		String[] identifers = new String[size]; 
		for(int i=0; i<size;i++){ 
			identifers[i] = testParser.parser.get(i).getText();
		}
		for(int k=0; k<size; k++) { 
			toDo = toDo.replaceFirst(identifers[k], "IDENTIFIER"); 
			description = toDo.split(" "); 
				for(int j=0; j<description.length; j++) { 
					if(description[j].contains("IDENTIFIER") && j>0) { 
						String prepWord = description[j-1]; 			
							if(prepWordsList.contains(prepWord)) { 
								String finaltoDo = "";  
								description[j-1] ="";
								for(int j1=0; j1<description.length; j1++) {
									finaltoDo = finaltoDo + description[j1] + " "; 
								}
								toDo = finaltoDo; 
								toDo = toDo.replace("IDENTIFIER", "");
								toDo = toDo.trim(); 
								toDo = toDo.replaceAll("( )+", " ");
							}
					}
					toDo = toDo.replace("IDENTIFIER", ""); 
					toDo = toDo.trim(); 
				}
		}
		if(toDo.contains(" today")){
			index = toDo.indexOf(" today");
		} else if(toDo.contains(" tomorrow")){
			index = toDo.indexOf(" tomorrow");			
	//	} else if(toDo.contains(" by ")){
	//		index = toDo.indexOf(" by ");
	//	} else if (toDo.contains(" at ")){
	//		index = toDo.indexOf(" at ");
	//	} else if(toDo.contains(" from ")){
	//		index = toDo.indexOf(" from ");
	//	} else if(toDo.contains(" on ")){
	//		index = toDo.indexOf(" on ");
		}
		//return toDo.substring(0, index);
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
			case "search":
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
				keyWord = "add";
				toDo = userCommand + "";
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
			case "view":
			case "search":
				keyWord = "view";
				break;
			
			default:
				keyWord = "add";
			}
			toDo = userCommand + "";
		}
		
		TimeParser timeParser = new TimeParser(toDo);
		sortedCommand = timeParser.getSortedCommand() + "";
		
		if (timeParser.isTimedTask()){
			startTime = timeParser.getStartTime();
			endTime = timeParser.getEndTime();
			isTimedTask = true;
		} else if(timeParser.isDeadLineTask()){
			endTime = timeParser.getEndTime();
			isDeadlineTask = true;
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

	public int getEditIndex() { 
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
	
	public boolean isDeadlineTask(){
		return isDeadlineTask;
	}
	
	public boolean isFloatingTask(){
		return isFloatingTask;
	}
	
	public boolean isTimedTask(){
		return isTimedTask;
	}

}