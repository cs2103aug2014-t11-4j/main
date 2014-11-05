package logic;

import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.Task;

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
