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
	private List<DateGroup> parser;
	
	private final static Logger LOGGER = Logger.getLogger(TimeParser.class.getName()); 

	private int numDate = -1;
	
	public TimeParser(String someCommand){
		userCommand = someCommand.trim();
		assert userCommand!=null;
		parser = new PrettyTimeParser().parseSyntax(userCommand);
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
	    	    parseTime();
		}
		}
	    
		assert sortedUserCommand!=null;
	}

	private void parseTime() {
		LOGGER.log(Level.FINE, "Time String dected:" + parser.get(0).getText());
		dates = parser.get(0).getDates();
		numDate = dates.size();
		sortedUserCommand = userCommand + "";
		sortedUserCommand = sortedUserCommand.replaceAll(parser.get(0).getText(), "");
		LOGGER.log(Level.FINE, "UserCommand after removing time info: " + sortedUserCommand);
		
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

	private boolean isDateFree() {
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
		
		Scanner sc = new Scanner(System.in);
		while (true){	
			TimeParser mp = new TimeParser(sc.nextLine());
			System.out.println("numDateGroup:" + mp.parser.size());
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
