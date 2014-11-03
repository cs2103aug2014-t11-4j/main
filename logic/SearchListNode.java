package logic;

import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;

public class SearchListNode {
	private FloatingTask task;
	private int originalIndex;
	
	public SearchListNode(FloatingTask task, int index){
		this.task = task;
		originalIndex = index;
	}
	
	public SearchListNode(DeadlineTask task, int index) {
		// TODO Auto-generated constructor stub
		this.task = task;
		originalIndex = index;
	}

	public FloatingTask getTask(){
		return task;
	}
	
	public int getIndex(){
		return originalIndex;
	}
	
}
