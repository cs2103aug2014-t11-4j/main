package logic;

import indigoSrc.FloatingTask;
import indigoSrc.Parser;
import indigoSrc.TaskList;

public class Complete extends CommandClass {
	
	FloatingTask toDo;
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
		taskListVar.get(index).complete();
		return toDo.toString() + " is completed!";
	}
	
	private String unCheck() {
		// TODO Auto-generated method stub
		assert(isValid);
		taskListVar.get(index).unComplete();
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
