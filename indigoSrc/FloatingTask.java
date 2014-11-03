package indigoSrc;

import org.joda.time.format.DateTimeFormatter;

/**
 * A floating task is a task with the following 4 fields
 * -isDone
 * -taskDescription
 * -taskDetails
 * -isImportant
 * 
 * 
 * 
 * @author jjlu
 *
 */

public class FloatingTask extends Task{
	
	public static void main(String[] args){
		FloatingTask task = new FloatingTask();
		System.out.println(task.toString());
	}
	
	public FloatingTask(FloatingTask another){
		this.taskDescription = another.taskDescription;
		this.isDone = another.isDone;
		this.isImportant = another.isImportant;
		this.taskDetails = another.taskDetails;
		this.numDates = another.numDates;
		this.location = another.location;
	}
	
	public FloatingTask() {
		// a stub default value for default constructor
		this("default task.");
	}

	public FloatingTask(String description) {
		taskDescription = description + "";
		isDone = false;
		isImportant = false;
		taskDetails = "";
		numDates = 0;
		this.location = "home";
	}
	
	public FloatingTask(String description, String place){
		taskDescription = description + "";
		isDone = false;
		isImportant = false;
		taskDetails = "";
		numDates = 0;
		this.location = place + "";
	}
	

	
	public DeadlineTask toDeadlineTask(){
		if (this instanceof DeadlineTask){
			DeadlineTask temp = (DeadlineTask)this;
			return temp;
		} else {
			return null;
		}
	}
	
	public TimedTask toTimedTask(){
		if (this instanceof TimedTask){
			TimedTask temp = (TimedTask)this;
			return temp;
		} else {
			return null;
		}
	}

	@Override
	public String toString(DateTimeFormatter dtf) {
		StringBuilder result = new StringBuilder("");
		result.append(taskDescription);
		return result.toString();
	}

	@Override
	public int compareTo(Task aTask) {
		if (aTask.numDates==0){
			return this.taskDescription.compareTo(aTask.taskDescription);
		} else {
			return -1;
		}
	}
}
