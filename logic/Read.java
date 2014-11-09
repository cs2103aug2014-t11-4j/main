package logic;
import indigoSrc.LogicFacade;

import org.joda.time.DateTime;

import Storage.DeadlineTask;
import Storage.TaskList;
import parser.Parser;
import parser.TaskIdentifiers;

/** This class is the read class which will display the list that user
 * would want to see. This class has different types of views that user
 * can choose from. The Tabbed Pane Display will have some default view
 * type implemented like viewToday() and viewThisWeek().
 * 
 * @author KenHua
 */

public class Read extends CommandClass{
	
	private static final int TODAY = 0;
	private static final int TOMORROW = 1;
	private static final int NEXT_WEEK = 7;
	private static final int TAB_TODAY = 1;
	private static final int TAB_WEEK = 2;
	private static final int TAB_MONTH = 3;
	private static final int TAB_FLOAT = 4;
	static final String newLine = System.getProperty("line.separator");
	public String feedback = "Wrong command";
	public String resultString = new String();
	public int tabNo = 0;			//This is for the GUI to decide which tab to display
	
	DateTime start = new DateTime();
	DateTime end = new DateTime();

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		view();
		return feedback;
	}
	
	public Read(Parser parsing, TaskList taskList){
		parserVar = parsing;
		taskListVar = taskList;
		resultString = view();
		if (parserVar.isTimedTask()){
			start = parserVar.getStartTime();
			end = parserVar.getEndTime();
		} else if(parserVar.isDeadlineTask()){
			end = parserVar.getEndTime();
		} else {
			assert parserVar.isFloatingTask();
		}
	}
	
	public Read(TaskList taskList){
		taskListVar = taskList;
		resultString = viewAll();
	}
	
	public String view(){
		TaskIdentifiers word = parserVar.getTaskWord();
		if(!word.equals(TaskIdentifiers.INVALID)){
			switch(word){
				case ALL:
					feedback = "All tasks are shown";
					return viewAll();
				case OVERDUE:
					feedback = "All tasks overdue are shown";
					return viewOverDue();
				case FLOATING:
					feedback = "All the floating tasks are shown";
					tabNo = TAB_FLOAT;
					return viewFloatingTask();
				case DEADLINE:
					feedback = "All the deadline tasks are shown";
					return viewDeadlineTask();
				case COMPLETED:
					feedback = "All the completed tasks are shown";
					return viewDone();
				case UNCOMPLETED:
					feedback = "All the uncompleted tasks are shown";
					return viewUndone();
				case TODAY:
					feedback = "All today's tasks are shown";
					tabNo = TAB_TODAY;
					String resultTod = viewOverDue() + newLine + viewDay(TODAY);
					return resultTod.trim();
				case TOMORROW:
					feedback = "All tomorrow's tasks are shown";
					String resultTom = viewDay(TOMORROW);
					return resultTom.trim();
				case THIS_WEEK:
					feedback = "All this week's tasks are shown";
					tabNo = TAB_WEEK;
					String resultWk = viewOverDue() + newLine + viewWeek(TODAY);
					return resultWk.trim();
				case NEXT_WEEK:
					feedback = "All next week's tasks are shown";
					String resultNw = viewWeek(NEXT_WEEK);
					return resultNw.trim();
				case THIS_MONTH:
					feedback = "All this month's tasks are shown";
					tabNo = TAB_MONTH;
					return viewThisMonth();
				default:
					return viewAll();
			}
		}
		return "-";
	}

	//The view of all tasks in floating tasklist
	public String viewFloatingTask(){
		return viewFloatingTask(0);
	}
	
	//The view of all tasks in floating tasklist with index addendum.
	public String viewFloatingTask(int index){
		StringBuilder result = new StringBuilder("Floating tasks are:" + newLine);
		for (int i=0,j=index+1;i<taskListVar.getFloatingList().size();i++,j++){
			assert taskListVar.getFloatingList().get(i).getNumDates()!=1;
			result.append(j + ". " + taskListVar.getFloatingList().get(i).toString());
			if(taskListVar.getRecentIndex() == j){
				result.append(" \u2605");
			}
			result.append(newLine);
		}
		return result.toString().trim();
	}
	
	//The view of all tasks in deadline tasklist
	public String viewDeadlineTask(){
		StringBuilder result = new StringBuilder("Deadline tasks are:" + newLine);
		for (int i=0,j=1;i<taskListVar.getTimedList().size();i++,j++){
			DeadlineTask temp = (DeadlineTask) taskListVar.getTimedList().get(i);
			result.append(j + ". " + temp.toString(LogicFacade.DATE_FORMAT));
			if(taskListVar.getRecentIndex() == j){
				result.append(" \u2605");
			}
			result.append(newLine);
		}
		return result.toString().trim();
	}
	
	//View all basically views deadline tasklist and floating tasklist
	public String viewAll(){
		int sum = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size();
		int index = taskListVar.getTimedList().size();
		StringBuilder result = new StringBuilder("There are " + sum + " tasks listed:" + newLine);
		result.append(viewDeadlineTask() + newLine + newLine + viewFloatingTask(index));
		return result.toString();
	} 
	
	//View all the tasks which are done
	public String viewDone(){
		return taskListVar.viewDone();
	}
	
	//View all the tasks which are not done
	public String viewUndone(){
		return taskListVar.viewUndone();
	}
	
	//This method is to find tasks which are due today or tomorrow depending on Index.
	private String viewDay(int index) {
		DateTime now = new DateTime();
		now = now.plusDays(index);
		int yearNow = now.getYear();
		int dayNow = now.getDayOfYear();
		StringBuilder result = new StringBuilder();
		String timeKeeperCompare = dayLeft(now);
		result.append(timeKeeperCompare + newLine);
		int tlSize = taskListVar.getTimedList().size();
		for (int i=1; i<=tlSize; i++){
				DeadlineTask temp = (DeadlineTask) taskListVar.get(i);
				if((temp.getKeyTime().getYear() == yearNow) && (temp.getKeyTime().getDayOfYear() == dayNow)){
					result.append(temp.toString(LogicFacade.TIME_FORMAT) + newLine);
			}
		}
		return result.toString().trim();
	}

	//This method is to find tasks which are due this week or next week. 
	//It does not follow the pattern on Monday to Sunday. It takes tasks of
	//the next seven days.
	private String viewWeek(int index) {
		// TODO Auto-generated method stub
		int daysLater = index + 7;
		StringBuilder result = new StringBuilder();
		for( ; index <= daysLater; index++){
			result.append(viewDay(index) + newLine + newLine);
		}
		return result.toString().trim();
	}

	//This method is to find the tasks which are due this current month. Taking Overdue tasks 
	//for the month into consideration as well.
	public String viewThisMonth(){
		DateTime now = new DateTime();
		int yearNow = now.getYear();
		int monthNow = now.getMonthOfYear();
		
		StringBuilder result = new StringBuilder();
		int tlSize = taskListVar.getTimedList().size();
		String timeKeeperCompare = dayLeft(now);
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
					result.append(temp.toString(LogicFacade.TIME_FORMAT) + newLine);
				}
		}
		return result.toString().trim();
	}
	
	//This method lets user see tasks which are overdue but not done
	public String viewOverDue(){
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
			if(((tempDateYear < yearNow)  && !temp.isCompleted()) || 
				((tempDateDay < dayNow) && (tempDateYear <= yearNow) && !temp.isCompleted())){
				if(!timeKeeperCompare.equals(timeKeeper)){
					result.append(newLine + timeKeeper + newLine);
					timeKeeperCompare = timeKeeper;
				}
				result.append(temp.toString(LogicFacade.TIME_FORMAT) + newLine);
				if(result.equals(newLine)){
					return result.append("").toString().trim();
				}
			}
		}
		return result.toString().trim() + newLine;
	}
	
	//Prints the heading for each task separation. Prints out the day and dates
	private String dayLeft(DateTime taskDate){
		DateTime today = new DateTime();
		return dayLeft(today, taskDate);
	}
	
	private String dayLeft(DateTime today, DateTime taskDate){
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
	
	//Undo is ignored.
	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}
}
