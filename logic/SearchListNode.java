package logic;

import Storage.DeadlineTask;
import Storage.Task;
//@author A0112230H
/** This is an data type object used by the search class. It is used to return two 
 * data from a search result. Namely, the task and the index number in the taskList.
 * 
 */

public class SearchListNode {
	private Task task;
	private int originalIndex;
	
	public SearchListNode(Task task, int index){
		this.task = task;
		originalIndex = index;
	}
	
	public SearchListNode(DeadlineTask task, int index) {
		// TODO Auto-generated constructor stub
		this.task = task;
		originalIndex = index;
	}

	public Task getTask(){
		return task;
	}
	
	public int getIndex(){
		return originalIndex;
	}
	
}
