import java.util.ArrayList;

/** This class stores the task-list as an arrayList.
 *  The basic functions have been provided as a skeleton program.
 * @author jjlu
 * 
 *
 *
 * API:
 * Constructor
 * taskList()
 * taskList(String)
 * store a string into an arrayList
 * 
 * methods:
 * Task addTask(Task)
 * Task deleteTask(Task)
 * Task deleteTask(int)
 * Task viewTask(int)
 * Task editTask(int, Task)
 * return the original task before editing.
 * ArrayList<Task> viewTaskList()
 * return the string version of taskList
 * String viewList()
 * return the string version of taskList
 */


public class TaskList {
	
	private ArrayList<Task> taskList = new ArrayList<Task>();

	public TaskList() {
		
	}	
	
	public TaskList(String allTasks){
		//TODO takes a string and stores them in an arrayList
	}
	
	public ArrayList<Task> viewTaskList(){
		return taskList;
	}
	
	public String viewList(){
		//TODO a string representation of taskList
		return "";
	}
	
	// basic functions
	// add
	public Task addTask(Task newTask){
		taskList.add(newTask);
		return newTask;
	}
	
	// delete
	public Task deleteTask(Task task){
		taskList.remove(task);
		return task;
	}
	public Task deleteTask(int index){
		int indexComputing = index - 1;
		Task tempTask = taskList.get(indexComputing);
		taskList.remove(indexComputing);
		return tempTask;
	}
	
	// edit
	public Task editTask(int index, Task newTask){
		int indexComputing = index - 1;
		Task tempTask = taskList.get(indexComputing);
		taskList.add(indexComputing, newTask);
		taskList.remove(index);
		return tempTask;
	}
	
	// view the taskList in a particular format
	public Task viewTask(int index){
		int indexComputing = index - 1;
		return taskList.get(indexComputing);
	}

}
