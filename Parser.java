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
<<<<<<< HEAD
<<<<<<< HEAD
	String [] commandTime		= new String[4]; 
	String [] commandDate		= new String[3]; 
	String [] details 		  	= null; 				//store the remaining words excluding key command individually
	String toDo               	= ""; 					//stores the final command to return to logic
	static int [] startDate = new int[3];
	static int [] endDate = new int[3]; 
	static int [] dateOnly = new int[3]; 
	static boolean toCheckStartDate = false; 
	static boolean toCheckEndDate = false; 
	static boolean toCheckTime = false; 
=======
	String [] details 		  	= null; 				//store the remaining words excluding key command individually
	String toDo               	= ""; 					//stores the final command to return to logic
>>>>>>> origin/ObjectOrientedLogic
	String [] date 				= new String[3];		//stores the date in string array (deal with 23 dec 2014)
	int [] dateIntArr 			= new int[3];			//stores the date to return to logic
	String dateStr 			  	= null; 				//stores the date in string to eliminate "/" 		
	String timeArr[] 			= new String [2]; 
	String timeStr 				= null;
	String startTimeStr 		= null;
	String endTimeStr			= null;
	String timeInfo 			= null;
	String dateStartInfo 			= null; 
	static String dateEndInfo = ""; 
	int timeStartIndex 			= 0;
	int timeEndIndex 			= 0;
	int dateStartStartIndex 			= 0;
	int dateStartEndIndex 			= 0; 
	int dateEndStartIndex = 0;
	int dateEndEndIndex = 0; 
	static int startHoursInt 	= 0;
	static int startMinutesInt	= 0; 
	static int endHoursInt 	 	= 0;
	static int endMinutesInt	= 0; 
	static String timeFormat    = null; 
	static String dateFormatStart= null;
	static String dateFormatEnd = null; 
	public static Pattern patternStartDate;
	public static Pattern patternEndDate;
	public static Pattern patternTime; 
	public static Matcher matcherStartDate;
	public static Matcher matcherEndDate; 
	public static Matcher matcherTime; 
=======
	String [] details 		  	= null; 				//store the remaining words excluding key command individually
	String toDo               	= "";//stores the final command to return to logic
	private DateTime startTime;
	private DateTime endTime;
>>>>>>> 90a2dd3a2abb1446c0ea9377a8c04d7d9fba36bc
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
	
<<<<<<< HEAD
	public Parser(){
		this("view");
	}
	
=======
	public String getCommand() {
		toDo = toDo.trim();
		return toDo;
	}

>>>>>>> 90a2dd3a2abb1446c0ea9377a8c04d7d9fba36bc
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
<<<<<<< HEAD
				 } 				 
				 break;
				 			 
			case "delete" : 
				String[] split2 = commandSentence[1].split(" ");
				delIndex = Integer.parseInt(split2[0]); 
				break;
			
			case "edit" : 								// edit 2 catch a cat
				details = commandWords.split(" ");
				editIndex = Integer.parseInt(details[0]);
				for(int c=1; c<details.length; c++) { 
					toDo = toDo + " " + details[c];
				}
				break;
				
			case "complete" :
			case "uncomplete" :
				details = commandWords.split(" ");
				editIndex = Integer.parseInt(details[0]);
				for(int c=1; c<details.length; c++) { 
					toDo = toDo + " " + details[c];
				}
				break;
=======
			LOGGER.log(Level.FINE, "toDo: " + toDo);
			LOGGER.log(Level.FINE, "editIndex " + editIndex);
			break;
