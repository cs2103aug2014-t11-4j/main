package indigoSrc;

import org.joda.time.format.DateTimeFormatter;

public abstract class Task implements Comparable<Task>{
	protected boolean isDone; // to indicate the status of task e.g is it done or due

	protected String taskDescription;
	
	protected String taskDetails; // details that can be stored

	protected boolean isImportant; // a mark up to tell whether the task is important
	
	protected int numDates;
	
	protected String location;
	
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
	
	public String taskDetails(){
		return taskDetails;
	}
	
	public boolean isCompleted(){
		return isDone;
	}
	
	// setter
	public String editDescription(String newDescription) {
		taskDescription = newDescription;
		return taskDescription;
	}
	
	public String editLocation(String newPlace){
		location = newPlace;
		return newPlace;
	}


	abstract public String toString(DateTimeFormatter dtf);
	
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
}
