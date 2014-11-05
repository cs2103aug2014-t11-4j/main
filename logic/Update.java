package logic;

import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.Parser;
import indigoSrc.Task;
import indigoSrc.TaskList;
import indigoSrc.TimedTask;
/* This class is the update class which can update the task that user
 * would want to edit. User will have to indicate the index which the task 
 * lies to update the task.
 * 
 * @author Ken
 */

public class Update extends CommandClass{
	
	Task toDo;
	Task toDoReplaced;
	int index;
	
	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return edit();
	}

	public Update(Parser parsing, TaskList taskList){
		parserVar = parsing;
		taskListVar = taskList; 
		int totalSize = taskListVar.getSize();
		index = parserVar.getEditIndex();
		if (parserVar.isDeadlineTask()){
			toDo = new DeadlineTask(parserVar.getCommand(),parserVar.getEndTime());
		} else if (parserVar.isTimedTask()){
			toDo = new TimedTask(parserVar.getCommand(),parserVar.getStartTime(),parserVar.getEndTime());
		} else {
			toDo = new FloatingTask(parserVar.getCommand());
		}
		if (index > totalSize || index < 1){
			isValid = false;
			toDoReplaced = null;
		} else {
			toDoReplaced = taskListVar.get(index);
		}
	}
	
	
	private String edit() throws ArrayIndexOutOfBoundsException{
		if (isValid==false){
			return "Invalid index";
		} else {
			assert(toDoReplaced != null);
			index = taskListVar.search(toDoReplaced) + 1;
			taskListVar.editTask(index, toDo);
			return "Task updated to " + toDo.toString();
		}
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		index = taskListVar.search(toDo) + 1;
		taskListVar.editTask(index, toDoReplaced);
		return "Task updated to " + toDoReplaced.toString();
	}

}
