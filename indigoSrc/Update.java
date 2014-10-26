package indigoSrc;
import static org.junit.Assert.*;

import org.junit.Test;

public class Update extends CommandClass{

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		return edit();
	}

	public Update(Parser parsing, ParserList parseL, TaskList taskList){
		parserVar = parsing;
		psl = parseL;
		this.taskListVar = taskList; 

	}
	
	@Test
	public void testUpdate(){
		Parser testParse = new Parser("edit 1 change to study");
		assertEquals(edit(), "Task updated");
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
		psl.push(new Parser("edit " + index + " " + taskListVar.get(index).getDescription()));
		taskListVar.editTask(index, task);
		return "Task updated";
		}
	}

}