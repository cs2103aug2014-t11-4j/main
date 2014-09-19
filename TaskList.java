import java.util.ArrayList;

/** This class stores 4 lists of tasks, named as daily,weekly,monthly or floating.
 *  All the functionalities should be contained in this class.
 *  The basic functions have been provided as a skeleton program.
 * @author jjlu
 *
 */


public class TaskList {
	
	private ArrayList<Task> floatingTask = new ArrayList<Task>();
	
	private ArrayList<Task> dailyTask = new ArrayList<Task>();
	
	private ArrayList<Task> weeklyTask = new ArrayList<Task>();
	
	private ArrayList<Task> monthlyTask = new ArrayList<Task>();

	public enum Type {
		FLOATING, DAILY, WEEKLY, MONTHLY
	}
	public TaskList() {
		this(Type.FLOATING, new Task());
	}
	
	public TaskList(Type type, Task newTask){
		switch (type) {
			case DAILY:
				dailyTask.add(newTask);
				break;
			
			case WEEKLY:
				weeklyTask.add(newTask);
			
			case MONTHLY:
				monthlyTask.add(newTask);
				
			default:
				floatingTask.add(newTask);
		}
	}
	
	
	// basic functions
	// add
	public String addTask(String type, Task newTask){
		//stub
		return null;
	}
	
	// delete
	public String deleteTask(String type, Task newTask){
		// stub
		return null;
	}
	
	// edit
	public String editTask(String type, Task newTask){
		// stub
		return null;
	}
	
	// view the taskList in a particular format
	public String viewTask(String type){
		// stub
		return null;
	}

}