>>>>>>> 90a2dd3a2abb1446c0ea9377a8c04d7d9fba36bc
			
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
<<<<<<< HEAD
		}			
	}
	public void parseTime(String timeInfo) { 
		commandTime = timeInfo.split(" ");
		startTimeStr = commandTime[1];
		endTimeStr = commandTime[3];
		int lengthStart = startTimeStr.length(); 
		int lengthEnd = endTimeStr.length(); 
		boolean shouldAddEndTime = false; 
		boolean shouldAddStartTime = false; 
		
		if(startTimeStr.charAt(lengthStart-2) == 'p') { 
			shouldAddStartTime = true;
		}
<<<<<<< HEAD
		String [] splitStartTime = new String [2]; 
		splitStartTime = startTimeStr.split(":"); 
		String startHours = splitStartTime[0]; 
		String startMinutes = splitStartTime[1]; 
		startMinutes = startMinutes.substring(0,2);  //to remove the am/pm 
		startHoursInt = Integer.parseInt(startHours);
		startMinutesInt = Integer.parseInt(startMinutes); 
		if(shouldAddStartTime) { 
			startHoursInt = startHoursInt + 12; 		
=======

		 else{ 								// date is in the format of 23 Dec 2014
			 for(int j=details.length-3; j<details.length; j++) {   
				 date[p] = details[j]; 
				 p++; 
			 }
			 if(!checkValidMonthWord()) {
				 System.out.println("month word invalid."); 
			 }
			 else{
				 dateInt = Integer.parseInt(date[0]);
			 	 monthInt = convertMonth(); 
			 	 yearInt = Integer.parseInt(date[2]);
			 }
		 }  
		System.out.print(dateInt + " " + monthInt + " " + yearInt);
	}  	
	public void parseInfo() { 
		int size1 = detailsList.size(); 
		if(detailsList.get(size1-1).contains("/")) { 
			for(int k=0; k<details.length-2; k++) {  
		 		 toDo = toDo+ " " + details[k];
			} 
>>>>>>> origin/ObjectOrientedLogic
=======
>>>>>>> 90a2dd3a2abb1446c0ea9377a8c04d7d9fba36bc
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
<<<<<<< HEAD
	public boolean checkValidDate() {
		String dayStr = new String();
		String monthStr = new String();
		if(dateIntArr[0] < 10){
			dayStr = "0" + dateIntArr[0];
		} else {
			dayStr = dateIntArr[0] + "";
		}
		if(dateIntArr[1] < 10){
			monthStr = "0" + dateIntArr[1];
		} else {
			monthStr = dateIntArr[1] + "";
		}
		String validateDate = monthStr + "/" + dayStr + "/" + dateIntArr[2];{
			assert validateDate.length() == 10;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); 
		Date testDate = null;
		try{
			testDate = sdf.parse(validateDate); 		
		}
		catch(ParseException e)
		{
			System.out.println("Date input is in wrong format");
			return false;
		}
		if(!sdf.format(testDate).equals(validateDate)) { 
			System.out.println("Date input is invalid.");
			return false; 
		}
		return true; 
	}
	public static void defineTimePattern() { 
		timeFormat = "\\bfrom\\b (1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm) \\bto\\b (1[012]|[1-9]):[0-5][0-9](\\s)?(?i)(am|pm)";
		patternTime = Pattern.compile(timeFormat);
	}
	public static void defineStartDatePattern() { 
		dateFormatStart = "\\b(on|by)\\b (0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";  
		patternStartDate = Pattern.compile(dateFormatStart); 
	}
	public static void defineEndDatePattern() { 
		dateFormatEnd = "\\b(until|till)\\b (0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)";  
		patternEndDate = Pattern.compile(dateFormatEnd); 
	}
	public DateTime getDateOnly() {
		int startDateInt = dateOnly[0]; 
		int startMonthInt = dateOnly[1]; 
		int startYearInt = dateOnly[2]; 
		DateTime dateOnlyObj = new DateTime(startYearInt, startMonthInt-1, startDateInt, 0, 0, 0);
		return dateOnlyObj; 
	}
	public DateTime getStartTime() {
		int startDateInt = startDate[0]; 
		int startMonthInt = startDate[1]; 
		int startYearInt = startDate[2]; 
		DateTime startDateObj = new DateTime(startYearInt, startMonthInt-1, startDateInt, startHoursInt, startMinutesInt, 0); 
		return startDateObj; 
	}
	public DateTime getEndTime() { 
		DateTime endDateObj; 
		if(dateEndInfo != "") {  //there is an end date indicated
			int endDateInt = endDate[0]; 
			int endMonthInt = endDate[1];
			int endYearInt = endDate[2]; 
		endDateObj = new DateTime(endYearInt, endMonthInt-1, endDateInt, endHoursInt, endMinutesInt, 0);
			return endDateObj; 
		}
		else {
			int startDateInt = startDate[0]; 
			int startMonthInt = startDate[1]; 
			int startYearInt = startDate[2];  
			endDateObj = new DateTime(startYearInt, startMonthInt-1, startDateInt, endHoursInt, endMinutesInt, 0);
			return endDateObj;
		}
=======
	
	public DateTime getStartTime(){
		return startTime;
>>>>>>> 90a2dd3a2abb1446c0ea9377a8c04d7d9fba36bc
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
