package logic;

import indigoSrc.Task;
import indigoSrc.Parser;
import indigoSrc.TaskList;

/** This class is the Complete class which marks a task. This class has 
 * the function to mark the task either as done or undone.
 * in the floating tasklist or the deadline tasklist.
 * 
 * @author KenHua
 */

public class Complete extends CommandClass {
	
	Task toDo;
	int index;
	boolean toCheck;
	
	public Complete(Parser parsing, TaskList taskList, boolean boolCheck){
		parserVar = parsing;
		taskListVar = taskList;
		int totalSize = taskListVar.getSize();
		index = parserVar.getEditIndex();
		if (index > totalSize || index < 1){
			isValid = false;
		} else {
			toDo = taskListVar.get(index);
		}
		toCheck = boolCheck;
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
		assert(isValid);
		taskListVar.complete(index);
		return toDo.toString() + " is completed!";
	}
	
	private String unCheck() {
		// TODO Auto-generated method stub
		assert(isValid);
		taskListVar.unComplete(index);
		return toDo.toString() + " is not completed!";
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
