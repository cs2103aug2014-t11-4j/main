package indigoSrc;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

public abstract class Task implements Comparable<Task>{
	protected boolean isDone; // to indicate the status of task e.g is it done or due
	protected String taskDescription;
	protected boolean isImportant; // a mark up to tell whether the task is important
	protected int numDates;
	protected String location;
	
	protected DateTime endTime;
	protected static DateTime currentTime = DateTime.now();
	protected DateTime keyTime;
	
	protected DateTime startTime;	
	protected String newLine = System.getProperty("line.separator");
	
	public Task(String description){
		taskDescription = description + "";
		isDone = false;
		isImportant = false;
		numDates = 0;
		this.location = "home";
	}
	
	//getter	
	public String getLocation(){
		return this.location;
	}
	
	public String getDescription() {
		return this.taskDescription;
	}

	public boolean isImportant() {
		return isImportant;
	}
	
	public boolean isCompleted(){
		return isDone;
	}
	
	public DateTime getTime(){
		return endTime;
	}
	
	public int getNumDates(){
		return numDates;
	}
	
	public DateTime getKeyTime(){
		return keyTime;
	}
	
	public DateTime getStartTime(){
		return startTime;
	}
	
	
	// setter
	public String editDescription(String newDescription) {
		taskDescription = newDescription;
		return taskDescription;
	}
	
	public DateTime editTime(DateTime newTime){
		endTime = newTime;
		return endTime;
	}
	
	public boolean isOverdue(){
		return endTime.isBefore(currentTime);
	}
	
	public String editLocation(String newPlace){
		location = newPlace;
		return newPlace;
	}
	
	@Override
	public String toString(){
		return toStringWODate();
	}


	public String toString(DateTimeFormatter dtf){
		return toStringWODate();
	}
	
	public String toStringWODate(){
		StringBuilder result = new StringBuilder("");
		result.append(taskDescription);
		return result.toString();
	}
	
	public boolean complete(){
		isDone = true;
		return isDone;
	}
	
	public boolean unComplete(){
		isDone = false;
		return isDone;
	}
	
	public boolean highlight(){
		isImportant = true;
		return isImportant;
	}
	
	public boolean hide(){
		isImportant = false;
		return isImportant;
	}
	
	@Override
	/* compare the two floating task
		by default: deadlinetask and timedtask will be placed at the front, follow a chronological order
		then it is arranged by taskDescription alphabetically
		*/
	abstract public int compareTo(Task aTask);
	
	@Override
	public boolean equals(Object anotherTask) {
		// TODO Auto-generated method stub
		if (anotherTask instanceof FloatingTask){
			FloatingTask myTask = (FloatingTask) anotherTask;
			return this.getDescription().equals(myTask.getDescription()) &&
					this.isDone == myTask.isDone && this.numDates == myTask.numDates;
		} else {
			return false;
		}
	}
}
