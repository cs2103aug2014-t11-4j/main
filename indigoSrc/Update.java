package indigoSrc;
/* This class is the update class which can update the task that user
 * would want to edit. User will have to indicate the index which the task 
 * lies to update the task.
 * 
 * @author Ken
 */

public class Update extends CommandClass{

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return edit();
	}

	public Update(Parser parsing, UndoList psList, TaskList taskList){
		parserVar = parsing;
		uList = psList;
		taskListVar = taskList; 

	}
	
	
	public String edit() throws ArrayIndexOutOfBoundsException{
		int index = parserVar.getEditIndex();
		int totalSize = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size();
		FloatingTask task;
		if (parserVar.isDeadlineTask()){
			task = new DeadlineTask(parserVar.getCommand(),parserVar.getEndTime());
		} else if (parserVar.isTimedTask()){
			task = new TimedTask(parserVar.getCommand(),parserVar.getStartTime(),parserVar.getEndTime());
		} else {
			task = new FloatingTask(parserVar.getCommand());
		}
		if (index > totalSize || index <1){
			return "index is not within the number of tasks in taskList";
		} else {
			uList.push(new Parser("edit " + parserVar.getRawCommand()));
			taskListVar.editTask(index, task);
			return "Task updated";
		}
	}

}
