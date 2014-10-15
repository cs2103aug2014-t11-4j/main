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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;

public class Parser {
	private static Logger logger = Logger.getLogger("Parser");
	String keyWord  			= null;  				//stores the key command "add"/"delete" to return to logic
	String commandWords  		= null; 				//stores the remaining words excluding key command
	String [] commandSentence 	= new String[2]; 		//to help store the splited string command
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
	boolean containConj 		= false;				//determine if it is a floating task
	static int dateInt 			= 0;
	static int monthInt			= 0;
	static int yearInt      	= 0; 
	int delIndex				= 0; 

	Integer editIndex 			= null; 
	ArrayList<String> conjWords = new ArrayList<String>();
	ArrayList<String> detailsList = new ArrayList<String>();
	ArrayList<String> month      = new ArrayList<String>();
	ArrayList<String> monthWords = new ArrayList<String>();
	ArrayList<String> daysList  = new ArrayList<String>();
	String INVALID_MONTH_MESSAG	= "Month input is invalid.";
	static String testInput 	= null;	

	
	public static void main(String args[]) { 
		System.out.println("Enter command:");
		Scanner sc = new Scanner(System.in);
		testInput = sc.nextLine();
		
		Parser test = new Parser(testInput);
		test.getKeyCommand(); 
		test.getCommand();
		if(ifTimedTaskOverDays()) { 
			getStartTime();
			getEndTime(); 
		}
		if(ifTimedTaskOneDay()) { 
			getStartTime();
			getEndTime(); 
		}
		if(ifDeadlineTask()) { 
			getDateOnly(); 
		}
		sc.close(); 
	}
	
