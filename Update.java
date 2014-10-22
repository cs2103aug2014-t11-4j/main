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
		FloatingTask task = new FloatingTask(parserVar.getCommand());
		try{
			psl.push(new Parser("edit " + index + " " + taskListVar.get(index).getDescription()));
		} catch (ArrayIndexOutOfBoundsException err){
			return "index is not within the number of tasks in taskList";
		}
		taskListVar.editTask(index, task);
		return "Task updated";
	}

}
