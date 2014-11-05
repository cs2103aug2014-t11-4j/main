package logic;

import indigoSrc.Parser;
import indigoSrc.Task;
import indigoSrc.TaskList;

import java.util.Stack;

import parser.TaskIdentifiers;

/* This class is the delete class which deletes the task that user
 * would want to delete. User will have to indicate the index which the task 
 * lies to delete the task.
 * 
 * @author Ken
 */

public class Delete extends CommandClass{
	
	Task toDo;
	int index;
	TaskIdentifiers type;
	private Stack<Task> deleteAll = new Stack<Task>();
	private Stack<Integer> deleteAllIndex = new Stack<Integer>();

	@Override
	public String execute() {
		return delete();
	}
	
	public Delete(Parser parsing, TaskList taskList){
		parserVar = parsing;
		taskListVar = taskList;
		int totalSize = taskListVar.getSize();
		index = parserVar.getRawEditIndex();
		type = parserVar.taskWord;
		if(!type.equals(TaskIdentifiers.INVALID)){
			deleteAllIndex = fillDeleteAll(type);
		} else if ((index <= totalSize) && (index >= 1)){
			toDo = taskListVar.get(index);
		} else {
			index = taskListVar.getRecentIndex();
			toDo = taskListVar.get(index);
			//toDo = null;
			//isValid = false;
		}
	}
	
	public Stack<Integer> fillDeleteAll(TaskIdentifiers type){
		Stack<Integer> indices = new Stack<Integer>();
		if(type.equals(TaskIdentifiers.FLOATING)){
			int timedListSize = taskListVar.getTimedList().size();
			int floatListSize = taskListVar.getFloatingList().size();
			for(int i = 0; i < floatListSize; i++){
				deleteAll.push(taskListVar.getFloatingList().get(i));
				indices.push(i + timedListSize + 1);
			}
		} else if(type.equals(TaskIdentifiers.DEADLINE)){
			int timedListSize = taskListVar.getTimedList().size();
			for(int i = 0; i < timedListSize; i++){
				deleteAll.push(taskListVar.getTimedList().get(i));
				indices.push(i + 1);
			}
		} else if(type.equals(TaskIdentifiers.OVERDUE)){
			indices = taskListVar.findOverdue(deleteAll);
		} else if(type.equals(TaskIdentifiers.COMPLETED)){
			indices = taskListVar.findCompleted(deleteAll);
		} else if(type.equals(TaskIdentifiers.UNCOMPLETED)){
			indices = taskListVar.findNoComplete(deleteAll);
		} else if(type.equals(TaskIdentifiers.ALL)){
			int timeSize = taskListVar.getTimedList().size();
			int floatSize = taskListVar.getFloatingList().size();
			for(int i=0; i<timeSize; i++){
				deleteAll.push(taskListVar.getTimedList().get(i));
				indices.push(i+1);
			}
			for(int i=0; i<floatSize; i++){
				deleteAll.push(taskListVar.getFloatingList().get(i));
				indices.push(i + timeSize + 1);
			}
		} else{
			indices = null;
		}
		return indices;
	}
	
	private String delete() throws ArrayIndexOutOfBoundsException{
		if(!type.equals(TaskIdentifiers.INVALID)){
			Stack<Integer> temp = new Stack<Integer>();
			int count = 0;
			while(!deleteAllIndex.empty()){
				int indexNum = deleteAllIndex.pop();
				taskListVar.deleteTask(indexNum);
				temp.push(indexNum);
				count++;
			}
			deleteAllIndex = temp;
			return count + " task(s) is/are deleted.";
		}
		if (isValid==false){
			return "Invalid index";
		} 
		taskListVar.deleteTask(index);
		return toDo.toString() + " is deleted";
	}
	
	public String undo(){
		if(!type.equals(TaskIdentifiers.INVALID)){
			Stack<Integer> tempIndices = new Stack<Integer>();
			Stack<Task> tempTasks = new Stack<Task>();
			int count = 0;
			while(!deleteAll.empty()){
				int indexNum = deleteAllIndex.pop();
				Task task = deleteAll.pop();
				taskListVar.addTask(indexNum, task);
				tempIndices.push(indexNum);
				tempTasks.push(task);
				count++;
			}
			deleteAll = tempTasks;
			deleteAllIndex = tempIndices;
			return count + " task(s) is/are re-added!";
		}
		taskListVar.addTask(index, toDo);
		return toDo.toString() + " is re-added!";
	}
}
