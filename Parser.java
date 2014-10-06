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


public class Parser {
	String keyWord  			= null;  				//stores the key command "add"/"delete" to return to logic
	String commandWords  		= null; 				//stores the remaining words excluding key command
	String [] commandSentence 	= new String[2]; 			//to help store the splited string command
	String [] details 		  	= null; 				//store the remaining words excluding key command individually
	String toDo               	= ""; 					//stores the final command to return to logic
	String [] date 				= new String[3];			//stores the date in string array (deal with 23 dec 2014)
	int [] dateIntArr 			= new int[3];				//stores the date to return to logic
	String dateStr 			  	= null; 				//stores the date in string to eliminate "/" 		
	String timeArr[] 			= new String [2]; 
	String timeStr 				= null;
	String startTimeStr 		= null;
	String endTimeStr			= null;
	boolean containConj 		= false;				//determine if it is a floating task
	int dateInt 			  	= 0;
	int monthInt			  	= 0;
	int yearInt      		  	= 0; 
	int delIndex				= 0; 
	int editIndex 				= 0; 
	ArrayList<String> conjWords 		= new ArrayList<String>();
	ArrayList<String> detailsList 		= new ArrayList<String>();
	ArrayList<String> month      		= new ArrayList<String>();
	ArrayList<String> monthWords 		= new ArrayList<String>();
	ArrayList<String> daysList  		= new ArrayList<String>();
	String INVALID_MONTH_MESSAG			= "Month input is invalid.";
	static String testInput 			= null;
	
	public static void main(String args[]) { 
		System.out.println("Enter command:");
		Scanner sc = new Scanner(System.in);
		testInput = sc.nextLine();
		Parser test = new Parser(testInput);
		test.getKeyCommand();
		test.getDate(); 
		test.getCommand();
		test.getDelIndex();
		test.getEditIndex();
		sc.close(); 
	}
	
	public Parser(String userCommand) {
		addDays(); 
		
		conjWords.add("by"); 
		conjWords.add("on"); 							// words to filter out dates
		
		userCommand.toLowerCase();
		if (userCommand.contains(" ")){
			commandSentence = userCommand.split(" ", 2);
			keyWord = commandSentence[0];
			commandWords = commandSentence[1];
			switch(keyWord) { 
			case "add": 								// for instance add buy a cat on 23/12/2014
				 details = commandWords.split(" ");
				 for(int i=0; i<details.length; i++) 
					detailsList.add(details[i]);
					 
				 for(int j=0; j<details.length; j++) { 
					 if(conjWords.contains(details[j])) {
						 containConj = true; 
					 }
				 }
						
				 if(containConj == true) {
					 parseInfo();
					 parseDate(); 				 
					 break;  
				 }
				 
				 else{ 								// this is a floating task
					for(int b=0; b<details.length; b++) { 
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
			
			case "undo" :
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
	public void parseDate() {  
		int p = 0;
		int size = detailsList.size();
		
		if(detailsList.get(size-1).contains(")")) 			//it is a timed task eg: [5pm-7pm]
			parseTime(); 
	
		if(detailsList.get(size-1).contains("/")) {			// date is in the format of 23/12/2014
			dateStr = details[details.length-1];	
			if(!checkValidMonthNumber())  
				System.out.println("monthnumber invalid"); 
			else {
				dateInt = Integer.parseInt(date[0]);
				monthInt = Integer.parseInt(date[1]);
				yearInt = Integer.parseInt(date[2]);
			} 
		}

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
	}  	
	public void parseInfo() { 
		int size1 = detailsList.size(); 
		if(detailsList.get(size1-1).contains("/")) { 
			for(int k=0; k<details.length-2; k++) {  
		 		 toDo = toDo+ " " + details[k];
			} 
		}
		else {
			for(int m=0; m<details.length-4; m++) { 
				toDo = toDo+ " " +details[m]; 
			}
		}
	}
	public void parseTime() { 
		//eg [5pm-7pm]
		int size2 = detailsList.size(); 
		timeStr = detailsList.get(size2-1);
		System.out.println(timeStr);
		timeStr.replace("(", "");
		timeStr.replace(")", ""); 
		timeArr = timeStr.split("-");
		startTimeStr = timeArr[0];
		endTimeStr = timeArr[1];
		System.out.println(startTimeStr);
		System.out.println(endTimeStr);
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
		if(checkValidDate()) { 
			System.out.print("Date:");
			for(int k=0; k<3; k++) { 
				System.out.print(dateIntArr[k]);
			}
			System.out.println(); 
			return dateIntArr; 
		}
		else { // date is not valid
			System.out.println("Date input is invalid.");
			return null;
		}
	}
	public String getCommand() { 
		toDo = toDo.trim();
		System.out.println("Command instructions: " +toDo);
		return toDo; 
	}
	public String getKeyCommand() { 
		System.out.println("Key command: " +keyWord);
		return keyWord;
	}
	public int getDelIndex() { 
		System.out.println("Delete index: " +delIndex); 
		return delIndex; 
	}
	public int getEditIndex() { 
		System.out.println("Edit index: " +editIndex);
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
}
