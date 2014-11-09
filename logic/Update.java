package logic;

import org.joda.time.DateTime;

import parser.Parser;
import Storage.DeadlineTask;
import Storage.FloatingTask;
import Storage.Task;
import Storage.TaskList;
import Storage.TimedTask;
/** This class is the update class which can update the task that user
 * would want to edit. User will have to indicate the index which the task 
 * lies to update the task.
 * 
 * @author KenHua
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
		index = parserVar.getRawEditIndex();
		if (index > totalSize || index < 1){
			index = taskListVar.getRecentIndex();
		}
		toDoReplaced = taskListVar.get(index);
		
		String newTaskDescription = parserVar.getCommand();
		DateTime newStartTime = parserVar.getStartTime();
		
		DateTime newEndTime = parserVar.getEndTime();
		if(newTaskDescription.equals("")){
			newTaskDescription = toDoReplaced.getDescription();
		}
		if(newEndTime==null){
			newEndTime = toDoReplaced.getKeyTime();
		}
		if(newStartTime==null){
			newStartTime = toDoReplaced.getStartTime();
		}
		toDo = getTask(newTaskDescription, newEndTime, newStartTime);
	}

	public Task getTask(String details, DateTime end, DateTime start) {
		Task toDo = null;
		if (start == null && end == null){
			toDo = new FloatingTask(details);
		} else if (start == null){
			toDo = new DeadlineTask(details, end);
		} else {
			toDo = new TimedTask(details, start, end);
		}
		return toDo;
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
