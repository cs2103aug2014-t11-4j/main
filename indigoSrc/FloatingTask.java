package indigoSrc;

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

public class FloatingTask extends Task{
	
	public static void main(String[] args){
		Task task = new FloatingTask();
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
		System.out.println(task.toString(dtf));
		System.out.println(task.getKeyTime());
	}
	
	public FloatingTask(FloatingTask another){
		super(another.getDescription());
		this.isDone = another.isCompleted();
		this.isImportant = another.isImportant();
		this.numDates = another.getNumDates();
		this.location = another.getLocation();
	}
	
	public FloatingTask() {
		// a stub default value for default constructor
		this("default task.");
	}

	public FloatingTask(String description) {
		super(description);
	}
	
	public FloatingTask(String description, String place){
		super(description);
		isDone = false;
		isImportant = false;
		numDates = 0;
		this.location = place + "";
	}

	@Override
	public int compareTo(Task aTask) {
		if (aTask.getNumDates()==0){
			return this.taskDescription.compareTo(aTask.taskDescription);
		} else {
			return -1;
		}
	}
}
