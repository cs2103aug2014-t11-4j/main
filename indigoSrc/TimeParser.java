package indigoSrc;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.joda.time.DateTime;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;


public class TimeParser {
//	private DateTime startTime;
//	private DateTime endTime;
	private String userCommand; // raw command from user
	private String sortedUserCommand; // commands follows a format without a date:  e.g.add go to school
	private DateTime endTime;
	private DateTime startTime;
	private List<Date> dates;
	static List<DateGroup> parser;
	ArrayList<String> filterWords = new ArrayList<String>();
	
	private final static Logger LOGGER = Logger.getLogger(TimeParser.class.getName()); 

	private int numDate = -1;
	private static Scanner sc;
	
	public TimeParser(String someCommand){
		userCommand = someCommand.trim();
		assert userCommand!=null;
		parser = new PrettyTimeParser().parseSyntax(userCommand);
		filterParser();  
		if (parser == null){
			LOGGER.log(Level.FINE, "floating task detected.");
			sortedUserCommand = userCommand + "";
			assert isDateFree();
		} else {
	    switch (parser.size()){
	    	case 0:
	    		assert isDateFree();
		    	break;
	    	case 1: // continue
	    	    parseTime();
	    		break;
	    	default:
	    		LOGGER.log(Level.FINE, "multiple dateGroup detected.");
	    		LOGGER.log(Level.FINE, listAllDates(parser));
	    	    parseTime();
		}
		}
	    
		assert sortedUserCommand!=null;
	}

