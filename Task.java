/** this class is meant for storage of one task only
 * 3 way to create a task
 * -task(Parser)
 * -task()
 * -task(String, Type, String, String, String)
 * There are 5 fields for creating a new task: the title, the description, the time, the type of the task and the level of importance.
 * @author jjlu
 *
 */

public class Task {
	
	private String taskTitle;
	
	private Type taskType;
	
	private String taskDescription;
	
	//TODO data type to be amended
	private int[] taskTime;
	// exact time period, date, week or month
	
	private String taskImportance;
	// important, immediate, normal or pleasure
	
	public enum Type {
		FLOATING, DAILY, WEEKLY
	}

	public Task() {
		// a stub default value for default constructor
		this ("CS2103", Type.FLOATING, "finish CS2103 assignments", null, "immediate");
	}
	
	// TODO exact class method to be ammended
	public Task(Parser userCommand){
		this (userCommand.getCommand(), Type.FLOATING, "Enter your task description", userCommand.getDate(), "immediate");
	}
	
	public Task(String title, Type type, String description, int[] time, String importance){
		taskTitle = title;
		taskType = type;
		taskDescription = description;
		taskTime = time;
		taskImportance = importance;
	}
	
	// accessor
	public String getTitle(){
		return taskTitle;
	}
	
	public Type getType(){
		return taskType;
	}
	
	public String getDescription(){
		return taskDescription;
	}
	
	public int[] getTime(){
		return taskTime;
	}
	
	public String getImportance(){
		return taskImportance;
	}
	
	// mutator
	public String editTitle(String newTitle){
		taskTitle = newTitle;
		return newTitle;
	}
	
	public String editDescription (String newDescription){ 
		taskDescription = newDescription;
		return newDescription;
	}
	
	public int[] editTime (int[] newTime){
		taskTime = newTime;
		return newTime;
	}
	
	public String editImportance (String newImportance){
		taskImportance = newImportance;
		return newImportance;
	}

}
