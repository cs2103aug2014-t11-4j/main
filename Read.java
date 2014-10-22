import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;

public class Read extends CommandClass{
	
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
	private static final String FILE_NAME = "myTask";

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return view();
	}
	
	public Read(Parser parsing, ParserList parseL, TaskList taskList){
		parserVar = parsing;
		psl = parseL;
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
		} else if (parserVar.getCommand().contains("today")){
			return viewToday();
		} else
			return taskListVar.viewAll(DATE_FORMAT);
	}
	
	public String viewDone(){
		return taskListVar.viewDone();
	}
	
	public String viewUndone(){
		return taskListVar.viewUndone();
	}
	
/*	public static void main(String args[]){
		TaskList ntl = new TaskList();
		DeadlineTask time = new DeadlineTask("boo", new DateTime(2014,10,22,19,15,00));
		DeadlineTask time1 = new DeadlineTask("bee", new DateTime(2014,10,24,19,15,00));
		DeadlineTask time2 = new DeadlineTask("baa", new DateTime(2014,10,26,19,15,00));
		ntl.addTask(time);
		ntl.addTask(time2);
		ntl.addTask(time1);
		
		System.out.println(ntl.viewAll(DATE_FORMAT));
		
		System.out.println(viewToday());
		System.out.println(time.getTime().getYear());
		System.out.println(time.getTime().getDayOfYear());
		System.out.println(time2.getTime().getDayOfYear());
	}
*/
	public static String viewToday(){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();

		System.out.println(now.getYear());
		System.out.println(now.getDayOfYear());
		
		String newLine = System.getProperty("line.separator");
		StringBuilder result = new StringBuilder("Today's tasks are:" + newLine);
		int ntlsize = taskListVar.getSize();
		System.out.println(ntlsize);
		for (int i=0, j=1; i<ntlsize; i++){
			if ((taskListVar.get(i+1) instanceof DeadlineTask) && !(taskListVar.get(i+1) instanceof TimedTask)){
				DeadlineTask temp = (DeadlineTask) taskListVar.get(i+1);
				if((temp.getTime().getYear() == yearNow) && (temp.getTime().getDayOfYear() == dayNow)){
					result.append("[NO." + j++ + "]" + temp.toString(DATE_FORMAT) + newLine);
				}
			}
		}
		return result.toString();
	}
	
	public static String viewThisWeek(){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		int day7 = dayNow + 1;

		System.out.println(now.getYear());
		System.out.println(now.getDayOfYear());
		
		String newLine = System.getProperty("line.separator");
		StringBuilder result = new StringBuilder("Today's tasks are:" + newLine);
		int ntlsize = taskListVar.getSize();
		System.out.println(ntlsize);
		for (int i=0, j=1; i<ntlsize; i++){
			if ((taskListVar.get(i+1) instanceof DeadlineTask) && 
				!(taskListVar.get(i+1) instanceof TimedTask)){
				DeadlineTask temp = (DeadlineTask) taskListVar.get(i+1);
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

}
