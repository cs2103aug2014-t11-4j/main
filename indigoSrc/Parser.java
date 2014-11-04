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
import java.util.Arrays;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

import org.joda.time.DateTime;

import parser.CommandKey;
import parser.StringDecipher;
import parser.TaskIdentifiers;

public class Parser {
	private boolean isValid;
	private String message;
	private DateTime now;
	private DateTime TimeRef;
	public TaskIdentifiers taskWord = null;
	
	private static Logger logger = Logger.getLogger("Parser");
	private String sortedCommand;
<<<<<<< HEAD
	CommandKey keyWord			;  				//stores the key command "add"/"delete" to return to logic
	String toDo               	= "";//stores the final command to return to logic
=======
	String keyWord  			;  					//stores the key command "add"/"delete" to return to logic
	String commandWords  		; 					//stores the remaining words excluding key command
	String [] commandSentence 	= new String[2]; 	//to help store the splited string command
	String [] details 		  	; 					//store the remaining words excluding key command individually
	String toDo               	= "";				//stores the final command to return to logic
>>>>>>> origin/master
	private DateTime startTime;
	private DateTime endTime;
	boolean containConj 		= false;			//determine if it is a floating task
	private int editIndex;
	private String location;
	private boolean isFloatingTask;
	private boolean isDeadlineTask;
	private boolean isTimedTask; 
	static TimeParser testParser;	
	ArrayList<String> detailsList = new ArrayList<String>();
	private final static Logger LOGGER = Logger.getLogger(Parser.class.getName());
	private static Scanner sc;

	
	public static void main(String args[]) {
	/*	ConsoleHandler handler = new ConsoleHandler();
		LOGGER.setLevel(Level.FINER);
		handler.setLevel(Level.FINER);
		LOGGER.addHandler(handler);
	*/	
		String testInput;
		System.out.println("Enter command:");
		sc = new Scanner(System.in);
		testInput = sc.nextLine();
		Parser test = new Parser(testInput);
		testParser = new TimeParser(testInput);	
		try {
			System.out.println(testParser.parser.get(0).getText());
		} catch (IndexOutOfBoundsException err){
			System.out.println("There are no such thing as time!");
		}
		System.out.println(test.getCommand());
	}
	
	public String getRawCommand(){
		toDo = toDo.trim();
		return toDo;
	}

	public String getLocation(){
		return location + "";
	}
	
	public String getCommand() {
		toDo = toDo.trim();
		
		ArrayList<String> prepWordsList = new ArrayList<String>();
		prepWordsList.add("on");
		prepWordsList.add("by");
		prepWordsList.add("at");
		prepWordsList.add("from");	
		prepWordsList.add("in"); 
		prepWordsList.add("until"); 
	
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
		
		for (int k=0;k<identifers.length;k++){
			if(isInteger(identifers[k])) { 
				identifers[k] = ""; 
			}
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
				String regex13 = "\bmon\b";
				String regex14 = "\bwed\b";
				String regex15 = "\beve\b"; 
				
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
					if(!identifers[j].equals(regex12)) 
					if(!identifers[j].equals(regex13)) 
					if(!identifers[j].equals(regex14)) 
					if(!identifers[j].equals(regex15)){  
						identifers[j] = ""; 
				}
			}
		}
	
