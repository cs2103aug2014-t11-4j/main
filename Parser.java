/** this class is meant to read in the command line from the user in the form of a string
 * and parse it so that the logic can access it simply. 
 * @author Joanna
 *
 */
import java.util.ArrayList;
import java.util.*;

public class Parser {
	String keyWord  				= null; 
	String commandWords  			= null; 
	String [] commandSentence = new String[2]; 
	String [] details 		  		= null; 
	String toDo               		= ""; 
	String [] date = new String[3];
	int [] dateIntArr = new int[3];
	String dateStr 			  		= null; 
	int dateInt 			  		= 0;
	int monthInt			  		= 0;
	int yearInt      		  		= 0; 
	ArrayList<String> conjWords 	= new ArrayList<String>();
	ArrayList<String> detailsList 	= new ArrayList<String>();
	ArrayList<String> month      	= new ArrayList<String>();
	ArrayList<String> monthWords 	= new ArrayList<String>();
	ArrayList<String> daysList  	= new ArrayList<String>();
	String INVALID_MONTH_MESSAGE    = "Month input is invalid.";
	static String testInput 				= null;
	
	public static void main(String args[]) { 
		System.out.println("Enter command:");
		Scanner sc = new Scanner(System.in);
		testInput = sc.nextLine();
		Parser test = new Parser();
		test.parse(testInput);
		test.getDate(); 
		test.getKeyCommand();
		test.getCommand();
		sc.close(); 
	}
	
	public void parse(String userCommand) { 
		userCommand.toLowerCase();
		commandSentence = userCommand.split(" ", 2);
		keyWord = commandSentence[0];
		commandWords = commandSentence[1];
		addDays(); 
		
		conjWords.add("by"); 
		conjWords.add("on"); 							// words to filter out dates
		
		switch(keyWord) { 
		case "add": 									// for instance add buy a cat on 23/12/2014
			 details = commandWords.split(" "); 
			 for(int i=0; i<details.length; i++) 
				detailsList.add(details[i]);
				 
			 for(int j=0; j<details.length; j++) { 
				 if(conjWords.contains(details[j])) { 
					 parseDate(); 
					 parseInfo(); 
					 break;  
				 }
				 if(j==details.length-1)
					 System.out.println("Not a valid input, no date detected.");
			 }
				 
			 			 
		case "delete" : 
		case "edit" : 
		case "undo" : 
		} 					
	}
	public void parseDate() {
		 if(detailsList.contains("/")) {		// date is in the format of 23/12/2014
			 dateStr = details[details.length-1];
			 if(!checkValidMonthNumber())  
				 System.out.println("monthnumber invalid"); 
			 else {
				 dateInt = Integer.parseInt(date[0]);
			     monthInt = Integer.parseInt(date[1]);
			     yearInt = Integer.parseInt(date[2]);
			 } 
		 } 
		 int p=0;
		 if(detailsList.contains(daysList)) {    // date is in the format of Monday
			 dateStr = details[details.length-1];
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
		if(detailsList.contains("/")) { 
			for(int k=0; k<details.length-1; k++) {  
		 		 toDo = toDo+ " " + details[k];
			} 
		}
		else {
			for(int m=0; m<details.length-4; m++) { 
				toDo = toDo+ " " +details[m]; 
			}
		}
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
		System.out.print("Date:");
		for(int k=0; k<3; k++) { 
			System.out.print(dateIntArr[k]);
		}
		System.out.println();
		return dateIntArr;
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
}
