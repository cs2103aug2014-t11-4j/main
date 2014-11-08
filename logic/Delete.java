package logic;

import java.util.ArrayList;
import java.util.Stack;

import parser.TaskIdentifiers;
import indigoSrc.Parser;
import indigoSrc.Task;
import indigoSrc.TaskList;

/** This class is the delete class which deletes the task that user
 * would want to delete. User will have to indicate the index which the task 
 * lies to delete the task.
 * 
 * @author KenHua
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
		ArrayList<Integer> manyNumbers = new ArrayList<Integer>();
		manyNumbers = parserVar.getMultipleIndices();
		
		if(manyNumbers != null){
			ArrayList<Integer> check = new ArrayList<Integer>();
			check = manyNumbers;
			if(checkValidIndices(check)){
				deleteAllIndex = fillDeleteAll(manyNumbers);
				type = TaskIdentifiers.ALL;
			} else{
				isValid = false;
			}
		} else if(!type.equals(TaskIdentifiers.INVALID)){
			deleteAllIndex = fillDeleteAll(type);
		} else if ((index <= totalSize) && (index >= 1)){
			toDo = taskListVar.get(index);
		} else {
			isValid = false;
			toDo = null;
		}
	}
	
	private boolean checkValidIndices(ArrayList<Integer> manyNumbers) {
		// TODO Auto-generated method stub
		int totalSize = taskListVar.getSize();
		for(int i=0; i<=manyNumbers.size() - 1; i++){
			int number = manyNumbers.get(i);
			if(!((number <= totalSize) && (number >= 1))){
				return false;
			}
		}
		return true;
	}
	
	public Stack<Integer> fillDeleteAll(ArrayList<Integer> manyNumbers) {
		// TODO Auto-generated method stub
		Stack<Integer> indices = new Stack<Integer>();
		for(int i=0; i<manyNumbers.size(); i++){
			int number = manyNumbers.get(i);
			deleteAll.push(taskListVar.get(number));
			indices.push(number);
		}
		return indices;
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
		if (isValid==false){
			return "Invalid index";
		} 
		if(!type.equals(TaskIdentifiers.INVALID)){
			Stack<Integer> temp = new Stack<Integer>();
			Stack<Task> taskTemp = new Stack<Task>();
			int count = 0;
			while(!deleteAllIndex.empty()){
				int indexNum = deleteAllIndex.pop();
				taskListVar.deleteTask(indexNum);
				temp.push(indexNum);
				taskTemp.push(deleteAll.pop());
				count++;
			}
			deleteAllIndex = temp;
			deleteAll = taskTemp;
			return count + " task(s) is/are deleted.";
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