		for(int k=0; k<size; k++) {
			System.out.println(toDo);
			toDo = toDo.replaceFirst(identifers[k], "IDENTIFIER"); 
			description = toDo.split(" "); 
			System.out.println("HERE"); 
			System.out.println(toDo); 
				for(int j=0; j<description.length; j++) { 
					if(description[j].contains("IDENTIFIER") && j>0) { 
						String prepWord = description[j-1]; 			
							if(prepWordsList.contains(prepWord) && description[j].equals("IDENTIFIER")) {
								String finaltoDo = "";  
								description[j-1] ="";
								for(int j1=0; j1<description.length; j1++) { //removes prep word if there is
									finaltoDo = finaltoDo + description[j1] + " "; 
								}
								toDo = finaltoDo; 
								toDo = toDo.replace("IDENTIFIER", ""); //removes the identifier
								toDo = toDo.trim(); 
								toDo = toDo.replaceAll("( )+", " ");
								
							}
					} 
							toDo = toDo.replace("IDENTIFIER", ""); //if identifier does not have prep words before it
							toDo = toDo.replaceAll("( )+", " ");
							toDo = toDo.trim();
						}
					}		
		return toDo; 
	}

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // only got here if we didn't return false
	    return true;
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
		monthsList.add("mon"); 
		monthsList.add("wed"); 
		return monthsList;
	}

	public Parser(String userCommand) {
		isValid = checkEmpty(userCommand);
		message = "Command is sucessfully processed.";
		editIndex = -1;
		
		String[] sentenceArray = userCommand.split("\\s+");
		StringDecipher sentenceString = new StringDecipher(sentenceArray);
		
		//The key word of the command must be either the first or last of the sentence.
		keyWord = sentenceString.getKey();
		if(keyWord.equals(CommandKey.CLEAR) && sentenceString.getWordsLeft()!=0){
			keyWord = CommandKey.DELETE;
		}
		assert sentenceString.getWordsLeft() >= 0;
		
	/*	If only the index is stated (apart from key word), index can be first 
	 * 	or last. Unless it's an add which makes sense if user wants to add a 
	 * 	number to tasklist. Or edit, which doesn't make sense as to which task
	 *  to edit. Clear too cannot have number as it by default clears all. 
	 *  editIndex by default is -1. 
	 */
		if(sentenceString.getWordsLeft() == 1 && 
		  (!(keyWord.equals(CommandKey.CREATE) || keyWord.equals(CommandKey.UPDATE)))){
			editIndex = sentenceString.getIndex();
		}
		if(keyWord.equals(CommandKey.DELETE) || keyWord.equals(CommandKey.COMPLETE) ||
		   keyWord.equals(CommandKey.READ) || keyWord.equals(CommandKey.UNCOMPLETE)) {
			taskWord = sentenceString.checkTaskWords();
		}
		
	/*	=== editIndex status ===
	 * 	If the command word is the first word, index of editing must be stated
	 *	after it. Else if command word is not the first word, index must be the 
	 *	first word. If it appears anywhere else, it is regarded as time. 
	 *	Only when adding a task, index will not be considered.
	 */
		if (sentenceString.getWordsLeft() >= 1){
			if(!(keyWord.equals(CommandKey.CREATE))){
				editIndex = sentenceString.getIndex();
			}
			//commandSentence = sentenceString.returnTwoRemaining();
			//commandWords = commandSentence[1];
			//location = parseLocation(commandSentence);
			
			toDo = sentenceString.remainingToString();
			
			LOGGER.log(Level.FINE, "toDo: " + toDo);
			LOGGER.log(Level.FINE, "editIndex " + editIndex);
		}	else {
			//sentenceString.getWordsLeft == 0
			assert editIndex < 0;
			assert sentenceString.getWordsLeft() == 0;
			isValid = keyWord.checkValidAlone();
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
		
		if (endTime !=null){
			smartParserCheck();
		}
		assert keyWord !=null;
	}

	public boolean checkEmpty(String userCommand) {
		userCommand.trim();
		if(userCommand.equals("")){
			return false;
		} else {
			return true;
		}
	}
	
/*	private String parseLocation(String[] words) {
		String place = new String("");
		for (String str: words){
			if (str.startsWith("@")){
				place = str.substring(1);
				return place;
			}
		}
		return null;
	}
*/
	public CommandKey getKeyCommand() { 
		return keyWord;
	}

	public int getEditIndex() { 
		if (editIndex == -1){
			return 1;
		}
		return editIndex; 
	}
	
	public int getRawEditIndex(){
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
	
	public void smartParserCheck(){
		now = new DateTime();
		TimeRef = now.plusMinutes(2);
		if (endTime.isBefore(now)){
			isValid = false;
			message = "The task added is overDue!";
		} else if(endTime.isBefore(TimeRef)){
			DateTime newDate = endTime.plusHours(1);
			endTime = newDate;
		}
	}
	
	public boolean isValid(){
		return isValid;
	}
	
	public String getMessage(){
		return message;
	}

}