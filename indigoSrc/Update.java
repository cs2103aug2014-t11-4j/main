package indigoSrc;
/* This class is the update class which can update the task that user
 * would want to edit. User will have to indicate the index which the task 
 * lies to update the task.
 * 
 * @author Ken
 */

public class Update extends CommandClass{
	
	FloatingTask toDo;{
		assert parserVar.getCommand() instanceof String;
	}
	Integer editIndex;{
		assert editIndex instanceof Integer;
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return edit();
	}

	public Update(Parser parsing, UndoList psList, TaskList taskList){
		parserVar = parsing;
		uList = psList;
		taskListVar = taskList; 
		if (parserVar.isDeadlineTask()){
			toDo = new DeadlineTask(parserVar.getCommand(),parserVar.getEndTime());
		} else if (parserVar.isTimedTask()){
			toDo = new TimedTask(parserVar.getCommand(),parserVar.getStartTime(),parserVar.getEndTime());
		} else {
			toDo = new FloatingTask(parserVar.getCommand());
		}

	}
	
	
	public String edit() throws ArrayIndexOutOfBoundsException{
		int index = parserVar.getEditIndex();
		int totalSize = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size();
		if (index > totalSize || index <1){
			return "index is not within the number of tasks in taskList";
		} else {
			//uList.push(new Parser("edit " + parserVar.getRawCommand()), parserVar);
			taskListVar.editTask(index, toDo);
			return "Task updated";
		}
	}

	@Override
	public String undo() {
		// TODO Auto-generated method stub
		return null;
	}

}
