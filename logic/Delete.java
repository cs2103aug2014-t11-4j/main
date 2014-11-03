package logic;

import indigoSrc.DeadlineTask;
import indigoSrc.FloatingTask;
import indigoSrc.Parser;
import indigoSrc.TaskList;
import indigoSrc.TimedTask;

/* This class is the delete class which deletes the task that user
 * would want to delete. User will have to indicate the index which the task 
 * lies to delete the task.
 * 
 * @author Ken
 */

public class Delete extends CommandClass{
	
	FloatingTask toDo;
	int index;
	private boolean byNum;

	@Override
	public String execute() {
		return delete();
	}
	
	public Delete(Parser parsing, TaskList taskList){
		parserVar = parsing;
		taskListVar = taskList;
		byNum = deleteByNum(parserVar.getRawCommand());
		int totalSize = taskListVar.getSize();
		index = parserVar.getEditIndex();
		if (index > totalSize || index < 1){
			toDo = null;
			isValid = false;
		} else {
			toDo = taskListVar.get(index);
		}
	}
	
	private boolean deleteByNum(String input){
		input = input.trim();
		if(input.length()==1){
			try{
				Integer num = Integer.parseInt(input);
				return true;
			} catch(Exception err) {
			}
		}
		return false;
	}
	
	private int searchForTasks(){
		Search findClass = new Search(parserVar, taskListVar);
		return findClass.getFound();
	}
	 
	
	private String delete() throws ArrayIndexOutOfBoundsException{
		if (isValid==false){
			return "Invalid index";
		} 
		taskListVar.deleteTask(index);
		/*else {
			if(byNum){
				taskListVar.deleteTask(index);
			} else {
				//delete a floating task by float index
				if(parserVar.getCommand().contains("-f")){
					index = index + taskListVar.getTimedList().size();
					System.out.println(index);
					taskListVar.deleteTask(index);
				} else if(parserVar.getCommand().contains("-d")){
					System.out.println(index);
					taskListVar.deleteTask(index);
				} else {
					return "Schortcut not recognised";
				} */
				FloatingTask task;
				if (parserVar.isDeadlineTask()){
					task = new DeadlineTask(parserVar.getCommand(),parserVar.getEndTime());
				} else if (parserVar.isTimedTask()){
					task = new TimedTask(parserVar.getCommand(),parserVar.getStartTime(),parserVar.getEndTime());
				} else {
					task = new FloatingTask(parserVar.getCommand(),parserVar.getLocation());
				}
				//taskListVar.deleteTask(task); 
			//}
			return toDo.toString() + " is deleted";
		//}
	}
	
	public String undo(){
		taskListVar.addTask(index, toDo);
		return toDo.toString() + " is re-added!";
	}

}
