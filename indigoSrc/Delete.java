package indigoSrc;

/* This class is the delete class which deletes the task that user
 * would want to delete. User will have to indicate the index which the task 
 * lies to delete the task.
 * 
 * @author Ken
 */

public class Delete extends CommandClass{
	
	FloatingTask toDo;
	int index;

	@Override
	public String execute() {
		return delete();
	}
	
	public Delete(Parser parsing, UndoList psList, TaskList taskList){
		parserVar = parsing;
		uList = psList;
		taskListVar = taskList;
		int totalSize = taskListVar.getSize();
		index = parserVar.getEditIndex();
		if (index > totalSize || index < 1){
			isValid = false;
		}
	}
	
	public String delete() throws ArrayIndexOutOfBoundsException{
		if (isValid==false){
			return "Invalid index";
		} else {
			taskListVar.deleteTask(index);
			return toDo.toString() + " is deleted";
		}
	}
	
	public String undo(){
		taskListVar.addTask(index, toDo);
		return toDo.toString() + " is re-added!";
	}

}
