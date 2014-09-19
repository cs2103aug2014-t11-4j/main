/** this class is meant for storage of one task only
 * There are 4 fields for creating a new task: the title, the decription, the time and the level of importance.
 * @author jjlu
 *
 */

public class Task {
	
	private String taskTitle;
	
	private String taskDescription;
	
	private String taskTime;
	// exact time period, date, week or month
	
	private String taskImportance;
	// important, immediate, normal or pleasure
	
	public static int numOfTasks = 0;
	// total number of tasks in the list

	public Task() {
		// a stub default value for default constructor
		this ("CS2103", "finish CS2103 assignments", "today", "immediate");
	}
	
	public Task(String title, String description, String time, String importance){
		taskTitle = title;
		taskDescription = description;
		taskTime = time;
		taskImportance = importance;
		numOfTasks ++;
	}
	
	// accessor
	public String getTitle(){
		return taskTitle;
	}
	
	public String getDescription(){
		return taskDescription;
	}
	
	public String getTime(){
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
	
	public String editTime (String newTime){
		taskTime = newTime;
		return newTime;
	}
	
	public String editImportance (String newImportance){
		taskImportance = newImportance;
		return newImportance;
	}

}
