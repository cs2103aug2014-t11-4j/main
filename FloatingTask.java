import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
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

public class FloatingTask implements Comparable<FloatingTask>{
	
	protected boolean isDone; // to indicate the status of task e.g is it done or due

	protected String taskDescription;
	
	protected String taskDetails; // details that can be stored

	protected boolean isImportant; // a mark up to tell whether the task is important
	
	protected int typeIndex;
	// Floating = 2, Deadline = 1; Timed = 0;
	
	public static void main(String[] args){
		FloatingTask task = new FloatingTask();
		System.out.println(task.toString());
	}

	public FloatingTask() {
		// a stub default value for default constructor
		this("default task.");
	}

	public FloatingTask(String description) {
		taskDescription = description;
		isDone = false;
		isImportant = false;
		taskDetails = "";
		typeIndex = 2;
	}

	// accessor
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
	
	// mutator
	public String editDescription(String newDescription) {
		taskDescription = newDescription;
		return taskDescription;
	}

	public boolean editImportance(boolean newImportance) {
		isImportant = newImportance;
		return isImportant;
	}

	public String toString() {
		StringBuilder result = new StringBuilder(taskDescription);
		result.append(" @isDone " + isDone + " @isImpor " + isImportant);
		return result.toString();
	}

	@Override
	public boolean equals(Object anotherTask) {
		// TODO Auto-generated method stub
		return false;
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
	public int compareTo(FloatingTask aTask) {
		if (this.typeIndex != aTask.typeIndex){
			return this.typeIndex - aTask.typeIndex;
		} else if (this.typeIndex == 2){
			return this.taskDescription.compareTo(aTask.taskDescription);
		} else {
			return this.toDeadlineTask().endTime.compareTo(aTask.toDeadlineTask().endTime);
		}
	}

}
