package indigoSrc;

/* This class is the delete class which deletes the task that user
 * would want to delete. User will have to indicate the index which the task 
 * lies to delete the task.
 * 
 * @author Ken
 */

public class Delete extends CommandClass{

	@Override
	public String execute() {
		return delete();
	}
	
	public Delete(Parser parsing, ParserList parseL, TaskList taskList){
		parserVar = parsing;
		psl = parseL;
		taskListVar = taskList;
	}
	
	public String delete() throws ArrayIndexOutOfBoundsException{
		int index = parserVar.getEditIndex();
		int totalSize = taskListVar.getFloatingList().size() + taskListVar.getTimedList().size();
		if (index > totalSize || index < 1){
			return "Invalid index";
		} else {
			FloatingTask task = taskListVar.get(index);
			if(!(task instanceof DeadlineTask)){
				index += taskListVar.getTimedList().size();
			}
			psl.push(new Parser("add " + index + " " + taskListVar.get(index).getDescription()));
			taskListVar.deleteTask(index);
			return "Task deleted";
		}
	}

}