	public Parser(String userCommand) {
		addDays(); 
		
		//conjWords.add("by"); 
		//conjWords.add("on"); 							// words to filter out dates
		
		userCommand = userCommand.trim().toLowerCase();
		if (userCommand.contains(" ")){
			commandSentence = userCommand.split(" ", 2);
			keyWord = commandSentence[0];
			commandWords = commandSentence[1];
			
			switch(keyWord) { 
			case "add": 								// for instance add buy a cat on 23/12/2014

				defineTimePattern(); 
				matcherTime = patternTime.matcher(testInput); 
				
				while(matcherTime.find()) { 
					toCheckTime = true; 
					timeStartIndex = matcherTime.start(); 
					timeEndIndex = matcherTime.end();
				}
				
				defineStartDatePattern();
				matcherStartDate = patternStartDate.matcher(testInput); 
							
				while (matcherStartDate.find()) {
					toCheckStartDate = true; 
					dateStartStartIndex = matcherStartDate.start();
					dateStartEndIndex = matcherStartDate.end(); 
				}
				
				defineEndDatePattern();
				matcherEndDate = patternEndDate.matcher(testInput); 
				
				while (matcherEndDate.find()) {
					toCheckEndDate = true; 
					dateEndStartIndex = matcherEndDate.start();
					dateEndEndIndex = matcherEndDate.end(); 
				}
				
				details = commandWords.split(" ");
				 

				if(toCheckTime == true) { 						//time input found = timed task 
					timeInfo = testInput.substring(timeStartIndex, timeEndIndex); 
					System.out.println(timeInfo); 
					parseTime(timeInfo); 
					dateStartInfo = testInput.substring(dateStartStartIndex, dateStartEndIndex); 
					
					System.out.println(dateStartInfo); 
					
					startDate = parseDate(dateStartInfo); 		
					
					
					if(toCheckEndDate) {						//task spans over days 
						dateEndInfo = testInput.substring(dateEndStartIndex, dateEndEndIndex); 
						endDate = parseDate(dateEndInfo); 
						System.out.println(dateEndInfo); 
					}
					parseInfo(dateStartInfo, dateEndInfo, timeInfo); 
				}
				
				else if(toCheckStartDate == true) {				//date input found = deadline task 
					dateStartInfo = testInput.substring(dateStartStartIndex, dateStartEndIndex); 
					System.out.println(dateStartInfo); 
					dateOnly = parseDate(dateStartInfo); 
					parseInfo(dateStartInfo, dateEndInfo, timeInfo); 
				}
				
				else { 											//floating task
					 int b; 									// to see if there is a index

					 if (editIndex !=null){
						 b=1;
					 } else {
						 b=0;
					 }
					 for(; b<details.length; b++) { 
						toDo = toDo+ " " + details[b]; 
					}
				 } 				 
				 break;
				 			 
			case "delete" : 
				delIndex = Integer.parseInt(commandSentence[1]); 
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
			
			case "undo" :
				keyWord = "undo";
				break;
			case "view" :
				keyWord = "view";
				details = commandWords.split(" ");
				for(int c=0; c<details.length; c++) { 
					toDo = toDo + " " + details[c];
				}
				break;
			} 
		}	else {
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
		String [] splitStartTime = new String [2]; 
		splitStartTime = startTimeStr.split(":"); 
		String startHours = splitStartTime[0]; 
		String startMinutes = splitStartTime[1]; 
		startMinutes = startMinutes.substring(0,2);  //to remove the am/pm 
		startHoursInt = Integer.parseInt(startHours);
		startMinutesInt = Integer.parseInt(startMinutes); 
		if(shouldAddStartTime) { 
			startHoursInt = startHoursInt + 12; 		
		}
		//System.out.println(startHoursInt);
		//System.out.println(startMinutesInt); 
		
		if(endTimeStr.charAt(lengthEnd-2) == 'p') { 
			shouldAddEndTime = true;
		}
		String [] splitEndTime = new String [2]; 
		splitEndTime = endTimeStr.split(":"); 
		String endHours = splitEndTime[0]; 
		String endMinutes = splitEndTime[1]; 
		endMinutes = endMinutes.substring(0,2);  //to remove the am/pm 
		endHoursInt = Integer.parseInt(endHours);
		endMinutesInt = Integer.parseInt(endMinutes); 
		if(shouldAddEndTime) { 
			endHoursInt = endHoursInt + 12; 		
		}
		//System.out.println(endHoursInt);
		//System.out.println(endMinutesInt); 
	}
	public int[] parseDate(String dateInfo) {  
			logger.log(Level.INFO, "going to start processing");
			String [] separateDate = new String[3];
			String dateToSeparate = null; 
			int [] separated = new int[3]; 
			commandDate = dateInfo.split(" ", 2); 
			dateToSeparate = commandDate[1];  
			separateDate = dateToSeparate.split("/"); 
			separated[0] = Integer.parseInt(separateDate[0]);
			separated[1] = Integer.parseInt(separateDate[1]); 
			separated[2] = Integer.parseInt(separateDate[2]); 
			logger.log(Level.INFO, "end of processing");
			return separated; 
	}   	
	public void parseInfo(String dateStartInfo, String dateEndInfo, String timeInfo) { 
		toDo = commandWords.replace(dateStartInfo, ""); 
		if(timeInfo!=null) { 
			toDo = toDo.replace(timeInfo, "");
		} 
		if(dateEndInfo!= "") { 
			toDo = toDo.replace(dateEndInfo, ""); 
		}
		toDo = toDo.trim();
	}
	public boolean checkValidMonthWord() { 
		monthWords.add("jan");
		monthWords.add("feb"); 
		monthWords.add("mar"); 
		monthWords.add("apr");
		monthWords.add("may");
		monthWords.add("jun");
		monthWords.add("jul");
		monthWords.add("aug");
		monthWords.add("sep");
		monthWords.add("oct");
		monthWords.add("nov");
		monthWords.add("dec");  
		
		if(monthWords.contains(date[1])) { 
			return true;
		} 
		else {  
			return false;
		} 
	}
	public boolean checkValidMonthNumber(){ 
		date = dateStr.split("/");
		int month = Integer.parseInt(date[1]); 
		
		if(month>0 && month<13)
			return true;
		else
			return false; 
	}
	public void addDays() { 
		daysList.add("monday");
		daysList.add("tuesday");
		daysList.add("wednesday");
		daysList.add("thursday");
		daysList.add("friday"); 
	}
	public int convertMonth() { 
		switch(date[1]) { 
			case "jan" : 
				return 1;
			case "feb" : 
				return 2; 
			case "mar" : 
				return 3;
			case "apr" :
				return 4;
			case "may" : 
				return 5; 
			case "jun" :
				return 6; 
			case "jul" : 
				return 7; 
			case "aug" : 
				return 8; 
			case "sep" : 
				return 9;
			case "oct" :
				return 10;
			case "nov" :
				return 11; 
			case "dec" : 
				return 12; 
			default : 
				return 0; 
		}
	}
	public int[] getDate() { 
		dateIntArr[0] = dateInt;
		dateIntArr[1] = monthInt;
		dateIntArr[2] = yearInt; 
		//if(checkValidDate()) { 
		//	System.out.print("Date:");
		//	for(int k=0; k<3; k++) { 
		//		System.out.print(dateIntArr[k]);
		//	}
		//	System.out.println(); 
		//	return dateIntArr; 
		//}
		//else { // date is not valid
		//	System.out.println("Date input is invalid.");
		//	return null;
		//}
		//System.out.println("Date is:" +dateInt); 
		//System.out.println("Month is:" +monthInt);
		//System.out.println("Year is:" +yearInt); 
		return dateIntArr; 
	}
	public String getCommand() { 
		toDo = toDo.trim();
		System.out.println("Command instructions:" +toDo);
		return toDo; 
	}
	public String getKeyCommand() { 
		System.out.println("Key command: " +keyWord);
		return keyWord;
	}
	public int getDelIndex() { 
		System.out.println("Delete index: " +delIndex);
		if (delIndex == 0){
			return 1;
		}
		return delIndex; 
	}
	public Integer getEditIndex() { 
		System.out.println("Edit index: " +editIndex);
		if (editIndex == null){
			return 1;
		}
		return editIndex; 
	}
	public boolean checkValidDate() {
		String validateDate = dateIntArr[1] + "/" + dateIntArr[0] + "/" + dateIntArr[2];
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy"); 
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
	public static Calendar getDateOnly() {
		Calendar dateOnlyObj = Calendar.getInstance();
		int startDateInt = dateOnly[0]; 
		int startMonthInt = dateOnly[1]; 
		int startYearInt = dateOnly[2]; 
		dateOnlyObj.set(startYearInt, startMonthInt-1, startDateInt, 0, 0, 0);
		System.out.println(dateOnlyObj.getTime()); 
		return dateOnlyObj; 
	}
	public static Calendar getStartTime() {
		Calendar startDateObj = Calendar.getInstance();
		int startDateInt = startDate[0]; 
		int startMonthInt = startDate[1]; 
		int startYearInt = startDate[2]; 
		startDateObj.set(startYearInt, startMonthInt-1, startDateInt, startHoursInt, startMinutesInt, 0);
		System.out.println(startDateObj.getTime()); 
		return startDateObj; 
	}
	public static Calendar getEndTime() { 
		Calendar endDateObj = Calendar.getInstance(); 
		if(dateEndInfo != "") {  //there is an end date indicated
			int endDateInt = endDate[0]; 
			int endMonthInt = endDate[1];
			int endYearInt = endDate[2]; 
			endDateObj.set(endYearInt, endMonthInt-1, endDateInt, endHoursInt, endMinutesInt, 0);
			System.out.println(endDateObj.getTime());
			return endDateObj; 
		}
		else {
			int startDateInt = startDate[0]; 
			int startMonthInt = startDate[1]; 
			int startYearInt = startDate[2];  
			endDateObj.set(startYearInt, startMonthInt-1, startDateInt, endHoursInt, endMinutesInt, 0);
			System.out.println(endDateObj.getTime());
			return endDateObj;
		}
	}
	public static boolean ifTimedTaskOneDay() {
		if(toCheckTime && toCheckStartDate && !toCheckEndDate) {
			return true;
		}
	return false;
	}
	public static boolean ifTimedTaskOverDays() {
		if(toCheckTime && toCheckStartDate && toCheckEndDate) { 
			return true; 
		}
	return false; 
	}
	public static boolean ifDeadlineTask() { 
		if(!toCheckTime &&toCheckStartDate &&!toCheckEndDate) { 
			return true; 
		}
	return false; 
	}
	public static boolean ifFloatingTask() { 
		if(!toCheckTime &&!toCheckStartDate &&!toCheckEndDate) { 
			return true; 
		}
	return false; 
	}
}
