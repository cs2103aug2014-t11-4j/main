package logic;

import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.Parser;
import indigoSrc.Task;
import indigoSrc.TaskList;
import indigoSrc.TimedTask;

/* This class is the delete class which deletes the task that user
 * would want to delete. User will have to indicate the index which the task 
 * lies to delete the task.
 * 
 * @author Ken
 */

public class Delete extends CommandClass{
	
	Task toDo;
	int index;
	private boolean byNum;

	@Override
	public String execute() {
		return delete();
	}
	
	public void main(String arg[]){
		Delete del = new Delete(new Parser("delete 3"), new TaskList());
		int index = parserVar.getEditIndex();
		System.out.println(index +"getmain");
		index = parserVar.getRawEditIndex();
		System.out.println(index +"getrawmain");
	}
	
	public Delete(Parser parsing, TaskList taskList){
		parserVar = parsing;
		taskListVar = taskList;
		int totalSize = taskListVar.getSize();
		index = parserVar.getEditIndex();
		System.out.println(index +"get");
		if (index > totalSize || index < 1){
			toDo = null;
			isValid = false;
		} else {
			toDo = taskListVar.get(index);
		}
	}
	
	private String delete() throws ArrayIndexOutOfBoundsException{
		if (isValid==false){
			return "Invalid index";
		} 
		System.out.println(index + "getRaw");
		taskListVar.deleteTask(index);
		return toDo.toString() + " is deleted";

	}
	
	public String undo(){
		taskListVar.addTask(index, toDo);
		return toDo.toString() + " is re-added!";
	}

}
