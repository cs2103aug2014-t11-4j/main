package indigoSrc;

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
	
	protected int numDates;
	// Floating = 0, Deadline = 1; Timed = 2;
	
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
		numDates = 0;
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


	public String toString() {
		StringBuilder result = new StringBuilder("");
		result.append(taskDescription);
		if (isDone){
			result.append(" [Completed] ");
		}
		return result.toString();
	}

	@Override
	public boolean equals(Object anotherTask) {
		// TODO Auto-generated method stub
		if (anotherTask instanceof FloatingTask){
			FloatingTask myTask = (FloatingTask) anotherTask;
			return this.getDescription().equals(myTask.getDescription());
		} else {
			return false;
		}
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
	/* compare the two floating task
		by default: deadlinetask and timedtask will be placed at the front, follow a chronological order
		then it is arranged by taskDescription alphabetically
		*/
	public int compareTo(FloatingTask aTask) {
		if (this.numDates != aTask.numDates){
			return  aTask.numDates - this.numDates;
		} else if (this.numDates == 2){
			return this.taskDescription.compareTo(aTask.taskDescription);
		} else {
			return this.toDeadlineTask().endTime.compareTo(aTask.toDeadlineTask().endTime);
		}
	}

}
