package logic;

import indigoSrc.FloatingTask;
import indigoSrc.TaskList;

public class GridViewTaskList {
	private static final int MAX_COL = 5;
	private static final int INDEX = 0;
	private static final int NAME = 1;
	private static final int START = 2;
	private static final int END = 3;
	private static final int DONE = 4;
	
	private TaskList taskList;
	public String[][] grid;
	private int rowOne;
	private int rowTwo;
	private int maxRow;
	
	public GridViewTaskList(TaskList taskList){
		this.taskList = taskList;
		rowOne = taskList.getTimedList().size();
		rowTwo = taskList.getFloatingList().size();
		maxRow = rowOne + rowTwo;
		grid = new String[maxRow - 1][MAX_COL];
	}
	
	public void buildGrid(){
		for(int i=0; i<rowOne; i++){
			FloatingTask fTask = new FloatingTask();
			fTask = taskList.getTimedList().get(i);
			grid[i][INDEX] = i+1 + "";
			grid[i][NAME] = fTask.getDescription();
			if(fTask.getNumDates()==2){
				grid[i][START] = fTask.getStartTime().toString();
				grid[i][END] = fTask.getKeyTime().toString();
			} else if (fTask.getNumDates()==1){
				grid[i][START] = " - ";
				grid[i][END] = fTask.getKeyTime().toString();
			} else {
				grid[i][START] = " - ";
				grid[i][END] = " - ";
			}
			
			if(fTask.isCompleted()){
				grid[i][DONE] = "DONE";
			} else {
				grid[i][DONE] = " ";
			}
		}
		
	}
	
	public String toString(){
		String result = "";
		for(int i=0; i<maxRow-1; i++){
			for(int j=0; j<MAX_COL; j++){
				result += "[" + grid[i][j] + "]" + " ";
			}
			result += "\n";
		}
		return result;
	}
	
}