	private void filterParser() { 
		filterWords.add("mon"); 
		filterWords.add("tue"); 
		filterWords.add("wed"); 
		filterWords.add("thu"); 
		filterWords.add("fri"); 
		filterWords.add("jan"); 
		filterWords.add("feb"); 
		filterWords.add("mar"); 
		filterWords.add("apr");
		filterWords.add("may"); 
		filterWords.add("june"); 
		filterWords.add("jul"); 
		filterWords.add("aug"); 
		filterWords.add("sep");
		filterWords.add("oct"); 
		filterWords.add("nov"); 
		filterWords.add("dec"); 
		
		String regex1 = "\bmon\b";
		String regex2 = "\btue\b";
		String regex3 = "\bwed\b"; 
		String regex4 = "\bthu\b"; 
		String regex5 = "\bfri\b"; 
		String regex6 = "\bjan\b"; 
		String regex7 = "\bfeb\b"; 
		String regex8 = "\bmar\b"; 
		String regex9 = "\bapr\b"; 
		String regex10 = "\bmay\b"; 
		String regex11 = "\bjun\b"; 
		String regex12 = "\bjul\b"; 
		String regex13 = "\baug\b";
		String regex14 = "\bsep\b";
		String regex15 = "\boct\b";
		String regex16 = "\bnov\b";
		String regex17 = "\bdec\b"; 
		
		boolean needRemove = false;
		for (int j=0;j<parser.size();j++){
			needRemove = false;
				String identified = parser.get(j).getText();
				if(isInteger(parser.get(j).getText())) { 
					needRemove = true; 
				}
				if(identified.matches(".*\\d.*")) {				
					for(int d=0; d<identified.length()-2;d++) { 
						if(Character.isDigit(identified.charAt(d))){ 
							if(!(identified.charAt(d+1) == 'a') && (!(identified.charAt(d+2) == 'm')))
							if(!(identified.charAt(d+1) == 'p') && (!(identified.charAt(d+2) == 'm'))) { 
								//op2 tomorrow, i need to remove the 2 and just have tomorrow only
								needRemove = true; 
								//String s = identified.charAt(d) + ""; 
							//	identified = identified.replace(s, "");
								//parser.add(identified); 
							}
								
						}
					}
					
				}
				if(filterWords.contains(identified)) { 
					if(!parser.get(j).getText().equals(regex1)) 
					if(!parser.get(j).getText().equals(regex2)) 
					if(!parser.get(j).getText().equals(regex3)) 
					if(!parser.get(j).getText().equals(regex4)) 
					if(!parser.get(j).getText().equals(regex5)) 
					if(!parser.get(j).getText().equals(regex6))
					if(!parser.get(j).getText().equals(regex7)) 
					if(!parser.get(j).getText().equals(regex8))
					if(!parser.get(j).getText().equals(regex9)) 
					if(!parser.get(j).getText().equals(regex10)) 
					if(!parser.get(j).getText().equals(regex11))
					if(!parser.get(j).getText().equals(regex12)) 
					if(!parser.get(j).getText().equals(regex13)) 
					if(!parser.get(j).getText().equals(regex14))
					if(!parser.get(j).getText().equals(regex15)) 
					if(!parser.get(j).getText().equals(regex16)) 
					if(!parser.get(j).getText().equals(regex17)) { 
						needRemove = true;
					}
				}
				if (needRemove = true){
					parser.remove(j--);
				}
		}
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
	
	private String listAllDates(List<DateGroup> parser2) {
		StringBuilder result = new StringBuilder("Dates are:\n");
		for (int i=0;i<parser2.size();i++){
			result.append("DateGroup " + i+ " "+  parser2.get(i).getText() + ":\n");
			for (int j=0;j<parser2.get(i).getDates().size();j++){
				result.append(parser2.get(i).getDates().get(j).toString());
			}
		}
		return result.toString();
	}

	private void parseTime() {
		LOGGER.log(Level.FINE, "Time String dected:" + parser.get(0).getText());
		dates = parser.get(0).getDates();
		System.out.println("TESTTTTTT");
		System.out.println(dates); 
		numDate = dates.size();
		sortedUserCommand = userCommand + "";
		sortedUserCommand = sortedUserCommand.replaceAll(parser.get(0).getText(), "");
		LOGGER.log(Level.FINE, "UserCommand after removing time info: " + sortedUserCommand);
		System.out.println(numDate); 
	    switch (numDate){
	    case 1: // a deadLine task
	    	parseDeadLineTask();
	    	break;
	    case 2://  a timed task
	    	parseTimedTask();
	    	break;
	    default:
	    	LOGGER.log(Level.FINE, "multiple dates detected.");
	    	parseTimedTask();
	    }
	}
	
	private void parseTimedTask() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "Timed task detected.");
		startTime = new DateTime(dates.get(0));
		LOGGER.log(Level.FINE, "startTime: " + startTime.toString());
    	endTime = new DateTime(dates.get(1));
		LOGGER.log(Level.FINE, "endTime: " + endTime.toString());
		assert isDateFree();
    	assert startTime != null;
    	assert endTime != null;
	}

	private void parseDeadLineTask() {
		// TODO Auto-generated method stub
		LOGGER.log(Level.FINE, "DeadLine task detected.");
		endTime = new DateTime(dates.get(0));
		LOGGER.log(Level.FINE, "endTime: " + endTime.toString());
		assert isDateFree();
		assert endTime != null;
	}

	public boolean isDateFree() {
		List<DateGroup> parse = new PrettyTimeParser().parseSyntax(userCommand);
		int numDate = parse.get(0).getDates().size();
		
		if (numDate ==0){
			return true;
		} else {
			return false;
		}
	}

	public static void main(String[] argv){
		ConsoleHandler handler = new ConsoleHandler();
		LOGGER.setLevel(Level.FINER);
		handler.setLevel(Level.FINER);
		LOGGER.addHandler(handler);
		
		sc = new Scanner(System.in);
		while (true){	
			TimeParser mp = new TimeParser(sc.nextLine());
			//System.out.println("numDateGroup:" + mp.parser.size());
		}
	}
	
	public String getSortedCommand(){
		return sortedUserCommand;
	}
	
	public DateTime getStartTime(){
		return startTime;
	}
	
	public DateTime getEndTime(){
		return endTime;
	}
	
	public boolean isFloatingTask(){
		if (numDate <1){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isDeadLineTask(){
		if (numDate == 1){
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isTimedTask(){
		if (numDate > 1){
			return true;
		} else {
			return false;
		}
	}
		
	}

	
/*	getDate:
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	output = sdf.format(mp.dateGroup.getDates().get(0));
	*/
	//search for key from the userCommand
