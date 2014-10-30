package indigoSrc;

/* This class is the create class which adds the task that user
 * would want to add. This class has the function to add task either
 * in the floating tasklist or the deadline tasklist.
 * 
 * @author Ken
 */

public class Create extends CommandClass {

	FloatingTask toDo;
	int index;
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return add();
	}	
	
	public Create(Parser parsing, UndoList pslist, TaskList taskList){
		parserVar = parsing;
		uList = pslist;
		taskListVar = taskList;
		index = parserVar.getEditIndex();
		int totalSize = taskListVar.getSize() + 1;
		if (index > totalSize || index < 1){
			isValid = false;
		}
		if (parserVar.isDeadlineTask()){
			toDo = new DeadlineTask(parserVar.getCommand(),parserVar.getEndTime());
		} else if (parserVar.isTimedTask()){
			toDo = new TimedTask(parserVar.getCommand(),parserVar.getStartTime(),parserVar.getEndTime());
		} else {
			toDo = new FloatingTask(parserVar.getCommand());
		}
	}
	
	public String add() {
		if (isValid==false){
			return "Invalid index";
		} else {
		taskListVar.addTask(index, toDo);
		return toDo.toString() + " is added to taskList!";
		}

	}
	
	public String undo(){
		int i = taskListVar.search(toDo);
		taskListVar.deleteTask(i + 1);
		return toDo.toString() + " is deleted!";
	}

}