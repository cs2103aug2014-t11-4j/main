package indigoSrc;
import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class Read extends CommandClass{
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
	private static final String FILE_NAME = "myTask";
	static final String newLine = System.getProperty("line.separator");

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return view();
	}
	
	public Read(Parser parsing, TaskList taskList){
		parserVar = parsing;
		this.taskListVar = taskList;
	}
	
/*	@Test
	public void testRead(){
		Parser testParse = new Parser("view");
		//assertEquals(view(), taskList.writeXMLDocument(FILE_NAME));
	}
	*/
	public String view(){
		if(parserVar.getCommand().contains("-done")){
			return viewDone();
		} else if(parserVar.getCommand().contains("-undone")){
			return viewUndone();
		} else if (parserVar.getCommand().contains("-f")){
			return taskListVar.viewFloatingTask();
		} else if (parserVar.getCommand().contains("-d")){
			return taskListVar.viewDeadlineTask(DATE_FORMAT);
		} else if (parserVar.getCommand().contains("-t")){
			return taskListVar.viewTimedTask(DATE_FORMAT);
		} else if (parserVar.getCommand().contains("-today")){
			return viewToday(taskListVar);
		} else
			return taskListVar.viewAll(DATE_FORMAT);
	}
	
	public String viewDone(){
		return taskListVar.viewDone();
	}
	
	public String viewUndone(){
		return taskListVar.viewUndone();
	}
	
	public static void main(String args[]){
		TaskList test = new TaskList();
		DeadlineTask time0 = new DeadlineTask("buy book", new DateTime(2014,10,23,11,00,00));
		DeadlineTask time1 = new DeadlineTask("read book", new DateTime(2014,10,24,19,15,00));
		DeadlineTask time2 = new DeadlineTask("buy bread", new DateTime(2014,10,22,19,15,00));
		DeadlineTask time3 = new DeadlineTask("eat bread", new DateTime(2013,5,22,19,15,00));
		DeadlineTask time4 = new DeadlineTask("buy movie ticket", new DateTime(2013,11,21,19,15,00));
		DeadlineTask time5 = new DeadlineTask("watch movie", new DateTime(2015,1,22,19,15,00));
		DeadlineTask time6 = new DeadlineTask("go shopping", new DateTime(2015,10,23,19,15,00));
		test.addTask(time0);
		test.addTask(time1);
		test.addTask(time2);
		test.addTask(time3);
		test.addTask(time4);
		test.addTask(time5);
		test.addTask(time6);
		
		//Test the viewToday method. Only "buy book" should appear.
		System.out.println(viewToday(test));
		//Test the viewThisWeek method. "buy book" and "read book" only should appear.
		System.out.println(viewThisWeek(test));
		//Test overdue view. "buy bread", "eat bread", "buy movie ticket" should appear.
		System.out.println(viewOverDue(test));
	}
	
	//This method is to find tasks which are due today.
	public static String viewToday(TaskList test){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		
		StringBuilder result = new StringBuilder("Today's tasks are:" + newLine);
		int tlSize = test.getSize();
		for (int i=0, j=1; i<tlSize; i++){
			if ((test.get(i+1) instanceof DeadlineTask) && !(test.get(i+1) instanceof TimedTask)){
				DeadlineTask temp = (DeadlineTask) test.get(i+1);
				if((temp.getTime().getYear() == yearNow) && (temp.getTime().getDayOfYear() == dayNow)){
					result.append("[No." + j++ + "]" + temp.toString(DATE_FORMAT) + newLine);
				}
			}
		}
		return result.toString();
	}
	
	//This method is to find tasks which are due this week. 
	//It does not follow the pattern on Monday to Sunday. It takes tasks of
	//the next seven days.
	public static String viewThisWeek(TaskList test){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		int day7 = dayNow + 1;
		
		StringBuilder result = new StringBuilder("This week's tasks are:" + newLine);
		int tlSize = test.getSize();
		for (int i=0, j=1; i<tlSize; i++){
			if ((test.get(i+1) instanceof DeadlineTask) && 
				!(test.get(i+1) instanceof TimedTask)){
				DeadlineTask temp = (DeadlineTask) test.get(i+1);
				DateTime tempDate = temp.getTime();
				int tempDateDay = tempDate.getDayOfYear();
				if((tempDate.getYear() == yearNow) && 
					((tempDateDay >= dayNow) && (tempDateDay <= day7))){
					result.append("[NO." + j++ + "]" + temp.toString(DATE_FORMAT) + newLine);
				}
			}
		}
		return result.toString();
	}
	
	//This method lets user see tasks which are overdue but not done
	public static String viewOverDue(TaskList test){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		
		StringBuilder result = new StringBuilder("Tasks overdue are:" + newLine);
		int ntlsize = test.getSize();
		for (int i=0, j=1; i<ntlsize; i++){
			if ((test.get(i+1) instanceof DeadlineTask) && !(test.get(i+1) instanceof TimedTask)){
				DeadlineTask temp = (DeadlineTask) test.get(i+1);
				DateTime tempDate = temp.getTime();
				int tempDateDay = tempDate.getDayOfYear();
				int tempDateYear = tempDate.getYear();
				if((tempDateYear < yearNow) || 
						((tempDateDay < dayNow) && (tempDateYear <= yearNow))){
					result.append("[NO." + j++ + "]" + temp.toString(DATE_FORMAT) + newLine);
				}
			}
		}
		return result.toString();
	}

}
