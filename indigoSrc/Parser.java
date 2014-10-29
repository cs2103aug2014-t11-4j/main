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
		prepWordsList.add("in"); 
		
		ArrayList<String> monthsList = doMonthsList();
				
		String[] description;
		int size = 0;
		try {
			size = TimeParser.parser.size();
		} catch (Exception err){
			
		}
		String[] identifers = new String[size]; 
		
		for(int i=0; i<size;i++){ 
			identifers[i] = TimeParser.parser.get(i).getText();
			System.out.println(identifers[i]);
		} 
		
		for(int j=0; j<size; j++) { 
			if(monthsList.contains(identifers[j])) { 
				String regex1 = "\bjan\b";
				String regex2 = "\bfeb\b";
				String regex3 = "\bmar\b";
				String regex4 = "\bapr\b";
				String regex5 = "\bmay\b";
				String regex6 = "\bjun\b";
				String regex7 = "\bjul\b";
				String regex8 = "\baug\b";
				String regex9 = "\bsep\b";
				String regex10 = "\boct\b";
				String regex11 = "\bnov\b";
				String regex12 = "\bdec\b";
					if(!identifers[j].equals(regex1)) 
					if(!identifers[j].equals(regex2)) 
					if(!identifers[j].equals(regex3)) 
					if(!identifers[j].equals(regex4)) 
					if(!identifers[j].equals(regex5)) 
					if(!identifers[j].equals(regex6)) 
					if(!identifers[j].equals(regex7))
					if(!identifers[j].equals(regex8))
					if(!identifers[j].equals(regex9))
					if(!identifers[j].equals(regex10))
					if(!identifers[j].equals(regex11)) 
					if(!identifers[j].equals(regex12)){  
						identifers[j] = ""; 
				}
			}
		}
	
		for(int k=0; k<size; k++) {
			String word = identifers[k]; 
			toDo = toDo.replaceFirst(identifers[k], "IDENTIFIER"); 
			System.out.println(toDo); 
			description = toDo.split(" "); 
				for(int j=0; j<description.length; j++) { 
					if(description[j].contains("IDENTIFIER") && j>0) { 
						String prepWord = description[j-1]; 			
							if(prepWordsList.contains(prepWord) && description[j].equals("IDENTIFIER")) {
								String finaltoDo = "";  
								description[j-1] ="";
								for(int j1=0; j1<description.length; j1++) {
									finaltoDo = finaltoDo + description[j1] + " "; 
								}
								toDo = finaltoDo; 
								System.out.println(toDo); 
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
		}
		return toDo; 
	}

	public ArrayList<String> doMonthsList() {
		ArrayList<String> monthsList = new ArrayList<String>();
		monthsList.add("jan");
		monthsList.add("feb");
		monthsList.add("mar");
		monthsList.add("apr");
		monthsList.add("may");
		monthsList.add("jun");
		monthsList.add("jul");
		monthsList.add("aug");
		monthsList.add("sep");
		monthsList.add("nov");
		monthsList.add("dec");
		return monthsList;
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