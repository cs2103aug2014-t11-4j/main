package logic;

import java.util.ArrayList;
import java.util.Stack;

import Storage.Task;
import Storage.TaskList;
import parser.Parser;
import parser.TaskIdentifiers;
//@author A0112230H
/** This class is the Complete class which marks a task. This class has 
 * the function to mark the task either as done or undone.
 * in the floating tasklist or the deadline tasklist.
 * 
 */

public class Complete extends CommandClass {
	Task toDo;
	int index;
	boolean toCheck;
	TaskIdentifiers type;
	private Stack<Task> completeAll = new Stack<Task>();
	private Stack<Integer> completeAllIndex = new Stack<Integer>();
	
	public Complete(Parser parsing, TaskList taskList, boolean boolCheck){
		parserVar = parsing;
		taskListVar = taskList;
		toCheck = boolCheck;
		int totalSize = taskListVar.getSize();
		index = parserVar.getEditIndex();
		type = parserVar.getTaskWord();
		ArrayList<Integer> manyNumbers = new ArrayList<Integer>();
		manyNumbers = parserVar.getMultipleIndices();
		
		if(manyNumbers != null){
			ArrayList<Integer> check = new ArrayList<Integer>();
			check = manyNumbers;
			if(checkValidIndices(check)){
				completeAllIndex = fillIn(manyNumbers);
				type = TaskIdentifiers.ALL;
			} else{
				isValid = false;
			}
		} else if(!type.equals(TaskIdentifiers.INVALID)){
			completeAllIndex = fillIn(type);
		} else if ((index <= totalSize) && (index >= 1)){
			toDo = taskListVar.get(index);
		} else {
			isValid = false;
			toDo = null;
		}
	}
	
	//Checks if the array of integers are valid index based on the tasklist.
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

	public Stack<Integer> fillIn(ArrayList<Integer> manyNumbers) {
		// TODO Auto-generated method stub
		Stack<Integer> indices = new Stack<Integer>();
		for(int i=0; i<manyNumbers.size(); i++){
			int number = manyNumbers.get(i);
			completeAll.push(taskListVar.get(number));
			indices.push(number);
		}
		return indices;
	}
	
	public Stack<Integer> fillIn(TaskIdentifiers type){
		Stack<Integer> indices = new Stack<Integer>();
		if(type.equals(TaskIdentifiers.FLOATING)){
			int timedListSize = taskListVar.getTimedList().size();
			int floatListSize = taskListVar.getFloatingList().size();
			for(int i = 0; i < floatListSize; i++){
				completeAll.push(taskListVar.getFloatingList().get(i));
				indices.push(i + timedListSize + 1);
			}
		} else if(type.equals(TaskIdentifiers.DEADLINE)){
			int timedListSize = taskListVar.getTimedList().size();
			for(int i = 0; i < timedListSize; i++){
				completeAll.push(taskListVar.getTimedList().get(i));
				indices.push(i + 1);
			}
		} else if(type.equals(TaskIdentifiers.OVERDUE)){
			indices = taskListVar.findOverdue(completeAll);
		} else if(type.equals(TaskIdentifiers.COMPLETED)){
			indices = taskListVar.findCompleted(completeAll);
		} else if(type.equals(TaskIdentifiers.UNCOMPLETED)){
			indices = taskListVar.findNoComplete(completeAll);
		} else if(type.equals(TaskIdentifiers.ALL)){
			int timeSize = taskListVar.getTimedList().size();
			int floatSize = taskListVar.getFloatingList().size();
			for(int i=0; i<timeSize; i++){
				completeAll.push(taskListVar.getTimedList().get(i));
				indices.push(i+1);
			}
			for(int i=0; i<floatSize; i++){
				completeAll.push(taskListVar.getFloatingList().get(i));
				indices.push(i + timeSize + 1);
			}
		} else{
			indices = null;
		}
		return indices;
	}
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		assert(isValid);
		if(toCheck){
			return check();
		} else {
			return unCheck();
		}
	}

	private String check() {
		// TODO Auto-generated method stub		
		if (isValid==false){
			return "Invalid index";
		} 
		assert(isValid);
		if(!type.equals(TaskIdentifiers.INVALID)){
			Stack<Integer> temp = new Stack<Integer>();
			Stack<Task> taskTemp = new Stack<Task>();
			int count = 0;
			while(!completeAllIndex.empty()){
				int indexNum = completeAllIndex.pop();
				taskListVar.complete(indexNum);
				temp.push(indexNum);
				taskTemp.push(completeAll.pop());
				count++;
			}
			completeAllIndex = temp;
			completeAll = taskTemp;
			return count + " task(s) is/are completed.";
		}
		taskListVar.complete(index);
		return toDo.toString() + " is completed";
	}
	
	private String unCheck() {
		// TODO Auto-generated method stub	
		taskListVar.unComplete(index);
		//return toDo.toString() + " is not completed!";
		if (isValid==false){
			return "Invalid index";
		} 
		assert(isValid);
		if(!type.equals(TaskIdentifiers.INVALID)){
			Stack<Integer> temp = new Stack<Integer>();
			Stack<Task> taskTemp = new Stack<Task>();
			int count = 0;
			boolean gotOne = false;
			while(!completeAllIndex.empty()){
				int indexNum = completeAllIndex.pop();
				if(indexNum == 1){
					gotOne = true;
				}
				System.out.println(indexNum);
				taskListVar.unComplete(indexNum);
				temp.push(indexNum);
				System.out.println(completeAll.peek());
				taskTemp.push(completeAll.pop());
				count++;
			}
			completeAllIndex = temp;
			completeAll = taskTemp;
			if (!gotOne){
				taskListVar.complete(1);
			}
			return count + " task(s) is/are uncompleted.";
		}
		taskListVar.unComplete(index);
		return toDo.toString() + " is uncompleted";
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		assert(isValid);
		if(!toCheck){
			return check();
		} else {
			return unCheck();
		}
	}

}
