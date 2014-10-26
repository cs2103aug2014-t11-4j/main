package indigoSrc;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class Read extends CommandClass{
	
	//private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
	private static final String FILE_NAME = "myTask";
	static final String newLine = System.getProperty("line.separator");
	String feedback = "ViewClass";
	String resulting = new String();

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		view();
		return feedback;
	}
	
	public Read(Parser parsing, TaskList taskList){
		parserVar = parsing;
		this.taskListVar = taskList;
		resulting = this.view();
	}
	
	public String view(){
		if(parserVar.getCommand().contains("done")){
			feedback = "Done tasks are shown. Good Job!";
			return viewDone();
		} else if(parserVar.getCommand().contains("undone")){
			feedback = "These are your undone tasks. You can do it!";
			return viewUndone();
		} else if (parserVar.getCommand().contains("-f")){
			feedback = "All the floating tasks are shown";
			return viewFloatingTask();
		} else if (parserVar.getCommand().contains("-d")){
			feedback = "All the deadline tasks are shown";
			return viewDeadlineTask();
		}  else if (parserVar.getCommand().contains("today")){
			feedback = "Today's tasks are shown";
			return viewOverDue() + newLine + viewToday();
		}  else if (parserVar.getCommand().contains("this week")){
			feedback = "This week's tasks are shown";
			return viewOverDue() + newLine + viewThisWeek();
		}  else if (parserVar.getCommand().contains("this month")){
			feedback = "This month's tasks are shown";
			return viewThisMonth();
		}  else if (parserVar.getCommand().contains("overdue")){
			feedback = "All tasks overdue are shown";
			return viewOverDue().trim();
		} else
			feedback = "All the tasks are shown!";
			return viewAll();
	}
	
	public String viewFloatingTask(){
		StringBuilder result = new StringBuilder("Floating tasks are:" + newLine);
		for (int i=0,j=1;i<taskListVar.getFloatingList().size();i++){
			assert taskListVar.getFloatingList().get(i).toDeadlineTask()==null;
			result.append(j++ + ". " + 
					taskListVar.getFloatingList().get(i).toString() + newLine);
		}
		return result.toString().trim();
	}
	
	public String viewDeadlineTask(){
		StringBuilder result = new StringBuilder("Deadline tasks are:" + newLine);
		for (int i=0,j=1;i<taskListVar.getTimedList().size();i++){
			DeadlineTask temp = (DeadlineTask) taskListVar.getTimedList().get(i);
			result.append(j++ + ". " + temp.toString() + newLine);
		}
		return result.toString().trim();
	}
	
	public String viewAll(){
		int sum = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size();
		StringBuilder result = new StringBuilder("There are " + sum + " tasks listed:" + newLine);
		result.append(viewDeadlineTask() + newLine + newLine + viewFloatingTask());
		return result.toString();
	} 
	
	public String viewDone(){
		return taskListVar.viewDone();
	}
	
	public String viewUndone(){
		return taskListVar.viewUndone();
	}
	
	public static void main(String args[]){
		DateTime now = DateTime.now();
		//TaskList test = new TaskList();
		DeadlineTask time0 = new DeadlineTask("buy book", new DateTime(2014,10,24,11,00,00));
		DeadlineTask time1 = new DeadlineTask("read book", new DateTime(2014,10,26,19,15,00));
		DeadlineTask time2 = new DeadlineTask("buy bread", new DateTime(2014,10,22,19,15,00));
		DeadlineTask time3 = new DeadlineTask("eat bread", new DateTime(2013,5,22,19,15,00));
		DeadlineTask time4 = new DeadlineTask("buy movie ticket", new DateTime(2013,11,21,19,15,00));
		DeadlineTask time5 = new DeadlineTask("watch movie", new DateTime(2015,1,22,19,15,00));
		DeadlineTask time6 = new DeadlineTask("go shopping", new DateTime(2015,11,23,19,15,00));
		DeadlineTask time7 = new DeadlineTask("go shopping", new DateTime(2014,10,28,23,59,00));
		DeadlineTask time8 = new DeadlineTask("go shopping", new DateTime(2014,11,2,00,1,00));
		
		System.out.println(dayLeft(now, now)); //Today
		System.out.println(dayLeft(now, time0.endTime)); //Due 
		System.out.println(dayLeft(now, time1.endTime)); //future
		System.out.println(dayLeft(now, time3.endTime)); //due before today
		System.out.println(dayLeft(now, time4.endTime)); //due after today
		System.out.println(dayLeft(now, time5.endTime)); //future before today
		System.out.println(dayLeft(now, time6.endTime)); //future after today
		System.out.println(dayLeft(now, time7.endTime));
		System.out.println(dayLeft(now, time8.endTime));
		
		//test.addTask(time0);
		//test.addTask(time1);
		//test.addTask(time2);
		//test.addTask(time3);
		//test.addTask(time4);
		//test.addTask(time5);
		//test.addTask(time6);
		
	}
	
	//This method is to find tasks which are due today.
	public static String viewToday(){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		
		StringBuilder result = new StringBuilder("Today's tasks are: " + newLine);
		int tlSize = taskListVar.getTimedList().size();
		for (int i=1; i<=tlSize; i++){
				DeadlineTask temp = (DeadlineTask) taskListVar.get(i);
				if((temp.getKeyTime().getYear() == yearNow) && (temp.getKeyTime().getDayOfYear() == dayNow)){
					result.append(temp.toStringWODate() + newLine);
			}
		}
		return result.toString().trim();
	}
	
	//This method is to find tasks which are due this week. 
	//It does not follow the pattern on Monday to Sunday. It takes tasks of
	//the next seven days.
	public static String viewThisWeek(){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		int day7 = dayNow + 7;
		
		StringBuilder result = new StringBuilder();
		int tlSize = taskListVar.getTimedList().size();
		String timeKeeperCompare = dayLeft(now, now);
		result.append(timeKeeperCompare + newLine);
		for (int i=1; i<=tlSize; i++){
				DeadlineTask temp = (DeadlineTask) taskListVar.get(i);
				DateTime tempDate = temp.getTime();
				String timeKeeper = dayLeft(now, tempDate);
				int tempDateDay = tempDate.getDayOfYear();
				if((tempDate.getYear() == yearNow) && 
					((tempDateDay >= dayNow) && (tempDateDay <= day7))){
					if(!timeKeeperCompare.equals(timeKeeper)){
						result.append(newLine + timeKeeper + newLine);
						timeKeeperCompare = timeKeeper;
					}
						result.append(temp.toStringWODate() + newLine);
				}
		}
		return result.toString().trim();
	}
	
	//This method is to find the tasks which are due this current month. Taking Overdue tasks 
	//for the month into consideration as well.
	public static String viewThisMonth(){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int monthNow = now.getMonthOfYear();
		
		StringBuilder result = new StringBuilder();
		int tlSize = taskListVar.getTimedList().size();
		String timeKeeperCompare = dayLeft(now, now);
		for (int i=1; i<=tlSize; i++){
				DeadlineTask temp = (DeadlineTask) taskListVar.get(i);
				DateTime tempDate = temp.getTime();
				String timeKeeper = dayLeft(now, tempDate);
				int tempMonth = tempDate.getMonthOfYear();
				if((tempDate.getYear() == yearNow) && (tempMonth == monthNow)){
					if(!timeKeeperCompare.equals(timeKeeper)){
						result.append(newLine + timeKeeper + newLine);
						timeKeeperCompare = timeKeeper;
					}
					result.append(temp.toStringWODate() + newLine);
				}
		}
		return result.toString().trim();
	}
	
	//This method lets user see tasks which are overdue but not done
	public static String viewOverDue(){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		
		StringBuilder result = new StringBuilder();
		int ntlsize = taskListVar.getTimedList().size();
		String timeKeeperCompare = dayLeft(now, now);
		for (int i=1; i<=ntlsize; i++){
			DeadlineTask temp = (DeadlineTask) taskListVar.get(i);
			DateTime tempDate = temp.getTime();
			String timeKeeper = dayLeft(now, tempDate);
			int tempDateDay = tempDate.getDayOfYear();
			int tempDateYear = tempDate.getYear();
			if((tempDateYear < yearNow) || 
				((tempDateDay < dayNow) && (tempDateYear <= yearNow))){
				if(!timeKeeperCompare.equals(timeKeeper)){
					result.append(newLine + timeKeeper + newLine);
					timeKeeperCompare = timeKeeper;
				}
				result.append(temp.toStringWODate() + newLine);
			}
		}
		return result.toString().trim() + newLine;
	}
	
	public static String dayLeft(DateTime today, DateTime taskDate){
		String returnString[] = new String[2];
		int days = 0;
		if(today.getYear() == taskDate.getYear()){
			days = taskDate.getDayOfYear() - today.getDayOfYear();
		} else if (today.getYear() < taskDate.getYear()){
			int noOfYears = taskDate.getYear() - today.getYear();
			days = taskDate.getDayOfYear() + (noOfYears*365) - today.getDayOfYear() + 1;
		} else if (today.getYear() > taskDate.getYear()){
			int noOfYears = today.getYear() - taskDate.getYear();
			days = taskDate.getDayOfYear() - today.getDayOfYear() - (noOfYears*365) - 1;
		}
		switch (days){
			case 0: 
				returnString[0] = "Today ";
				break;
			case 1:
				returnString[0] = "Tomorrow ";
				break;
			case -1:
				returnString[0] = "Yesterday ";
				break;
			default:
				if(days < -1){
					returnString[0] = (days*-1) + " days ago ";
				} else if (days <= 7){
					DateTime.Property dtp = taskDate.dayOfWeek();
					returnString[0] = dtp.getAsText() + " ";
				} else {
					returnString[0] = "";
				}
		}
		DateTime.Property dtp2 = taskDate.monthOfYear();
		returnString[1] = taskDate.getDayOfMonth() + " ";
		returnString[1] += dtp2.getAsShortText();
		if(today.getYear()!=taskDate.getYear()){
			returnString[1] += " " + taskDate.getYear();
		}
		
		return returnString[0] + returnString[1];
	}

}
